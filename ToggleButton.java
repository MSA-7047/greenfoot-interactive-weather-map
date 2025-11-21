import greenfoot.*;

/**
 * The ToggleButton class is used to dynamically update the information displayed by the CurrentWeatherDisplay class. The class uses the 
 * name of the button as a key to determine the data to be displayed. It also contains a boolean variable to determine the state of the 
 * toggle. When the toggle is clicked, the data in the display will update to match the state of the toggle.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class ToggleButton extends Button {
    // Name and state of the toggles
    private final String key;
    private boolean isActive;
    
    // Colors and Font
    private static final Color ACTIVE_COLOR = Color.GREEN;
    private static final Color HOVER_ACTIVE_COLOR = new Color(50, 205, 50); // Dark green
    private static final Color INACTIVE_COLOR = Color.LIGHT_GRAY;
    private static final Color HOVER_INACTIVE_COLOR = Color.YELLOW;
    private static final Font TOGGLE_FONT = new Font("Arial", 10); // Override font

    /**
     * Constructor for objects of class ToggleButton.
     * 
     * @param   key         the name of the toggle
     * @param   isActive    a boolean value determining whether or not the togggle is active
     */
    public ToggleButton(String key, boolean isActive) {
        super(110, 25, key);
        this.key = key;
        this.isActive = isActive;
        
        Color color = getCurrentColor();
        updateButtonVisuals(color);
    }
    
    /**
     * Overrides the onClick() method to update the weather display with the corresponding weather metric of the toggle button.
     */
    @Override
    protected void onClick() {
        setActive(!isActive);

        // Get the CurrentWeatherDisplay object and update it
        MapScreen world = (MapScreen) getWorld();
        CurrentWeatherDisplay weatherDisplay = world.getWeatherDisplay();

        weatherDisplay.updateWeatherFromToggle(key);
    }
    
    /**
     * Sets the isActive field to true or false.
     * The color of the toggle is automatically updated when changing its state.
     * 
     * @param   active      the new boolean value of the isActive field.
     */
    public void setActive(boolean active) {
        this.isActive = active;
        updateButtonVisuals(getCurrentColor());
    }

    /**
     * Overrides the updateButtonVisual() method to update the look of the toggle and make sure it matches the data shown on the display.
     * Active toggles are green and end with "[ON]" whereas inactive toggles are light grey and end in "[OFF]".
     * 
     * @param   backgroundColor     the new background color of the toggle
     */
     @Override
    protected void updateButtonVisuals(Color backgroundColor) {
        // Updates the background color
        button.setColor(backgroundColor);
        button.fill();

        // Draws the border
        button.setFont(TOGGLE_FONT);
        button.setColor(TEXT_COLOR);
        button.drawRect(0, 0, width - 1, height - 1);

        // Draw the text based on the toggle state
        String text = key + (isActive ? " [ON]" : " [OFF]");
        button.drawString(text, 5, 15);

        setImage(button);
    }
    
    /**
     * Override the checkMouseHover() method to highlight the toggle in different colors depending on its state.
     * Active toggles will be highlighted in a darker green color whereas inactive toggles will be highlighted in yellow.
     * If the mouse is not hovering over the toggle then the color will not change.
     */
    @Override
    protected void checkMouseHover() {
        if (!isPressed) {   // Only changes color if the button is not already being pressed
            if (Greenfoot.mouseMoved(this)) {
                if (!isHovered) {
                    isHovered = true;
                    updateButtonVisuals(isActive ? HOVER_ACTIVE_COLOR : HOVER_INACTIVE_COLOR);
                }
            } else if (Greenfoot.mouseMoved(null) && isHovered) {
                isHovered = false;
                updateButtonVisuals(getCurrentColor());
            }
        }
    }
    
    /**
     * Returns the correct color based on the toggle's current state.
     * 
     * @return  the current color of the toggle
     */
    private Color getCurrentColor() {
        return isActive ? ACTIVE_COLOR : INACTIVE_COLOR;
    }
}