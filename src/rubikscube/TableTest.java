package rubikscube;

import java.util.Arrays;
public class TableTest {

    public static void main(String[] args) {
        System.out.println("--- Testing Edge Orientation Table ---");
        // Create a dummy state to start generation
        RubiksCube solved = new RubiksCube();
        cubeState root = new cubeState(solved);

        // Generate the table - Assuming TableManager has these static methods
        // If these methods are in a different class, adjust the call accordingly.
        // For the purpose of this standalone test, I'm assuming you might put the generation
        // methods in TableManager or here. I will use TableManager as per previous context.
        // If you haven't created TableManager yet, you can just call your static methods directly
        // if they are in the same package or class.

        // Example: int[][] eoTable = Solver.edgeOrientationMoveTable(root);
        // Adjust the class name 'TableManager' to wherever you put your move table generation code.
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

        // 2. Check Reachability (Did we fill every row?)
        // A simple check is to ensure no row is completely empty/default (all 0s)
        // except potentially for state 0 if no moves change it (which is unlikely for all moves).
        // Better yet, check if there are any rows where ALL entries are 0.
        // Since index 0 is a valid state, 0 is a valid entry.
        // A truly unreached state might have default values.
        // However, your generation logic ensures reachability by BFS.
        // We can check for "Dead Ends" - states that transition to 0 for EVERY move,
        // which implies they weren't filled correctly (unless state 0 is a sink, which it isn't).

        boolean hasDeadEnds = false;
        for(int i = 1; i < expectedSize; i++) { // Skip 0 as it might validly transition to itself or others
            boolean allZero = true;
            for(int m = 0; m < 18; m++) {
                if(table[i][m] != 0) {
                    allZero = false;
                    break;
                }
            }
            if(allZero) {
                // It's highly unlikely a state > 0 transitions to 0 for ALL 18 moves.
                // This suggests the row wasn't populated.
                System.out.println("WARNING: Index " + i + " appears to be empty/unreachable (All transitions -> 0).");
                hasDeadEnds = true;
            }
        }
        if(!hasDeadEnds) {
            System.out.println("PASSED: " + name + " table appears fully populated.");
        }

        // 3. Reversibility Check (F then F')
        // Assuming:
        // Move 0 = F, Move 12 = F' (FFF)
        // Move 2 = R, Move 14 = R' (RRR)
        // Adjust indices if your move array is different!
        // Moves: F, B, R, L, U, D, FF, BB, RR, LL, UU, DD, FFF, BBB, RRR, LLL, UUU, DDD
        // F=0, F'=12. R=2, R'=14.

        boolean reversible = true;
        for (int i = 0; i < expectedSize; i++) {
            int stateAfterF = table[i][0]; // F
            int stateAfterF_Prime = table[stateAfterF][12]; // F'

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
        // Rule: moves U (4) and D (5) should NOT flip edges on a standard cube definition.
        // So table[0][4] should be 0.
        // Rule: move F (0) usually flips edges. table[0][0] should NOT be 0.

        int start = 0; // Solved EO

        // Check Move U (Index 4)
        int afterU = table[start][4];
        if (afterU == 0) {
            System.out.println("PASSED: Move U preserves solved EO.");
        } else {
            System.out.println("WARNING: Move U changed EO state. (Check your EO definition, this might be valid but unusual)");
        }

        // Check Move F (Index 0)
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
        // Rule: The Slice Coordinate tracks if FR, FL, BL, BR are in the middle layer.
        // Index 0 = All slice edges are in the middle layer.

        // Phase 2 Moves: U, D, F2, B2, R2, L2
        // These moves should NEVER take the cube out of Slice State 0.

        int start = 0; // Solved Slice

        // Indices based on your moves array:
        // Moves: F, B, R, L, U, D, FF, BB, RR, LL, UU, DD, FFF, BBB, RRR, LLL, UUU, DDD
        // U=4, D=5
        // F2=6, B2=7, R2=8, L2=9
        int[] phase2Moves = {4, 5, 6, 7, 8, 9, 10, 11, 16, 17}; // U, D, F2, B2, R2, L2, U2, D2, U', D'

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

        // Check that F (0) breaks it
        if (table[start][0] != 0) {
            System.out.println("PASSED: Move F breaks Slice alignment (as expected).");
        } else {
            System.out.println("FAILED: Move F did not break Slice alignment.");
        }
    }
}
