import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    GUI gui;
    JPanel mainMenuPanel = new JPanel();

    // constructor that displays a panel with a Play button
    public MainMenu() {
        gui = new GUI();

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new DifficultySelector(gui);
            }
        });
        playButton.setFocusPainted(false);

        mainMenuPanel = new JPanel();
        mainMenuPanel.add(playButton);
        mainMenuPanel.revalidate();
        mainMenuPanel.repaint();
        gui.addToContainer(mainMenuPanel, "Main menu");
        gui.getCard().show(gui.getContain(), "Main menu");
    }
}
