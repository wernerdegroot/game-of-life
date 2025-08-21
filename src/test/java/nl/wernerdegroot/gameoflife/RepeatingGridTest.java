package nl.wernerdegroot.gameoflife;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepeatingGridTest {

    // Used throughout many (but not all!) of the tests in this class:
    private static final RepeatingGrid GRID = RepeatingGridReader.read("""
            ┌─┬─┬─┐
            │ │X│ │
            │ │X│X│
            │X│ │ │
            └─┴─┴─┘
            """);

    @Test
    void constructing_from_a_matrix_should_work_for_3_by_3_grid() {

        var expected = new RepeatingGrid(
                3,
                3,
                Set.of(
                        new Cell(0, 1),
                        new Cell(1, 1),
                        new Cell(1, 2),
                        new Cell(2, 0)

                )
        );

        var toVerify = RepeatingGrid.fromMatrix(
                List.of(
                        List.of(false, true, false),
                        List.of(false, true, true),
                        List.of(true, false, false)
                )
        );

        assertEquals(expected, toVerify);
    }

    @Test
    void constructing_from_a_matrix_should_work_for_3_by_3_grid_where_not_all_rows_have_the_same_length() {

        var expected = new RepeatingGrid(
                3,
                3,
                Set.of(
                        new Cell(0, 1),
                        new Cell(1, 1),
                        new Cell(1, 2),
                        new Cell(2, 0)

                )
        );

        var toVerify = RepeatingGrid.fromMatrix(
                List.of(
                        List.of(false, true),
                        List.of(false, true, true),
                        List.of(true)
                )
        );

        assertEquals(expected, toVerify);
    }

    @Test
    void constructing_from_a_matrix_should_work_for_grid_with_no_rows() {
        var expected = RepeatingGrid.EMPTY;

        var toVerify = RepeatingGrid.fromMatrix(
                List.of()
        );

        assertEquals(expected, toVerify);
    }

    @Test
    void constructing_from_a_matrix_should_work_for_grid_with_no_columns() {
        var expected = RepeatingGrid.EMPTY;

        var toVerify = RepeatingGrid.fromMatrix(
                List.of(List.of())
        );

        assertEquals(expected, toVerify);
    }

    @Test
    void get_neighbours_should_be_correct_for_cell_in_center() {

        var cellInCenter = new Cell(1, 1);

        assertThat(
                GRID.getNeighboursOf(cellInCenter).toList(),
                containsInAnyOrder(
                        new Cell(0, 1),
                        new Cell(0, 2),
                        new Cell(1, 2),
                        new Cell(2, 2),
                        new Cell(2, 1),
                        new Cell(2, 0),
                        new Cell(1, 0),
                        new Cell(0, 0)
                )
        );
    }

    @Test
    void get_neighbours_should_be_correct_for_cell_in_bottom_left_corner() {
        var cellInBottomLeftCorner = new Cell(2, 0);

        assertThat(
                GRID.getNeighboursOf(cellInBottomLeftCorner).toList(),
                containsInAnyOrder(
                        new Cell(1, 0),
                        new Cell(1, 1),
                        new Cell(2, 1),
                        new Cell(0, 1),
                        new Cell(0, 0),
                        new Cell(0, 2),
                        new Cell(2, 2),
                        new Cell(1, 2)
                )
        );
    }

    @Test
    void get_neighbours_should_be_correct_for_cell_in_top_right_corner() {
        var cellInTopRightCorner = new Cell(0, 2);

        assertThat(
                GRID.getNeighboursOf(cellInTopRightCorner).toList(),
                containsInAnyOrder(
                        new Cell(2, 2),
                        new Cell(2, 0),
                        new Cell(0, 0),
                        new Cell(1, 0),
                        new Cell(1, 2),
                        new Cell(1, 1),
                        new Cell(0, 1),
                        new Cell(2, 1)
                )
        );
    }

    @Test
    void number_of_live_neighbours_should_be_correct_for_cell_in_center() {
        var cellInCenter = new Cell(1, 1);

        var expected = 3L;
        var toVerify = GRID.getNumberOfLivingNeighboursOf(cellInCenter);

        assertEquals(expected, toVerify);
    }

    @Test
    void number_of_live_neighbours_should_be_correct_for_cell_in_bottom_left_corner() {
        var cellInBottomLeftCorner = new Cell(2, 0);

        var expected = 3L;
        var toVerify = GRID.getNumberOfLivingNeighboursOf(cellInBottomLeftCorner);

        assertEquals(expected, toVerify);
    }

    @Test
    void number_of_live_neighbours_should_be_correct_for_cell_in_top_right_corner() {
        var cellInTopRightCorner = new Cell(0, 2);

        var expected = 4L;
        var toVerify = GRID.getNumberOfLivingNeighboursOf(cellInTopRightCorner);

        assertEquals(expected, toVerify);
    }

    @Test
    void get_next_iteration_should_consider_survivors() {
        // See https://nl.wikipedia.org/wiki/Game_of_Life
        RepeatingGrid repeatingGrid = RepeatingGridReader.read("""
                ┌─┬─┬─┐
                │X│ │ │
                │ │X│ │
                │X│ │ │
                └─┴─┴─┘
                """);

        var toVerify = repeatingGrid.getNextIteration();

        assertTrue(toVerify.isAlive(1, 1));
    }

    @Test
    void get_next_iteration_should_consider_overcrowding() {
        // See https://nl.wikipedia.org/wiki/Game_of_Life
        RepeatingGrid repeatingGrid = RepeatingGridReader.read("""
                ┌─┬─┬─┐
                │X│ │X│
                │ │X│ │
                │X│ │X│
                └─┴─┴─┘
                """);

        var toVerify = repeatingGrid.getNextIteration();

        assertTrue(toVerify.isDead(1, 1));
    }

    @Test
    void get_next_iteration_should_consider_births() {
        // See https://nl.wikipedia.org/wiki/Game_of_Life
        RepeatingGrid repeatingGrid = RepeatingGridReader.read("""
                ┌─┬─┬─┐
                │X│ │X│
                │ │ │ │
                │X│ │ │
                └─┴─┴─┘
                """);

        var toVerify = repeatingGrid.getNextIteration();

        assertTrue(toVerify.isAlive(1, 1));
    }

    @Test
    void get_next_iteration_should_be_correct() {

        RepeatingGrid repeatingGrid = RepeatingGridReader.read("""
                ┌─┬─┬─┬─┬─┐
                │ │ │ │ │ │
                │ │ │X│ │ │
                │ │ │X│X│ │
                │ │X│ │ │ │
                │ │ │ │ │ │
                └─┴─┴─┴─┴─┘
                """);

        RepeatingGrid expected = RepeatingGridReader.read("""
                ┌─┬─┬─┬─┬─┐
                │ │ │ │ │ │
                │ │ │X│X│ │
                │ │X│X│X│ │
                │ │ │X│ │ │
                │ │ │ │ │ │
                └─┴─┴─┴─┴─┘
                """);

        var toVerify = repeatingGrid.getNextIteration();

        assertEquals(expected, toVerify);
    }
}
