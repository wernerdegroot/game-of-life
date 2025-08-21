package nl.wernerdegroot.gameoflife;

import java.time.Duration;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class GameOfLife {

    public static void main(String[] args) throws InterruptedException {

        var random = new Random();

        var numberOfRows = 10;
        var numberOfColumns = 10;
        var initialNumberOfLiveCells = 20;
        var iterationInterval = Duration.ofSeconds(2);

        var generation = IntStream.range(0, initialNumberOfLiveCells)
                .mapToObj(ignored -> {
                    var rowIndex = random.nextInt(numberOfRows);
                    var columnIndex = random.nextInt(numberOfColumns);
                    return new Cell(rowIndex, columnIndex);
                })
                .collect(toSet());

        var grid = new RepeatingGrid(numberOfRows, numberOfColumns, generation);

        System.out.print(RepeatingGridWriter.write(grid));

        while(true) {
            Thread.sleep(iterationInterval);

            // Redraw the grid by moving the cursor up a couple of lines.
            // Two extra, because of the header and the footer:
            System.out.printf("\033[%dF", numberOfRows + 2);

            grid = grid.getNextIteration();

            System.out.print(RepeatingGridWriter.write(grid));
        }
    }
}
