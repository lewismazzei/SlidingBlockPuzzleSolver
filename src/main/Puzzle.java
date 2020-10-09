package src.main;

import java.util.*;

public class Puzzle {
    private int n, m;
    private int[][] blocks;

    public Puzzle(int n, int m, Integer[] blocksList) {
        if (n < 2 || m < 2) {
            System.err.printf("-1\n[provided dimension(s) (n=%s, m=%s) are out of range: n && m > 1]\n", n, m);
            System.exit(0);
        }
        this.n = n;
        this.m = m;
        initialiseBlocks(blocksList);
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

    private void initialiseBlocks(Integer[] blocksList) {
        blocks = new int[n][m];

        int i = 0;
        var seen = new HashSet<Integer>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (blocksList[i] < 0 || blocksList[i] > n * m - 1) {
                    System.err.printf("-1\n[block (%s) is out of range: {0, 1, ..., n*m-1}]\n", blocksList[i]);
                    System.exit(0);
                }
                if (!seen.add(blocksList[i])) {
                    System.err.printf("-1\n[block (%s) appears more than once]\n", blocksList[i]);
                    System.exit(0);
                }
                blocks[row][col] = blocksList[i];
                seen.add(blocksList[i]);
                i++;
            }
        }
    }

    public boolean isSolvable() {
        int rowWithBlank = 0;

        int[] blocksList = new int[n * m];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (blocks[row][col] == 0) {
                    rowWithBlank = row;
                }
                blocksList[col + (row * m)] = blocks[row][col];
            }
        }

        int inversionCount = 0;
        for (int i = 0; i < blocksList.length - 1; i++) {
            if (blocksList[i] != 0) {
                for (int j = i + 1; j < blocksList.length; j++) {
                    if (blocksList[j] != 0 && blocksList[i] > blocksList[j]) {
                        inversionCount += 1;
                    }
                }
            }
        }

        // credit: https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
        if ((isOdd(m) && isEven(inversionCount)) || (isEven(m) && (isOdd(n - rowWithBlank) == isEven(inversionCount)))) {
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
