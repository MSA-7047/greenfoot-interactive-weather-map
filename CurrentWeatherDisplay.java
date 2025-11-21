import greenfoot.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * The CurrentWeatherDisplay class is responsible for displaying current weather information from the OpenWeatherMap API onto the screen.
 * It contains methods to update the information on the display from clicking the mouse on the MapScreen world and clicking on the toggle
 * buttons. The vertical dimensions of CurrentWeatherDisplay objects are updated dynamically based on the number of active toggles. 
 * Objects of this class can be dragged around the screen during runtime.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class CurrentWeatherDisplay extends Actor {   
    // Objects to be initialised in the constructor
    private final CurrentWeatherFetcher fetcher;
    private final ToggleManager toggleManager;
    
    private City selectedCity;
    private CurrentWeatherData currentWeather;
    
    // Width of the display
    private static final int WIDTH = 280;
    
    // Colors and Fonts
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Font NORMAL_FONT = new Font("Monospaced", false, false, 12);
    private static final Font BOLD_FONT = new Font("Monospaced", true, false, 12);
    
    /**
     * Constructor for objects of class CurrentWeatherDisplay.
     * 
     * @param   fetcher         the CurrentWeatherFetcher object
     * @param   toggleManager   the ToggleManager object
     */
    public CurrentWeatherDisplay(CurrentWeatherFetcher fetcher, ToggleManager toggleManager) {
        this.fetcher = fetcher;
        this.toggleManager = toggleManager;
        updateDisplay();
    }
    
    /**
     * Returns the ToggleManager object being stored and by this object.
     * 
     * @return  the ToggleManager object used to track the active toggles
     */
    public ToggleManager getToggleManager() {
        return toggleManager;
    }
    
    /**
     * Returns the selected City object being stored by this object.
     * 
     * @return  a City object
     */
    public City getSelectedCity() {
        return selectedCity;
    }
    
    /**
     * Allows for the user to drag the display around the screen whenever the 'Act' or 'Run' button gets pressed or executed in
     * the environment.
     */
    public void act () {
        checkMouseDrag();
    }
    
    /**
     * Checks if the mouse is attempting to drag the weather display.
     * If the mouse is attempting to drag the weather display then the display moves based on the mouse's new positions.
     */
    private void checkMouseDrag() {
        if (Greenfoot.mouseDragged(this)) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            this.setLocation(mouse.getX(), mouse.getY());
        }
    }
    
    /**
     * Updates the most recent city selected, retrieves data from that city and showcases the retrieved data on the display.
     * Called after clicking anywhere on the map.
     * 
     * @param   city    the city that the data will be retrieved from
     */
    public void displayWeatherFor(City city) {
        selectedCity = city;    // The given city becomes the new city
        currentWeather = fetcher.getWeatherData(selectedCity.getName());
        updateDisplay();
    }
    
    /**
     * Updates the ArrayList of active keys being used by the display based on whether the most recent toggle is in the list.
     * The display is updated to match the new ArrayList of active keys.
     * Called whenever a ToggleButton object is clicked.
     * 
     * @param   key    the key of the toggle that was clicked
     */
    public void updateWeatherFromToggle(String key) {
        // Updates the list of active keys
        if (toggleManager.isActive(key)) {
            toggleManager.removeToggle(key);
        } else {
            toggleManager.addToggle(key);
        }
        
        updateDisplay();    // Dynamically updates the display
    }
    
    /**
     * Updates the display of the CurrentWeatherDisplay object being used by the MapScreen.
     * Only the data where the corresponding toggles are active will be displayed.
     */
    private void updateDisplay() {        
        // Adjust the height based on the number of active toggles
        int height = 30 + (toggleManager.getSize() * 20);
        GreenfootImage display = new GreenfootImage(WIDTH, height);
        
        // Adds the background color and borders around the display
        display.setColor(BACKGROUND_COLOR);
        display.fill();
        display.setColor(TEXT_COLOR);
        display.drawRect(0, 0, display.getWidth() - 1, display.getHeight() - 1);    // Draws a border around the display
        
        if (selectedCity == null) {     // Map has not been clicked yet
            display.setFont(BOLD_FONT);
            display.drawString("Click on the map to display data.", 25, 15);
        } else {    // Map has been clicked at least once
            display.setFont(BOLD_FONT);
            display.drawString("Nearest City:", 10, 15);
            display.setFont(NORMAL_FONT);
            display.drawString(selectedCity.getName(), 125, 15);
            
            // Draws weather info for active keys
            int y = 40;
            for (String key : toggleManager.getActiveToggles()) {
                display.setFont(BOLD_FONT);
                display.drawString(key + ":", 10, y);    // First draws the title
                
                display.setFont(NORMAL_FONT);
                display.drawString(getWeatherValue(key), 125, y);   // Then draws the data
                
                y += 20;
            }
        }
        
        setImage(display);
    }
    
    /**
     * Modifies the weather information from the API so it can be seen as a readable string.
     * 
     * @param   key     the key of the toggle determining which String will be returned
     * @return          a human-readable String which holds some information from the API
     */
    private String getWeatherValue(String key) {
        switch (key) {
            case "Timestamp":   return convertUnixTimeToString(currentWeather.getTimestamp(), true, true);
            case "Description": return currentWeather.getWeatherDescription();
            case "Temperature": return currentWeather.getTemperature() + " °C";
            case "Feels Like":  return currentWeather.getFeelsLike() + " °C";
            case "Humidity":    return currentWeather.getHumidity() + "%";
            case "Wind Speed":  return currentWeather.getWindSpeed() + " m/s";
            case "Rain Volume": return currentWeather.getRainVolume() == 0.0 ? "No data" : currentWeather.getRainVolume() + "mm/h";
            case "Sunrise":     return convertUnixTimeToString(currentWeather.getSunrise(), false, true);
            case "Sunset":      return convertUnixTimeToString(currentWeather.getSunset(), false, true);
            
            default: return "No data";
        }
    }
    
    /**
     * Converts the time returned by the API from the Unix timestamp system into a readable String.
     * 
     * @param   timestamp   the Unix timestamp of the time data received from the API
     * @param   ddmmyyyy    a boolean value determining whether the outputted String will contain the date, month and year in the dd.MM.yyyy format
     * @param   hhmmss      a boolean value determining whether the outputted String will contain the hours, minutes and seconds in the HH:mm:ss format
     * @return              the converted String of the time in the specified format
     */
    private String convertUnixTimeToString(long timestamp, boolean hasDate, boolean hasTime) {
        // Converts to LocalDateTime in UTC
        LocalDateTime dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.UTC).toLocalDateTime();
        String layout = String.join(" ", hasDate ? "dd/MM/yyyy" : "", hasTime ? "HH:mm:ss" : "").trim();

        return dateTime.format(DateTimeFormatter.ofPattern(layout));
    }
}