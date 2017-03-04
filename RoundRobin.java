import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by sanchitmehta on 03/03/17.
 */
public class RoundRobin {
    ArrayList<Process> procs;
    ArrayList<Process> notStartedQ = new ArrayList<Process>();
    PriorityQueue<Process> readyQ 	= new PriorityQueue<Process>(new CompareByExecutionTime());
    ArrayList<Process> blockedQ= new ArrayList<Process>();
    ArrayList<Process> finishedQ = new ArrayList<Process>();

    int cycleCount = 0;
    int cpuTime = 0;
    int ioTime = 0;
    int finishedCount = 0;
    int quantum = 2;

    public RoundRobin(ArrayList<Process> procs){

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
        System.out.print("The original input was: "+numProcs);
        for(int i=0;i<procs.size();i++) {
            Process p = procs.get(i);
            System.out.print(" ("+p.arrivalTime+" "
                    +p.burstTime+" "
                    +p.totalCPUTime+" "
                    +p.multiplier+") ");
        }

        System.out.println("\n\n\nThe scheduling algorithm used was First Come First Served\n");
        //default sorts by arrival time
        cycleCount = 0;
        Process currRunningProc = null;


        for(int i=0;i<numProcs;i++)
            notStartedQ.add(procs.get(i));

        printProcessState(numProcs);

        //While all processes have not finished execution
        //clock goes tick tock tick tock
        while(finishedCount<numProcs){


            //put unstarted process into ready
            //since unstarted is sorted by arrtime, we'll only
            //check the list head
            boolean flag = false;
            for(Process p:notStartedQ){
                if(p.arrivalTime == cycleCount){
                    readyQ.add(p);
                    flag=true;
                }
            }
            if(flag)
                notStartedQ.remove(readyQ.peek());




            //no running process, getting a process to run from ready
            if(currRunningProc == null&&!readyQ.isEmpty()){
                currRunningProc = readyQ.remove();
                currRunningProc.setRandomBurstForCPU();
            }


            cycleCount++;
            quantum--;
            printProcessState(numProcs);


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
                PriorityQueue<Process> addToReadyProcess = new PriorityQueue<>(new TieBreakerComparator());
                Process[] pArray = blockedQ.toArray(new Process [0]);
                for(int i=0;i<pArray.length;i++){
                    pArray[i].ioTime++;
                    pArray[i].currIOBurst--;
                    if(pArray[i].currIOBurst == 0){
                        addToReadyNum++;
                        addToReadyProcess.add(pArray[i]);
                        blockedQ.remove(pArray[i]);
                    }
                }
                readyQ.addAll(addToReadyProcess);
            }

            if(!blockedQ.isEmpty())
                ioTime++;

            if(currRunningProc!=null){
                if(currRunningProc.totalCPUTimeRemaining==0){
                    finishedCount++;
                    currRunningProc.finishingTime = cycleCount;
                    readyQ.remove(currRunningProc);
                    finishedQ.add(currRunningProc);
                    currRunningProc=null;
                }else if(currRunningProc.totalCPUTimeRemaining>0&&
                        currRunningProc.currCPUBurst==0){
                    blockedQ.add(currRunningProc);
                    currRunningProc=null;
                }
            }
        }

        float turnaround=0;
        float waiting=0;
        Collections.sort(procs,new CompareByProcessID());
        for(int i=0;i<procs.size();i++) {
            Process p = procs.get(i);
            System.out.println("\n");
            System.out.println("Process "+i+":");
            System.out.println("\t(A,B,C,M) = ("+p.arrivalTime+","
                    +p.burstTime+","
                    +p.totalCPUTime+","
                    +p.multiplier+")");
            System.out.println("\tFinishing Time : "+p.finishingTime);
            System.out.println("\tTurnaround time: "+(p.finishingTime-p.arrivalTime));
            System.out.println("\tI/O time : "+p.ioTime);
            System.out.println("\tWaiting time : "+p.waitingTime);
            turnaround+=(p.finishingTime-p.arrivalTime);
            waiting+=p.waitingTime;
        }

        System.out.println("\n\nSummary Data: ");
        System.out.println("\tFinishing time: "+cycleCount);
        System.out.println("\tCPU Utilization: "+String.format("%.6f",(float)cpuTime/cycleCount));
        System.out.println("\tI/O Utilization: "+String.format("%.6f",(float)ioTime/cycleCount));
        System.out.println("\tThroughput: "+String.format("%.6f",(((float)numProcs/(float)cycleCount)*100))+" processes per hundred cycles");
        System.out.println("\tAverage turnaround time: "+String.format("%.6f",(float)(turnaround/numProcs)));
        System.out.println("\tAverage waiting time: "+String.format("%.6f",(float)(waiting/numProcs)));
    }

    public void printProcessState(int numProcs){
        System.out.print("Before cycle\t"+cycleCount+"  ");
        for(int i=0;i<numProcs;i++){
            if(blockedQ.contains(procs.get(i)))
                System.out.print("\tblocked  "+procs.get(i).currIOBurst+"\t");
            else if(readyQ.contains(procs.get(i)))
                System.out.print("\tready   0");
            else if(finishedQ.contains(procs.get(i)))
                System.out.print("\tterminated  ");
            else if(notStartedQ.contains(procs.get(i)) && cycleCount <= procs.get(i).arrivalTime)
                System.out.print("\tunstarted");
            else
                System.out.print("\trunning  "+((1+procs.get(i).currCPUBurst)-1));
        }
        System.out.println(".");
    }
}
