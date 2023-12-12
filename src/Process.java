import java.util.List;
import java.util.Random;

public class Process {
    String name;
    String color;
    int arrivalTime;
    int burstTime;
    int priority;
    int quantum;
    int remainingTime;
    int AGFactor;

    public Process(String name, String color, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.quantum = 0;
        this.remainingTime = burstTime;
    }
    public int random_function(){
        Random rand = new Random();
        int num = rand.nextInt(20);
        return num + 1 ;
    }
    public void set_ag_factor(){
        int random = random_function();
        if(random < 10){
            setAGFactor(arrivalTime+burstTime+random);
        } else if (random==10) {
            setAGFactor(priority + arrivalTime + burstTime);
        }
        else{
            setAGFactor(10 + arrivalTime + burstTime);
        }

    }
    public int getAGFactor() {
        return AGFactor;
    }

    public void setAGFactor(int AGFactor) {
        this.AGFactor = AGFactor;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }
}