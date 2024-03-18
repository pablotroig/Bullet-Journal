package cs3500.pa05.view;

import cs3500.pa05.controller.JournalController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * repersents a journal view
 */
public class JournalViewImpl implements JournalView {
  private final FXMLLoader loader = new FXMLLoader();

  /**
   * creates a new instance of journal view
   *
   * @param controller - controller for journal appplication
   */
  public JournalViewImpl(JournalController controller) {
    this.loader.setLocation(getClass().getClassLoader().getResource("cs3500/pa05/weekview.fxml"));
    this.loader.setController(controller);
  }

  /**
   * @return Loads a scene from a Journal GUI layout.
   * @throws IllegalStateException if unable to load layout
   */
  @Override
  public Scene load() throws IllegalStateException {
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
