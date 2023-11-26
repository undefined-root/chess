package src.move;

public class MoveExtra {
	public boolean isCapture;
	public boolean isCheck;
	public boolean isCheckmate;
	public boolean isPromotion;
	public CastleType castleType;

	public MoveExtra(boolean isCapture, boolean isCheck, boolean isCheckmate, boolean isPromotion, CastleType isCastle) {
		this.isCapture = isCapture;
		this.isCheck = isCheck;
		this.isCheckmate = isCheckmate;
		this.isPromotion = isPromotion;
		this.castleType = isCastle;
	}

	public boolean isCastle() {
		return this.castleType != CastleType.NONE;
	}
}