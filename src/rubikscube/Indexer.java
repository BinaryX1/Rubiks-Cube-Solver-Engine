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
        int k = 3;
        int n = 11;
        boolean[] occupied = new boolean[12];


        for (int i = 0; i < 12; i++) {
            occupied[i] = false;
            cubeState.edgecubie e = state.edges[i];

            String edgeID = e.originaledge;
            if (Objects.equals(edgeID, "LB") || Objects.equals(edgeID, "RB") || Objects.equals(edgeID, "LF") || Objects.equals(edgeID, "RF")) {
                occupied[i] = true;
            }
        }

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

    private static int nametoIDcorner(String corner){
        if(corner.equals("URF")){
            return 0;
        }
        else if(corner.equals("UFL")){
            return 1;
        }
        else if(corner.equals("ULB")){
            return 2;
        }
        else if(corner.equals("UBR")){
            return 3;
        }
        else if(corner.equals("DFR")){
            return 4;
        }
        else if(corner.equals("DLF")){
            return 5;
        }
        else if(corner.equals("DBL")){
            return 6;
        }
        else if(corner.equals("DRB")){
            return 7;
        }
        else{
            System.out.println("Invalid corner provided to nametoIdcorner converter");
            return -2;
        }
    }

    public static int getCornerPermutationIndex(cubeState state){
        int index = 0;
        for(int i = 7; i >= 1; i--){
            int higherCount = 0;
            for(int j = i-1; j >= 0; j--){
                if(nametoIDcorner(state.corners[j].originalcorner) > nametoIDcorner(state.corners[i].originalcorner) ){
                    higherCount++;
                }
            }
            index = (index+ higherCount)*i;
        }
        return index;
    }

    private static int nametoIDedge(String edge){
        switch(edge){
            case "UL": return 0;
            case "UR": return 1;
            case "UB": return 2;
            case "UF": return 3;
            case "DL": return 4;
            case "DR": return 5;
            case "DB": return 6;
            case "DF": return 7;
            case "LB": return 8;
            case "LF": return 9;
            case "RB": return 10;
            case "RF": return 11;
            default:
                System.out.println("Invalid edge provided to nametoIDedge converter");
                return -2;
        }
    }

    public static int getEdgePermutationIndex(cubeState state){
        int index = 0;
        for(int i = 7; i >= 1; i--){
            int higherCount = 0;
            for(int j = i-1; j >= 0; j--){
                if(nametoIDedge(state.edges[j].originaledge) > nametoIDedge(state.edges[i].originaledge)){
                    higherCount++;
                }
            }
            index = (index + higherCount) * i;
        }
        return index;
    }

    public static int getSlicePermutationIndex(cubeState state){
        int index = 0;
        for(int i = 11; i >= 9; i--){
            int higherCount = 0;
            for(int j = i-1; j >= 8; j--){
                if(nametoIDedge(state.edges[j].originaledge) > nametoIDedge(state.edges[i].originaledge)){
                    higherCount++;
                }
            }
            int base = i-8; //Bring factorial down to 1-4
            index = (index + higherCount) * base;
        }
        return index;
    }


}
