package mines;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MinesFX extends Application {

	private Mines mineBoard;
	private Button buttons[][];
	private int height, width, numOfMines;
	private HBox hbox;
	private Controller controller;
	private Stage stage;
	private ImageView smiley;
	private GridPane grid;
	private Button resetBTN;
	private ProgressIndicator progress;

	private boolean isEmpty = false, gameOver = false;

	@Override
	public void start(Stage stage) { // regular stage/ controller / containers init

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MinesFXML.fxml"));
			hbox = loader.load();
			controller = loader.getController();
			smiley = controller.GetImageField();
			this.stage = stage;
			progress = controller.prog();

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		resetBTN = controller.GetResetBTN();

		Scene scene = new Scene(hbox);
		stage.setTitle("The Amazing Minesweeper");
		stage.setScene(scene);
		stage.show();
		isEmpty = true;
		File file = new File("src/mines/images/start.png"); // adds an image to the board
		Image image = new Image(file.toURI().toString());
		smiley.setImage(image);
		resetBTN.setOnAction(new Reset(hbox));

	}

	public static void main(String[] args) {
		launch(args);
	}

	public GridPane GetGrid(int height, int width) { // returns a grid of buttons to be implemented into the container as a GridPane  instance
		GridPane gridp = new GridPane();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				buttons[i][j] = new Button();
				buttons[i][j].setPrefSize(50, 50);
				buttons[i][j].setMaxSize(50, 50);
				buttons[i][j].setMinSize(50, 50);
				buttons[i][j].setOnMouseClicked(new MouseClick(i, j)); // init an event for each button
				gridp.add(buttons[i][j], i, j);
			}
		}
		gridp.setPadding(new Insets(60));
		return gridp;
	}

	public double GetProgress() { // returns the progress of the player for the game as a double
		double prog = 0, totalsquares = height * width - numOfMines;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (mineBoard.minesMatrix[i][j].open && !mineBoard.minesMatrix[i][j].mine) {
					prog++;
				}
			}
		}
		return prog / totalsquares;
	}

	class Reset implements EventHandler<ActionEvent> { // resets the game with the user's input.
		private HBox root;

		public Reset(HBox root) {
			this.root = root;
		}

		@Override
		public void handle(ActionEvent event) { // reset button even handler
			if (!isEmpty)
				root.getChildren().remove(grid); // remove past grids from the board.
			isEmpty = false;
			gameOver = false;

			height = controller.AppHeight();
			width = controller.AppWidth();
			numOfMines = controller.InputMines();

			if (height == 0 || width == 0) { // for invalid input
				return;
			}

			mineBoard = new Mines(height, width, numOfMines); // initializes the logic class for the board

			buttons = new Button[height][width]; 

			grid = GetGrid(height, width);
			root.getChildren().add(grid);
			File file = new File("src/mines/images/playing.png"); 
			Image image = new Image(file.toURI().toString());
			smiley.setImage(image);
			progress.setProgress(0); // reset progress every reset event

			stage.setHeight(width * 60 + 300); 
			stage.setWidth(height * 65 + 300);

			for (int i = 0; i < height; i++) { // initializes all the boxes on the reset to their starting point "."
				for (int j = 0; j < width; j++) {
					buttons[i][j].setText(mineBoard.get(i, j));
				}
			}
		}

	}

	class MouseClick implements EventHandler<MouseEvent> { 

		private int i, j;

		public MouseClick(int i, int j) { // saves the mouse click position
			this.i = i;
			this.j = j;

		}

		@Override
		public void handle(MouseEvent event) { 
			MouseButton clickType = event.getButton(); 
			if (gameOver) {
				return;
			}
			if (clickType == MouseButton.PRIMARY && mineBoard.get(i, j) != "F") { // case left click (primary) clicked

				if (!mineBoard.isDone()) { // checks if a mine was clicked if so ends the game

					if (!mineBoard.open(i, j)) {
						mineBoard.setShowAll(true);
						File file = new File("src/mines/images/lose.png");
						Image image = new Image(file.toURI().toString());
						smiley.setImage(image);
						gameOver = true;

					}
				}
				if (mineBoard.isDone()) { // if the board was cleared finishes the game
					File file = new File("src/mines/images/win.jpg");
					Image image = new Image(file.toURI().toString());
					smiley.setImage(image);
				}

			}

			for (int x = 0; x < controller.AppHeight(); x++) { // updates each square of the board with it's value if it's open
				for (int y = 0; y < controller.AppWidth(); y++) {
					buttons[x][y].setText(mineBoard.get(x, y));
				}
			}

			if (clickType == MouseButton.SECONDARY) { // set's a flag "F" 
				mineBoard.toggleFlag(i, j);
				buttons[i][j].setText(mineBoard.get(i, j));
			}
			progress.setProgress(GetProgress()); // updates the progress after a click on the board was made
		}

	}

}
