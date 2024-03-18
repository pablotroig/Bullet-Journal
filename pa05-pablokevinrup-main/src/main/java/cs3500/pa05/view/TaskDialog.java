package cs3500.pa05.view;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.TaskEntry;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * represent a dialog for creating a task
 */
public class TaskDialog extends Dialog {
  private final TextField nameField;
  private final ComboBox<DayOfWeek> dayField;
  private final ComboBox<String> completeField;
  private final HBox complete;
  private final TextField descriptionField;

  /**
   * creates new instance of TaskDialog
   */
  public TaskDialog() {
    completeField = new ComboBox<>();
    completeField.getItems().addAll("No", "Yes");
    completeField.setValue("No");
    complete = new HBox(new Label("Completed: "), completeField);
    complete.setVisible(false);
    dayField = new ComboBox<>();
    dayField.getItems().addAll(DayOfWeek.values());
    HBox day = new HBox(new Label("Day: "), dayField);
    descriptionField = new TextField();
    HBox description = new HBox(new Label("Description: "), descriptionField);
    nameField = new TextField();
    HBox name = new HBox(new Label("Name: "), nameField);
    VBox vbox = new VBox(name, day, description, complete);
    vbox.setPadding(new Insets(0, 0, 0, 0));
    this.setTitle("Add Event");
    this.getDialogPane().setContent(vbox);
  }

  /**
   * getter for name field
   *
   * @return name field as String
   */
  public String getName() {
    return nameField.getText();
  }

  /**
   * getter for day field
   *
   * @return day field as DayOfWeek
   */
  public DayOfWeek getDay() {
    return dayField.getValue();
  }

  /**
   * getter for description field
   *
   * @return description field as String
   */
  public String getDescription() {
    return descriptionField.getText();
  }

  /**
   * getter for complete field
   *
   * @return complete field as boolean
   */
  public boolean getComplete() {
    return !completeField.getValue().equals("No");
  }

  /**
   * sets values of input fields to given entries values
   *
   * @param taskEntry information to set fields to
   */
  public void setValues(TaskEntry taskEntry) {
    complete.setVisible(true);
    nameField.setText(taskEntry.getName());
    dayField.setValue(taskEntry.getDay());
    descriptionField.setText(taskEntry.getDescription());
    if (taskEntry.isComplete()) {
      completeField.setValue("Yes");
    } else {
      completeField.setValue("No");
    }
  }
}
