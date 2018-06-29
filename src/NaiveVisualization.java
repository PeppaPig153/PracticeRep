import java.awt.*;

public class NaiveVisualization extends Visualizable {


    public NaiveVisualization(String text, String pattern) {
        super(text, pattern);
    }

    @Override
    public void visualize(Graphics2D g2d) {
        Color tmp = g2d.getColor();
        g2d.setColor(Color.BLUE);
        g2d.drawOval(0,0,100,100);
    }
}
