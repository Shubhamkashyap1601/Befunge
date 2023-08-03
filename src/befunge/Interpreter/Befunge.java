package befunge.Interpreter;
import befunge.Interpreter.BefungeMachine;
import befunge.Loader.FileToMatrix;
import befunge.Loader.Matrix;

import java.nio.file.*;
import java.util.*;
import java.io.*;

public class Befunge {
	public static void main(String[] args) {
		Path file = Paths.get(args[0]);

		Scanner sc = new Scanner(System.in);

		int height = 20;
		int width = 80;
		BefungeMachine befunge = new BefungeMachine(height, width, sc, System.out);

		
		Matrix M;
		try { 
			M = FileToMatrix.makeMatrix(file, height, width);
			befunge.instr_mem.load(M);
		} catch(FileNotFoundException e) {
			System.out.println("The file does not exist");
			return;
		}
		
		//befunge.instr_mem.display();

		/*
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) 
				System.out.print(M.array[i][j]);
			System.out.println();
		}
		*/
		
		


		befunge.run();

		sc.close();
		
	}

}
