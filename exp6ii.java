public class exp6ii {

    static class Process {
        int pid; // Process ID
        int arrivalTime; // Arrival time of process
        int burstTime; // Burst time of process
        int remainingTime; // Remaining time of process
        int completionTime; // Completion time of process
        int waitingTime; // Waiting time of process
        int turnaroundTime; // Turnaround time of process
        boolean isComplete = false; // Completion status of process

        public Process(int pid, int arrivalTime, int burstTime) {
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    public static void main(String[] args) {
        // Define 5 hardcoded processes
        Process[] processes = {
            new Process(1, 0, 7),
            new Process(2, 2, 4),
            new Process(3, 4, 1),
            new Process(4, 5, 4),
            new Process(5, 6, 3)
        };

        int time = 0; // Current time
        int completed = 0; // Number of processes completed
        int n = processes.length; // Number of processes

        while (completed != n) {
            int shortestIndex = -1;
            int minRemainingTime = Integer.MAX_VALUE;

            // Find process with shortest remaining time at current time
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= time && !processes[i].isComplete &&
                    processes[i].remainingTime < minRemainingTime) {
                    minRemainingTime = processes[i].remainingTime;
                    shortestIndex = i;
                }
            }

            if (shortestIndex == -1) {
                // If no process is found, increment time
                time++;
                continue;
            }

            // Process found with shortest remaining time
            Process currentProcess = processes[shortestIndex];
            currentProcess.remainingTime--;

            // If the process is completed
            if (currentProcess.remainingTime == 0) {
                currentProcess.isComplete = true;
                completed++;
                currentProcess.completionTime = time + 1;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
            }

            // Increment time
            time++;
        }

        // Display the results
        System.out.println("PID\tArrival\tBurst\tCompletion\tWaiting\tTurnaround");
        for (Process p : processes) {
            System.out.println(p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                               p.completionTime + "\t\t" + p.waitingTime + "\t" + p.turnaroundTime);
        }

        // Calculate and display average waiting time and turnaround time
        double avgWaitingTime = 0, avgTurnaroundTime = 0;
        for (Process p : processes) {
            avgWaitingTime += p.waitingTime;
            avgTurnaroundTime += p.turnaroundTime;
        }

        System.out.println("\nAverage Waiting Time: " + (avgWaitingTime / n));
        System.out.println("Average Turnaround Time: " + (avgTurnaroundTime / n));
    }
}
