/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control;

import iot.entity.Tag;
import iot.entity.maps.TagLog;
import iot.service.TagLogService;
import iot.service.TagService;
import iot.service.control.ErrorLog;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 *
 * @author andrelima
 */
public class TagControl extends ErrorLog {

    private final TagLogService tls = new TagLogService();
    private final TagService ts = new TagService();
    private final PublishControl pc = new PublishControl();
    private final ControllerControl cc = new ControllerControl();

    public void loadTagtoWork() {

        List<Tag> listtag = new ArrayList<>();
        List<TagLog> listlog = new ArrayList<>();

        try {
            listtag = ts.getall();
            listlog = tls.getNews();
        } catch (Exception e) {
            logError(e);
        }

        if (!listtag.isEmpty() || (cc.getController().getDiscovery() == 1 && !listlog.isEmpty())) {
            //adding new tags to table TAG
            for (TagLog lg : listlog) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime discovery = LocalDateTime.parse(lg.getDatadiscovery(), formatter);

                Tag n = new Tag(null, lg.getAddress(), lg.getName(), discovery, 0);

                if (listtag.stream()
                        .filter(e -> e.equals(n))
                        .findAny()
                        .isEmpty()) {

                    listtag.add(n);
                }

            }

            if (listtag != null && cc.getController().getDiscovery() == 1) {
                long nullIdCount = listtag.stream()
                        .filter(e -> e != null && e.getId() == null)
                        .count();

                if (nullIdCount > 0) {
                    System.out.println("New entries with null Id: " + nullIdCount);

                    listtag.forEach(e -> {
                        if (e != null && e.getId() == null) {
                            ts.save(e);
                        }
                    });
                }
            } else {
                System.out.println("Has no new MacAddress or this Controller is Discovery Disable");
            }

            //filtring only tags that's not ignored o deleted
            if (cc.getController().getDiscovery() == 0) {

                listtag = listtag.stream()
                        .filter(e -> (e.getStatus() == 1))
                        .collect(Collectors.toList());
                
                
            } else {
                listtag = listtag.stream()
                        .filter(e -> (e.getStatus() != 2 && e.getStatus() != 3))
                        .collect(Collectors.toList());
            }

            prepareTagPublish(listtag, listlog);

        } else {
            System.out.println("Has no address to monotoring");
        }

    }

    private void prepareTagPublish(List<Tag> listtag, List<TagLog> listlog) {

        List<TagLog> listlogfinal = new ArrayList<>();

        //revome status ignore o deleted
        /*listlog = listlog.stream()
                .filter(a -> listtag.stream().filter(b -> b.getAddress().equals(a.getAddress()))
                .findAny()
                .isPresent()
                ).collect(Collectors.toList());
        */

        //revome of Log duplicated and cancelled tags
        for (TagLog lg : listlog) {
            if (listlogfinal.stream().filter(d -> d.equals(lg)).findFirst().isEmpty()) {  
                if(listtag.stream().filter(a -> ((a.getStatus() == 1 || a.getStatus() == 0) && a.getAddress().equals(lg.getAddress()))).count()>0) {
                    listlogfinal.add(lg);
                } 
            }
        }

        //get tag status
        for (Tag t : listtag) {
            for (TagLog tg : listlogfinal) {

                if (t.getAddress().equals(tg.getAddress())) {
                    tg.setStatus_tag(t.getStatus());
                }
            }
        }

        //including tag missing
        for (Tag t : listtag) {

            if (listlogfinal.stream().filter(a -> a.getAddress().equals(t.getAddress())).findAny().isEmpty() && t.getStatus() == 1) {
                TagLog aa = new TagLog(t.getAddress(), t.getName(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), "0", "0", 9);
                    listlogfinal.add(aa);
            }

            if (listlogfinal.stream().filter(a -> a.getAddress().equals(t.getAddress())).findAny().isEmpty() && t.getStatus() == 0) {
                TagLog aa = new TagLog(t.getAddress(), t.getName(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), "0", "0", 0);
                    listlogfinal.add(aa);
            }
             
            
        }

        //all adrres ready to be publishTagDiscovery
        pc.publishTagDiscovery(listlogfinal);

    }

    public Tag findAddress(String address) {
        Tag found = ts.find(address);
        return found;
    }

    public void save(Tag t) {
        ts.save(t);
    }

}
