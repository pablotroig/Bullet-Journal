package cs3500.pa05.model;

import java.util.Objects;

/**
 * Represents an event in a journal entry
 */
public class Event implements EventEntry {
  private String start;
  private String duration;
  private String name;
  private String description;
  private DayOfWeek day;

  /**
   * Creates a new instance of an event
   *
   * @param name name of the event
   * @param description description of the event
   * @param day day of the event
   * @param start start time of the event
   * @param duration duration of an event
   */
  public Event(String name, String description, DayOfWeek day, String start, String duration) {
    if (name.equals("") || Objects.isNull(day)
        || Objects.isNull(start) || Objects.isNull(duration)) {
      throw new IllegalArgumentException("Missing one or more required inputs");
    } else {
      this.name = name;
      this.description = description;
      this.day = day;
      this.start = start;
      this.duration = duration;
    }
  }

  /**
   * creates a new instance of Event for Json
   */
  //don't delete
  public Event() {
  }

  //don't delete setters (important for json)

  /**
   * setter for start field
   *
   * @param start value to set field to
   */
  public void setStart(String start) {
    this.start = start;
  }

  /**
   * setter for duration field
   *
   * @param duration value to set field to
   */
  public void setDuration(String duration) {
    this.duration = duration;
  }

  /**
   * setter for name field
   *
   * @param name name to set field too
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * setter for description field
   *
   * @param description description to set to
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * setter for day field
   *
   * @param day given day to set field to
   */
  public void setDay(DayOfWeek day) {
    this.day = day;
  }

  /**
   * getter for day field
   *
   * @return day field as DayOfWeek
   */
  public DayOfWeek getDay() {
    return this.day;
  }

  /**
   * getter for name field
   *
   * @return name field as String
   */
  public String getName() {
    return this.name;
  }

  /**
   * getter for description field
   *
   * @return description field as String
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * getter for start method
   *
   * @return start method as String
   */
  public String getStart() {
    return this.start;
  }

  /**
   * getter for duration method
   *
   * @return duration as String
   */
  public String getDuration() {
    return this.duration;
  }

  /**
   * determines if given object is the same as this
   *
   * @param o obejct to check if equal to this
   * @return true if equal, false if not
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Event) {
      Event e = (Event) o;
      return Objects.equals(this.name, e.name)
          && Objects.equals(this.description, e.description)
          && Objects.equals(this.day, e.day)
          && Objects.equals(this.start, e.start)
          && Objects.equals(this.duration, e.duration);
    } else {
      return false;
    }
  }

}
