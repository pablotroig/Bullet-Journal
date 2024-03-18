package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represent a Week in JSON
 *
 * @param tasks  the list of tasks for the week
 * @param events the list of events for the week
 */
public record WeekJson(
    @JsonProperty("weekStart") DayOfWeek day,
    @JsonProperty("title") String title,
    @JsonProperty("quote") String quote,
    @JsonProperty("max tasks") int maxTasks,
    @JsonProperty("max events") int maxEvents,
    @JsonProperty("task") List<TaskJson> tasks,
    @JsonProperty("events") List<EventJson> events
) {
}
