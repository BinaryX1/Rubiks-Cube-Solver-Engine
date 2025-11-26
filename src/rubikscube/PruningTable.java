package rubikscube;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PruningTable {
    static byte[][] CornerOrientationSlicePruning(int[][] COMoveTable, int[][] sliceMoveTable){
        byte[][] pruningtable = new byte[2187][495];
        for(byte[] row : pruningtable){
            Arrays.fill(row, (byte) -1);
        }
        Queue<Integer> queue = new LinkedList<>();

        int startCO = 0;
        int startSliceIndex = 0;

        pruningtable[startCO][startSliceIndex] = 0; //values in table are distance from solved

        int startCombined = (startCO * 495) + startSliceIndex;
        queue.add(startCombined);
        while(!queue.isEmpty()){
            int currentCombined = queue.poll();

            int currentCO = currentCombined/495;
            int currentSliceIndex = currentCombined%495;
            int currentDistance = pruningtable[currentCO][currentSliceIndex];

            for(int i = 0; i < 18; i++){
                int nextCO = COMoveTable[currentCO][i];
                int nextSliceIndex = sliceMoveTable[currentSliceIndex][i];
                if(pruningtable[nextCO][nextSliceIndex] == -1){
                    pruningtable[nextCO][nextSliceIndex] = (byte) (currentDistance + 1);

                    int nextCombined = (nextCO * 495) + nextSliceIndex;
                    queue.add(nextCombined);
                }
            }
        }
        return pruningtable;
    }

    static byte[][] EdgeOrientationSlicePruning(int[][] EOMoveTable, int[][] sliceMoveTable){
        byte[][] pruningtable = new byte[2048][495];
        for(byte[] row : pruningtable){
            Arrays.fill(row, (byte) -1);
        }
        Queue<Integer> queue = new LinkedList<>();

        int startEO = 0;
        int startSliceIndex = 0;

        pruningtable[startEO][startSliceIndex] = 0;

        int startCombined = (startEO * 495) + startSliceIndex;
        queue.add(startCombined);
        while(!queue.isEmpty()){
            int currentCombined = queue.poll();
            int currentEO = currentCombined/495;
            int currentSliceIndex = currentCombined%495;
            int currentDistance = pruningtable[currentEO][currentSliceIndex];

            for(int i = 0; i < 18; i++){
                int nextEO = EOMoveTable[currentEO][i];
                int nextSliceIndex = sliceMoveTable[currentSliceIndex][i];

                if(pruningtable[nextEO][nextSliceIndex] == -1){
                    pruningtable[nextEO][nextSliceIndex] = (byte) (currentDistance + 1);

                    int nextCombined = (nextEO * 495) + nextSliceIndex;
                    queue.add(nextCombined);
                }
            }
        }
        return pruningtable;
    }

    static byte[][] CornerPermutationSlicePruning(int[][] CPMoveTable, int[][] sliceMoveTable){
        byte[][] pruningtable = new byte[40320][24];
        for(byte[] row : pruningtable){
            Arrays.fill(row, (byte) -1);
        }
        Queue<Integer> queue = new LinkedList<>();

        int startCP = 0;
        int startSliceIndex = 0;
        pruningtable[startCP][startSliceIndex] = 0;

        int startCombined = (startCP * 24) + startSliceIndex;
        queue.add(startCombined);
        while(!queue.isEmpty()){
            int currentCombined = queue.poll();
            int currentCP = currentCombined/24;
            int currentSliceIndex = currentCombined%24;
            int currentDistance = pruningtable[currentCP][currentSliceIndex];

            for(int i = 0; i < 10; i++){
                int nextCP = CPMoveTable[currentCP][i];
                int nextSliceIndex = sliceMoveTable[currentSliceIndex][i];


                if(pruningtable[nextCP][nextSliceIndex] == -1){
                    pruningtable[nextCP][nextSliceIndex] = (byte) (currentDistance + 1);

                    int nextCombined = (nextCP * 24) + nextSliceIndex;
                    queue.add(nextCombined);
                }
            }
        }
        return pruningtable;
    }

    static byte[][] EdgePermutationSlicePruning(int[][] EPMoveTable, int[][] sliceMoveTable){
        byte[][] pruningtable = new byte[40320][24];
        for(byte[] row : pruningtable){
            Arrays.fill(row, (byte) -1);
        }
        Queue<Integer> queue = new LinkedList<>();

        int startEP = 0;
        int startSliceIndex = 0;
        pruningtable[startEP][startSliceIndex] = 0;

        int startCombined = (startEP * 24) + startSliceIndex;
        queue.add(startCombined);
        while(!queue.isEmpty()){
            int currentCombined = queue.poll();
            int currentEP = currentCombined/24;
            int currentSliceIndex = currentCombined%24;
            int currentDistance = pruningtable[currentEP][currentSliceIndex];

            for(int i = 0; i < 10; i++){
                int nextEP = EPMoveTable[currentEP][i];
                int nextSliceIndex = sliceMoveTable[currentSliceIndex][i];


                if(pruningtable[nextEP][nextSliceIndex] == -1){
                    pruningtable[nextEP][nextSliceIndex] = (byte) (currentDistance + 1);

                    int nextCombined = (nextEP * 24) + nextSliceIndex;
                    queue.add(nextCombined);
                }
            }
        }
        return pruningtable;
    }

    public static void main(String[] args){
        long startTime = System.nanoTime();
        RubiksCube cube = new RubiksCube();
        cubeState state = new cubeState(cube);
//        byte[][] table = CornerOrientationSlicePruning(MoveTable.CornerOrientationMoveTable(state), MoveTable.sliceMoveTable(state));
//        boolean complete = true;
//        for(int i=0; i<2187; i++) {
//            for(int j=0; j<495; j++) {
//                if(table[i][j] == -1) {
//                    System.out.println("Error: Index " + i + " has no result for move " + j);
//                    complete = false;
//                }
//            }
//        }
        byte[][] table = EdgePermutationSlicePruning(MoveTable.edgePermutationTable(state), MoveTable.sliceEdgePermutationTable(state));
        boolean complete = true;
        for(int i=0; i<40320; i++) {
            for(int j=0; j<24; j++) {
                if(table[i][j] == -1) {
                    System.out.println("Error: Index " + i + " has no result for move " + j);
                    complete = false;
                }
            }
        }
        if(complete) System.out.println("Test 1 Passed: Table is full.");
        System.out.println(state.cube.toString());
        System.out.println((System.nanoTime() - startTime)/1_000_000.0);

//
//        byte[][] table2 = EdgeOrientationSlicePruning(MoveTable.edgeOrientationMoveTable(state), MoveTable.sliceMoveTable(state));
//        for(int i=0; i<2048; i++) {
//            for(int j=0; j<495; j++) {
//                if(table2[i][j] == -1) {
//                    System.out.println("Error: Index " + i + " has no result for move " + j);
//                    complete = false;
//                }
//            }
//        }
//        if(complete) System.out.println("Test 2 Passed: Table is full.");
//        System.out.println(state.cube.toString());
//        System.out.println((System.nanoTime() - startTime)/1_000_000.0);


    }
}
