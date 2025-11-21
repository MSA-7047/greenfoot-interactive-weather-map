/**
 * The City class contains the city name, latitude and longitude information for every city displayed on the MapScreen world. 
 * This information is retrieved from the "cities.txt" file and encapsulated here. Additional cities can be added by updating
 * the "cities.txt" file.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class City {
    private String name;
    private double latitude;
    private double longitude;
    
    /**
     * Constructor for objects of class City.
     * 
     * @param   name        the name of the city
     * @param   latitude    the latitude of the city
     * @param   longitude   the longitude of the city
     */
    public City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Returns the name of the city.
     * 
     * @return  the name of the city as a String
     */
    public String getName() { 
        return name; 
    }
    
    /**
     * Returns the latitude of the city.
     * 
     * @return  the latitude coordinate of the city as a double
     */
    public double getLatitude() { 
        return latitude; 
    }
    
    /**
     * Returns the longitude of the city.
     * 
     * @return  the longitude coordinate of the city as a double
     */
    public double getLongitude() { 
        return longitude;
    }
}