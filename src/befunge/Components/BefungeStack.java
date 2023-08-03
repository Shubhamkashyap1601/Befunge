package befunge.Components;

public class BefungeStack {
	private Node top;

	public BefungeStack() {
		top = null;
	}

	public int pop() {
		if(top == null) 
			return 0;
		else {
			int hold = top.val;
			top = top.next;
			return hold;
		}
	}

	public void push(int x) {
		Node N = new Node(x);
		N.next = top;
		top = N;
	}

	public void clear() {
		top = null;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder("");
		
		Node ptr = top;
		while(ptr != null) {
			s.append(ptr.val + "\n");
			ptr = ptr.next;
		}
		
		return s.toString();
	}
			
}


class Node {
	int val;
	Node next;

	Node(int x) {
		val = x;
	}

	Node() {
	}
}
