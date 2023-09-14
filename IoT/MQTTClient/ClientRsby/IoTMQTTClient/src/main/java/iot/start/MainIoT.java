/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package iot.start;

import iot.control.threads.SubscribeClient;
import iot.control.threads.TagSearch;
import iot.service.ControllerService;
import iot.service.control.ErrorLog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 *
 * @author andrelima
 */
public class MainIoT extends ErrorLog{

    public static void main(String[] args) {

        final String PROPERTIES_FILE = "config.properties";
        Properties properties = new Properties();

        int cycle = 0;
        int timelastcycle = 0;
        int interval = 1;

        TagSearch ts = new TagSearch();
        SubscribeClient sc = new SubscribeClient();
        sc.manager();

        while (true) {

            try (InputStream inputStream = ControllerService.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
                properties.load(inputStream);

                interval = Integer.valueOf(properties.getProperty("timecycle"));

            } catch (IOException e) {
                e.printStackTrace();
                interval = 1;
            }

            cycle++;
            System.out.println("-------------- Start Client Mqtt RsBy---------------\n");
            System.out.println("Cycle: " + cycle + " Started in " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "\n");

            ts.manager();

            try {
                Duration durationToAdd = Duration.ofMillis(interval);
                System.out.println("----- Await Next Cycle -----" + LocalDateTime.now().plus(durationToAdd));
                Thread.sleep(interval);
            } catch (Exception e) {
                logError(e);
            }

        }

    }
}
