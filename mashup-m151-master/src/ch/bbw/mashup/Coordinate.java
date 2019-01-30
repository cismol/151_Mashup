package ch.bbw.mashup;

/**
 * @author Cisco Molnár
 * @version 1.0
 *
 */
public class Coordinate {
	private double x;
	private double y;

	public Coordinate(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public double getxCoordinate() {
		return x;
	}

	public void setxCoordinate(double x) {
		this.x = x;
	}

	public double getyCoordinate() {
		return y;
	}

	public void setyCoordinate(double y) {
		this.y = y;
	}
}
