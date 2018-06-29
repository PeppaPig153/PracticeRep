import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel {
    Visualizable visualizable;

    public DrawingPanel(Visualizable visualizable) {
        this.visualizable = visualizable;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Creating visualizer object
        // object.paint(g2d);
    }
}
