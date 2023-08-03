package befunge.Loader;

import java.util.Scanner;

public class StringToMatrix {
	public static Matrix makeMatrix(String prg, int height, int width)  {
		boolean lost_info;
		int[] dim = prgDimensions(prg);
		if(dim[0] <= height && dim[1] <= width) 
			lost_info = false;
		else
			lost_info = true;


		char[][] matrix = new char[height][width];

		for(int i = 0; i < height; i++) 
			for(int j = 0; j < width; j++) 
				matrix[i][j] = ' ';
		
			
		try {
			Scanner sc = new Scanner(prg);

			for(int i = 0; i < Math.min(height, dim[0]); i++) {
				char[] line = sc.nextLine().toCharArray();

				for(int j = 0; j < Math.min(width, line.length); j++) 
					matrix[i][j] = line[j];
			}
			
			sc.close();
		} catch (Exception e) {

		}

		return new Matrix(matrix, height, width, lost_info);
	}

	private static int[] prgDimensions(String prg) {
		int height = 0;
		int width = 0;

		try {
			Scanner sc = new Scanner(prg);

			int linenum = 1;
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				width = Math.max(width, trimLength(line));

				if(width != 0)
					height = linenum;

				linenum++;
			}

			sc.close();

		} catch (Exception e) {

		}

		int[] dim = {height, width};
		return dim;
	}

	private static int trimLength(String s) {
		char[] array = s.toCharArray();

		int i;
		for(i = array.length - 1; i >= 0; i--) 
			if(!isspace(array[i]))
				break;
		return i + 1;
	}

	private static boolean isspace(char c) {
		if(c == ' ')
			return true;
		else
			return false;
	}
}



