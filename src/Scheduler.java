import java.util.List;

public abstract class Scheduler {
    List<Process> processes;

    public Scheduler(List<Process> processes) {
        this.processes = processes;
    }

    public abstract void execute();

   
}
