package rubikscube;

import java.util.*;

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

    public static int[][] cornerPermutationMoveTable(cubeState state){
        int[][] table = new int[40320][10];
        for(int[] row : table){
            Arrays.fill(row, -1);
        }
        boolean[] visited = new boolean[40320];
        Queue<cubeState> queue = new LinkedList<>();
        queue.add(state);
        visited[Indexer.getCornerPermutationIndex(state)] = true;
//        int[] phase2Moves = {4, 5, 6, 7, 8, 9, 10, 11, 16, 17};
        String[] phase2moves = {
                "U", "D", "FF", "BB", "RR", "LL", "UU", "DD", "UUU", "DDD"
        };
        while(!queue.isEmpty()){
            cubeState current = queue.poll();
            int index = Indexer.getCornerPermutationIndex(current);
            for(int m = 0; m < 10; m++){
                RubiksCube nextCube = new RubiksCube();
                nextCube.copyFrom(current.cube);
                nextCube.applyMoves(phase2moves[m]);

                cubeState cube = new cubeState(nextCube);
                int neighbourindex = Indexer.getCornerPermutationIndex(cube);

                table[index][m] = neighbourindex;
                if(!visited[neighbourindex]){
                    visited[neighbourindex] = true;
                    queue.add(cube);
                }
            }
        }
        return table;
    }

    public static int[][] edgePermutationTable(cubeState state){
        int[][] table = new int[40320][18];
        for(int[] row : table){
            Arrays.fill(row, -1);
        }
        boolean[] visited = new boolean[40320];
        Queue<cubeState> queue = new LinkedList<>();
        queue.add(state);
        visited[Indexer.getEdgePermutationIndex(state)] = true;
//        int[] phase2Moves = {4, 5, 6, 7, 8, 9, 10, 11, 16, 17};
//        String[] moves = {
//                "F", "B", "R", "L", "U", "D",
//                "FF", "BB", "RR", "LL", "UU", "DD",
//                "FFF", "BBB", "RRR", "LLL", "UUU", "DDD"
//        };
        String[] phase2moves = {
                "U", "D", "FF", "BB", "RR", "LL", "UU", "DD", "UUU", "DDD"
        };
        while(!queue.isEmpty()){
            cubeState current = queue.poll();
            int index = Indexer.getEdgePermutationIndex(current);
            for(int m = 0; m < 10; m++){
                RubiksCube nextCube = new RubiksCube();
                nextCube.copyFrom(current.cube);
                nextCube.applyMoves(phase2moves[m]);

                cubeState cube = new cubeState(nextCube);
                int neighbourindex = Indexer.getEdgePermutationIndex(cube);

                table[index][m] = neighbourindex;
                if(!visited[neighbourindex]){
                    visited[neighbourindex] = true;
                    queue.add(cube);
                }
            }
        }
        return table;
    }

    public static int[][] sliceEdgePermutationTable(cubeState state){
        int[][] table = new int[24][10];
        for(int[] row : table){
            Arrays.fill(row, -1);
        }
        boolean[] visited = new boolean[24];
        Queue<cubeState> queue = new LinkedList<>();
        queue.add(state);
        visited[Indexer.getSlicePermutationIndex(state)] = true;
        int[] phase2Moves = {4, 5, 6, 7, 8, 9, 10, 11, 16, 17};
//        String[] moves = {
//                "F", "B", "R", "L", "U", "D",
//                "FF", "BB", "RR", "LL", "UU", "DD",
//                "FFF", "BBB", "RRR", "LLL", "UUU", "DDD"
//        };
        String[] phase2moves = {
                "U", "D", "FF", "BB", "RR", "LL", "UU", "DD", "UUU", "DDD"
        };
        while(!queue.isEmpty()){
            cubeState current = queue.poll();
            int index = Indexer.getSlicePermutationIndex(current);
            for(int i = 0; i < 10; i++){
                RubiksCube nextCube = new RubiksCube();
                nextCube.copyFrom(current.cube);
                nextCube.applyMoves(phase2moves[i]);

                cubeState cube = new cubeState(nextCube);
                int neighbourindex = Indexer.getSlicePermutationIndex(cube);

                table[index][i] = neighbourindex;
                if(!visited[neighbourindex]){
                    visited[neighbourindex] = true;
                    queue.add(cube);
                }
            }
        }
        return table;
    }


    public static void main(String[] args){
        long startTime = System.nanoTime();
        System.out.println("--- Testing Phase 2 Corner Permutation Table ---");

        // 1. Generate the Table
        RubiksCube solved = new RubiksCube();
        cubeState root = new cubeState(solved);

        System.out.println("Generating CP Table...");
        int[][] cpTable = MoveTable.cornerPermutationMoveTable(root);

        // 2. Verify Completeness
        // Valid Phase 2 Move Indices:
        // U(4), D(5), F2(6), B2(7), R2(8), L2(9), U2(10), D2(11), U'(16), D'(17)
        int[] validMoves = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        int totalStates = 40320; // 8!
        int filledRows = 0;
        boolean flawless = true;

        for (int i = 0; i < totalStates; i++) {
            boolean rowOk = true;
            for (int m : validMoves) {
                if (cpTable[i][m] == -1) {
                    rowOk = false;
                    flawless = false;
                    // System.out.println("Missing entry for State " + i + " at Move " + m);
                    // break; // Uncomment to spam console with errors
                }
            }
            if (rowOk) {
                filledRows++;
            }
        }

        System.out.println("Filled Rows: " + filledRows + " / " + totalStates);

        if (filledRows == totalStates) {
            System.out.println("PASSED: Corner Permutation graph is fully connected.");
        } else {
            System.out.println("FAILED: Some states were unreachable via Phase 2 moves.");
        }

//
//        System.out.println("\n--- Testing Phase 2 Edge Permutation Table ---");
//
//        // 1. Generate
//        RubiksCube solved = new RubiksCube();
//        cubeState root = new cubeState(solved);
//
//        // NOTE: Replace 'MoveTable' with the class name where you put the method
//        int[][] epTable = MoveTable.edgePermutationTable(root);
//
//        // 2. Verify Size
//        if (epTable.length != 40320) {
//            System.out.println("FAILED: Wrong table size. Expected 40320, got " + epTable.length);
//        } else {
//            System.out.println("PASSED: Table size is 40320.");
//        }
//
//        // 3. Verify Reachability & Constraints
//        // Phase 2 Allowed Moves: U, D, F2, B2, R2, L2, U2, D2, U', D'
//        // Indices: 4, 5, 6, 7, 8, 9, 10, 11, 16, 17
//        Set<Integer> allowedMoves = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11, 16, 17));
//
//        int reachedStates = 0;
//        boolean constraintsPassed = true;
//
//        for (int i = 0; i < 40320; i++) {
//            boolean stateReachable = false;
//
//            for (int m = 0; m < 18; m++) {
//                int nextState = epTable[i][m];
//
//                if (allowedMoves.contains(m)) {
//                    // This IS an allowed move. It MUST have a value != -1
//                    if (nextState == -1) {
//                        // This means the graph is disconnected or incomplete
//                        stateReachable = false;
//                        break;
//                    } else {
//                        stateReachable = true;
//                    }
//                } else {
//                    // This is a FORBIDDEN move (e.g., F, R). It MUST be -1.
//                    if (nextState != -1) {
//                        System.out.println("FAILED: Table contains forbidden move index " + m + " at state " + i);
//                        constraintsPassed = false;
//                    }
//                }
//            }
//
//            if (stateReachable) {
//                reachedStates++;
//            }
//        }
//
//        System.out.println("Total Reachable States: " + reachedStates + " / 40320");
//
//        if (reachedStates == 40320 && constraintsPassed) {
//            System.out.println("✅ PASSED: Phase 2 Edge Permutation Graph is fully connected and valid.");
//        } else {
//            System.out.println("❌ FAILED: Table is incomplete or invalid.");
//        }


//        System.out.println("\n--- Testing Phase 2 Slice Permutation Table ---");
//
//        // 1. Generate
//        RubiksCube solved = new RubiksCube();
//        cubeState root = new cubeState(solved);
//
//        // NOTE: Replace 'MoveTable' with your class name
//        int[][] spTable = MoveTable.sliceEdgePermutationTable(root);
//
//        // 2. Verify Size
//        if (spTable.length != 24) {
//            System.out.println("FAILED: Wrong table size. Expected 24, got " + spTable.length);
//        } else {
//            System.out.println("PASSED: Table size is 24.");
//        }

        // 3. Verify Reachability & Logic
        // Moves: U, D, F2, B2, R2, L2, U2, D2, U', D'
        // Indices: 4, 5, 6, 7, 8, 9, 10, 11, 16, 17
//
//        int reachedStates = 0;
//        boolean constraintsPassed = true;
//        boolean udLogicPassed = true;
//
//        for (int i = 0; i < 24; i++) {
//            boolean rowOk = true;
//
//            // Check U/D moves (Should be Identity)
//            int[] udMoves = {4, 5, 10, 11, 16, 17};
//            for (int m : udMoves) {
//                if (spTable[i][m] != i) {
//                    // System.out.println("FAILED: Move " + m + " changed slice permutation! (Should be identity)");
//                    udLogicPassed = false;
//                }
//            }
//
//            // Check F2/B2/R2/L2 (Should allow changes)
//            int[] sliceMoves = {6, 7, 8, 9};
//            for (int m : sliceMoves) {
//                if (spTable[i][m] == -1) {
//                    rowOk = false;
//                }
//            }
//
//            if (rowOk) reachedStates++;
//        }
//
//        System.out.println("Total Reachable States: " + reachedStates + " / 24");
//
//        if (reachedStates == 24) {
//            System.out.println("PASSED: Slice Permutation Graph is fully connected.");
//        } else {
//            System.out.println("FAILED: Table is incomplete.");
//        }
//
//        if (udLogicPassed) {
//            System.out.println("PASSED: U/D moves preserve Slice Permutation (as expected).");
//        } else {
//            System.out.println("WARNING: U/D moves changed Slice Permutation (Check physics or indexer).");
//        }
        long endTime = System.nanoTime();
        long totalTimeNanos = endTime - startTime;
        double totalTimeMillis = (double) totalTimeNanos / 1_000_000.0;
        System.out.println("Execution time in milliseconds: " + totalTimeMillis);


//        RubiksCube cube = new RubiksCube();
//        cubeState state = new cubeState(cube);
//        int[][] table = CornerOrientationMoveTable(state);
//        boolean complete = true;
//        for(int i=0; i<2187; i++) {
//            for(int j=0; j<18; j++) {
//                if(table[i][j] == -1) {
//                    System.out.println("Error: Index " + i + " has no result for move " + j);
//                    complete = false;
//                }
//            }
//        }
//        if(complete) System.out.println("Test 1 Passed: Table is full.");
//        System.out.println(state.cube.toString());
//
//        table = edgeOrientationMoveTable(state);
//
//        for(int i=0; i<2048; i++) {
//            for(int j=0; j<18; j++) {
//                if(table[i][j] == -1) {
//                    System.out.println("Error: Index " + i + " has no result for move " + j);
//                    complete = false;
//                }
//            }
//        }
//        if(complete)System.out.println(("Edge table test passed"));
//        int start = 0; // Solved CO
//        int moveF = 0; // Index of "F" in your array
//        int moveF_Prime = 12; // Index of "FFF" (F') in your array
//
//        int afterF = table[start][moveF];
//        int backHome = table[afterF][moveF_Prime];
//
//        if(backHome == start) {
//            System.out.println("Test 2 Passed: F then F' returns to start.");
//        } else {
//            System.out.println("Test 2 Failed: F then F' went to " + backHome);
//        }
//        System.out.println(Indexer.getSliceIndex(state));

    }
}
