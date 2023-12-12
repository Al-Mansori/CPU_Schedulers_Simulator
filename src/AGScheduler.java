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
                Process currentProcess = waiting.poll();
                int halfTime = currentProcess.getQuantum()/2;
                time += halfTime;
                int remainingTime = currentProcess.getQuantum() - halfTime;
                currentProcess.setQuantum(currentProcess.quantum+=remainingTime);

                //preemptive check if there is process arrived + have smaller ag factor
                while(true){
                   Process newProcess =  get_smallest_ag_factor(processes,currentProcess);
                    if(newProcess != null && newProcess.arrivalTime>=time){
                        //must update current burst and
                        break;
                    }
                    else{
                        time+=1; // run second by second
                        //update quantum time



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
