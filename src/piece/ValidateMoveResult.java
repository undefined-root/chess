package src.piece;

public class ValidateMoveResult {
	public boolean isOk;
	public String errorMessage;

	public ValidateMoveResult(boolean isOk, String errorMessage) {
		this.isOk = isOk;
		this.errorMessage = errorMessage;
	}
}