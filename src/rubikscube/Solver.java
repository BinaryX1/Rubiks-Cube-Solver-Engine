package rubikscube;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class Solver {

    public static String Solve(cubeState start){
        long startTime = System.nanoTime();
        PriorityQueue<cubeState> openqueue = new PriorityQueue<>();
        HashMap<cubeState, Integer> openmap = new HashMap<>(); //Stores the smallest scores (heuristic+distance) of each cubestate
        HashMap<cubeState, Integer> visited = new HashMap<>();
        start.score = start.heuristic;
        start.distance = 0;
        openqueue.add(start);
        while(!openqueue.isEmpty()){
            cubeState current = openqueue.poll();
            if(openmap.get(current) != null && current.score > openmap.get(current)){
                continue; //Ignore outdated queued cubestates
            }
            for(cubeState neighbour : current.getNeighbours()){
                neighbour.distance = current.distance+1;
                neighbour.score = neighbour.heuristic + neighbour.distance;
                if(neighbour.cube.isSolved()){
                    StringBuilder Solution = new StringBuilder();
                    while(neighbour.parent != null){
                        Solution.append(neighbour.Move);
                        neighbour = neighbour.parent;
                    }
                    long endTime = System.nanoTime();
                    long totalTimeNanos = endTime - startTime;
                    double totalTimeMillis = (double) totalTimeNanos / 1_000_000.0;
                    System.out.println("Execution time in milliseconds: " + totalTimeMillis);
                    Solution.reverse();
                    return Solution.toString();
                }
                else if(openmap.get(neighbour) != null && openmap.get(neighbour) >= neighbour.score){
                    openmap.put(neighbour, neighbour.score);
                    openqueue.add(neighbour);
                }
                else if(visited.get(neighbour) != null && visited.get(neighbour) >= neighbour.score){
                    openmap.put(neighbour, neighbour.score);
                    visited.remove(neighbour);
                    openqueue.add(neighbour);
                }
                else{
                    openqueue.add(neighbour);
                }
            }
            visited.put(current, current.score);
        }
        return "deez";
    }

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
            cubeState start = new cubeState(cube);
            System.out.println(cube.hashCode());
            System.out.println(cube.toString());
            System.out.println(cube.clone());
            start.cube.applyMoves(Solve(start));
            System.out.println(start.cube.toString());
        }
        catch (IOException e){
            System.out.println("Could not open file");
        }
        catch(IncorrectFormatException e){
            System.out.println("Wrong format");
        }


    }
}
