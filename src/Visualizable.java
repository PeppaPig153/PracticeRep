import java.awt.*;

/**
 * Класс, описывающий абстрактный визуализатор
 */
public abstract class Visualizable {
    public abstract void visualize(Graphics2D g2d);

    /**
     * Визуализирующая функция для визуализатора алгоритма поиска подстроки в строке
     * @param text - строка
     * @param pattern - подстрока
     */
    public Visualizable(String text, String pattern){
        this.text = text;
        this.pattern = pattern;
    }

    protected String text;
    protected String pattern;
}
