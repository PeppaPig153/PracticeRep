import javax.swing.*;
import java.awt.*;

/**
 * Класс, описывающий абстрактный визуализатор
 */
public abstract class Visualizable {
    public abstract void visualize(int step);

    /**
     * Визуализирующая функция для визуализатора алгоритма поиска подстроки в строке
     * @param text - строка
     * @param pattern - подстрока
     */
    public Visualizable(String text, String pattern){
        this.text = text;
        this.pattern = pattern;
    }

    public int getStepsNumber() {
        return stepsNumber;
    }

    protected String text;
    protected String pattern;
    protected String answer;
    private int stepsNumber;
}
