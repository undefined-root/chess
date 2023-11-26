package src.notation;

import src.move.CastleType;
import src.move.Coordinate;
import src.move.Move;
import src.move.MoveExtra;
import src.piece.*;

public class NotationHelper {
	public MoveExtra moveExtra;
	public PieceType pieceType;
	public PieceType promotePieceType;
	public Move move;

	public NotationHelper(String notation) {
		this.moveExtra = getMoveExtra(notation);
		this.pieceType = getPieceType(notation);
		this.promotePieceType = getPromotionPieceType(notation);

		this.move = new Move(new Coordinate(-1, -1), new Coordinate(-1, -1));
		this.move.from = getMoveFrom(notation);
		this.move.to = getMoveTo(notation);
	}

	private MoveExtra getMoveExtra(String notation) {
		CastleType castleType = CastleType.NONE;

		if (notation.equals(Notation.SHORT_CASTLE)) {
			castleType = CastleType.SHORT;
		} else if (notation.equals(Notation.LONG_CASTLE)) {
			castleType = CastleType.LONG;
		}

		return new MoveExtra(
			notation.contains(Notation.CAPTURE),
			notation.contains(Notation.CHECK),
			notation.contains(Notation.CHECKMATE),
			notation.contains(Notation.PROMOTION),
			castleType
		);
	}

	private PieceType getPieceType(String notation) {
		if (this.moveExtra.isPromotion) {
			return PieceType.PAWN;
		}

		if (this.moveExtra.isCastle()) {
			return PieceType.KING;
		}

		return getPieceTypeFromNotation(notation.charAt(0));
	}

	private PieceType getPromotionPieceType(String notation) {
		if (!this.moveExtra.isPromotion) {
			return PieceType.NONE;
		}

		return getPieceTypeFromNotation(notation.charAt(notation.length() - getExtraOffset() - 1));
	}

	private Coordinate getMoveFrom(String notation) {
		if (this.moveExtra.isPromotion) {
			if (this.moveExtra.isCapture) {
				return new Coordinate(
					getXFromNotation(notation.charAt(0)),
					getYFromNotation(notation.charAt(notation.length() - getExtraOffset() - 3))
				);
			}

			return new Coordinate(
				getXFromNotation(notation.charAt(0)),
				getYFromNotation(notation.charAt(1))
			);
		}

		if (this.moveExtra.isCastle()) {
			return new Coordinate(-1, -1);
		}

		int pieceOffset = this.pieceType == PieceType.PAWN ? 0 : 1;
		int notationLengthWithoutExtraAndPiece =
			notation.length()
			- (this.moveExtra.isCapture || this.moveExtra.isCheck || this.moveExtra.isCheckmate ? 1 : 0)
			- (this.pieceType != PieceType.PAWN ? 1 : 0);

		if (notationLengthWithoutExtraAndPiece < 3) {
			return new Coordinate(-1, -1);
		}

		if (notationLengthWithoutExtraAndPiece == 4) {
			return new Coordinate(
				getXFromNotation(notation.charAt(pieceOffset)),
				getYFromNotation(notation.charAt(pieceOffset + 1))
			);
		}

		if (Character.isDigit(notation.charAt(pieceOffset))) {
			return new Coordinate(-1, getYFromNotation(notation.charAt(pieceOffset)));
		}

		return new Coordinate(getXFromNotation(notation.charAt(pieceOffset)), -1);
	}

	private Coordinate getMoveTo(String notation) {
		if (this.moveExtra.isPromotion) {
			if (!this.moveExtra.isCapture) {
				return new Coordinate(-1, -1);
			}

			return new Coordinate(
				getXFromNotation(notation.charAt(notation.length() - getExtraOffset() - 4)),
				getYFromNotation(notation.charAt(notation.length() - getExtraOffset() - 3))
			);
		}

		if (this.moveExtra.isCastle()) {
			return new Coordinate(-1, -1);
		}

		return new Coordinate(
			getXFromNotation(notation.charAt(notation.length() - getExtraOffset() - 2)),
			getYFromNotation(notation.charAt(notation.length() - getExtraOffset() - 1))
		);
	}

	private int getExtraOffset() {
		return this.moveExtra.isCheck || this.moveExtra.isCheckmate ? 1 : 0;
	}

	private int getXFromNotation(char charX) {
		return Character.getNumericValue(charX) - 10;
	}

	private int getYFromNotation(char charY) {
		return Character.getNumericValue(charY) - 1;
	}

	private PieceType getPieceTypeFromNotation(char pieceChar) {
		switch (pieceChar) {
			case Notation.KNIGHT:
				return PieceType.KNIGHT;
			case Notation.BISHOP:
				return PieceType.BISHOP;
			case Notation.ROOK:
				return PieceType.ROOK;
			case Notation.QUEEN:
				return PieceType.QUEEN;
			case Notation.KING:
				return PieceType.KING;
			default:
				return PieceType.PAWN;
		}
	}
}