package rubikscube;

public class TableGenerator {
    public static void main(String[] args) {
        System.out.println("Generating Manhattan Distance Table...");

        String[] corners = {
                "URF", // 0
                "UFL", // 1
                "ULB", // 2
                "UBR", // 3
                "DFR", // 4
                "DLF", // 5
                "DBL", // 6
                "DRB"  // 7
        };

        RubiksCube solvedCube = new RubiksCube();

        cubeState state = new cubeState(solvedCube);

        System.out.println("static final int[][] CORNER_DIST = {");

        for (int i = 0; i < 8; i++) {
            System.out.print("    {"); // Start row
            for (int j = 0; j < 8; j++) {

                int dist = state.getManhattandistance(corners[i], corners[j]);

                System.out.print(dist);

                if (j < 7) System.out.print(", ");
            }
            System.out.println("}, // Row " + i + ": " + corners[i]);
        }
        System.out.println("};");
    }
}
