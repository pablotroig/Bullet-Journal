package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an event in JSON
 *
 * @param day day of the week
 * @param name name of the event
 * @param description description of the event
 * @param start start time of the event
 * @param duration how long the event is
 */
public record EventJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("day") DayOfWeek day,
    @JsonProperty("start") String start,
    @JsonProperty("duration") String duration
) {
}