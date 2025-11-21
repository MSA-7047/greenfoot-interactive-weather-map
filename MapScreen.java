import greenfoot.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The MapScreen class is the central class for this program. When this class is initialised and running as an object, it will always
 * check if any keyboard or mouse inputs have occured. Pressing an up or down arrow key will zoom in and out of the map on the screen. 
 * Pressing the W, A, S and D keys will pan the map up, left, down and right respectively. Clicking anywhere on the map will display 
 * current weather information from the nearest marked city with respect to the position of the mouse click. Clicking on any toggles 
 * will update the information shown on the weather display based on the status of the toggles. Pressing the right arrow key when fully
 * zoomed in and having the selected city visible on the screen will allow the user to switch to the GraphScreen world. 
 * 
 * @author Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class MapScreen extends World {
    // Map coordinates
    private final double TOP_LAT = 61.10;  
    private final double BOTTOM_LAT = 49.00;  
    private final double LEFT_LON = -10.48;  
    private final double RIGHT_LON = 1.77;  
    
    // Stores the cities from the text file
    private ArrayList<City> cities = new ArrayList<>();
    
    // Stores the most recent mouse clicks and selected cities
    private double lastMouseLat = 0, lastMouseLon = 0;
    
    // Map image and weather display objects yet to be initialised
    private GreenfootImage map;
    private CurrentWeatherDisplay weatherDisplay;
    
    // Zoom and pan figures
    private double zoomFactor = 1.0;
    private final double MIN_ZOOM = 1.0;    // Prevents zooming out smaller than original size
    private final double MAX_ZOOM = 5.0; 
    private int currentMapWidth, currentMapHeight;  // Tracks the zoomed map dimensions

    // Tracks the horizontal and vertical panning offsets
    private int offsetX = 0;
    private int offsetY = 0;
    
    // Graph Screen
    private GraphScreen graphScreen;
    
    // Colors
    private static final Color SEA_COLOR = new Color(22,187,255);   // Custom blue
    private static final Color MOUSE_CLICK_COLOR = Color.YELLOW;
    private static final Color CITY_COLOR = Color.WHITE;
    private static final Color HIGHLIGHTED_CITY_COLOR = Color.PINK;
    private static final Color LINE_COLOR = Color.YELLOW;
    
    
    /**
     * Constructor for objects of class MapScreen.
     */
    public MapScreen() {
        super(471, 788, 1); // Sets the size of the screen to the dimensions of the map image
        
        // Creates the map and the city markers
        map = new GreenfootImage("united-kingdom.png");   // Set the UK map as the background
        loadCitiesFromFile("cities.txt");
    
        // Initialises a CurrentWeatherDisplay object and adds it to the screen
        weatherDisplay = new CurrentWeatherDisplay(new CurrentWeatherFetcher(), new ToggleManager());
        addObject(weatherDisplay, 145, 110);
        
        // Adds the remaining actors to the screen
        addTogglesToScreen();
        addKeyIconsToScreen();
        
        // Draws all map and city markers onto the screen
        redraw();
    }
    
    /**
     * Returns the CurrentWeatherDisplay object being used by the MapScreen.
     * 
     * @return  the WeatherDisplay object stored in the MapScreen world
     */
    public CurrentWeatherDisplay getWeatherDisplay() {
        return weatherDisplay;
    }
    
    /**
     * Checks for any use mouse clicks and keyboard button presses whenever the 'Act' or 'Run' button gets pressed or 
     * executed in the environment.
     */
    public void act() {
        // Constantly checking for keyboard and mouse inputs
        handleMouseClickInput();
        handleArrowKeyInput();
        handleWASDKeyInput();
        redraw();
    }
    
    /**
     * Loads the text file containing city names, latitude coordinates and longitude coordinates and parses through them.
     * The name, latitude and longitude are encapsulated into City objects.
     * These City objects are placed into an ArrayList containing every City object.
     * 
     * @param   filename    the name of the .txt file
     */
    private void loadCitiesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {    // Loads the file so it can be read
            String line;
            
            while ((line = reader.readLine()) != null) {    // Reads the file line by line until the end
                String[] parts = line.split(",");
                
                if (parts.length == 3) {    // Separates the line into thirds
                    String name = parts[0].trim();
                    double latitude = Double.parseDouble(parts[1].trim());
                    double longitude = Double.parseDouble(parts[2].trim());
                    cities.add(new City(name, latitude, longitude));    // Adds the variables to a new City object
                }
            }
        } catch (IOException e) {   // Outputs a message if the file cannot be found or if the file cannot be read
            System.out.println("Error reading cities file: " + e.getMessage());
        }
    }
    
    /**
     * Creates an array of toggle names and positions, initialises ToggleButton objects and places them onto the top right of the screen.
     * The number of toggles is determined by the size of the array.
     */
    private void addTogglesToScreen() {
        String[] toggleNames = {"Timestamp", "Description", "Temperature", 
                                "Feels Like", "Humidity", "Wind Speed", 
                                "Rain Volume", "Sunrise", "Sunset"};
                                
        ToggleManager toggleManager = weatherDisplay.getToggleManager();    // Used to determine which toggles are active
        
        int posX = 410;
        int posY = 20;
        int spacing = 30;
        
        for (String name : toggleNames) {
            // Creates and places every ToggleButtonobject
            boolean isActive = toggleManager.isActive(name); 
            ToggleButton button = new ToggleButton(name, isActive);
            addObject(button, posX, posY);
            posY += spacing;
        }
    }
    
    /**
     * Creates the keyboard buttons and adds them to the screen using 2 arrays.
     * The first array contains the letters and arrows that will be displayed on the screen.
     * The second array contains a 2D array containing x and y coordinates for the positions of each key on the screen.
     */
    private void addKeyIconsToScreen() {
        // Defines the names of each key
        String[] keys = {"w", "a", "s", "d", "up", "down", "right"};
    
        // Stores the positions of each key in a 2D array
        int[][] positions = {
            {60, 730},  // W
            {20, 770},  // A
            {60, 770},  // S
            {100, 770}, // D
            {420, 730}, // Up arrow
            {420, 770}, // Down arrow
            {450, 750}  // Right arrow
        };
    
        // Loops through both arrays to add each key to their respective positions
        for (int i = 0; i < keys.length; i++) {
            addObject(new KeyIcon(keys[i]), positions[i][0], positions[i][1]);
        }
        
        updateKeyIcons();
    }
    
    /**
     * Uses the pixel coordinates of the user's mouse click to identify the nearest city on the map.
     * After the nearest city has been found, the map and its markers are redrawn.
     * The CurrentWeatherDisplay object updates to display the current weather data for the new city.
     */
    private void handleMouseClickInput() {
        if (Greenfoot.mouseClicked(this)) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {    // Makes sure the mouse is on the screen
                // Calculates the latitude and longitude coordinates based on the mouse click coordinates
                lastMouseLon = convertPixelXtoLon(mouse.getX());
                lastMouseLat = convertPixelYtoLat(mouse.getY());
                
                City nearest = findNearestCity(lastMouseLat, lastMouseLon);
                if (nearest != null) {
                    // Updates the map and the display
                    weatherDisplay.displayWeatherFor(nearest);
                }
            }
        }
    }
    
    /**
     * Zooms into or zooms out of the map depending on whether the up or down arrow keys have been pressed.
     * Switches to the graph screen if the right arrow key has been pressed, the map is fully zoomed in and 
     * the selected city can be seen on the screen.
     */
    private void handleArrowKeyInput() {
        // Zooming in and out with the up and down arrow keys
        if (Greenfoot.isKeyDown("up")) {  
            zoomFactor += 0.1;
            if (zoomFactor > MAX_ZOOM) {
                zoomFactor = MAX_ZOOM;
            }
        } 
        else if (Greenfoot.isKeyDown("down")) {  
            zoomFactor -= 0.1;
            if (zoomFactor < MIN_ZOOM) {
                zoomFactor = MIN_ZOOM;
            }
            constrainPan();     // keep the map visible when zooming outward from the corners of the screen
        }
    
        // Switching to the graph screen with the right arrow key
        if (Greenfoot.isKeyDown("right") && weatherDisplay.getSelectedCity() != null 
            && zoomFactor == MAX_ZOOM && isCityVisibleOnScreen(weatherDisplay.getSelectedCity())) {
            String city = weatherDisplay.getSelectedCity().getName();
            if (graphScreen == null) {
                graphScreen = new GraphScreen(this, city);  // Create once
            } else {
                graphScreen.updateCity(city);  // Refresh if needed
            }
            Greenfoot.delay(10);
            Greenfoot.setWorld(graphScreen);
        }
        
        // Updates the icon colors
        updateKeyIcons();
    }
    
    /**
     * Pans the map and its markers horizontally or vertically depending on which letter from WASD has been pressed.
     * The offset variables determines the new position of the map and its markers, giving the illusion that the screen
     * is moving.
     */
    private void handleWASDKeyInput() {
        if (Greenfoot.isKeyDown("a")) {         // Move map elements to the right
            offsetX += 20;
        }
        else if (Greenfoot.isKeyDown("d")) {    // Move map elements to the left
            offsetX -= 20;
        }
        else if (Greenfoot.isKeyDown("w")) {    // Move map elements down
            offsetY += 20;
        }
        else if (Greenfoot.isKeyDown("s")) {    // Move map elements up
            offsetY -= 20;
        }
        constrainPan();     // Stops jittering at the edges
        
    }
    
    /**
     * Ensures that the panning functionality does not allow users to keep panning past the map image.
     */
    private void constrainPan() {
        // Calculates the boundaries for panning horizontally and vertically based on the centre of the map
        int offsetLimitX = (currentMapWidth - getWidth()) / 2;
        int offsetLimitY = (currentMapHeight - getHeight()) / 2;
        
        // Makes sure the offset values do not go beyond their limits
        if (offsetX > offsetLimitX) {
            offsetX = offsetLimitX;                 // Left border
        } 
        else if (offsetX < -offsetLimitX) {
            offsetX = -offsetLimitX;                // Right border
        }   
        else if (offsetY > offsetLimitY) {
            offsetY = offsetLimitY;                 // Top border
        }
        else if (offsetY < -offsetLimitY) {
            offsetY = -offsetLimitY;                // Bottom border
        }
    }
    
    /**
     * Redraws the map screen background.
     * First fills the screen with the map, then adds the city markers.
     * The mouse click marker and connecting line are added at the end.
     */
    private void redraw() {
        getBackground().clear();
        drawMap();
        drawCityMarkers();
        int[] newMouseCoords = updateUserClickMarker(); // Draws and returns the new mouse click points
        drawConnectingLine(newMouseCoords); // Draw the line between the mouse click marker and the nearest city marker
    }
    
    /**
     * Draws the sea and the map image onto the background.
     * The map is scaled according to zoomFactor before being drawn onto the screen.
     */
    private void drawMap(){
        GreenfootImage background = new GreenfootImage(getWidth(), getHeight());
        
        // Draws the sea first
        background.setColor(SEA_COLOR);
        background.fill();
        
        // Scales the new map according to the zoomFactor
        GreenfootImage zoomedMap = new GreenfootImage(map);
        zoomedMap.scale((int) (map.getWidth() * zoomFactor), (int) (map.getHeight() * zoomFactor));
        
        // Calculates the new map size
        currentMapWidth = zoomedMap.getWidth();
        currentMapHeight = zoomedMap.getHeight();
        
        // Draws the new map onto the screen
        background.drawImage(zoomedMap, (getWidth() - currentMapWidth) / 2 + offsetX, (getHeight() - currentMapHeight) / 2 + offsetY);
        setBackground(background);
    }
    
    /**
     * Draws the city markers onto the background.
     * All cities are colored white, except for the selected city which is colored red.
     */
    private void drawCityMarkers() {
        GreenfootImage bg = getBackground();
        City selectedCity = weatherDisplay.getSelectedCity();
        for (City city : cities) {  // Loops through every city
            // Calculates screen pixel coordinates with zooms and offsets in mind
            int x = convertLonToPixelX(city.getLongitude());
            int y = convertLatToPixelY(city.getLatitude());
            
            if (city != null) {
                if (city.equals(selectedCity)) {
                    bg.setColor(HIGHLIGHTED_CITY_COLOR);    // Highlights the selected city in red
                } else {
                    bg.setColor(CITY_COLOR);    // Default city color
                }
            } else {
                bg.setColor(CITY_COLOR);    // Set all the markers to the dafault color at the start 
            }
            
            int markerSize = (int) (5 * zoomFactor);
            bg.fillOval(x - markerSize / 2, y - markerSize / 2, markerSize, markerSize);    // Adjusts the oval to be placed in the centre
        }
    }
    
    /**
     * Draws the most recent mouse click on the map.
     * The pixel coordinates of the mouse are caculated using latitude and longitude and are returned as an int[].
     * 
     * @return  the pixel coordinates of the mouse click
     */
    private int[] updateUserClickMarker() {
        GreenfootImage bg = getBackground();
        bg.setColor(MOUSE_CLICK_COLOR);
        
        // Calculates screen pixel coordinates with zooms and offsets in mind
        int x = convertLonToPixelX(lastMouseLon);
        int y = convertLatToPixelY(lastMouseLat);
        
        int markerSize = (int) (5 * zoomFactor);
        bg.fillOval(x - markerSize / 2, y - markerSize / 2, markerSize, markerSize);    // Adjusts the oval to be placed in the centre
        
        return new int[]{x, y};     // Returns the corrected mouse pixel coordinates
    }
        
    /**
     * Draws a line connecting the last mouse click on the map with the nearest city to that mouse click.
     * 
     * @param   mouseCoords     the pixel coordinates of the mouse
     */
    private void drawConnectingLine(int[] mouseCoords) {
        City nearestCity = weatherDisplay.getSelectedCity();
        
        if (nearestCity != null) {
            GreenfootImage bg = getBackground();
            bg.setColor(LINE_COLOR);
            
            // Calculates screen pixel coordinates with zooms and offsets in mind
            int cityX = convertLonToPixelX(nearestCity.getLongitude());
            int cityY = convertLatToPixelY(nearestCity.getLatitude());
            
            bg.drawLine(mouseCoords[0], mouseCoords[1], cityX, cityY);
        }
    }

    /**
     * Uses the inputted City object to determine whether or not that city is visible on the screen.
     * 
     * @param   city    the City object of the city marker being checked to see if it is visible on the screen
     */
    private boolean isCityVisibleOnScreen(City city) {
        // Converts the city coordinates to pixel coodinates on the map
        int pixelX = convertLonToPixelX(city.getLongitude());
        int pixelY = convertLatToPixelY(city.getLatitude());
        
        // Gets the current visible screen bounds
        int screenWidth = getWidth();
        int screenHeight = getHeight();
    
        // Checks if the coordinates are on the screen
        boolean isVisibleX = pixelX >= 0 && pixelX <= screenWidth;
        boolean isVisibleY = pixelY >= 0 && pixelY <= screenHeight;
        return isVisibleX && isVisibleY;
    }
        
    /**
     * Updates the state of the up, down and right arrow key icons based on the current zoom level. 
     * The status of the right arrow key is also dependent on the status of the selected city in the weather display
     * and whether or not that city is currently visible on the screen.
     * The colors displayed for each icon are automatically updated based on the state of each icon.
     */
    private void updateKeyIcons() {
        List<KeyIcon> keyIcons = getObjects(KeyIcon.class); // Gets every KeyIcon object in the world
        
        for (KeyIcon icon : keyIcons) {    
            switch (icon.getKey()) {
                case "right":
                    boolean isActive =  weatherDisplay.getSelectedCity() != null 
                                        && zoomFactor == MAX_ZOOM 
                                        && isCityVisibleOnScreen(weatherDisplay.getSelectedCity());
                    icon.setActive(isActive);
                    break;
                case "up":
                    icon.setActive(zoomFactor < MAX_ZOOM);
                    break;
                case "down":
                    icon.setActive(zoomFactor > MIN_ZOOM);
                    break;
            }
        }
    }
    
    /**
     * Converts a pixel x coordinate into a longitude coordinate.
     * 
     * @param   x       the pixel x coordinate to be converted
     * @return          the longitude coordinate
     */
    private double convertPixelXtoLon(int x) {
        double worldX = ((x - offsetX - getWidth() / 2) / zoomFactor) + getWidth() / 2;     // Ensures that offsets are taken into account
        double longitude = LEFT_LON + ((worldX / (double) getWidth()) * (RIGHT_LON - LEFT_LON));    // Explicity double division
        return longitude;
    }
    
    /**
     * Converts a pixel y coordinate into a latitude coordinate.
     * 
     * @param   y       the pixel y coordinate to be converted
     * @return          the latitude coordinate
     */
    private double convertPixelYtoLat(int y) {
        double worldY = ((y - offsetY - getHeight() / 2) / zoomFactor) + getHeight() / 2;   // Ensures that all offsets are taken into account
        double adjustedY = worldY - 20;
        double latitude = TOP_LAT - ((adjustedY / (double) getHeight()) * (TOP_LAT - BOTTOM_LAT));  // Explicity double division
        return latitude;
    }

    /**
     * Converts a longitude coordinate into a pixel x coordinate.
     * 
     * @param   lon     the longitude coordinate to be converted
     * @return          the pixel x coordinate
     */
    private int convertLonToPixelX(double lon) {
        // Converts the longitude to a pixel x-coordinate on the original map
        int pixelX = (int) ((lon - LEFT_LON) / (RIGHT_LON - LEFT_LON) * getWidth());
        
        // Adjusts the coordinate for zooming and panning
        return (int) ((pixelX - getWidth() / 2) * zoomFactor + getWidth() / 2) + offsetX;
    }

    /**
     * Converts a latitude coordinate into a pixel y coordinate.
     * 
     * @param   lon     the latitude coordinate to be converted
     * @return          the pixel y coordinate
     */
    private int convertLatToPixelY(double lat) {
        // Converts the latitude to a pixel y-coordinate on the original map
        int pixelY = (int) ((TOP_LAT - lat) / (TOP_LAT - BOTTOM_LAT) * getHeight());
        
        // Adjusts the coordinate for zooming and panning
        return (int) ((pixelY - getHeight() / 2) * zoomFactor + getHeight() / 2) + offsetY + (int) (20 * zoomFactor);
    }
    
    /**
     * Calculates the nearest city to a pair of latitude and longitude coordinates using the haversine formula.
     * 
     * @param   lat     the latitude coordinate to be used in the calculation
     * @param   lon     the longitude coordinate to be used in the calculation
     * @return          the nearest city as a City object
     */
    private City findNearestCity(double lat, double lon) {
        // Initial values
        City nearestCity = null;
        double minDistance = Double.MAX_VALUE;  // Finding the shortest distance means the base value should be very high
    
        for (City city : cities) {  // Loops through every city
            double cityLat = city.getLatitude();
            double cityLon = city.getLongitude();
    
            double distance = haversine(lat, lon, cityLat, cityLon); // Calculates the distance using the haversine formula
    
            if (distance < minDistance) {   // Updates the nearestCity localCity and minDistance variables
                minDistance = distance;
                nearestCity = city;
            }
        }
        
        return nearestCity;
    }
    
    /**
     * The haversine formula calculates the distance between two points on a large sphere.
     * 
     * @param   lat1    the latitude coordinate of the first pair of points
     * @param   lon1    the longitude coordinate of the first pair of points
     * @param   lat2    the latitude coordinate of the second pair of points
     * @param   lon2    the longitude coordinate of the second pair of points
     * @return          the kilometre distance between two pairs of latitude and longitude coordinates
     */
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // The radius of the Earth in kilometres
        
        // Calculates the difference in latitude and longitude
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        // a = sin^2(dLat/2) + cos(lat1) * cos(lat2) * sin^2(dLon/2)
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.pow(Math.sin(dLon / 2), 2);
                   
        // c = 2 * atan2(sqrt(a), sqrt(1 - a))
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    
        return R * c; // Distance is returned in kilometres
    }
}