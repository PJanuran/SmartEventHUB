import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class EventOrganizerConsole {
    
    private static final String[] CATEGORIES = {
        "Birthday", "Anniversary", "Burial"
    };

    private static final Map<String, Map<String, List<String>>> CATEGORY_INCLUSIONS = createInclusionsMap();

    private static Map<String, Map<String, List<String>>> createInclusionsMap() {
        Map<String, Map<String, List<String>>> map = new HashMap<>();

        Map<String, List<String>> birthday = new HashMap<>();
        birthday.put("VENUE", Arrays.asList("PRIVATE ROOM", "EVENT SPACE"));
        birthday.put("FOOD AND BEVERAGE", Arrays.asList("BUFFET", "PLATED MEAL"));
        birthday.put("DECORATIONS AND THEME", Arrays.asList("BALLOONS", "BANNERS", "THEMED DECOR", "INVITATIONS", "SOUVENIR"));
        birthday.put("ENTERTAINMENT", Arrays.asList("DJ", "LIVE BAND", "GAMES", "ACTIVITIES"));
        birthday.put("PHOTOGRAPHY", Arrays.asList("PROFESSIONAL PHOTOGRAPHER", "PHOTO BOOTH"));
        birthday.put("ADD-ONS", Arrays.asList("PRE - BIRTHDAY SHOOT", "PROGRAM HOST"));
        map.put("Birthday", birthday);

        Map<String, List<String>> anniversary = new HashMap<>();
        anniversary.put("VENUE", Arrays.asList("PRIVATE ROOM", "EVENT SPACE"));
        anniversary.put("FOOD AND BEVERAGE", Arrays.asList("BUFFET", "PLATED MEAL"));
        anniversary.put("DECORATIONS AND THEME", Arrays.asList("BALLOONS", "BANNERS", "THEMED DECOR", "INVITATIONS", "SOUVENIR"));
        anniversary.put("ENTERTAINMENT", Arrays.asList("DJ", "LIVE BAND", "GAMES", "ACTIVITIES"));
        anniversary.put("PHOTOGRAPHY", Arrays.asList("PROFESSIONAL PHOTOGRAPHER", "PHOTO BOOTH"));
        anniversary.put("ADD-ONS", Arrays.asList("PRE - ANNIVERSARY SHOOT", "PROGRAM HOST"));
        map.put("Anniversary", anniversary);
        
        Map<String, List<String>> burial = new HashMap<>();
        burial.put("VENUE", Arrays.asList("HOME", "CEMETERY"));
        burial.put("CASKET", Arrays.asList("KIDS", "SMALL", "MEDIUM", "LARGE", "X - LARGE", "2X - LARGE"));
        burial.put("TRANSPORTATION", Arrays.asList("HEARSE", "LIMOUSINE"));
        burial.put("FLORAL ARRANGEMENTS", Arrays.asList("FUNERAL FLOWERS", "WREATH"));
        map.put("Burial", burial);

        return map;
    }

    private final List<Event> events;
    private final Scanner scanner;
    private final AtomicInteger idCounter = new AtomicInteger(1); 

    private static class Event {
        private int id;
        private String title;
        private String date;
        private String time;
        private String location;
        private String category;
        private List<String> inclusion;
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
        
        List<String> bdayInclusions = Arrays.asList("EVENT SPACE", "BUFFET", "DJ", "PROFESSIONAL PHOTOGRAPHER");
        events.add(new Event(idCounter.getAndIncrement(), "Alice's 30th Party", "2024-11-20", "19:00", "The Loft Venue", "Birthday", bdayInclusions, "Celebration for Alice's 30th birthday."));
        
        List<String> anniInclusions = Arrays.asList("PRIVATE ROOM", "PLATED MEAL", "BALLOONS", "PROGRAM HOST");
        events.add(new Event(idCounter.getAndIncrement(), "25th Wedding Milestone", "2025-05-15", "18:00", "Grand Ballroom", "Anniversary", anniInclusions, "Celebrating the couple's silver wedding anniversary."));
        
        List<String> burialInclusions = Arrays.asList("HOME", "LARGE", "HEARSE", "FUNERAL FLOWERS");
        events.add(new Event(idCounter.getAndIncrement(), "John Doe Service", "2024-12-24", "10:30", "Pine Hill Chapel", "Burial", burialInclusions, "Memorial service for the late John Doe."));
    }

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
                    generateReceipt();
                    break;
                case "6":
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

    private boolean isDateBooked(String date, int excludeId) {
        return events.stream()
                .filter(e -> e.getId() != excludeId)
                .anyMatch(e -> e.getDate().equals(date));
    }


    private void displayMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("  EVENT ORGANIZER MENU");
        System.out.println("-".repeat(50));
        System.out.println("1. Add New Event");
        System.out.println("2. View All Events");
        System.out.println("3. Update Event");
        System.out.println("4. Delete Event");
        System.out.println("5. Generate Receipt");
        System.out.println("6. Exit");
        System.out.println("-".repeat(50));
    }

    private void addEvent() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("--- ADD NEW EVENT ---");
        
        String title, date, time, location, category, description;
        List<String> inclusion;

        System.out.print("1. Enter Reservation Name (Required): ");
        title = scanner.nextLine().trim();
        while (title.isEmpty()) {
            System.out.print("[ERROR] Reservation Name is required. Re-enter: ");
            title = scanner.nextLine().trim();
        }

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

        System.out.print("3. Enter Time (HH:MM, Required): ");
        time = scanner.nextLine().trim();
        while (time.isEmpty()) {
            System.out.print("[ERROR] Time is required. Re-enter: ");
            time = scanner.nextLine().trim();
        }

        System.out.print("4. Enter Location (Required): ");
        location = scanner.nextLine().trim();
        while (location.isEmpty()) {
            System.out.print("[ERROR] Location is required. Re-enter: ");
            location = scanner.nextLine().trim();
        }

        category = getCategoryChoice(null);
        
        inclusion = getInclusionChoices(category, new ArrayList<>());
        
        System.out.println("7. Enter Description (Optional). Press Enter to skip.");
        System.out.print("> ");
        description = scanner.nextLine().trim();
        
        int newId = idCounter.getAndIncrement();
        Event newEvent = new Event(newId, title, date, time, location, category, inclusion, description);
        events.add(newEvent);

        System.out.println("\n[SUCCESS] Event added successfully with ID: " + newId);
    }
    
    private String getCategoryChoice(String currentCategory) {
        System.out.println("\n5. Choose Category:");
        for (int i = 0; i < CATEGORIES.length; i++) {
            System.out.printf("   [%d] %s\n", i + 1, CATEGORIES[i]);
        }
        
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
                System.out.println("[INFO] Invalid category number. Using default category.");
                return CATEGORIES[defaultIndex];
            }
        } catch (NumberFormatException e) {
            System.out.println("[INFO] Invalid input (not a number). Using default category.");
            return CATEGORIES[defaultIndex];
        }
    }
    
    private List<String> getInclusionChoices(String category, List<String> currentInclusions) {
        List<String> selected = new ArrayList<>(currentInclusions);
        Map<String, List<String>> categoryOptions = CATEGORY_INCLUSIONS.get(category);
        
        if (categoryOptions == null || categoryOptions.isEmpty()) {
            System.out.println("[INFO] No specific inclusions defined for category: " + category);
            return Collections.emptyList();
        }

        Map<Integer, String> inclusionIndexMap = new HashMap<>();
        int index = 1;

        System.out.printf("\n6. Choose Inclusions for %s (Select up to 10 items):\n", category.toUpperCase());
        System.out.println("-".repeat(50));
        
        for (Map.Entry<String, List<String>> groupEntry : categoryOptions.entrySet()) {
            System.out.printf("  --- %s ---\n", groupEntry.getKey());
            for (String item : groupEntry.getValue()) {
                String status = selected.contains(item) ? "[X]" : "[ ]";
                System.out.printf("   %s [%2d] %s\n", status, index, item);
                inclusionIndexMap.put(index, item);
                index++;
            }
        }
        System.out.println("-".repeat(50));

        String currentList = selected.isEmpty() ? "None" : String.join(", ", selected);
        System.out.printf("   CURRENT SELECTIONS (%d/%d): %s\n", selected.size(), 10, currentList);
        System.out.println("   Enter numbers to toggle (e.g., '1 5 10'), or 'D' when Done:");
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("D") || input.isEmpty()) {
                if (selected.isEmpty()) {
                    System.out.println("[ERROR] You must select at least one inclusion to proceed. Enter selection numbers or 'D' to finish if you have selections.");
                    continue;
                }
                break;
            }

            try {
                String[] parts = input.split("\\s+");
                for (String part : parts) {
                    if (part.isEmpty()) continue;
                    int selectedIndex = Integer.parseInt(part);
                    String item = inclusionIndexMap.get(selectedIndex);

                    if (item != null) {
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
                        System.out.println("[INFO] Invalid number: " + part + ". Please enter a number between 1 and " + (index - 1) + ".");
                    }
                }
                
                currentList = selected.isEmpty() ? "None" : String.join(", ", selected);
                System.out.printf("   [Current selections (%d/10): %s]\n", selected.size(), currentList);

            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid input. Please enter numbers separated by spaces or 'D'.");
            }
        }
        return selected;
    }

    private void viewEvents() {
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

        String header = String.format("| %-5s | %-25s | %-10s | %-5s | %-15s | %-15s |",
            "ID", "Reservation Name", "Date", "Time", "Location", "Category"
        );
        System.out.println(header);
        System.out.println("-".repeat(80));

        for (Event event : events) {
            System.out.println(event);
        }
        System.out.println("=".repeat(80));
    }

    private Event findEventById(int id) {
        return events.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    private int getValidEventId(String action) {
        System.out.print("Enter the ID of the event to " + action + " (or 0 to cancel): ");
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return -1; 
            int id = Integer.parseInt(input);
            return id;
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid input. Please enter a valid number for the ID.");
            return -1;
        }
    }

    private void updateEvent() {
        viewEvents();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("--- UPDATE EXISTING EVENT ---");
        
        int eventId = getValidEventId("update");
        if (eventId <= 0) {
            if (eventId == 0) {
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

        System.out.printf("1. Reservation Name (Current: %s): ", eventToUpdate.getTitle());
        String title = scanner.nextLine().trim();
        if (!title.isEmpty()) eventToUpdate.setTitle(title);

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

        System.out.printf("3. Time (HH:MM, Required. Current: %s): ", eventToUpdate.getTime());
        String time = scanner.nextLine().trim();
        if (!time.isEmpty()) eventToUpdate.setTime(time);
        
        System.out.printf("4. Location (Required. Current: %s): ", eventToUpdate.getLocation());
        String location = scanner.nextLine().trim();
        if (!location.isEmpty()) eventToUpdate.setLocation(location);
        
        String oldCategory = eventToUpdate.getCategory();
        String newCategory = getCategoryChoice(oldCategory);
        eventToUpdate.setCategory(newCategory);
        
        List<String> startingInclusions = oldCategory.equals(newCategory) ? eventToUpdate.getInclusion() : new ArrayList<>();
        List<String> newInclusion = getInclusionChoices(newCategory, startingInclusions);
        eventToUpdate.setInclusion(newInclusion);

        String currentDescriptionDisplay = eventToUpdate.getDescription().isEmpty() ? "None" : eventToUpdate.getDescription().replaceAll("\n", " / ");
        System.out.printf("7. Description (Current: %s): ", currentDescriptionDisplay);
        System.out.println("\nEnter new description (or just press Enter to keep current):");
        System.out.print("> ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) eventToUpdate.setDescription(description);

        System.out.println("\n[SUCCESS] Event ID " + eventId + " updated successfully.");
    }

    private void deleteEvent() {
        viewEvents();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("--- DELETE EVENT ---");

        int eventId = getValidEventId("delete");
        if (eventId <= 0) {
            if (eventId == 0) {
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
                System.out.println("\n[ERROR] Failed to delete event ID " + eventId + ". Event may have been modified or already removed.");
            }
        } else {
            System.out.println("\n[INFO] Deletion cancelled.");
        }
    }
    
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

        Map<String, List<String>> categoryOptions = CATEGORY_INCLUSIONS.getOrDefault(event.getCategory(), Collections.emptyMap());

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
        System.out.printf("INCLUDED SERVICES (%d/10 Selected):\n", event.getInclusion().size());
        
        if (event.getInclusion().isEmpty()) {
            System.out.println("  None selected (required for booking).");
        } else {
            for (Map.Entry<String, List<String>> groupEntry : categoryOptions.entrySet()) {
                List<String> selectedInGroup = groupEntry.getValue().stream()
                        .filter(item -> event.getInclusion().contains(item))
                        .collect(Collectors.toList());
                
                if (!selectedInGroup.isEmpty()) {
                    System.out.printf("  [-- %s --]\n", groupEntry.getKey());
                    selectedInGroup.forEach(item -> System.out.printf("    - %s\n", item));
                }
            }
        }
        
        System.out.println("-".repeat(60));
        System.out.println("Description:");
        String descriptionText = event.getDescription().isEmpty() ? "  (No description provided)" : event.getDescription();
        String[] words = descriptionText.split(" ");
        StringBuilder wrappedText = new StringBuilder("  ");
        int lineLength = 2;
        for (String word : words) {
            if (lineLength + word.length() + 1 > 58) {
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


    public static void main(String[] args) {
        EventOrganizerConsole app = new EventOrganizerConsole();
        app.run();
    }
}
