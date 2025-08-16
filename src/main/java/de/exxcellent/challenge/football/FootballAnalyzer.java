package de.exxcellent.challenge.football;

import de.exxcellent.challenge.util.CsvReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FootballAnalyzer {

    /**
     * Gives team with smallest distance of goals received and scored from file
     *
     * @param filePath path to file containing football data to be analyzed
     * @return team with smallest distance
     */
    public static String findTeamWithSmallestDistance(String filePath) {
        try {
            List<Map<String, String>> csvRows = CsvReader.readCSV(filePath);
            List<Football> footballData = parseFootballData(csvRows);

            return footballData.stream()
                    .min(Comparator.comparingInt(Football::getGoalDistance))
                    .map(team -> String.valueOf(team.teamName()))
                    .orElse("N/A");
        } catch (IOException | IllegalArgumentException e) {
            System.out.printf("error while reading csv file: %s", e.getMessage());
            return "N/A";
        }
    }

    /**
     * converts data to football type
     *
     * @param rows List of String containing info of football data
     * @return List of football data with team name, goals scored and goals received
     */
    private static List<Football> parseFootballData(List<Map<String, String>> rows) throws IllegalArgumentException {
        List<Football> data = new ArrayList<>();
        for (Map<String, String> row : rows) {
            if (!row.containsKey("Team") || !row.containsKey("Goals") || !row.containsKey("Goals Allowed")) {
                throw new IllegalArgumentException("CSV row is missing required keys: " + row.keySet());
            }
            data.add(new Football(row.get("Team"), Integer.parseInt(row.get("Goals")), Integer.parseInt(row.get("Goals Allowed"))));
        }
        return data;
    }
}
