package rubikscube;

public class EdgeTableGenerator {
    public static void main(String[] args) {

        String[] edges = {
                "UL", // 0
                "UR", // 1
                "UB", // 2
                "UF", // 3
                "LB", // 4
                "LF", // 5
                "RB", // 6
                "RF", // 7
                "DL", // 8
                "DR", // 9
                "DB", // 10
                "DF"  // 11
        };

        RubiksCube solvedCube = new RubiksCube();

        cubeState state = new cubeState(solvedCube);

        System.out.println("static final int[][] EDGE_DIST = {");

        for (int i = 0; i < 12; i++) {
            System.out.print("    {"); // Start row
            for (int j = 0; j < 12; j++) {

                int dist = state.getManhattandistanceedge(edges[i], edges[j]);

                System.out.print(dist);

                if (j < 11) System.out.print(", ");
            }
            System.out.println("}, // Row " + i + ": " + edges[i]);
        }
        System.out.println("};");
    }
}