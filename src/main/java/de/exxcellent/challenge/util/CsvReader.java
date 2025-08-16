package de.exxcellent.challenge.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    /**
     * Reads csv file and gives content, removing file header
     *
     * @param path of file to be read
     * @return list of file content
     * @throws IOException
     */
    public static List<String[]> readCSV(String path) throws IOException {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String header = br.readLine();
            if (header == null) {
                //file is empty
                return rows;
            }
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        }
        return rows;
    }
}
