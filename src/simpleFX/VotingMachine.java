package simpleFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VotingMachine extends Application {
	private int i = 0;

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(createMachine());
		stage.setScene(scene);
		stage.setTitle("Voting Machine");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private VBox createMachine() {
		HBox root = new HBox(40); // initializes the root HBox 
		Label label = new Label(); // creates a label
		label.setPadding(new Insets(10, 100, 10, 100)); // sets the padding for the label inside its parent
		root.setPadding(new Insets(30));
		Button yardena = new Button("Yardena Arazi"); // creates the buttons
		Button ofra = new Button("Ofra Haza");
		root.getChildren().addAll(yardena, ofra); // adds the buttons into the HBox
		BackgroundFill original = new BackgroundFill(Color.AQUAMARINE, new CornerRadii(0), null); // creates a background for 0 value
		label.setBackground(new Background(original));
		label.setText("" + i); // sets the label text

		class LabelIncreaser implements EventHandler<ActionEvent> { // event handler for a button pressed 
			@Override
			public void handle(ActionEvent event) {
				i++;
				BackgroundFill fill = new BackgroundFill(Color.DARKGREEN, new CornerRadii(0), null); 
				if (i == 0) {
					label.setBackground(new Background(original));
				}

				if (i > 0) {
					label.setBackground(new Background(fill)); // changes the background for positive values
				}
				label.setText("" + i); // updates the value inside the label
			}
		}
		class LabelDecreaser implements EventHandler<ActionEvent> { // even handler for the second button
			@Override
			public void handle(ActionEvent event) {
				i--;
				BackgroundFill fill = new BackgroundFill(Color.DARKRED, new CornerRadii(0), null);
				if (i == 0) {
					label.setBackground(new Background(original));
				}

				if (i < 0) {
					label.setBackground(new Background(fill));
				}
				label.setText("" + i);
			}
		}
		label.setAlignment(Pos.CENTER);// fixing alignments of the containers and the labels
		label.setMaxWidth(Double.MAX_VALUE);
		yardena.setOnAction(new LabelDecreaser()); // set the button's event handlers
		ofra.setOnAction(new LabelIncreaser());
		VBox vbox = new VBox(root, label);
		vbox.setAlignment(Pos.CENTER);
		root.setAlignment(Pos.CENTER);
		vbox.setPrefWidth(400);
		vbox.setPadding(new Insets(5, 30, 5, 30));
		return vbox;
	}
}
