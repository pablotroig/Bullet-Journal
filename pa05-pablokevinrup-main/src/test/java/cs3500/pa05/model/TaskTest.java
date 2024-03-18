package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {

  private Task task;

  @BeforeEach
  void setUp() {
    task = new Task("Finish homework", "Complete cs hw for the day", DayOfWeek.MONDAY, false);
  }

  @Test
  void isComplete() {
    assertFalse(task.isComplete());
  }

  @Test
  void getDay() {
    DayOfWeek day = task.getDay();
    assertEquals(DayOfWeek.MONDAY, day);
  }

  @Test
  void getName() {
    String name = task.getName();
    assertEquals("Finish homework", name);
  }

  @Test
  void getDescription() {
    String description = task.getDescription();
    assertEquals("Complete cs hw for the day", description);
  }

  @Test
  void setName() {
    String newName = "Start cs hw";
    task.setName(newName);
    assertEquals(newName, task.getName());
  }

  @Test
  void setDescription() {
    String newDescr = "Start the cs hw";
    task.setDescription(newDescr);
    assertEquals(newDescr, task.getDescription());
  }

  @Test
  void setDay() {
    task.setDay(DayOfWeek.TUESDAY);
    assertEquals(DayOfWeek.TUESDAY, task.getDay());
  }

  @Test
  void setComplete() {
    task.setComplete(true);
    assertEquals(true, task.isComplete());
    task.setComplete(false);
    assertEquals(false, task.isComplete());
  }

  @Test
  void testEquals() {
    Task taskCopy
        = new Task("Finish homework", "Complete cs hw for the day", DayOfWeek.MONDAY, false);
    assertTrue(task.equals(taskCopy));
    Task task1 = new Task("Same", "Same", DayOfWeek.MONDAY, true);
    Task task2 = new Task("diff", "Same", DayOfWeek.MONDAY, true);
    assertNotEquals(task1, task2);
    Task task3 = new Task("Same", "diff", DayOfWeek.MONDAY, true);
    assertNotEquals(task1, task3);
    Task task4 = new Task("Same", "Same", DayOfWeek.TUESDAY, true);
    assertNotEquals(task1, task4);
    Task task5 = new Task("Same", "Same", DayOfWeek.MONDAY, false);

    assertNotEquals(task1, task5);
    assertFalse(task1.equals("Test"));
  }

  @Test
  void testException() {
    assertThrows(NullPointerException.class, () -> {
      Task task =
          new Task(null, "desc", DayOfWeek.TUESDAY, false);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      Task task =
          new Task("test", "desc", null, false);
    });
  }
}