import java.util.ArrayList;

/**
 * The ForecastWeatherData class stores information on the city for which forecast data is requested. Whenever the ForecastWeatherFetcher 
 * parses through an API response, the compiled data is encapsulated into objects of this class. The data held in ForecastWeatherData 
 * objects can be accessed via get methods for each field. The data in this class is obtained from the OpenWeatherMap API.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class ForecastWeatherData {    
    // City Information
    private int cityID;
    private String cityName;
    private double latitude;
    private double longitude;
    private String country;
    private int population;
    private int timezoneShift;
    private long sunrise;
    private long sunset;
    
    // Forecast List
    private ArrayList<ThreeHourForecast> forecasts;
    
    // Number of Forecasts
    private int count;
    
    //Internal Parameters
    private int statusCode;
    private int message;

    /**
     * Constructor for objects of class ForecastWeatherData.
     * Every parameter stores current weather data from the OpenWeatherMap API.
     */
    public ForecastWeatherData(
            int cityID, String cityName, double latitude, double longitude, String country, int population,
            int timezoneShift, long sunrise, long sunset, ArrayList<ThreeHourForecast> forecasts, 
            int count, int statusCode, int message) {
        // City Information
        this.cityID = cityID;
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.population = population;
        this.timezoneShift = timezoneShift;
        this.sunrise = sunrise;
        this.sunset = sunset;
        // Forecast List
        this.forecasts = forecasts;
        // Number of Forecasts
        this.count = count;
        // Internal Parameters
        this.statusCode = statusCode;
        this.message = message;
    }
    
    // Get methods for every field
    // City Information
    public int getCityID() { return cityID; }
    public String getCityName() { return cityName; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getCountry() { return country; }
    public int getPopulation() { return population; }
    public int getTimezoneShift() { return timezoneShift; }
    public long getSunrise() { return sunrise; }
    public long getSunset() { return sunset; }
    // Forecast List
    public ArrayList<ThreeHourForecast> getForecasts() { return forecasts; }
    // Number of Forecasts
    public int getCount() { return count; }
    // Internal Parameters
    public int getStatusCode() { return statusCode; }
    public int getMessage() { return message; }
}