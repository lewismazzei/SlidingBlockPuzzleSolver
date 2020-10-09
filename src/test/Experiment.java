package src.test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import src.main.Puzzle;
import src.main.Solve;

import java.io.IOException;
import java.math.BigInteger;

class Experiment {
    static int maxDimension;
    public static void main(String[] args) {
        maxDimension = Integer.parseInt(args[0]);
        for (int n = 2; n <= maxDimension; n++) {
            for (int m = 2; m <= maxDimension; m++) {
                Integer[] instance = new Integer[n * m];
                for (int i = 0; i < n * m; i++) {
                    instance[i] = i;
                }
                System.out.println("Working on puzzle: " + n + "x" + m);
                performExperiment(n, m, new Permutations<Integer>(instance));
                System.out.println();
            }
        }
        // System.exit(0);
    }

    private static void performExperiment(int n, int m, Permutations<Integer> instances) {
        int instanceCount = 0;
        while (instances.hasNext()) {
            printProgress(++instanceCount, n, m);

            String output = Solve.bestFirstSearch(new Puzzle(n, m, instances.next()), new Solve.ManhattanHeuristic());

            int moves, timeTaken;
            var scanner = new Scanner(output);
            if (scanner.nextInt() == 1) {
                moves = scanner.nextInt();
                scanner.nextLine();
                scanner.nextLine();
                timeTaken = scanner.nextInt();
                scanner.close();
            } else {
                moves = 0;
                timeTaken = 0;
            }

            try (var writer = new FileWriter(new File("results.csv"), true)) {
                writer.write(n * m);
                writer.write(",");
                writer.write(moves);
                writer.write(",");
                writer.write(timeTaken);
                writer.write("\n");
            } catch (IOException e) {
                System.exit(0);
            }
        }
    }

    private static void printProgress(int instanceCount, int n, int m) {
        long totalInstances = factorial(n*m);
        long percentComplete = instanceCount / totalInstances * 100;
        System.out.print(instanceCount + " / " + totalInstances + "\r");
        // System.out.print("|");
        // for (long i = 0; i < Math.floor(percentComplete / 5); i++) {
        //     System.out.print("=");
        // }
        // for (int i = 0; i < 20 - Math.floor(percentComplete / 5); i++) {
        //     System.out.print(" ");
        // }
        // System.out.print("|\r");
    }
    public static long factorial(int x) {
        long fact = 1;
        for (int i = 2; i <= x; i++){
            fact *= i;
        }
        return fact;
    }
}

