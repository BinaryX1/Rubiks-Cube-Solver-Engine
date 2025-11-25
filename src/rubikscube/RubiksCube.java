package rubikscube;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.util.Arrays;

public class RubiksCube {
    private String cube;
    private char[] arr;
    /**
     * default constructor
     * Creates a Rubik's Cube in an initial state:
     *    OOO
     * GGGWWWBBBYYY
     * GGGWWWBBBYYY
     * GGGWWWBBBYYY
     *    RRR
     *    RRR
     *    RRR
     */
    public RubiksCube() {
        this.cube =
                "   OOO\n" +
                        "   OOO\n" +
                        "   OOO\n" +
                        "GGGWWWBBBYYY\n" +
                        "GGGWWWBBBYYY\n" +
                        "GGGWWWBBBYYY\n" +
                        "   RRR\n" +
                        "   RRR\n" +
                        "   RRR\n";
        arr = "OOOOOOOOOGGGWWWBBBYYYGGGWWWBBBYYYGGGWWWBBBYYYRRRRRRRRR".toCharArray();
    }

    /**
     * @param fileName
     * @throws IOException
     * @throws IncorrectFormatException
     * Creates a Rubik's Cube from the description in fileName
     */
    public RubiksCube(String fileName) throws IOException, IncorrectFormatException {
        int lineCount = 0;
        final String valid_characters = " WYBRGO";
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            lineCount++;
            if (lineCount > 9) {
                throw new IncorrectFormatException("Incorrect number of lines in file");
            }
            String line = scanner.nextLine();
            for (char c : line.toCharArray()) {
                if (valid_characters.indexOf(c) == -1) {
                    throw new IncorrectFormatException("Incorrect character found in file");
                }
            }
            if (lineCount <= 3 || lineCount >= 7) {
                if (line.length() != 6 || !line.startsWith("   ")) {
                    throw new IncorrectFormatException("Incorrect file format");
                }
            } else {
                if (line.length() != 12) {
                    throw new IncorrectFormatException("Incorrect file format");
                }
            }
            builder.append(line.trim());
        }
        if(lineCount != 9){
            throw new IncorrectFormatException("Incorrect number of lines in file");
        }
        this.arr = builder.toString().toCharArray();
    }

    private void swap(int a, int b, int c, int d){
        char temp = arr[a];
        arr[a] = arr[b];
        arr[b] = arr[c];
        arr[c] = arr[d];
        arr[d] = temp;
    }

    private static final int[][] Faces = {
            {0,1,2,3,4,5,6,7,8},
            {9,10,11,21,22,23,33,34,35},
            {12,13,14,24,25,26,36,37,38},
            {15,16,17,27,28,29,39,40,41},
            {18,19,20,30,31,32,42,43,44},
            {45,46,47,48,49,50,51,52,53}
    };

    private void rotateFace(int[] Face){
        swap(Face[0], Face[6], Face[8], Face[2]);
        swap(Face[1], Face[3], Face[7], Face[5]);
    }
    private void applyF(){
        swap(6, 35, 47, 15);
        swap(7, 23, 46, 27);
        swap(8, 11, 45, 39);
        rotateFace(Faces[2]);
    }

    private void applyB(){
        swap(2, 41,  51, 9);
        swap(1, 29, 52, 21);
        swap(0, 17, 53, 33);
        rotateFace(Faces[4]);
    }

    private void applyR(){
        swap(2, 14, 47, 42);
        swap(5, 26, 50, 30);
        swap(8, 38, 53, 18);
        rotateFace(Faces[3]);
    }

    private void applyL(){
        swap(3, 32, 48, 24);
        swap(6, 20, 51, 36);
        swap(0, 44, 45, 12);
        rotateFace(Faces[1]);
    }

    private void applyU(){
        swap(9, 12, 15, 18);
        swap(10, 13, 16, 19);
        swap(11, 14, 17, 20);
        rotateFace(Faces[0]);
    }

    private void applyD(){
        swap(42, 39, 36, 33);
        swap(43, 40, 37, 34);
        swap(44, 41, 38, 35);


        // Ensure this rotates indices 45-53 Clockwise
        rotateFace(Faces[5]);
    }
    /**
     * @param moves
     * Applies the sequence of moves on the Rubik's Cube
     */
    public void applyMoves(String moves) {
        for (char move : moves.toCharArray()) {
            switch (move) {
                case 'F':
                    applyF();
                    break;
                case 'B':
                    applyB();
                    break;
                case 'R':
                    applyR();
                    break;
                case 'L':
                    applyL();
                    break;
                case 'U':
                    applyU();
                    break;
                case 'D':
                    applyD();
                    break;
            }
        }
    }


    /**
     * returns true if the current state of the Cube is solved,
     * i.e., it is in this state:
     *    OOO
     *    OOO
     *    OOO
     * GGGWWWBBBYYY
     * GGGWWWBBBYYY
     * GGGWWWBBBYYY
     *    RRR
     *    RRR
     *    RRR
     */
    public boolean isSolved() {
        return Arrays.equals(this.arr, "OOOOOOOOOGGGWWWBBBYYYGGGWWWBBBYYYGGGWWWBBBYYYRRRRRRRRR".toCharArray());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 3; i++){
            builder.append("   ").append(arr, i*3, 3).append('\n');
        }
        for(int i = 0; i < 3; i++){
            builder.append(arr, 9+i*12, 12).append('\n');
        }
        for(int i = 0; i < 3; i++){
            builder.append("   ").append(arr, 45+i*3, 3).append('\n');
        }
        return builder.toString();
    }

    /**
     *
     * @param moves
     * @return the order of the sequence of moves
     */
    public static int order(String moves) {
        int n = 0;
        RubiksCube cube = new RubiksCube();
        do{
            cube.applyMoves(moves);
            n++;
        }while(!cube.isSolved());
        return n;
    }


    @Override
    public int hashCode(){
        return Arrays.hashCode(arr);
    }

    @Override
    public RubiksCube clone(){
        RubiksCube clone = new RubiksCube();
        clone.arr = Arrays.copyOf(arr, arr.length);
        return clone;
    }

    public void copyFrom(RubiksCube other){
        this.arr = Arrays.copyOf(other.arr, other.arr.length);
    }

    public char[] getArr(){
        return arr;
    }
}

