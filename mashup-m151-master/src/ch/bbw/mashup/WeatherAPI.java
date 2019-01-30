package ch.bbw.mashup;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Cisco Molnár
 * @version 1.0
 *
 */
public class WeatherAPI {
	private String APIKey;
	private APICall weatherAPI;
	private ObservableList<String> weatherIcons;

	public WeatherAPI() {
		this.APIKey = "533b245128772abd6348b8bdcdabed59";
		this.weatherAPI = new APICall();
	}

	public ObservableList<String> getCurrentWeather(ObservableList<Coordinate> coordinates) {
		String weatherIcon = new String();
		Double temperature;
		Long temperatureLong;

		this.weatherIcons = FXCollections.observableArrayList();

		for (Coordinate coordinate : coordinates) {
			JSONObject mainJSONObject = new JSONObject(weatherAPI
					.getResponse("http://api.openweathermap.org/data/2.5/weather?lat=" + coordinate.getxCoordinate()
							+ "&lon=" + coordinate.getyCoordinate() + "&APPID=" + APIKey));
			JSONArray jsonWeather = mainJSONObject.getJSONArray("weather");
			JSONObject jsonWeatherMain = mainJSONObject.getJSONObject("main");
			JSONObject jsonWeatherInstance = jsonWeather.getJSONObject(0);

			weatherIcon = jsonWeatherInstance.getString("icon");

			// temperatur von Kelvin zu Grad Celsius
			temperature = (jsonWeatherMain.getDouble("temp") - 273.15);
			temperatureLong = Math.round(temperature);

			weatherIcons.add(weatherIcon + ";" + temperatureLong.toString());
			weatherIcon = "";
		}
		return weatherIcons;
	}

}
