package cs3500.pa05.model;

/**
 * interface methods for a journal ENTRY (EVENT OR TASK
 */
public interface JournalEntry {
  /**
   * getter for day field
   *
   * @return day as DayOfWeek
   */
  DayOfWeek getDay();

  /**
   * getter for name field
   *
   * @return name as String
   */
  String getName();

  /**
   * getter for description field
   *
   * @return description as String
   */
  String getDescription();

  /**
   * sets name field to given name
   *
   * @param name name to set field too
   */
  void setName(String name);

  /**
   * sets description field to given description
   *
   * @param description description to set to
   */
  void setDescription(String description);

  /**
   * sets day field to given field
   *
   * @param day given day to set field to
   */
  void setDay(DayOfWeek day);
}
