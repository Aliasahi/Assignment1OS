package RoundRobin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class roundR {
    public static void main(String[] args) {
        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        List<Process> processes = noOfProcess(scanner);
        int timeQuantum = getTimeQuantum(scanner);

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getAT));
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int completedProcesses = 0;

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Add processes to the ready queue based on arrival time
            while (!processes.isEmpty() && processes.get(0).getAT() <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            // Execute processes in the ready queue
            Process currentProcess = readyQueue.poll();

            if (currentProcess != null) {
                int remainingBurstTime = currentProcess.leftBT();
                int executionTime = Math.min(timeQuantum, remainingBurstTime);
                currentProcess.reducedBT();

                System.out.print("P" + currentProcess.getProcessId() + "  ");

                currentTime += executionTime;

                if (currentProcess.leftBT() == 0) {
                    int turnaroundTime = currentTime - currentProcess.getAT();
                    int waitingTime = turnaroundTime - currentProcess.getbt();

                    totalTurnaroundTime += turnaroundTime;
                    totalWaitingTime += waitingTime;
                    completedProcesses++;

                    System.out.println("(Finishing Time: " + currentTime + ")");
                } else {
                    readyQueue.add(currentProcess);
                }
            } else {
                // No process to execute, go to the next time unit
                System.out.print("IDLE  ");
                currentTime++;
            }
        }

        System.out.println("\nTotal Turnaround Time: " + totalTurnaroundTime);
        System.out.println("Total Waiting Time: " + totalWaitingTime);

        double averageTurnaroundTime = (completedProcesses > 0) ? (double) totalTurnaroundTime / completedProcesses : 0;
        System.out.println("Average Turnaround Time: " + String.format("%.4f", averageTurnaroundTime));

        double averageWaitingTime = (completedProcesses > 0) ? (double) totalWaitingTime / completedProcesses : 0;
        System.out.println("Average Waiting Time: " + String.format("%.4f", averageWaitingTime));

        scanner.close();
    }

    private static List<Process> noOfProcess(Scanner scanner) {
        List<Process> processes = new ArrayList<>();
        char addP;

        do {
            System.out.print("Enter your Process ID: ");
            int processId = scanner.nextInt();

            System.out.print("Enter your Arrival Time: ");
            int at = scanner.nextInt();

            System.out.print("Enter your Burst Time: ");
            int bt = scanner.nextInt();

            processes.add(new Process(processId, at, bt));

            System.out.print("Do you want to add another process? (y/n): ");
            addP = scanner.next().toLowerCase().charAt(0);
        } while (addP == 'y');

        return processes;
    }

    private static int getTimeQuantum(Scanner scanner) {
        System.out.print("Enter time quantum: ");
        return scanner.nextInt();
    }

    static class Process {
        private int processId;
        private int at;
        private int bt;
        private int remainingbt;

        public Process(int processId, int at, int bt) {
            this.processId = processId;
            this.at = at;
            this.bt = bt;
            this.remainingbt = bt;
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
    }
}
