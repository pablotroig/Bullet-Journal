package cs3500.pa05.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * represents a Bujo file reader
 * - Parses JSON to java object
 */
public class BujoReader {
  private final ObjectMapper mapper = new ObjectMapper();
  private final File fileToRead;

  /**
   * creates a new instance of BujoReader
   *
   * @param f - file to read
   */
  public BujoReader(File f) {
    this.fileToRead = f;
  }

  /**
   * Parses Bujo JSON events to java event objects
   *
   * @return Bujo JSON events as list of java events
   * @throws IOException if given file in invalid
   */
  public List<EventEntry> parseEvents() throws IOException {
    Scanner s = new Scanner(this.fileToRead);
    List<EventJson> eventJsons = new ArrayList<>();
    List<EventEntry> events = new ArrayList<>();
    while (s.hasNext()) {
      String next = s.nextLine();
      if (next.equals("event: ")) {
        while (s.hasNext()) {
          StringBuilder event = new StringBuilder();
          event.append(s.nextLine());
          event.append(s.nextLine());
          event.append(s.nextLine());
          event.append(s.nextLine());
          event.append(s.nextLine());
          event.append(s.nextLine());
          event.append(s.nextLine());
          eventJsons.add(this.mapper.readValue(event.toString(), EventJson.class));
        }
      }
    }
    for (EventJson e : eventJsons) {
      events.add(this.mapper.convertValue(e, Event.class));
    }
    System.out.println(events);
    return events;
  }

  /**
   * Parses Bujo JSON tasks to java task objects
   *
   * @return Bujo JSON tasks as list of java tasks
   * @throws IOException if given file in invalid
   */
  public List<TaskEntry> parseTasks() throws IOException {
    Scanner s = new Scanner(this.fileToRead);
    List<TaskJson> taskJsons = new ArrayList<>();
    List<TaskEntry> tasks = new ArrayList<>();
    while (s.hasNext()) {
      String next = s.nextLine();
      if (next.equals("tasks: ")) {
        next = s.nextLine();
        while (!next.equals("event: ")) {
          StringBuilder task = new StringBuilder();
          task.append(next);
          task.append(s.nextLine());
          task.append(s.nextLine());
          task.append(s.nextLine());
          task.append(s.nextLine());
          task.append(s.nextLine());
          next = s.nextLine();
          taskJsons.add(this.mapper.readValue(task.toString(), TaskJson.class));
        }
      }
    }
    for (TaskJson t : taskJsons) {
      tasks.add(this.mapper.convertValue(t, Task.class));
    }
    System.out.println(tasks);
    return tasks;
  }

  /**
   * parses Bujo JSON quote to String
   *
   * @return Bujo quote as String
   * @throws IOException if given file is invalid
   */
  public String parseQuote() throws IOException {
    Scanner s = new Scanner(this.fileToRead);
    StringBuilder quote = new StringBuilder();
    while (s.hasNext()) {
      String next = s.nextLine();
      if (next.equals("quote: ")) {
        while (s.hasNext()) {
          next = s.nextLine();
          if (next.equals("max tasks: ")) {
            break;
          }
          quote.append(next);
          quote.append("\n");
        }
        break;
      }
    }
    return quote.toString();
  }

  /**
   * parses the saved value of max events from bujo file
   *
   * @return value of max events as int
   * @throws IOException if given file is invalid
   */
  public int parseMaxEvents() throws IOException {
    Scanner s = new Scanner(this.fileToRead);
    int maxEvents = 0;
    while (s.hasNext()) {
      String next = s.nextLine();
      if (next.equals("max events: ")) {
        maxEvents = Integer.parseInt(s.nextLine().trim());
        break;
      }
    }
    return maxEvents;
  }

  /**
   * parses the saved value of max tasks from bujo file
   *
   * @return value of max tasks as int
   * @throws IOException if given file is invalid
   */
  public int parseMaxTasks() throws IOException {
    Scanner s = new Scanner(this.fileToRead);
    int maxTasks = 0;
    while (s.hasNext()) {
      String next = s.nextLine();
      if (next.equals("max tasks: ")) {
        maxTasks = Integer.parseInt(s.nextLine().trim());
        break;
      }
    }
    return maxTasks;
  }

  /**
   * parses title of weekJson to String
   *
   * @return title property of week record
   * @throws IOException in file is invalid
   */
  public String parseTitle() throws IOException {
    Scanner s = new Scanner(this.fileToRead);
    String title = "";
    while (s.hasNext()) {
      String next = s.nextLine();
      if (next.equals("title: ")) {
        title = s.nextLine();
      }
    }
    return title;
  }

  /**
   * parses day of week in weekjson
   *
   * @return weekstart property of week record
   * @throws IOException in file is invalid
   */
  public DayOfWeek parseStartWeek() throws IOException {
    Scanner s = new Scanner(this.fileToRead);
    String dayOfWeek = "";
    while (s.hasNext()) {
      String next = s.nextLine();
      if (next.equals("weekStart: ")) {
        dayOfWeek = s.nextLine();
      }
    }
    return DayOfWeek.valueOf(dayOfWeek);
  }
}
