[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/x6ckGcN8)
# 3500 PA05 Project Repo

[PA Write Up](https://markefontenot.notion.site/PA-05-8263d28a81a7473d8372c6579abd6481)

weekview GUI: 
![weekView.png](src%2Fmain%2Fresources%2FweekView.png)


This Bullet Journal application supports a multitude of features 
to help users keep track of events and tasks during the week 
- a view of the week, with tasks and events displayed for each day
- buttons + shortcuts to add events and tasks
- ability to save and open weeks
- set a max number of tasks and events per day
- a sidebar which seamlessly appears on the screen to show all tasks
- a menubar which contains all commands a user may wish to use to customize their week with intuitive keyboard shortcuts
- an area to write quotes of the user's choice
- ability to delete and edit tasks and events 
- a dialog which shows total tasks + events and task completion %
- ability tot determine which day the week starts on


How this application was designed with SOLID in mind:
- Single-Responsibility: We followed single responsibility in each of the class
we designed by ensuring each of them only carry out a single task. Our controller class takes
care of updating the view and model depending on user input, our view classes only take care of 
the visuals, and our model classes are about handling data and storing it, as well as sending it to the view
- Open/Closed Principle: We made use of interfaces in each of our packages to ensure classes are able
to extend the necessary functionality but are also prevented form potentially adding unexpected bugs
from modifying the classes directly. For example, we used EventEntry and TaskEntry interfaces which
both extend JournalEntry, to ensure that our classes will not lose the methods from journalentry, but are still
able to add methods to their classes through their separate interfaces
- Liskov Substitution: We used liskov substitution with our JournalEntry class, specifically
in the deleteEntry method, where if we were to replace journalentry with event or taskentry
it would not cause our program to break. In summary, journalentry could be replaced with either of its 
subtypes
- Interface Segregation: This was done, as stated before in our journalentry interface which, get extended by two
other interfaces, so that task specific methods are only available to the task, and same for the event
- Dependency Inversion: We used dependency inversion in our event and task entry classes, by using the depedncy injection pattern
to facilitate decoupling. This was also done in the bujowriter and reader classes. Our many abstractions also
allow for less dependecy on specific implementations and allow for more flexibility 


Our program could implement the progress bar. This is because we already keep track of the number of tasks associated with each 
day, as well as if a task is completed or not. We would add a small textarea to the bottom of each day that updates
when a task is marked complete. We would also include some sort of image/visual to display the number of tasks
planned vs completed. Since all the necessary information about tasks is already stored, it would mostly be a matter of
displaying it on the view