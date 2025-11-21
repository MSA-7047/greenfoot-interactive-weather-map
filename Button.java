import greenfoot.*;

/**
 * The Button class is the parent class responsible for providing some common functionality across the buttons in the program. Objects
 * created from classes that extend from this class will automatically be able to handle mouse clicks and hovering effects. These 
 * subclases must override the onClick() method in this class to suit their purpose.
 *  
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public abstract class Button extends Actor {
    // Button image, dimensions and text
    protected GreenfootImage button;
    protected int width;    
    protected int height;
    protected String buttonText;
    
    // Tracks the states of buttons
    protected boolean isHovered = false;
    protected boolean isPressed = false;
    
    // Colors and Font
    protected static final Color TEXT_COLOR = Color.BLACK;
    protected static final Color INACTIVE_COLOR = Color.LIGHT_GRAY;
    protected static final Color HOVER_COLOR = Color.YELLOW;
    protected static final Color PRESSED_COLOR = Color.ORANGE;
    protected static final Font NORMAL_FONT = new Font("Arial", true, false, 14);
    
    /**
     * Constructor for objects of class Button.
     * 
     * @param   width   the width of the button
     * @param   height  the height of the button
     * @param   text    the text to be displayed on the button
     */
    public Button(int width, int height, String text) {
        this.width = width;
        this.height = height;
        this.buttonText = text;
        
        // Creates a new button image using the width and height
        button = new GreenfootImage(width, height);
        updateButtonVisuals(INACTIVE_COLOR);
    }
    
    /**
     * Checks if the button has been clicked or hovered whenever the 'Act' or 'Run' button gets pressed in the environment.
     * The methods displayed in act() are responsible for making the checks and executing the result of the check.
     */
    public void act() {
        checkMouseClickInput();
        checkMouseHover();
    }
    
    /**
     * Overrides the setLocation() method in the Actor class to stop the buttons from being dragged when the program is paused.
     */
    @Override
    public void setLocation(int x, int y) {
        // Stops movement by not executing any code
    }
    
    /**
     * Checks if the button has been clicked by the user.
     * If it has then the button wil change to PRESSED_COLOR and wait for 10 frames to clearly show the button has been clicked.
     * The subclasses define the functionality in the onClick() method.
     */
    private void checkMouseClickInput() {
        if (Greenfoot.mouseClicked(this)) {
            updateButtonVisuals(PRESSED_COLOR);
            Greenfoot.delay(10);
            updateButtonVisuals(isHovered ? HOVER_COLOR : INACTIVE_COLOR);
            onClick();  // Calls the abstract method for the functionality specific to each subclass
        }
    }

    /**
     * Checks if the mouse is hovering over the button.
     * If it is then the background color of the button will change to HOVER_COLOR.
     * If it is not then the button will revert to INACTIVE_COLOR.
     */
    protected void checkMouseHover() {
        if (!isPressed) {   // Only changes color if the button is not already being pressed
            if (Greenfoot.mouseMoved(this)) {
                if (!isHovered) {
                    isHovered = true;
                    updateButtonVisuals(HOVER_COLOR);
                }
            } else if (Greenfoot.mouseMoved(null) && isHovered) {   // If the mouse is not hovering anymore
                isHovered = false;
                updateButtonVisuals(INACTIVE_COLOR);
            }
        }
    }
    
    /**
     * Updates the look of the button.
     * The background color is dependent on the color inputted as a parameter.
     * 
     * @param   backgroundColor     the new background color of the button
     */
    protected void updateButtonVisuals(Color backgroundColor) {
        // Filling the background
        button.setColor(backgroundColor);
        button.fill();
        
        // Drawing the borders and the text
        button.setColor(TEXT_COLOR);
        button.drawRect(0, 0, width - 1, height - 1);
        button.setFont(NORMAL_FONT);
        button.drawString(buttonText, 10, 20);
        
        setImage(button);
    }
    
    /**
     * Executed when the mouse is clicking the button.
     * Subclasses must override this method to perform their specific functionality.
     */
    protected abstract void onClick();
}