import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.Container;
import java.awt.CardLayout;

public class GUI extends JFrame {
    private int width = 650;
    private int height = 600;
    private CardLayout card;
    private Container container;

    public GUI() {
        // needed for corect display on mac OS
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        card = new CardLayout();
        container = getContentPane(); // sets a container so that multiple panels can be put onto the contentpane
        container.setLayout(card); // sets the frame as having a cardlayout so UI pages can be switched
        setPreferredSize(new Dimension(width, height));
        setLocationByPlatform(true);

        // program ends when closing application
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addToContainer(JPanel panel, String panelName) {
        container.add(panelName, panel);

    }

    public Container getContainer() {
        return container;
    }

    public CardLayout getCard() {
        return card;
    }

}
