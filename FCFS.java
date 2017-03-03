
/*
 *
 * @Course : Operating Systems , Lab 2
 * @Author : Sanchit Mehta<sanchit.mehta@cs.nyu.edu>
 * @Desc: FCFS Implementation
 *
 */


import java.util.*;

public class FCFS{
    
    ArrayList<Process> procs;
    ArrayList<Process> notStartedQ = new ArrayList<Process>();
    Queue<Process> readyQ 	= new LinkedList<Process>();
    ArrayList<Process> blockedQ= new ArrayList<Process>();
    ArrayList<Process> finishedQ = new ArrayList<Process>();
    
    int cycleCount = 0;
    int finishedCount = 0;
    int cpuTime = 0;
    int ioTime = 0;
    
    public FCFS(ArrayList<Process> procs){
        this.procs = procs;
        int numProcs = procs.size();
        
        //default sorts by arrival time
        Collections.sort(procs);
        readyQ.add(procs.get(0));
        count = procs.poll().arrivalTime;
        Process currRunningProc = null;
        
        for(int i=1;i<numProcs;i++)
            notStartedQ.add(procs.get(i));
        
        
        //While all processes have not finished execution
        //clock goes tick tock tick tock
        while(finishedCount<numProcs){
            
            //put unstarted process into ready
            //since unstarted is sorted by arrtime, we'll only
            //check the list head
            if(notStartedQ.size()!=0){
                if(notStartedQ.get(0).arrivalTime == cycle){
                    readyQ.add(notStartedQ.get(0));
                    notStartedQ.remove(0);
                }
            }
            
            //no running process, getting a process to run from ready
            if(currRunningProc == null&&!readyQ.isEmpty()){
                currRunningProc = readyQ.remove();
                currRunningProc.randomBurstTime();
            }
            
            if(currRunningProc!=null){
                currRunningProc.run();
                cpuTime++;
            }
            
            
        }
    }
}
