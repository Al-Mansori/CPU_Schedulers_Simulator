import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numProcesses = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter the number of processes: ");
                numProcesses = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }

        int timeQuantum = 0;
        validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter Round Robin Time Quantum: ");
                timeQuantum = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }

        int contextSwitchingTime = 0;
        validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter context switching time: ");
                contextSwitchingTime = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }

        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter details for process " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Color(Graphical Representation): ");
            String color = scanner.nextLine();

            int arrivalTime = 0;
            validInput = false;

            while (!validInput) {
                try {
                    System.out.print("Arrival Time: ");
                    arrivalTime = Integer.parseInt(scanner.nextLine());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid integer.");
                }
            }

            int burstTime = 0;
            validInput = false;

            while (!validInput) {
                try {
                    System.out.print("Burst Time: ");
                    burstTime = Integer.parseInt(scanner.nextLine());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid integer.");
                }
            }

            int priority = 0;
            validInput = false;

            while (!validInput) {
                try {
                    System.out.print("Priority Number: ");
                    priority = Integer.parseInt(scanner.nextLine());
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid integer.");
                }
            }

            Process process = new Process(name, color, arrivalTime, burstTime, priority);
            processes.add(process);
        }

        // Create instances of schedulers
        SJFScheduler sjfScheduler = new SJFScheduler(new ArrayList<>(processes),contextSwitchingTime);
        SRTFScheduler srtfScheduler = new SRTFScheduler(new ArrayList<>(processes));
        PriorityScheduler priorityScheduler = new PriorityScheduler(new ArrayList<>(processes));
        AGScheduler agScheduler = new AGScheduler(new ArrayList<>(processes), timeQuantum);

        // Execute each scheduler
        sjfScheduler.execute();
        srtfScheduler.execute();
        priorityScheduler.execute();
        agScheduler.printTable();
        agScheduler.execute();

        // Display output for each scheduler
        // Print Processes execution order, Waiting Time, Turnaround Time, Average
        // Waiting Time, Average Turnaround Time
        // Print all history updates of quantum time for each process (AG Scheduling)
    }
}
