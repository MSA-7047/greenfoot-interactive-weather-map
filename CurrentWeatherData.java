/**
 * The CurrentWeatherData class stores a variety of weather-related information retrieved from the CurrentWeatherFetcher class. 
 * The data held in CurrentWeatherData objects can be accessed via numerous get methods for each field. The data in this class 
 * is obtained from the OpenWeatherMap API.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class CurrentWeatherData {
    // City
    private int cityID;
    private String cityName;
    
    // Coordinates
    private double latitude;
    private double longitude;

    // Weather Conditions
    private int weatherID;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    
    // Main Data
    private double temperature;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private int pressure;
    private int humidity;
    private int seaLevelPressure;
    private int groundLevelPressure;
    
    //Visibility
    private int visibility;
            
    // Wind
    private double windSpeed;
    private int windDirection;
    private double windGust;
    
    // Clouds
    private int cloudiness;
    
    // Rain
    private double rainVolume;
    
    // Snow
    private double snowVolume;

    // Time of Data
    private long timestamp;
    
    // System
    private String country;
    private long sunrise;
    private long sunset;
    
    // Timezone
    private int timezoneShift;
    
    // Internal Parameters
    private String base;
    private int systemType;
    private int systemID;
    private String message;
    private int statusCode;

    /**
     * Constructor for objects of class CurrentWeatherData. 
     * Every parameter stores current weather data from the OpenWeatherMap API. 
     */
    public CurrentWeatherData(
            int cityID, String cityName, double latitude, double longitude, int weatherID, String weatherMain, String weatherDescription, String weatherIcon,
            double temperature, double feelsLike, double tempMin, double tempMax, int pressure, int humidity, int seaLevelPressure, int groundLevelPressure,
            int visibility, double windSpeed, int windDirection, double windGust, int cloudiness, double rainVolume, double snowVolume, long timestamp,
            String country, long sunrise, long sunset, int timezoneShift, String base, int systemType, int systemID, String message, int statusCode) {
        // City
        this.cityID = cityID;
        this.cityName = cityName;
        // Coordinates
        this.latitude = latitude;
        this.longitude = longitude;
        // Weather Description
        this.weatherID = weatherID;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
        // Main Data
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
        this.seaLevelPressure = seaLevelPressure;
        this.groundLevelPressure = groundLevelPressure;
        //Visibility
        this.visibility = visibility;
        // Wind
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.windGust = windGust;
        // Clouds
        this.cloudiness = cloudiness;
        // Rain
        this.rainVolume = rainVolume;
        // Snow
        this.snowVolume = snowVolume;
        // Time of Data
        this.timestamp = timestamp;
        // System
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        // Timezone
        this.timezoneShift = timezoneShift;
        // Internal Parameters
        this.base = base;
        this.systemType = systemType;
        this.systemID = systemID;
        this.message = message;
        this.statusCode = statusCode;
    }

    // Get methods for every field
    // City
    public int getCityID() { return cityID; }
    public String getCityName() { return cityName; }
    // Coordinates
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    // Weather Description
    public int getWeatherID() { return weatherID; }
    public String getWeatherMain() { return weatherMain; }
    public String getWeatherDescription() { return weatherDescription; }
    public String getWeatherIcon() { return weatherIcon; }
    // Main Data
    public double getTemperature() { return temperature; }
    public double getFeelsLike() { return feelsLike; }
    public double getTempMin() { return tempMin; }
    public double getTempMax() { return tempMax; }
    public int getPressure() { return pressure; }
    public int getHumidity() { return humidity; }
    public int getSeaLevelPressure() { return seaLevelPressure; }
    public int getGroundLevelPressure() { return groundLevelPressure; }
    // Visibility
    public int getVisibility() { return visibility; }
    // Wind
    public double getWindSpeed() { return windSpeed; }
    public int getWindDirection() { return windDirection; }
    public double getWindGust() { return windGust; }
    // Clouds
    public int getCloudiness() { return cloudiness; }
    // Rain
    public double getRainVolume() { return rainVolume; }
    // Snow
    public double getSnowVolume() { return snowVolume; }
    // Time of Data
    public long getTimestamp() { return timestamp; }
    // System
    public String getCountry() { return country; }
    public long getSunrise() { return sunrise; }
    public long getSunset() { return sunset; }
    // Timezone
    public int getTimezoneShift() { return timezoneShift; }
    // Internal parameters
    public String getBase() { return base; }
    public int getSystemType() { return systemType; }
    public int getSystemID() { return systemID; }
    public String getMessage() { return message; }
    public int getStatusCode() { return statusCode; }
}