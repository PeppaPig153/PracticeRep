package GUIs;

import Visualizators.Visualizable;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

public class DrawingPanel extends JLayeredPane {
    public DrawingPanel() {
        setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void setVisualization(Visualizable visualizable) {
        this.visualizable = visualizable;
    }

    public Visualizable getVisualization() {
        return visualizable;
    }

    public JPanel getDrawingLayer() {
        return drawingLayer;
    }

    public void setDrawingLayer(JPanel panel) {
        if (drawingLayer != null) {
            remove(drawingLayer);
        }
        this.drawingLayer = panel;
        add(drawingLayer, Integer.valueOf(1));
        drawingLayer.setBounds(0,0, 1000,600);
        drawingLayer.setOpaque(false);

    }
//    class DrawingLayer extends JPanel {
//        @Override
//        public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            g.setColor(Color.BLACK);
//            g.drawString("BLAH", 20, 20);
//            g.fillRect(200, 200, 200, 200);
//        }
//    }

    private Visualizable visualizable;
    private JPanel drawingLayer;
}
