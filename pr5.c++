#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Process {
    int pid;             // Process ID
    int arrival_time;    // Arrival Time
    int burst_time;      // Burst Time
    int start_time;      // Start Time
    int completion_time; // Completion Time
    int waiting_time;    // Waiting Time
    int turnaround_time; // Turnaround Time
};

// Function to sort processes by arrival time
bool compareArrival(const Process &p1, const Process &p2) {
    return p1.arrival_time < p2.arrival_time;
}

void fcfs_with_arrival(vector<Process> &processes) {
    int n = processes.size();
    int current_time = 0;
    double total_waiting_time = 0;
    double total_turnaround_time = 0;

    // Sort processes by arrival time
    sort(processes.begin(), processes.end(), compareArrival);

    // Calculate start time, completion time, waiting time, and turnaround time
    for (int i = 0; i < n; ++i) {
        if (current_time < processes[i].arrival_time) {
            current_time = processes[i].arrival_time;
        }
        processes[i].start_time = current_time;
        processes[i].completion_time = processes[i].start_time + processes[i].burst_time;
        processes[i].turnaround_time = processes[i].completion_time - processes[i].arrival_time;
        processes[i].waiting_time = processes[i].turnaround_time - processes[i].burst_time;

        total_waiting_time += processes[i].waiting_time;
        total_turnaround_time += processes[i].turnaround_time;

        current_time = processes[i].completion_time;
    }

    // Display results
    cout << "PID\tArrival\tBurst\tStart\tCompletion\tWaiting\tTurnaround\n";
    for (const auto &p : processes) {
        cout << p.pid << "\t" << p.arrival_time << "\t" << p.burst_time << "\t"
             << p.start_time << "\t" << p.completion_time << "\t\t"
             << p.waiting_time << "\t" << p.turnaround_time << "\n";
    }
    cout << "Average Waiting Time: " << total_waiting_time / n << endl;
    cout << "Average Turnaround Time: " << total_turnaround_time / n << endl;
}

int main() {
    int n;
    cout << "Enter the number of processes: ";
    if (!(cin >> n)) { // Check if input is valid
        cout << "Invalid input. Please enter an integer for the number of processes.\n";
        return 1; // Exit if input is invalid
    }

    vector<Process> processes(n);
    for (int i = 0; i < n; ++i) {
        processes[i].pid = i + 1;
        cout << "Enter arrival time and burst time for process " << processes[i].pid << ": ";
        if (!(cin >> processes[i].arrival_time >> processes[i].burst_time)) { // Validate input
            cout << "Invalid input. Please enter integers for arrival and burst times.\n";
            return 1;
        }
    }

    fcfs_with_arrival(processes);
    return 0;
}
