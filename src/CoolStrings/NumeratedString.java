package CoolStrings;

import javax.swing.*;

/**
 * Класс, реализующий CoolStrings.CoolString для последовательности чисел
 */
public class NumeratedString extends CoolString {
    public NumeratedString(int[] elements, int elementSize, JPanel panel, int x, int y) {
        super(elements.length, elementSize, panel, x, y);
        for(int i = 0; i < elements.length; ++i) {
            setText(i, String.valueOf(elements[i]));
            // TODO: Отображение чисел, состоящих из нескольких цифр
//            setFontSize(i, getBasicFontSize()/(3-String.valueOf(elements[i]).length());
            setFontSize(i, 8);
        }
    }
    public NumeratedString(int[] elements, int elementSize, JPanel panel) {
        this(elements, elementSize, panel, 0, 0);
    }
}
