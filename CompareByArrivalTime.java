import java.util.Comparator;

/**
 * Created by sanchitmehta on 03/03/17.
 */
public class CompareByArrivalTime implements Comparator<Process> {

    public int compare(Process p1, Process p2 ){
        return p1.arrivalTime>p2.arrivalTime?1:-1;
    }
}