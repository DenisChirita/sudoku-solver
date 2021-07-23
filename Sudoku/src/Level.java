import javax.json.Json;
import javax.json.JsonArray;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.*;

public class Level {
    private String name;
    private String difficulty;
    private int size;
    private int[][] board = new int[Globals.BOARD_SIZE][Globals.BOARD_SIZE];
    private int[][] solution = new int[Globals.BOARD_SIZE][Globals.BOARD_SIZE];

    public Level(String filepath) {
        LevelReader(filepath);
    }

    /**
     * Method that reads the data from the JSON file
     * @param filepath is the path to the JSON file
     * @throws Exception if the file cannot be parsed or the content is not a valid
     *                   sudoku board
     */
    public void LevelReader(String filepath) {
        JsonParserFactory factory = Json.createParserFactory(null);
        try(JsonParser parser = factory.createParser(new FileReader(filepath))) {
            String eventName = "";
            while (parser.hasNext()) {
                JsonParser.Event event = parser.next();
                if (event == JsonParser.Event.KEY_NAME) {
                    eventName = parser.getString();
                    event = parser.next();
                    switch (eventName) {
                        case "Name":
                            this.name = parser.getString();
                            break;
                        case "Difficulty":
                            this.difficulty = parser.getString();
                            break;
                        case "Size":
                            this.size = Integer.parseInt(parser.getString());
                            break;
                        case "Board":
                            populateBoard(parser.getArray());
                            break;
                    }
                }
            }
        } catch (InvalidLevelException e) {
            System.out.println(name+": "+e.getDescription());
            System.exit(0);
        } catch (Exception e) {
            System.out.println(name+": "+e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Method that completes the game board squares values from the given JSON level
     * 
     * @param jsonBoard represents the bidimensional JSON Array containing the grid
     * @throws InvalidLevelException is thrown if the level file is invalid.
     */
    public void populateBoard(JsonArray jsonBoard) throws InvalidLevelException {
        for (int row = 0; row < Globals.BOARD_SIZE; ++row)
            for (int column = 0; column < Globals.BOARD_SIZE; ++column) {
                int value = ((JsonArray) jsonBoard.get(row)).getInt(column);
                if (value >= 0 && value <= 9)
                    board[row][column] = value;
                else
                    throw new InvalidLevelException(InvalidLevelException.INVALID_BOARD_VALUE);

            }
            SudokuSolver sudokuSolver = new SudokuSolver(board);
            // checking if the board is not valid
            if (sudokuSolver.isValidBoard() == false)
                throw new InvalidLevelException(InvalidLevelException.INVALID_BOARD);
            if (sudokuSolver.getNumberOfSolutions() >= 2)
                throw new InvalidLevelException(InvalidLevelException.INVALID_UNIQUE_BOARD_SOLUTION);
            else if (sudokuSolver.getNumberOfSolutions() == 0)
                throw new InvalidLevelException(InvalidLevelException.INVALID_BOARD);
            else {
                for (int row = 0; row < Globals.BOARD_SIZE; ++row)
                    for (int col = 0; col < Globals.BOARD_SIZE; ++col)
                        this.solution[row][col] = sudokuSolver.getSolution()[row][col];
            }

        }

    //getters
    public String getDifficulty() {
        return difficulty;
    }

    public int[][] getBoard(){
        return board;
    }

    public int[][] getSolution(){
        return solution;
    }

}