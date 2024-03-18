package cs3500.pa05.model;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a Bujo file writer
 * - parses java objects to JSON and saves in Bujo file
 */
public class BujoWriter {
  private final ObjectMapper mapper = new ObjectMapper();
  private final File fileToWrite;

  /**
   * creates a new instance of BujoWriter
   *
   * @param fileToWrite - given file to save parsed JSON to
   */
  public BujoWriter(File fileToWrite) {
    this.fileToWrite = fileToWrite;
  }

  /**
   * Parses given events and tasks to respective JSON records and writes them to file
   *
   * @param events    events to save
   * @param tasks     tasks to save
   * @param quote     quote to save
   * @param maxEvents max number of events from user
   * @param maxTasks  max number of tasks from user
   * @param title     title to save
   * @throws IOException if given file is invalid
   */
  public void writeEntries(List<EventEntry> events, List<TaskEntry> tasks, String quote,
                           String title, int maxEvents, int maxTasks, DayOfWeek weekStart)
      throws IOException {
    new FileWriter(this.fileToWrite, false).close();
    WeekJson weekJson
        = new WeekJson(weekStart, title, quote, maxTasks, maxEvents, new ArrayList<>(),
            new ArrayList<>());

    for (TaskEntry t : tasks) {
      TaskJson taskJson = mapper.convertValue(t, TaskJson.class);
      weekJson.tasks().add(taskJson);
    }

    for (EventEntry e : events) {
      EventJson eventJson = mapper.convertValue(e, EventJson.class);
      weekJson.events().add(eventJson);
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWrite, true))) {
      ObjectWriter objectWriter = mapper.writer(new DefaultPrettyPrinter());


      writer.write("weekStart: \n");
      writer.write(weekStart.toString());
      writer.newLine();
      writer.write("title: \n");
      writer.write(title);
      writer.newLine();
      writer.write("quote: \n");
      writer.write(quote);
      writer.newLine();
      writer.write("max tasks: \n");
      writer.write(objectWriter.writeValueAsString(maxTasks));
      writer.newLine();
      writer.write("max events: \n");
      writer.write(objectWriter.writeValueAsString(maxEvents));
      writer.newLine();
      writer.write("tasks: \n");
      for (TaskJson task : weekJson.tasks()) {
        writer.write(objectWriter.writeValueAsString(task));
        writer.newLine();
      }
      writer.write("event: \n");
      for (EventJson event : weekJson.events()) {
        writer.write(objectWriter.writeValueAsString(event));
        writer.newLine();
      }
    }
  }
}
