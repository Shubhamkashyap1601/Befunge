package befunge.Instructions;

import befunge.Interpreter.BefungeMachine;
import static befunge.Interpreter.BefungeMachine.ModeTypes.*;

class Special extends Instruction {
	Special(char c) {
		op = c;
	}

	void process(BefungeMachine machine) {
		switch(op) {
			case '#': 
				machine.instr_ptr.update();
				break;
			case '@': 
				machine.running = false;
				break;
			case '\"': 
				if(machine.mode == STRING) 
					machine.mode = CMD;
				else
					machine.mode = STRING;
				break;
		}
	}
}
