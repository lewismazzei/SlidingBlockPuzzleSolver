package src.main;

public class Main {
    static final String[] SOLVED = new String[]{"3", "4", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "0"};
    static final String[] SOLVABLE_SHORT = new String[]{"2", "2", "1", "2", "0", "3"};

    static final String[] SOLVABLE = new String[]{"3", "3", "1", "2", "0", "5", "7", "3", "4", "8", "6"};
    static final String[] UNSOLVABLE = new String[]{"4", "4", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "15", "14", "0"};

    static final String[] SOLVABLE_LONG = new String[]{"3", "3", "6", "4", "7", "8", "5", "0", "3", "2", "1"};

    public static void main(String[] in) {
        System.out.println(Solve.bestFirstSearch(new Puzzle(SOLVABLE_LONG), new Solve.TotalDistanceHeuristic()));
    }
}
