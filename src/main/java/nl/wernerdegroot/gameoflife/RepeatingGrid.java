package nl.wernerdegroot.gameoflife;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * The game of life is traditionally played on an infinite grid.
 * For this project, we simplify things by wrapping the grid around.
 * The only good reason to do so is that it simplifies drawing.
 * With some minor adjustments (skipping wrapping) this class
 * could easily represent an infinite grid.
 */
public class RepeatingGrid {

    final int numberOfRows;
    final int numberOfColumns;
    final Set<Cell> generation;

    public static final RepeatingGrid EMPTY = new RepeatingGrid(0, 0, Set.of());

    public RepeatingGrid(int numberOfRows, int numberOfColumns, Set<Cell> generation) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.generation = generation;
    }

    /**
     * Construct from a matrix (outer `List` contains rows from top
     * to bottom, the inner `List` contains cells from left to right)
     *
     * This method is somewhat lenient. Not all rows need to be of the
     * same length.
     */
    public static RepeatingGrid fromMatrix(List<List<Boolean>> matrix) {
        // Best guess of the number of columns. If its value
        // is empty, it means that there are no rows.
        var possibleNumberOfColumns = matrix.stream().map(List::size).max(Comparator.naturalOrder());

        return possibleNumberOfColumns
                .filter(numberOfColumns -> numberOfColumns > 0)
                .map(numberOfColumns -> {
                    var numberOfRows = matrix.size();

                    Set<Cell> generation = new HashSet<>();
                    for (var rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
                        var row = matrix.get(rowIndex);
                        for (var columnIndex = 0; columnIndex < row.size(); columnIndex++) {
                            if (row.get(columnIndex)) {
                                generation.add(new Cell(rowIndex, columnIndex));
                            }
                        }
                    }
                    return new RepeatingGrid(numberOfRows, numberOfColumns, generation);
                })
                .orElse(RepeatingGrid.EMPTY);
    }

    public boolean isAlive(Cell cell) {
        return generation.contains(cell);
    }

    public boolean isAlive(int rowIndex, int columnIndex) {
        var coordinates = new Cell(rowIndex, columnIndex);
        return isAlive(coordinates);
    }

    public boolean isDead(Cell cell) {
        return !isAlive(cell);
    }

    public boolean isDead(int rowIndex, int columnIndex) {
        var coordinates = new Cell(rowIndex, columnIndex);
        return isDead(coordinates);
    }

    private Cell wrap(Cell cell) {
        return new Cell(
                Math.floorMod(cell.rowIndex(), numberOfRows),
                Math.floorMod(cell.columnIndex(), numberOfColumns)
        );
    }

    Stream<Cell> getNeighboursOf(Cell cell) {
        return cell.neighbours().map(this::wrap);
    }

    long getNumberOfLivingNeighboursOf(Cell cell) {
        return getNeighboursOf(cell).filter(this::isAlive).count();
    }

    Stream<Cell> getSurvivors() {
        return generation
                .stream()
                .filter(cell -> {
                    var numberOfLiveNeighbours = getNumberOfLivingNeighboursOf(cell);
                    return 2 <= numberOfLiveNeighbours && numberOfLiveNeighbours <= 3;
                });
    }

    Stream<Cell> getBirths() {
        return generation
                .stream()
                .flatMap(this::getNeighboursOf)
                .filter(this::isDead)
                .filter(cell -> {
                    var numberOfLiveNeighbours = getNumberOfLivingNeighboursOf(cell);
                    return numberOfLiveNeighbours == 3;
                });
    }

    public RepeatingGrid getNextIteration() {
        var nextGeneration = Stream.concat(getSurvivors(), getBirths()).collect(toSet());
        return new RepeatingGrid(numberOfRows, numberOfColumns, nextGeneration);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RepeatingGrid that = (RepeatingGrid) o;
        return numberOfRows == that.numberOfRows && numberOfColumns == that.numberOfColumns && Objects.equals(generation, that.generation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfRows, numberOfColumns, generation);
    }

    @Override
    public String toString() {
        return "RepeatingGrid{" +
               "numberOfRows=" + numberOfRows +
               ", numberOfColumns=" + numberOfColumns +
               ", generation=" + generation +
               '}';
    }
}
