package de.exxcellent.challenge.football;

/**
 * Class defines football data object structure with relevant fields
 */
public record Football(String teamName, int goals, int goalsAllowed) {
    /**
     * Calculates distance between scored goals and received goals against team
     *
     * @return distance
     */
    public int getGoalDistance() {
        return Math.abs(goals - goalsAllowed);
    }
}
