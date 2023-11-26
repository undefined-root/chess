package src.pieces;

import src.*;
import src.move.*;
import src.piece.*;

public class Rook implements Piece {
	private Board board;
	private Coordinate coordinate;
	private PieceColor pieceColor;

	public Rook(Board board, Coordinate coordinate, PieceColor pieceColor) {
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
		return PieceType.ROOK;
	}

	public String getPieceSymbol() {
		return this.pieceColor == PieceColor.WHITE ? PieceSymbol.WHITE_ROOK : PieceSymbol.BLACK_ROOK;
	}

	public boolean canMoveTo(Coordinate coordinate, boolean isCapture) {
		Move move = new Move(this.coordinate, coordinate);
		return move.isFileMove() || move.isRankMove();
	}

	public ValidateMoveResult validateMoveTo(Coordinate coordinate, boolean isCapture) {
		Move move = new Move(this.coordinate, coordinate);

		if (coordinate.isOutOfBoard()) {
			return new ValidateMoveResult(false, ValidateMoveError.OUTSIDE_BOARD);
		}

		if (!move.isFileMove() && !move.isRankMove()) {
			return new ValidateMoveResult(false, ValidateMoveError.NOT_ROOK_MOVE);
		}

		if (move.isFileMove() && !move.isFileFree(this.board, true)) {
			return new ValidateMoveResult(false, ValidateMoveError.FILE_NOT_FREE);
		}

		if (move.isRankMove() && !move.isRankFree(this.board, true)) {
			return new ValidateMoveResult(false, ValidateMoveError.RANK_NOT_FREE);
		}

		return new ValidateMoveResult(true, null);
	}

	public boolean canCaptureAt(Coordinate coordinate) {
		Move move = new Move(this.coordinate, coordinate);

		return (move.isFileMove() && move.isFileFree(this.board, true))
			|| (move.isRankMove() && move.isRankFree(this.board, true));
	}
}