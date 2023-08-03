package befunge.Instructions;

import befunge.Components.OutOfInput;
import befunge.Interpreter.BefungeMachine;

public abstract class Instruction {
	char op;

	public void execute(BefungeMachine machine) throws OutOfInput {
		process(machine);
		machine.instr_ptr.update();
	}

	abstract void process(BefungeMachine machine) throws OutOfInput;

	public static Instruction get(char c) {
		switch(c) {
			case ' ': 
				return new NoOperation();
			case '+' : case '-': case '*': case '/': case '%': // A valid class
				return new Arithmetic(c);
			case '`': case '!': case ':': case '\\': case '$':  // A valid class
				return new StackOperation(c);
			case '<': case '>': case 'v': case '^': case '?': case '_': case '|':    // A valid class
				return new InstrPtrDir(c);
			case '.': case ',': case '&': case '~': case 'g': case 'p': // A valid class
				return new IO_Operation(c);
			case '#': case '@': case '\"':  // A valid class
				return new Special(c);
			case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':  // A valid class
				//System.out.println("Entered");
				return new Digit(c);
			default: 
				//what do we do here ??
				break;
		}

		return null;  // for now
	}
}
