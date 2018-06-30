import javax.swing.*;

/**
 * Класс, реализующий CoolString для последовательности чисел
 */
public class NumeratedString extends CoolString {
    public NumeratedString(int[] elements, int elementSize, int x, int y) {
        super(elements.length, elementSize, x, y);
        for(int i = 0; i < elements.length; ++i) {
            setText(i, String.valueOf(elements[i]));
//            setFontSize(i, getBasicFontSize()/(3-String.valueOf(elements[i]).length());
            setFontSize(i, 8);
        }
    }
    public NumeratedString(int[] elements, int elementSize) {
        this(elements, elementSize, 0, 0);
    }
}
