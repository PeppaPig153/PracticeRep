import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel {
    Visualizable visualizable;

    public void setVisualization(Visualizable visualizable) {
        this.visualizable = visualizable;
    }

    public Visualizable getVisualizable() {
        return visualizable;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
//        g2d.drawRect(0,0,100,100);
        if (visualizable != null) {
            visualizable.visualize(g2d);
        }
    }
}
