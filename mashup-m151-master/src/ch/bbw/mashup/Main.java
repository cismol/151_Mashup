package ch.bbw.mashup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Cisco Molnár
 * @version 1.0
 */

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
			AnchorPane root = loader.load();

			Scene scene = new Scene(root, 750, 450);

			primaryStage.setTitle("StationBoard");
			primaryStage.getIcons().add(new Image("resources/train.png"));
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
