package de.exxcellent.challenge.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class CsvReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadCSV_correctPath_fileWithContent() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("day,mxT,mnT\n");
            writer.write("1,30,12\n");
            writer.write("2,25,15\n");
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }

        List<Map<String, String>> result = Assertions.assertDoesNotThrow(() -> CsvReader.readCSV(csvFile.getAbsolutePath()));
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("1", result.get(0).get("day"));
        Assertions.assertEquals("30", result.get(0).get("mxT"));
        Assertions.assertEquals("12", result.get(0).get("mnT"));
        Assertions.assertEquals("2", result.get(1).get("day"));
        Assertions.assertEquals("25", result.get(1).get("mxT"));
        Assertions.assertEquals("15", result.get(1).get("mnT"));
    }

    @Test
    void testReadCSV_incorrectPath() {
        String wrongPath = tempDir.resolve("nonexistent.csv").toString();

        Assertions.assertThrows(IOException.class, () -> CsvReader.readCSV(wrongPath));
    }

    @Test
    void testReadCSV_correctPath_emptyFile() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try {
            csvFile.createNewFile();
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }

        List<Map<String, String>> result = Assertions.assertDoesNotThrow(() -> CsvReader.readCSV(csvFile.getAbsolutePath()));
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void testReadCSV_correctPath_incompleteRow() throws IOException {
        File csvFile = tempDir.resolve("incomplete.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("day,mxT,mnT\n");
            writer.write("1,30\n");
        }

        List<Map<String, String>> result = Assertions.assertDoesNotThrow(() -> CsvReader.readCSV(csvFile.getAbsolutePath()));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("1", result.get(0).get("day"));
        Assertions.assertEquals("30", result.get(0).get("mxT"));
        Assertions.assertNull(result.get(0).get("mnT"));
    }

}
