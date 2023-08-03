package befunge.Instructions;

import befunge.Interpreter.BefungeMachine;

class StackOperation extends Instruction {
    public StackOperation(char c)
	{
		op =c;
	}
	void process(BefungeMachine machine) {
        int x,y;
        switch(op)
        {
            case '`':
                x=machine.stack.pop();
			    y=machine.stack.pop();
                if(x<y)
				    machine.stack.push(1);
                else
                    machine.stack.push(0);
				break;
            case '!':
                x=machine.stack.pop();
                if(x==0)
                    machine.stack.push(1);
                else
                    machine.stack.push(0);
				break;
            case ':':
                x=machine.stack.pop();
                machine.stack.push(x);
                machine.stack.push(x); 
                break;
            case '$':
                x=machine.stack.pop();
                break;
            case '\\':
                x=machine.stack.pop();
                y=machine.stack.pop();
                int z;
                z=x;
                x=y;
                y=z;
                machine.stack.push(y); 
                machine.stack.push(x); 
                break;
        }
    }
}

