package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a task in JSON
 *
 * @param day day of the week
 * @param name name of the task
 * @param description description of the task
 * @param complete whether task is finished
 */
public record TaskJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("day") DayOfWeek day,
    @JsonProperty("complete") boolean complete
) {}
