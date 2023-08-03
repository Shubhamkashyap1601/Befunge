package befunge.Loader;

import java.util.Scanner;
import java.io.*;
import java.nio.file.*;

public class FileToMatrix {
    public static Matrix makeMatrix(Path file, int height, int width) {
        String content = null;

        try {
            Scanner scanner = new Scanner(file);
            content = scanner.useDelimiter("\\A").next();
            scanner.close();
        } catch (IOException e) {
            content = "";
        }

        /*
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        String line = null;
        
        while((line = reader.getLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }

        reader.close();
        String content = builder.toString();
        */

        return StringToMatrix.makeMatrix(content, height, width);
    }
}

