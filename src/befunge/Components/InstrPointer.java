package befunge.Components;

public class InstrPointer {
	public static enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT,
	}

	private int height;
	private int width;
	private Pair loc;
	private Direction dir;

	public InstrPointer(int h, int w) {
		height = h;
		width = w;
		loc = new Pair(0, 0);
		dir = Direction.RIGHT;
	}


	public void setDirection (Direction new_dir) {
		dir = new_dir;
	}

	public Pair value() {
		return new Pair(loc.x, loc.y);
	}

	public void reset() {
		loc.x = 0;
		loc.y = 0;

		dir = Direction.RIGHT;
	}

	public void update() {
		//System.out.println(loc.x + " " + loc.y);

		switch (dir) {
			case UP: 
				loc.x--;
				break;
			case DOWN: 
				loc.x++;
				break;
			case LEFT: 
				loc.y--;
				break;
			case RIGHT: 
				loc.y++;
				break;
		}

		wrap();
	}

	private void wrap() {
		if(loc.x >= height) 
			loc.x %= height;
		else if (loc.x < 0) 
			loc.x = loc.x % height + height;

		if(loc.y >= width) 
			loc.y %= width;
		else if (loc.y < 0) 
			loc.y = loc.y % width + width;
	}

	public static void main(String[] args) {
		InstrPointer P = new InstrPointer(10, 5);
		P.setDirection(Direction.DOWN);

		for(int i = 0; i < 20; i++) 
				P.update();
	}

}

