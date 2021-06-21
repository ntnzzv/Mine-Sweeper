package mines;

import java.util.HashSet;
import java.util.Random;

public class Mines {

	public class Point { // Point class for further references via Square
		int i, j;

		Point(int i, int j) {

			this.i = i;
			this.j = j;
		}

	}

	public class Square { // A class of a box inside a minesweeper game holds everything neccessery
		Point p;
		boolean open = false, flag = false, mine = false;
		int minesNearBy = 0;
		Mines minesBoard;

		Square(int i, int j, Mines currentBoard) { // initialize point + minesBoard reference
			p = new Point(i, j);
			minesBoard = currentBoard;
		}

		void checkFlag() {
			if (flag) {
				flag = false;
			} else {
				flag = true;
			}
		}

		boolean setAsOpen() {
			open = true;
			return mine;
		}

		boolean setMine() {
			mine = true;
			return mine;
		}

		HashSet<Square> getNeighbours() { // retrieves the neighbours of the specific square if inside valid bounds

			HashSet<Square> neighbours = new HashSet<Square>();

			if (p.i - 1 >= 0) {
				neighbours.add(minesBoard.minesMatrix[p.i - 1][p.j]);
			}
			if (p.i + 1 < minesBoard.maxHeight) {
				neighbours.add(minesBoard.minesMatrix[p.i + 1][p.j]);
			}
			if (p.j - 1 >= 0) {
				neighbours.add(minesBoard.minesMatrix[p.i][p.j - 1]);
			}
			if (p.j + 1 < minesBoard.maxWidth) {
				neighbours.add(minesBoard.minesMatrix[p.i][p.j + 1]);
			}
			if (p.i - 1 >= 0 && p.j - 1 >= 0) {
				neighbours.add(minesBoard.minesMatrix[p.i - 1][p.j - 1]);
			}
			if (p.i - 1 >= 0 && p.j + 1 < minesBoard.maxWidth) {
				neighbours.add(minesBoard.minesMatrix[p.i - 1][p.j + 1]);
			}
			if (p.i + 1 < minesBoard.maxHeight && p.j - 1 >= 0) {
				neighbours.add(minesBoard.minesMatrix[p.i + 1][p.j - 1]);
			}
			if (p.i + 1 < minesBoard.maxHeight && p.j + 1 < minesBoard.maxWidth) {
				neighbours.add(minesBoard.minesMatrix[p.i + 1][p.j + 1]);
			}

			return neighbours;
		}

		public String toString() {

			if (!open) {
				if (flag) {
					return "F";
				}
				return ".";
			}

			if (mine) {
				return "X";
			}
			if (minesNearBy == 0) {
				return " ";
			}
			return "" + minesNearBy;

		}
	}

	public Square[][] minesMatrix;
	private int maxHeight, maxWidth;
	private boolean showAll = false;

	public Mines(int height, int width, int numMines) { // initializes minesweeper matrix

		maxHeight = height;
		maxWidth = width;
		if (numMines > height * width) {
			numMines = height * width - 1;
		}

		int i, j, counter = numMines;
		minesMatrix = new Square[height][width];

		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				minesMatrix[i][j] = new Square(i, j, this); // initializes a square for each place in the matrix
			}
		}
		Random rand = new Random(); // adds mines on random locations
		while (counter > 0) {
			i = rand.nextInt(height);
			j = rand.nextInt(width);

			if (!minesMatrix[i][j].mine) {
				addMine(i,j);
				counter--;
			}
		}
	}

	public boolean addMine(int i, int j) { // adds a mine in a specific place on the matrix

		if (minesMatrix[i][j].mine) {
			return false;
		}

		minesMatrix[i][j].setMine();

		for (Square s : minesMatrix[i][j].getNeighbours()) { // updates its neighbours that they are near a mine
			s.minesNearBy++;
		}
		return true;
	}

	public boolean open(int i, int j) { // opens neighbours

		if (minesMatrix[i][j].mine) {
			return false;
		}

		minesMatrix[i][j].setAsOpen();

		if (minesMatrix[i][j].minesNearBy == 0) {

			for (Square s : minesMatrix[i][j].getNeighbours()) {
				if (!s.open) {
					s.minesBoard.open(s.p.i, s.p.j);
				}
			}
		}

		return true;

	}

	public void toggleFlag(int x, int y) { // toggles the flag in the square instance

		minesMatrix[x][y].checkFlag();

	}

	public boolean isDone() { // checks if all the matrix is open and game is finished

		for (int i = 0; i < maxHeight; i++) {
			for (int j = 0; j < maxWidth; j++) {

				if (!minesMatrix[i][j].open && !minesMatrix[i][j].mine) {
					return false;
				}
			}
		}
		return true;
	}

	public String get(int i, int j) {
		String temp = "";
		if (showAll) {
			boolean flag = minesMatrix[i][j].open;
			minesMatrix[i][j].open = true;
			temp = minesMatrix[i][j].toString();
			minesMatrix[i][j].open = flag;
			return temp;
		}
		return minesMatrix[i][j].toString();
	}

	public void setShowAll(boolean showAll) { // shows all of the matrix square instances as their string
		this.showAll = showAll;
	}

	public String toString() {

		StringBuilder str = new StringBuilder();
		for (int i = 0; i < maxHeight; i++) {
			for (int j = 0; j < maxWidth; j++)
				str.append(this.get(i, j));
			str.append("\n");
		}
		return str.toString();

	}
}
