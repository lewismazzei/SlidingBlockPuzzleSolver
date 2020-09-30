package src.main;

import java.util.HashSet;

public class Puzzle {
    String[] SOLVABLE = new String[]{"3", "3", "1", "2", "0", "5", "7", "3", "4", "8", "6"};
    String[] UNSOLVABLE = new String[]{"4", "4", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "15", "14", "0"};

    private int n, m;
    private int[][] pieces;

    public Puzzle() {
        initialise_dimensions(SOLVABLE);
        initialise_pieces(SOLVABLE);
    }

    public Puzzle(String[] data) {
        try {
            initialise_dimensions(data);
            initialise_pieces(data);
        } catch (NumberFormatException e) {
            System.err.println("[non-integer argument(s) provided]");
            System.exit(0);
        }
    }

    private void initialise_dimensions(String[] data) throws NumberFormatException {
        this.n = Integer.parseInt(data[1]);
        this.m = Integer.parseInt(data[0]);
        if (n < 2 || m < 2) {
            System.err.printf("[provided dimension(s) (n=%s, m=$s) are out of range]\n", this.n, this.m);
            System.err.println("both n and m must be > 1");
            System.exit(0);
        }
        if (data.length != this.n * this.m + 2) {
            System.err.printf("[provided number of arguments (%s) is invalid]\n", data.length);
            System.err.println("total number of arguments must equal (arg1 * arg2 + 2}\n");
            System.exit(0);
        }
    }

    private void initialise_pieces(String[] data) throws NumberFormatException {
        this.pieces = new int[this.n][this.m];

        int piece = 0, index = 2;
        var seen = new HashSet<Integer>();
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                boolean isPieceoutOfRange = false;
                piece = Integer.parseInt(data[index]);
                if (piece < 0 || piece > this.n * this.m - 1) {
                    System.err.printf("[provided piece (%s) is out of range]\n", piece);
                    isPieceoutOfRange = true;
                }
                if (!seen.add(piece)) {
                    System.err.printf("[provided piece (%s) appears more than once]\n", piece);
                    isPieceoutOfRange = true;
                }
                if (isPieceoutOfRange) {
                    System.err.println("provided pieces must be equivalent to the set {0, ..., n * m - 1}");
                    System.exit(0);
                }
                this.pieces[i][j] = piece;
                seen.add(piece);
                index++;
            }
        }
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
