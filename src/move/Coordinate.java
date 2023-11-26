package src.move;

import src.Constants;

public class Coordinate {
	public int x;
	public int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equalsTo(Coordinate coordinate) {
		return this.x == coordinate.x && this.y == coordinate.y;
	}

	public boolean isOutOfBoard() {
		return this.x < 0 || this.x > Constants.BOARD_LAST_INDEX
			|| this.y < 0 || this.y > Constants.BOARD_LAST_INDEX;
	}
}