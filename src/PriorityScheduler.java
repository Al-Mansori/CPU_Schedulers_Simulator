import java.util.List;

public class PriorityScheduler extends Scheduler {

    public PriorityScheduler(List<Process> processes) {
        super(processes);
    }

    @Override
    public void execute() {
        int totalProcesses = processes.size();
        int currentTime = 0;
        boolean[] executed = new boolean[totalProcesses];

        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

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

                for (int i = 0; i < totalProcesses; i++) {
                    if (i != highestPriorityIndex && !executed[i]) {
                        processes.get(i).incrementPriority();
                    }
                }

                if (currentProcess.getRemainingTime() == 0) {
                    executed[highestPriorityIndex] = true;
                    currentProcess.setEndTime(currentTime + 1); // Set the end time for the completed process

                    // calculate waiting time and turnaround time for completed process
                    int waitingTime = currentTime + 1 - currentProcess.getArrivalTime() - currentProcess.getBurstTime();
                    int turnaroundTime = currentTime + 1 - currentProcess.getArrivalTime();
                    currentProcess.setWaitingTime(waitingTime);
                    currentProcess.setTurnaroundTime(turnaroundTime);
                }
                currentTime++;
            } else {
                currentTime++;
            }
        }

      
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        // print results
        System.out.println("Processes Execution Order:");
        for (Process p : processes) {
            System.out.println(p.name + ": Wait time: " + p.getWaitingTime());
            System.out.println("    Turnaround time: " + p.getTurnaroundTime());
            System.out.println();
        }

        double avgWaitingTime = (double) totalWaitingTime / totalProcesses;
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;

        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
