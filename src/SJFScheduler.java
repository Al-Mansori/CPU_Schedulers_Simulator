import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJFScheduler extends Scheduler {
    int contextSwitching;
    public SJFScheduler(List<Process> processes, int contextSwitching) {
        super(processes);
        this.contextSwitching = contextSwitching;
    }

    @Override
    public void execute() {
        int totalProcesses = processes.size();
        int completedProcesses = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;


        int currentTime = 0;
        while (completedProcesses < totalProcesses) {
            ArrayList<Process> arrivedProcesses = filterArrived(currentTime);
            if(arrivedProcesses.isEmpty()) {
                currentTime++;
                continue;
            }

            Collections.sort(arrivedProcesses, Comparator.comparingInt(Process::getBurstTime));
            Process currentProcess = arrivedProcesses.get(0);
            System.out.println("Executing: " + currentProcess.name + " at time " + currentTime);
            currentProcess.setStartTime(currentTime);
            currentTime += currentProcess.getBurstTime();
            currentProcess.setEndTime(currentTime);
            currentTime += contextSwitching;
            totalWaitingTime += currentProcess.waitingTime();
            totalTurnaroundTime += currentProcess.turnaroundTime();
            completedProcesses++;

            // Print details for each process
            System.out.println("Process: " + currentProcess.name);
            System.out.println("Waiting Time: " + currentProcess.waitingTime());
            System.out.println("Turnaround Time: " + currentProcess.turnaroundTime());
            System.out.println();


        }


        // Calculate averages
        double averageWaitingTime = (double) totalWaitingTime / totalProcesses;
        double averageTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;
        // Print averages
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);


    }

    public ArrayList<Process> filterArrived(int timestamp) {
        ArrayList<Process> arrivedProcesses = new ArrayList<>();
        for (Process process: processes) {
            if(process.getArrivalTime() <= timestamp){
                arrivedProcesses.add(process);
            }
        }
        return arrivedProcesses;
    }
}

