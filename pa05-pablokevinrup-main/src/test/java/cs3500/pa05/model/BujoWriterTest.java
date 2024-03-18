package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * class testing bujowriter
 */
public class BujoWriterTest {
  BujoWriter bujoWriter;
  BujoReader reader;
  File file;
  List<EventEntry> events;
  List<TaskEntry> tasks;
  String quote;
  String title;
  int maxEvents;
  int maxTasks;
  DayOfWeek day;

  /**
   * initializes bujowriter, bujowriter, and all nesscary parameters for testing
   *
   * @throws IOException for invalid inputs
   */
  @BeforeEach
  public void setup() throws IOException {
    file = new File("test.bujo");
    day = DayOfWeek.SUNDAY;
    bujoWriter = new BujoWriter(file);
    reader = new BujoReader(file);
    events = new ArrayList<>();
    tasks = new ArrayList<>();
    quote = "yeah";
    title = "example title";
    maxEvents = 10;
    maxTasks = 10;
    bujoWriter.writeEntries(events, tasks, quote, title, maxEvents, maxTasks, day);
  }

  /**
   * test writeentries if the correct amount of events have been parsed
   *
   * @throws IOException for invalid file
   */
  @Test
  public void testWriteEntriesEvents() throws IOException {
    assertEquals(0, reader.parseEvents().size());
  }

  /**
   * test writeentries if the correct amount of tasks have been parsed
   *
   * @throws IOException for invalid file
   */
  @Test
  public void testWriteEntriesTasks() throws IOException {
    assertEquals(0, reader.parseTasks().size());
  }

  /**
   * test if quote is parsed right
   *
   * @throws IOException for invalid file
   */
  @Test
  public void testWriteEntriesQuote() throws IOException {
    assertEquals("yeah\n", reader.parseQuote());
  }

  /**
   * test if title is parsed right
   *
   * @throws IOException for invalid file
   */
  @Test
  public void testWriteEntriesTitle() throws IOException {
    assertEquals("example title", reader.parseTitle());
  }

  /**
   * tests if max events is parsed right
   *
   * @throws IOException for invalid file
   */
  @Test
  public void testWriteEntriesMaxEvents() throws IOException {
    assertEquals(10, reader.parseMaxEvents());
  }

  /**
   * tests if max tasks is parsed right
   *
   * @throws IOException for invalid file
   */
  @Test
  public void testWriteEntriesMaxTasks() throws IOException {
    assertEquals(10, reader.parseMaxTasks());
  }

  /**
   * tests if the starting day is parsed right
   *
   * @throws IOException for invalid file
   */
  @Test
  public void testWriteEntriesStartWeek() throws IOException {
    assertEquals(DayOfWeek.SUNDAY, reader.parseStartWeek());
  }

}
