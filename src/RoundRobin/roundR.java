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
        int timeQuantum = getTQ(scanner);

        PriorityQueue<Process> inQ = new PriorityQueue<>(Comparator.comparingInt(Process::getAT));
        int currentTime = 0;
        int totalTAT = 0;
        int totalWT = 0;
        int processFinish = 0;

        while (!processes.isEmpty() || !inQ.isEmpty()) {
            //to add process by the at
            while (!processes.isEmpty() && processes.get(0).getAT() <= currentTime) {
                inQ.add(processes.remove(0));
            }

            // execute
            Process currentProcess = inQ.poll();

            if (currentProcess != null) {
                int remainingBT = currentProcess.leftBT();
                int executionTime = Math.min(timeQuantum, remainingBT);
                currentProcess.reducedBT();

                System.out.print("P" + currentProcess.getProcessId() + "  ");

                currentTime += executionTime;

                if (currentProcess.leftBT() == 0) {
                    int tat = currentTime - currentProcess.getAT();
                    int wt = tat - currentProcess.getbt();

                    totalTAT += tat;
                    totalWT += wt;
                    processFinish++;

                    System.out.println("(Finishing Time: " + currentTime + ")");
                } else {
                    inQ.add(currentProcess);
                }
            } else {
                currentTime++;
            }
        }

        System.out.println("\nTotal Turnaround Time: " + totalTAT);
        System.out.println("Total Waiting Time: " + totalWT);

        double avgTAT = (processFinish > 0) ? (double) totalTAT / processFinish : 0;
        System.out.println("Average Turnaround Time: " + String.format("%.2f", avgTAT));

        double avgWT = (processFinish > 0) ? (double) totalWT / processFinish : 0;
        System.out.println("Average Waiting Time: " + String.format("%.2f", avgWT));

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

    private static int getTQ(Scanner scanner) {
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
