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
    
    
    
    public Scheduler(String inputFile) throws Exception {
        this.fileName = inputFile;
        readInput();
        FCFS f = new FCFS(procs);
        System.out.println("\n\n\n\n");
        SJS sj = new SJS(procs);
        //UniProgrammed up = new UniProgrammed(procs);
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
        if (args.length != 1)
            throw new IllegalArgumentException("Exactly 1 parameters required : <Input File Name>");
        try {
            Scheduler s = new Scheduler(args[0]);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Erooorrr");
        }
    }
}
