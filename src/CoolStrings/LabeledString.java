package CoolStrings;

import GUIs.DrawingPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Класс, реализующий интерфейс CoolStrings.CoolString для символьных строк
 */
public class LabeledString extends CoolString {
    public LabeledString(String string, int elementSize, DrawingPanel panel, int x, int y) {
        super(string.length(), elementSize, panel, x, y);
        Font f = new Font("Arial", 1, elementSize);//установка шрифта
        for (int i = 0; i < string.length(); ++i) {
            setText(i, String.valueOf(string.charAt(i)));
        }
    }
    public LabeledString(String string, int elementSize, DrawingPanel panel) {
        this(string, elementSize, panel, 0, 0);
    }

}


