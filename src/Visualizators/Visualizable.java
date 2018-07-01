package Visualizators;

import javax.swing.*;
import java.awt.*;

/**
 * Класс, описывающий абстрактный визуализатор
 */
public abstract class Visualizable {
    public abstract void visualize(int step);

    public abstract void clear();
    /**
     * Визуализирующая функция для визуализатора алгоритма поиска подстроки в строке
     * @param text Строка
     * @param pattern Подстрока
     * @param panel Панель, на которой происходит визуализация
     */
    public Visualizable(String text, String pattern, JPanel panel){
        this.text = text;
        this.pattern = pattern;
        this.panel = panel;
    }
    public int getStepsNumber() {
        return stepsNumber;
    }

    public JPanel getPanel() {
        return panel;
    }

    protected String text;
    protected String pattern;
    protected String answer;
    protected int stepsNumber;

    private final JPanel panel;
}
