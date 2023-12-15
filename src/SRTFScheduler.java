import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SRTFScheduler extends Scheduler {
    private static final int AGING_FACTOR = 1;

    public SRTFScheduler(List<Process> processes) {
        super(processes);
    }

    public void sortArrival(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process -> Process.getArrivalTime()));
    }

    public ArrayList<Process> removeDuplicates(List<Process> list)
    {

        // Create a new ArrayList
        ArrayList<Process> newList = new ArrayList<>();


        for (Process element : list) {

            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        return newList;
    }

    public Process findShortestProcess(List<Process> processes, int time) {
        Process shortestProcess = null;
        for (Process process : processes) {
            if (process.getArrivalTime() <= time && process.remainingTime > 0) {
                int priority = process.remainingTime + AGING_FACTOR * process.getWaitingTime(time);

                if (shortestProcess == null || priority < shortestProcess.priority) {
                    shortestProcess = process;
                }
            }
        }
        return shortestProcess;
    }

    @Override
    public void execute() {
        int currentTime = 0, completedProcesses = 0;
        int totalWaitingTime = 0, totalTurnaroundTime = 0;
        List<Process> executionOrder = new ArrayList<>();
        List<Process> result = new ArrayList<>();

        sortArrival(processes);

        while (completedProcesses < processes.size()) {
            // Update waiting time for all processes
            for (Process process : processes) {
                if (process.getArrivalTime() <= currentTime && process.remainingTime > 0) {
                    process.updateWaitingTime(1);
                }
            }

            Process shortestProcess = findShortestProcess(processes, currentTime);

            if (shortestProcess != null) {
                executionOrder.add(shortestProcess);
                shortestProcess.remainingTime--;

                if (shortestProcess.remainingTime == 0) {
                    completedProcesses++;

                    int turnaroundTime = currentTime - shortestProcess.getArrivalTime() + 1;
                    int waitingTime = turnaroundTime - shortestProcess.burstTime;

                    shortestProcess.waitingTime = waitingTime;
                    shortestProcess.turnaroundTime = turnaroundTime;

                    totalTurnaroundTime += currentTime - shortestProcess.getArrivalTime() + 1;
                    totalWaitingTime += waitingTime;
                }
            } else {
                System.out.print("_ ");
            }

            currentTime++;
        }


        for (int i = 1; i < executionOrder.size(); i++) {
            if (executionOrder.get(i) != executionOrder.get(i - 1)) {
                result.add(executionOrder.get(i - 1));

            }
        }
        result.add(executionOrder.get(executionOrder.size() - 1));

        System.out.print("\n\nProcesses completion Order:  ");
        for (Process p : result) {
            System.out.print(p.name + " ");
        }
        System.out.println("\n\n");

        result = removeDuplicates(result);

        for (Process p : result) {

            System.out.println(p.name + ": Wait time: " + p.waitingTime);
            System.out.println("    Turnaround time: " + p.turnaroundTime);
            System.out.println();

        }



        double avgWaitingTime = (double) totalWaitingTime / processes.size();

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);


        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
