import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
GUI gui;
JPanel mainMenuPanel= new JPanel();
public MainMenu(){
    gui= new GUI();
    JButton playButton= new JButton("Play");
    playButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event){
            new DifficultySelector(gui);
        }
    });
    playButton.setFocusPainted(false);

    mainMenuPanel = new JPanel();
    mainMenuPanel.add(playButton);
    mainMenuPanel.revalidate();
    mainMenuPanel.repaint();
    gui.addToContainer(mainMenuPanel, "Main menu");
    gui.getCard().show(gui.getContainer(), "Main menu");
}
}
