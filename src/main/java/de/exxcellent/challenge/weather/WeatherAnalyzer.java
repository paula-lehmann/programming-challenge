package de.exxcellent.challenge.weather;

import de.exxcellent.challenge.util.CsvReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WeatherAnalyzer {

    /**
     * Gives day with smallest temperature spread from  file
     *
     * @param filePath path to file containing weather data to be analyzed
     * @return day with smallest spread
     */
    public static String findDayWithSmallestSpread(String filePath) {
        try {
            List<String[]> csvRows = CsvReader.readCSV(filePath);
            List<Weather> weatherData = parseWeatherData(csvRows);

            return weatherData.stream()
                    .min(Comparator.comparingInt(Weather::getTempSpread))
                    .map(weather -> String.valueOf(weather.day()))
                    .orElse("N/A");
        } catch (IOException e) {
            System.out.printf("error while reading csv file: %s", e.getMessage());
            return "N/A";
        }
    }

    /**
     * converts data to weather type
     *
     * @param rows List of String containing info of weather data
     * @return List of weather data with day, min and max temp
     */
    private static List<Weather> parseWeatherData(List<String[]> rows) {
        List<Weather> data = new ArrayList<>();
        for (String[] row : rows) {
            data.add(new Weather(Integer.parseInt(row[0]), Integer.parseInt(row[1]), Integer.parseInt(row[2])));
        }
        return data;
    }
}
