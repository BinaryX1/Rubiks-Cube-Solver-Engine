package rubikscube;

import java.util.Objects;

public class Indexer {
    public static int getCornerOrientationIndex(cubeState state){
        int index = 0;
        for(int i = 0; i < 7; i++){
            index = (index*3) + state.corners[i].orientation;
        }
        return index;
    }

    public static int getEdgeOrientationIndex(cubeState state){
        int index = 0;
        for(int i = 0; i < 11; i++){
            index = (index*2) + state.edges[i].orientation;
        }
        return index;
    }

    public static int getSliceIndex(cubeState state) {
        int s = 0;
        int k = 3; // We are looking for 4 edges, so k starts at 3 (representing 4 items: 0,1,2,3)
        int n = 11; // 12 positions total (0-11), so n starts at 11
        boolean[] occupied = new boolean[12];

        // 1. Mark occupied positions
        // The Pascal code iterates from UR to BR (0 to 11).
        // It checks if PEdge^[ed].e >= FR.
        // In standard ordering: UR, UF, UL, UB, DR, DF, DL, DB, FR, FL, BL, BR
        // Indices: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
        // FR is index 8. So it checks if the edge index is >= 8.
        // This means it checks if the edge is one of the slice edges (FR, FL, BL, BR).
        // Note: This relies on your edgecubie.originaledge mapping to these specific indices correctly.

        for (int i = 0; i < 12; i++) {
            occupied[i] = false;
            cubeState.edgecubie e = state.edges[i];
            // Check if the edge AT position i is a Slice Edge
            // We check the identity (originaledge) of the piece.
            // Based on standard ordering (and your nameToIdEdge), Slice edges are indices 4, 5, 6, 7
            // Wait, the Pascal code assumes a specific ordering where Slice edges are >= FR (8,9,10,11).
            // Your ordering (from nameToIdEdge) seems to be:
            // UL, UR, UB, UF, LB, LF, RB, RF, DL, DR, DB, DF
            // Indices: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
            // Your Slice edges (LB, LF, RB, RF) are indices 4, 5, 6, 7.

            // So we check if the piece at slot i is one of our slice edges (4, 5, 6, 7)
            String edgeID = e.originaledge;
            if (Objects.equals(edgeID, "LB") || Objects.equals(edgeID, "RB") || Objects.equals(edgeID, "LF") || Objects.equals(edgeID, "RF")) {
                occupied[i] = true;
            }
        }

        // 2. Calculate Coordinate using Combinadics (Binomial Coefficients)
        // Loop from n=11 down to 0
        while (k >= 0) {
            if (occupied[n]) {
                k--;
            } else {
                s = s + binomial(n, k);
            }
            n--;
        }
        return s;
    }

    // Helper for n choose k (Combinations)
    private static int binomial(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k == 0 || k == n) return 1;
        if (k > n / 2) k = n - k;
        int res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (n - i + 1) / i;
        }
        return res;
    }


//    public static int getSliceIndex(cubeState state){
//        int index = 0;
//        int k = -1;
//        int n = 11;
//        for(int i = 0; i < 12; i++){
//            cubeState.edgecubie e = state.edges[i];
//            if(!Objects.equals(e.originaledge, "LB") && !Objects.equals(e.originaledge, "RB") && !Objects.equals(e.originaledge, "LF") && !Objects.equals(e.originaledge, "RF") && k!=-1){
//                index += binomial(i, k);
//            }
//            else{
//                k++;
//            }
//        }
//        return index;
//    }
//
//    private static int binomial(int n, int k) {
//        if (k < 0 || k > n) return 0;
//        if (k == 0 || k == n) return 1;
//        if (k > n / 2) k = n - k;
//        int res = 1;
//        for (int i = 1; i <= k; i++) {
//            res = res * (n - i + 1) / i;
//        }
//        return res;
//    }
}
