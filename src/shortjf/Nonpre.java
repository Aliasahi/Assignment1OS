package shortjf;

import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Nonpre {
	public static void main(String[] args) {
		 // Scanner for user input 
        Scanner scanner = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();
        
        // Collect details for each process
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
        
         // Sort processes based on burst times (shortest job first)
        Collections.sort(processes, Comparator.comparingInt(Process::getBurstTime));
        
        // Display Gantt chart
        System.out.println("\nGantt Chart:");

        int currentTime = 0;
        int totalTurnaroundTime = 0;       
        int totalWaitingTime = 0;
       
       /** // Display the process details in a table format
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|                     Process Information             |");
        System.out.println("+-----------------------------------------------------+");
        System.out.printf("| %-15s | %-15s | %-15s |\n", "Process ID", "Arrival Time", "Burst Time");
        System.out.println("+-----------------------------------------------------+");

        */
        
        for (Process process : processes) {
        	//display process execution
            System.out.print("  P" + process.getProcessId()+ "  ");
            
            //display timelinw
            for (int i = 0; i < process.getBurstTime(); i++ ) {
            	System.out.print("-");
            }
            
            // Calculate and display turnaround time for each process
            int turnaroundTime = currentTime + process.getBurstTime() - process.getArrivalTime();
            System.out.println("  (Turnaround Time: " + turnaroundTime + ")");
            
            //total the turnaround time
            totalTurnaroundTime += turnaroundTime;
            
            //calculare and dsiplay waiting time for each process
            int waitingTime = turnaroundTime - process.getBurstTime();
            System.out.println("    (Waiting Time: " + waitingTime + ")");

            // Accumulate total waiting time
            totalWaitingTime += waitingTime;
            
            currentTime += process.getBurstTime();
        }

        // System.out.println("+-----------------------------------------------------+");
        
        //display total avg tt
        System.out.println("\nTotal Turnaround Time: " + totalTurnaroundTime);
        double averageTurnaroundTime = (double) totalTurnaroundTime / (double) processes.size();
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        
        //dispplay total wt
        System.out.println("Total Waiting Time: " + totalWaitingTime);
        double averageWaitingTime = (double) totalWaitingTime / (double) processes.size();
        System.out.println("Average Waiting Time: " + averageWaitingTime);

        scanner.close();
    }

	static class Process {
	    private int processId;
	    private int arrivalTime;
	    private int burstTime;
	
	    public Process(int processId, int arrivalTime, int burstTime) {
	        this.processId = processId;
	        this.arrivalTime = arrivalTime;
	        this.burstTime = burstTime;
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
		}
	}


