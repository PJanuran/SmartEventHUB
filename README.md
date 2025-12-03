<h1 align = "center"> |||| SmartEvent HUB |||| <h1 
<h3 align = "center">Your console-based Event Management System.</h3>
<p align = "center">
<b>IT 2110 </b> <br/>
Aguado, Claire M. <br/>
Anuran, Prince Jonnell M. <br/>
Hibaler, khyl Jaspher <br/>
Mendoza, Kayzel Marie B.
</p>

### Overview
Eventplanner is a console-based Java application allowing users to manage Event plans entries directly through the terminal.
<br/><br/>
The development of the SmartEvent Hub: Event Management and Analytics System applies several key Object-Oriented Programming (OOP) concepts to ensure the platform is organized, scalable, and easy to maintain. Encapsulation is implemented by grouping related functions—such as data handling, user interface elements, and database operations—into specific classes that hide internal processes while exposing only necessary methods. Inheritance is used to streamline the creation of different windows and features by allowing new frames or pages to reuse and extend the structure of a base Tkinter class, reducing repetitive code. Polymorphism is also present, particularly in how shared methods like button commands and display functions behave differently depending on which page or event type (Birthday, Anniversary, Burial) is being processed. Finally, Abstraction helps simplify complex processes, such as generating analytics or querying the database, by placing them into reusable methods that shield the programmer from low-level implementation details. Together, these OOP principles make the system more efficient, modular, and easier to update as new features are added.

<br/>
Users can:

✏️ Add a new event<br/>
📔 View all event<br/>
✍🏻 Update event<br/>
📑 Delete event<br/>
📑 Generate the reciept


💾 All Event are stored in the event file

### Project Structure
```
event planner/
│
└── src/
     │
     └── final project.java

```
- `final project.java` -all the code handling, CRUD and etc.


### How to Run the Program
Open a terminal or command prompt, navigate to the folder where the .java files are located, and run:
```
javac event planner*.java
```
Run the program using:
```
java event planner.Main
```
Features
1. **Add Event.** Create a new Event being planned.
2. **View Event.** Display all saved Event, show the name of the clients.
3. **Update Event.** update the reservation, name of client, location, time, the bundle bieng purchased.
4. **Delete Event.** Remove a specific event permanently.
5. **Generates reciept.** show the planned event.

### Object-oriented Principles
Object-oriented Principles
Encapsulation

Encapsulation was applied through the use of classes with private fields.
In the program, the ```Event``` class contains private variables such as:
```
id

title

date

time

location

category

inclusion

description
```

These fields can only be accessed or modified through the class's public getters and setters, such as ```getTitle()```, ```setDate()```, and ```getDescription()```.

This ensures that each event’s data is protected from unauthorized modification, and all updates happen only through controlled methods inside the class.

### Abstraction

Abstraction was implemented by dividing the program into clear, simplified methods that hide complex processes.
For example, methods such as:
```
addEvent()

updateEvent()

deleteEvent()

viewAllEvents()

generateReceipt()
```
Each method hides the detailed steps needed to perform its task.
The main program does not need to know how an event is stored, updated, or formatted—it simply calls the method.

This removes low-level complexity and keeps the program’s logic clean and easy to maintain.

### Inheritance

Inheritance was not used directly in this program.
There is only one main class (```EventOrganizerConsole```) and a nested ```Event``` class.
However, the structure is ready for extension.
For example, different event types such as:
```
BirthdayEvent

AnniversaryEvent

BurialEvent
```
could inherit from the base Event class and override certain behaviors like toString() or how inclusions are handled.
This shows potential for code reuse and expansion without rewriting the existing logic.

### Polymorphism

Polymorphism is applied in the program through method overriding.
The ```Event``` class overrides the inherited ```toString()``` method:

```@Override
public String toString() { ... }
```
This allows each event object to provide its own customized string format when displayed.
Additionally, if subclasses of Event were added in the future (e.g., ```BirthdayEvent```, ```BurialEvent```), they could override certain methods like ```toString()``` or ```calculateCost()```.
This would let the program call the correct version dynamically at runtime.
This demonstrates how polymorphism allows flexible behavior while using the same method names.

### Example Output
```
Welcome to TREZ Event Organizer (Java Console Edition)

--------------------------------------------------
  EVENT ORGANIZER MENU
--------------------------------------------------
1. Add New Event
2. View All Events
3. Update Event
4. Delete Event
5. Generate Receipt
6. Exit
--------------------------------------------------
Enter your choice (1-6): 2

================================================================================
--- ALL SCHEDULED EVENTS ---
--------------------------------------------------------------------------------
| ID    | Reservation Name          | Date       | Time  | Location        | Category        |
--------------------------------------------------------------------------------
| 1     | Alice's 30th Party        | 2024-11-20 | 19:00 | The Loft Venue  | Birthday        |
| 3     | John Doe Service          | 2024-12-24 | 10:30 | Pine Hill Chapel | Burial          |
| 2     | 25th Wedding Milestone    | 2025-05-15 | 18:00 | Grand Ballroom  | Anniversary     |
================================================================================

--------------------------------------------------
  EVENT ORGANIZER MENU
--------------------------------------------------
1. Add New Event
2. View All Events
3. Update Event
4. Delete Event
5. Generate Receipt
6. Exit
--------------------------------------------------
Enter your choice (1-6): 3

================================================================================
--- ALL SCHEDULED EVENTS ---
--------------------------------------------------------------------------------
| ID    | Reservation Name          | Date       | Time  | Location        | Category        |
--------------------------------------------------------------------------------
| 1     | Alice's 30th Party        | 2024-11-20 | 19:00 | The Loft Venue  | Birthday        |
| 3     | John Doe Service          | 2024-12-24 | 10:30 | Pine Hill Chapel | Burial          |
| 2     | 25th Wedding Milestone    | 2025-05-15 | 18:00 | Grand Ballroom  | Anniversary     |
================================================================================

==================================================
--- UPDATE EXISTING EVENT ---
Enter the ID of the event to update (or 0 to cancel): 2

Editing Event ID 2: '25th Wedding Milestone'
Leave fields blank to keep the current value.
1. Reservation Name (Current: 25th Wedding Milestone): PRINCE
2. Date (YYYY-MM-DD, Current: 2025-05-15): 2025-12-25
3. Time (HH:MM, Required. Current: 18:00): 12:00
4. Location (Required. Current: Grand Ballroom): MATINGGAIN

5. Choose Category:
   [1] Birthday
   [2] Anniversary
   [3] Burial
   Enter category number (Default: 2 - Anniversary): 2

6. Choose Inclusions for ANNIVERSARY (Select up to 10 items):
--------------------------------------------------
  --- VENUE ---
   [X] [ 1] PRIVATE ROOM
   [ ] [ 2] EVENT SPACE
  --- ENTERTAINMENT ---
   [ ] [ 3] DJ
   [ ] [ 4] LIVE BAND
   [ ] [ 5] GAMES
   [ ] [ 6] ACTIVITIES
  --- FOOD AND BEVERAGE ---
   [ ] [ 7] BUFFET
   [X] [ 8] PLATED MEAL
  --- PHOTOGRAPHY ---
   [ ] [ 9] PROFESSIONAL PHOTOGRAPHER
   [ ] [10] PHOTO BOOTH
  --- ADD-ONS ---
   [ ] [11] PRE - ANNIVERSARY SHOOT
   [X] [12] PROGRAM HOST
  --- DECORATIONS AND THEME ---
   [X] [13] BALLOONS
   [ ] [14] BANNERS
   [ ] [15] THEMED DECOR
   [ ] [16] INVITATIONS
   [ ] [17] SOUVENIR
--------------------------------------------------
   CURRENT SELECTIONS (4/10): PRIVATE ROOM, PLATED MEAL, BALLOONS, PROGRAM HOST
   Enter numbers to toggle (e.g., '1 5 10'), or 'D' when Done:
> 2
[INFO] Added: EVENT SPACE
   [Current selections (5/10): PRIVATE ROOM, PLATED MEAL, BALLOONS, PROGRAM HOST, EVENT SPACE]
> D
7. Description (Current: Celebrating the couple's silver wedding anniversary.): 
Enter new description (or just press Enter to keep current):
> 

[SUCCESS] Event ID 2 updated successfully.

--------------------------------------------------
  EVENT ORGANIZER MENU
--------------------------------------------------
1. Add New Event
2. View All Events
3. Update Event
4. Delete Event
5. Generate Receipt
6. Exit
--------------------------------------------------
Enter your choice (1-6): 5

============================================================
--- GENERATE EVENT RECEIPT ---
Enter the ID of the event to generate a receipt for (or 0 to cancel): 2

************************************************************
          TREZ EVENT BOOKING RECEIPT
************************************************************
Event ID       : 2
Reservation    : PRINCE
Category       : Anniversary
------------------------------------------------------------
Date           : 2025-12-25
Time           : 12:00
Location       : MATINGGAIN
------------------------------------------------------------
INCLUDED SERVICES (5/10 Selected):
  [-- VENUE --]
    - PRIVATE ROOM
    - EVENT SPACE
  [-- FOOD AND BEVERAGE --]
    - PLATED MEAL
  [-- ADD-ONS --]
    - PROGRAM HOST
  [-- DECORATIONS AND THEME --]
    - BALLOONS
------------------------------------------------------------
Description:
Celebrating the couple's silver wedding anniversary.
************************************************************

--------------------------------------------------
  EVENT ORGANIZER MENU
--------------------------------------------------
1. Add New Event
2. View All Events
3. Update Event
4. Delete Event
5. Generate Receipt
6. Exit
--------------------------------------------------
Enter your choice (1-6): 6

Thank you for using the Event Organizer. Goodbye!
```

All Scheduled Events.txt Snippet
```
================================================================================
--- ALL SCHEDULED EVENTS ---
--------------------------------------------------------------------------------
| ID    | Reservation Name          | Date       | Time  | Location        | Category        |
--------------------------------------------------------------------------------
| 1     | Alice's 30th Party        | 2024-11-20 | 19:00 | The Loft Venue  | Birthday        |
| 3     | John Doe Service          | 2024-12-24 | 10:30 | Pine Hill Chapel | Burial          |
| 2     | 25th Wedding Milestone    | 2025-05-15 | 18:00 | Grand Ballroom  | Anniversary     |
================================================================================


```

Contributors

<table>
<tr>
    <th> Name </th>
    <th> Role </th>
</tr>
<tr>
    <td><strong>Aguado, Claire M. </strong> <br/>
    <td>Project Leader</td>
</tr>
  <tr>
    <td><strong>Anuran, Prince Jonnell M. </strong> <br/>
    <td>System Developer</td>
</tr>
<tr>
    <td><strong>Hibaler, Jaspher Khyl </strong> <br/>
    </td>
    <td>System Developer</td>
</tr>
<tr>
   <td><strong>Mendoza, Kayzel Marie B.</strong> <br/>
    </td>
    <td>Project Leader</td>
</tr>
</table>

### Acknowledgment
We sincerely express our gratitude to our instructor for the guidance and support provided throughout the completion of this project. We also extend our appreciation to our classmates and peers for their cooperation and encouragement during the development process.
