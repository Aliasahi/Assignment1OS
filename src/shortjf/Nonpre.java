package shortjf;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Nonpre {
	public static void main(String[] args) {
		 // Scanner for user input 
        Scanner scanner = new Scanner(System.in);
        List<Process> processes = new ArrayList<>();
        
        // user input No of Process
        System.out.print("Enter the number of processes: ");
        int numberOfProcesses = scanner.nextInt();
        
        // Collect details for each process
        for (int i = 1; i <= numberOfProcesses; i++) {
            System.out.println("\nProcess #" + i);
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
        
    }

        // Display the process details in a table format
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("|                     Process Information             |");
        System.out.println("+-----------------------------------------------------+");
        System.out.printf("| %-15s | %-15s | %-15s |\n", "Process ID", "Arrival Time", "Burst Time");
        System.out.println("+-----------------------------------------------------+");


        for (Process process : processes) {
            System.out.printf("| %-15d | %-15d | %-15d |\n", process.getProcessId(), process.getArrivalTime(), process.getBurstTime());
        }

        System.out.println("+-----------------------------------------------------+");

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


