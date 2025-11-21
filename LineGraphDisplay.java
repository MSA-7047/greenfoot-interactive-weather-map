import greenfoot.*;
import java.util.ArrayList;

/**
 * The LineGraphDisplay class displays an image of a line graph which contains the forecast weather data from a city. The graph shows
 * the time of each forecast on the x-axis in HH:mm and shows the temperature of the data metric in Celcius (°C). The line graph displays
 * 8 points of the forecast at a time since each day is split into 3-hour segments. There are 5 days in total, which means there are 40
 * data points that can be displayed for a data metric. This class contains methods to draw the graph, and update it to view the next and 
 * previous days.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class LineGraphDisplay extends Actor {
    private GraphScreen graphScreen;
    private ForecastWeatherData weatherData;
    
    // LineGraphDisplay dimensions
    private int width = 500;
    private int height = 300;
    
    // Days are indexed 0-4 (each day has 8 points)
    private int dayIndex = 0;  // 0 = day 1, 1 = day 2 etc.
    
    // Min and Max values of the line graph
    private double globalMin;
    private double globalMax;
    
    // Colors and Font
    private static final Color GRAPH_COLOR = Color.LIGHT_GRAY;
    private static final Color LINE_COLOR = Color.RED;
    private static final Color X_AXIS_COLOR = Color.BLUE;
    private static final Color Y_AXIS_COLOR = Color.RED;
    private static final Color HEADER_COLOR = Color.BLACK;
    private static final Font HEADER_FONT = new Font("Monospaced", true, false, 14);
    
    /**
     * Constructor for objects of class LineGraphDisplay.
     * 
     * @param   graphScreen     the GraphScreen world the LineGraphDisplay is in
     * @param   weatherData     the ForecastWeatherData object containing the weather data of the city
     */
    public LineGraphDisplay(GraphScreen graphScreen, ForecastWeatherData weatherData) {
        this.graphScreen = graphScreen;
        this.weatherData = weatherData;
        updateGraph();
    }

    /**
     * Returns the ForecastWeatherData object held by this LineGraphDisplay object.
     */
    public ForecastWeatherData getWeatherData() {
        return weatherData;
    }
    
    /**
     * Returns the current day index.
     * 
     * @return      the day index of the line graph
     */
    public int getDayIndex() {
        return dayIndex;
    }

    /**
     * Updates the set of points displayed on the graph to show the next 8 points after increasing the day offset.
     * The latest day that can be viewed is day 5.
     */
    public void nextDay() {
        if (dayIndex < 4) {
            dayIndex++;
            updateGraph();
        }
    }

    /**
     * Updates the set of points displayed on the graph to show the previous 8 points after decreasing the day offset.
     * The earliest day that can be viewed is day 1.
     */
    public void prevDay() {
        if (dayIndex > 0) {
            dayIndex--;
            updateGraph();
        }
    }
    
    /**
     * Executes the calculateGlobalMinMax() method to find the largest and smallest values of the y-axis of the graph across the 5 days.
     * The new graph will be drawn to accomodate for all the points within these values.
     */
    public void updateGraph() {
        ArrayList<ThreeHourForecast> forecasts = weatherData.getForecasts();
        calculateGlobalMinMax(forecasts);
        drawGraph(forecasts);
    }

    /**
     * Updates the weather data for the line graph before redrawing the graph.
     * The day index will be reset to the 0 (day 1).
     * 
     * @param   newData     the new ForecastWeatherData object to be displayed
     */
    public void updateData(ForecastWeatherData newData) {
        this.weatherData = newData;
        this.dayIndex = 0; // Resets to Day 1
        updateGraph();      // Redraws the graph using current type from GraphScreen
    }
    
    /**
     * Returns the value from a specific forecast depending on the current type stored in the GraphScreen world.
     * 
     * @param   forecast    the specfic ThreeHourForecast object
     * @return              the value of the weather metric in that specific forecast
     */
    public double getValue(ThreeHourForecast forecast) {
        switch (graphScreen.getGraphType()) {
            case "Temperature": return forecast.getTemperature();
            case "Feels Like": return forecast.getFeelsLike();
            default: return forecast.getTemperature();
        }
    }
    
    /**
     * Calculates the smallest and largest number of the values to be displayed on the y-axis.
     * 
     * @param   forecasts   an ArrayList of ThreeHourForecast objects
     */
    private void calculateGlobalMinMax(ArrayList<ThreeHourForecast> forecasts) {
        // Initial max and min values
        globalMin = Double.POSITIVE_INFINITY;
        globalMax = Double.NEGATIVE_INFINITY;

        for (ThreeHourForecast forecast : forecasts) {
            double value = getValue(forecast);          // Compare the value in every forecast
            globalMin = Math.min(globalMin, value);
            globalMax = Math.max(globalMax, value);
        }

        globalMin = Math.floor(globalMin);  // Rounds down
        globalMax = Math.ceil(globalMax);   // Rounds up
    }
    
    /**
     * Draws the entire grid and the points of the line graph using the list of forecast data.
     * Uses three helper functions to draw the y-axis lines, the x-axis lines and the points themselves.
     * 
     * @param   forecasts   an ArrayList containing all 40 ThreeHourForecast objects
     */
    private void drawGraph(ArrayList<ThreeHourForecast> forecasts) {
        GreenfootImage image = new GreenfootImage(width, height);
        
        // Calculates the space between each line for the x-axis and y-axis
        double xSpacing = (width - 80) / 7.0;
        double ySpacing = (height - 85) / (globalMax - globalMin);
        
        // Draws the lines after the grid has been drawn
        drawYAxisGrid(image, ySpacing);
        drawXAxisGrid(image, forecasts, xSpacing);
        drawLineGraph(image, forecasts, xSpacing, ySpacing);
        
        // Draws the header
        image.setColor(HEADER_COLOR);
        image.setFont(HEADER_FONT);
        image.drawString(graphScreen.getGraphType() + " in " + weatherData.getCityName() + " - Day " + (dayIndex + 1), 75, 10); // Title at the top
        
        setImage(image);
    }
    
    /**
     * Draws the the horizontal lines of the y-axis, the integer intervals and the header for the y-axis.
     * 
     * @param   image       the image for the line graph to be displayed on
     * @param   ySpacing    the spacing between the y-axis values
     */
    private void drawYAxisGrid(GreenfootImage image, double ySpacing) {        
        // Loops through every integer from globalMin to globalMax
        for (int value = (int) globalMin; value <= (int) globalMax; value++) {          
            // Draws the horizontal grid line
            image.setColor(GRAPH_COLOR);
            int yPos = 250 - (int) ((value - globalMin) * ySpacing);  // The lines are drawn upwards one by one
            image.drawLine(50, yPos, width - 15, yPos);
    
            // Draws the y-axis labels
            image.setColor(Y_AXIS_COLOR);
            image.drawString(value + "", 30, yPos + 5);
        }
        
        // Draws the header
        image.setColor(HEADER_COLOR);
        image.drawString("Temp (°C)", 5, 10); // Y-axis label
    }

    /**
     * Draws the the vertical lines of the y-axis, the time intervals and the header for the x-axis.
     * 
     * @param   image       the image for the line graph to be displayed on
     * @param   forecasts   an ArrayList containing all 40 ThreeHourForecast objects
     * @param   xSpacing    the spacing between the x-axis values
     */
    private void drawXAxisGrid(GreenfootImage image, ArrayList<ThreeHourForecast> forecasts, double xSpacing) {
        // 8 is the maximum that will be shown in one graph
        for (int i = 0; i < 8; i++) {
            int xPos = (int) (50 + i * xSpacing);
            
            // Draws the vertical grid line
            image.setColor(Color.LIGHT_GRAY);
            image.drawLine(xPos, 20, xPos, 250);
            
            // Draws the x-axis labels
            image.setColor(X_AXIS_COLOR);
            int index = dayIndex * 8 + i;  // Calculates each specific forecast index
            String timestampString = forecasts.get(index).getTimestampString();
            String time = timestampString.substring(11, 16);   // The characters for the time e.g. "09:00"
            image.drawString(time, xPos - 15, 270);
        }

        // Draws the header
        image.setColor(HEADER_COLOR);
        image.drawString("Time (HH:mm)", 210, 290);
    }
    
    /**
     * Draws the the points onto the line graph and the lines connecting them.
     * 
     * @param   image       the image for the line graph to be displayed on
     * @param   forecasts   an ArrayList containing all 40 ThreeHourForecast objects
     * @param   xSpacing    the spacing between the x-axis values
     * @param   ySpacing    the spacing between the y-axis values
     */
    private void drawLineGraph(GreenfootImage image, ArrayList<ThreeHourForecast> forecasts, double xSpacing, double ySpacing) {
        image.setColor(LINE_COLOR);
        // Initialises the previous position of dots
        int prevX = 0;
        int prevY = 0;

        // 8 is the maximum that will be shown in one graph
        for (int i = 0; i < 8; i++) {
            // Calculates x and y positions
            int xPos = (int) (50 + i * xSpacing);
            int index = dayIndex * 8 + i;
            int yPos = 250 - (int) ((getValue(forecasts.get(index)) - globalMin) * ySpacing);

            image.fillOval(xPos - 3, yPos - 3, 6, 6); // Draw dots on each point
            if (i > 0) {  // Draws a line if there exists 2 or more dots
                image.drawLine(prevX, prevY, xPos, yPos);
            }
            
            // Stores the most recent position as the previous position
            prevX = xPos;
            prevY = yPos;
        }
    }
    
    /**
     * Overrides the setLocation() method in the Actor class to stop the buttons from being dragged when the program is paused.
     */
    @Override
    public void setLocation(int x, int y) {
        // Stops movement by not executing any code
    }
}