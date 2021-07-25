public class SudokuSolver {

    private int[] rows = new int[Globals.BOARD_SIZE];
    private int[] columns = new int[Globals.BOARD_SIZE];
    private int[][] boxes = new int[Globals.BOX_SIZE][Globals.BOX_SIZE];
    private int[][] board = new int[Globals.BOARD_SIZE][Globals.BOARD_SIZE];
    private int[][] solution = new int[Globals.BOARD_SIZE][Globals.BOARD_SIZE];
    private int numberOfSolutions = 0;
    private boolean validBoard = true;

    /**
     * Method that creates an array to store the given values of the bord squares
     * and initialises the arrays used to check each, row, column or box with the
     * binary encoding of the values in each row/column/box.
     */
    public SudokuSolver(int[][] board) {
        for (int row = 0; row < Globals.BOARD_SIZE; ++row)
            for (int col = 0; col < Globals.BOARD_SIZE; ++col)
                this.board[row][col] = board[row][col];
        initializeCheckArrays();
        if (validBoard == true)
            solve(0, 0);
    }

    /**
     * Method that adds for each square value i: 2 to the power of i to the row,
     * column and box array in which the square is this is used to save memory and
     * time, we keep a number instead of 9 values. Checking if a number is already
     * in a row/column/box can be done in O(1).
     */
    public void initializeCheckArrays() {
        for (int row = 0; row < Globals.BOARD_SIZE; ++row)
            for (int col = 0; col < Globals.BOARD_SIZE; ++col) {
                int boardValue = board[row][col];

                // checking if the given board was invalid
                // (containing the same value in a row, column or 3x3 box).
                board[row][col] = 0;
                if (boardValue != 0 && isValidSquareValue(1 << (boardValue), row, col) == false) {
                    validBoard = false;
                    break;
                }
                board[row][col]=boardValue;


                rows[row] |= (1 << boardValue);
                columns[col] |= (1 << boardValue);
                boxes[row / Globals.BOX_SIZE][col / Globals.BOX_SIZE] |= (1 << boardValue);
            }
    }

    /**
     * Method that checks if we can add a value to a given square by checking if 2
     * to the power value is in the binary representation of the the row, column or
     * box coresponding to the square
     * @param value  the value that we try to add to the square
     * @param row    the row of the givem square
     * @param column the column of the given square
     * @return true if the value can be added to the square
     */
    public boolean isValidSquareValue(int value, int row, int column) {
        if (board[row][column] != 0)
            return false;
        else
            return ((value & rows[row]) == 0 && (value & columns[column]) == 0
                    && (value & boxes[row / Globals.BOX_SIZE][column / Globals.BOX_SIZE]) == 0);
    }


    /**
     * Method that finds a solution (using backtracking) if it exists.
     * @param row    the current row of the square we are trying to complete
     * @param column the current column of the square we are trying to complete
     * @return true if the board is solvable and has more than 1 solution
     */
    public boolean solve(int row, int column) {
        // checking if we found a solution
        if (row == Globals.BOARD_SIZE) {
            numberOfSolutions++;
            for (int i = 0; i < Globals.BOARD_SIZE; ++i)
                for (int j = 0; j < Globals.BOARD_SIZE; ++j)
                    solution[i][j] = board[i][j];
            // if we have 2 solutions, it's redundant to search for more
                return numberOfSolutions == 2;
        }
        // setting the next square that will be completed after this one
        int nextRow = row + (column + 1) / Globals.BOARD_SIZE;
        int nextColumn = (column + 1) % Globals.BOARD_SIZE;
        // if the current square is already filled, we move to the next one
        if (board[row][column] != 0) {
            boolean isSolvable = solve(nextRow, nextColumn);
            if (isSolvable)
                return true;
        }
        for (int i = 1; i <= 9; ++i) {
            int value = (1 << i);
            if (isValidSquareValue(value, row, column)) {
                // add the encoded value to its respective row, column, box
                rows[row] |= value;
                columns[column] |= value;
                boxes[row / Globals.BOX_SIZE][column / Globals.BOX_SIZE] |= value;
                // add the value to the square
                board[row][column] = i;
                // after choosing the value of this square we move on to the next square
                boolean isSolvable = solve(nextRow, nextColumn);
                if (isSolvable)
                    return true;
                // remove the value from the square
                board[row][column] = 0;
                // remove what was changed in the checking arrays
                rows[row] ^= value;
                columns[column] ^= value;
                boxes[row / Globals.BOX_SIZE][column / Globals.BOX_SIZE] ^= value;
            }
        }
        return false;
    }

    /**
     * Method that returns a solution to the board
     * @return a solution to the board, or contains only 0 if no solution is found
     */
    public int[][] getSolution() {
        return solution;
    }

    /**
     * Method that returns the number of solutions to the Sudoku board
     * @return 0 if there is no solution, 1 if exactly 1 solution exists, 2 if there
     *         are at least 2 solutions
     */
    public int getNumberOfSolutions() {
        return numberOfSolutions;
    }

    public boolean isValidBoard() {
        return validBoard;
    }
}