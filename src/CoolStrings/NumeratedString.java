package CoolStrings;

import GUIs.DrawingPanel;
import java.util.ArrayList;

/**
 * Класс, реализующий интерфейс CoolString для последовательности чисел
 */
public class NumeratedString extends CoolString {
    /**
     * Конструктор для формирования строки из JLabel, содержащей числа elements
     * @param elements Массив чисел, из которых сформируется строка
     * @param elementSize Размер каждого элемента строки
     * @param panel Панель, на которой отображается строка
     * @param x Координата x строки
     * @param y Координата y строки
     */
    public NumeratedString(ArrayList<Integer> elements, int elementSize, DrawingPanel panel, int x, int y) {
        super(elements.size(), elementSize, panel, x, y);
        for(int i = 0; i < elements.size(); ++i) {
            setText(i, String.valueOf(elements.get(i)));
            // TODO: Отображение чисел, состоящих из нескольких цифр
//            setFontSize(i, getBasicFontSize()/(3-String.valueOf(elements[i]).length());
            setFontSize(i, elementSize/2); // Для двузначных чисел достаточно
        }
    }

    /**
     * Конструктор для формирования строки из JLabel, содержащей числа {0, 1, 2, ...}
     * @param length Длина строки
     * @param elementSize Размер каждого элемента строки
     * @param panel Панель, на которой отображается строка
     * @param x Координата x строки
     * @param y Координата y строки
     */
    public NumeratedString(int length, int elementSize, DrawingPanel panel, int x, int y) {
        super(length, elementSize, panel, x, y);
        for (int i = 0; i < length; ++i) {
            setText(i, String.valueOf(i));
            setFontSize(i, elementSize/2);
        }
    }


    /**
     * Конструктор для формирования строки из JLabel, содержащей числа elements. Позиция устанавливается в (0, 0)
     * @param elements Массив чисел, из которых сформируется строка
     * @param elementSize Размер каждого элемента строки
     * @param panel Панель, на которой отображается строка
     */
    public NumeratedString(ArrayList<Integer> elements, int elementSize, DrawingPanel panel) {
        this(elements, elementSize, panel, 0, 0);
    }
}
