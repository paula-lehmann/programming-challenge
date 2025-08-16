package de.exxcellent.challenge.weather;

/**
 * Class defines weather data object structure with relevant fields
 */
public record Weather(int day, int maxTemp, int minTemp) {

    /**
     * Calculates the spread of temperature (difference between maximum & minimum temperature of the day)
     *
     * @return spread
     */
    public int getTempSpread() {
        return Math.abs(maxTemp - minTemp);
    }
}
