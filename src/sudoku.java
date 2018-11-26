import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Miguel Estrada
 * Sudoku project
 * Data Structures
 */

public class sudoku {
    /**
     * Main method we start file, scan it and solve soduku.
     * @param arg
     */
    public static void main(String[] arg) throws IOException{
        String suduko = "sudoku.txt";
        File input = new File(suduko);
       //for use later
        String name  = input.toString();
        //reading file
        readFile(suduko);
        //solving sudoku
        sudoku s = new sudoku();

        if (s.SolveSudoku()) {
            s.print(grid, name);
        } else {
            System.out.println("No Solution");
        }
    }
    //made the grid static for better call.
    public static int grid[][];

    /**
     * Method that uses scanner to scan file in.
     * initialize grid, and add them.
     * @param file
     * @return
     */
    public static int[][] readFile(String file) {
        try {
            Scanner input = new Scanner(new File(file));
            grid = new int[9][9];
            //while (input.hasNextLine()) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    grid[i][j] = input.nextInt();
                    //System.out.println(grid[i][j]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return grid;
    }

    /**
     * this method finds location of the grid
     * @return
     */
    public static int[] findBlankLocation() {
        int[] cell = new int[2];//cell[0]-row cell[1]-column
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    cell[0] = i;
                    cell[1] = j;
                    return cell;
                }
            }
        }
        cell[0] = -1;
        cell[1] = -1;
        return cell;//grid is full
    }

    /**
     * Method we call to solve sudoku
     * @return
     */
    public static boolean SolveSudoku() {
        int row;
        int col;
        int[] blankCell = findBlankLocation();
        row = blankCell[0];
        col = blankCell[1];
        if (row == -1) {
            return true;
        }//we fill grid
        for (int i = 0; i <= 9; i++) {
            //check if i is save on the grid
            if (checkConflict(row, col, i)) {
                grid[row][col] = i;
                if (SolveSudoku()) {
                    return true;
                }
                //revert back if numbers ddnt work
                grid[row][col] = 0;
            }
        }
        return false;
    }

    /**
     * this will help on checking if row or column contain number
     * @param row
     * @param col
     * @param n
     * @return
     */
    public static boolean checkConflict(int row, int col, int n) {
        if (!UsedInRow(row, n) && !UsedInColumn(col, n) && !UsedInBox(row - row % 3, col - col % 3, n)) {
            return true;
        }
        return false;
    }

    /**
     * check if numberis not in particular row
     * @param row
     * @param n
     * @return
     */
    public static boolean UsedInRow(int row, int n) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == n) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if number is not in particular column
     * @param col
     * @param n
     * @return
     */
    public static boolean UsedInColumn(int col, int n) {
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == n) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if not in 3x3 box
     * @param StartRow
     * @param StartCol
     * @param n
     * @return
     */
    public static boolean UsedInBox(int StartRow, int StartCol, int n) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + StartRow][j + StartCol] == n) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method to print it out
     * use File writer to write it to a file
     */
    public static void print(int [][] grid, String name) throws IOException{
        FileWriter writer = new FileWriter(name + ".solved");

        for (int row = 0; row < grid.length; row++) {

            for (int col = 0; col < grid.length; col++) {

                //System.out.print(grid[row][col] + " ");
                writer.write(Integer.toString(grid[row][col]));

            }
            //System.out.println();
            writer.write("\n");
        }
        writer.close();
    }
}