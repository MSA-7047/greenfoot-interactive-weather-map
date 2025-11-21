import greenfoot.*;

/**
 * The GraphScreen world displays a line graph of the given city's temperature or feels like weather data for the upcoming 5 days. It 
 * can be viewed after fully zooming in over the selected city and pressing the left arrow key. This world contains a graph to view the 
 * forecast statistics, buttons to view the next and previous days of forecast data, and a dropdown tool to change the type of data being 
 * displayed on the line graph. This world also contains a button to print a summary of the visible line graph onto the Terminal. To 
 * return to the MapScreen, the left arrow key must be pressed.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class GraphScreen extends World {
    private MapScreen mapScreen;
    private String city;
    
    // Actors in the GraphScreen world
    private ForecastWeatherFetcher fetcher;
    private ForecastWeatherData weatherData;
    private LineGraphDisplay graph;
    
    // Current weather metric
    private String graphType;
    
    /**
     * Constructor for objects of class GraphScreen.
     * 
     * @param   mapScreen   the MapScreen world
     * @param   city        the selected city stored in CurrentWeatherDisplay
     */
    public GraphScreen(MapScreen mapScreen, String city) {    
        super(600, 400, 1);
        this.mapScreen = mapScreen;
        
        // Initialises a new ForecastWeatherFetcher and gets forecast data from the city
        fetcher = new ForecastWeatherFetcher();
        weatherData = fetcher.getWeatherData(city);
        
        // Creates a line graph with a default data type
        graphType = "Temperature";
        graph = new LineGraphDisplay(this, weatherData);
        addObject(graph, 300, 200);

        // Creates a dropdown menu to change the data being displayed on the graph
        Dropdown dropdown = new Dropdown(this);
        addObject(dropdown, 505, 25);

        // Buttons to view the next and previous days
        addObject(new GraphNavigationButton(graph, true), 450, 350);
        addObject(new GraphNavigationButton(graph, false), 150, 350);
        
        // Summary button to print a summary of the graph
        addObject(new SummaryButton(this, graph), 300, 370);
        
        // Highlights when the left arrow key has been pressed
        addObject(new KeyIcon("left"), 20,20);
    }
    
    /**
     * Returns the current graph type.
     * 
     * @return  the current weather metric being displayed by this world.
     */
    public String getGraphType() {
        return graphType;
    }
    
    /**
     * Sets the line graph in the world to display the given type of data.
     * 
     * @param   type    the type of temperature data to be displayed
     */
    public void setGraphType(String type) {
        graphType = type;
        graph.updateGraph();
    }
    
    /**
     * Fetches forecast weather data from the given city if the city is different from the one currently selected.
     * The graph will update with this new data.
     * 
     * @param   newCity     the new city for forecast weather to be displayed from
     */
    public void updateCity(String newCity) {
        if (!newCity.equals(this.city)) {
            this.city = newCity;
            ForecastWeatherData newData = fetcher.getWeatherData(newCity);
            graph.updateData(newData);
        }
    }
    
    /**
     * Runs the checkScreenSwitchInput() method whenever the 'Act' or 'Run' button gets pressed or executed in the environment.
     */
    public void act() {
        checkScreenSwitchInput();
    }
    
    /**
     * Switches to the MapScreen world if the left arrow key is pressed. 
     */
    private void checkScreenSwitchInput() {
        if (Greenfoot.isKeyDown("left")) {
            KeyIcon keyIcon = getObjects(KeyIcon.class).get(0); // Only 1 KeyIcon object exists in this world
            keyIcon.setPressedImage();
            Greenfoot.delay(10);
            Greenfoot.setWorld(mapScreen);
        }
    }
}