package ch.bbw.mashup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Cisco Molnár
 * @version 1.0
 *
 */
public class StationBoard {

	private ObservableList<String> ziele = FXCollections.observableArrayList();;
	private ObservableList<String> abfahrten = FXCollections.observableArrayList();;
	private ObservableList<String> linien = FXCollections.observableArrayList();;
	private ObservableList<Coordinate> zielCoordinates = FXCollections.observableArrayList();;
	private ObservableList<String> abfahrtsOrte = FXCollections.observableArrayList();;

	public ObservableList<String> getZiele() {
		return ziele;
	}

	public void setZiele(ObservableList<String> ziele) {
		this.ziele = ziele;
	}

	public ObservableList<String> getAbfahrten() {
		return abfahrten;
	}

	public void setAbfahrten(ObservableList<String> abfahrten) {
		this.abfahrten = abfahrten;
	}

	public ObservableList<String> getNames() {
		return linien;
	}

	public void setLinien(ObservableList<String> linien) {
		this.linien = linien;
	}

	public ObservableList<Coordinate> getCoordinates() {
		return zielCoordinates;
	}

	public void setCoordinates(ObservableList<Coordinate> coordinates) {
		this.zielCoordinates = coordinates;
	}

	public ObservableList<Coordinate> getZielCoordinates() {
		return zielCoordinates;
	}

	public void setZielCoordinates(ObservableList<Coordinate> zielCoordinates) {
		this.zielCoordinates = zielCoordinates;
	}

	public ObservableList<String> getAbfahrtsOrte() {
		return abfahrtsOrte;
	}

	public void setAbfahrtsOrte(ObservableList<String> abfahrtsOrte) {
		this.abfahrtsOrte = abfahrtsOrte;
	}

	public void addItems(String abfahrtsort, String ziel, String abfahrt, String linie, Double xCoordinate,	Double yCoordinate) {
		this.ziele.add(ziel);
		this.abfahrten.add("In " + abfahrt + "min");
		this.linien.add(linie);
		this.zielCoordinates.add(new Coordinate(xCoordinate, yCoordinate));
		this.abfahrtsOrte.add(abfahrtsort);
	}
}
