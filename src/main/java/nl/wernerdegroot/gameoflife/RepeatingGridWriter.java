package nl.wernerdegroot.gameoflife;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class RepeatingGridWriter {

    public static final String HEAD_DELIMITER_LEFT = "┌";
    public static final String HEAD_CONTENT = "─";
    public static final String HEAD_SEPARATOR = "┬";
    public static final String HEAD_DELIMITER_RIGHT = "┐";
    public static final String BODY_DELIMITER_LEFT = "│";
    public static final String BODY_SEPARATOR = "│";
    public static final String BODY_DELIMITER_RIGHT = "│";
    public static final String FOOT_DELIMITER_LEFT = "└";
    public static final String FOOT_CONTENT = "─";
    public static final String FOOT_SEPARATOR = "┴";
    public static final String FOOT_DELIMITER_RIGHT = "┘";

    public static final String ALIVE = "X";
    public static final String DEAD = " ";

    /**
     * Writes a {@link RepeatingGrid} in the following format:
     *
     * ┌─┬─┬─┐
     * │ │X│ │
     * │ │X│X│
     * │X│ │ │
     * └─┴─┴─┘
     */
    public static String write(RepeatingGrid grid) {

        if (grid.numberOfColumns == 0 || grid.numberOfRows == 0) {
            return null;
        }

        List<String> rows = new ArrayList<>();

        var head = Collections.nCopies(grid.numberOfColumns, HEAD_CONTENT)
                .stream()
                .collect(joining(HEAD_SEPARATOR, HEAD_DELIMITER_LEFT, HEAD_DELIMITER_RIGHT));

        rows.add(head);

        IntStream.range(0, grid.numberOfRows).forEach(rowIndex -> {
            var row = IntStream.range(0, grid.numberOfColumns)
                    .mapToObj(columnIndex -> {
                        return grid.isAlive(rowIndex, columnIndex)
                                ? ALIVE
                                : DEAD;
                    })
                    .collect(joining(BODY_SEPARATOR, BODY_DELIMITER_LEFT, BODY_DELIMITER_RIGHT));

            rows.add(row);
        });

        var foot = Collections.nCopies(grid.numberOfColumns, FOOT_CONTENT)
                .stream()
                .collect(joining(FOOT_SEPARATOR, FOOT_DELIMITER_LEFT, FOOT_DELIMITER_RIGHT));

        rows.add(foot);

        return rows.stream().collect(joining("\n", "", "\n"));
    }
}
