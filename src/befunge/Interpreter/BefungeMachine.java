package befunge.Interpreter;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import befunge.Instructions.Instruction;
import befunge.Loader.*;
import befunge.Components.*;


public class BefungeMachine {
	public enum ModeTypes {STRING, CMD};

	public InstrPointer instr_ptr;
	public InstrMemory instr_mem;
	public BefungeInput input_port;
	public BefungeOutput output_port;
	public BefungeStack stack;
	public ModeTypes mode;
	public boolean running;
	
	public int height;
	public int width;

	public BefungeMachine(int height, int width, PrintWriter pw) {
		instr_ptr = new InstrPointer(height, width);
		instr_mem = new InstrMemory(height, width);
		stack = new BefungeStack();
		input_port = new BefungeInput();
		output_port = new BefungeOutput(pw);
		
		this.height = height;
		this.width = width;
		
		reset();
	}

	public void step() throws OutOfInput {
		if(running == false)
			return;
		
		char c = instr_mem.read(instr_ptr.value());
		
		if(mode == ModeTypes.STRING && c != '\"') {
			stack.push(c);
			instr_ptr.update();
		} else {
			Instruction inst = Instruction.get(c);
			inst.execute(this);
		}
	
	}

	public void step(int n) throws OutOfInput {
		while(n-- > 0)
			step();
	}

	public void run() throws OutOfInput {
		while(running) {
			step();
			//System.out.println("Running: " + running);
		}
	}

	public void reset() {
		instr_ptr.reset();
		stack.clear();
		running = true;
		mode = ModeTypes.CMD;
	}

	public void reset_and_clear() {
		reset();
		instr_mem.clear();
	}
	
	public void load(String s) {
		Matrix M = StringToMatrix.makeMatrix(s, height, width);
		instr_mem.load(M);
		reset();
	}
	
}
