package de.exxcellent.challenge.football;

import de.exxcellent.challenge.weather.WeatherAnalyzer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FootballAnalyzerTest {
    @TempDir
    Path tempDir;

    @Test
    void testFindTeamWithSmallestDistance_expectedFileFormat() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("Team,Goals,other,Goals Allowed\n");
            writer.write("bvb,30,31,12\n");
            writer.write("arsenal,25,1,26\n");
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }

        String smallestDif = FootballAnalyzer.findTeamWithSmallestDistance(csvFile.getAbsolutePath());
        Assertions.assertEquals("arsenal", smallestDif);
    }

    @Test
    void testFindTeamWithSmallestDistance_unexpectedFileHeaders() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("one,two,three,four\n");
            writer.write("bvb,30,31,12\n");
            writer.write("arsenal,25,1,26\n");
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }

        String smallestDif = FootballAnalyzer.findTeamWithSmallestDistance(csvFile.getAbsolutePath());
        Assertions.assertEquals("N/A", smallestDif);
    }

    @Test
    void testFindTeamWithSmallestDistance_emptyFile() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try {
            csvFile.createNewFile();
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }
        String smallestDif = FootballAnalyzer.findTeamWithSmallestDistance(csvFile.getAbsolutePath());
        Assertions.assertEquals("N/A", smallestDif);
    }

    @Test
    void testFindTeamWithSmallestDistance_incorrectFilePath() {
        String wrongPath = tempDir.resolve("nonexistent.csv").toString();
        String smallestDif = FootballAnalyzer.findTeamWithSmallestDistance(wrongPath);
        Assertions.assertEquals("N/A", smallestDif);
    }

    @Test
    void testFindTeamWithSmallestDistance_unexpectedFileDataFormat() {
        File csvFile = tempDir.resolve("test.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("Team,Goals,other,Goals Allowed\n");
            writer.write("bvb,thirty,31,12\n");
            writer.write("arsenal,25,1,26\n");
        } catch (IOException e) {
            System.out.printf("error during test setup: %s\n", e.getMessage());
        }
        String smallestDif = FootballAnalyzer.findTeamWithSmallestDistance(csvFile.getAbsolutePath());
        Assertions.assertEquals("N/A", smallestDif);
    }
}
