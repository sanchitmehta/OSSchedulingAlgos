
import java.util.*;

public class FCFS{
    
    ArrayList<Process> procs;
    ArrayList<Process> notStartedQ = new ArrayList<Process>();
    Queue<Process> readyQ 	= new ConcurrentLinkedQueue<Process>();
    ArrayList<Process> blockedQ= new ArrayList<Process>();
    ArrayList<Process> finishedQ = new ArrayList<Process>();
    
    int cycleCount = 0;
    int finishedCount = 0;
    
    public FCFS(ArrayList<Process> procs){
        this.procs = procs;
        int numProcs = procs.size();
        
        //default sorts by arrival time
        Collections.sort(procs);
        readyQ.add();
        for(int i=1;i<numProcs;i++)
            notStartedQ.add(procs.get(i));
        
        
        //While all processes have not finished execution
        //clock goes tick tock tick tock
        while(finishedCount<numProcs){
            
            //put unstarted process into ready
            for(Process p : notStartedQ){
                if(notStartedQ.size()!=0){
                    notStartedQ.get(0).arrivalTime == cycle
                    readyQ.add(notStartedQ.get(i));
                }
            }
        }
    }
}
