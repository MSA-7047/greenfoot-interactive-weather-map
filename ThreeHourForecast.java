/**
 * The ThreeHourForecast class stores forecast weather data in a 3-hour snapshot for the city in ForecastWeatherData. All the 3-hour segments 
 * are compiled into an ArrayList in a ForecastWeatherData object.The data held in ThreeHourForecast objects can be accessed via get methods 
 * for each field. The data in this class is obtained from the OpenWeatherMap API.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class ThreeHourForecast {
    // Time of Data   
    private long timestamp;
    private String timestampString;
    
    // Main Data
    private double temperature;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private int pressure;
    private int seaLevelPressure;
    private int groundLevelPressure;
    private int humidity;
    private double tempKf;  // Internal parameter
    
    // Weather Conditions
    private int weatherID;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    
    // Clouds
    private int cloudiness;
    
    // Wind
    private double windSpeed;
    private int windDirection;
    private double windGust;
    
    // Visibility
    private int visibility;
    
    // Probability of Precipitation
    private double precipitationProb;
    
    // Rain
    private double rainVolume;
    
    // Snow
    private double snowVolume;
    
    // Part of Day
    private String partOfDay;

    /**
     * Constructor for objects of class ForecastWeatherData.
     * Every parameter stores current weather data from the OpenWeatherMap API.
     */
    public ThreeHourForecast(
            long timestamp, String timestampString, double temperature, double feelsLike, double tempMin, double tempMax,
            int pressure, int seaLevelPressure, int groundLevelPressure, int humidity, double tempKf,
            int weatherID, String weatherMain, String weatherDescription, String weatherIcon, int cloudiness,
            double windSpeed, int windDirection, double windGust, int visibility, double precipitationProb,
            double rainVolume, double snowVolume, String partOfDay) {
        // Time of Data
        this.timestamp = timestamp;
        this.timestampString = timestampString;
        // Main Data
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.seaLevelPressure = seaLevelPressure;
        this.groundLevelPressure = groundLevelPressure;
        this.humidity = humidity;
        this.tempKf = tempKf;
        // Weather Conditions
        this.weatherID = weatherID;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        // Clouds
        this.cloudiness = cloudiness;
        // Wind
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.windGust = windGust;
        // Visibility
        this.visibility = visibility;
        // Probability of Precipitation
        this.precipitationProb = precipitationProb;
        // Rain
        this.rainVolume = rainVolume;
        // Snow
        this.snowVolume = snowVolume;
        // Part of Day
        this.partOfDay = partOfDay;
    }

    // Get methods for every field
    // Time of Data
    public long getTimestamp() { return timestamp; }
    public String getTimestampString() { return timestampString; }
    // Main Data
    public double getTemperature() { return temperature; }
    public double getFeelsLike() { return feelsLike; }
    public double getTempMin() { return tempMin; }
    public double getTempMax() { return tempMax; }
    public int getPressure() { return pressure; }
    public int getSeaLevelPressure() { return seaLevelPressure; }
    public int getGroundLevelPressure() { return groundLevelPressure; }
    public int getHumidity() { return humidity; }
    public double getTempKf() { return tempKf; }
    // Weather Conditions
    public int getWeatherID() { return weatherID; }
    public String getWeatherMain() { return weatherMain; }
    public String getWeatherDescription() { return weatherDescription; }
    public String getWeatherIcon() { return weatherIcon; }
    // Clouds
    public int getCloudiness() { return cloudiness; }
    // Wind
    public double getWindSpeed() { return windSpeed; }
    public int getWindDirection() { return windDirection; }
    public double getWindGust() { return windGust; }
    // Visibility
    public int getVisibility() { return visibility; }
    // Probability of Precipitation
    public double getPrecipitationProb() { return precipitationProb; }
    // Rain
    public double getRainVolume() { return rainVolume; }
    // Snow
    public double getSnowVolume() { return snowVolume; }
    // Part of Day
    public String getPartOfDay() { return partOfDay; }
}