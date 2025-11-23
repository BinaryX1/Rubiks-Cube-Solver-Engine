package rubikscube;

import java.util.*;

public class cubeState implements Comparable<cubeState>{
    class edgecubie{
        char a, b;
        public String originaledge;
        public String trueedge;
        int orientation;
        public edgecubie(int index1, int index2, String edge){
            char[] faces = cube.getArr();
            originaledge = edge;
            a = faces[index1];
            b = faces[index2];
            trueedge = identifyEdge(a, b);
            orientation = orientEdge(index1, index2);
        }

        private int orientEdge(int index1, int index2){
            char[] faces = cube.getArr();
            int[] indices = {index1, index2};
            for(int i : indices){
                if(faces[i] == 'O'){
                    if(i <= 8){
                        return 0;
                    }
                    else{
                        return 1;
                    }
                }
                else if(faces[i] == 'R'){
                    if(i >= 45){
                        return 0;
                    }
                    else{
                        return 1;
                    }
                }
                else if(faces[i] == 'W'){
                    if ((i >= 12 && i<= 14) || (i >= 24 && i <= 26) || (i >= 36 && i <= 38)){
                        return 0;
                    }
                    else{
                        return 1;
                    }
                }
                else if(faces[i] == 'Y'){
                    if ((i >= 18 && i <= 20) || (i >= 30 && i <= 32) || (i >= 42 && i <=44)){
                        return 0;
                    }
                    else{
                        return 1;
                    }
                }
                continue;
            }
            System.out.println("Error: incorrect edge");
            return 1;
        }

        private String identifyEdge(char a, char b){
            Set<Character> s = Set.of(a, b);

            if (s.equals(Set.of('O', 'Y'))) return "UB";
            if (s.equals(Set.of('O', 'G'))) return "UL";
            if (s.equals(Set.of('O', 'B'))) return "UR";
            if (s.equals(Set.of('O', 'W'))) return "UF";

            if (s.equals(Set.of('G', 'Y'))) return "LB";
            if (s.equals(Set.of('B', 'Y'))) return "RB";
            if (s.equals(Set.of('G', 'W'))) return "LF";
            if (s.equals(Set.of('B', 'W'))) return "RF";

            if (s.equals(Set.of('R', 'Y'))) return "DB";
            if (s.equals(Set.of('R', 'G'))) return "DL";
            if (s.equals(Set.of('R', 'B'))) return "DR";
            if (s.equals(Set.of('R', 'W'))) return "DF";

            System.out.println("CRASH! Found impossible edge: " + s);
            throw new IllegalStateException("Unknown edge");
        }
    }
    class cornercubie {
        char a, b, c;
        public String originalcorner;
        int orientation;
        public String truecorner;
        public cornercubie(int index1, int index2, int index3, String type){
            char[] faces = cube.getArr();
            a = faces[index1];
            b = faces[index2];
            c = faces[index3];
            originalcorner = type;
            truecorner = identifyCubie(a, b, c);
            orientation = orientcorner(index1, index2, index3);
        }

        private int orientcorner(int index1, int index2, int index3){
            char[] faces = cube.getArr();
            int[] indices = {index1, index2, index3};
            for(int i : indices) {
                char s = faces[i];
                if(s == 'O' || s == 'R'){
                    if(i < 9){
                        return 0;
                    }
                    else if(i >= 45){
                        return 0;
                    }
                    else if((i >= 9 && i <= 11) || (i>=21 && i<=23) || (i>=33 && i <= 35)){ //On left face (G)
                        return 1;
                    }
                    else if((i >= 15 && i<= 17) || (i>=27 && i<=29) || (i>=39 && i<= 41)){ //On right face (B)
                        return 1;
                    }
                    else if ((i >= 12 && i<= 14) || (i >= 24 && i <= 26) || (i >= 36 && i <= 38)) //On front face (W)
                        return 2;
                    else //On back face (Y)
                        return 2;
                }
            }
            return 0;
        }

        private String identifyCubie(char a, char b, char c) {
            // convert to set for comparison
            Set<Character> s = Set.of(a, b, c);

            if (s.equals(Set.of('O','B','W'))) return "URF";
            if (s.equals(Set.of('O','W','G'))) return "UFL";
            if (s.equals(Set.of('O','G','Y'))) return "ULB";
            if (s.equals(Set.of('O','Y','B'))) return "UBR";

            if (s.equals(Set.of('R','W','B'))) return "DFR";
            if (s.equals(Set.of('R','G','W'))) return "DLF";
            if (s.equals(Set.of('R','Y','G'))) return "DBL";
            if (s.equals(Set.of('R','B','Y'))) return "DRB";
            System.out.println("CRASH! Found impossible corner with colors: " + s + originalcorner);
            System.out.println(cube.toString());
            throw new IllegalStateException("Unknown corner");
        }
    }
    public static char[] solved = new RubiksCube().getArr();
    RubiksCube cube;
    cubeState parent;
    public int heuristic;
    public int distance;
    String previousMoves;
    public int score;
    String Move;

    cornercubie[] corners;
    edgecubie[] edges;
    cubeState(RubiksCube cube){
        this.cube = cube;
        this.parent = null;
        this.previousMoves = "";
        this.corners = new cornercubie[8];
        corners[0] = new cornercubie(8, 15, 14, "URF");
        corners[1] = new cornercubie(6, 11, 12, "ULF");
        corners[2] = new cornercubie(0, 9, 20, "ULB");
        corners[3] = new cornercubie(2, 17, 18, "URB");
        corners[4] = new cornercubie(47, 39, 38, "DRF");
        corners[5] = new cornercubie(45, 35, 36, "DLF");
        corners[6] = new cornercubie(51, 33, 44, "DLB");
        corners[7] = new cornercubie(53, 41, 42, "DRB");
        this.edges = new edgecubie[12];
        edges[0] = new edgecubie(3, 10, "UL");
        edges[1] = new edgecubie(5, 16, "UR");
        edges[2] = new edgecubie(1, 19, "UB");
        edges[3] = new edgecubie(7, 13, "UF");
        edges[4] = new edgecubie(21, 32, "LB");
        edges[5] = new edgecubie(23, 24, "LF");
        edges[6] = new edgecubie(29, 30, "RB");
        edges[7] = new edgecubie(27, 26, "RF");
        edges[8] = new edgecubie(48, 34, "DL");
        edges[9] = new edgecubie(50, 40, "DR");
        edges[10] = new edgecubie(52, 43, "DB");
        edges[11] = new edgecubie(46, 37, "DF");
        this.heuristic = CalculateHeuristic();
    }

    cubeState(RubiksCube cube, cubeState parent){
        this.cube = cube;
        this.parent = parent;
        this.heuristic = CalculateHeuristic();
        this.previousMoves = "";
    }

    cubeState(RubiksCube cube, cubeState parent, String move){
        this.cube = cube;
        this.parent = parent;
        this.previousMoves = "";
        this.Move = move;
        this.corners = new cornercubie[8];
        corners[0] = new cornercubie(8, 15, 14, "URF");
        corners[1] = new cornercubie(6, 11, 12, "ULF");
        corners[2] = new cornercubie(0, 9, 20, "ULB");
        corners[3] = new cornercubie(2, 17, 18, "URB");
        corners[4] = new cornercubie(47, 39, 38, "DRF");
        corners[5] = new cornercubie(45, 35, 36, "DLF");
        corners[6] = new cornercubie(51, 33, 44, "DLB");
        corners[7] = new cornercubie(53, 41, 42, "DRB");
        this.edges = new edgecubie[12];
        edges[0] = new edgecubie(3, 10, "UL");
        edges[1] = new edgecubie(5, 16, "UR");
        edges[2] = new edgecubie(1, 19, "UB");
        edges[3] = new edgecubie(7, 13, "UF");
        edges[4] = new edgecubie(21, 32, "LB");
        edges[5] = new edgecubie(23, 24, "LF");
        edges[6] = new edgecubie(29, 30, "RB");
        edges[7] = new edgecubie(27, 26, "RF");
        edges[8] = new edgecubie(48, 34, "DL");
        edges[9] = new edgecubie(50, 40, "DR");
        edges[10] = new edgecubie(52, 43, "DB");
        edges[11] = new edgecubie(46, 37, "DF");
        this.heuristic = CalculateHeuristic();
    }

    private int CalculateHeuristic(){
//        int score = 0;
//        char[] faces = cube.getArr();
//        for(int i = 0; i < faces.length; i++){
//            if(faces[i]!=solved[i]){
//                score++;
//            }
//        }
//        return score;
        int cornerscore = 0;
        int edgescore = 0;
        for(cornercubie c : corners){
            if(c.orientation!=0){
                cornerscore++;
            }
            if(!Objects.equals(c.truecorner, c.originalcorner)){
                cornerscore++;
            }
        }

//        edgescore += edgescore(3, 10);
//
//        edgescore += edgescore(1, 19);
//
//        edgescore += edgescore(5, 16);
//
//        edgescore += edgescore(23, 24);
//
//        edgescore += edgescore(26, 27);
//
//        edgescore += edgescore(37, 46);
//
//        edgescore += edgescore(7, 13);
//
//        edgescore += edgescore(34, 48);
//
//        edgescore += edgescore(50, 40);
//
//        edgescore += edgescore(52, 43);
//
//        edgescore += edgescore(21, 32);
//
//        edgescore += edgescore(29, 30);

        for(edgecubie e : edges){
            if(e.orientation!=0){
                edgescore++;
            }
            if(!Objects.equals(e.trueedge, e.originaledge)){
                edgescore++;
            }
        }
        int movesForCorners = (int) Math.ceil(cornerscore / 4.0);
        int movesForEdges = (int) Math.ceil(edgescore / 4.0);
        return movesForCorners + movesForEdges;
    }

    char faceOfIndex(int idx) {
        if (idx >= 0 && idx <= 8) return 'U';
        if (idx >= 9 && idx <= 35) {
            if ((idx >= 9 && idx <= 11) || (idx >= 21 && idx <= 23) || (idx >= 33 && idx <= 35)) return 'L';
            else if ((idx >= 12 && idx <= 14) || (idx >= 24 && idx <= 26) || (idx >= 36 && idx <= 38)) return 'F';
            else if ((idx >= 15 && idx <= 17) || (idx >= 27 && idx <= 29) || (idx >= 39 && idx <= 41)) return 'R';
            else if ((idx >= 18 && idx <= 20) || (idx >= 30 && idx <= 32) || (idx >= 42 && idx <= 44)) return 'B';
        }
        if (idx >= 45 && idx <= 53) return 'D';
        return '?';
    }

    private int edgescore(int index1, int index2){
        int score = 0;
        char[] faces = cube.getArr();
        Set<Character> solvedEdge = new HashSet<>();
        solvedEdge.add(solved[index1]);
        solvedEdge.add(solved[index2]);
        Set<Character> realEdge = new HashSet<>();
        realEdge.add(faces[index1]);
        realEdge.add(faces[index2]);
        if(!solvedEdge.equals(realEdge)){
            score+=1;
        }
        return score;
    }
    // orientation for one edge given the two sticker indices for that edge and the cube array.
// return 0 if oriented, 1 if flipped (using UD color rule with U='O', D='R')
    int edgeOrientation(int idxA, int idxB) {
        char[] faces = cube.getArr();
        char a = faces[idxA];
        char b = faces[idxB];
        // UD colors in your cube:
        char Ucolor = 'O';
        char Dcolor = 'R';

        // If one sticker is U or D color, use that sticker to decide orientation
        if (a == Ucolor || a == Dcolor) {
            char face = faceOfIndex(idxA);
            if (face == 'U' || face == 'D') return 0;
            else return 1;
        }
        if (b == Ucolor || b == Dcolor) {
            char face = faceOfIndex(idxB);
            if (face == 'U' || face == 'D') return 0;
            else return 1;
        }

        // Otherwise (no U/D color on this edge) — use the F/B convention:
        // find the sticker that belongs to F or B (your F color is 'W', B color is 'Y')
        // and check if it currently sits on F or B face.
        char Fcolor = 'W';
        char Bcolor = 'Y';
        if (a == Fcolor || a == Bcolor) {
            char face = faceOfIndex(idxA);
            if (face == 'F' || face == 'B') return 0;
            else return 1;
        }
        if (b == Fcolor || b == Bcolor) {
            char face = faceOfIndex(idxB);
            if (face == 'F' || face == 'B') return 0;
            else return 1;
        }

        // fallback (shouldn't happen on a valid cube)
        return 0;
    }

    public HashSet<cubeState> getNeighbours(){
        HashSet<cubeState> neighbours = new HashSet<cubeState>();
        String[] moves = new String[18];
        moves[0] = "F";
        moves[1] = "B";
        moves[2] = "R";
        moves[3] = "L";
        moves[4] = "U";
        moves[5] = "D";
        moves[6] = "FF";
        moves[7] = "BB";
        moves[8] = "RR";
        moves[9] = "LL";
        moves[10] = "UU";
        moves[11] = "DD";
        moves[12] = "FFF";
        moves[13] = "BBB";
        moves[14] = "RRR";
        moves[15] = "LLL";
        moves[16] = "UUU";
        moves[17] = "DDD";
        RubiksCube temp = new RubiksCube();
        for(String move : moves){
            if(parent!=null) {
                if (Objects.equals(move, parent.Move)) {
                    continue;
                }
            }
            temp.copyFrom(this.cube);
            temp.applyMoves(move);
            cubeState tempState = new cubeState(temp.clone(), this, move);
//            tempState.previousMoves = this.previousMoves + move;
            neighbours.add(tempState);
        }
        return neighbours;
    }

    @Override
    public int compareTo(cubeState o) {
        cubeState other = (cubeState)o;
        if(this.score > other.score){
            return 1;
        }
        else if(this.score == other.score){
            return 0;
        }
        else{
            return -1;
        }
    }
}
