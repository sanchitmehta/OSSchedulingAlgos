/*
 *
 * @Course : Operating Systems , Lab 2
 * @Author : Sanchit Mehta<sanchit.mehta@cs.nyu.edu>
 * @NYUID :  N17896805
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
    }
    
    public void readInput() throws Exception {
        File f = new File(fileName);
        Scanner sc = new Scanner(f);
        String input = sc.nextLine();
        input = input.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] inputs = input.split("\\s+");
        int noOfProcesses = Integer.parseInt(inputs[0]);
        System.out.println(noOfProcesses);
        for (int i = 1; i < noOfProcesses * 4; i = i * 4) {
            Process p = new Process(Integer.parseInt(inputs[i]),
                                    Integer.parseInt(inputs[i + 1]),
                                    Integer.parseInt(inputs[i + 2]),
                                    Integer.parseInt(inputs[i + 3]));
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
    
    //Static class to return a random number from the pre rand generated file
    static class randomGenerator {
        private static ArrayList < Integer > randomNums = new ArrayList < Integer > ();
        private static int count = 0;
        static File file = new File("random-numbers.txt");
        static void initialise() {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    randomNums.add(Integer.parseInt(line.trim()));
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        static void reset() {
            count = 0;
        }
        
        static int get(int U) {
            if (randomNums.size() == 0)
                randomGenerator.initialise();
            int rand = randomNums.get(count++);
            return 1 + (rand % U);
        }
    }
}
