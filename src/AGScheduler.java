import java.util.List;

public class AGScheduler extends Scheduler {
    int quantum;

    public AGScheduler(List<Process> processes, int quantum) {
        super(processes);
        this.quantum = quantum;
    }

    @Override
    public void execute() {
        // Implement AG scheduling logic
    }
}
