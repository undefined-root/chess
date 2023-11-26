package src.pieces;

import src.*;
import src.move.*;
import src.piece.*;

public class Pawn implements Piece {
	private Board board;
	private Coordinate coordinate;
	private PieceColor pieceColor;

	public Pawn(Board board, Coordinate coordinate, PieceColor pieceColor) {
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
		return PieceType.PAWN;
	}

	public String getPieceSymbol() {
		return this.pieceColor == PieceColor.WHITE ? PieceSymbol.WHITE_PAWN : PieceSymbol.BLACK_PAWN;
	}

	public boolean canMoveTo(Coordinate coordinate, boolean isCapture) {
		Move move = new Move(this.coordinate, coordinate);

		boolean isPawnMove = move.isPawnMove(this.board);
		boolean isFileFree = move.isFileFree(this.board, false);
		boolean canCapture = canCaptureAt(coordinate);

		return (isPawnMove && isFileFree) || canCapture;
	}

	public ValidateMoveResult validateMoveTo(Coordinate coordinate, boolean isCapture) {
		Move move = new Move(this.coordinate, coordinate);

		if (coordinate.isOutOfBoard()) {
			return new ValidateMoveResult(false, ValidateMoveError.OUTSIDE_BOARD);
		}

		if (!canCaptureAt(coordinate)) {
			if (!move.isPawnMove(this.board)) {
				return new ValidateMoveResult(false, ValidateMoveError.NOT_PAWN_MOVE);
			}

			if (!move.isFileFree(this.board, false)) {
				return new ValidateMoveResult(false, ValidateMoveError.FILE_NOT_FREE);
			}
		}

		if (move.doesSelfCheck(this.board, this.pieceColor == PieceColor.WHITE)) {
			return new ValidateMoveResult(false, ValidateMoveError.KING_IN_CHECK);
		}

		return new ValidateMoveResult(true, null);
	}

	public boolean canCaptureAt(Coordinate coordinate) {
		int deltaX = Math.abs(this.coordinate.x - coordinate.x);
		int forwardY = this.pieceColor == PieceColor.WHITE
			? coordinate.y - this.coordinate.y
			: this.coordinate.y - coordinate.y;

		boolean isEnemyCapture = this.pieceColor == PieceColor.WHITE
			? this.board.getPieceAt(coordinate).getPieceColor() == PieceColor.BLACK
			: this.board.getPieceAt(coordinate).getPieceColor() == PieceColor.WHITE;

		return deltaX == 1 && forwardY == 1 && isEnemyCapture;
	}
}