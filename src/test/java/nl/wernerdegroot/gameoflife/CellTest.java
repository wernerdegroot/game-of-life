package nl.wernerdegroot.gameoflife;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CellTest {

    @Test
    void north_should_return_cell_above() {
        var cell = new Cell(3, 4);

        var expected = new Cell(2, 4);
        var toVerify = cell.north();

        assertEquals(expected, toVerify);
    }

    @Test
    void east_should_return_cell_to_the_right() {
        var cell = new Cell(3, 4);

        var expected = new Cell(3, 5);
        var toVerify = cell.east();

        assertEquals(expected, toVerify);
    }

    @Test
    void south_should_return_cell_below() {
        var cell = new Cell(3, 4);

        var expected = new Cell(4, 4);
        var toVerify = cell.south();

        assertEquals(expected, toVerify);
    }

    @Test
    void west_should_return_cell_to_the_left() {
        var cell = new Cell(3, 4);

        var expected = new Cell(3, 3);
        var toVerify = cell.west();

        assertEquals(expected, toVerify);
    }

    @Test
    void neighbours_should_return_cells_around() {
        var cell = new Cell(3, 4);

        assertThat(
                cell.neighbours().toList(),
                containsInAnyOrder(
                        new Cell(2, 4),
                        new Cell(2, 5),
                        new Cell(3, 5),
                        new Cell(4, 5),
                        new Cell(4, 4),
                        new Cell(4, 3),
                        new Cell(3, 3),
                        new Cell(2, 3)
                )
        );
    }
}
