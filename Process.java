    //Maintains all the process data
    public class Process implements Comparable<Process>{
        int arrivalTime;
        int burstTime;
        int totalCPUTime;
        int multiplier;
        
        public Process(int A,int B, int C, int M){
            this.arrivalTime = A;
            this.burstTime = B;
            this.totalCPUTime = C;
            this.multiplier = M;
        }
        
        @Override
        public String toString() {
            return arrivalTime + " " + burstTime + " " + totalCPUTime + " " + multiplier;
        }
        
        
        // Default to sort processes by arrival time,
        // can be changed by passing comparator
        public int compareTo(Process p2) {
            if(this.arrivalTime==p2.arrivalTime)
                return 0;
            return this.arrivalTime>p2.arrivalTime?1:-1;
        }
    }
