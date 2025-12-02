<h1 align = "center"> |||| SmartEvent HUB |||| <h1 
<h3 align = "center">Your console-based Event Management System.</h3>
<p align = "center">
<b>IT 2110 </b> <br/>
Aguado, Claire M. <br/>
Anuran, Prince Jonnell M. <br/>
Hibaler, khyl Jaspher <br/>
Mendoza, Kayzel Marie B.
</p>

## ‧₊˚ ┊ Overview
Eventplanner is a console-based Java application allowing users to manage Event plans entries directly through the terminal.
<br/><br/>
The development of the SmartEvent Hub: Event Management and Analytics System applies several key Object-Oriented Programming (OOP) concepts to ensure the platform is organized, scalable, and easy to maintain. Encapsulation is implemented by grouping related functions—such as data handling, user interface elements, and database operations—into specific classes that hide internal processes while exposing only necessary methods. Inheritance is used to streamline the creation of different windows and features by allowing new frames or pages to reuse and extend the structure of a base Tkinter class, reducing repetitive code. Polymorphism is also present, particularly in how shared methods like button commands and display functions behave differently depending on which page or event type (Birthday, Anniversary, Burial) is being processed. Finally, Abstraction helps simplify complex processes, such as generating analytics or querying the database, by placing them into reusable methods that shield the programmer from low-level implementation details. Together, these OOP principles make the system more efficient, modular, and easier to update as new features are added.

<br/>
### Users can:
✏️ Add a new event<br/>
📔 View all event<br/>
✍🏻 Update event<br/>
📑 Delete event<br/>
📑 Generate the reciept

### Event plan is stored 
💾 All Event are stored in the event file

## ‧₊˚ ┊ Project Structure
```
📂 src/
└── 📂 diaryapp/
    ├── ☕ Main.java          
    ├── ☕ Diary.java
    └── ☕ FileHandler.java
```
- `Main.java` - Entry point of the program, containing the menu and handles user interactions.
- `Diary.java` - Handles the diary operations (CRUD)
- `FileHandler.java` - Handles file creation, reading, writing, and appending.
### How to Run the Program
Open your terminal in the `src/` folder and run:
```
javac diaryapp/*.java
```
Run the program using:
```
java diaryapp.Main
```
## ‧₊˚ ┊ Features
1. **Add Event.** Create a new Event being planned.
2. **View Event.** Display all saved Event, show the name of the clients.
3. **Update Event.** update the reservation, name of client, location, time, the bundle bieng purchased.
4. **Delete Event.** Remove a specific event permanently.
5. **Generates reciept.** show the planned event.

## ‧₊˚ ┊ Object-oriented Principles
### 💊 Encapsulation
Encapsulation was applied through class design and private fields. For instance, in `Diary`, the `filepath` variable is private and can only be accessed through the class's own methods such as, `addEntry()`, `viewEntries()`, etc.

This ensures that data and operations on it are bundled together and protected from unauthorized modification.

### 💡 Abstraction
Abstraction was implemented when the `FileHandler` class abstracts file operations like reading, writing, and appending. The `Diary` class doesn't need to know how file handling works, for it just calls methods like `FileHandler.appendLine()` or `FileHandler.readAllLines()`.

This hides low-level complexity and keeps the main logic clean.

### 🧬 Inheritance
Inheritance was not heavily used in the program, however, its structure is ready for extension.
For instance, if a subclass like for diary is to be created, it could inherit from `Diary` and override some methods like `addEntry()` and `viewEntries()`.

This shows potential for code reuse and expansion without rewriting existing logic.

### 🎭 Polymorphism
The `switch` expression in `Main.java` demonstrates method-level polymorphism, the same action (`diary.[action]`) calls different behaviors depending on user choice.

Also, if a subclass of `Diary` overrides a method, for instance `addEntry()`, the program could dynamically call the correct version at runtime, enabling flexible behavior.

## ‧₊˚ ┊ Example Output
```
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
1. Reservation Name (Current: 25th Wedding Milestone): Prince
2. Date (YYYY-MM-DD, Current: 2025-05-15): 2025-12-25
3. Time (HH:MM, Required. Current: 18:00): 12:00
4. Location (Required. Current: Grand Ballroom): Matinggain

5. Choose Category:
   [1] Birthday
   [2] Anniversary
   [3] Burial
   Enter category number (Default: 2 - Anniversary): 2

6. Choose Inclusions (Select up to 10 items):
------------------------------
   [X] [1] Catering Service
   [ ] [2] Live Band / DJ
   [ ] [3] Security Personnel
   [ ] [4] Floral Arrangements
   [X] [5] Photography Package
   [ ] [6] Videography Service
   [ ] [7] Venue Decoration
   [ ] [8] Transportation (Limo/Hearse)
   [ ] [9] Custom Cake / Dessert Bar
   [ ] [10] Event Planner Fee
------------------------------
   CURRENT: Catering Service, Photography Package
   Enter numbers to toggle (e.g., '1 5 10'), or 'D' when Done:
> 1
[INFO] Removed: Catering Service
   [Current selections: Photography Package]
> 2
[INFO] Added: Live Band / DJ
   [Current selections: Photography Package, Live Band / DJ]
> 3
[INFO] Added: Security Personnel
   [Current selections: Photography Package, Live Band / DJ, Security Personnel]
> d
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
          HEBS EVENT BOOKING RECEIPT
************************************************************
Event ID       : 2
Reservation    : Prince
Category       : Anniversary
------------------------------------------------------------
Date           : 2025-12-25
Time           : 12:00
Location       : Matinggain
------------------------------------------------------------
INCLUDED SERVICES (Inclusion):
  - ( 1) Photography Package
  - ( 2) Live Band / DJ
  - ( 3) Security Personnel
------------------------------------------------------------
Description:
Celebrating the couple's silver wedding anniversary.
************************************************************

```

##  ‧₊˚ ┊ notes.txt Snippet
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

##  ‧₊˚ ┊ Contributors

<table>
<tr>
    <th> &nbsp; </th>
    <th> Name </th>
    <th> Role </th>
</tr>
<tr>
    <td><img src="final oop/static/Claire.JPG" width="100" height="100"> </td>
    <td><strong>Aguado, Claire M. </strong> <br/>
    <td>Project Leader/System Architect</td>
</tr>
  <tr>
    <td><img src="static/marieemoiselle.JPG" width="100" height="100"> </td>
    <td><strong>Anuran, Prince Jonnell M. </strong> <br/>
    <td>Feature Developer</td>
</tr>
<tr>
    <td><img src="static/jeisquared.jpg" width="100" height="100"> </td>
    <td><strong>Hibaler, khyl Jaspher </strong> <br/>
    </td>
    <td>Feature Developer</td>
</tr>
<tr>
    <td><img src="static/renzmarrion.jpg" width="100" height="100"> </td>
    <td><strong>Mendoza, Kayzel Marie B.</strong> <br/>
    </td>
    <td>Project Leader/System Architect</td>
</tr>
</table>

##  ‧₊˚ ┊ Acknowledgment
We sincerely express our gratitude to our instructor for the guidance and support provided throughout the completion of this project. We also extend our appreciation to our classmates and peers for their cooperation and encouragement during the development process.

---
<small>
<b>DISCLAIMER</b><br/>
This project and its contents are provided for example and learning purposes only. Students are encouraged to use it as a reference and not copy it in its entirety.</small>
