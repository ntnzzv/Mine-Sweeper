package mines;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


public class Controller {

    @FXML
    private Button resetBTN;

    @FXML
    private TextField widthFLD;

    @FXML
    private TextField heightFLD;

    @FXML
    private TextField minesFLD;

    @FXML
    private ImageView GameImg;
    
    @FXML
    private ProgressIndicator ProgressSymbol;

    public ProgressIndicator prog() {
    	return ProgressSymbol;
    }
    public ImageView GetImageField() {
    	return GameImg;
    }

    public Button GetResetBTN() {
    	return resetBTN;
    }
    
    public int AppHeight() { // returns the Height of the application
    	int height;
    	try {
    		height = Integer.parseInt(heightFLD.getText());
    		return height >=0 ? height : 0;
    	}catch(Exception e) {return 0;}
    }
    
	public int AppWidth() { // returns the width of the application
		int width;
		try {
			width = Integer.parseInt(widthFLD.getText());
			return width >= 0 ? width : 0;
		}
		catch(Exception e){return 0;}
	}

	public int InputMines() { // returns the number of mines to implement
		int mines;
		try {
			mines = Integer.parseInt(minesFLD.getText());
			return mines > 0 ? mines : 0;
		}catch(Exception e){return 0;}
	}

}
