package src.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import src.*;
import src.move.*;
import src.notation.NotationHelper;
import src.piece.*;
import src.pieces.*;

public class MoveController {
	public Move move;
	private BufferedReader reader;
	private Board board;

	public MoveController(Board board) {
		this.reader = new BufferedReader(new InputStreamReader(System.in));
		this.board = board;
		this.move = new Move(new Coordinate(-1, -1), new Coordinate(-1, -1));
	}

	public ValidateMoveResult doUserMove(boolean isWhiteMove, int moveNumber) {
		this.move = new Move(new Coordinate(-1, -1), new Coordinate(-1, -1));

		String moveNotation = waitForMoveInput(moveNumber);
		NotationHelper notationHelper = new NotationHelper(moveNotation);

		ValidateMoveResult validateMoveResult = validateMove(notationHelper, isWhiteMove);

		if (!validateMoveResult.isOk) {
			return validateMoveResult;
		}

		this.move.from = getMoveFromIfNotSet(notationHelper, isWhiteMove);
		this.move.to = getMoveToIfNotSet(notationHelper, isWhiteMove);

		doMove(notationHelper, isWhiteMove);
		return validateMoveResult;
	}

	private String waitForMoveInput(int moveNumber) {
		try {
			String prefix = String.format("%s>", moveNumber);
			System.out.print(prefix);

			return reader.readLine();
		} catch (IOException ex) {
			return null;
		}
	}

	private ValidateMoveResult validateMove(NotationHelper notationHelper, boolean isWhiteMove) {
		List<Piece> piecesToValidate = getPiecesToValidate(notationHelper, isWhiteMove);

		if (piecesToValidate.size() == 0) {
			return new ValidateMoveResult(false, ValidateMoveError.NO_PIECES_CAN_MOVE);
		}

		if (piecesToValidate.size() != 1) {
			return new ValidateMoveResult(false, ValidateMoveError.MANY_PIECES_CAN_MOVE);
		}

		Piece validatedPiece = piecesToValidate.get(0);

		ValidateMoveResult validateMoveResult =
			validatedPiece.validateMoveTo(notationHelper.move.to, notationHelper.moveExtra.isCapture);

		if (!validateMoveResult.isOk) {
			return validateMoveResult;
		}

		this.move.from = validatedPiece.getCoordinate();
		return new ValidateMoveResult(true, null);
	}

	private List<Piece> getPiecesToValidate(NotationHelper notationHelper, boolean isWhiteMove) {
		List<? extends Piece> pieces = getPiecesFromType(notationHelper.pieceType, isWhiteMove);
		List<Piece> piecesToValidate = new ArrayList<>();

		for (Piece piece : pieces) {
			if (piece.canMoveTo(notationHelper.move.to, notationHelper.moveExtra.isCapture)) {
				piecesToValidate.add(piece);
			}
		}

		return piecesToValidate;
	}

	private List<? extends Piece> getPiecesFromType(PieceType pieceType, boolean isWhiteMove) {
		PieceColor pieceColor = isWhiteMove ? PieceColor.WHITE : PieceColor.BLACK;

		switch (pieceType) {
			case PAWN:
				return board.<Pawn>getPieces(Pawn.class, pieceColor);
			case KNIGHT:
				return board.<Knight>getPieces(Knight.class, pieceColor);
			case BISHOP:
				return board.<Bishop>getPieces(Bishop.class, pieceColor);
			case ROOK:
				return board.<Rook>getPieces(Rook.class, pieceColor);
			case QUEEN:
				return board.<Queen>getPieces(Queen.class, pieceColor);
			case KING:
				return board.<King>getPieces(King.class, pieceColor);
			default:
				return null;
		}
	}

	private Coordinate getMoveFromIfNotSet(NotationHelper notationHelper, boolean isWhiteMove) {
		if (!this.move.from.equalsTo(new Coordinate(-1, -1))) {
			return this.move.from;
		}

		if (!notationHelper.move.from.equalsTo(new Coordinate(-1, -1))) {
			return notationHelper.move.from;
		}

		if (notationHelper.moveExtra.isCastle()) {
			return new Coordinate(4, isWhiteMove ? 0 : Constants.BOARD_LAST_INDEX);
		}

		return new Coordinate(-1, -1);
	}

	private Coordinate getMoveToIfNotSet(NotationHelper notationHelper, boolean isWhiteMove) {
		if (!this.move.to.equalsTo(new Coordinate(-1, -1))) {
			return this.move.to;
		}

		if (!notationHelper.move.to.equalsTo(new Coordinate(-1, -1))) {
			return notationHelper.move.to;
		}

		if (!notationHelper.moveExtra.isCastle()) {
			return notationHelper.move.to;
		}

		return notationHelper.moveExtra.castleType == CastleType.SHORT
			? new Coordinate(Constants.BOARD_LAST_INDEX - 1, isWhiteMove ? 0 : Constants.BOARD_LAST_INDEX)
			: new Coordinate(2, isWhiteMove ? 0 : Constants.BOARD_LAST_INDEX);
	}

	private void doMove(NotationHelper notationHelper, boolean isWhiteMove) {
		PieceColor pieceColor = isWhiteMove ? PieceColor.WHITE : PieceColor.BLACK;

		if (notationHelper.moveExtra.isPromotion) {
			board.promotePiece(this.move.from.x, this.move.to.x, pieceColor, notationHelper.promotePieceType);
		} else if (notationHelper.moveExtra.isCastle()) {
			board.castleKing(isWhiteMove, notationHelper.moveExtra.castleType);
		} else {
			board.movePiece(this.move);
		}
	}
}