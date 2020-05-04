package algorithms.mazeGenerators;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Maze {

    private int[][] mat;
    private Position start;
    private Position end;

    public Maze(int[][] mat, Position start, Position end) {
        this.start = start;
        this.end = end;
        this.mat = mat;
    }

    public Maze(byte[] byteArray) {
        byte[] curr4Bytes = new byte[4];
        int j=0;
        for (int i = 0; i < 4; i++) {
            curr4Bytes[j] = byteArray[i];
            j++;
        }
        j=0;
        int rows = convertByteToInt(curr4Bytes);
        for (int i = 4; i < 8; i++) {
            curr4Bytes[j] = byteArray[i];
            j++;
        }
        j=0;
        int cols = convertByteToInt(curr4Bytes);
        for (int i = 8; i < 12; i++) {
            curr4Bytes[j] = byteArray[i];
            j++;
        }
        j=0;
        int startRow = convertByteToInt(curr4Bytes);
        for (int i = 12; i < 16; i++) {
            curr4Bytes[j] = byteArray[i];
            j++;
        }
        j=0;
        int startCol = convertByteToInt(curr4Bytes);
        for (int i = 16; i < 20; i++) {
            curr4Bytes[j] = byteArray[i];
            j++;
        }
        j=0;
        int endRow = convertByteToInt(curr4Bytes);
        for (int i = 20; i < 24; i++) {
            curr4Bytes[j] = byteArray[i];
            j++;
        }
        j=0;
        int endCol = convertByteToInt(curr4Bytes);

        //create start and end
        Position start = new Position(startRow,startCol);
        Position end = new Position(endRow,endCol);
        // create mat data
        int temp = 0;
        int[][] mat = new int[rows][cols];
        int k = 25;
        for (int i = 0; i < rows; i++) {
            for (j = 0; j < cols; j++) {
                mat[i][j] = (byteArray[k]);  ////TODO: check
                k++;
            }
        }
        //initialize
        this.start = start;
        this.end = end;
        this.mat = mat;
    }

    public int[][] getMat() {
        return mat;
    }

    public int getNumOfRows() {
        return mat.length;
    }

    public int getNumOfColumns() {
        return mat[0].length;
    }

    public Position getStartPosition() {
        return start;
    }

    public Position getGoalPosition() {
        return end;
    }

    public void print(){
        if (mat == null)
            System.out.print("null");
        int iMax = mat.length - 1;
        if (iMax == -1)
            System.out.print("");

        for (int i = 0; i<mat.length ; i++) {
            for (int j=0; j<mat[0].length; j++){
                if (i==start.getRowIndex() && j==start.getColumnIndex()){
                    System.out.print('S');
                }
                else if (i==end.getRowIndex() && j==end.getColumnIndex()){
                    System.out.print('E');
                }
                //if it's the end of a row
                else System.out.print(mat[i][j]);
                if (j==mat[0].length-1){
                    System.out.print('\n');
                }
            }
        }
    }

    public byte[] toByteArray() {
        int rows = mat.length;
        int cols = mat[0].length;
        byte[] byteArray = new byte[25 + (rows * cols)];
        byte[] bytesOfRows = convertIntToByte(rows);
        byte[] bytesOfCols = convertIntToByte(cols);
        byte[] bytesOfStartRow = convertIntToByte(start.getRowIndex());
        byte[] bytesOfStartCol = convertIntToByte(start.getColumnIndex());
        byte[] bytesOfGoalRow = convertIntToByte(end.getRowIndex());
        byte[] bytesOfGoalCol = convertIntToByte(end.getColumnIndex());
        // add to array the first 24 byte - all the maze's data
        int j = 0;
        for (int i = 0; i < 4; i++) {
            byteArray[i] = bytesOfRows[j];
            j++;
        }
        j = 0;
        for (int i = 4; i < 8; i++) {
            byteArray[i] = bytesOfCols[j];
            j++;
        }
        j = 0;
        for (int i = 8; i < 12; i++) {
            byteArray[i] = bytesOfStartRow[j];
            j++;
        }
        j = 0;
        for (int i = 12; i < 16; i++) {
            byteArray[i] = bytesOfStartCol[j];
            j++;
        }
        j = 0;
        for (int i = 16; i < 20; i++) {
            byteArray[i] = bytesOfGoalRow[j];
            j++;
        }
        j = 0;
        for (int i = 20; i < 24; i++) {
            byteArray[i] = bytesOfGoalCol[j];
            j++;
        }
        byteArray[24] = (byte) (0); //empty for left
        // add the rest of the bytes - the mat
        int k = 25;
        for (int i = 0; i < rows; i++) {
            for (j = 0; j < cols; j++) {
                byteArray[k] = (byte) (mat[i][j]);  ////TODO: check
                k++;
            }
        }
        return byteArray;
    }

    private  byte[] convertIntToByte(int i){
        ByteBuffer allBytes = ByteBuffer.allocate(4);
        allBytes.putInt(i);
        return allBytes.array();
    }

    private int convertByteToInt(byte[] bytes){
        ByteBuffer allBytes = ByteBuffer.wrap(bytes);
        int i = allBytes.getInt();
        return i;
    }

    @Override
    public String toString() {
        return "Maze{" +
                "mat=" + Arrays.toString(mat) +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
