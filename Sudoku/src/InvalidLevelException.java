public class InvalidLevelException extends RuntimeException {

    private final String ERROR_MESSAGE;

    public static final String INVALID_FILE = "File not found";
    public static final String INVALID_CONTENTS = "Contents of file are not as expected";
    public static final String INVALID_BOARD_VALUE = "Unexpected value found in board layout";
    public static final String INVALID_BOARD = "The given board is not solvable";
    public static final String INVALID_DIFFICULTY = "Difficulty not recognised";
    public static final String INVALID_FILE_FORMAT = "File must be a json file";
    public static final String INVALID_UNIQUE_BOARD_SOLUTION="The board has more than one solution";

    public InvalidLevelException(String errorMsg) {
        ERROR_MESSAGE = errorMsg;
    }

    
    /**
     * Get a description of how the exception occurred.
     * @return a string containing a description of how the error occurred.
     */
    public String getDescription() {
        return ERROR_MESSAGE;
    }

}