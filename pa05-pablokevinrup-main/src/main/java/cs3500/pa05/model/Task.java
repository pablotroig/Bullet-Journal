package cs3500.pa05.model;


import java.util.Objects;

/**
 * represents a task in bullet journal
 */
public class Task implements TaskEntry {
  private boolean complete;
  private String name;
  private String description;
  private DayOfWeek day;

  /**
   * creates a new instance of task
   *
   * @param name - name of task
   * @param description - description of task (optional)
   * @param day - day of task
   * @param complete - complete or incomplete
   */
  public Task(String name, String description, DayOfWeek day, boolean complete) {
    if (name.equals("") || Objects.isNull(day)) {
      throw new IllegalArgumentException("Missing one or more required inputs");
    } else {
      this.name = name;
      this.description = description;
      this.day = day;
      this.complete = complete;
    }
  }

  //don't delete

  /**
   * creates a new instance of Task for Json
   */
  public Task() {}

  /**
   * determines if this task is complete
   *
   * @return true if complete, false if not
   */
  @Override
  public boolean isComplete() {
    return this.complete;
  }

  /**
   * getter for day field
   *
   * @return the day of the task
   */
  @Override
  public DayOfWeek getDay() {
    return this.day;
  }

  /**
   * getter for name field
   *
   * @return the name of a task
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * getter for description field
   *
   * @return the description of a task
   */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the name field to the passed in String
   *
   * @param name what the field is being set to
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the description field to the passed in String
   *
   * @param description what the field is being set to
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets the day field to the passed DayOfWeek
   *
   * @param day what the field is being set to
   */
  public void setDay(DayOfWeek day) {
    this.day = day;
  }

  /**
   * Sets the complete field to the passed boolean
   *
   * @param complete what the complete field is being changed to
   */
  public void setComplete(boolean complete) {
    this.complete = complete;
  }

  /**
   * determines if this task is equal to given object
   *
   * @param o is the object we are checking if equal to this
   * @return true if the objects are the same
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Task) {
      Task t = (Task) o;
      return Objects.equals(this.name, t.name)
          && Objects.equals(this.description, t.description)
          && Objects.equals(this.day, t.day)
          && Objects.equals(this.complete, t.complete);
    } else {
      return false;
    }
  }
}