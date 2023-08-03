package befunge.Components;

import java.io.*;

public class BefungeOutput {
	PrintWriter output;

	public BefungeOutput(PrintWriter out) {
		output = out;
	}

	public void writeChar(char c) {
		output.print(c);
	}

	public void writeInt(int x) {
		output.print(x + " ");
	}

}
