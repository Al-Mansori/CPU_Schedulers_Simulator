import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriorityScheduler extends Scheduler {

    public PriorityScheduler(List<Process> processes) {
        super(processes);
    }

    @Override
    public void execute() {
        // Implement Priority scheduling logic
        Collections.sort(processes, Comparator.comparingInt(Process::getPriority)); // Sort processes by priority

        int currentTime = 0;
        int totalProcesses = processes.size();
        boolean[] executed = new boolean[totalProcesses]; // Track executed processes

        while (true) {
            boolean allExecuted = true;
            int highestPriority = Integer.MAX_VALUE;
            int highestPriorityIndex = -1;

            for (int i = 0; i < totalProcesses; i++) {
                if (!executed[i] && processes.get(i).getArrivalTime() <= currentTime) {
                    allExecuted = false;
                    if (processes.get(i).getPriority() < highestPriority) {
                        highestPriority = processes.get(i).getPriority();
                        highestPriorityIndex = i;
                    }
                }
            }

            if (allExecuted) {
                break;
            }

            if (highestPriorityIndex != -1) {
                Process currentProcess = processes.get(highestPriorityIndex);
                System.out.println("Executing: " + currentProcess.name + " at time " + currentTime);
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - 1);

                if (currentProcess.getRemainingTime() == 0) {
                    executed[highestPriorityIndex] = true;
                }
                currentTime++;
            } else {
                currentTime++;
            }
        }
    }
    
    public void printProcesses() {
        int totalProcesses = processes.size();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("Process Execution Order:");
        for (Process process : processes) {
            System.out.print(process.name + " ");
            totalWaitingTime += process.waitingTime();
            totalTurnaroundTime += process.turnaroundTime();
        }
        System.out.println();

        double avgWaitingTime = (double) totalWaitingTime / totalProcesses;
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;

        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
