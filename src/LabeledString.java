import javax.swing.*;
import java.awt.*;

/**
 * Класс, реализующий интерфейс CoolString для символьных строк
 */
public class LabeledString extends CoolString {
    public LabeledString(String string, int elementSize, int x, int y) {
        super(string.length(), elementSize, x, y);
        Font f = new Font("Arial", 1, elementSize);//установка шрифта
        for (int i = 0; i < string.length(); ++i) {
            setText(i, String.valueOf(string.charAt(i)));
        }
    }
    public LabeledString(String string, int elementSize, JPanel panel) {
        this(string, elementSize, 0, 0);
    }

}


