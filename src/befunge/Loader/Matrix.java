package befunge.Loader;

public class Matrix {
	public int height;
	public int width;
	public char[][] array;
	public boolean lost_info;

	public Matrix(char[][] a, int h, int w, boolean l) {
		height = h;
		width = w;
		array = a;
		lost_info = l;
	}
}
