package rubikscube;

public class Phase1Verifier {

    public static void main(String[] args) {
        // 1. Create a solved cube (This is definitely Phase 1 Solved)
        RubiksCube solved = new RubiksCube();
        cubeState start = new cubeState(solved);

        System.out.println("Checking Solved Cube...");
        verify(start);

        // 2. Create a scrambled cube that is NOT Phase 1 Solved
        RubiksCube scrambled = new RubiksCube();
        scrambled.applyMoves("R F"); // F breaks edge orientation and slice
        cubeState messy = new cubeState(scrambled);

        System.out.println("\nChecking Scrambled Cube (R F)...");
        verify(messy);

        // 3. Create a Phase 1 Solved cube (but not fully solved)
        // Moves like R2, L2, U, D, F2, B2 preserve Phase 1
        RubiksCube g1 = new RubiksCube();
        g1.applyMoves("RRUFFDBBLL");
        cubeState phase1State = new cubeState(g1);

        System.out.println("\nChecking Valid G1 Cube (R2 U F2 D B2 L2)...");
        verify(phase1State);
    }

    public static boolean verify(cubeState state) {
        boolean isCO = checkCornerOrientation(state);
        boolean isEO = checkEdgeOrientation(state);
        boolean isSlice = checkSliceEdges(state);

        if (isCO && isEO && isSlice) {
            System.out.println("✅ VALID Phase 1 State (G1)");
            return true;
        } else {
            System.out.println("❌ INVALID Phase 1 State");
            if (!isCO) System.out.println("   - Corner Orientation Failed");
            if (!isEO) System.out.println("   - Edge Orientation Failed");
            if (!isSlice) System.out.println("   - Slice Edges Failed");
            return false;
        }
    }

    private static boolean checkCornerOrientation(cubeState state) {
        // All corners must be 0
        for (cubeState.cornercubie c : state.corners) {
            if (c.orientation != 0) return false;
        }
        return true;
    }

    private static boolean checkEdgeOrientation(cubeState state) {
        // All edges must be 0
        for (cubeState.edgecubie e : state.edges) {
            if (e.orientation != 0) return false;
        }
        return true;
    }

    private static boolean checkSliceEdges(cubeState state) {
        // The 4 slice edges (LB, LF, RB, RF) must be in the slice slots (Indices 8, 9, 10, 11)
        // OR simply: The edges currently residing in slots 8, 9, 10, 11 MUST be slice edges.

        // Your slice edge indices (based on previous code): 8, 9, 10, 11
        // Slice edge names: "LB", "LF", "RB", "RF"

        for (int i = 8; i <= 11; i++) {
            String edgeName = state.edges[i].originaledge;
            boolean isSliceEdge = edgeName.equals("LB") ||
                    edgeName.equals("LF") ||
                    edgeName.equals("RB") ||
                    edgeName.equals("RF");

            if (!isSliceEdge) return false;
        }
        return true;
    }
}