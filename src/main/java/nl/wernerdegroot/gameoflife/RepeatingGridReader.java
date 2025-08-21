package nl.wernerdegroot.gameoflife;

import java.util.*;

public class RepeatingGridReader {

    /**
     * Parses expressions like the following:
     * <p>
     * ┌─┬─┬─┐
     * │ │X│ │
     * │ │X│X│
     * │X│ │ │
     * └─┴─┴─┘
     * <p>
     * It is the simplest possible parser that could possibly work. It
     * ignores everything but the relevant characters ' ' and 'X'. It
     * does not even require that the provided lines are equally long.
     */
    public static RepeatingGrid read(String input) {

        if (input == null || input.isEmpty()) {
            return RepeatingGrid.EMPTY;
        }

        // A grid of `true` and `false`. This is an intermediate
        // data structure. We prefer a more sparse representation
        // like `RepeatingGrid` that only stores the cells that
        // contain a live cell.
        List<List<Boolean>> matrix = new ArrayList<>();

        List<Boolean> row = new ArrayList<>();

        for (var current : input.toCharArray()) {
            switch (current) {
                case 'X' -> row.add(true);
                case ' ' -> row.add(false);
                case '\n' -> {
                    if (!row.isEmpty()) {
                        matrix.add(row);
                        row = new ArrayList<>();
                    }
                }
                default -> {
                    // Ignore all other characters.
                }
            }
        }

        // Last row (in case there's no trailing '\n'):
        if (!row.isEmpty()) {
            matrix.add(row);
        }

        return RepeatingGrid.fromMatrix(matrix);
    }
}
