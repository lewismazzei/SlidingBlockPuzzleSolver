package src.main;

import java.util.*;

class State implements Comparable<State> {
    private Puzzle puzzle;
    private int h;
    private int[] moves;

    // construct inital state
    public State(Puzzle puzzle) {
        this.puzzle = puzzle;
        h = this.puzzle.manhattanDistance();
        moves = new int[0];
    }

    // construct child state
    public State(State parentState, int pieceToMove) {
        this.puzzle = new Puzzle(parentState.puzzle, pieceToMove);
        h = this.puzzle.manhattanDistance();

        moves = new int[parentState.moves.length + 1];

        for (int i = 0; i < moves.length - 1; i++) {
            moves[i] = parentState.moves[i];
        }
        moves[moves.length - 1] = pieceToMove;
    }

    @Override
    // evaluation function: f(n) = h(n)
    public int compareTo(State state) {
        return this.h - state.h;
    }

    @Override
    // equal when block arrays are
    public boolean equals(Object obj) {
        return Arrays.deepEquals(this.puzzle.getBlocks(), ((State) obj).puzzle.getBlocks());
    }

    @Override
    // required for equals()
    public int hashCode() {
        int hash = 1;
        for (int row = 0; row < puzzle.getN(); row++) {
            for (int col = 0; col < puzzle.getM(); col++) {
                hash *= puzzle.getBlocks()[row][col];
            }
        }
        return hash;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public int getH() {
        return h;
    }

    public int[] getMoves() {
        return moves;
    }
}
