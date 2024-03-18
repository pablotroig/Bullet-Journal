package cs3500.pa05;

import cs3500.pa05.controller.JournalController;
import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.view.JournalView;
import cs3500.pa05.view.JournalViewImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main Driver class for application
 */
public class Driver extends Application {
  /**
   * starts the GUI for a bullet journal
   *
   * @param stage the primary stage for this application to add elements to
   */
  @Override
  public void start(Stage stage) {
    JournalController journalController = new JournalControllerImpl();
    JournalView journalView = new JournalViewImpl(journalController);

    try {
      stage.setScene(journalView.load());
      stage.show();
      journalController.run();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }
  }

  /**
   * Entry point for a game of Whack-a-Mole.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch();
  }
}
