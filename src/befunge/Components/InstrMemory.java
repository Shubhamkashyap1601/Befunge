package befunge.Components;

import befunge.Loader.Matrix;

public class InstrMemory {
	public int height;
	public int width;
	private char[][] memory;

	public InstrMemory(int h, int w) {
		height = h;
		width = w;
		memory = new char[height][width];
		clear();
	}
	
	public char[][] getMatrix() {
		return memory;
	}

	public void load(Matrix matrix) {
		clear();

		char[][] array = matrix.array;
		for(int i = 0; i < Math.min(array.length, height); i++) {
			char[] row = array[i];

			for(int j = 0; j < Math.min(row.length, width); j++) 
				memory[i][j] = row[j];
		}
	}

	public void display(int x, int y) {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(i == x && j == y) 
					System.out.print("█");	
				else
					System.out.print(memory[i][j]);
			}
			System.out.println();
		}
	}

	public void clear() {
		for(int i = 0; i < height; i++) 
			for(int j  = 0; j < width; j++) 
				memory[i][j] = ' ';
	}

	public char read(Pair loc) {
		if(loc.x >= height || loc.x < 0 ||
		   loc.y >= width  || loc.y < 0)
				return 0;   //What should we do here ??
	
		return memory[loc.x][loc.y];
	}

	public void write(Pair loc, char c) {
		if(loc.x >= height || loc.x < 0 ||
		   loc.y >= width  || loc.y < 0)
				return;   //What should we do here ??
	
		memory[loc.x][loc.y] = c;
	}

}
