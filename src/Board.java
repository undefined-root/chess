package src;

import java.util.*;

import src.console.ConsoleColors;
import src.controllers.MoveController;
import src.move.*;
import src.piece.*;
import src.pieces.*;

public class Board {
	private Piece[][] board;

	public Board() {
		this.board = new Piece[Constants.BOARD_SIZE][Constants.BOARD_SIZE];
	}

	public void setupBoard() {
		PieceType[] backRankPieces = {
			PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
			PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
		};

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				this.board[i][j] = new PieceNone();
			}
		}

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			this.board[i][0] = new PieceFactory(this, new Coordinate(i, 0), PieceColor.WHITE, backRankPieces[i]).piece;
			this.board[i][Constants.BOARD_LAST_INDEX] = new PieceFactory(this, new Coordinate(i, Constants.BOARD_LAST_INDEX), PieceColor.BLACK, backRankPieces[i]).piece;

			this.board[i][1] = new PieceFactory(this, new Coordinate(i, 1), PieceColor.WHITE, PieceType.PAWN).piece;
			this.board[i][Constants.BOARD_LAST_INDEX - 1] = new PieceFactory(this, new Coordinate(i, Constants.BOARD_LAST_INDEX - 1), PieceColor.BLACK, PieceType.PAWN).piece;
		}
	}

	public List<Piece> getAllPieces(PieceColor... pieceColor) {
		List<Piece> pieces = new ArrayList<>();

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				Piece piece = this.board[i][j];
				boolean isValidPieceColor = pieceColor.length == 0 || piece.getPieceColor() == pieceColor[0];

				if (isValidPieceColor) {
					pieces.add(piece);
				}
			}
		}

		return pieces;
	}

	public <T extends Piece> List<T> getPieces(Class<T> pieceClass, PieceColor... pieceColor) {
		List<T> pieces = new ArrayList<>();

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				Piece piece = this.board[i][j];
				boolean isValidPieceColor = pieceColor.length == 0 || piece.getPieceColor() == pieceColor[0];

				if ((pieceClass).isInstance(piece) && isValidPieceColor) {
					pieces.add(pieceClass.cast(piece));
				}
			}
		}

		return pieces;
	}

	public Piece getPieceAt(Coordinate coordinate) {
		return this.board[coordinate.x][coordinate.y];
	}

	public boolean isEmptyAt(Coordinate coordinate) {
		return this.board[coordinate.x][coordinate.y].getPieceColor() == PieceColor.NONE;
	}

	public void movePiece(Move move) {
		this.board[move.to.x][move.to.y] = this.board[move.from.x][move.from.y];
		this.board[move.from.x][move.from.y] = new PieceNone();

		this.board[move.to.x][move.to.y].setCoordinate(move.to);
	}

	public void promotePiece(int fromX, int toX, PieceColor pieceColor, PieceType promotePieceType) {
		this.board[fromX][Constants.BOARD_LAST_INDEX - 1] = new PieceNone();
		this.board[toX][Constants.BOARD_LAST_INDEX] = new PieceFactory(this, new Coordinate(toX, Constants.BOARD_LAST_INDEX), pieceColor, promotePieceType).piece;
		this.board[toX][Constants.BOARD_LAST_INDEX].setCoordinate(new Coordinate(toX, Constants.BOARD_LAST_INDEX));
	}

	public void castleKing(boolean isWhiteMove, CastleType castleType) {
		final int KING_FROM_X = 4;
		int kingToX;
		int rookFromX;
		int rookToX;
		int y = isWhiteMove ? 0 : Constants.BOARD_LAST_INDEX;

		if (castleType == CastleType.SHORT) {
			kingToX = Constants.BOARD_LAST_INDEX - 1;
			rookFromX = Constants.BOARD_LAST_INDEX;
			rookToX = Constants.BOARD_LAST_INDEX - 2;
		} else {
			kingToX = 2;
			rookFromX = 0;
			rookToX = 3;
		}

		movePiece(new Move(new Coordinate(KING_FROM_X, y), new Coordinate(kingToX, y)));
		movePiece(new Move(new Coordinate(rookFromX, y), new Coordinate(rookToX, y)));
	}

	public Board copyBoard() {
		Board newBoard = new Board();

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				newBoard.board[i][j] = new PieceFactory(
					newBoard,
					this.board[i][j].getCoordinate(),
					this.board[i][j].getPieceColor(),
					this.board[i][j].getPieceType()
				).piece;
			}
		}

		return newBoard;
	}

	public void outputBoard(boolean isWhiteMove, MoveController moveController, boolean doRotateBoard) {
		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			int y = getOutputBoardY(isWhiteMove, doRotateBoard, i);
			System.out.print(y + 1 + " ");

			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				int x = getOutputBoardX(isWhiteMove, doRotateBoard, j);
				Coordinate pieceCoordinate = new Coordinate(x, y);
				String pieceOutputColor = isRecentCoordinateAt(pieceCoordinate, moveController) ? ConsoleColors.YELLOW : "";
				String pieceSymbol = getPieceAt(pieceCoordinate).getPieceSymbol();

				System.out.print(pieceOutputColor + pieceSymbol + ConsoleColors.RESET + " ");
			}

			System.out.println();
		}

		printHorizontalRuler(isWhiteMove, doRotateBoard);
	}

	private int getOutputBoardX(boolean isWhiteMove, boolean doRotateBoard, int index) {
		return doRotateBoard ?
			(isWhiteMove ? index : Constants.BOARD_LAST_INDEX - index)
			: index;
	}

	private int getOutputBoardY(boolean isWhiteMove, boolean doRotateBoard, int index) {
		return doRotateBoard ?
			(isWhiteMove ? Constants.BOARD_LAST_INDEX - index : index)
			: Constants.BOARD_LAST_INDEX - index;
	}

	private void printHorizontalRuler(boolean isWhiteMove, boolean doRotateBoard) {
		final String RANK_STRING = "a b c d e f g h";

		if (!doRotateBoard || isWhiteMove) {
			System.out.println("  " + RANK_STRING);
		} else {
			System.out.println("  " + new StringBuilder(RANK_STRING).reverse().toString());
		}
	}

	private boolean isRecentCoordinateAt(Coordinate coordinate, MoveController moveController) {
		return coordinate.equalsTo(moveController.move.from)
			|| coordinate.equalsTo(moveController.move.to);
	}
}