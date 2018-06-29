import java.awt.*;

/**
 * Класс-визуализатор для наивного алгоритма
 */
public class NaiveVisualization extends Visualizable {


    public NaiveVisualization(String text, String pattern) {
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
        g2d.setColor(Color.BLUE);
        g2d.drawOval(0,0,100,100);
    }
}
