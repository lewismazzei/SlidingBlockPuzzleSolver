package src.main;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class SlidingBlockPuzzleSolver {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        int n, m;
        try {
            n = scanner.nextInt();
            m = scanner.nextInt();
            int[] blocks = new int[n * m];
            for (int i = 0; i < n * m; i++) {
                blocks[i] = scanner.nextInt();
            }
            System.out.print(Solve.bestFirstSearch(new Puzzle(n, m, blocks), new Solve.ManhattanHeuristic()));
        } catch (NoSuchElementException|ArrayIndexOutOfBoundsException e) {
            System.err.println("-1\n[usage: n m block(1) block(2) ... block(n*m-1)]");
            System.exit(0);
        }
        scanner.close();


    }
}
