import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class SudokuGrid extends JPanel {
    private int selectedRow;
    private int selectedColumn;
    private int lives;
    private int squaresToWin;
    GUI gui;
    private SudokuSquare[][] gridButtons;
    private int[][] board = new int[Globals.BOARD_SIZE][Globals.BOARD_SIZE];
    private int[][] solution = new int[Globals.BOARD_SIZE][Globals.BOARD_SIZE];
    private final Border BLACK_SQUARE_BORDER = BorderFactory.createLineBorder(Color.BLACK);
    private final Border BOTTOM_BORDER = BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK);
    private final Border RIGHT_BORDER = BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK);
    private final Border BOTTOM_RIGHT_BORDER = BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK);

    // constructor
    public SudokuGrid(GUI gui, int[][] board, int[][] solution) {
        this.gui = gui;
        this.lives = Globals.NUMBER_OF_LIVES;
        squaresToWin = Globals.BOARD_SIZE * Globals.BOARD_SIZE;
        for (int row = 0; row < Globals.BOARD_SIZE; row++)
            for (int col = 0; col < Globals.BOARD_SIZE; col++) {
                this.board[row][col] = board[row][col];
                this.solution[row][col] = solution[row][col];
            }
        setLayout(new GridLayout(Globals.BOARD_SIZE + 1, Globals.BOARD_SIZE));
        this.selectedRow = -1;
        this.selectedColumn = -1;
        createGrid();
        createNumberBar();
        gui.addToContainer(this, "SudokuGrid");
        gui.getCard().show(gui.getContain(), "SudokuGrid");
        revalidate();
        repaint();
    }

    /**
     * Method that creates the Sudoku Grid. Each square has a special type of border
     * so that the 3x3 boxes are highlighted
     */
    public void createGrid() {
        gridButtons = new SudokuSquare[Globals.BOARD_SIZE][Globals.BOARD_SIZE];
        for (int row = 0; row < Globals.BOARD_SIZE; row++) {
            for (int col = 0; col < Globals.BOARD_SIZE; col++) {
                // creates a new button and puts this button into the grid
                gridButtons[row][col] = new SudokuSquare(row, col);
                gridButtons[row][col].setBorder(BLACK_SQUARE_BORDER);
                if ((row + 1) % Globals.BOX_SIZE == 0)
                    gridButtons[row][col].setBorder(BOTTOM_BORDER);
                if ((col + 1) % Globals.BOX_SIZE == 0)
                    gridButtons[row][col].setBorder(RIGHT_BORDER);
                if ((col + 1) % Globals.BOX_SIZE == 0 && (row + 1) % Globals.BOX_SIZE == 0)
                    gridButtons[row][col].setBorder(BOTTOM_RIGHT_BORDER);
                add(gridButtons[row][col]);

            }
        }
        revalidate();
        repaint();
    }

    /**
     * Method that creates the bar with number choices
     */
    public void createNumberBar() {
        for (int i = 1; i <= Globals.BOARD_SIZE; ++i)
            add(new NumberButton(i));
        revalidate();
        repaint();
    }

    /**
     * A private inner class for Sudoku Grid Squares
     */
    private class SudokuSquare extends JButton implements ActionListener {
        protected int row, column;
        private Color color;
        private final Color INITIAL_SQUARE_COLOR = Color.WHITE;
        private final Color SELECTED_SQUARE_COLOR = Color.LIGHT_GRAY;
        private boolean clickableSquare;

        public SudokuSquare(int row, int column) {
            this.row = row;
            this.column = column;
            addActionListener(this);
            resetSquare(row, column);
            // the number in the square will not be highlighted
            setFocusPainted(false);
        }

        /**
         * Method that resets a square when the reset button is pressed
         * 
         * @param row    the row of the square
         * @param column the column of the square
         */
        public void resetSquare(int row, int column) {
            this.color = INITIAL_SQUARE_COLOR;
            setBackground(INITIAL_SQUARE_COLOR);
            if (board[row][column] != 0) {
                addNumberToSquare(board[row][column]);
                // make the given squares unclickable
                this.clickableSquare = false;
                squaresToWin--;
            } else {
                this.clickableSquare = true;
                setText("");
            }
        }

        /**
         * When a new button is pressed, its color is changed. If the button was already
         * pressed the color remains the same
         */
        public void actionPerformed(ActionEvent event) {
            if (clickableSquare == true) {
                switchColor();
                // checking if the square was already selected
                if (selectedRow != -1 && !(selectedRow == row && selectedColumn == column)) {
                    gridButtons[selectedRow][selectedColumn].colorButton(INITIAL_SQUARE_COLOR);
                }
                selectedRow = row;
                selectedColumn = column;
            }
        }

        /**
         * Method that sets the background color of a button to a given color
         * 
         * @param color is the given color
         */
        public void colorButton(Color color) {
            this.color = color;
            setBackground(color);
        }

        /**
         * Method that changes the color from white to light gray, respectively from
         * light gray to white
         */
        public void switchColor() {
            if (this.color == INITIAL_SQUARE_COLOR)
                this.color = SELECTED_SQUARE_COLOR;
            else
                this.color = INITIAL_SQUARE_COLOR;
            setBackground(color);
        }

        /**
         * Method that adds a given number to the square
         * @param number is the given number
         */
        public void addNumberToSquare(int number) {
            setText(number + "");
        }

        /**
         * method that makes a square unclickable
         */
        public void setAsUnclickable() {
            this.clickableSquare = false;
        }

        /**
         * Method that returns if a square is clickable
         * 
         * @return true if the square is clickable
         */
        public boolean isClickable() {
            return clickableSquare;
        }

    }

    /**
     * Method that resets the board when the replay button is pushed
     */
    public void resetBoard() {
        this.lives = Globals.NUMBER_OF_LIVES;
        this.squaresToWin = Globals.BOARD_SIZE * Globals.BOARD_SIZE;
        this.selectedRow = -1;
        this.selectedColumn = -1;
        for (int r = 0; r < Globals.BOARD_SIZE; r++)
            for (int c = 0; c < Globals.BOARD_SIZE; c++)
                gridButtons[r][c].resetSquare(r, c);
    }

    /**
     * An inner class for the buttons that select numbers to be added to square
     */
    private class NumberButton extends JButton implements ActionListener {
        /**
         * Constructor that creates the button with a given number in it
         * 
         * @param value the number written on the button
         */
        public NumberButton(int value) {
            super(value + "");
            addActionListener(this);
            // the number in the button will not be highlighted
            setFocusPainted(false);
        }

        public void actionPerformed(ActionEvent event) {
            // checking if any square is selected
            if (selectedRow != -1)
                // checking if we chose the right button and the value can be added to the
                // selected square
                if (solution[selectedRow][selectedColumn] == Integer.parseInt(this.getText())) {
                    if (gridButtons[selectedRow][selectedColumn].color == Color.RED)
                        gridButtons[selectedRow][selectedColumn].colorButton(Color.WHITE);
                    if(gridButtons[selectedRow][selectedColumn].getText().equals("")){
                    gridButtons[selectedRow][selectedColumn].addNumberToSquare(Integer.parseInt(this.getText()));
                    squaresToWin--;
                    }
                    gridButtons[selectedRow][selectedColumn].setAsUnclickable();
                    if (squaresToWin == 0) {
                        displayWinLoseDialog("You won!", "Congratulations, you solved this board!");
                    }
                }
                // if we chose the wrong button a message will be displayed and we lose a life
                else if (gridButtons[selectedRow][selectedColumn].isClickable()) {
                    gridButtons[selectedRow][selectedColumn].colorButton(Color.RED);
                    lives--;
                    // if the game is not over show a warning!
                    if (lives > 0) {
                        JDialog warningDialog = new JDialog(gui, "Warning!");
                        warningDialog.setLocationRelativeTo(gui);
                        JPanel warningPanel = new JPanel();
                        warningPanel.add(new JLabel("Wrong number, you lost a life!"));
                        warningPanel.add(new JLabel("You have " + lives + " lives left."));
                        warningDialog.add(warningPanel);
                        // set the size of dialog
                        warningDialog.setSize(200, 200);
                        // block any other move on the board until the warning is closed
                        warningDialog.setModal(true);
                        warningDialog.setVisible(true);
                    } else {
                        displayWinLoseDialog("Game over!", "Game over, you lost!");
                    }

                }
        }

        /**
         * Method that displays a Game Over Dialog or a Win Dialog. The user can choose
         * to restart the level or play a new one
         */
        private void displayWinLoseDialog(String dialogName, String message) {
            JDialog dialog = new JDialog(gui, dialogName);
            dialog.setLocationRelativeTo(gui);
            JPanel panel = new JPanel();
            JPanel messagePanel = new JPanel();
            JButton restartButton = new JButton("Restart Level");
            JButton nextLevelButton = new JButton("New Game");
            JLabel messageLabel = new JLabel(message);
            restartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    dialog.dispose();
                    resetBoard();
                }
            });
            nextLevelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    dialog.dispose();
                    new DifficultySelector(gui);
                }
            });
            restartButton.setFocusPainted(false);
            nextLevelButton.setFocusPainted(false);
            messagePanel.add(messageLabel);
            panel.add(restartButton);
            panel.add(nextLevelButton);
            dialog.add(messagePanel, BorderLayout.NORTH);
            dialog.add(panel);
            dialog.setSize(300, 300);
            dialog.setModal(true);
            dialog.setVisible(true);
        }
    }
}
