package simpleFX2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class SampleController {
	int i = 0;
    @FXML
    private Button ofra;

    @FXML
    private Button yardena;

    @FXML
    private Label label;

    @FXML
    void Increaser(ActionEvent event) {
    	i++;
		BackgroundFill fill = new BackgroundFill(Color.DARKGREEN, new CornerRadii(0), null);
	

		if (i > 0) {
			label.setBackground(new Background(fill));
		}
		label.setText("" + i);
    }

    @FXML
    void Decreaser(ActionEvent event) {
    	i--;
    	
		BackgroundFill fill = new BackgroundFill(Color.DARKRED, new CornerRadii(0), null);

    	if (i < 0) {
			label.setBackground(new Background(fill));
		}
		label.setText("" + i);
    }

}
