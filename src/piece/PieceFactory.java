package src.piece;

import src.*;
import src.move.Coordinate;
import src.pieces.*;

public class PieceFactory {
	public Piece piece;

	public PieceFactory(Board board, Coordinate coordinate, PieceColor pieceColor, PieceType pieceType) {
		this.piece = getPiece(board, coordinate, pieceColor, pieceType);
	}

	private Piece getPiece(Board board, Coordinate coordinate, PieceColor pieceColor, PieceType pieceType) {
		switch (pieceType) {
			case PAWN:
				return new Pawn(board, coordinate, pieceColor);
			case KNIGHT:
				return new Knight(board, coordinate, pieceColor);
			case BISHOP:
				return new Bishop(board, coordinate, pieceColor);
			case ROOK:
				return new Rook(board, coordinate, pieceColor);
			case QUEEN:
				return new Queen(board, coordinate, pieceColor);
			case KING:
				return new King(board, coordinate, pieceColor);
			default:
				return new PieceNone();
		}
	}
}