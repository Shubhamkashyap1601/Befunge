package befunge.Instructions;

import befunge.Interpreter.BefungeMachine;

class Digit extends Instruction {
	Digit(char c) {
		op = c;
	}

    void process(BefungeMachine machine) {
        
        switch(op){
        case '0':
        machine.stack.push(0);
        break;
        case '1':
        machine.stack.push(1);
        break;
        case '2':
        machine.stack.push(2);
        break;
        case '3':
        machine.stack.push(3);
        break;
        case '4':
        machine.stack.push(4);
        break;
        case '5':
        machine.stack.push(5);
        break;
        case '6':
        machine.stack.push(6);
        break;
        case '7':
        machine.stack.push(7);
        break;
        case '8':
        machine.stack.push(8);
        break;
        case '9':
        machine.stack.push(9);
        break;

        }
    }  
}
