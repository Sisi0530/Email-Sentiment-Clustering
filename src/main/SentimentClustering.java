package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;
import javax.swing.JOptionPane;
/**
 *
 * @author jc166795,jc270969
 */
public class SentimentClustering {

	/** Read Email data from data file
	 *  Input file format: <id, topic, timestamp, [features]>
	 */
    public static void main(String[] args) throws NumberFormatException, IOException {
        
    	
    	//Define start time
    	long startTime = System.currentTimeMillis();
    	
    	//Define input parameters minPts and epsilon for DBSCAN
        int minPts = 5;
        double epsilon = 0.15;
        
        //Change input and output file directory to be your own directory
        String inputfileName = "enronemail_owl_ts_2001_01_05";
        String inputfilePath = "data" + File.separator + inputfileName;
        
        String outfilePath = "results" + File.separator + "minpts " + minPts + 
                "_eps " + epsilon + "_" + inputfileName + "_" + 
                startTime + File.separator;;   
        
        boolean ret = new File(outfilePath).mkdir();
        if (ret == false) {     
             System.out.println("Error: unable to mkdir" + new File(outfilePath).getAbsolutePath());
            return;
        }
    	
        System.out.println("---------Load Email data--------");
        List<Email> emails = new ArrayList<>();
        FileOperator fop = new FileOperator();                   
        emails = fop.readTxtFile(inputfilePath + ".txt");        
        System.out.println("---------Number of emails: " + emails.size() + "----------");
        
        //Perform temporal clustering
        System.out.println("----------Prepare Temporal Clustering---------");
        EmailTemporalClassify emailClassify = new EmailTemporalClassify(emails);
        emailClassify.process();        
        emailClassify.printSummary();
              
        //Apply algoDBSCAN to each temporal group
        int week;
        int day;
        String outfileName = "";
        List<Email> subgroupEmails = new ArrayList<>();
        
        System.out.println("----------Prepare Topic Clustering----------");
        for(Map.Entry<Integer, Map<Integer, List<Email>>> entry : emailClassify.emailGroups.entrySet()){
            week = entry.getKey();
            System.out.println("Week of year: " + week + " number day of week is " + entry.getValue().size());

            for(Map.Entry<Integer,List<Email>> entry1 : entry.getValue().entrySet()){
                day = entry1.getKey();
                System.out.println("for day " + day + " emails: " + entry1.getValue().size());
                
                subgroupEmails.clear();
                subgroupEmails.addAll(entry1.getValue());
                
                //Perform topic clustering - DBSCAN algorithm
                AlgoDBSCAN algoDBSCAN = new AlgoDBSCAN();
                algoDBSCAN.runAlgorithm(subgroupEmails, minPts, epsilon);
                outfileName = outfilePath + "week" + week + " day " + (day-1) + ".txt";
                algoDBSCAN.saveToFile(outfileName);
                                
            }
            
        }
        System.out.println("----------Topic Clustering Completed----------");
        
        // ----------------------------------------------

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;

		System.out.println("----------------------------------------------");
		System.out.println("STATS - TIME: Analysis took "
				+ TimeUnit.SECONDS.convert(totalTime, TimeUnit.MILLISECONDS)
				+ " seconds");

		// ----------------------------------------------

        
    }
    
    
}
