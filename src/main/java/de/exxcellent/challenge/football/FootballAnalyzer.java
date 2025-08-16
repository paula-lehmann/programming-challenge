package de.exxcellent.challenge.football;

import de.exxcellent.challenge.util.CsvReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FootballAnalyzer {

    /**
     * Gives team with smallest distance of goals received and scored from file
     *
     * @param filePath path to file containing football data to be analyzed
     * @return team with smallest distance
     */
    public static String findTeamWithSmallestDistance(String filePath) {
        try {
            List<String[]> csvRows = CsvReader.readCSV(filePath);
            List<Football> footballData = parseFootballData(csvRows);

            return footballData.stream()
                    .min(Comparator.comparingInt(Football::getGoalDistance))
                    .map(team -> String.valueOf(team.teamName()))
                    .orElse("N/A");
        } catch (IOException e) {
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
    private static List<Football> parseFootballData(List<String[]> rows) {
        List<Football> data = new ArrayList<>();
        for (String[] row : rows) {
            data.add(new Football(row[0], Integer.parseInt(row[5]), Integer.parseInt(row[6])));
        }
        return data;
    }
}
