/*
 *
 * @Course : Operating Systems , Lab 2
 * @Author : Sanchit Mehta<sanchit.mehta@cs.nyu.edu>
 * @Desc: Main class that scans input and calls
 *        all Schedulers
 *
 */

import java.util.*;

public class RoundRobin {
    ArrayList<Process> procs;
    ArrayList<Process> notStartedQ = new ArrayList<Process>();
    Queue<Process> readyQ 	= new LinkedList<>();
    ArrayList<Process> blockedQ= new ArrayList<Process>();
    ArrayList<Process> finishedQ = new ArrayList<Process>();
    ArrayList<Process> add2Ready = new ArrayList<>();

    int cycleCount = 0;
    int cpuTime = 0;
    int ioTime = 0;
    int finishedCount = 0;
    int quantum = 2;
    boolean detailedOP = false;

    public RoundRobin(ArrayList<Process> procs,boolean detailedOP){

        this.detailedOP = detailedOP;
        this.procs = procs;
        int numProcs = procs.size();
        System.out.print("The original input was: "+numProcs);
        for(int i=0;i<procs.size();i++) {
            Process p = procs.get(i);
            System.out.print(" ("+p.arrivalTime+" "
                    +p.burstTime+" "
                    +p.totalCPUTime+" "
                    +p.multiplier+") ");
        }
        System.out.println();
        Collections.sort(procs,new CompareByArrivalTime());
        System.out.print("The (sorted) input is: "+numProcs);
        for(int i=0;i<procs.size();i++) {
            Process p = procs.get(i);
            System.out.print(" ("+p.arrivalTime+" "
                    +p.burstTime+" "
                    +p.totalCPUTime+" "
                    +p.multiplier+") ");
        }

        //default sorts by arrival time
        cycleCount = 0;
        Process currRunningProc = null;


        for(int i=0;i<numProcs;i++)
            notStartedQ.add(procs.get(i));

        System.out.println("\n\n");
        if(detailedOP)
            printProcessState(numProcs,0);

        //While all processes have not finished execution
        //clock goes tick tock tick tock
        while(finishedCount<numProcs){

            //put unstarted process into ready
            //since unstarted is sorted by arrtime, we'll only
            //check the list head
            boolean flag = false;
            ArrayList<Process> toBeRemoved = new ArrayList<>();
            for(Process p:notStartedQ){
                if(p.arrivalTime == cycleCount){
                    readyQ.add(p);
                    flag=true;
                    toBeRemoved.add(p);
                }
            }
            if(flag)
                if(flag) {
                    for(Process p:toBeRemoved)
                        notStartedQ.remove(p);
                }



            //no running process, getting a process to run from ready
            if(currRunningProc == null&&!readyQ.isEmpty()){
                quantum=2;
                currRunningProc = readyQ.remove();
                if(currRunningProc.currCPUBurst<=0) {
                    currRunningProc.setRandomBurstForCPU();
                }
            }

            cycleCount++;
            quantum--;
            if(detailedOP)
                printProcessState(numProcs,quantum);

            if(currRunningProc!=null){
                currRunningProc.addRunTimes();
                cpuTime++;
            }

            for(Process p:readyQ){
                p.waitingTime++;
            }

            if(!blockedQ.isEmpty()){
                ioTime++;
                int addToReadyNum = 0;
                Process[] blockedProcs = blockedQ.toArray(new Process [0]);
                for(int i=0;i<blockedProcs.length;i++){
                    blockedProcs[i].ioTime++;
                    blockedProcs[i].currIOBurst--;
                    if(blockedProcs[i].currIOBurst == 0){
                        addToReadyNum++;
                        add2Ready.add(blockedProcs[i]);
                        blockedQ.remove(blockedProcs[i]);
                    }
                }
                //if there are multiple processes turned ready at the same time, then sort by priority
                //add2Ready.addAll(addToReadyProcess);
            }

                if (currRunningProc != null) {
                    if (currRunningProc.totalCPUTimeRemaining == 0) {
                        finishedCount++;
                        currRunningProc.finishingTime = cycleCount;
                        readyQ.remove(currRunningProc);
                        finishedQ.add(currRunningProc);
                        currRunningProc = null;
                        quantum=2;
                    } else if ((currRunningProc.totalCPUTimeRemaining > 0 &&
                            currRunningProc.currCPUBurst == 0)) {
                        blockedQ.add(currRunningProc);
                        currRunningProc = null;
                        quantum=2;
                    }else if(quantum<=0){
                        //readyQ.add(currRunningProc);
                        add2Ready.add(0,currRunningProc);
                        currRunningProc=null;
                        quantum=2;
                    }
                }
            Collections.sort(add2Ready,new CompareByArrivalTime());
            for(Process p :add2Ready)
                readyQ.add(p);
            add2Ready.clear();
        }
        System.out.println("\n\n\nThe scheduling algorithm used was Round Robin\n\n");
        Collections.sort(procs,new CompareByArrivalTime());
        float turnaround=0;
        float waiting=0;
        for(int i=0;i<procs.size();i++) {
            Process p = procs.get(i);
            System.out.println("\n");
            System.out.println("Process "+i+":");
            System.out.println("\t(A,B,C,M) = ("+p.arrivalTime+","
                    +p.burstTime+","
                    +p.totalCPUTime+","
                    +p.multiplier+")");
            System.out.println("\tFinishing time: "+p.finishingTime);
            System.out.println("\tTurnaround time: "+(p.finishingTime-p.arrivalTime));
            System.out.println("\tI/O time: "+p.ioTime);
            System.out.println("\tWaiting time: "+p.waitingTime);
            turnaround+=(p.finishingTime-p.arrivalTime);
            waiting+=p.waitingTime;
            p.clear();
        }

        System.out.println("\n\nSummary Data: ");
        System.out.println("\tFinishing time: "+cycleCount);
        System.out.println("\tCPU Utilization: "+String.format("%.6f",(float)cpuTime/cycleCount));
        System.out.println("\tI/O Utilization: "+String.format("%.6f",(float)ioTime/cycleCount));
        System.out.println("\tThroughput: "+String.format("%.6f",(((float)numProcs/(float)cycleCount)*100))+" processes per hundred cycles");
        System.out.println("\tAverage turnaround time: "+String.format("%.6f",(float)(turnaround/numProcs)));
        System.out.println("\tAverage waiting time: "+String.format("%.6f",(float)(waiting/numProcs)));
        Process.randomGenerator.resetPtr();
    }

    public void printProcessState(int numProcs,int quantum){
        System.out.print("Before cycle:\t"+cycleCount+"  ");
        for(int i=0;i<numProcs;i++){
            if(blockedQ.contains(procs.get(i)))
                System.out.print("\tblocked  "+procs.get(i).currIOBurst);
            else if(readyQ.contains(procs.get(i)))
                System.out.print("\tready   0");
            else if(finishedQ.contains(procs.get(i)))
                System.out.print("\tterminated  ");
            else if(notStartedQ.contains(procs.get(i)) && cycleCount <= procs.get(i).arrivalTime)
                System.out.print("\tunstarted 0");
            else
                System.out.print("\trunning  "+Math.min((quantum+1),procs.get(i).currCPUBurst));
        }
        System.out.println(".");
    }
}
