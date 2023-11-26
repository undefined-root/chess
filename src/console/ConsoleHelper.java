package src.console;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class ConsoleHelper {
	public void setEncoding(String encoding) {
		try {
			System.setOut(new PrintStream(System.out, true, encoding));
		} catch (UnsupportedEncodingException exception) {
			return;
		}
	}

	public void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}