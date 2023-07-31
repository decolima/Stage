/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mqtt.start;

import mqtt.control.threads.SubscribeClient;
import mqtt.control.threads.TagSearch;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author andrelima
 */
public class Main {

    public static void main(String[] args) {

        int cycle = 1;
        int timelastcycle = 0;
        LocalDateTime lastCycle = LocalDateTime.now();
        
        TagSearch ts = new TagSearch();
        SubscribeClient sc = new SubscribeClient();
        sc.manager();
        
        while (true) {

            timelastcycle = (int) lastCycle.until(LocalDateTime.now(), ChronoUnit.MINUTES);

            if (cycle == 1 || timelastcycle >= 3) {

                System.out.println("-------------- Start Client Mqtt RsBy---------------\n");
                System.out.println("Cycle: " + cycle + " Started in " + lastCycle.toString() + "\n");

                cycle++;

                lastCycle = LocalDateTime.now();

                /*ExecutorService executorService = Executors.newFixedThreadPool(2);

                //executorService.submit(new SubscribeClient());
                //executorService.submit(new TagSearch());

                executorService.shutdown();

                try {
                    if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */
                ts.manager();
                //sc.manager();
                

            } else {
                System.out.println("----- Await Next Cycle -----" + LocalDateTime.now().toString()
                        + " Last Cycle: " + lastCycle.toString());

                try {

                    if ((timelastcycle - 1) < 0) {
                        Thread.sleep(3 * 60000);
                    } else {
                        Thread.sleep(60000);
                    }

                } catch (Exception e) {
                }
            }
        }

    }
}
