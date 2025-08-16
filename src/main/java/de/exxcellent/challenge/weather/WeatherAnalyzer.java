package de.exxcellent.challenge.weather;

import de.exxcellent.challenge.util.CsvReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WeatherAnalyzer {

    /**
     * Gives day with smallest temperature spread from  file
     *
     * @param filePath path to file containing weather data to be analyzed
     * @return day with smallest spread
     */
    public static String findDayWithSmallestSpread(String filePath) {
        try {
            List<Map<String, String>> csvRows = CsvReader.readCSV(filePath);
            List<Weather> weatherData = parseWeatherData(csvRows);

            return weatherData.stream()
                    .min(Comparator.comparingInt(Weather::getTempSpread))
                    .map(weather -> String.valueOf(weather.day()))
                    .orElse("N/A");
        } catch (IOException | IllegalArgumentException e) {
            System.out.printf("error while reading csv file: %s%n", e.getMessage());
            return "N/A";
        }
    }

    /**
     * converts data to weather type
     *
     * @param rows List of String containing info of weather data
     * @return List of weather data with day, min and max temp
     */
    private static List<Weather> parseWeatherData(List<Map<String, String>> rows) throws IllegalArgumentException {
        List<Weather> data = new ArrayList<>();
        for (Map<String, String> row : rows) {
            if (!row.containsKey("Day") || !row.containsKey("MxT") || !row.containsKey("MnT")) {
                throw new IllegalArgumentException("data is missing required keys: " + row.keySet());
            }
            try {
                data.add(new Weather(Integer.parseInt(row.get("Day")), Integer.parseInt(row.get("MxT")), Integer.parseInt(row.get("MnT"))));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format, " + e);
            }
        }
        return data;
    }
}
