import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherApp {

    // Replace with your actual API key
    static String apiKey = "7599c6df9b7fb92554edb86385b465be";

    public static void main(String[] args) {

        try {

            // City name
            String city = "London";

            // API URL
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                    + city
                    + "&appid="
                    + apiKey
                    + "&units=metric";

            // Create URL object
            URI uri = new URI(urlString);
            URL url = uri.toURL();

            // Open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check response code
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                System.out.println("Error: Failed to fetch weather data.");
                System.out.println("HTTP Response Code: " + responseCode);
                return;
            }

            // Read API response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Parse JSON response
            JsonObject json = JsonParser.parseString(response.toString())
                    .getAsJsonObject();

            // Extract values
            String cityName = json.get("name").getAsString();

            JsonObject main = json.getAsJsonObject("main");

            double temp = main.get("temp").getAsDouble();
            int humidity = main.get("humidity").getAsInt();

            String weather = json.getAsJsonArray("weather")
                    .get(0)
                    .getAsJsonObject()
                    .get("description")
                    .getAsString();

            // Display output
            System.out.println("====== Weather Report ======");
            System.out.println("City       : " + cityName);
            System.out.println("Temperature: " + temp + " °C");
            System.out.println("Humidity   : " + humidity + " %");
            System.out.println("Condition  : " + weather);
            System.out.println("============================");

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
}