import java.awt.*;

public abstract class Visualizable {
    public abstract void visualize(Graphics2D g2d);

    public Visualizable(String text, String pattern){
        this.text = text;
        this.pattern = pattern;
    }

    protected String text;
    protected String pattern;
}
