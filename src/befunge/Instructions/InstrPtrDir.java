package befunge.Instructions;

import java.util.*;
import static befunge.Components.InstrPointer.Direction.*;
import befunge.Interpreter.BefungeMachine;


public class InstrPtrDir extends Instruction {
	public InstrPtrDir(char c) {
		op = c;
	}

	void process (BefungeMachine machine) {
		switch(op) {
			case '>': 
				machine.instr_ptr.setDirection(RIGHT);
				break;
			case '<':
				machine.instr_ptr.setDirection(LEFT);
				break;
			case 'v': 
				machine.instr_ptr.setDirection(DOWN);
				break;
			case '^': 
				machine.instr_ptr.setDirection(UP);
				break;
			case '_': 
				int x = machine.stack.pop();
				if(x == 0)
					machine.instr_ptr.setDirection(RIGHT);
				else 
					machine.instr_ptr.setDirection(LEFT);
					break;
			case '?': 
					Random randomGen = new Random(System.nanoTime());
					int z= randomGen.nextInt(4);
					switch(z)
					{
						case 0:
							machine.instr_ptr.setDirection(UP);
							break;
						case 1:
							machine.instr_ptr.setDirection(DOWN);
							break;
						case 2:
							machine.instr_ptr.setDirection(LEFT);
							break;
						case 3:
							machine.instr_ptr.setDirection(RIGHT);
							break;
					}
					break;
			case '|': 
				int Y = machine.stack.pop();
				if(Y == 0)
					machine.instr_ptr.setDirection(DOWN);
				else
					machine.instr_ptr.setDirection(UP);
					break;
		

		}
	}

}

