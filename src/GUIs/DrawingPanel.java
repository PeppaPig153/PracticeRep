package GUIs;

import Visualizators.Visualizable;

import javax.swing.*;

/**
 * Класс, описывающий компоненту, расширяющую JLayeredPane, в которой происходит отображение визуализации алгоритма.
 * Добавляемые компоненты по умолчанию располагаются на нулевом(самом нижнем) уровне, а рисующий слой - на первом уровне(устанавливается с помощью setDrawingLayer)
 */
public class DrawingPanel extends JLayeredPane {
    public DrawingPanel() {
        setBorder(BorderFactory.createLoweredBevelBorder());
        new JPanel();
    }

    void setVisualization(Visualizable visualizable) {
        this.visualizable = visualizable;
    }

    Visualizable getVisualization() {
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

    private Visualizable visualizable;
    private JPanel drawingLayer;
}
