package src.main;

import java.util.Set;
import java.util.HashSet;

public class Puzzle {
    String[] SOLVABLE = new String[]{"3", "3", "1", "2", "0", "5", "7", "3", "4", "8", "6"};
    String[] UNSOLVABLE = new String[]{"4", "4", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "15", "14", "0"};

    int n, m;
    public int[][] pieces;

    public Puzzle() {
        String[] puzzle = SOLVABLE;

        initialise_dimensions(puzzle);
        initialise_pieces(puzzle);
    }

    public Puzzle(String[] data) {
        if (data.length <= 2) {
            System.err.println("usage: m n p(1) ... p(n * m)");
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
            System.err.println("usage: m & n must be integers (> 1)");
            System.exit(0);
        }

        // validate number of args
        if (data.length - 2 != this.n * this.m) {
            System.err.printf("usage: %d pieces and a blank space (0) must be provided for a board of this size (%d %s)\n",
                this.n * this.m - 1, data.length - 2, data.length - (this.n * this.m + 2) < 0 ? "short" : "too many");
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
            System.err.printf("usage: p(1) ... p(n * m) must be unique integers in the range 0 to n * m - 1\n", s);
            System.exit(0);
        }
        return true;
    }

    public boolean isSolvable() {
        boolean blankIsOnOddRowFromBottom = true;

        int[] pieces_list = new int[n * m];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                if (pieces[i][j] == 0) {
                    blankIsOnOddRowFromBottom = isOdd(n);
                }
                pieces_list[j + (i * m)] = pieces[i][j];
            }
        }

        int inversions = 0;
        for (int i = 0; i < pieces_list.length; i++) {
            for (int j = i + 1; j < pieces_list.length; j++) {
                if (pieces_list[i] > pieces_list[j]) {
                    inversions += 1;
                }
            }
        }

        if ((isOdd(this.m) && isEven(inversions))
                || (isEven(this.m) && (blankIsOnOddRowFromBottom == isEven(inversions)))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOdd(int n) {
        return n % 2 != 0;
    }

    private boolean isEven(int n) {
        return n % 2 == 0;
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
