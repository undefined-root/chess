package src.arguments;

public class ArgumentsReader {
	public boolean doRotateBoard;
	public boolean isSinglePlayer;

	public ArgumentsReader(String[] arguments) {
		this.doRotateBoard = false;
		this.isSinglePlayer = false;

		setArguments(arguments);
	}

	private void setArguments(String[] arguments) {
		for (String argument : arguments) {
			switch (argument) {
				case Arguments.ROTATE_BOARD:
					this.doRotateBoard = true;
				case Arguments.SINGLE_PLAYER:
					this.isSinglePlayer = true;
				default:
					break;
			}
		}
	}
}