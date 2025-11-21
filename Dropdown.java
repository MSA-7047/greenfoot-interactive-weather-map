import greenfoot.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Dropdown class acts as a dropdown tool allowing the user to click on it to change the data being displayed on the line graph.
 * This class works with the DropdownOption class to provide all the necessary functionality to switch between temperature data and
 * feels like data. This class also extends the Button class which gives it the same clicking and highlighting functionality as the
 * Button subclasses.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class Dropdown extends Button {
    private GraphScreen graphScreen;
    
    // Options for DropdownOption
    private ArrayList<String> graphTypes = new ArrayList<>(Arrays.asList("Temperature", "Feels Like"));
    
    // Dropdown state
    private boolean isOpen = false;

    /**
     * Constructor for objects of class Dropdown.
     * 
     * @param   graphScreen     the GraphScreen world
     */
    public Dropdown(GraphScreen graphScreen) {
        super(170, 30, "- Select Graph Here -");    // Superclass constructor
        this.graphScreen = graphScreen;
    }

    /**
     * Selects a data type from the dropdown options.
     * The Dropdown text will be updated to display the new graph data metric before closing the dropdown.
     * 
     * @param   type    the new weather metric type
     */
    public void selectOption(String type) {
        graphScreen.setGraphType(type);
        buttonText = "Graph: " + type; // Updates the text of the main dropdown tool

        Color currentColor = isHovered ? HOVER_COLOR : INACTIVE_COLOR;  // Chooses the correct background color
        updateButtonVisuals(currentColor);  // Forces the dropdown to redraw with the new text
        closeDropdown();
    }
    
    /**
     * Toggles between opening and closing the dropdown depending on the current state of the dropdown.
     */
    @Override
    protected void onClick() {
        if (isOpen) {   // Closes the dropdown if it is being clicked when already open
            closeDropdown();
        } else {
            openDropdown();
        }
    }

    /**
     * Opens the dropdown by creating DropdownOption objects and displaying them underneath this Dropdown object.
     */
    private void openDropdown() {
        isOpen = true;
        for (int i = 0; i < graphTypes.size(); i++) {   // Iterates through all the elements in graphType
            // Creates a DropdownOption and adds it below the previous one
            DropdownOption option = new DropdownOption(this, graphTypes.get(i), width, height);
            graphScreen.addObject(option, getX(), getY() + (i + 1) * height);
        }
    }

    /**
     * Closes the dropdown by removing the dropdown elements underneath.
     * The isOpen field is updated to reflect this change and the DropdownOption objects are deleted from the world.
     */
    private void closeDropdown() {
        isOpen = false;
        graphScreen.removeObjects(graphScreen.getObjects(DropdownOption.class));
    }
}