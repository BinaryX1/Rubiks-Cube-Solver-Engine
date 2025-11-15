package rubikscube;

import java.util.HashSet;

public class cubeState implements Comparable<cubeState>{
    public static RubiksCube solvedState = new RubiksCube();
    RubiksCube cube;
    cubeState parent;
    public int heuristic;
    public int distance;
    String previousMoves;
    public int score;
    String Move;
    cubeState(RubiksCube cube){
        this.cube = cube;
        this.parent = null;
        this.heuristic = CalculateHeuristic();
        this.previousMoves = "";
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
        this.heuristic = CalculateHeuristic();
        this.previousMoves = "";
        this.Move = move;
    }

    private int CalculateHeuristic(){
        int score = 0;
        char[] faces = cube.getArr();
        char[] solved = solvedState.getArr();
        for(int i = 0; i < faces.length; i++){
            if(faces[i]!=solved[i]){
                score++;
            }
        }
        return score;
    }

    public HashSet<cubeState> getNeighbours(){
        HashSet<cubeState> neighbours = new HashSet<cubeState>();
        String[] moves = new String[6];
        moves[0] = "F";
        moves[1] = "B";
        moves[2] = "R";
        moves[3] = "L";
        moves[4] = "U";
        moves[5] = "D";
        RubiksCube temp = new RubiksCube();
        for(String move : moves){
            temp.copyFrom(this.cube);
            temp.applyMoves(move);
            cubeState tempState = new cubeState(temp.clone(), this, move);
//            tempState.previousMoves = this.previousMoves + move;
            neighbours.add(tempState);
        }
        return neighbours;
//        cubeState Fneighbour = new cubeState(cube.clone(), this);
//        cubeState Bneighbour = new cubeState(cube.clone(), this);
//        cubeState Rneighbour = new cubeState(cube.clone(), this);
//        cubeState Lneighbour = new cubeState(cube.clone(), this);
//        cubeState Uneighbour = new cubeState(cube.clone(), this);
//        cubeState Dneighbour = new cubeState(cube.clone(), this);
//
//        Fneighbour.cube.applyMoves("F");
//        Fneighbour.previousMoves = this.previousMoves + "F";
//        Bneighbour.cube.applyMoves("B");
//        Bneighbour.previousMoves = this.previousMoves + "B";
//        Rneighbour.cube.applyMoves("R");
//        Rneighbour.previousMoves = this.previousMoves + "R";
//        Lneighbour.cube.applyMoves("L");
//        Lneighbour.previousMoves = this.previousMoves + "L";
//        Uneighbour.cube.applyMoves("U");
//        Uneighbour.previousMoves = this.previousMoves + "U";
//        Dneighbour.cube.applyMoves("D");
//        Dneighbour.previousMoves = this.previousMoves + "D";
//
//        neighbours.add(Fneighbour);
//        neighbours.add(Bneighbour);
//        neighbours.add(Rneighbour);
//        neighbours.add(Lneighbour);
//        neighbours.add(Uneighbour);
//        neighbours.add(Dneighbour);
//
//        return neighbours;
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
