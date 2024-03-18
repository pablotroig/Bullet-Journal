package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

  private Event exampleEvent;

  @BeforeEach
  void setUp() {
    exampleEvent = new Event("Soccer game",
        "Regular season match", DayOfWeek.FRIDAY, "9:00", "90 minutes");
  }

  @Test
  void setStart() {
    String newStartTime = "10:00";
    exampleEvent.setStart(newStartTime);
    assertEquals(newStartTime, exampleEvent.getStart());
  }

  @Test
  void setDuration() {
    String duration = "100 minutes";
    exampleEvent.setDuration(duration);
    assertEquals(duration, exampleEvent.getDuration());
  }

  @Test
  void setName() {
    String name = "Soccer game and dinner";
    exampleEvent.setName(name);
    assertEquals(name, exampleEvent.getName());
  }

  @Test
  void setDescription() {
    String description = "Playing soccer game then getting dinner";
    exampleEvent.setDescription(description);
    assertEquals(description, exampleEvent.getDescription());
  }

  @Test
  void setDay() {
    exampleEvent.setDay(DayOfWeek.SATURDAY);
    assertEquals(DayOfWeek.SATURDAY, exampleEvent.getDay());
  }

  @Test
  void getDay() {
    DayOfWeek day = exampleEvent.getDay();
    assertEquals(DayOfWeek.FRIDAY, day);
  }

  @Test
  void getName() {
    String name = exampleEvent.getName();
    assertEquals("Soccer game", name);
  }

  @Test
  void getDescription() {
    String description = exampleEvent.getDescription();
    assertEquals("Regular season match", description);
  }

  @Test
  void getStart() {
    String start = exampleEvent.getStart();
    assertEquals("9:00", start);
  }

  @Test
  void getDuration() {
    String duration = exampleEvent.getDuration();
    assertEquals("90 minutes", duration);
  }

  @Test
  void testEquals() {
    Event event1 =
        new Event("Soccer game", "Regular season match", DayOfWeek.FRIDAY, "9:00", "90 minutes");
    Event event2 =
        new Event("Soccer game", "Regular season match", DayOfWeek.FRIDAY, "9:00", "90 minutes");
    Event event3 =
        new Event("Different game", "Regular season match", DayOfWeek.FRIDAY, "9:00", "90 minutes");
    Event event4 = new Event("Soccer game", "Diff", DayOfWeek.FRIDAY, "9:00", "90 minutes");
    assertNotEquals(event1, event3);
    assertNotEquals(event1, event4);
    Event event5 =
        new Event("Soccer game", "Regular season match", DayOfWeek.MONDAY, "9:00", "90 minutes");
    Event event6 =
        new Event("Soccer game", "Regular season match", DayOfWeek.MONDAY, "Diff", "90 minutes");
    assertEquals(event1, event2);
    assertNotEquals(event1, event5);
    assertNotEquals(event5, event6);
    assertNotEquals(event1, event6);
    Event event7 =
        new Event("Soccer game", "Regular season match", DayOfWeek.MONDAY, "9:00", "Diff");
    assertNotEquals(event1, event7);
    assertNotEquals(event5, event7);
    assertFalse(event1.equals("Test"));
  }

  @Test
  void testException() {
    assertThrows(NullPointerException.class, () -> {
      Event event =
          new Event(null, "Description", DayOfWeek.MONDAY, "10:00", "1 hour");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Event event
          = new Event("Event", "Description", null, "10:00", "1 hour");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Event event =
          new Event("Event", " Description", DayOfWeek.MONDAY, null, "1 hour");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Event event =
          new Event("Event", "Description", DayOfWeek.MONDAY, "10:00", null);
    });
  }
}