package befunge.Instructions;

import befunge.Interpreter.BefungeMachine;

class Arithmetic extends Instruction {
    public Arithmetic(char c)
	{
		op =c;
	}
	void process(BefungeMachine machine) {
		int x,y;
		switch(op)
		{
			case '+':
				x=machine.stack.pop();
				y=machine.stack.pop();
				machine.stack.push(x+y);
				break;
			case '-':
				x=machine.stack.pop();
				y=machine.stack.pop();
				machine.stack.push(y-x);
				break;
			case '*':
				x=machine.stack.pop();
				y=machine.stack.pop();
				machine.stack.push(x*y);
				break;
			case '%':
				x=machine.stack.pop();
				y=machine.stack.pop();
				machine.stack.push(y%x);
				break;
			case '/':
				x=machine.stack.pop();
				y=machine.stack.pop();
				if(y!=0){
					machine.stack.push(x/y);
				} else{
					machine.stack.push(0);
				}
				break;
			
			}			
		}
	}
