import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;

/**
 * The CurrentWeatherFetcher class retrieves current weather data from the OpenWeatherMap API's "current weather forecast" for a given city. 
 * The API returns a JSON response in the form of a JSONObject object, which gets processed and then encapsulated into objects of the class 
 * CurrentWeatherData.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class CurrentWeatherFetcher {
    private final String API_KEY = "API_KEY";  // Replace with your own API key from the OpenWeatherMap website
    private String country = "GB";
    private String units = "metric";
    
    /**
     * Fetches current weather data for the specified city using the OpenWeatherMap API.
     * 
     * @param   city    the name of the city for which current weather data is requested
     * @return          a CurrentWeatherData object containing the retrieved weather information, or null if an error occurs
     */
    public CurrentWeatherData getWeatherData(String city) {
        try {
            // Connects to the API 
            city  = city.replace(" ", "+");
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&appid=" + API_KEY + "&units=" + units);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Reads the JSON response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
    
            // Parses the JSON response
            JSONObject currentWeatherJson = new JSONObject(response.toString());
            
            // City
            int cityID = currentWeatherJson.getInt("id");
            String cityName = currentWeatherJson.getString("name");
            
            // Coordinates
            JSONObject coord = currentWeatherJson.getJSONObject("coord");
            double latitude = coord.getDouble("lat");
            double longitude = coord.getDouble("lon");

            // Weather Conditions
            JSONObject weather = currentWeatherJson.getJSONArray("weather").getJSONObject(0);
            int weatherID = weather.getInt("id");
            String weatherMain = weather.getString("main");
            String weatherDescription = weather.getString("description");
            String weatherIcon = weather.getString("icon");
            
            // Main Data
            JSONObject main = currentWeatherJson.getJSONObject("main");
            double temperature = main.getDouble("temp");
            double feelsLike = main.getDouble("feels_like");
            double tempMin = main.getDouble("temp_min");
            double tempMax = main.getDouble("temp_max");
            int pressure = main.getInt("pressure");
            int humidity = main.getInt("humidity");
            int seaLevelPressure = main.has("sea_level") ? main.getInt("sea_level") : -1;
            int groundLevelPressure = main.has("grnd_level") ? main.getInt("grnd_level") : -1;

            //Visibility
            int visibility = currentWeatherJson.has("visibility") ? currentWeatherJson.getInt("visibility") : -1;
            
            // Wind
            JSONObject wind = currentWeatherJson.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");
            int windDirection = wind.getInt("deg");
            double windGust = wind.has("gust") ? wind.getDouble("gust") : -1;
            
            // Clouds
            int cloudiness = currentWeatherJson.getJSONObject("clouds").getInt("all");
            
            // Rain
            double rainVolume = currentWeatherJson.has("rain") ? currentWeatherJson.getJSONObject("rain").getDouble("1h") : 0.0;
            
            // Snow
            double snowVolume = currentWeatherJson.has("snow") ? currentWeatherJson.getJSONObject("snow").getDouble("1h") : 0.0;

            // Time of Data
            long timestamp = currentWeatherJson.getLong("dt");
            
            // System
            JSONObject sys = currentWeatherJson.getJSONObject("sys");
            String country = sys.getString("country");
            long sunrise = sys.getLong("sunrise");
            long sunset = sys.getLong("sunset");
            
            // Timezone
            int timezoneShift = currentWeatherJson.getInt("timezone");
            
            // Internal Parameters
            String base = currentWeatherJson.getString("base");
            int systemType = sys.has("type") ? sys.getInt("type") : -1;
            int systemID = sys.has("id") ? sys.getInt("id") : -1;
            String message = sys.has("message") ? sys.getString("message") : "No message found";
            int statusCode = currentWeatherJson.getInt("cod");
            
            // Creates a CurrentWeatherData class containing all the information from the API response
            return new CurrentWeatherData(
                    cityID, cityName, latitude, longitude, weatherID, weatherMain, weatherDescription, weatherIcon,
                    temperature, feelsLike, tempMin, tempMax, pressure, humidity, seaLevelPressure, groundLevelPressure, 
                    visibility, windSpeed, windDirection, windGust, cloudiness, rainVolume, snowVolume, timestamp,
                    country, sunrise, sunset, timezoneShift, 
                    base, systemType, systemID, message, statusCode);

        } catch (Exception e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
            return null;
        }
    }
}