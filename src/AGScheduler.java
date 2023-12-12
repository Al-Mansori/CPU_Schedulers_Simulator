import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class AGScheduler extends Scheduler {
    int quantum;
    int time;
    Queue<Process> died;
    Queue<Process> waiting;


    public AGScheduler(List<Process> processes, int quantum) {
        super(processes);
        this.quantum = quantum;
    }

    public void sort_arrival(List<Process> processes){
        processes.sort(Comparator.comparing(Process::getArrivalTime));

    }
    public double mean_of_quantum(List<Process> processes){
        int sumOfQuantum=0;
        for (Process p : processes){
            sumOfQuantum+=p.quantum;
        }
        return sumOfQuantum/processes.size();
    }
    public Process get_smallest_ag_factor(List<Process> processes , Process currentProcess){
        Process smallestAgFactor = null;
        for (Process p: processes){
            if(currentProcess == null || p.getAGFactor()<currentProcess.getAGFactor()){
                if (smallestAgFactor == null || p.getAGFactor() < smallestAgFactor.getAGFactor()){
                    smallestAgFactor = p;
                }
            }
        }
        return smallestAgFactor;
    }

    @Override
    public void execute() {
        sort_arrival(processes);
        time = 0;
        while (true){
            if(!waiting.isEmpty()){
                //here we should run non-preemptive for the half of quantum time
                //increase time update current process quantum time
                Process currentProcess = waiting.poll();
                int processTime = currentProcess.getQuantum()/2;
                time += processTime;
                int remainingTime = currentProcess.getQuantum() - processTime;
                currentProcess.setQuantum(currentProcess.quantum+=remainingTime);

                //preemptive check if there is process arrived + have smaller ag factor
                while(true){
                   Process newProcess =  get_smallest_ag_factor(processes,currentProcess);
                    if(newProcess != null &&  newProcess.arrivalTime>=time){
                        currentProcess.burstTime -= processTime;//must update current burst and
                        waiting.add(newProcess);
                        break;
                    }
                    else {
                        time += 1; // run second by second
                        processTime++;//update quantum time
                        remainingTime--;
                        if (processTime == quantum) {
                            currentProcess.setQuantum((int) Math.ceil(0.1 * mean_of_quantum(processes)));
                        } else {
                            currentProcess.setQuantum(currentProcess.quantum += remainingTime);
                        }
                        currentProcess.burstTime--;
                        if (currentProcess.burstTime == 0) {
                            currentProcess.setQuantum(0);
                            died.add(currentProcess);

                        }
                        break;
                    }
                }
            }else{
                //here we must get smallest ag factor
                //put him in the waiting queue
                //remove from processes list
                Process newProcess = get_smallest_ag_factor(processes,null);
                if(newProcess!=null){
                    waiting.add(newProcess);
                    processes.remove(newProcess);

                }else{
                    break;
                }
            }
        }
    }
}
