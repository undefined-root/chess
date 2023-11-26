package src.pieces;

import src.*;
import src.move.*;
import src.piece.*;

public class King implements Piece {
	private Board board;
	private Coordinate coordinate;
	private PieceColor pieceColor;

	public King(Board board, Coordinate coordinate, PieceColor pieceColor) {
		this.board = board;
		this.coordinate = coordinate;
		this.pieceColor = pieceColor;
	}

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public PieceColor getPieceColor() {
		return this.pieceColor;
	}

	public PieceType getPieceType() {
		return PieceType.KING;
	}

	public String getPieceSymbol() {
		return this.pieceColor == PieceColor.WHITE ? PieceSymbol.WHITE_KING : PieceSymbol.BLACK_KING;
	}

	public boolean canMoveTo(Coordinate coordinate, boolean isCapture) {
		Move move = new Move(this.coordinate, coordinate);
		return move.isKingMove();
	}

	public ValidateMoveResult validateMoveTo(Coordinate coordinate, boolean isCapture) {
		Move move = new Move(this.coordinate, coordinate);

		if (coordinate.isOutOfBoard()) {
			return new ValidateMoveResult(false, ValidateMoveError.OUTSIDE_BOARD);
		}

		if (!move.isKingMove()) {
			return new ValidateMoveResult(false, ValidateMoveError.NOT_KING_MOVE);
		}

		if (this.pieceColor == this.board.getPieceAt(coordinate).getPieceColor()) {
			return new ValidateMoveResult(false, ValidateMoveError.SQUARE_NOT_FREE);
		}

		if (move.doesSelfCheck(this.board, this.pieceColor == PieceColor.WHITE)) {
			return new ValidateMoveResult(false, ValidateMoveError.KING_IN_CHECK);
		}

		return new ValidateMoveResult(true, null);
	}

	public boolean canCaptureAt(Coordinate coordinate) {
		Move move = new Move(this.coordinate, coordinate);
		return move.isKingMove() && this.pieceColor != this.board.getPieceAt(coordinate).getPieceColor();
	}
}