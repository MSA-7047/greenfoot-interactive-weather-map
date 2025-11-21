import greenfoot.*;

/**
 * The DropdownOption class supports the Dropdown class to allows users to change the data being displayed on the line graph. Clicking 
 * on this object will select the data type and display the upcoming forecast of that data onto the graph. This class also extends the
 * Button class which gives it the same highlight functionality as the other Button subclasses.
 *  
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class DropdownOption extends Button {
    // The main dropdown
    private Dropdown dropdown;
    
    // The label of the option 
    private String label;

    /**
     * Constructor for objects of class DropdownOption.
     * 
     * @param   dropdown    the main Dropdown object
     * @param   label       the name of the DropdownOption
     * @param   width       the width of the object
     * @param   height      the height of the object 
     */
    public DropdownOption(Dropdown dropdown, String label, int width, int height) {
        super(width, height, label);    // Superclass constructor
        this.dropdown = dropdown;
        this.label = label;
    }

    /**
     * Selects the corresponding data type based on the options's label.
     */
    @Override
    protected void onClick() {
        dropdown.selectOption(label);
    }
}