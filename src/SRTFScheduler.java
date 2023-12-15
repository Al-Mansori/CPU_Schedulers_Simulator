import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SRTFScheduler extends Scheduler {

    public SRTFScheduler(List<Process> processes) {
        super(processes);
    }

    public void sortArrival(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process -> Process.getArrivalTime()));
    }

    public Process findShortestProcess(List<Process> processes, int time) {
        Process shortestProcess = null;
        for (Process process : processes) {
            if (process.getArrivalTime() <= time && process.remainingTime > 0) {
                if (shortestProcess == null || process.remainingTime < shortestProcess.remainingTime) {
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


                    totalTurnaroundTime += turnaroundTime;
                    totalWaitingTime += waitingTime;


                }
            } else {
                System.out.print("_ ");
            }

            currentTime++;
        }

        // Print Processes Execution Order

        for (int i = 1 ; i < executionOrder.size();i++) {
            if(executionOrder.get(i)!=executionOrder.get(i-1)){
                result.add(executionOrder.get(i-1));

            }
        }
        result.add(executionOrder.get(executionOrder.size()-1));

        System.out.print("\n\nProcesses completion Order:  ");
        for (Process p:result){
            System.out.print(p.name + " ");

        }
        System.out.println("\n\n");


        for(Process p :result){

            System.out.println(p.name + ": Wait time: " + p.waitingTime);
            System.out.println("    Turnaround time: " + p.turnaroundTime);
            System.out.println();

        }


        // Calculate and print Average Waiting Time
        double avgWaitingTime = (double) totalWaitingTime / processes.size();

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);

        // Calculate and print Average Turnaround Time
        double avgTurnaroundTime = (double) totalTurnaroundTime / processes.size();
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
