import greenfoot.*;
import java.util.ArrayList;

/**
 * The SummaryButton class creates a button that displays a string of summary information onto the terminal for the current line graph 
 * being displayed in the GraphScreen world. The summary information contains the day and the time range of the forecast. It also includes 
 * the minimum, maximum and average forecast temperatures of the day being summarised. The background colors of the buttons change when 
 * hovered and when clicked by the user due to inheritance from the Button class.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class SummaryButton extends Button {
    private GraphScreen graphScreen;
    private LineGraphDisplay graph;

    /**
     * Constructor for objects of class SummaryButton.
     * 
     * @param   graphScreen     the GraphScreen world
     * @param   graph           the LineGraphDisplay object
     */
    public SummaryButton(GraphScreen graphScreen, LineGraphDisplay graph) {
        super(85, 30, "Summary");
        this.graph = graph;
        this.graphScreen = graphScreen;
    }

    /**
     * Calculates the summary values for the current data type of the graph and outputs the result onto the terminal.
     * The values include the minimum and maximum temperature in the 24 hour interval, the average temperature during 
     * that time and the time range of the data.
     */
    @Override
    protected void onClick() {
        ArrayList<ThreeHourForecast> forecastList = graph.getWeatherData().getForecasts();
        
        // Stores the name of the city and the day of the forecast
        String cityName = graph.getWeatherData().getCityName();
        int dayIndex = graph.getDayIndex();
        
        // Calculates the time range of the data        
        String[] timeRange = getTimeRange(forecastList, dayIndex);
        
        // Calculates minimum and maximum values
        double min = calculateMin(forecastList, dayIndex);
        double max = calculateMax(forecastList, dayIndex);
        
        // Calculates the time for the minimum and maximum
        String minTime = getMinTime(forecastList, dayIndex, min);
        String maxTime = getMaxTime(forecastList, dayIndex, max);
        
        // Calculates the average of the values
        double average = calculateAverage(forecastList, dayIndex);
        
        // Prints the summary information
        printTimeRange(cityName, dayIndex, timeRange[0], timeRange[1]);
        printSummary(min, minTime, max, maxTime, average);
    }
    
    /**
     * Calculates and returns the start and end time of the data summary at a particular day.
     *
     * @param   forecastList    an ArrayList containing 40 ThreeHourForecast objects
     * @param   dayIndex        the forecast day index
     * @return                  an array containing the start and end times as Strings
     */
    private String[] getTimeRange(ArrayList<ThreeHourForecast> forecastList, int dayIndex) {
        // Finds the first forecast entry for the selected day
        int startIndex = dayIndex * 8;    
        String startTime = forecastList.get(startIndex).getTimestampString();
        
        // Finds the last forecast entry for the selected day
        int endIndex = (dayIndex + 1) * 8 - 1;
        String endTime = forecastList.get(endIndex).getTimestampString();
    
        return new String[]{startTime, endTime};
    }
    
    /**
     * Calculates and returns the minimum value at a particular day in the forecast.
     * 
     * @param   forecastList    an ArrayList containing 40 ThreeHourForecast objects
     * @param   dayIndex        the forecast day index
     * @return                  the minimum value in the given day
     */
    private double calculateMin(ArrayList<ThreeHourForecast> forecastList, int dayIndex) {
        double min = Double.POSITIVE_INFINITY;
        
        // The result is calculated using 8 values (8 values per day)
        for (int i = dayIndex * 8; i < (dayIndex + 1) * 8; i++) {
            double value = graph.getValue(forecastList.get(i));
            if (value < min) {
                min = value;
            }
        }
    
        return min;
    }
    
    /**
     * Calculates and returns the maximum value at a particular day in the forecast.
     * 
     * @param   forecastList    an ArrayList containing 40 ThreeHourForecast objects
     * @param   dayIndex        the forecast day index
     * @return                  the maximmum value in the given day
     */
    private double calculateMax(ArrayList<ThreeHourForecast> forecastList, int dayIndex) {
        double max = Double.NEGATIVE_INFINITY;
        
        // The result is calculated using 8 values (8 values per day)
        for (int i = dayIndex * 8; i < (dayIndex + 1) * 8; i++) {
            double value = graph.getValue(forecastList.get(i));
            if (value > max) {
                max = value;   
            }
        }
    
        return max;
    }
    
    /**
     * Calculates and returns the time of the minimum value at a particular day in the forecast.
     * 
     * @param   forecastList    an ArrayList containing 40 ThreeHourForecast objects
     * @param   dayIndex        the forecast day index
     * @param   min             the minimum value in the given day
     * @return                  the time of the minimum value
     */
    private String getMinTime(ArrayList<ThreeHourForecast> forecastList, int dayIndex, double min) {
        String minTime = "";
    
        // The result is calculated using 8 values (8 values per day)
        for (int i = dayIndex * 8; i < (dayIndex + 1) * 8; i++) {
            ThreeHourForecast forecast = forecastList.get(i);
            double value = graph.getValue(forecast);
    
            if (value == min) {
                minTime = forecast.getTimestampString();
                break;  // Leaves the loop once found
            }
        }
    
        return minTime;
    }
    
    
    /**
     * Calculates and returns the time of the maximum value at a particular day in the forecast.
     * 
     * @param   forecastList    an ArrayList containing 40 ThreeHourForecast objects
     * @param   dayIndex        the forecast day index
     * @param   min             the maximum value in the given day
     * @return                  the time of the maximum value
     */
    private String getMaxTime(ArrayList<ThreeHourForecast> forecastList, int dayIndex, double max) {
        String maxTime = "";
    
        // The result is calculated using 8 values (8 values per day)
        for (int i = dayIndex * 8; i < (dayIndex + 1) * 8; i++) {
            ThreeHourForecast forecast = forecastList.get(i);
            double value = graph.getValue(forecast);
    
            if (value == max) {
                maxTime = forecast.getTimestampString();
                break;  // Leaves the loop once found
            }
        }
    
        return maxTime;
    }
    
    /**
     * Calculates and returns the average of the 8 values at a particular day in the forecast.
     * 
     * @param   forecastList    an ArrayList of the forecasts
     * @param   dayIndex        the forecast day index
     * @return                  the average value in the given day
     */
    private double calculateAverage(ArrayList<ThreeHourForecast> forecastList, int dayIndex) {
        double total = 0.0;
    
        // The result is calculated using 8 values (8 values per day)
        for (int i = dayIndex * 8; i < (dayIndex + 1) * 8; i++) {
            total += graph.getValue(forecastList.get(i));
        }
    
        return total / 8;
    }
    
    /**
     * Prints a String onto the terminal containing information about the name of the city, the forecast day and 
     * the start and end times of the data.
     * 
     * @param   cityName    the name of the city
     * @param   dayIndex    the forecast day index
     * @param   startTime   the start time of the forecast data
     * @param   endTime     the end time of the forecast data
     */
    private void printTimeRange(String cityName, int dayIndex, String startTime, String endTime) {
        System.out.println(
            "\n===== Forecast Time Range ===== \n" +
            "City: " + cityName + "\n" +
            "Day: " + (dayIndex + 1) + "\n" +
            "Time Range: " + startTime + " - " + endTime
        );
    }
    
    /**
     * Prints a String onto the terminal containing a summary of the temperature data.
     * The summary includes the the minimum and maximum temperatures and times, and the average temperature across the day.
     * 
     * @param   minTemp     the minimum temperature in the 24-hour timespan
     * @param   minTime     the time of the minimum temperature
     * @param   maxtemp     the minimum temperature in the 24-hour timespan
     * @param   maxTime     the time of the minimum temperature
     * @param   average     the average temperature in the 24-hour timespan
     */
    private void printSummary(double minTemp, String minTime, double maxTemp, String maxTime, double average) {
        System.out.printf(
            "======= Weather Summary =======\n" +
            "Weather Metric: %s\n" +
            "Min Temperature: %.2f°C at %s\n" +
            "Max Temperature: %.2f°C at %s\n" +
            "Average Temperature: %.2f°C\n" +
            "===============================\n",
            graphScreen.getGraphType(),
            minTemp, minTime,
            maxTemp, maxTime,
            average
        );
    }
}