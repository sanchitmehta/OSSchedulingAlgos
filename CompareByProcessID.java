import java.util.Comparator;

/**
 * Created by sanchitmehta on 03/03/17.
 */
public class CompareByProcessID implements Comparator<Process> {

    public int compare(Process p1, Process p2 ){
        return p1.procID>p2.procID?1:-1;
    }
}