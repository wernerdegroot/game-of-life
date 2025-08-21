package nl.wernerdegroot.gameoflife;

import java.util.stream.Stream;

/**
 * Coordinates on a grid.
 *
 * The grid's origin is in the top-left corner.
 */
public record Cell(int rowIndex, int columnIndex) {

    public Cell north() {
        return new Cell(rowIndex - 1, columnIndex);
    }

    public Cell east() {
        return new Cell(rowIndex, columnIndex + 1);
    }

    public Cell south() {
        return new Cell(rowIndex + 1, columnIndex);
    }

    public Cell west() {
        return new Cell(rowIndex, columnIndex - 1);
    }

    public Stream<Cell> neighbours() {
        return Stream.of(
                north(),
                north().east(),
                east(),
                south().east(),
                south(),
                south().west(),
                west(),
                north().west()
        );
    }
}
