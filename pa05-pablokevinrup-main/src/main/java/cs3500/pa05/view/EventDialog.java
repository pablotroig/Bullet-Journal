package cs3500.pa05.view;

import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.EventEntry;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * represents a dialog for users to create event
 */
public class EventDialog extends Dialog {
  private final TextField nameField;
  private final ComboBox<DayOfWeek> dayField;
  private final ComboBox<String> comboBoxTimeHours;
  private final ComboBox<String> comboBoxTimeMins;
  private final ComboBox<String> amOrPm;
  private final ComboBox<String> durHours;
  private final ComboBox<String> durMins;
  private final TextField descriptionField;

  /**
   * creates a new instance of EventDialog
   */
  public EventDialog() {
    comboBoxTimeHours = new ComboBox<>();
    comboBoxTimeMins = new ComboBox<>();
    comboBoxTimeHours.setValue("1");
    comboBoxTimeMins.setValue("00");
    comboBoxTimeHours.setVisibleRowCount(10);
    comboBoxTimeMins.setVisibleRowCount(10);
    amOrPm = new ComboBox<>();
    amOrPm.setValue("AM");
    durHours = new ComboBox<>();
    durHours.setValue("0");
    durHours.setVisibleRowCount(10);
    durMins = new ComboBox<>();
    durMins.setValue("0");
    durMins.setVisibleRowCount(10);
    populateChoices();
    nameField = new TextField();
    dayField = new ComboBox<>();
    dayField.getItems().addAll(DayOfWeek.values());
    HBox day = new HBox(new Label("Day: "), dayField);
    descriptionField = new TextField();
    HBox description = new HBox(new Label("Description: "), descriptionField);
    HBox startTime = new HBox(new Label("Start Time: "),
        comboBoxTimeHours, new Label(" : "), comboBoxTimeMins, amOrPm);
    HBox duration = new HBox(new Label("Duration: "),
        durHours, new Label("Hours"), durMins, new Label("Mins"));
    HBox name = new HBox(new Label("Name: "), nameField);
    VBox vbox = new VBox(name, day, description, startTime, duration);
    vbox.setPadding(new Insets(0, 0, 0, 0));
    this.setTitle("Add Event");
    this.getDialogPane().setContent(vbox);
  }

  /**
   * populates choiceBox and comboBox with values
   */
  private void populateChoices() {
    amOrPm.getItems().add("AM");
    amOrPm.getItems().add("PM");
    for (int i = 1; i <= 12; i++) {
      comboBoxTimeHours.getItems().add(Integer.toString(i));
    }
    for (int i = 0; i < 60; i++) {
      if (i < 10) {
        comboBoxTimeMins.getItems().add("0" + i);
      } else {
        comboBoxTimeMins.getItems().add(Integer.toString(i));
      }
      durMins.getItems().add(Integer.toString(i));
    }
    for (int i = 0; i < 24; i++) {
      durHours.getItems().add(Integer.toString(i));
    }
  }

  /**
   * getter for name input
   *
   * @return name input as String
   */
  public String getName() {
    return nameField.getText();
  }

  /**
   * getter for day input
   *
   * @return day input as DayOfWeek
   */
  public DayOfWeek getDay() {
    return dayField.getValue();
  }

  /**
   * getter for description input
   *
   * @return description input as String
   */
  public String getDescription() {
    return descriptionField.getText();
  }

  /**
   * getter for hour input
   *
   * @return hour input as String
   */
  public String getHour() {
    return comboBoxTimeHours.getValue();
  }

  /**
   * getter for minute input
   *
   * @return minute input as String
   */
  public String getMin() {
    return comboBoxTimeMins.getValue();
  }

  /**
   * getter for AM or PM input
   *
   * @return AM or PM input as String
   */
  public String getAmPm() {
    return amOrPm.getValue();
  }

  /**
   * getter for duration hour input
   *
   * @return duration hour input as String
   */
  public String getDurHour() {
    return durHours.getValue();
  }

  /**
   * getter for duration minute input
   *
   * @return duration minute input as String
   */
  public String getDurMin() {
    return durMins.getValue();
  }

  /**
   * sets all field to values of given event
   *
   * @param eventEntry event to set all fields values to
   */
  public void setValues(EventEntry eventEntry) {
    nameField.setText(eventEntry.getName());
    dayField.setValue(eventEntry.getDay());
    descriptionField.setText(eventEntry.getDescription());
    comboBoxTimeHours.setValue(eventEntry.getStart().substring(0,
        eventEntry.getStart().indexOf(":")));
    comboBoxTimeMins.setValue(eventEntry.getStart().substring(
        eventEntry.getStart().indexOf(":") + 1,
        eventEntry.getStart().length() - 2));
    amOrPm.setValue(eventEntry.getStart().substring(
        eventEntry.getStart().indexOf(":") + 3));
    durHours.setValue(eventEntry.getDuration().substring(0,
        eventEntry.getDuration().indexOf("Hours") - 1));
    durMins.setValue(eventEntry.getDuration().substring(
        eventEntry.getDuration().indexOf("Hours") + 6,
        eventEntry.getDuration().indexOf("Minutes") - 1));
  }
}
