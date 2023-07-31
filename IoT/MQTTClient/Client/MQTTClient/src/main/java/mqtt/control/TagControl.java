/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.control;

import mqtt.entity.Tag;
import mqtt.entity.maps.TagLog;
import mqtt.service.TagLogService;
import mqtt.service.TagService;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.ArrayList;
/**
 *
 * @author andrelima
 */
public class TagControl {
    
    private final TagLogService tls = new TagLogService();
    private final TagService ts = new TagService();
    private final PublishLogControl pc = new PublishLogControl();
    
    public void loadTagtoWork(){
        
        List<Tag> listtag = ts.getall();
        List<TagLog> listlog = tls.getNews();
        
        //adding new tags to table TAG
        for(TagLog lg : listlog) {
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime discovery = LocalDateTime.parse(lg.getDatadiscovery(), formatter);

            
            Tag n = new Tag(null, lg.getAddress(), lg.getName(), discovery, 0);
            
            if(listtag.stream()
                    .filter(e -> e.equals(n))
                    .findAny()
                    .isEmpty()) {
                
                listtag.add(n);
            }
                    
        }
        
        if(listtag.stream().filter(e -> e.getId() == null).count() > 0) {
            
            System.out.println("New entries: " + listtag.stream().filter(e -> e.getId() == null).count());
            
            listtag.forEach(e -> {
        
             //System.out.println(e);    
                if(e.getId() == null){
                        ts.save(e);
                }
            });
        }
        else {
            System.out.println("Has no new MacAddress");    
        }
        
        //filtring only tags that's not ignored o deleted
        
        listtag = listtag.stream()
                            .filter(e -> (e.getStatus() != 2 && e.getStatus() != 3 ))
                            .collect(Collectors.toList());
                  
        
        prepareTagPublish(listtag, listlog);
        
        //System.out.println("Fim");
        
    }

    private void prepareTagPublish(List<Tag> listtag, List<TagLog> listlog) {
        
        List<TagLog> listlogfinal = new ArrayList<>();


        //revome status ignore o deleted
        listlog = listlog.stream().filter(a -> listtag.stream().filter(b -> b.getAddress().equals(a.getAddress()))
                .findAny()
                .isPresent()

        ).collect(Collectors.toList());

        //revome duplicated
        for(TagLog lg : listlog) {
            if(listlogfinal.stream().filter(d -> d.equals(lg)).findFirst().isEmpty()) {
                listlogfinal.add(lg);
            }
        }

        //get tag status
        for(Tag t : listtag){
            for(TagLog tg : listlogfinal) {

                if(t.getAddress().equals(tg.getAddress())){
                    tg.setStatus_tag(t.getStatus());
                }
            }
        }


        //including tag missing
        for(Tag t : listtag){

            if(listlogfinal.stream().filter(a -> a.getAddress().equals(t.getAddress())).findAny().isEmpty()){

                TagLog aa = new TagLog(t.getAddress(), t.getName(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), "0", "0", 9);
                listlogfinal.add(aa);
            }

        }

        //all adrres ready to be publish
        pc.publish(listlogfinal);
                 
    }
    
    public Tag findAddress(String address){
        Tag found = ts.find(address);
        return found;
    }

    public void save(Tag t) {
        ts.save(t);
    }
    
}
