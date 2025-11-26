package rubikscube;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class Solver {
    static byte[][] eoSliceTable;
    static byte[][] coSliceTable;

    static StringBuilder phase1sol = new StringBuilder();

    public static String Solve1(cubeState start){
        phase1sol.setLength(0);
        int threshold = getHeuristic1(start);

        while(true){
            int distance = search(start, 0 ,threshold); //starting distance of 0 from start

            if(distance == -1){
                return phase1sol.reverse().toString();
            }

            if(distance == 12){
                System.out.println("Error: no solution found");
                return "";
            }
            threshold = distance;
        }
    }

    private static int search(cubeState node, int g, int threshold){
        int h = getHeuristic1(node);
        int f = g + h;
        if(f>threshold){
            return f;
        }

        if(h==0){
            boolean verified = Phase1Verifier.verify(node);
            if (!verified) System.out.println("WARNING: Solver claims success but state is invalid!");
            while(node.parent!=null){
                phase1sol.append(node.Move);
                node = node.parent;
            }
            return -1;
        }

        int min = Integer.MAX_VALUE;

        for(cubeState neighbour : node.getNeighbours()){
            int res = search(neighbour, g+1, threshold);
            if(res==-1){
                return -1;
            }
            if(res<min){
                min = res;
            }
        }
        return min;
    }

    public static int getHeuristic1(cubeState state){
        int CO = Indexer.getCornerOrientationIndex(state);
        int EO = Indexer.getEdgeOrientationIndex(state);
        int slice = Indexer.getSliceIndex(state);

        int edgedistance = eoSliceTable[EO][slice];
        int cornerdistance = coSliceTable[CO][slice];

        return Math.max(edgedistance, cornerdistance);
    }

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
        RubiksCube solved = new RubiksCube();
        cubeState root = new cubeState(solved);
        int[][] coMoves = MoveTable.CornerOrientationMoveTable(root);
        int[][] eoMoves = MoveTable.edgeOrientationMoveTable(root);
        int[][] sliceMoves = MoveTable.sliceMoveTable(root);

        coSliceTable = PruningTable.CornerOrientationSlicePruning(coMoves, sliceMoves);
        eoSliceTable = PruningTable.EdgeOrientationSlicePruning(eoMoves, sliceMoves);

        File input = new File(args[0]);
        try{
            for(int i = 0; i < args.length; i++){
                long startTime = System.nanoTime();
                System.out.println(i);
                RubiksCube cube = new RubiksCube(args[i]);
                cubeState start = new cubeState(cube);
//            System.out.println(cube.hashCode());
//            System.out.println(cube.toString());
//            System.out.println(cube.clone());
//            start.cube.applyMoves(Solve(start));
//            System.out.println(start.cube.toString());
                String solution = Solve1(start);
                System.out.println(solution);
                start.cube.applyMoves(solution);
                System.out.println(start.cube.toString());
                long endTime = System.nanoTime();
                long totalTimeNanos = endTime - startTime;
                double totalTimeMillis = (double) totalTimeNanos / 1_000_000.0;
                System.out.println("Execution time in milliseconds: " + totalTimeMillis);
            }
        }
        catch (IOException e){
            System.out.println("Could not open file");
        }
        catch(IncorrectFormatException e){
            System.out.println("Wrong format");
        }

    }
}
