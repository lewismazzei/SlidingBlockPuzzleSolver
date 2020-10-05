package src.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.HashSet;

public class Search {
    static class State implements Comparable<State> {
        private int h;
        private int[] moves;
        private Puzzle puzzle;

        public State(Puzzle puzzle) {
            h = manhattanDistance(puzzle);
            moves = new int[0];
            this.puzzle = puzzle;
        }

        public State(State parentState, int pieceToMove) {
            moves = new int[parentState.moves.length + 1];
            for (int i = 0; i < moves.length - 1; i++) {
                moves[i] = parentState.moves[i];
            }
            moves[moves.length - 1] = pieceToMove;

            puzzle = new Puzzle(parentState.puzzle, pieceToMove);
            h = manhattanDistance(puzzle);
        }

        @Override
        public int compareTo(State state) {
            return this.h - state.h;
        }

        @Override
        public int hashCode() {
            int hash = 1;
            for (int row = 0; row < puzzle.getN(); row++) {
                for (int col = 0; col < puzzle.getM(); col++) {
                    hash *= puzzle.getBlocks()[row][col];
                }
            }
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return Arrays.deepEquals(this.puzzle.getBlocks(), ((State) obj).puzzle.getBlocks());
        }
    }

    public static String bestFirstSearchManhattan(Puzzle puzzle) {
        if (!puzzle.isSolvable()) {
            return "0";
        }

        var frontier = new PriorityQueue<State>();
        frontier.add(new State(puzzle));

        var visitedStates = new HashSet<State>();

        while (frontier.peek() != null) {
            State currentState = frontier.poll();
            if (currentState.h == 0) {
                var s = new StringBuilder(String.format("1 %d", currentState.moves.length));
                for (int move : currentState.moves) {
                    s.append(String.format(" %d", move));
                }
                return s.toString();
            } else {
                ArrayList<State> childStates = childStates(currentState);
                for (int i = 0; i < childStates.size(); i++) {
                    State childState = childStates.get(i);
                    if (!visitedStates.contains(childState)) {
                        frontier.add(childState);
                        visitedStates.add(childState);
                    }
                }
            }
        }
        return "-1";
    }


    private static int manhattanDistance(Puzzle puzzle) {
        int m = puzzle.getM();
        int n = puzzle.getN();

        int distance = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                int block = puzzle.getBlock(row, col);
                if (block != 0) {
                    distance += Math.abs(goalRow(block, m) - row);
                    distance += Math.abs(goalCol(block, m) - col);
                }
            }
        }
        return distance;
    }

    private static int goalRow(int piece, int puzzle_width) {
        return (int) Math.floor((double) (piece - 1) / (double) puzzle_width);

    }

    private static int goalCol(int piece, int puzzle_width) {
        return (piece - 1) % puzzle_width;
    }

    private static ArrayList<State> childStates(State parentState) {
        var children = new ArrayList<State>();
        int n = parentState.puzzle.getN();
        int m = parentState.puzzle.getM();

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                int[][] blocks = parentState.puzzle.getBlocks();
                if (blocks[row][col] == 0) {
                    int pieceToMove;
                    if (row > 0) {
                        pieceToMove = blocks[row - 1][col];
                        children.add(new State(parentState, pieceToMove));
                    }
                    if (row < n - 1) {
                        pieceToMove = blocks[row + 1][col];
                        children.add(new State(parentState, pieceToMove));
                    }
                    if (col > 0) {
                        pieceToMove = blocks[row][col - 1];
                        children.add(new State(parentState, pieceToMove));
                    }
                    if (col < n - 1) {
                        pieceToMove = blocks[row][col + 1];
                        children.add(new State(parentState, pieceToMove));
                    }
                }
            }
        }
        return children;
    }
}



