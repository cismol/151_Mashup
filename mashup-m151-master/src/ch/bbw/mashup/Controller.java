package ch.bbw.mashup;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Cisco Molnár
 * @version 1.0
 *
 */
public class Controller implements Initializable {

	@FXML
	private ListView<String> lv_Ziel;

	@FXML
	private ListView<String> lv_Abfahrt;

	@FXML
	private ListView<String> lv_Linie;

	@FXML
	private Label lb_Ziel;

	@FXML
	private Label lb_Abfahrt;

	@FXML
	private Label lb_Linie;

	@FXML
	private Label lb_StationBoard;

	@FXML
	private Label lb_Text;

	@FXML
	private TextField tf_from;

	@FXML
	private TextField tf_to;

	@FXML
	private Button bt_search;

	@FXML
	private ListView<String> lv_weather;

	@FXML
	private Label lb_weather;

	@FXML
	private ListView<String> lv_abfahrtsort;

	@FXML
	private Label lb_abfahrtsort;

	private StationBoard stationBoard;

	private Integer stationIDFrom;
	private Integer stationIDTo;
	private TransportAPI transportAPI;
	private WeatherAPI weatherAPI;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		transportAPI = new TransportAPI();
		weatherAPI = new WeatherAPI();

		ObservableList<String> test = FXCollections.observableArrayList();
		test.add("test");

		// Standard StationBoard ist Dorf mit ID 8506980
		stationBoard = transportAPI.getStationBoard("8506989");

		lv_abfahrtsort.setMouseTransparent(true);
		lv_abfahrtsort.setFocusTraversable(false);
		lv_weather.setMouseTransparent(true);
		lv_weather.setFocusTraversable(false);
		lv_Linie.setMouseTransparent(true);
		lv_Linie.setFocusTraversable(false);
		lv_Ziel.setMouseTransparent(true);
		lv_Ziel.setFocusTraversable(false);
		lv_Ziel.setMouseTransparent(true);
		lv_Ziel.setFocusTraversable(false);
		lv_Abfahrt.setMouseTransparent(true);
		lv_Abfahrt.setFocusTraversable(false);
		refresh();

		bt_search.setOnAction(ae -> {
			try {
				stationIDFrom = transportAPI.getID(tf_from.getText());
				if (!tf_from.getText().isEmpty() & !tf_to.getText().isEmpty()) {
					stationIDTo = transportAPI.getID(tf_to.getText());
					stationBoard = transportAPI.getConnections(stationIDFrom.toString(), stationIDTo.toString());
				} else if (!tf_from.getText().isEmpty()) {
					stationBoard = transportAPI.getStationBoard(stationIDFrom.toString());
				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehlerdialog");
				alert.setHeaderText("Es ist ein Fehler aufgetreten!");
				alert.setContentText(
						"Mindestens das Feld \"Von\" muss ausgefüllt sein, oder eine gï¿½ltige Eingabe muss gemacht werden!");
				alert.showAndWait();
			}
			refresh();
		});

		// WetterIcons in die ListView hinzufügen
		lv_weather.setCellFactory(param -> new ListCell<String>() {
			private ImageView imageView = new ImageView();

			@Override
			public void updateItem(String name, boolean empty) {
				super.updateItem(name, empty);

				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					String splitIconAndTemp[] = name.split(";");
					imageView.setImage(new Image("/resources/" + splitIconAndTemp[0] + ".png"));

					imageView.setFitWidth(30);
					setGraphic(imageView);
					setText(splitIconAndTemp[1] + "°C");
				}
			}
		});
	}

	private void refresh() {
		lv_Abfahrt.setItems(stationBoard.getAbfahrten());
		lv_Linie.setItems(stationBoard.getNames());
		lv_Ziel.setItems(stationBoard.getZiele());
		lv_weather.setItems(weatherAPI.getCurrentWeather(stationBoard.getCoordinates()));
		lv_abfahrtsort.setItems(stationBoard.getAbfahrtsOrte());
	}
}
