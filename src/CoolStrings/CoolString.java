package CoolStrings;

import javax.swing.*;
import java.awt.*;

/**
 * Интерфейс, описывающий строку из JLabel с одинаковым отступом между символами и возможностью изменения цветов конкретных элементов.
 */
public abstract class CoolString {
    public CoolString(int elementsNumber, int elementSize, JPanel panel, int x, int y) {
        this.x = x;
        this.y = y;
        this.basicFontSize = elementSize;
        this.elementSize = elementSize;
        labels = new JLabel[elementsNumber];
        for (int i = 0; i < elementsNumber; ++i) {
            labels[i] = new JLabel();
            panel.add(labels[i]);
        }

        Font f = new Font("Arial", 1, elementSize);//установка шрифта
        for (int i = 0; i < elementsNumber; ++i) {
            labels[i].setFont(f);
            labels[i].setBounds(elementSize * i + x, y, elementSize, elementSize);//задаём гранцы и нужное смещение
            labels[i].setHorizontalAlignment(JLabel.CENTER); //размещаем символ в центре JLabel
        }
    }

    /**
     * Добавляет элемент в панель panel
     * @param panel Панель, в которую добавляется элемент
     */
    public void addToPanel(JPanel panel) {
        for(int i = 0; i < labels.length; ++i)
            panel.add(labels[i]);
    }

    /**
     * Удаляет элемент из панели panel
     * @param panel Панель, из которой удаляется элемент
     */
    public void removeFromPanel(JPanel panel) {
        for(int i = 0; i < labels.length; ++i)
            panel.remove(labels[i]);
    }

    /**
     * Устанавливает цвет элементов [begin, end) строки в color
     * @param color Устанавливаемый цвет для символов в формате RGB
     * @param begin Левая граница символов, у которых изменяется цвет
     * @param end Правая граница символов, у которых изменяется цвет
     * @throws IndexOutOfBoundsException В случае, если begin или end выходит за пределы массива из элементов
     */
    public void setColor(int color, int begin, int end) throws IndexOutOfBoundsException{
        if (begin < 0 || begin > labels.length-1 || end < 1 || end > labels.length) {
            throw new IndexOutOfBoundsException("Index " + begin + " " + end +
                    " out of bounds in setColor of CoolStrings.LabeledString: ");
        }
        for (int i = begin; i < end; ++i) {
            labels[i].setForeground(new Color(color));
        }
    }

    /**
     * Устанавливает цвет символов [begin, end) строки в color
     * @param color Устанавливаемый цвет для символа в формате RGB
     * @param index Индекс символа, цвет которого меняется
     * @throws IndexOutOfBoundsException В случае, если index выходит за пределы массива из элементов
     */
    public void setColor(int color, int index) throws IndexOutOfBoundsException{
        if (index < 0 || index > labels.length-1) {
            throw new IndexOutOfBoundsException("Index " + index +
                    " out of bounds in setColor of CoolStrings.LabeledString: ");
        }
        labels[index].setForeground(new Color(color));
    }

    /**
     * Функция, возвращающая цвет символа в формате RGB по индексу index.
     * @param index Индекс символа, цвет которого нужно получить
     * @return Цвет символа в формате RGB с индексом index, или null, если на этом индексе нет символов(индекс выходит за пределы строки)
     */
    public int getColor(int index) {
        if (index < 0 || index > labels.length) {
            throw new IndexOutOfBoundsException("Index " + index +
                    " out of bounds in getColor of CoolStrings.LabeledString: ");
        }
        return labels[index].getForeground().getRGB();
    }

    /**
     * Устанавливает позицию(левый верхний угол) строки на (x, y) в пикселях относительно компонента, в котором строка находится
     * @param x Координата x
     * @param y Координата y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(elementSize * i + x, y, elementSize, elementSize);//задаём гранцы и нужное смещение
        }
    }
    /**
     * Устанавлиает координату x строки
     * @param x Координата x
     */
    public void setX(int x) {
        this.x = x;
        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(elementSize * i + x, y, elementSize, elementSize);//задаём гранцы и нужное смещение
        }

    }
    /**
     * Устанавливает координату y строки
     * @param y Координата y
     */
    public void setY(int y) {
        this.y = y;
        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(elementSize * i + x, y, elementSize, elementSize);//задаём гранцы и нужное смещение
        }

    }
    /**
     * Возвращает координату x строки
     * @return Координата x
     */
    public int getX() {
        return x;
    }
    /**
     * Возвращает координату y строки
     * @return Координата y
     */
    public int getY() {
        return y;
    }

    /**
     * Возвращает размер одного элемента в пикселях
     * @return Размер одного элемента в пикселях
     */
    public int getElementSize() {
        return elementSize;
    }

    /**
     * Возвращает количество элементов в строке
     * @return Количество элементов в строке
     */
    public int getElementsNumber() {
        return labels.length;
    }

    protected void setText(int index, String text) {
        labels[index].setText(text);
    }

    protected int getBasicFontSize() {
        return basicFontSize;
    }

    protected void setFontSize(int index, int fontSize) {
        labels[index].setFont(new Font("Arial", 1, fontSize));
    }

    private int basicFontSize;
    private int elementSize;
    private int x;
    private int y;
    private JLabel[] labels;
}
