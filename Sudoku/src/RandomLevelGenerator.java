import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;

public class RandomLevelGenerator {
    private static final String LEVEL_DIRECTORY_PATH = "levels";
    private static ArrayList<String> filePaths = new ArrayList<String>();
    private static ArrayList<Level> easyLevels= new ArrayList<Level>();
    private static ArrayList<Level> mediumLevels= new ArrayList<Level>();
    private static ArrayList<Level> hardLevels= new ArrayList<Level>();
    private static int easyLevelsPointer;
    private static int mediumLevelsPointer;
    private static int hardLevelsPointer;

    // constructor
    public RandomLevelGenerator() throws Exception{
        easyLevelsPointer=0;
        mediumLevelsPointer=0;
        hardLevelsPointer= 0;
        createRandomNameList();
        createDifficultyLists();
    }

    /**
     * Method that creates a list with all the names of the files in a random order
     * @throws Exception
     */
    public void createRandomNameList() throws Exception {
        try {
            // using a filter to ignore all the files that don't end with json
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File file, String name) {
                    return name.endsWith("json");
                }
            };
            File directoryFiles = new File(LEVEL_DIRECTORY_PATH);
            File[] files = directoryFiles.listFiles(filter);
            // adding the file paths to an arraylist
            for (int i = 0; i < files.length; i++) {
                filePaths.add(LEVEL_DIRECTORY_PATH + "/" + files[i].getName());
            }
            Collections.shuffle(filePaths);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Method that creates a list with the levels for each difficulty
     */
    public void createDifficultyLists(){
        for(String filepath:filePaths){
            Level currentLevel= new Level(filepath);
        switch (currentLevel.getDifficulty()){
            case "easy": easyLevels.add(currentLevel);break;
            case "medium": mediumLevels.add(currentLevel);break;
            case "hard": hardLevels.add(currentLevel);break;
        }
    }
    }

    /**
     * Method that generates a random level with a specified difficulty
     * @param difficulty is the specified difficulty
     * @return the next level that will be loaded and displayed
     */
    public static Level getLevelWithDifficulty(String difficulty) {
        switch (difficulty){
            case "easy":return easyLevels.get((easyLevelsPointer++) %easyLevels.size());
            case "medium":return mediumLevels.get((mediumLevelsPointer++) %mediumLevels.size());
            case "hard":return hardLevels.get((hardLevelsPointer++) %hardLevels.size());
            default: return new Level("");
        }
    }
}
