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

    public static int getSliceIndex(cubeState state){
        int index = 0;
        int k = -1;
        int n = 11;
        for(int i = 0; i < 12; i++){
            cubeState.edgecubie e = state.edges[i];
            if(!Objects.equals(e.originaledge, "LB") && !Objects.equals(e.originaledge, "RB") && !Objects.equals(e.originaledge, "LF") && !Objects.equals(e.originaledge, "RF") && k!=-1){
                index += binomial(i, k);
            }
            else{
                k++;
            }
        }
        return index;
    }

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
}
