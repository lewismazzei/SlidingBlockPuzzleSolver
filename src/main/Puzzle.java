package src.main;

import java.util.Set;
import java.util.HashSet;

public class Puzzle {
    int n, m;
    public int[][] pieces;

    public Puzzle() {
        String[] extended_example =
            new String[]{"3", "3", "1", "2", "0", "5", "7", "3", "4", "8", "6"};
        initialise_dimensions(extended_example);
        initialise_pieces(extended_example);
    }

    public Puzzle(String[] data) {
        if (data.length <= 2) {
            System.out.print("usage: m n p(1) ... p(m*n)\n");
            System.exit(0);
        }
        initialise_dimensions(data);
        initialise_pieces(data);
    }

    private void initialise_dimensions(String[] data) {
        try {
            this.n = Integer.parseInt(data[1]);
            this.m = Integer.parseInt(data[0]);
            if (m < 2 || n < 2) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.print("exit: m and n must be integers (> 1)\n");
            System.exit(0);
        }

        // validate number of args
        if (data.length - 2 != this.n * this.m) {
            System.out.printf("usage: %d pieces must be provided for a board of this size (%d provided)\n",
                this.n * this.m - 1, data.length - 2);
            System.exit(0);
        }
    }

    private void initialise_pieces(String[] data) {
        this.pieces = new int[this.n][this.m];

        Set<Integer> seen = new HashSet<Integer>();
        int pointer = 2;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                if (isValidPiece(data[pointer], seen)) {
                    int piece = Integer.parseInt(data[pointer]);

                    this.pieces[i][j] = piece;
                    seen.add(piece);

                    pointer++;
                }
            }
        }
    }

    private boolean isValidPiece(String s, Set<Integer> seen) {
        try {
            int piece = Integer.parseInt(s);
            if (piece < 0 || piece > this.n * this.m - 1) {
                throw new IllegalArgumentException();
            }
            if (!seen.add(piece)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.printf("exit: invalid piece (%s)", s);
            System.exit(0);
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                s.append(this.pieces[i][j]);
                s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
