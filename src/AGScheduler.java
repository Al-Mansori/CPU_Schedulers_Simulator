import java.util.*;

public class AGScheduler extends Scheduler {
    int quantum;
    int startTime;

    int currentTime;
    Queue<Process> readyQueue;
    List<String> quantumHistory;
    List<Process> deadList;
    int processTime;


    Map<Process, Integer> turnAroundTimeMap = new HashMap<>();
    Map<Process, Integer> waitingTimeMap = new HashMap<>();

    private void setAllQuantum(List<Process> processes){
        for (Process p: processes){
            p.setQuantum(quantum);
            p.setInitialBurstTime(p.getBurstTime());
        }
    }
    public double meanOfQuantum(List<Process> processes, int currentTime) {
        int sumOfQuantum = 0;
        int size = 0;
        for (Process p : processes) {
            if (p.arrivalTime >= currentTime && p.burstTime > 0) {
                sumOfQuantum += p.quantum;
                size++;

            }
        }
        return size == 0 ? 0 : (double)sumOfQuantum / size;
    }


    public AGScheduler(List<Process> processes, int quantum) {
        super(processes);
        this.quantum = quantum;
        this.readyQueue = new LinkedList<>();
        this.deadList = new LinkedList<>();
        this.quantumHistory = new LinkedList<>();
        currentTime = 0;
        startTime=0;
        setAllQuantum(processes);
    }


    @Override
    public void execute()
    {
        while(!processes.isEmpty())
        {
            int MaxAG=500;
            int index = -1;
            for (int i =0; i< processes.size();i++){
                if(processes.get(i).arrivalTime <= currentTime && processes.get(i).getAGFactor()<MaxAG && !isInReadyQueue(processes.get(i))){
                    index = i;
                    MaxAG = processes.get(i).getAGFactor();
                }
            }

            if(index == -1)
            {
                if(!readyQueue.isEmpty())
                {
                    executeNonPreemptive(readyQueue.poll());

                }

            }
            else {
                executeNonPreemptive(processes.get(index));
            }



        }
        // Print final results
        printTurnaroundAndWaitingTimes();
        printQuantumHistory();


    }


    private boolean isInReadyQueue(Process currentProcess){
        boolean found = false;
        for (int i = 0 ; i<readyQueue.size();i++){
            Process p = readyQueue.poll();
            if ( p == currentProcess){
                found = true;
            }
            readyQueue.add(p);
        }
        return found;
    }
    private void executePreemptive(Process currentProcess) {
        Process newProcess = checkForNewProcesses(currentProcess);
        // second by second
        while (newProcess == null && currentProcess.getBurstTime() > 0 && !checkForQuantumFinish(currentProcess, processTime)) {

            currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
            processTime++; // 3
//            printOutput(currentProcess, startTime, processTime);
            currentTime++;

            newProcess = checkForNewProcesses(currentProcess);

        }



        if (checkForExecutionFinished(currentProcess)) {

            deadList.add(currentProcess);
            currentProcess.setQuantum(0);
            processTime = 0;
            updateTurnaroundAndWaitingTimes(currentProcess);
            processes.remove(currentProcess);
            currentProcess.setEndTime(currentTime); // 0->2 p1 2->3 p1 3 ->5 p2
            printOutput(currentProcess,startTime,currentProcess.endTime);

        }

        // Check for updating quantum time
        else if (checkForQuantumFinish(currentProcess, processTime)) {

            currentProcess.setQuantum(currentProcess.getQuantum() + ((int) Math.ceil(0.1 * meanOfQuantum(processes, currentTime))));
            processTime = 0;
            currentProcess.setEndTime(currentTime); // 0->2 p1 2->3 p1 3 ->5 p2
            readyQueue.add(currentProcess);
            printOutput(currentProcess,startTime,currentProcess.endTime);

        }

        else if (newProcess != null) {
            currentProcess.setQuantum(currentProcess.getQuantum() + (currentProcess.getQuantum() - processTime));
            readyQueue.add(currentProcess);
            currentProcess.setEndTime(currentTime);
            processTime = 0;
            printOutput(currentProcess,startTime,currentProcess.endTime);

            executeNonPreemptive(newProcess);

        }



    }
    private void printQuantumHistory() {
        System.out.println("===================");
        System.out.println("Quantum History:");
        for (String entry : quantumHistory) {
            System.out.println(entry);
        }
    }

    private void executeNonPreemptive(Process currentProcess) {
//        System.out.println("Current process: " + currentProcess.name);
//        System.out.println("start time: " + currentTime);



        int halfQuantum = Math.min((int) Math.ceil(currentProcess.getQuantum() / 2.0), currentProcess.getBurstTime());
        startTime = currentTime;
        currentTime += halfQuantum;
//        System.out.println("Current process: " + currentProcess.name);
//        System.out.println("end time: " + currentTime);
        processTime = halfQuantum;//2

        currentProcess.setBurstTime(currentProcess.getBurstTime() - halfQuantum);

        if (checkForExecutionFinished(currentProcess)) {
            processes.remove(currentProcess);

            deadList.add(currentProcess);
            currentProcess.setQuantum(0);
            updateTurnaroundAndWaitingTimes(currentProcess);
            currentProcess.setEndTime(currentTime); // 0->2 p1 2->3 p1 3 ->5 p2
            printOutput(currentProcess,startTime,currentProcess.endTime);

        }

        else {
            //System.out.println("continue");
            executePreemptive(currentProcess);

        }

    }


    private boolean checkForQuantumFinish(Process currentProcess,int processTime){
        return processTime == currentProcess.quantum;
    }

    private void removeFromQueue(Process p)
    {
        if(!readyQueue.isEmpty())
        {
            System.out.println("first process in queue name: " + readyQueue.element().name);

        }
        int size = readyQueue.size();
        for(int i = 0; i < size; i++)
        {
            Process removed = readyQueue.remove();
            if(removed != p)
            {
                readyQueue.add(removed);
            }

        }
        if(!readyQueue.isEmpty())
        {
            System.out.println("first process in queue name: " + readyQueue.element().name);

        }

    }


    //remaining process wil be put in ready queue but must be arrived
    private Process checkForNewProcesses(Process currentProcess) {
        int MaxAG=currentProcess.getAGFactor();
        int index = -1;
        for (int i =0; i< processes.size();i++){
            if(processes.get(i).arrivalTime <= currentTime && processes.get(i).getAGFactor()<MaxAG ){
                index = i;
                MaxAG = processes.get(i).getAGFactor();
            }

        }
        if(index == -1)
        {
            return null;
        }
        else {
            removeFromQueue(processes.get(index));
            return processes.get(index);

        }


    }



    private boolean checkForExecutionFinished(Process currentProcess) {
        //check if the burst time is = 0
        return currentProcess.burstTime == 0;
    }

    private void updateTurnaroundAndWaitingTimes(Process currentProcess) {
        int turnaroundTime = currentTime - currentProcess.arrivalTime;
        int waitingTime = turnaroundTime - currentProcess.initialBurstTime;

        turnAroundTimeMap.put(currentProcess, turnaroundTime);
        waitingTimeMap.put(currentProcess, waitingTime);
    }

    private void printOutput(Process currentProcess, int startTime, int endTime) {
        System.out.println("Process name ==> " + currentProcess.name);
        System.out.println("Process start time ==> " + startTime);
        System.out.println("Process end time ==> " + endTime);
        System.out.println("Process quantum ==> " + currentProcess.getQuantum());

        System.out.println("===================");

        quantumHistory.add("Process name ==> " + currentProcess.name);
        quantumHistory.add("QT ==>" + currentProcess.getQuantum());
    }

    private void printTurnaroundAndWaitingTimes() {
        System.out.println("===================");
        for (Map.Entry<Process, Integer> entry : turnAroundTimeMap.entrySet()) {
            Process process = entry.getKey();
            int turnaroundTime = entry.getValue();
            int waitingTime = turnaroundTime - process.getInitialBurstTime();

            System.out.println("process name ==> " + process.name);
            System.out.println("process Turnaround time ==> " + turnaroundTime);
            System.out.println("process Waiting time ==> " + waitingTime);
            System.out.println("===================");
        }

        double avgWaitingTime = waitingTimeMap.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

        double avgTurnaroundTime = turnAroundTimeMap.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

        System.out.println("average waiting time ==> " + avgWaitingTime);
        System.out.println("average turn around ==> " + avgTurnaroundTime);
        System.out.println("===================");
    }

    public void printTable() {
        System.out.println("process\tBurst Time\tArrival Time\tPriority\tQuantum\t\tRandom Function\t AG Factor");
        for (Process p : processes) {
            System.out.println(p.name + "\t\t" + p.burstTime + "\t\t\t" + p.arrivalTime + "\t\t\t\t" + p.priority + "\t\t\t" + quantum + "\t\t\t" + p.getRandom() + "\t\t\t\t" + p.getAGFactor());
        }
    }
}
