import java.util.*;

public class exp6 {
    static class Process implements Comparable<Process> {
        int pid;
        int burstTime;
        int arrivalTime;
        int turnaroundTime;
        int waitingTime;
        int completionTime;

        public Process(int pid, int burstTime, int arrivalTime) {
            this.pid = pid;
            this.burstTime = burstTime;
            this.arrivalTime = arrivalTime;
        }

        @Override
        public int compareTo(Process p2) {
            return this.burstTime - p2.burstTime;
        }
    }

    public static void main(String[] args) {
        // List of processes
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 6, 2));
        processes.add(new Process(2, 2, 5));
        processes.add(new Process(3, 8, 1));
        processes.add(new Process(4, 3, 0));
        processes.add(new Process(5, 4, 4));

        // Sort processes by arrival time initially
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        // Priority queue for scheduling based on burst time (SJF logic)
        PriorityQueue<Process> pq = new PriorityQueue<>();
        Queue<Integer> q = new LinkedList<>();
        
        int currentTime = 0;
        int i = 0;

        while (i < processes.size() || !pq.isEmpty()) {
            // Add all processes that have arrived by the current time
            while (i < processes.size() && processes.get(i).arrivalTime <= currentTime) {
                pq.add(processes.get(i));
                i++;
            }

            // If the priority queue is not empty, process the next task
            if (!pq.isEmpty()) {
                Process curr = pq.remove();
                currentTime += curr.burstTime;
                curr.completionTime = currentTime;
                curr.turnaroundTime = curr.completionTime - curr.arrivalTime;
                curr.waitingTime = curr.turnaroundTime - curr.burstTime;
                
                q.add(curr.pid);

                System.out.println("Process " + curr.pid + " -> Completion Time: " + curr.completionTime +
                        ", Turnaround Time: " + curr.turnaroundTime + ", Waiting Time: " + curr.waitingTime);
            } else {
                // If no process is ready, increment time to the next process's arrival time
                if (i < processes.size()) {
                    currentTime = processes.get(i).arrivalTime;
                }
            }
        }

        System.out.println("Order of execution: " + q);
    }
}
