import java.util.Comparator;

/**
 * Created by sanchitmehta on 03/03/17.
 */

public class CompareByExecutionTime implements Comparator<Process> {

    public int compare(Process p1, Process p2 ){
        if(p1.totalCPUTimeRemaining==p2.totalCPUTimeRemaining){
            if(p1.arrivalTime==p2.arrivalTime)
                return p1.procID>p2.procID?1:-1;
            else
                return p1.arrivalTime>p2.arrivalTime?1:-1;
        }else
            return p1.totalCPUTimeRemaining>p2.totalCPUTimeRemaining?1:-1;
    }
}
