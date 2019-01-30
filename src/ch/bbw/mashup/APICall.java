package ch.bbw.mashup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Cisco Molnár
 * @version 1.0
 *
 */
public class APICall {
	private StringBuilder response;
	private URL url;
	private HttpURLConnection conn;
	private BufferedReader in;
	private String input;

	public String getResponse(String url) {
		try {
			this.url = new URL(url);
			conn = (HttpURLConnection) this.url.openConnection();
			conn.setRequestMethod("GET");

			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			this.response = new StringBuilder();
			input = "";

			while ((input = in.readLine()) != null) {
				response.append(input);
			}

			in.close();

			return response.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
