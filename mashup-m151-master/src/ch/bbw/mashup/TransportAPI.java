package ch.bbw.mashup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Cisco Molnár
 * @version 1.0
 */
public class TransportAPI {
	private APICall transportAPICall;
	private StationBoard stationBoard;

	public TransportAPI() {

		transportAPICall = new APICall();

	}

	public StationBoard getStationBoard(String id) {

		JSONObject mainJSONObject = new JSONObject(transportAPICall
				.getResponse("http://transport.opendata.ch/v1/stationboard?station=" + id + "&limit=15"));
		JSONObject jsonStartStation = mainJSONObject.getJSONObject("station");
		JSONArray jsonStationBoard = mainJSONObject.getJSONArray("stationboard");

		stationBoard = new StationBoard();

		for (int i = 0; i < jsonStationBoard.length(); i++) {

			JSONObject jsonStationBoardInstance = jsonStationBoard.getJSONObject(i);

			JSONArray jsonPassList = jsonStationBoardInstance.getJSONArray("passList");
			JSONObject jsonDeparture = jsonPassList.getJSONObject(0);

			JSONObject jsonStationCoordinates = jsonPassList.getJSONObject(jsonPassList.length() - 1);
			JSONObject jsonStation = jsonStationCoordinates.getJSONObject("station");
			JSONObject jsonCoordinate = jsonStation.getJSONObject("coordinate");

			stationBoard.addItems(jsonStartStation.getString("name"), jsonStationBoardInstance.getString("to"),
					getDepartureTime(jsonDeparture.getString("departure")), jsonStationBoardInstance.getString("name"),
					jsonCoordinate.getDouble("x"), jsonCoordinate.getDouble("y"));

		}

		return this.stationBoard;
	}

	public int getID(String name) {

		// Erstes Wort der Benutzereingabe speichern für API Call
		String test[] = name.split(" ");

		JSONObject jsonMainObject = new JSONObject(
				transportAPICall.getResponse("http://transport.opendata.ch/v1/locations?query=" + test[0].toString()));
		JSONArray jsonStations = jsonMainObject.getJSONArray("stations");

		for (int i = 0; i < jsonStations.length(); i++) {

			JSONObject jsonStationsInstance = jsonStations.getJSONObject(i);

			// Wenn die gesuchte Haltestelle mit einer von der API
			// zurï¿½ckgelieferten Haltestelle ï¿½bereinstimmt
			if (jsonStationsInstance.getString("name").equals(name)) {
				return jsonStationsInstance.getInt("id");
			}

		}

		// Wenn keine Haltestelle genau mit den Ergebnissen ï¿½bereinstimmt, die
		// erste Haltestelle zurï¿½ckliefern
		return jsonStations.getJSONObject(0).getInt("id");

	}

	public StationBoard getConnections(String from, String to) {

		JSONObject jsonMain = new JSONObject(transportAPICall
				.getResponse("http://transport.opendata.ch/v1/connections?from=" + from + "&to=" + to + "&limit=13"));
		JSONArray jsonConnections = jsonMain.getJSONArray("connections");
		stationBoard = new StationBoard();

		// Bei zweitem Element in Array beginnen, da die TransportAPI immer eine
		// bereits vergangene Verbindung liefert
		for (int i = 1; i < jsonConnections.length(); i++) {
			JSONObject jsonConnectionInstance = jsonConnections.getJSONObject(i);
			JSONObject jsonFrom = jsonConnectionInstance.getJSONObject("from");
			JSONObject jsonStation = jsonFrom.getJSONObject("station");

			JSONObject jsonTo = jsonConnectionInstance.getJSONObject("to");
			JSONObject jsonStationTo = jsonTo.getJSONObject("station");
			JSONObject jsonCoordinate = jsonStationTo.getJSONObject("coordinate");

			JSONArray jsonSections = jsonConnectionInstance.getJSONArray("sections");
			JSONObject jsonSectionsInstance = jsonSections.getJSONObject(0);

			stationBoard.addItems(jsonStation.getString("name"), jsonStationTo.getString("name"),
					getDepartureTime(jsonFrom.getString("departure")),
					jsonSectionsInstance.getJSONObject("journey").getString("name"), jsonCoordinate.getDouble("x"),
					jsonCoordinate.getDouble("x"));

		}

		return this.stationBoard;
	}

	private String getDepartureTime(String isoDate) {
		try {

			TimeZone tz = TimeZone.getTimeZone("GMT");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

			Date departureDate = df.parse(isoDate);
			Date currentDate = new Date();

			df.setTimeZone(tz);

			// Differenz zwischen aktueller Uhrzeit und Abfahrtszeit berechnen
			// und
			// diese von Millisekunden zu Minuten rechnen
			int departureInMinutes = ((int) Math
					.ceil(((double) departureDate.getTime() - (double) currentDate.getTime()) / (1000 * 60)));

			// Wenn Abfahrt in unter einer Stunde, nur die Minuten zurï¿½ckgeben
			// (Modulo 60 von der Abfahrt in Minuten)
			if (departureInMinutes / 60 == 0) {
				return String.valueOf(departureInMinutes % 60);
			}
			// Sonst die Minuten zu Stunden umwandeln und mit den restlichen
			// Minuten (Modulo 60 von der Abfahrt in Minuten)
			// konkatinieren
			else {
				return String.valueOf(departureInMinutes / 60 + "h, " + departureInMinutes % 60);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

}
