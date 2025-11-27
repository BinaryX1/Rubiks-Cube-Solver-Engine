package rubikscube;

import java.util.*;

public class cubeState implements Comparable<cubeState>{
    static final int[][] CORNER_DIST = {
            {0, 1, 1, 1, 1, 1, 2, 1}, // Row 0: URF
            {1, 0, 1, 1, 1, 1, 1, 2}, // Row 1: UFL
            {1, 1, 0, 1, 2, 1, 1, 1}, // Row 2: ULB
            {1, 1, 1, 0, 1, 2, 1, 1}, // Row 3: UBR
            {1, 1, 2, 1, 0, 1, 1, 1}, // Row 4: DFR
            {1, 1, 1, 2, 1, 0, 1, 1}, // Row 5: DLF
            {2, 1, 1, 1, 1, 1, 0, 1}, // Row 6: DBL
            {1, 2, 1, 1, 1, 1, 1, 0}, // Row 7: DRB
    };
    static final int[][] EDGE_DIST = {
            {0, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 2}, // Row 0: UL
            {1, 0, 1, 1, 2, 2, 1, 1, 2, 1, 2, 2}, // Row 1: UR
            {1, 1, 0, 1, 1, 2, 1, 2, 2, 2, 1, 2}, // Row 2: UB
            {1, 1, 1, 0, 2, 1, 2, 1, 2, 2, 2, 1}, // Row 3: UF
            {1, 2, 1, 2, 0, 1, 1, 2, 1, 2, 1, 2}, // Row 4: LB
            {1, 2, 2, 1, 1, 0, 2, 1, 1, 2, 2, 1}, // Row 5: LF
            {2, 1, 1, 2, 1, 2, 0, 1, 2, 1, 1, 2}, // Row 6: RB
            {2, 1, 2, 1, 2, 1, 1, 0, 2, 1, 2, 1}, // Row 7: RF
            {1, 2, 2, 2, 1, 1, 2, 2, 0, 1, 1, 1}, // Row 8: DL
            {2, 1, 2, 2, 2, 2, 1, 1, 1, 0, 1, 1}, // Row 9: DR
            {2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 0, 1}, // Row 10: DB
            {2, 2, 2, 1, 2, 1, 2, 1, 1, 1, 1, 0}, // Row 11: DF
    };
    class edgecubie{
        char a, b;
        public String originaledge;
        public String trueedge;
        int orientation;
        public edgecubie(int index1, int index2, String edge){
            char[] faces = cube.getArr();
//            originaledge = edge;
            trueedge = edge;
            a = faces[index1];
            b = faces[index2];
//            trueedge = identifyEdge(a, b);
            originaledge = identifyEdge(a, b);
            orientation = orientEdge(index1, index2);
        }

        private boolean inInspectionSpot(int i) {
            // "Look at the U/D faces" (Indices 0-8 and 45-53)
            if (i <= 8 || i >= 45){
                return true;
            }

            if (i == 24 || i == 26 || i == 30 || i == 32){ //Look at the middle slice faces
                return true;
            }

            return false;
        }

        private int orientEdge(int index1, int index2){
            char[] faces = cube.getArr();
            char checkFace;
            char sideFace;
            if(inInspectionSpot(index1)){
                checkFace = faces[index1];
                sideFace = faces[index2];
            }
            else if(inInspectionSpot(index2)){
                checkFace = faces[index2];
                sideFace = faces[index1];
            }
            else{
                System.out.println("Error: invalid edge provided");
                return 1;
            }

            if(checkFace == 'G' || checkFace == 'B'){
                return 1;
            }
            else if(checkFace == 'W' || checkFace == 'Y'){
                if(sideFace == 'O' || sideFace == 'R'){
                    return 1;
                }
                return 0;
            }

            return 0;
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
//            originalcorner = type;
//            truecorner = identifyCubie(a, b, c);
            truecorner = type;
            originalcorner = identifyCubie(a, b, c);
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
                    else //On back face
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
        corners[1] = new cornercubie(6, 11, 12, "UFL");
        corners[2] = new cornercubie(0, 9, 20, "ULB");
        corners[3] = new cornercubie(2, 17, 18, "UBR");
        corners[4] = new cornercubie(47, 39, 38, "DFR");
        corners[5] = new cornercubie(45, 35, 36, "DLF");
        corners[6] = new cornercubie(51, 33, 44, "DBL");
        corners[7] = new cornercubie(53, 41, 42, "DRB");
        this.edges = new edgecubie[12];
        edges[0] = new edgecubie(3, 10, "UL");
        edges[1] = new edgecubie(5, 16, "UR");
        edges[2] = new edgecubie(1, 19, "UB");
        edges[3] = new edgecubie(7, 13, "UF");
        edges[4] = new edgecubie(48, 34, "DL");
        edges[5] = new edgecubie(50, 40, "DR");
        edges[6] = new edgecubie(52, 43, "DB");
        edges[7] = new edgecubie(46, 37, "DF");
        edges[8] = new edgecubie(21, 32, "LB");
        edges[9] = new edgecubie(23, 24, "LF");
        edges[10] = new edgecubie(29, 30, "RB");
        edges[11] = new edgecubie(27, 26, "RF");
//        this.heuristic = CalculateHeuristic();
    }

    cubeState(RubiksCube cube, cubeState parent){
        this.cube = cube;
        this.parent = parent;
//        this.heuristic = CalculateHeuristic();
        this.previousMoves = "";
    }

    cubeState(RubiksCube cube, cubeState parent, String move){
        this.cube = cube;
        this.parent = parent;
        this.previousMoves = "";
        this.Move = move;
        this.corners = new cornercubie[8];
        corners[0] = new cornercubie(8, 15, 14, "URF");
        corners[1] = new cornercubie(6, 11, 12, "UFL");
        corners[2] = new cornercubie(0, 9, 20, "ULB");
        corners[3] = new cornercubie(2, 17, 18, "UBR");
        corners[4] = new cornercubie(47, 39, 38, "DFR");
        corners[5] = new cornercubie(45, 35, 36, "DLF");
        corners[6] = new cornercubie(51, 33, 44, "DBL");
        corners[7] = new cornercubie(53, 41, 42, "DRB");
        this.edges = new edgecubie[12];
//        edges[0] = new edgecubie(3, 10, "UL");
//        edges[1] = new edgecubie(5, 16, "UR");
//        edges[2] = new edgecubie(1, 19, "UB");
//        edges[3] = new edgecubie(7, 13, "UF");
//        edges[4] = new edgecubie(21, 32, "LB");
//        edges[5] = new edgecubie(23, 24, "LF");
//        edges[6] = new edgecubie(29, 30, "RB");
//        edges[7] = new edgecubie(27, 26, "RF");
//        edges[8] = new edgecubie(48, 34, "DL");
//        edges[9] = new edgecubie(50, 40, "DR");
//        edges[10] = new edgecubie(52, 43, "DB");
//        edges[11] = new edgecubie(46, 37, "DF");
        edges[0] = new edgecubie(3, 10, "UL");
        edges[1] = new edgecubie(5, 16, "UR");
        edges[2] = new edgecubie(1, 19, "UB");
        edges[3] = new edgecubie(7, 13, "UF");
        edges[4] = new edgecubie(48, 34, "DL");
        edges[5] = new edgecubie(50, 40, "DR");
        edges[6] = new edgecubie(52, 43, "DB");
        edges[7] = new edgecubie(46, 37, "DF");
        edges[8] = new edgecubie(21, 32, "LB");
        edges[9] = new edgecubie(23, 24, "LF");
        edges[10] = new edgecubie(29, 30, "RB");
        edges[11] = new edgecubie(27, 26, "RF");
//        this.heuristic = CalculateHeuristic();
    }

    private int getCornerIndex(String name) {
        switch(name) {
            case "URF": return 0;
            case "UFL": return 1;
            case "ULB": return 2;
            case "UBR": return 3;
            case "DFR": return 4;
            case "DLF": return 5;
            case "DLB": return 6;
            case "DRB": return 7;
            default: return 0;
        }
    }

    private int getEdgeIndex(String name) {
        switch(name) {
            case "UL": return 0;
            case "UR": return 1;
            case "UB": return 2;
            case "UF": return 3;
            case "LB": return 4;
            case "LF": return 5;
            case "RB": return 6;
            case "RF": return 7;
            case "DL": return 8;
            case "DR": return 9;
            case "DB": return 10;
            case "DF": return 11;
            default: return 0;
        }
    }

    private int CalculateHeuristic(){
        int cornerscore = 0;
        int edgescore = 0;
        for(cornercubie c : corners){
//            if(c.orientation!=0){
//                cornerscore++;
//            }
            if(!Objects.equals(c.truecorner, c.originalcorner)){
                cornerscore += CORNER_DIST[getCornerIndex(c.truecorner)][getCornerIndex(c.originalcorner)];
//                cornerscore++;
            }
            if(c.orientation!=0){
                cornerscore++;
            }
        }

        for(edgecubie e : edges){
            if(!Objects.equals(e.trueedge, e.originaledge)){
                edgescore += EDGE_DIST[getEdgeIndex(e.trueedge)][getEdgeIndex(e.originaledge)];
//                edgescore++;
            }
            if(e.orientation!=0){
                edgescore++;
            }
        }
        int movesForCorners = (int) Math.ceil(cornerscore / 4.0);
        int movesForEdges = (int) Math.ceil(edgescore / 4.0);
        return Math.max(movesForCorners, movesForEdges);
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
            neighbours.add(tempState);
        }
        return neighbours;
    }

    public HashSet<cubeState> getNeighbours2(){
        HashSet<cubeState> neighbours2 = new HashSet<cubeState>();
        String[] moves = new String[10];
        moves[0] = "U";
        moves[1] = "D";
        moves[2] = "FF";
        moves[3] = "BB";
        moves[4] = "RR";
        moves[5] = "LL";
        moves[6] = "UU";
        moves[7] = "DD";
        moves[8] = "UUU";
        moves[9] = "DDD";
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
            neighbours2.add(tempState);
        }
        return neighbours2;
    }

    public cornercubie getCornercubiebytruecorner(String corner){
        for(cornercubie c : corners){
            if(c.truecorner.equals(corner)){
                return c;
            }
        }
        throw new IllegalArgumentException("Wrong input");
    }

    public cornercubie getCornercubiebyoriginalcorner(String corner){
        for(cornercubie c : corners){
            if(c.originalcorner.equals(corner)){
                return c;
            }
        }
        throw new IllegalArgumentException("Wrong input");
    }

    public int getManhattandistance(String corner1, String corner2){
        String startingcorner = getCornercubiebytruecorner(corner1).originalcorner;
        Queue<cubeState> queue = new LinkedList<>();
        queue.add(this);
        int moves = 0;
        while(!queue.isEmpty()){
            cubeState cube = queue.poll();
            cornercubie c1 = cube.getCornercubiebyoriginalcorner(startingcorner);
            cornercubie c2 = cube.getCornercubiebytruecorner(corner2);
            StringBuilder solution = new StringBuilder();
            if(c1.equals(c2)){
                if(cube.parent == null){
                    return 0;
                }
                int parents = 0;
                while(cube.parent!=null) {
                    cube = cube.parent;
                    parents++;
                }
                return parents;
            }
            for(cubeState state : cube.getNeighbours()){
                queue.add(state);
            }
        }
        return moves;
    }

    public edgecubie getEdgecubiebytrueedge(String edge){
        for(edgecubie e : edges){
            if(e.trueedge.equals(edge)){
                return e;
            }
        }
        throw new IllegalArgumentException("Wrong input");
    }

    public edgecubie getEdgecubiebyoriginaledge(String edge){
        for(edgecubie e : edges){
            if(e.originaledge.equals(edge)){
                return e;
            }
        }
        throw new IllegalArgumentException("Wrong input");
    }

    public int getManhattandistanceedge(String edge1, String edge2){
        String startingedge = getEdgecubiebytrueedge(edge1).originaledge;
        Queue<cubeState> queue = new LinkedList<>();
        queue.add(this);
        int moves = 0;
        while(!queue.isEmpty()){
            cubeState cube = queue.poll();
            edgecubie c1 = cube.getEdgecubiebyoriginaledge(startingedge);
            edgecubie c2 = cube.getEdgecubiebytrueedge(edge2);
            if(c1.equals(c2)){
                if(cube.parent == null){
                    return 0;
                }
                int parents = 0;
                while(cube.parent!=null) {
                    cube = cube.parent;
                    parents++;
                }
                return parents;
            }
            for(cubeState state : cube.getNeighbours()){
                queue.add(state);
            }
        }
        return moves;
    } //Previously used for A* algorithm but scrapped

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        cubeState that = (cubeState) o;
        return this.cube.equals(that.cube);
    }

    @Override
    public int hashCode() {
        return this.cube.hashCode();
    } //Used for getneighbours
}
