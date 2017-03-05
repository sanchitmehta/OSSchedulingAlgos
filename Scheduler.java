/*
 *
 * @Course : Operating Systems , Lab 2
 * @Author : Sanchit Mehta<sanchit.mehta@cs.nyu.edu>
 * @Desc: Main class that scans input and calls 
 *        all Schedulers
 *
 */

import java.util.*;
import java.io.*;

public class Scheduler {
    
    private String fileName;
    private ArrayList <Process> procs = new ArrayList <>();
    private static int time, running, cpuBurst;
    private static int[] blocked;
    private static int[] bursts;
    private static BufferedReader br;
    private static boolean isDone, verbose = false;
    
    
    
    public Scheduler(String inputFile,String algo,Boolean detailedOP) throws Exception {
        this.fileName = inputFile;
        readInput();
        System.out.println("\n\n");
        switch(algo.toLowerCase()){
            case "fcfs":
                FCFS f = new FCFS(procs,detailedOP);
                break;
            case "sjf":
                SJS sj = new SJS(procs,detailedOP);
                break;
            case "rr":
                RoundRobin rr = new RoundRobin(procs,false);
                break;
            case "uni":
                UniProgrammed up = new UniProgrammed(procs,detailedOP);
                break;
        }
        System.out.println("\n\n");
    }
    
    public void readInput() throws Exception {
        File f = new File(fileName);
        Scanner sc = new Scanner(f);
        String input = sc.nextLine();
        System.out.println("\n");
        input = input.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] inputs = input.split("\\s+");
        int noOfProcesses = Integer.parseInt(inputs[0]);
        for (int i = 1; i < noOfProcesses * 4; i = i + 4) {
            Process p = new Process(Integer.parseInt(inputs[i]),
                                    Integer.parseInt(inputs[i + 1]),
                                    Integer.parseInt(inputs[i + 2]),
                                    Integer.parseInt(inputs[i + 3]),
                                    i-1);
            procs.add(p);
        }
        Collections.sort(procs);
    }
    
    
    public static void main(String args[]) {
        if (args.length != 3)
            throw new IllegalArgumentException("Exactly 3 parameters required : <Input File Name> <SchedulingAlgo: (fcfs,sjf,rr,uni)> <Detailed-OP : (0,1)>");
        try {
            Scheduler s = new Scheduler(args[0],args[1],args[2].equals("1")?true:false);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Erooorrr");
        }
    }
}
