package rubikscube;

public class Phase1Verifier {

    public static void main(String[] args) {
        RubiksCube solved = new RubiksCube();
        cubeState start = new cubeState(solved);

        System.out.println("Checking Solved Cube...");
        verify(start);

        RubiksCube scrambled = new RubiksCube();
        scrambled.applyMoves("R F");
        cubeState messy = new cubeState(scrambled);

        System.out.println("\nChecking Scrambled Cube (R F)...");
        verify(messy);

        RubiksCube g1 = new RubiksCube();
        g1.applyMoves("RRUFFDBBLLUUDDUUUDDD");
        cubeState phase1State = new cubeState(g1);

        System.out.println("\nChecking Valid G1 Cube (R2 U F2 D B2 L2)...");
        verify(phase1State);
    }

    public static boolean verify(cubeState state) {
        boolean isCO = checkCornerOrientation(state);
        boolean isEO = checkEdgeOrientation(state);
        boolean isSlice = checkSliceEdges(state);

        if (isCO && isEO && isSlice) {
            System.out.println("VALID Phase 1 State (G1)");
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

        for (cubeState.cornercubie c : state.corners) {
            if (c.orientation != 0) return false;
        }
        return true;
    }

    private static boolean checkEdgeOrientation(cubeState state) {

        for (cubeState.edgecubie e : state.edges) {
            if (e.orientation != 0) return false;
        }
        return true;
    }

    private static boolean checkSliceEdges(cubeState state) {

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