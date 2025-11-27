package rubikscube;

//Tests for movetables

import java.util.Arrays;
public class TableTest {

    public static void main(String[] args) {

//// Count flips

//// If flips is Even (0, 2, 4), you are stuck on one island.
//// If flips is Odd (1, 3), you can reach all 2048 states.

        System.out.println("--- DEBUGGING F MOVE FLIPS ---");
        RubiksCube cube = new RubiksCube();
        cubeState start = new cubeState(cube);

        cube.applyMoves("F");
        cubeState next = new cubeState(cube);

        int flipCount = 0;
        for(int i = 0; i < 11; i++) {
            if(start.edges[i].orientation != next.edges[i].orientation) {
                System.out.println("Edge " + i + " (" + start.edges[i].originaledge + ") FLIPPED");
                flipCount++;
            }
        }
        System.out.println("Total Tracked Flips: " + flipCount);
        System.out.println("--- Testing Edge Orientation Table ---");

        RubiksCube solved = new RubiksCube();
        cubeState root = new cubeState(solved);

        int[][] eoTable = MoveTable.edgeOrientationMoveTable(root);

        testTable(eoTable, 2048, "Edge Orientation");
        testEOBehavior(eoTable);

        System.out.println("\n--- Testing Slice Table ---");
        int[][] sliceTable = MoveTable.sliceMoveTable(root);
        testTable(sliceTable, 495, "Slice");
        testSliceBehavior(sliceTable);
    }

    /**
     * Generic test for any move table
     */
    public static void testTable(int[][] table, int expectedSize, String name) {
        // 1. Check Size
        if (table.length != expectedSize) {
            System.out.println("FAILED: " + name + " table has wrong size. Expected " + expectedSize + " but got " + table.length);
            return;
        }

        System.out.println("PASSED: " + name + " table structure initialized.");


        boolean hasDeadEnds = false;
        for(int i = 1; i < expectedSize; i++) {
            boolean allZero = true;
            for(int m = 0; m < 18; m++) {
                if(table[i][m] != 0) {
                    allZero = false;
                    break;
                }
            }
            if(allZero) {

                System.out.println("WARNING: Index " + i + " appears to be empty/unreachable (All transitions -> 0).");
                hasDeadEnds = true;
            }
        }
        if(!hasDeadEnds) {
            System.out.println("PASSED: " + name + " table appears fully populated.");
        }


        boolean reversible = true;
        for (int i = 0; i < expectedSize; i++) {
            int stateAfterF = table[i][0];
            int stateAfterF_Prime = table[stateAfterF][12];

            if (stateAfterF_Prime != i) {
                System.out.println("FAILED: Reversibility check on index " + i);
                System.out.println("  Start: " + i + " -> F -> " + stateAfterF + " -> F' -> " + stateAfterF_Prime);
                reversible = false;
                break;
            }
        }
        if (reversible) System.out.println("PASSED: " + name + " Reversibility Check (F + F').");
    }

    /**
     * Specific Logic Check for Edge Orientation
     */
    public static void testEOBehavior(int[][] table) {

        int start = 0;

        int afterU = table[start][4];
        if (afterU == 0) {
            System.out.println("PASSED: Move U preserves solved EO.");
        } else {
            System.out.println("WARNING: Move U changed EO state.");
        }

        int afterF = table[start][0];
        if (afterF != 0) {
            System.out.println("PASSED: Move F changes EO state (as expected).");
        } else {
            System.out.println("FAILED: Move F did not change EO state.");
        }
    }

    /**
     * Specific Logic Check for Slice
     */
    public static void testSliceBehavior(int[][] table) {

        int start = 0;

        int[] phase2Moves = {4, 5, 6, 7, 8, 9, 10, 11, 16, 17};

        boolean passed = true;
        for (int move : phase2Moves) {
            if (table[start][move] != 0) {
                System.out.println("FAILED: Phase 2 move index " + move + " broke the Slice alignment!");
                passed = false;
            }
        }

        if (passed) {
            System.out.println("PASSED: Phase 2 moves (U, D, F2...) preserve Slice State 0.");
        }

        if (table[start][0] != 0) {
            System.out.println("PASSED: Move F breaks Slice alignment (as expected).");
        } else {
            System.out.println("FAILED: Move F did not break Slice alignment.");
        }
    }
}
