package src.piece;

import src.move.Coordinate;

public interface Piece {
	public Coordinate getCoordinate();
	public void setCoordinate(Coordinate coordinate);
	public PieceColor getPieceColor();
	public PieceType getPieceType();
	public String getPieceSymbol();
	public boolean canMoveTo(Coordinate coordinate, boolean isCapture);
	public ValidateMoveResult validateMoveTo(Coordinate coordinate, boolean isCapture);
	public boolean canCaptureAt(Coordinate coordinate);
}