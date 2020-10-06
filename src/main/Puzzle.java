package src.main;

import java.util.*;

public class Puzzle {
    private int n, m;
    private int[][] blocks;

    public Puzzle(String[] in) {
        try {
            initialise_dimensions(in);
            initialise_pieces(in);
        } catch (NumberFormatException e) {
            System.err.println("[non-integer argument(s) provided]");
            System.exit(0);
        }
    }

    public Puzzle(Puzzle parentPuzzle, int pieceToMove) {
        n = parentPuzzle.n;
        m = parentPuzzle.m;

        blocks = new int[n][m];
        for (int row = 0; row < parentPuzzle.n; row++) {
            for (int col = 0; col < parentPuzzle.m; col++) {
                int currentPiece = parentPuzzle.blocks[row][col];
                if (currentPiece == pieceToMove) {
                    currentPiece = 0;
                } else if (currentPiece == 0) {
                    currentPiece = pieceToMove;
                }
                blocks[row][col] = currentPiece;
            }
        }
    }

    private void initialise_dimensions(String[] in) throws NumberFormatException {
        n = Integer.parseInt(in[0]);
        m = Integer.parseInt(in[1]);
        if (n < 2 || m < 2) {
            System.err.printf("[provided dimension(s) (n=%s, m=$s) are out of range]\n", n, m);
            System.err.println("both n and m must be > 1");
            System.exit(0);
        }
        if (in.length != n * m + 2) {
            System.err.printf("[provided number of arguments (%s) is invalid]\n", in.length);
            System.err.println("total number of arguments must equal: arg1 * arg2 + 2\n");
            System.exit(0);
        }
    }

    private void initialise_pieces(String[] in) throws NumberFormatException {
        blocks = new int[n][m];

        int block = 0, inIndex = 2;
        var seen = new HashSet<Integer>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                boolean isPieceInvalid = false;
                block = Integer.parseInt(in[inIndex]);
                if (block < 0 || block > n * m - 1) {
                    System.err.printf("[provided piece (%s) is out of range]\n", block);
                    isPieceInvalid = true;
                }
                if (!seen.add(block)) {
                    System.err.printf("[provided piece (%s) appears more than once]\n", block);
                    isPieceInvalid = true;
                }
                if (isPieceInvalid) {
                    System.err.println("[provided pieces must be equivalent to the set {0, ..., n * m - 1}]");
                    System.exit(0);
                }
                blocks[row][col] = block;
                seen.add(block);
                inIndex++;
            }
        }
    }

    public boolean isSolvable() {
        boolean isBlankOnOddRowFromBottom = false;

        int[] blocksList = new int[n * m];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (blocks[row][col] == 0) {
                    if (isEven(n) && isOdd(row)) {
                        isBlankOnOddRowFromBottom = false;
                    } else {
                        isBlankOnOddRowFromBottom = true;
                    }
                }
                blocksList[col + (row * m)] = blocks[row][col];
            }
        }

        int inversionCount = 0;
        for (int i = 0; i < blocksList.length - 1; i++) {
            for (int j = i + 1; j < blocksList.length; j++) {
                if (blocksList[i] > blocksList[j] && blocksList[j] != 0) {
                    inversionCount += 1;
                }
            }
        }

        // credit: https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
        if ((isOdd(m) && isEven(inversionCount))
                || (isEven(m) && (isBlankOnOddRowFromBottom == isEven(inversionCount)))) {
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

    public int manhattanDistance() {
        int distance = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                int block = blocks[row][col];
                if (block != 0) {
                    distance += Math.abs(goalRow(block, m) - row);
                    distance += Math.abs(goalCol(block, m) - col);
                }
            }
        }
        return distance;
    }

    private static int goalRow(int block, int puzzle_width) {
        return (int) Math.floor((double) (block - 1) / (double) puzzle_width);

    }

    private static int goalCol(int block, int puzzle_width) {
        return (block - 1) % puzzle_width;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                s.append(blocks[row][col]);
                s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getBlock(int row, int col) {
        return blocks[row][col];
    }

    public int[][] getBlocks() {
        return blocks;
    }
}
