import java.util.List;
import java.util.Random;

public class Process {
    String name;
    String color;
    int random;

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    int arrivalTime;

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }
    public int getWaitingTime(int currentTime) {
        return Math.max(0, currentTime - arrivalTime);
    }
    public void updateWaitingTime(int elapsedTime) {
        waitingTime += elapsedTime;
    }

    boolean isDone;

    int burstTime;
    int priority;
    int quantum;
    int remainingTime;
    int AGFactor;
    int endTime;

    int waitingTime;

    int turnaroundTime;

    int initialBurstTime;

    private int startTime;

    public Process(String name, String color, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.quantum = 0;
        this.remainingTime = burstTime;
        setRandom();
        set_ag_factor();

    }

    //
    public int getPriority() {
        return priority;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    

    public int turnaroundTime() {
        return startTime + burstTime - arrivalTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getInitialBurstTime() {
        return initialBurstTime;
    }

    public void setInitialBurstTime(int initialBurstTime) {
        this.initialBurstTime = initialBurstTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int random_function() {
        Random rand = new Random();
        int num = rand.nextInt(20);
        return num + 1;
    }

    public void set_ag_factor() {
        if (random < 10) {
            setAGFactor(arrivalTime + burstTime + random);
        } else if (random == 10) {
            setAGFactor(priority + arrivalTime + burstTime);
        } else {
            setAGFactor(10 + arrivalTime + burstTime);
        }
    }

    public int getAGFactor() {
        return AGFactor;
    }

    public void setAGFactor(int AGFactor) {
        this.AGFactor = AGFactor;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom() {
        this.random = random_function();
    }

    public void incrementPriority() {

        this.priority += 1;
    }

   
    public int getTurnaroundTime(int currentTime) {
        return turnaroundTime;
    }


    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public boolean getIsDone(){
        return this.isDone;
    }

    public void setIsDone(){
        this.isDone = true;
    }


}