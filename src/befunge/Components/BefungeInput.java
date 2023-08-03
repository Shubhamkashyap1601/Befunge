package befunge.Components;

import java.util.*;


public class BefungeInput {
	Scanner input;
	
	public void setScanner(Scanner sc) {
		input = sc;
	}
	
	public Scanner getScanner() {
		return input;
	}
	
	public int readInt() throws OutOfInput {
		int x = 0;
		
		try {
			x = input.nextInt();
			readChar();
		} catch (Exception e) {
			throw new OutOfInput();
		}

		return x;
	}

	public char readChar() throws OutOfInput {
		try {
			input.useDelimiter("");
			char c = input.next().charAt(0);
			input.reset();
			return c;
		} catch (Exception e) {
			throw new OutOfInput();
		}

	
	}
	
}

