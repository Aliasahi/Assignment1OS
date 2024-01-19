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
        List<Process> processes = new ArrayList<>();
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getRemainingBurstTime));
        
        // Detail for each process collected here
        char addAnotherProcess;
        
        do {
        // user enter Pid
        System.out.print("Enter your Process ID: ");
        int processId = scanner.nextInt();
        
        // user enter AT
        System.out.print("Enter your Arrival Time: ");
        int arrivalTime = scanner.nextInt();
        
        // user enter BT
        System.out.print("Enter your Burst Time: ");
        int burstTime = scanner.nextInt();
        
        processes.add(new Process(processId, arrivalTime, burstTime));

        System.out.print("Do you want to add another process? (y/n): ");
        addAnotherProcess = scanner.next().toLowerCase().charAt(0);
        } while (addAnotherProcess == 'y');
        
        // Sort the process by AT
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        

        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Move arrived processes to the ready queue
            while (!processes.isEmpty() && processes.get(0).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            // Select the process with the shortest remaining burst time from the ready queue
            Process currentProcess = readyQueue.poll();

            // Execute the process for 1 time unit
            if (currentProcess != null) {
                System.out.print("P" + currentProcess.getProcessId() + "  ");
                currentProcess.decrementRemainingBurstTime();
                
                // Update turnaround time and waiting time for the executed process
                if (currentProcess.getRemainingBurstTime() == 0) {
                    int turnaroundTime = currentTime - currentProcess.getArrivalTime() + 1;
                    int waitingTime = turnaroundTime - currentProcess.getBurstTime();
                    
                    totalTurnaroundTime += turnaroundTime;
                    totalWaitingTime += waitingTime;
                    
                    System.out.println("(Finishing Time: " + currentTime + ")");
                } else {
                    // If the process is not finished, put it back in the ready queue
                    readyQueue.add(currentProcess);
                } 
            } else {
            	System.out.print("IDLE  ");
            }

            currentTime++;
        }
        
        System.out.println("\nTotal Turnaround Time: " + totalTurnaroundTime);

        double averageTurnaroundTime = (double) totalTurnaroundTime / (double) processes.size();
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);

        // Display total and average waiting time
        System.out.println("Total Waiting Time: " + totalWaitingTime);

        double averageWaitingTime = (double) totalWaitingTime / (double) processes.size();
        System.out.println("Average Waiting Time: " + averageWaitingTime);

        scanner.close();
	}
	
	 static class Process {
	        private int processId;
	        private int arrivalTime;
	        private int burstTime;
	        private int remainingBurstTime;
	        private int waitingTime;

	        public Process(int processId, int arrivalTime, int burstTime) {
	            this.processId = processId;
	            this.arrivalTime = arrivalTime;
	            this.burstTime = burstTime;
	            this.remainingBurstTime = burstTime;
	            this.waitingTime = 0;
	        }

	        public int getProcessId() {
	            return processId;
	        }

	        public int getArrivalTime() {
	            return arrivalTime;
	        }

	        public int getBurstTime() {
	            return burstTime;
	        }

	        public int getRemainingBurstTime() {
	            return remainingBurstTime;
	        }

	        public void decrementRemainingBurstTime() {
	            this.remainingBurstTime = Math.max(0, this.remainingBurstTime - 1);
	        }

	        public void incrementWaitingTime() {
	            this.waitingTime++;
	        }

	        public int getWaitingTime() {
	            return waitingTime;
	        }
	 }
}
