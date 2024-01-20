package shortjf;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;
import java.util.PriorityQueue;

//import shortjf.Nonpre.Process;

public class preemp {
	public static void main(String[] args) {
		 // Scanner for user input 
        Scanner scanner = new Scanner(System.in);
        List<Process> processes = noOfProcess(scanner);
        PriorityQueue<Process> inQ = new PriorityQueue<>(Comparator.comparingInt(Process::leftBT));

        int processCount = processes.size();
        
        // Sort the process by AT
        processes.sort(Comparator.comparingInt(Process::getAT));

        int executionTime = 0;
        int totalTAT = 0;
        int totalWT = 0;
        int processFinish = 0;

        while (!processes.isEmpty() || !inQ.isEmpty()) {
            // process to execute go to the ready queue
            while (!processes.isEmpty() && processes.get(0).getAT() <= executionTime) {
                inQ.add(processes.remove(0));
            }

            // to select the shortest bt
            Process currentProcess = inQ.poll();

            // Execute the process
            if (currentProcess != null) {
                System.out.print("P" + currentProcess.getProcessId() + "  ");
                currentProcess.reducedBT();

                // Update tat and wt for executed process
                if (currentProcess.leftBT() == 0) {
                    int tat = executionTime - currentProcess.getAT() + 1;
                    int wt = tat - currentProcess.getbt();

                    totalTAT += tat;
                    totalWT += wt;

                    System.out.println("(Finishing Time: " + executionTime + ")");
                    processFinish++;
                } else {
                    // If the process not done move to ready queue
                    inQ.add(currentProcess);
                } 
            }

            executionTime++;
        }

        System.out.println("\nTotal Turnaround Time: " + totalTAT);
        
        //total and avg tat
        double avgTAT = (processFinish > 0) ? (double) totalTAT / processFinish : 0;
        System.out.println("Average Turnaround Time: " + String.format("%.4f", avgTAT));

        // total and avg wt
        System.out.println("Total Waiting Time: " + totalWT);

        double avgWT = (processFinish > 0) ? (double) totalWT / processFinish : 0;
        System.out.println("Average Waiting Time: " + String.format("%.4f", avgWT));
        scanner.close();
    }

    private static List<Process> noOfProcess(Scanner scanner) {
        List<Process> processes = new ArrayList<>();
        char addP;

        do {
            System.out.print("Process ID: ");
            int processId = scanner.nextInt();

            System.out.print("Arrival Time: ");
            int at = scanner.nextInt();

            System.out.print("Burst Time: ");
            int bt = scanner.nextInt();

            processes.add(new Process(processId, at, bt));

            System.out.print("Add process? (y/n): ");
            addP = scanner.next().toLowerCase().charAt(0);
        } while (addP == 'y');

        return processes;
    }
	
	 static class Process {
	        private int processId;
	        private int at;
	        private int bt;
	        private int remainingbt;
	        private int wt;

	        public Process(int processId, int at, int bt) {
	            this.processId = processId;
	            this.at = at;
	            this.bt = bt;
	            this.remainingbt = bt;
	            this.wt = 0;
	        }

	        public int getProcessId() {
	            return processId;
	        }

	        public int getAT() {
	            return at;
	        }

	        public int getbt() {
	            return bt;
	        }

	        public int leftBT() {
	            return remainingbt;
	        }

	        public void reducedBT() {
	            this.remainingbt = Math.max(0, this.remainingbt - 1);
	        }
	        
	        //for incrasing the wt in the queue in purpose when avg count
	        public void incrementwt() {
	            this.wt++;
	        }

	        public int getWT() {
	            return wt;
	        }
	 }
}
