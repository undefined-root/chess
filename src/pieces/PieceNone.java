package src.pieces;

import src.move.Coordinate;
import src.piece.*;

public class PieceNone implements Piece {
	public Coordinate getCoordinate() {
		return new Coordinate(-1, -1);
	}

	public void setCoordinate(Coordinate coordinate) {
	}

	public PieceColor getPieceColor() {
		return PieceColor.NONE;
	}

	public PieceType getPieceType() {
		return PieceType.NONE;
	}

	public String getPieceSymbol() {
		return PieceSymbol.NONE;
	}

	public boolean canMoveTo(Coordinate coordinate, boolean isCapture) {
		return false;
	}

	public ValidateMoveResult validateMoveTo(Coordinate coordinate, boolean isCapture) {
		return new ValidateMoveResult(false, null);
	}

	public boolean canCaptureAt(Coordinate coordinate) {
		return false;
	}
}