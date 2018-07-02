package GUIs;

import Visualizators.Visualizable;

import javax.swing.*;

public class DrawingPanel extends JLayeredPane {
    Visualizable visualizable;
    public DrawingPanel() {
        drawingLayer = new JPanel();
        add(drawingLayer, PALETTE_LAYER);
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

    JPanel drawingLayer;
}
