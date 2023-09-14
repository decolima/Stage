/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author andrelima
 */
public class ErrorLog {
    
    public static void logError(Exception e) {
        String logFileName = System.getProperty("user.dir") + File.separator + "errorlog.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " - Class: " + e.getClass().getName() + " - " + e.getMessage());
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
