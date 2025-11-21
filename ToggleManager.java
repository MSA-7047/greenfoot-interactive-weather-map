import java.util.ArrayList;
import java.util.Arrays;

/**
 * The ToggleManager class is responsible for keeping track of the active toggle buttons in the program. The active toggles are stored as 
 * Strings in an ArrayList, which can be modified by the methods in this class. The elements stored in this ArrayList determine which toggle 
 * buttons are already active as soon as the "Run" button is pressed.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class ToggleManager {
    private ArrayList<String> activeToggles = new ArrayList<>(Arrays.asList("Description", "Temperature")); // Dynamically updates
    
    /**
     * Returns all the currently active toggle buttons.
     * 
     * @return   the ArrayList containing the name of every active toggle button
     */
    public ArrayList<String> getActiveToggles() {
        return new ArrayList<>(activeToggles);
    }
    
    /**
     * Returns the size of activeToggles.
     * 
     * @return  the number of elements in activeToggles
     */
    public int getSize() {
        return activeToggles.size();
    }
    
    /**
     * Returns whether or not the state of a toggle button is currently active.
     * 
     * @return  a boolean value determining whether or not a toggle is active
     */
    public boolean isActive(String toggleName) {
        return activeToggles.contains(toggleName);
    }

    /**
     * Adds the toggle button name to the ArrayList of currently active toggle buttons.
     */
    public void addToggle(String key) {
        activeToggles.add(key);
    }
    
    /**
     * Adds the toggle button name to the ArrayList of currently active toggle buttons.
     */
    public void removeToggle(String key) {
        activeToggles.remove(key);
    }
}