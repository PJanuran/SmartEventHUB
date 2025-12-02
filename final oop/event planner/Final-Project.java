import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * EventOrganizerConsole Class: Handles application logic and console I/O.
 * This class simulates the persistence layer using an ArrayList.
 * It contains the main method for execution.
 */
public class EventOrganizerConsole {
    // CATEGORIES have been limited to the requested three: Birthday, Anniversary, Burial
    private static final String[] CATEGORIES = {
        "Birthday", "Anniversary", "Burial"
    };

    // List of 10 available inclusions to choose from
    private static final String[] INCLUSIONS = {
        "Catering Service", "Live Band / DJ", "Security Personnel", "Floral Arrangements",
        "Photography Package", "Videography Service", "Venue Decoration", "Transportation (Limo/Hearse)",
        "Custom Cake / Dessert Bar", "Event Planner Fee"
    };

    private final List<Event> events;
    private final Scanner scanner;
    // Used to simulate the database's AUTOINCREMENT feature
    private final AtomicInteger idCounter = new AtomicInteger(1); 

    /**
     * Private Inner Class: Data model for an individual event.
     * Making it private static eliminates external access and startup ambiguity.
     */
    private static class Event {
        private int id;
        private String title;
        private String date;
        private String time;
        private String location;
        private String category;
        private List<String> inclusion; // Changed from String to List<String>
        private String description;

        public Event(int id, String title, String date, String time, String location, String category, List<String> inclusion, String description) {
            this.id = id;
            this.title = title;
            this.date = date;
            this.time = time;
            this.location = location;
            this.category = category;
            this.inclusion = inclusion;
            this.description = description;
        }

        // Getters and Setters
        public int getId() { return id; }
        public String getTitle() { return title; }
        public String getDate() { return date; }
        public String getTime() { return time; }
        public String getLocation() { return location; }
        public String getCategory() { return category; }
        public List<String> getInclusion() { return inclusion; } 
        public String getDescription() { return description; }

        public void setTitle(String title) { this.title = title; }
        public void setDate(String date) { this.date = date; }
        public void setTime(String time) { this.time = time; }
        public void setLocation(String location) { this.location = location; }
        public void setCategory(String category) { this.category = category; }
        public void setInclusion(List<String> inclusion) { this.inclusion = inclusion; } 
        public void setDescription(String description) { this.description = description; }

        @Override
        public String toString() {
            // Ensuring title fits in 25 characters for table formatting
            String shortTitle = title.length() > 25 ? title.substring(0, 22) + "..." : title;
            String displayTime = time.isEmpty() ? "" : time;
            String displayLocation = location.isEmpty() ? "" : location;
            String displayCategory = category.isEmpty() ? "None" : category;

            return String.format("| %-5d | %-25s | %-10s | %-5s | %-15s | %-15s |",
                    id,
                    shortTitle,
                    date,
                    displayTime,
                    displayLocation,
                    displayCategory
            );
        }
    }


    public EventOrganizerConsole() {
        this.events = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        // Add sample data using the new, restricted categories and the new 'inclusion' field (now a List)
        events.add(new Event(idCounter.getAndIncrement(), "Alice's 30th Party", "2024-11-20", "19:00", "The Loft Venue", "Birthday", Arrays.asList(INCLUSIONS[0], INCLUSIONS[8]), "Celebration for Alice's 30th birthday."));
        events.add(new Event(idCounter.getAndIncrement(), "25th Wedding Milestone", "2025-05-15", "18:00", "Grand Ballroom", "Anniversary", Arrays.asList(INCLUSIONS[0], INCLUSIONS[4]), "Celebrating the couple's silver wedding anniversary."));
        events.add(new Event(idCounter.getAndIncrement(), "John Doe Service", "2024-12-24", "10:30", "Pine Hill Chapel", "Burial", Arrays.asList(INCLUSIONS[3], INCLUSIONS[7]), "Memorial service for the late John Doe."));
    }

    /**
     * Main application loop.
     */
    public void run() {
        boolean running = true;
        System.out.println("Welcome to HEBS Event Organizer (Java Console Edition)");
        while (running) {
            displayMenu();
            System.out.print("Enter your choice (1-6): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addEvent();
                    break;
                case "2":
                    viewEvents();
                    break;
                case "3":
                    updateEvent();
                    break;
                case "4":
                    deleteEvent();
                    break;
                case "5":
                    generateReceipt(); // New function
                    break;
                case "6": // Exit is now option 6
                    running = false;
                    System.out.println("\nThank you for using the Event Organizer. Goodbye!");
                    break;
                default:
                    System.out.println("\n[INFO] Invalid choice. Please enter a number between 1 and 6.");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Checks if any event is already scheduled for the given date.
     * @param date The date to check (YYYY-MM-DD).
     * @param excludeId Event ID to exclude from the check (used during update). Use 0 for new events.
     * @return true if the date is booked by another event, false otherwise.
     */
    private boolean isDateBooked(String date, int excludeId) {
        return events.stream()
                .filter(e -> e.getId() != excludeId) // Exclude the current event when updating
                .anyMatch(e -> e.getDate().equals(date));
    }


    /**
     * Displays the main menu options.
     */
    private void displayMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("  EVENT ORGANIZER MENU");
        System.out.println("-".repeat(50));
        System.out.println("1. Add New Event");
        System.out.println("2. View All Events");
        System.out.println("3. Update Event");
        System.out.println("4. Delete Event");
        System.out.println("5. Generate Receipt"); // New option
        System.out.println("6. Exit");             // Moved
        System.out.println("-".repeat(50));
    }

    /**
     * Gathers data for a new event and adds it to the list.
     */
    private void addEvent() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("--- ADD NEW EVENT ---");
        
        String title, date, time, location, category, description;
        List<String> inclusion;

        // 1. Input validation for Title (Required)
        System.out.print("1. Enter Reservation Name (Required): ");
        title = scanner.nextLine().trim();
        while (title.isEmpty()) {
            System.out.print("[ERROR] Reservation Name is required. Re-enter: ");
            title = scanner.nextLine().trim();
        }

        // 2. Input validation for Date (Required + Double Booking Check)
        System.out.print("2. Enter Date (YYYY-MM-DD, Required): ");
        date = scanner.nextLine().trim();
        while (date.isEmpty() || isDateBooked(date, 0)) {
            if (date.isEmpty()) {
                System.out.print("[ERROR] Date is required. Re-enter: ");
            } else {
                System.out.print("[ERROR] A major event is already scheduled for " + date + ". Please choose another date. Re-enter Date (YYYY-MM-DD): ");
            }
            date = scanner.nextLine().trim();
        }

        // 3. Input validation for Time (Required)
        System.out.print("3. Enter Time (HH:MM, Required): ");
        time = scanner.nextLine().trim();
        while (time.isEmpty()) {
            System.out.print("[ERROR] Time is required. Re-enter: ");
            time = scanner.nextLine().trim();
        }

        // 4. Input validation for Location (Required)
        System.out.print("4. Enter Location (Required): ");
        location = scanner.nextLine().trim();
        while (location.isEmpty()) {
            System.out.print("[ERROR] Location is required. Re-enter: ");
            location = scanner.nextLine().trim();
        }

        // 5. Category Selection
        category = getCategoryChoice(null);
        
        // 6. Inclusion Selection (Now multi-choice)
        inclusion = getInclusionChoices(new ArrayList<>());
        
        // 7. Description (Optional)
        System.out.println("7. Enter Description (Optional). Press Enter to skip.");
        System.out.print("> ");
        description = scanner.nextLine().trim();
        
        int newId = idCounter.getAndIncrement();
        Event newEvent = new Event(newId, title, date, time, location, category, inclusion, description);
        events.add(newEvent);

        System.out.println("\n[SUCCESS] Event added successfully with ID: " + newId);
    }
    
    /**
     * Helper method to handle category selection.
     */
    private String getCategoryChoice(String currentCategory) {
        System.out.println("\n5. Choose Category:");
        for (int i = 0; i < CATEGORIES.length; i++) {
            System.out.printf("   [%d] %s\n", i + 1, CATEGORIES[i]);
        }
        
        // Determine the default category index for display/empty input
        int defaultIndex = 0;
        if (currentCategory != null) {
            for (int i = 0; i < CATEGORIES.length; i++) {
                if (CATEGORIES[i].equalsIgnoreCase(currentCategory)) {
                    defaultIndex = i;
                    break;
                }
            }
        }
        
        System.out.printf("   Enter category number (Default: %d - %s): ", defaultIndex + 1, CATEGORIES[defaultIndex]);
        String catChoice = scanner.nextLine().trim();

        if (catChoice.isEmpty()) {
            return CATEGORIES[defaultIndex];
        }

        try {
            int index = Integer.parseInt(catChoice) - 1;
            if (index >= 0 && index < CATEGORIES.length) {
                return CATEGORIES[index];
            } else {
                // If number is out of range
                System.out.println("[INFO] Invalid category number. Using default category.");
                return CATEGORIES[defaultIndex];
            }
        } catch (NumberFormatException e) {
            // If input is not a number
            System.out.println("[INFO] Invalid input (not a number). Using default category.");
            return CATEGORIES[defaultIndex];
        }
    }
    
    /**
     * Helper method to handle multi-selection of inclusions.
     */
    private List<String> getInclusionChoices(List<String> currentInclusions) {
        List<String> selected = new ArrayList<>(currentInclusions);
        String currentList = selected.isEmpty() ? "None" : String.join(", ", selected);
        
        System.out.println("\n6. Choose Inclusions (Select up to 10 items):");
        System.out.println("-".repeat(30));
        for (int i = 0; i < INCLUSIONS.length; i++) {
            String status = selected.contains(INCLUSIONS[i]) ? "[X]" : "[ ]";
            System.out.printf("   %s [%d] %s\n", status, i + 1, INCLUSIONS[i]);
        }
        System.out.println("-".repeat(30));
        System.out.printf("   CURRENT: %s\n", currentList);
        System.out.println("   Enter numbers to toggle (e.g., '1 5 10'), or 'D' when Done:");
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("D") || input.isEmpty()) {
                if (selected.isEmpty()) {
                    System.out.println("[ERROR] You must select at least one inclusion. Enter selection numbers or 'D' to finish if you have selections.");
                    continue;
                }
                break;
            }

            try {
                String[] parts = input.split("\\s+");
                for (String part : parts) {
                    int index = Integer.parseInt(part) - 1;
                    if (index >= 0 && index < INCLUSIONS.length) {
                        String item = INCLUSIONS[index];
                        if (selected.contains(item)) {
                            selected.remove(item);
                            System.out.printf("[INFO] Removed: %s\n", item);
                        } else if (selected.size() < 10) {
                            selected.add(item);
                            System.out.printf("[INFO] Added: %s\n", item);
                        } else {
                            System.out.println("[INFO] Maximum 10 inclusions reached. Cannot add: " + item);
                        }
                    } else {
                        System.out.println("[INFO] Invalid number: " + part + ". Please enter a number between 1 and " + INCLUSIONS.length + ".");
                    }
                }
                
                // Re-display current status
                currentList = selected.isEmpty() ? "None" : String.join(", ", selected);
                System.out.printf("   [Current selections: %s]\n", currentList);

            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid input. Please enter numbers separated by spaces or 'D'.");
            }
        }
        return selected;
    }

    /**
     * Displays all scheduled events in a formatted table, sorted by date and time.
     */
    private void viewEvents() {
        // Sort events by date, then by time
        events.sort((e1, e2) -> {
            int dateCompare = e1.getDate().compareTo(e2.getDate());
            if (dateCompare != 0) return dateCompare;
            return e1.getTime().compareTo(e2.getTime());
        });

        System.out.println("\n" + "=".repeat(80));
        System.out.println("--- ALL SCHEDULED EVENTS ---");
        System.out.println("-".repeat(80));
        
        if (events.isEmpty()) {
            System.out.println("No events found in the database.");
            System.out.println("=".repeat(80));
            return;
        }

        // Print header
        String header = String.format("| %-5s | %-25s | %-10s | %-5s | %-15s | %-15s |",
            "ID", "Reservation Name", "Date", "Time", "Location", "Category"
        );
        System.out.println(header);
        System.out.println("-".repeat(80));

        // Print events
        for (Event event : events) {
            System.out.println(event);
        }
        System.out.println("=".repeat(80));
    }

    /**
     * Finds an event by its ID.
     */
    private Event findEventById(int id) {
        return events.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Handles the process of getting a valid ID from the user.
     */
    private int getValidEventId(String action) {
        System.out.print("Enter the ID of the event to " + action + " (or 0 to cancel): ");
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return -1; 
            int id = Integer.parseInt(input);
            return id;
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid input. Please enter a valid number for the ID.");
            return -1; // Indicates an invalid input occurred
        }
    }

    /**
     * Allows the user to update an existing event.
     */
    private void updateEvent() {
        viewEvents();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("--- UPDATE EXISTING EVENT ---");
        
        int eventId = getValidEventId("update");
        if (eventId <= 0) {
            if (eventId == 0) { // User explicitly cancelled
                System.out.println("[INFO] Update operation cancelled.");
            }
            return;
        }

        Event eventToUpdate = findEventById(eventId);
        if (eventToUpdate == null) {
            System.out.println("[ERROR] Event with ID " + eventId + " not found.");
            return;
        }

        System.out.println("\nEditing Event ID " + eventId + ": '" + eventToUpdate.getTitle() + "'");
        System.out.println("Leave fields blank to keep the current value.");

        // 1. Title
        System.out.printf("1. Reservation Name (Current: %s): ", eventToUpdate.getTitle());
        String title = scanner.nextLine().trim();
        if (!title.isEmpty()) eventToUpdate.setTitle(title);

        // 2. Date (Validation for Double Booking)
        String oldDate = eventToUpdate.getDate();
        boolean dateValid = false;
        
        while (!dateValid) {
            System.out.printf("2. Date (YYYY-MM-DD, Current: %s): ", oldDate);
            String newDateInput = scanner.nextLine().trim();
            
            if (newDateInput.isEmpty()) {
                dateValid = true; 
            } else {
                if (isDateBooked(newDateInput, eventToUpdate.getId())) {
                    System.out.println("[ERROR] A major event is already scheduled for " + newDateInput + ". Please choose another date.");
                } else {
                    eventToUpdate.setDate(newDateInput);
                    dateValid = true;
                }
            }
        }

        // 3. Time (Required)
        System.out.printf("3. Time (HH:MM, Required. Current: %s): ", eventToUpdate.getTime());
        String time = scanner.nextLine().trim();
        if (!time.isEmpty()) eventToUpdate.setTime(time);
        
        // 4. Location (Required)
        System.out.printf("4. Location (Required. Current: %s): ", eventToUpdate.getLocation());
        String location = scanner.nextLine().trim();
        if (!location.isEmpty()) eventToUpdate.setLocation(location);
        
        // 5. Category
        String newCategory = getCategoryChoice(eventToUpdate.getCategory());
        eventToUpdate.setCategory(newCategory);
        
        // 6. Inclusion (Multi-Select)
        List<String> newInclusion = getInclusionChoices(eventToUpdate.getInclusion());
        eventToUpdate.setInclusion(newInclusion);

        // 7. Description
        String currentDescriptionDisplay = eventToUpdate.getDescription().isEmpty() ? "None" : eventToUpdate.getDescription().replaceAll("\n", " / ");
        System.out.printf("7. Description (Current: %s): ", currentDescriptionDisplay);
        System.out.println("\nEnter new description (or just press Enter to keep current):");
        System.out.print("> ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) eventToUpdate.setDescription(description);

        System.out.println("\n[SUCCESS] Event ID " + eventId + " updated successfully.");
    }

    /**
     * Allows the user to delete an existing event.
     */
    private void deleteEvent() {
        viewEvents();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("--- DELETE EVENT ---");

        int eventId = getValidEventId("delete");
        if (eventId <= 0) {
            if (eventId == 0) { // User explicitly cancelled
                System.out.println("[INFO] Deletion operation cancelled.");
            }
            return;
        }

        Event eventToDelete = findEventById(eventId);
        if (eventToDelete == null) {
            System.out.println("[ERROR] Event with ID " + eventId + " not found.");
            return;
        }

        System.out.print("Are you sure you want to delete event ID " + eventId + " ('" + eventToDelete.getTitle() + "')? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            if (events.remove(eventToDelete)) {
                System.out.println("\n[SUCCESS] Event ID " + eventId + " deleted.");
            } else {
                // This branch should theoretically not be hit if findEventById worked
                System.out.println("\n[ERROR] Failed to delete event ID " + eventId + ". Event may have been modified or already removed.");
            }
        } else {
            System.out.println("\n[INFO] Deletion cancelled.");
        }
    }
    
    /**
     * Generates and displays a detailed receipt for a specific event.
     */
    private void generateReceipt() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("--- GENERATE EVENT RECEIPT ---");
        
        int eventId = getValidEventId("generate a receipt for");
        if (eventId <= 0) {
            if (eventId == 0) {
                System.out.println("[INFO] Receipt generation cancelled.");
            }
            return;
        }

        Event event = findEventById(eventId);
        if (event == null) {
            System.out.println("[ERROR] Event with ID " + eventId + " not found.");
            return;
        }

        System.out.println("\n" + "*".repeat(60));
        System.out.println("          HEBS EVENT BOOKING RECEIPT");
        System.out.println("*".repeat(60));
        
        System.out.printf("%-15s: %s\n", "Event ID", event.getId());
        System.out.printf("%-15s: %s\n", "Reservation", event.getTitle());
        System.out.printf("%-15s: %s\n", "Category", event.getCategory());
        
        System.out.println("-".repeat(60));
        
        System.out.printf("%-15s: %s\n", "Date", event.getDate());
        System.out.printf("%-15s: %s\n", "Time", event.getTime());
        System.out.printf("%-15s: %s\n", "Location", event.getLocation());
        
        System.out.println("-".repeat(60));
        System.out.println("INCLUDED SERVICES (Inclusion):");
        if (event.getInclusion().isEmpty()) {
            System.out.println("  None selected (required for booking).");
        } else {
            for (int i = 0; i < event.getInclusion().size(); i++) {
                System.out.printf("  - (%2d) %s\n", i + 1, event.getInclusion().get(i));
            }
        }
        
        System.out.println("-".repeat(60));
        System.out.println("Description:");
        String descriptionText = event.getDescription().isEmpty() ? "  (No description provided)" : event.getDescription();
        // Print description with simple word wrap
        String[] words = descriptionText.split(" ");
        StringBuilder wrappedText = new StringBuilder("  ");
        int lineLength = 2; // Starts with '  ' padding
        for (String word : words) {
            if (lineLength + word.length() + 1 > 58) { // Max width 60, minus padding
                wrappedText.append("\n  ").append(word).append(" ");
                lineLength = 2 + word.length() + 1;
            } else {
                wrappedText.append(word).append(" ");
                lineLength += word.length() + 1;
            }
        }
        System.out.println(wrappedText.toString().trim());
        
        System.out.println("*".repeat(60));
    }


    /**
     * Main method: The entry point for the console application.
     */
    public static void main(String[] args) {
        EventOrganizerConsole app = new EventOrganizerConsole();
        app.run();
    }
}