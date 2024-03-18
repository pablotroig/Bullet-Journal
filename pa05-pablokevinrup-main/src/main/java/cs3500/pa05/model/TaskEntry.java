package cs3500.pa05.model;

/**
 * represents a TaskEntry in Journal
 */
public interface TaskEntry extends JournalEntry {
  /**
   * determines if taskEntry is complete
   *
   * @return true if complete, false if not.
   */
  boolean isComplete();

  /**
   * Sets the complete field to the passed boolean
   *
   * @param complete what the complete field is being changed to
   */
  void setComplete(boolean complete);
}
