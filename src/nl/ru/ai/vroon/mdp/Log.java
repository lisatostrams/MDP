/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ru.ai.vroon.mdp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lisa Tostrams s4386167 Klasse om alle stappen weg te schrijven naar
 * log file
 */
public class Log {

    private String mdpname;

    BufferedWriter writer = null;

    /**
     *
     * @param mdp
     * @param network
     */
    public Log(String mdp) {
        this.mdpname = mdp;

        try {
            //create a temporary file
            String timeLog = new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss").format(Calendar.getInstance().getTime());
            File logFile = new File(timeLog + "_Log_MDP_" +mdpname + ".txt");

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("Logfile for mdp " + mdp + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   public void write(String write) {
        try {
            writer.write(write);
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    

   public void close() {
        try {
            writer.close();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    
}