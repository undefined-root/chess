package src;

import src.arguments.ArgumentsReader;
import src.console.ConsoleHelper;
import src.controllers.GameController;

public class Main {
	public static void main(String[] arguments) {
		ConsoleHelper consoleHelper = new ConsoleHelper();
		consoleHelper.setEncoding("UTF-8");
		consoleHelper.clearScreen();

		ArgumentsReader argumentsReader = new ArgumentsReader(arguments);
		GameController gameController = new GameController(argumentsReader);

		while (true) {
			gameController.handleUserMove();
		}
	}
}