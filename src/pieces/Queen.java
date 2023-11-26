package src.pieces;

import src.*;
import src.move.*;
import src.piece.*;

public class Queen implements Piece {
	private Board board;
	private Coordinate coordinate;
	private PieceColor pieceColor;

	public Queen(Board board, Coordinate coordinate, PieceColor pieceColor) {
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
		return PieceType.QUEEN;
	}

	public String getPieceSymbol() {
		return this.pieceColor == PieceColor.WHITE ? PieceSymbol.WHITE_QUEEN : PieceSymbol.BLACK_QUEEN;
	}

	public boolean canMoveTo(Coordinate coordinate, boolean isCapture) {
		Move move = new Move(this.coordinate, coordinate);
		return move.isFileMove() || move.isRankMove() || move.isDiagonalMove();
	}

	public ValidateMoveResult validateMoveTo(Coordinate coordinate, boolean isCapture) {
		Move move = new Move(this.coordinate, coordinate);

		if (coordinate.isOutOfBoard()) {
			return new ValidateMoveResult(false, ValidateMoveError.OUTSIDE_BOARD);
		}

		if (!move.isFileMove() && !move.isRankMove() && !move.isDiagonalMove()) {
			return new ValidateMoveResult(false, ValidateMoveError.NOT_QUEEN_MOVE);
		}

		if (move.isFileMove() && !move.isFileFree(this.board, true)) {
			return new ValidateMoveResult(false, ValidateMoveError.FILE_NOT_FREE);
		}

		if (move.isRankMove() && !move.isRankFree(this.board, true)) {
			return new ValidateMoveResult(false, ValidateMoveError.RANK_NOT_FREE);
		}

		if (move.isDiagonalMove() && !move.isDiagonalFree(this.board, true)) {
			return new ValidateMoveResult(false, ValidateMoveError.DIAGONAL_NOT_FREE);
		}

		if (move.doesSelfCheck(this.board, this.pieceColor == PieceColor.WHITE)) {
			return new ValidateMoveResult(false, ValidateMoveError.KING_IN_CHECK);
		}

		return new ValidateMoveResult(true, null);
	}

	public boolean canCaptureAt(Coordinate coordinate) {
		Move move = new Move(this.coordinate, coordinate);

		return (move.isFileMove() && move.isFileFree(this.board, true))
			|| (move.isRankMove() && move.isRankFree(this.board, true))
			|| (move.isDiagonalMove() && move.isDiagonalFree(this.board, true));
	}
}