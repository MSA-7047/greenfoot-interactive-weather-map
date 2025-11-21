import greenfoot.*;

/**
 * The KeyIcon class is used to display small images of keys from the keyboard onto the MapScreen and GraphScreen worlds. 
 * These icons are highlighted with a yellow background when the corresponding keyboard keys are pressed. When the keys are
 * not being pressed, the icons will be colored with a grey background.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class KeyIcon extends Actor {
    // Key name
    private String key;  
    
    // Icon images
    private GreenfootImage defaultImage;
    private GreenfootImage pressedImage;
    private GreenfootImage blockedImage;
    
    // Colors
    private static final Color DEFAULT_COLOR = Color.GRAY;
    private static final Color PRESSED_COLOR = Color.YELLOW;
    private static final Color INACTIVE_COLOR = Color.BLACK;
    
    private boolean isActive = true; // Tracks if the key should be active

    /**
     * Constructor for objects of class MapScreen.
     * 
     * @param   key     the name of the key image in the images folder
     */
    public KeyIcon(String key) {
        this.key = key;
        
        // Creates a new icon image
        GreenfootImage icon = new GreenfootImage(key + "-key.png");
        icon.scale(30, 30);

        // Determines whether or not the key is an arrow key
        boolean isArrowKey = key.equals("up") || key.equals("down") || key.equals("right") || key.equals("left");

        // Creates 3 images for the different states of the icon
        defaultImage = createIcon(icon, DEFAULT_COLOR, isArrowKey);
        pressedImage = createIcon(icon, PRESSED_COLOR, isArrowKey);
        blockedImage = createIcon(icon, INACTIVE_COLOR, isArrowKey);
        
        setImage(defaultImage);
    }
    
    /**
     * Returns the name of the key aassociated with this icon.
     * 
     * @return  the name of the key associated with the icon
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Sets the icon image to pressedImage if the key is active.
     */
    public void setPressedImage() {
        if (!isActive) {
            return;     // Block if inactive
        }
        setImage(pressedImage);
    }

    /**
     * Sets the icon image to defaultImage if the key is active.
     */
    public void setDefaultImage() {
        if (!isActive) {
            return;     // Block if inactive
        }
        setImage(defaultImage);
    }

    /**
     * Sets the icon image to blockedImage.
     */
    public void setBlockedImage() {
        setImage(blockedImage);
    }

    /**
     * Sets the isActive field based on the inputted parameter.
     * If isActive is falee then the icon image will be set to blockedImage.
     * If isActive is true then the icon image will be set to defaultImage.
     * 
     * @param   active  the new boolean value for isActive
     */
    public void setActive(boolean active) {
        isActive = active;
        if (!active) {
            setBlockedImage(); // Immediately turns black when inactive
        } else {
            setDefaultImage();
        }
    }
    
    /**
     * Constantly calls checkArrowKeyInput() whenever the 'Act' or 'Run' button gets pressed or executed in the environment.
     */
    public void act() {
        checkArrowKeyInput();
    }

    /**
     * Checks if the assigned arrow key is currently being pressed.
     * If the icon is inactive then it displays the blocked image and exits the method.
     * If the icon is active then it updates the icon image to pressedImage when the key is pressed.
     * The key icon returns to its defualt image when the key is not pressed.
     */
    private void checkArrowKeyInput() {
        if (!isActive) {
            setImage(blockedImage); // Keep blocked if inactive
            return;
        }

        if (Greenfoot.isKeyDown(key)) {
            setImage(pressedImage);
        } else {
            setImage(defaultImage);
        }
    }
    
    /**
     * Creates an icon image using the icon and a background color.
     * A rectangle or an oval will be drawn depending on the isOval parameter.
     * The background is filled with the specified color before drawing the inputted icon onto the background.
     * 
     * @param   icon        the image to be placed on the icon
     * @param   color       the background color of the icon
     * @param   isOval      a boolean value determining whether or not the background shape is an oval or a rectangle
     * @return              a new GreenfootImage object combining the background and icon
     */
    private GreenfootImage createIcon(GreenfootImage icon, Color color, boolean isOval) {
        // Calculates the width and height of the icon
        int width = icon.getWidth();
        int height = icon.getHeight();
        
        // Creates a new icon image
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(color);
        
        // Creates the icon shape
        if (isOval) {
            image.fillOval(0, 0, width - 1, height - 1);
        } else {
            image.fillRect(2, 2, width - 3, height - 3);
        }

        image.drawImage(icon, 0, 0);
        return image;
    }

    /**
     * Overrides the setLocation() method in the Actor class to stop the icons from being dragged when the program is paused.
     */
    @Override
    public void setLocation(int x, int y) {
        // Stops movement by not executing any code
    }
}