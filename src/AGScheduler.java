import java.util.*;

public class AGScheduler extends Scheduler {
    int quantum;
    int time;
    Queue<Process> completedProcesses;
    Queue<Process> readyQueue;

    List<String> quantumHistory;

    Map<Process, Integer> turnAroundTimeMap = new HashMap<>();
    Map<Process, Integer> waitingTimeMap = new HashMap<>();

    public AGScheduler(List<Process> processes, int quantum) {
        super(processes);
        this.quantum = quantum;
        this.completedProcesses = new LinkedList<>();
        this.readyQueue = new LinkedList<>();
        this.quantumHistory = new LinkedList<>();
    }

    public void sortArrival(List<Process> processes) {
        processes.sort(Comparator.comparing(Process::getArrivalTime));
    }

    public double meanOfQuantum(List<Process> processes, int currentTime) {
        int sumOfQuantum = 0;
        int count = 0;
        for (Process p : processes) {
            if (p.arrivalTime <= currentTime && p.burstTime > 0) {
                sumOfQuantum += p.quantum;
                count++;
            }
        }
        return count == 0 ? 0 : sumOfQuantum / count;
    }

    public Process getSmallestAgFactor(List<Process> processes, Process currentProcess) {
        Process smallestAgFactor = null;
        for (Process p : processes) {
            if (currentProcess == null || p.getAGFactor() < currentProcess.getAGFactor()) {
                if (smallestAgFactor == null || p.getAGFactor() < smallestAgFactor.getAGFactor()) {
                    smallestAgFactor = p;
                }
            }
        }
        return smallestAgFactor;
    }

    @Override
    public void execute() {
        sortArrival(processes);
        time = 0;
        Process currentProcess = null;

        int currentQuantum = quantum; // Initialize current quantum

        while (!processes.isEmpty() || currentProcess != null || !readyQueue.isEmpty()) {
            if (currentProcess == null) {
                currentProcess = getSmallestAgFactor(processes, null);
                if (currentProcess != null) {
                    readyQueue.add(currentProcess);
                    processes.remove(currentProcess);
                } else {
                    break;
                }
            }

            // Non-preemptive
            int processTime = (int) Math.min(Math.ceil(currentQuantum / 2.0), currentProcess.burstTime);
            time += processTime;
            currentProcess.burstTime -= processTime;
            quantumHistory.add("Quantum time: " + currentQuantum + ", Process number: " + currentProcess.name
                    + ", Process quantum: " + currentProcess.getQuantum());

            if (currentProcess.burstTime == 0) {
                currentProcess.setQuantum(0);
                completedProcesses.add(currentProcess);
                turnAroundTimeMap.put(currentProcess, time - currentProcess.arrivalTime);
                currentProcess = null;
                currentQuantum = quantum; // Reset quantum for the next process
                continue;
            }
            currentProcess.setQuantum(currentProcess.quantum += processTime);

            // Preemptive
            while (true) {
                Process newProcess = getSmallestAgFactor(processes, currentProcess);
                if (newProcess != null && newProcess.arrivalTime >= time && newProcess.getAGFactor() < currentProcess.getAGFactor()) {
                    readyQueue.add(currentProcess);
                    currentProcess = newProcess;
                    processTime = 0;

                    processes.remove(newProcess);
                    break;
                } else {
                    time += 1;
                    processTime++;
                    currentProcess.setQuantum(currentProcess.getQuantum() + 1);
                    currentProcess.burstTime--;

                    if (processTime == currentQuantum && currentProcess.burstTime > 0) {
                        currentQuantum = (int) Math.ceil(0.1 * meanOfQuantum(processes, time));
                        quantumHistory.add("Quantum time updated to: " + currentQuantum + ", Process number: "
                                + currentProcess.name + ", Process quantum: " + currentProcess.getQuantum());
                        readyQueue.add(currentProcess);
                    }

                    quantumHistory.add("Quantum time: " + currentQuantum + ", Process number: " + currentProcess.name
                            + ", Process quantum: " + currentProcess.getQuantum());

                    if (currentProcess.burstTime == 0) {
                        currentProcess.setQuantum(0);
                        completedProcesses.add(currentProcess);
                        int turnAround = time - currentProcess.arrivalTime;
                        turnAroundTimeMap.put(currentProcess, turnAround);
                        int waitingTime = turnAround - currentProcess.burstTime;
                        waitingTimeMap.put(currentProcess, waitingTime);
                        currentProcess = null;
                        currentQuantum = quantum; // Reset quantum for the next process
                        break;
                    }
                }
            }
        }

        while (!processes.isEmpty()) {
            completedProcesses.add(processes.remove(0));
        }
    }

    public void printProcesses() {
        for (String s : quantumHistory) {
            System.out.println(s);
        }

        for (Map.Entry<Process, Integer> entry : turnAroundTimeMap.entrySet()) {
            Process process = entry.getKey();
            int turnaroundTime = entry.getValue();
            System.out.println("Process: " + process.name + ", Turnaround Time: " + turnaroundTime);
        }

        for (Map.Entry<Process, Integer> entry : waitingTimeMap.entrySet()) {
            Process process = entry.getKey();
            int waitingTime = entry.getValue();
            System.out.println("Process: " + process.name + ", Waiting Time: " + waitingTime);
        }
    }
}
