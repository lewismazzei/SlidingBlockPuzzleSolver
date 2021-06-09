package src.main;

import java.util.*;

public class Solve {
    public static class ManhattanHeuristic implements Comparator<State> {
        @Override
        public int compare(State state1, State state2) {
            return state1.getH() - state2.getH();
        }
    }
    public static class TotalDistanceHeuristic implements Comparator<State> {
        @Override
        public int compare(State state1, State state2) {
            return (state1.getMoves().length + state1.getH()) - (state2.getMoves().length + state2.getH());
        }
    }

    public static String bestFirstSearch(Puzzle puzzle, Comparator<State> heuristic) {
        long startTime = System.currentTimeMillis();
        // System.out.println(puzzle);

        if (!puzzle.isSolvable()) {
            return "0";
        }

        var frontier = new PriorityQueue<State>(heuristic);
        frontier.add(new State(puzzle));

        var visitedStates = new HashSet<State>();

        while (frontier.peek() != null && System.currentTimeMillis() - startTime < 50) {
            State currentState = frontier.poll();
            if (currentState.getH() == 0) {
                var s = new StringBuilder(String.format("1 %d", currentState.getMoves().length));
                for (int move : currentState.getMoves()) {
                    s.append(String.format(" %d", move));
                }
                s.append("\n\n");
                s.append(String.format("%d ms taken\n", System.currentTimeMillis() - startTime));
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

    private static ArrayList<State> childStates(State parentState) {
        var children = new ArrayList<State>();
        int n = parentState.getPuzzle().getN();
        int m = parentState.getPuzzle().getM();

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                int[][] blocks = parentState.getPuzzle().getBlocks();
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
                    if (col < m - 1) {
                        pieceToMove = blocks[row][col + 1];
                        children.add(new State(parentState, pieceToMove));
                    }
                }
            }
        }
        return children;
    }
}



