import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get input parameters
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        System.out.print("Enter Round Robin Time Quantum: ");
        int timeQuantum = scanner.nextInt();

        System.out.print("Enter context switching time: ");
        int contextSwitchingTime = scanner.nextInt();

        List<Process> processes = new ArrayList<>();

        // Input each process's details
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter details for process " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.next();

            System.out.print("Color(Graphical Representation): ");
            String color = scanner.next();

            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            System.out.print("Priority Number: ");
            int priority = scanner.nextInt();

            Process process = new Process(name, color, arrivalTime, burstTime, priority);
            processes.add(process);
        }

        // Create instances of schedulers
        SJFScheduler sjfScheduler = new SJFScheduler(new ArrayList<>(processes));
        SRTFScheduler srtfScheduler = new SRTFScheduler(new ArrayList<>(processes));
        PriorityScheduler priorityScheduler = new PriorityScheduler(new ArrayList<>(processes));
        AGScheduler agScheduler = new AGScheduler(new ArrayList<>(processes), timeQuantum);

        // Execute each scheduler
        sjfScheduler.execute();
        srtfScheduler.execute();
        priorityScheduler.execute();
        agScheduler.execute();
//
        // Display output for each scheduler
        // Print Processes execution order, Waiting Time, Turnaround Time, Average
        // Waiting Time, Average Turnaround Time
        // Print all history updates of quantum time for each process (AG Scheduling)
    }
}