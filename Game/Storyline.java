package Game;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Storyline implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ArrayList<String> Events;
    private int current;

    public Storyline(String path) {
        Events = new ArrayList<>();
        current = 0;

        if (!(new File(path).exists())) {
            System.out.println("File not found: " + path);
            return;
        }

        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Events.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading storyline file: " + e.getMessage());
        }
    }


    public String getCurrentEvent() {
        if (current < 0 || current >= Events.size()) {
            return "No more events.";
        }
        return Events.get(current);
    }

    public String nextEvent() {
        if (current + 1 >= Events.size()) {
            return "No more events.";
        }
        current++;
        return Events.get(current);
    }

    

    public void setCurrent(int index) {
        if (index >= 0 && index < Events.size()) {
            current = index;
        }
    }

    public int getCurrentIndex() {
        return current;
    }

    public int getLength() {
        return Events.size();
    }

    public String getEvent(int index) {
        if (index < 0 || index >= Events.size()) {
            return "No more events.";
        }
        this.current = index; 
        return Events.get(index);
    }

    
}