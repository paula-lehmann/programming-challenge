package de.exxcellent.challenge;

import de.exxcellent.challenge.football.FootballAnalyzer;
import de.exxcellent.challenge.weather.WeatherAnalyzer;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     *
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        String dayWithSmallestTempSpread = WeatherAnalyzer.findDayWithSmallestSpread("src/main/resources/de/exxcellent/challenge/weather.csv");
        System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);

        String teamWithSmallestGoalSpread = FootballAnalyzer.findTeamWithSmallestDistance("src/main/resources/de/exxcellent/challenge/football.csv");
        System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);
    }
}
