package befunge.Instructions;

import befunge.Interpreter.BefungeMachine;
import befunge.Components.OutOfInput;
import befunge.Components.Pair;

class IO_Operation extends Instruction {
	IO_Operation(char c) {
		op = c;
	}

	void process(BefungeMachine machine) throws OutOfInput {
		int x;
		switch(op) {
			case '.': 
				x = machine.stack.pop();
				machine.output_port.writeInt(x);
				break;
			case ',': 
				char c = (char) machine.stack.pop();
				machine.output_port.writeChar(c);
				break;
			case '&': 
				x = machine.input_port.readInt();
				machine.stack.push(x);
				break;
			case '~': 
				x = machine.input_port.readChar();
				machine.stack.push(x);
				break;
			case 'g': 
				int q = machine.stack.pop();
				int p = machine.stack.pop();
				x = machine.instr_mem.read(new Pair(p, q));
				machine.stack.push(x);
				break;
			case 'p': 
				q = machine.stack.pop();
				p = machine.stack.pop();
				c = (char) machine.stack.pop();
				machine.instr_mem.write(new Pair(p, q), c);
				break;
			default: 
				//What do we do here ??
				break;
		}
	}
}
