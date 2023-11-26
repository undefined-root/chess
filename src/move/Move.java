package src.move;

import java.util.List;

import src.Board;
import src.Constants;
import src.piece.Piece;
import src.piece.PieceColor;
import src.pieces.King;

public class Move {
	public Coordinate from;
	public Coordinate to;

	public Move(Coordinate from, Coordinate to) {
		this.from = from;
		this.to = to;
	}

	public boolean isFileMove() {
		return this.from.x == this.to.x && this.from.y != this.to.y;
	}

	public boolean isRankMove() {
		return this.from.x != this.to.x && this.from.y == this.to.y;
	}

	public boolean isDiagonalMove() {
		return Math.abs(this.from.x - this.to.x) == Math.abs(this.from.y - this.to.y);
	}

	public boolean isPawnMove(Board board) {
		if (this.from.x != this.to.x) {
			return false;
		}

		boolean isWhiteMove = board.getPieceAt(this.from).getPieceColor() == PieceColor.WHITE;
		boolean hasMoved = isWhiteMove ? this.from.y != 1 : this.from.y != Constants.BOARD_LAST_INDEX - 1;
		int deltaY = isWhiteMove ? this.to.y - this.from.y : this.from.y - this.to.y;

		return deltaY == 1 || (deltaY == 2 && !hasMoved);
	}

	public boolean isKnightMove() {
		int deltaX = Math.abs(this.from.x - this.to.x);
		int deltaY = Math.abs(this.from.y - this.to.y);

		return (deltaX == 1 && deltaY == 2) || (deltaX == 2 && deltaY == 1);
	}

	public boolean isKingMove() {
		return Math.abs(this.from.x - this.to.x) <= 1
			&& Math.abs(this.from.y - this.to.y) <= 1;
	}

	public boolean isFileFree(Board board, boolean doCheckForCapture) {
		int moveLength = Math.abs(this.from.y - this.to.y);
		return isMoveFree(board, moveLength, doCheckForCapture);
	}

	public boolean isRankFree(Board board, boolean doCheckForCapture) {
		int moveLength = Math.abs(this.from.x - this.to.x);
		return isMoveFree(board, moveLength, doCheckForCapture);
	}

	public boolean isDiagonalFree(Board board, boolean doCheckForCapture) {
		int moveLength = Math.abs(this.from.x - this.to.x);
		return isMoveFree(board, moveLength, doCheckForCapture);
	}

	public boolean doesSelfCheck(Board board, boolean isWhiteMove) {
		Board boardAfterMove = board.copyBoard();
		boardAfterMove.movePiece(this);

		Coordinate kingCoordinate = getKingCoordinate(boardAfterMove, isWhiteMove);
		List<Piece> pieces = boardAfterMove.getAllPieces(isWhiteMove ? PieceColor.BLACK : PieceColor.WHITE);

		for (Piece piece : pieces) {
			if (piece.canCaptureAt(kingCoordinate)) {
				return true;
			}
		}

		return false;
	}

	private boolean isMoveFree(Board board, int moveLength, boolean doCheckForCapture) {
		int directionX = this.from.x == this.to.x ? 0 : (this.from.x < this.to.x ? 1 : -1);
		int directionY = this.from.y == this.to.y ? 0 : (this.from.y < this.to.y ? 1 : -1);

		for (int i = 1; i < moveLength; i++) {
			Coordinate coordinate = new Coordinate(this.from.x + i * directionX, this.from.y + i * directionY);

			if (!board.isEmptyAt(coordinate)) {
				return false;
			}
		}

		if (doCheckForCapture) {
			return board.getPieceAt(this.from).getPieceColor() != board.getPieceAt(this.to).getPieceColor();
		}

		return board.isEmptyAt(this.to);
	}

	private Coordinate getKingCoordinate(Board board, boolean isWhiteMove) {
		return board
			.getPieces(King.class, isWhiteMove ? PieceColor.WHITE : PieceColor.BLACK)
			.get(0)
			.getCoordinate();
	}
}