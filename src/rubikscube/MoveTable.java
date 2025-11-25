package rubikscube;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MoveTable {
    static int[][] CornerOrientationMoveTable(cubeState state){
        int[][] table = new int[2187][18];
        for(int[] row : table) {
            Arrays.fill(row, -1);
        }
        boolean[] visited = new boolean[2187];
        Queue<cubeState> queue = new LinkedList<>();
        queue.add(state);
        visited[Indexer.getCornerOrientationIndex(state)] = true;
        String[] moves = {
                "F", "B", "R", "L", "U", "D",
                "FF", "BB", "RR", "LL", "UU", "DD",
                "FFF", "BBB", "RRR", "LLL", "UUU", "DDD"
        };
        while(!queue.isEmpty()){
            cubeState current = queue.poll();
            int index = Indexer.getCornerOrientationIndex(current);
            for(int j = 0; j < 18; j++){
                RubiksCube nextCube = new RubiksCube();
                nextCube.copyFrom(current.cube);
                nextCube.applyMoves(moves[j]);

                cubeState cube = new cubeState(nextCube);
                int neighbourindex = Indexer.getCornerOrientationIndex(cube);
                table[index][j] = neighbourindex;

                if(!visited[neighbourindex]){
                    visited[neighbourindex] = true;
                    queue.add(cube);
                }
            }
        }
        return table;
    }

    static int[][] edgeOrientationMoveTable(cubeState state) {
        int[][] table = new int[2048][18];
        for (int[] row : table) {
            Arrays.fill(row, -1);
        }
        boolean[] visited = new boolean[2048];
        Queue<cubeState> queue = new LinkedList<>();
        queue.add(state);
        visited[Indexer.getEdgeOrientationIndex(state)] = true;
        String[] moves = {
                "F", "B", "R", "L", "U", "D",
                "FF", "BB", "RR", "LL", "UU", "DD",
                "FFF", "BBB", "RRR", "LLL", "UUU", "DDD"
        };
        while (!queue.isEmpty()) {
            cubeState current = queue.poll();
            int index = Indexer.getEdgeOrientationIndex(current);
            for (int j = 0; j < 18; j++) {
                RubiksCube nextCube = new RubiksCube();
                nextCube.copyFrom(current.cube);
                nextCube.applyMoves(moves[j]);

                cubeState cube = new cubeState(nextCube);
                int neighbourindex = Indexer.getEdgeOrientationIndex(cube);
                table[index][j] = neighbourindex;

                if (!visited[neighbourindex]) {
                    visited[neighbourindex] = true;
                    queue.add(cube);
                }
            }
        }
        return table;
    }

    static int[][] sliceMoveTable(cubeState state){
        int[][] table = new int[495][18];
        boolean[] visited = new boolean[495];
        for(int[] row : table) {
            Arrays.fill(row, -1);
        }
        Queue<cubeState> queue = new LinkedList<>();
        queue.add(state);
        visited[Indexer.getSliceIndex(state)] = true;
        String[] moves = {
                "F", "B", "R", "L", "U", "D",
                "FF", "BB", "RR", "LL", "UU", "DD",
                "FFF", "BBB", "RRR", "LLL", "UUU", "DDD"
        };
        while(!queue.isEmpty()){
            cubeState current = queue.poll();
            int index = Indexer.getSliceIndex(current);
            for(int j = 0; j < 18; j++){
                RubiksCube nextCube = new RubiksCube();
                nextCube.copyFrom(current.cube);
                nextCube.applyMoves(moves[j]);

                cubeState cube = new cubeState(nextCube);
                int neighbourindex = Indexer.getSliceIndex(cube);
                table[index][j] = neighbourindex;
                if(!visited[neighbourindex]){
                    visited[neighbourindex] = true;
                    queue.add(cube);
                }
            }
        }
        return table;
    }

    public static void main(String[] args){
        RubiksCube cube = new RubiksCube();
        cubeState state = new cubeState(cube);
        int[][] table = CornerOrientationMoveTable(state);
        boolean complete = true;
        for(int i=0; i<2187; i++) {
            for(int j=0; j<18; j++) {
                if(table[i][j] == -1) {
                    System.out.println("Error: Index " + i + " has no result for move " + j);
                    complete = false;
                }
            }
        }
        if(complete) System.out.println("Test 1 Passed: Table is full.");
        System.out.println(state.cube.toString());

        table = edgeOrientationMoveTable(state);

        for(int i=0; i<2048; i++) {
            for(int j=0; j<18; j++) {
                if(table[i][j] == -1) {
                    System.out.println("Error: Index " + i + " has no result for move " + j);
                    complete = false;
                }
            }
        }
        if(complete)System.out.println(("Edge table test passed"));
        int start = 0; // Solved CO
        int moveF = 0; // Index of "F" in your array
        int moveF_Prime = 12; // Index of "FFF" (F') in your array

        int afterF = table[start][moveF];
        int backHome = table[afterF][moveF_Prime];

        if(backHome == start) {
            System.out.println("Test 2 Passed: F then F' returns to start.");
        } else {
            System.out.println("Test 2 Failed: F then F' went to " + backHome);
        }
    }
}
