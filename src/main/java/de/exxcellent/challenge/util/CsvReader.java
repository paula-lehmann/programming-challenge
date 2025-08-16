package de.exxcellent.challenge.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    /**
     * Reads csv file and gives content
     *
     * @param path of file to be read
     * @return list of map of file content, map contains name (from header) and value of each data point
     * @throws IOException on error
     */
    public static List<Map<String, String>> readCSV(String path) throws IOException {
        List<Map<String, String>> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String header = br.readLine();
            if (header == null) {
                //file is empty
                return rows;
            }
            String[] headerNames = header.split(",");
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> rowMap = new HashMap<>();
                for (int i = 0; i < headerNames.length && i < values.length; i++) {
                    rowMap.put(headerNames[i], values[i]);
                }
                rows.add(rowMap);
            }
        }
        return rows;
    }
}
