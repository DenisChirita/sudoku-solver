import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.*;

public class DifficultySelector {
    private JPanel difficultyPanel = new JPanel();
    GUI gui;

    /**
     * constructor that adds 3 difficulty buttons to an empty panel
     * @param gui is the GUI to which we add the panel
     */
    public DifficultySelector(GUI gui) {

        this.gui = gui;

        createButton("Easy");
        createButton("Medium");
        createButton("Hard");

        // adding and displaying the panel
        gui.addToContainer(difficultyPanel, "Difficulty");
        gui.getCard().show(gui.getContain(), "Difficulty");

    }

    /**
     * Method for creating a choice button
     */
    public void createButton(String buttonName) {
        JButton button = new JButton(buttonName);
        button.setFocusPainted(false);
        difficultyPanel.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String difficulty = buttonName.toLowerCase();
                Level level=RandomLevelGenerator.getLevelWithDifficulty(difficulty);
                new SudokuGrid(gui,level.getBoard(),level.getSolution());
            }
        });
    }
}
