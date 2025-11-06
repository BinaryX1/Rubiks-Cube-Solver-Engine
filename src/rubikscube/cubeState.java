package rubikscube;

public class cubeState {
    RubiksCube cube;
    cubeState parent;
    int distance;
    cubeState(RubiksCube cube){
        this.cube = cube;
        this.parent = null;
    }
    cubeState(RubiksCube cube, cubeState parent){
        this.cube = cube;
        this.parent = parent;
    }
}
