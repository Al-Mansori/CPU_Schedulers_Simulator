

# CPU Scheduler Simulator

This Java application simulates various CPU scheduling algorithms including Non-Preemptive Shortest-Job First (SJF), Shortest-Remaining-Time First (SRTF), Non-preemptive Priority Scheduling, and AG Scheduling. The program allows users to input process details, execute different scheduling algorithms, and display the scheduling results.

## File Structure

The project is organized into several files and classes as follows:

- **src**
  - **Process.java**: Represents the Process class holding process details.
  - **Scheduler.java**: Abstract class defining the basic structure for different schedulers.
  - **SJFScheduler.java**: Class implementing the SJF scheduling algorithm.
  - **SRTFScheduler.java**: Class implementing the SRTF scheduling algorithm.
  - **PriorityScheduler.java**: Class implementing the Priority scheduling algorithm.
  - **AGScheduler.java**: Class implementing the AG scheduling algorithm.
  - **Main.java**: Main class with the application entry point and GUI components.

## Usage

1. Compile the project using a Java compiler (`javac *.java`).
2. Run the Main.java file to start the application.
3. The GUI interface prompts the user to input the number of processes.
4. Enter the number of processes and click the "Submit" button.
5. Enter the details for each process through the GUI input fields.
6. After providing process details, the application allows executing different scheduling algorithms.
7. Results and scheduling details are displayed in the GUI or console output.

## Features

- Modularized structure with separate classes for each scheduling algorithm.
- GUI interface using Java Swing for user interaction.
- Capable of simulating various CPU scheduling algorithms.
- Basic functionalities to input process details, execute schedulers, and display results.

## Requirements

- Java Development Kit (JDK) installed on your system.
- IDE or command-line tools to compile and run Java programs.
- Basic understanding of CPU scheduling algorithms.

## Notes

- The application currently provides a basic GUI for inputting process details and executing schedulers. Further improvements can be made to enhance user experience and add more functionalities.
- Additional error handling and validation can be implemented for user inputs and scheduling logic.

