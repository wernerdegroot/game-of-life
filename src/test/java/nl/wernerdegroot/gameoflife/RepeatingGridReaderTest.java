package nl.wernerdegroot.gameoflife;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepeatingGridReaderTest {

    @Test
    void should_read_empty_input_correctly() {
        var input = "";

        var expected = RepeatingGrid.EMPTY;
        var toVerify = RepeatingGridReader.read(input);

        assertEquals(expected, toVerify);
    }

    @Test
    void should_read_null_input_correctly() {
        String input = null;

        var expected = RepeatingGrid.EMPTY;
        var toVerify = RepeatingGridReader.read(input);

        assertEquals(expected, toVerify);
    }

    @Test
    void should_read_input_with_only_whitespace_correctly() {
        String input = "\n\n  \n";

        var expected = new RepeatingGrid(1, 2, Set.of());
        var toVerify = RepeatingGridReader.read(input);

        assertEquals(expected, toVerify);
    }

    @Test
    void should_read_simple_input_correctly()  {

        // Note that the compiler ignores all trailing spaces in text blocks.
        // This means that the second (empty) line is completely ignored. This
        // is deliberate. If you really want an empty line, consider using '\s'
        // like we do in the fourth line.
        var input = """
                 X 
                   
                 XX
                \s
                X  
                """;

        var expected = new RepeatingGrid(
                4,
                3,
                Set.of(
                        new Cell(0, 1),
                        new Cell(1, 1),
                        new Cell(1, 2),
                        new Cell(3, 0)
                )
        );

        var toVerify = RepeatingGridReader.read(input);

        assertEquals(expected, toVerify);
    }

    @Test
    void should_read_pretty_input_correctly() {
        var input = """
                ┌─┬─┬─┐
                │ │X│ │
                │ │X│X│
                │ │ │ │
                │X│ │ │
                └─┴─┴─┘
                """;

        var expected = new RepeatingGrid(
                4,
                3,
                Set.of(
                        new Cell(0, 1),
                        new Cell(1, 1),
                        new Cell(1, 2),
                        new Cell(3, 0)
                )
        );

        var toVerify = RepeatingGridReader.read(input);

        assertEquals(expected, toVerify);
    }
}
