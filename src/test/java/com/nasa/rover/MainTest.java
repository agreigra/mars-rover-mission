package com.nasa.rover;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MainTest {

    @Test
    void mainParsesInputFileAndPrintsFinalMissionPositions(@TempDir Path tempDir) throws Exception {
        Path inputFile = tempDir.resolve("input.txt");
        Files.writeString(inputFile, "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM\n");

        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        try {
            Main.main(new String[] { inputFile.toString() });
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("1 3 N\n5 1 E\n", output.toString());
    }
}
