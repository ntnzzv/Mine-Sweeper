package simpleFX2;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		
		VBox vbox;

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Sample.fxml"));
			vbox = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		stage.setTitle("Voting Machine");
		Scene scene = new Scene(vbox);
		stage.setScene(scene);
		stage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
