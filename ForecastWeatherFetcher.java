import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.ArrayList;
import org.json.JSONArray;

/**
 * The ForecastWeatherFetcher class retrieves forecast weather data from the OpenWeatherMap API's "5-day weather forecast" for a given city 
 * in 3-hour segments. The API returns a JSON response in the form of a JSONObject containing city information, and a list of forecast data. 
 * The forecast list is parsed as a JSONArray object, which separates each segment of forecast data into separate JSONObject objects. When a 
 * 3-hour segment has been processed the forecast data for that segment will be encapsulated into a new ThreeHourForecast object. When the 
 * entire list has been processed the city information and every ThreeHourForecast object created by that API response will be encapsulated 
 * into objects of the class ForecastWeatherData. 
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class ForecastWeatherFetcher {
    private final String API_KEY = "API_KEY";  // Replace with your own API key from the OpenWeatherMap website
    private String country = "GB";
    private String units = "metric";
    
    /**
     * Fetches forecast weather data for the specified city using the OpenWeatherMap API.
     * 
     * @param   city    the name of the city for which forecast weather data is requested
     * @return          a ForecastWeatherData object containing the retrieved weather information, or null if an error occurs
     */
    public ForecastWeatherData getWeatherData(String city) {
        try {
            // Connects to the API 
            city  = city.replace(" ", "+");
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&appid=" + API_KEY + "&units=" + units);
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
            JSONObject forecastJson = new JSONObject(response.toString());
            
            // City Information
            JSONObject cityJson = forecastJson.getJSONObject("city");
            int cityID = cityJson.getInt("id");
            String cityName = cityJson.getString("name");
            double latitude = cityJson.getJSONObject("coord").getDouble("lat");
            double longitude = cityJson.getJSONObject("coord").getDouble("lon");
            String country = cityJson.getString("country");
            int population = cityJson.getInt("population");
            int timezoneShift = cityJson.getInt("timezone");
            long sunrise = cityJson.getLong("sunrise");
            long sunset = cityJson.getLong("sunset");

            // Forecast List
            ArrayList<ThreeHourForecast> forecasts = new ArrayList<>();
            JSONArray forecastList = forecastJson.getJSONArray("list");
            
            // Number of Forecasts
            int count = forecastJson.getInt("cnt");
            
            // Internal Parameters
            int statusCode = forecastJson.getInt("cod");
            int message = forecastJson.getInt("message");

            for (int i = 0; i < forecastList.length(); i++) {
                JSONObject forecast = forecastList.getJSONObject(i);    // Accesses one 3-hour forecast at a time
                
                // Time of Data
                long timestamp = forecast.getLong("dt");
                String timestampString = forecast.getString("dt_txt");

                // Main Data
                JSONObject main = forecast.getJSONObject("main");
                double temperature = main.getDouble("temp");
                double feelsLike = main.getDouble("feels_like");
                double tempMin = main.getDouble("temp_min");
                double tempMax = main.getDouble("temp_max");
                int pressure = main.getInt("pressure");
                int seaLevelPressure = main.getInt("sea_level");
                int groundLevelPressure = main.getInt("grnd_level");
                int humidity = main.getInt("humidity");
                double tempKf = main.getDouble("temp_kf");  // Internal parameter

                // Weather Conditions
                JSONObject weather = forecast.getJSONArray("weather").getJSONObject(0); // Weather information is held in an array with 1 item
                int weatherID = weather.getInt("id");
                String weatherMain = weather.getString("main");
                String weatherDescription = weather.getString("description");
                String weatherIcon = weather.getString("icon");
                
                // Clouds
                int cloudiness = forecast.getJSONObject("clouds").getInt("all");

                // Wind
                JSONObject wind = forecast.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                int windDirection = wind.getInt("deg");
                double windGust = wind.getDouble("gust");
                
                // Visibility
                int visibility = forecast.has("visibility") ? forecast.getInt("visibility") : -1;
                
                // Probability of Precipitation
                double precipitationProb = forecast.getDouble("pop");
                
                // Rain
                double rainVolume = forecast.has("rain") ? forecast.getJSONObject("rain").getDouble("3h") : 0.0;
                
                // Snow
                double snowVolume = forecast.has("snow") ? forecast.getJSONObject("snow").getDouble("3h") : 0.0;

                // Part of Day
                String partOfDay = forecast.getJSONObject("sys").getString("pod");
                
                // Creates a WeatherForecast class containing all the forecast information from one segment
                ThreeHourForecast weatherForecast = new ThreeHourForecast(
                        timestamp, timestampString, 
                        temperature, feelsLike, tempMin, tempMax, pressure, seaLevelPressure, groundLevelPressure, humidity, tempKf,
                        weatherID, weatherMain, weatherDescription, weatherIcon, cloudiness, windSpeed, windDirection, windGust,
                        visibility, precipitationProb, rainVolume, snowVolume, partOfDay);

                // Adds to the list of forecasts for one instance of the API response
                forecasts.add(weatherForecast);
            }
            
            // Creates a ForecastWeatherData class containing all the information from the API response
            return new ForecastWeatherData(
                    cityID, cityName, latitude, longitude, 
                    country, population, timezoneShift, 
                    sunrise, sunset, forecasts,
                    count, statusCode, message);
        } catch (Exception e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
            return null;
        }
    }
}