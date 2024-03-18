package cs3500.pa05.model;

/**
 * represents an Event entry in journal
 */
public interface EventEntry extends JournalEntry {
  /**
   * getter for start field
   *
   * @return start field as String
   */
  String getStart();

  /**
   * getter for duration field
   *
   * @return duration field as String
   */
  String getDuration();

  /**
   * setter for start field
   *
   * @param start given value to set field to
   */
  void setStart(String start);

  /**
   * setter for duration field
   *
   * @param duration given duration to set field to
   */
  void setDuration(String duration);
}
