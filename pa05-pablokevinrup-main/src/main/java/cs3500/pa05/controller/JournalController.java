package cs3500.pa05.controller;

import cs3500.pa05.model.BujoReader;
import java.io.IOException;

/**
 * represents a controller for a bullet journal
 */
public interface JournalController {

  /**
   * Initializes a bullet journal
   */
  void run();

  /**
   * loads file withing given bujo file reader
   *
   * @param bujoReader - contains file to load
   * @throws IOException if given file is invalid
   */
  void loadFile(BujoReader bujoReader) throws IOException;

  /**
   * initialises eventHandlers on view
   */
  void initView();
}
