package CoolStrings;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Класс, реализующий CoolStrings.CoolString для последовательности чисел
 */
public class NumeratedString extends CoolString {
    /**
     * Конструктор для отображения чисел elements.
     * @param elements Массив чисел, из которых сформируется строка
     * @param elementSize Размер каждого элемента строки
     * @param panel Панель, на которой отображается строка
     * @param x Координата x строки
     * @param y Координата y строки
     */
    public NumeratedString(ArrayList<Integer> elements, int elementSize, JPanel panel, int x, int y) {
        super(elements.size(), elementSize, panel, x, y);
        for(int i = 0; i < elements.size(); ++i) {
            setText(i, String.valueOf(elements.get(i)));
            // TODO: Отображение чисел, состоящих из нескольких цифр
//            setFontSize(i, getBasicFontSize()/(3-String.valueOf(elements[i]).length());
            setFontSize(i, elementSize/2); // Для двузначных чисел достаточно
        }
    }

    /**
     * Конструктор для формирования строки из length элементов, хранящих последовательность чисел {0, 1, 2, ...}
     * @param length Длина строки
     * @param elementSize Размер каждого элемента строки
     * @param panel Панель, на которой отображается строка
     * @param x Координата x строки
     * @param y Координата y строки
     */
    public NumeratedString(int length, int elementSize, JPanel panel, int x, int y) {
        super(length, elementSize, panel, x, y);
        for (int i = 0; i < length; ++i) {
            setText(i, String.valueOf(i));
            setFontSize(i, elementSize/2);
        }
    }

    public NumeratedString(ArrayList<Integer> elements, int elementSize, JPanel panel) {
        this(elements, elementSize, panel, 0, 0);
    }
}
