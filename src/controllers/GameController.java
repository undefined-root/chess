package src.controllers;

import src.Board;
import src.arguments.ArgumentsReader;
import src.console.ConsoleHelper;
import src.piece.ValidateMoveResult;

public class GameController {
	public int moveNumber;
	public boolean isWhiteMove;
	private MoveController moveController;
	private ArgumentsReader argumentsReader;
	private Board board;

	public GameController(ArgumentsReader argumentsReader) {
		this.argumentsReader = argumentsReader;
		this.moveNumber = 1;
		this.isWhiteMove = true;

		this.board = new Board();
		this.moveController = new MoveController(this.board);

		this.board.setupBoard();
		this.board.outputBoard(this.isWhiteMove, this.moveController, this.argumentsReader.doRotateBoard);
	}

	public void handleUserMove() {
		ValidateMoveResult validateMoveResult = this.moveController.doUserMove(this.isWhiteMove, this.moveNumber);

		if (!validateMoveResult.isOk) {
			System.out.println(validateMoveResult.errorMessage);
			return;
		}

		this.moveNumber++;
		switchActiveSide();

		new ConsoleHelper().clearScreen();
		this.board.outputBoard(this.isWhiteMove, this.moveController, this.argumentsReader.doRotateBoard);
	}

	private void switchActiveSide() {
		this.isWhiteMove = !this.isWhiteMove;
	}
}