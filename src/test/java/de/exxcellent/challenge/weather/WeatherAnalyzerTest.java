package de.exxcellent.challenge.weather;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class WeatherAnalyzerTest {
    @TempDir
    Path tempDir;

    @Test
    void testFindDayWithSmallestSpread_expectedFileFormat() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("Day,MxT,other,MnT\n");
            writer.write("1,30,31,12\n");
            writer.write("2,25,1,26\n");
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }

        String smallestSpread = WeatherAnalyzer.findDayWithSmallestSpread(csvFile.getAbsolutePath());
        Assertions.assertEquals("2", smallestSpread);
    }

    @Test
    void testFindDayWithSmallestSpread_expectedFileFormat_negativeMinTemp() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("Day,MxT,other,MnT\n");
            writer.write("1,5,31,-2\n");
            writer.write("2,25,1,10\n");
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }

        String smallestSpread = WeatherAnalyzer.findDayWithSmallestSpread(csvFile.getAbsolutePath());
        Assertions.assertEquals("1", smallestSpread);
    }

    @Test
    void testFindDayWithSmallestSpread_unexpectedFileHeaders() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("one,two,three,four\n");
            writer.write("1,5,31,-2\n");
            writer.write("2,25,1,10\n");
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }

        String smallestSpread = WeatherAnalyzer.findDayWithSmallestSpread(csvFile.getAbsolutePath());
        Assertions.assertEquals("N/A", smallestSpread);
    }

    @Test
    void testFindDayWithSmallestSpread_emptyFile() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try {
            csvFile.createNewFile();
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }
        String smallestSpread = WeatherAnalyzer.findDayWithSmallestSpread(csvFile.getAbsolutePath());
        Assertions.assertEquals("N/A", smallestSpread);
    }

    @Test
    void testFindDayWithSmallestSpread_incorrectFilePath() {
        String wrongPath = tempDir.resolve("nonexistent.csv").toString();
        String smallestSpread = WeatherAnalyzer.findDayWithSmallestSpread(wrongPath);
        Assertions.assertEquals("N/A", smallestSpread);
    }

    @Test
    void testFindDayWithSmallestSpread_invalidFileDataFormat() throws IOException {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("Day,MxT,MnT\n");
            writer.write("1,abc,10\n");
        }
        String result = WeatherAnalyzer.findDayWithSmallestSpread(csvFile.getAbsolutePath());
        Assertions.assertEquals("N/A", result);
    }
}
