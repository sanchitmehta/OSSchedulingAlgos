

/*
 *
 * @Course : Operating Systems , Lab 2
 * @Author : Sanchit Mehta<sanchit.mehta@cs.nyu.edu>
 * @Desc: Maintains all the process data
 *
 */

import java.util.*;
import java.io.*;

public class Process implements Comparable < Process > {
    
    //From input
    int arrivalTime;
    int totalCPUTime;
    int burstTime;
    int multiplier;
    int procID;
    
    //Time Exausted to determine completion
    int totalCPUTimeRemaining = 0;
    int totalBurstTimeExausted = 0;
    
    //Burst Info
    int currCPUBurst = 0;
    int currIOBurst = 0;
    
    //Final Outputs
    int finishingTime = 0;
    int ioTime = 0;
    int waitingTime = 0;

    //RR
    int lastRunning = 0;
    
    public Process(int A, int B, int C, int M,int id) {
        this.arrivalTime = A;
        this.burstTime = B;
        this.totalCPUTime = C;
        this.multiplier = M;
        this.procID = id;
        this.totalCPUTimeRemaining = C;
    }

    public void clear(){
        totalCPUTimeRemaining = totalCPUTime;
        totalBurstTimeExausted = 0;

        currCPUBurst = 0;
        currIOBurst = 0;

        finishingTime = 0;
        ioTime = 0;
        waitingTime = 0;
    }

    @Override
    public String toString() {
        return arrivalTime + " " + burstTime + " " + totalCPUTime + " " + multiplier;
    }
    
    public void setRandomBurstForCPU() {
        int random = randomGenerator.get(this.burstTime);
        currCPUBurst = random;
        currIOBurst = multiplier*random;
    }
    
    public void setRandomBurstForIO() {
        currIOBurst = randomGenerator.get(this.burstTime);
    }
    
    //Sorts processes by arrival time by defaulted
    public int compareTo(Process p2) {
        if (this.arrivalTime == p2.arrivalTime)
            return 0;
        return this.arrivalTime > p2.arrivalTime ? 1 : -1;
    }
    
    public void addRunTimes() {
        currCPUBurst--;
        totalCPUTimeRemaining--;
    }
    
    
    //Static class to get a random number from the pre generated randno file
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
        
        static int get(int U) {
            if (randomNums.size() == 0)
                randomGenerator.initialise();
            return 1 + (randomNums.get(count++) % U);
        }
        
        static void resetPtr() {
            count = 0;
        }
    }
}
