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
                //here we should run non preemptive for the half of quantum time
                //increase time update current process quantum time

                //preemptive check if there is process arrived + have smaller ag factor


            }else{
                Process newProcess = get_smallest_ag_factor(processes,null);
                if(newProcess!=null){
                    waiting.add(newProcess);
                    processes.remove(newProcess);
                }else{
                    break;
                }
                //here we must get smallest ag factor
                //put him in the waiting queue
                //remove from processes list

            }
        }
    }
}
