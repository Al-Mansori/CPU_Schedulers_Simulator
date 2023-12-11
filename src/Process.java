import java.util.List;

public class Process {
    String name;
    String color;
    int arrivalTime;
    int burstTime;
    int priority;
    int quantum;
    int remainingTime;

    public Process(String name, String color, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.quantum = 0;
        this.remainingTime = burstTime;
    }

}