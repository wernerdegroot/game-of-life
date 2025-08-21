package nl.wernerdegroot.gameoflife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepeatingGridWriterTest {

    @Test
    void shouldWriteCorrectly() {

        var expected = """
                ┌─┬─┬─┬─┬─┐
                │ │ │ │ │ │
                │ │ │X│ │ │
                │ │ │X│X│ │
                │ │X│ │ │ │
                │ │ │ │ │ │
                └─┴─┴─┴─┴─┘
                """;


        RepeatingGrid repeatingGrid = RepeatingGridReader.read(expected);

        var toVerify = RepeatingGridWriter.write(repeatingGrid);

        assertEquals(expected.trim(), toVerify.trim());
    }
}
