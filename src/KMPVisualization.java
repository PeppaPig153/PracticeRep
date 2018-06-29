import java.awt.*;

/**
 * Класс-визуализатор для алгоритма КМП
 */
public class KMPVisualization extends Visualizable {

    public KMPVisualization(String text, String pattern) {
        super(text, pattern);
    }

    /**
     * Функция для отрисовки визуализации
     * @param g2d
     * @see DrawingPanel
     */
    @Override
    public void visualize(Graphics2D g2d) {
        Color tmp = g2d.getColor();
        g2d.setColor(Color.RED);
        g2d.drawOval(0,0,100,100);
    }
}
