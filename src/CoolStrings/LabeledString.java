package CoolStrings;

import GUIs.DrawingPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Класс, реализующий интерфейс CoolString для символьных строк
 */
public class LabeledString extends CoolString {
    /**
     * Конструктор для формирования строки из JLabel, содержащей символы строки string
     * @param string Строка символов, из которых сформируется строка JLabel'ов
     * @param elementSize Размер каждого элемента строки
     * @param panel Панель, на которой отображается строка
     * @param x Координата x строки
     * @param y Координата y строки
     */
    public LabeledString(String string, int elementSize, DrawingPanel panel, int x, int y) {
        super(string.length(), elementSize, panel, x, y);
        Font f = new Font("Arial", 1, elementSize);//установка шрифта
        for (int i = 0; i < string.length(); ++i) {
            setText(i, String.valueOf(string.charAt(i)));
        }
    }

    /**
     * Конструктор для формирования строки из JLabel, содержащей символы строки string. Позиция сформированной строки устанавливается в (0, 0).
     * @param string Строка символов, из которых сформируется строка JLabel'ов
     * @param elementSize Размер каждого элемента строки
     * @param panel Панель, на которой отображается строка
     */
    public LabeledString(String string, int elementSize, DrawingPanel panel) {
        this(string, elementSize, panel, 0, 0);
    }

}


