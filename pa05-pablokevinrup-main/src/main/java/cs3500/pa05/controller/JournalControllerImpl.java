package cs3500.pa05.controller;

import cs3500.pa05.model.BujoReader;
import cs3500.pa05.model.BujoWriter;
import cs3500.pa05.model.DayOfWeek;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.EventEntry;
import cs3500.pa05.model.JournalEntry;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.TaskEntry;
import cs3500.pa05.view.EventDialog;
import cs3500.pa05.view.JournalViewImpl;
import cs3500.pa05.view.TaskDialog;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * represents a controller for journal application
 */
public class JournalControllerImpl implements JournalController {
  private final List<EventEntry> events;
  private final List<TaskEntry> tasks;
  private final Map<DayOfWeek, Integer> numEventPerDay;
  private final Map<DayOfWeek, Integer> numTasksPerDay;
  @FXML
  private TextArea quote;
  @FXML
  private TextField title;
  @FXML
  private MenuItem newTask;
  @FXML
  private MenuItem taskQueue;
  @FXML
  private MenuItem save;
  @FXML
  private MenuItem open;
  @FXML
  private MenuItem newWeek;
  @FXML
  private MenuItem setMax;
  @FXML
  private MenuItem showStats;
  @FXML
  private MenuItem weekStart;
  @FXML
  private MenuItem newEvent;
  @FXML
  private AnchorPane sidebar;
  @FXML
  private AnchorPane hiddenPane;
  @FXML
  private AnchorPane taskPane;
  @FXML
  private AnchorPane sundayTasks;
  @FXML
  private AnchorPane mondayTasks;
  @FXML
  private AnchorPane tuesdayTasks;
  @FXML
  private AnchorPane wednesdayTasks;
  @FXML
  private AnchorPane thursdayTasks;
  @FXML
  private AnchorPane fridayTasks;
  @FXML
  private AnchorPane saturdayTasks;
  @FXML
  private AnchorPane sundayEvents;
  @FXML
  private AnchorPane mondayEvents;
  @FXML
  private AnchorPane tuesdayEvents;
  @FXML
  private AnchorPane wednesdayEvents;
  @FXML
  private AnchorPane thursdayEvents;
  @FXML
  private AnchorPane fridayEvents;
  @FXML
  private AnchorPane saturdayEvents;
  @FXML
  private HBox week;
  private int eventsPerDayMax;
  private int tasksPerDayMax;
  private DayOfWeek initialDay;
  private DayOfWeek chosenDay;

  /**
   * creates a new instance of JournalControllerImpl
   */
  public JournalControllerImpl() {
    this.events = new ArrayList<>();
    this.tasks = new ArrayList<>();
    this.numEventPerDay = initEntriesPerDay();
    this.numTasksPerDay = initEntriesPerDay();
    this.eventsPerDayMax = 10;
    this.tasksPerDayMax = 10;
    this.initialDay = DayOfWeek.SUNDAY;
    this.chosenDay = DayOfWeek.SUNDAY;
  }

  /**
   * calculates percentage of tasks completed
   *
   * @return percentage of tasks completed as double
   */
  private double calculateTaskCompletion() {
    if (tasks.size() == 0) {
      return 0;
    }
    int numTasksCompleted = 0;
    for (TaskEntry t : tasks) {
      if (t.isComplete()) {
        numTasksCompleted++;
      }
    }
    return 100 * (double) numTasksCompleted / tasks.size();
  }

  /**
   * inializes the hashmaps to contain all days of the week and 0 events/tasks
   *
   * @return hashmap linking number of tasks/events to days
   */
  private Map<DayOfWeek, Integer> initEntriesPerDay() {
    HashMap<DayOfWeek, Integer> map = new HashMap<>();
    for (DayOfWeek day : DayOfWeek.values()) {
      map.put(day, 0);
    }
    return map;
  }

  /**
   * calls the view and also asks user if they want to load a file
   */
  @FXML
  public void run() {
    this.initView();
    this.loadDialog();
  }

  /**
   * intializes all event handlers for buttons, the sidebar, dialogs, etc
   */
  @FXML
  public void initView() {
    newEvent.setOnAction(event -> eventDialog());
    newTask.setOnAction(event -> taskDialog());
    setMax.setOnAction(event -> maxDialog());
    showStats.setOnAction(event -> showStatsDialog());
    newWeek.setOnAction(event -> openNewWeek());
    hiddenPane.setVisible(false);
    sidebar.setLayoutX(-600);
    taskQueue.setOnAction(event -> expandTaskQueue());
    save.setOnAction(event -> saveFile());
    open.setOnAction(event -> loadDialog());
    weekStart.setOnAction(event -> weekStartDialog());
    formatWeekStart(chosenDay);
  }

  /**
   * handles building dialog for userinput for week start
   */
  @FXML
  private void weekStartDialog() {
    GridPane gridPane = new GridPane();
    ChoiceBox<DayOfWeek> choiceBox = new ChoiceBox<>();
    gridPane.add(choiceBox, 0, 1);
    gridPane.add(new Label("Week Start:"), 0, 0);
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
    Button apply = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
    apply.setOnAction(event -> formatWeekStart(choiceBox.getValue()));
    dialog.getDialogPane().setContent(gridPane);
    choiceBox.getItems().addAll(DayOfWeek.values());
    choiceBox.setValue(this.initialDay);
    dialog.showAndWait();
  }

  /**
   * formats week start according to given selected day
   *
   * @param selectedDay day to start week from
   */
  @FXML
  private void formatWeekStart(DayOfWeek selectedDay) {
    this.chosenDay = selectedDay;
    for (int i = 0; i < DayOfWeek.values().length; i++) {
      if (DayOfWeek.values()[i].equals(this.initialDay)) {
        while (!DayOfWeek.values()[i % 7].equals(chosenDay)) {
          Node first = week.getChildren().get(0);
          week.getChildren().remove(first);
          week.getChildren().add(first);
          i++;
        }
        this.initialDay = chosenDay;
      }
    }
  }

  /**
   * opens a new week gui
   */
  @FXML
  private void openNewWeek() {
    Stage stage = new Stage();
    JournalController controller = new JournalControllerImpl();
    stage.setScene(new JournalViewImpl(controller).load());
    controller.initView();
    stage.show();
  }

  /**
   * opens a .bujo file with all the information preserved
   */
  @FXML
  private void loadDialog() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Existing Journal");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("bujo file", "*.bujo"));
    File f = fileChooser.showOpenDialog(new Popup());
    if (f != null) {
      try {
        BujoReader bujoReader = new BujoReader(f);
        JournalController newController = new JournalControllerImpl();
        Stage stage = new Stage();
        stage.setScene(new JournalViewImpl(newController).load());
        newController.loadFile(bujoReader);
        newController.initView();
        stage.show();
      } catch (IOException e) {
        errorDialog("Cannot open journal");
      }
    }
  }

  /**
   * loads file in given reader to a new window
   *
   * @param bujoReader - reader of file to load
   * @throws IOException if reader contains invalid file
   */
  public void loadFile(BujoReader bujoReader) throws IOException {
    this.initView();
    for (EventEntry e : bujoReader.parseEvents()) {
      setEventEntry(e);
    }
    for (TaskEntry t : bujoReader.parseTasks()) {
      setTaskEntry(t);
    }
    quote.setText(bujoReader.parseQuote());
    eventsPerDayMax = bujoReader.parseMaxEvents();
    tasksPerDayMax = bujoReader.parseMaxTasks();
    title.setText(bujoReader.parseTitle());
    chosenDay = bujoReader.parseStartWeek();
    formatWeekStart(chosenDay);
  }

  /**
   * saves the information of a .bujo file in json format
   */
  @FXML
  private void saveFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Journal");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("bujo file", "*.bujo"));
    File f = fileChooser.showSaveDialog(new Popup());
    if (f != null) {
      BujoWriter writer = new BujoWriter(f);
      try {
        writer.writeEntries(this.events, this.tasks, quote.getText(), title.getText(),
            eventsPerDayMax,
            tasksPerDayMax, chosenDay);
      } catch (IOException e) {
        errorDialog("Cannot save journal");
      }
    }
  }

  /**
   * allows the task queue to be expanded
   */
  @FXML
  private void expandTaskQueue() {
    if (!hiddenPane.isVisible()) {
      VBox taskChildren = (VBox) taskPane.getChildren().get(0);
      taskChildren.getChildren().clear();
      addToQueue();
      hiddenPane.setVisible(true);
      FadeTransition transition1 = new FadeTransition(Duration.seconds(0.5), hiddenPane);
      transition1.setFromValue(0);
      transition1.setToValue(0.15);
      transition1.play();

      TranslateTransition translateTransition =
          new TranslateTransition(Duration.seconds(0.5), sidebar);
      translateTransition.setByX(+600);
      translateTransition.play();

      hiddenPane.setOnMouseClicked(event -> collapseTaskQueue());
    } else {
      collapseTaskQueue();
    }
  }

  /**
   * makes the task queue disappear from view
   */
  @FXML
  private void collapseTaskQueue() {
    FadeTransition transition2 = new FadeTransition(Duration.seconds(0.5), hiddenPane);
    transition2.setFromValue(0.15);
    transition2.setToValue(0);
    transition2.play();

    transition2.setOnFinished(event1 -> {
      hiddenPane.setVisible(false);
    });

    TranslateTransition translateTransition1 =
        new TranslateTransition(Duration.seconds(0.5), sidebar);
    translateTransition1.setByX(-600);
    translateTransition1.play();
  }

  /**
   * opens a dialog that allows the use to set max events or tasks
   */
  @FXML
  private void maxDialog() {
    GridPane gridPane = new GridPane();
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
    dialog.getDialogPane().setContent(gridPane);
    gridPane.add(new Label("Max Events:"), 0, 0);
    ChoiceBox<Integer> eventBox = new ChoiceBox<>();
    eventBox.setValue(this.eventsPerDayMax);
    gridPane.add(new Label("Max Tasks:"), 0, 1);
    ChoiceBox<Integer> taskBox = new ChoiceBox<>();
    taskBox.setValue(this.tasksPerDayMax);
    gridPane.add(eventBox, 1, 0);
    gridPane.add(taskBox, 1, 1);
    for (int i = 1; i <= 10; i++) {
      eventBox.getItems().add(i);
      taskBox.getItems().add(i);
    }
    Button apply = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
    apply.setOnAction(event -> {
      this.eventsPerDayMax = eventBox.getValue();
      this.tasksPerDayMax = taskBox.getValue();
    });
    dialog.showAndWait();
  }

  /**
   * displays weekly stats for the week in a dialog
   */
  @FXML
  private void showStatsDialog() {
    Label totalTasks = new Label("Total Tasks:" + tasks.size());
    Label totalEvents = new Label("Total Events: " + events.size());
    Label taskCompletion = new Label("Task Completion %: " + calculateTaskCompletion());
    VBox vbox = new VBox(totalTasks, totalEvents, taskCompletion);
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Week Stats");
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    dialog.getDialogPane().setContent(vbox);
    dialog.show();
  }

  /**
   * handles creating a new event
   */
  @FXML
  private void eventDialog() {
    EventDialog dialog = new EventDialog();
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);

    okButton.setOnAction(event -> {
      String name = dialog.getName();
      DayOfWeek day = dialog.getDay();
      String description = dialog.getDescription();
      String hours = dialog.getHour();
      String minutes = dialog.getMin();
      String amPm = dialog.getAmPm();
      String numHours = dialog.getDurHour();
      String numMins = dialog.getDurMin();
      String startTime = hours + ":" + minutes + amPm;
      String duration = numHours + " Hours " + numMins + " Minutes";
      try {
        EventEntry e = new Event(name, description, day, startTime, duration);
        try {
          setEventEntry(e);
        } catch (IllegalArgumentException exc) {
          errorDialog("Number of events exceeded for: " + e.getDay());
        }
      } catch (IllegalArgumentException e) {
        errorDialog("Missing one or more required inputs");
      }
    });
    dialog.showAndWait();
  }

  /**
   * handles creating a new task
   */
  @FXML
  private void taskDialog() {
    TaskDialog dialog = new TaskDialog();
    dialog.setTitle("Add Task");
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);

    okButton.setOnAction(event -> {
      String name = dialog.getName();
      DayOfWeek day = dialog.getDay();
      String description = dialog.getDescription();
      boolean complete = dialog.getComplete();
      try {
        TaskEntry t = new Task(name, description, day, complete);
        try {
          setTaskEntry(t);
        } catch (IllegalArgumentException e) {
          errorDialog("Number of tasks exceeded for: " + t.getDay());
        }
      } catch (IllegalArgumentException e) {
        errorDialog("Missing one or more required inputs");
      }
    });
    dialog.showAndWait();
  }

  /**
   * shows error message for inavlid inputs, too many events/tasks/ etc
   *
   * @param message message to be shown to user
   */
  private void errorDialog(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(message);
    alert.show();
  }

  /**
   * formats the event on the view
   *
   * @param e event to be displayed
   * @param p anchor pane where the event should be shown
   */
  @FXML
  private void formatEventEntry(EventEntry e, AnchorPane p) {
    VBox vbox = (VBox) p.getChildren().get(0);
    Label eventLabel;
    if (vbox.getChildren().size() > 4) {
      p.setPrefSize(p.getWidth(), p.getHeight() + 55);
    }
    eventLabel = new Label(e.getName() + "\n" + e.getDescription() + "\n"
        + e.getStart() + "\n" + e.getDuration());
    eventLabel.setFont(new Font(10));
    eventLabel.setPrefSize(112, 55);
    Button delete = new Button("x");
    delete.setFont(new Font(10));
    HBox entry = new HBox(eventLabel, delete);
    entry.setPrefHeight(55);
    delete.setOnAction(event -> deleteEntry(vbox, entry, e));
    eventLabel.setOnMouseClicked(event -> editEventEntry(eventLabel, e, vbox, entry));
    vbox.getChildren().add(entry);
  }

  /**
   * formats the task on the view
   *
   * @param e task to be shown
   * @param p anchor pane where the task is shown
   */
  @FXML
  private void formatTaskEntry(TaskEntry e, AnchorPane p) {
    VBox vbox = (VBox) p.getChildren().get(0);
    Label taskLabel;
    if (vbox.getChildren().size() > 4) {
      p.setPrefSize(p.getWidth(), p.getHeight() + 55);
    }
    taskLabel = new Label(e.getName() + "\n" + e.getDescription() + "\n");
    taskLabel.setFont(new Font(10));
    taskLabel.setPrefSize(112, 55);
    Button delete = new Button("x");
    delete.setFont(new Font(10));
    HBox entry = new HBox(taskLabel, delete);
    entry.setPrefHeight(55);
    delete.setOnAction(event -> deleteEntry(vbox, entry, e));
    taskLabel.setOnMouseClicked(event -> editTaskEntry(taskLabel, e, vbox, entry));
    vbox.getChildren().add(entry);
  }

  /**
   * deletes entry
   *
   * @param vbox  vbox where the entry is placed in
   * @param entry entry in view to be removed
   * @param e     task/event be removed from the list
   */
  private void deleteEntry(VBox vbox, HBox entry, JournalEntry e) {
    vbox.getChildren().remove(entry);
    for (int i = 0; i < events.size(); i++) {
      if (events.get(i).equals(e)) {
        events.remove(e);
        int numEvents = this.numEventPerDay.get(e.getDay()) - 1;
        numEventPerDay.put(e.getDay(), numEvents);
        break;
      }
    }
    for (int i = 0; i < tasks.size(); i++) {
      if (tasks.get(i).equals(e)) {
        tasks.remove(e);
        taskPane.getChildren().remove(e);
        int numTasks = this.numTasksPerDay.get(e.getDay()) - 1;
        numTasksPerDay.put(e.getDay(), numTasks);
        break;
      }
    }
  }

  /**
   * handles user editing events
   *
   * @param label      information for the event
   * @param eventEntry event to be edited
   * @param vbox       where the information for the event is placed
   * @param entry      contains label, edit and delete buttons
   */
  private void editEventEntry(Label label, EventEntry eventEntry, VBox vbox, HBox entry) {
    EventDialog dialog = new EventDialog();
    dialog.setValues(eventEntry);
    dialog.setTitle("Edit Event");
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
    Button apply = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);

    DayOfWeek initalDay = eventEntry.getDay();

    apply.setOnAction(event -> {
      String name = dialog.getName();
      DayOfWeek day = dialog.getDay();
      String description = dialog.getDescription();
      String hours = dialog.getHour();
      String minutes = dialog.getMin();
      String amPm = dialog.getAmPm();
      String numHours = dialog.getDurHour();
      String numMins = dialog.getDurMin();
      String startTime = hours + ":" + minutes + amPm;
      String duration = numHours + " Hours " + numMins + " Minutes";

      eventEntry.setName(name);
      eventEntry.setDay(day);
      eventEntry.setDescription(description);
      eventEntry.setStart(startTime);
      eventEntry.setDuration(duration);

      if (!initalDay.equals(day)) {
        vbox.getChildren().remove(entry);
        numEventPerDay.put(initalDay, numEventPerDay.get(initalDay) - 1);
        try {
          setEventEntry(eventEntry);
        } catch (IllegalArgumentException e) {
          errorDialog("Number of tasks exceeded for: " + eventEntry.getDay());
          numTasksPerDay.put(day, numTasksPerDay.get(day) - 1);
          eventEntry.setDay(initalDay);
          setEventEntry(eventEntry);
        }
      } else {
        label.setText(eventEntry.getName() + "\n" + eventEntry.getDescription() + "\n"
            + eventEntry.getStart() + "\n" + eventEntry.getDuration());
      }
    });
    dialog.showAndWait();
  }

  /**
   * edits task
   *
   * @param label     information for the task
   * @param taskEntry task to be edited
   * @param vbox      where the task is located on the view
   * @param entry     contains label, eit nd delete buttons
   */
  private void editTaskEntry(Label label, TaskEntry taskEntry, VBox vbox, HBox entry) {
    TaskDialog dialog = new TaskDialog();
    dialog.setValues(taskEntry);
    dialog.setTitle("Edit Task");
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
    Button apply = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
    DayOfWeek initalDay = taskEntry.getDay();

    apply.setOnAction(event -> {
      String name = dialog.getName();
      DayOfWeek day = dialog.getDay();
      String description = dialog.getDescription();
      boolean complete = dialog.getComplete();

      taskEntry.setName(name);
      taskEntry.setDay(day);
      taskEntry.setDescription(description);
      taskEntry.setComplete(complete);

      if (!initalDay.equals(day)) {
        vbox.getChildren().remove(entry);
        numTasksPerDay.put(initalDay, numTasksPerDay.get(initalDay) - 1);
        try {
          setTaskEntry(taskEntry);
        } catch (IllegalArgumentException e) {
          errorDialog("Number of tasks exceeded for: " + taskEntry.getDay());
          numTasksPerDay.put(day, numTasksPerDay.get(day) - 1);
          taskEntry.setDay(initalDay);
          setTaskEntry(taskEntry);
        }
      } else {
        label.setText(taskEntry.getName() + "\n" + taskEntry.getDescription() + "\n");
      }
    });
    dialog.showAndWait();
  }

  /**
   * adds tasks to the task queue
   */
  @FXML
  private void addToQueue() {
    VBox vbox = (VBox) taskPane.getChildren().get(0);
    for (TaskEntry t : tasks) {
      Label taskLabel = new Label(t.getName() + "\n"
          + t.getDescription() + "\n" + t.getDay());
      taskLabel.setPrefSize(180, 55);
      Label complete;
      if (t.isComplete()) {
        complete = new Label("Completed");
      } else {
        complete = new Label("Incomplete");
      }

      complete.setPadding(new Insets(10, 0, 10, 0));
      HBox hbox = new HBox(taskLabel, complete);
      hbox.setPrefSize(270, 55);
      HBox.setMargin(complete, new Insets(10, 5, 10, 10));
      vbox.getChildren().add(hbox);
    }
  }

  /**
   * checks if the num events exceeds max, adds event to respective days event pane if not
   *
   * @param e event to be added
   */
  @FXML
  private void setEventEntry(EventEntry e) {
    this.numEventPerDay.put(e.getDay(), this.numEventPerDay.get(e.getDay()) + 1);
    if (this.eventsPerDayMax < this.numEventPerDay.get(e.getDay())) {
      throw new IllegalArgumentException("Number of events exceeded for: " + e.getDay());
    } else {
      this.events.add(e);
      switch (e.getDay()) {
        case SUNDAY -> formatEventEntry(e, sundayEvents);
        case MONDAY -> formatEventEntry(e, mondayEvents);
        case TUESDAY -> formatEventEntry(e, tuesdayEvents);
        case WEDNESDAY -> formatEventEntry(e, wednesdayEvents);
        case THURSDAY -> formatEventEntry(e, thursdayEvents);
        case FRIDAY -> formatEventEntry(e, fridayEvents);
        case SATURDAY -> formatEventEntry(e, saturdayEvents);
        default -> throw new IllegalArgumentException("invalid day");
      }
    }
  }

  /**
   * checks if num tasks exceeds the max, adds to respective days task pane if not
   *
   * @param t task to be added
   */
  @FXML
  private void setTaskEntry(TaskEntry t) {
    this.numTasksPerDay.put(t.getDay(), this.numTasksPerDay.get(t.getDay()) + 1);
    if (this.tasksPerDayMax < this.numTasksPerDay.get(t.getDay())) {
      throw new IllegalArgumentException("Number of tasks exceeded for: " + t.getDay());
    } else {
      this.tasks.add(t);
      switch (t.getDay()) {
        case SUNDAY -> formatTaskEntry(t, sundayTasks);
        case MONDAY -> formatTaskEntry(t, mondayTasks);
        case TUESDAY -> formatTaskEntry(t, tuesdayTasks);
        case WEDNESDAY -> formatTaskEntry(t, wednesdayTasks);
        case THURSDAY -> formatTaskEntry(t, thursdayTasks);
        case FRIDAY -> formatTaskEntry(t, fridayTasks);
        case SATURDAY -> formatTaskEntry(t, saturdayTasks);
        default -> throw new IllegalArgumentException("invalid day");
      }
    }
  }
}
