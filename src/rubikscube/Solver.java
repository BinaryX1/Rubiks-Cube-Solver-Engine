package rubikscube;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class Solver {
	public static void main(String[] args) {

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}

		// TODO
		//File input = new File(args[0]);
		// solve...
		//File output = new File(args[1]);
        File input = new File(args[0]);
        try{
            RubiksCube cube = new RubiksCube(args[0]);
            System.out.println(cube.toString());
        }
        catch (IOException e){
            System.out.println("Could not open file");
        }
        catch(IncorrectFormatException e){
            System.out.println("Wrong format");
        }
    }
}
