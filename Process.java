    //Maintains all the process data
    public class Process implements Comparable<Process>{
        int arrivalTime;
        int burstTime;
        int totalCPUTime;
        int multiplier;
        int totalWaitTime;
        
        int CPUExecutedTime = 0;
        int burstTimeTaken = 0;
        
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
        
        public int getBurstTime(){
            return randomGenerator.get(this.B);
        }
        
        //Sorts processes by arrival time by defaulted
        public int compareTo(Process p2) {
            if(this.arrivalTime==p2.arrivalTime)
                return 0;
            return this.arrivalTime>p2.arrivalTime?1:-1;
        }
        
        public void run(){
            CPUExecutedTime++;
            burstTimeTaken++;
        }
        
        
        //Static class to return a random number from the pre generated file
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
                int rand = randomNums.get(count++);
                return 1 + (rand % U);
            }
            
            static void resetPtr() {
                count = 0;
            }
        }
    }
