package Visualizators;

import GUIs.DrawingPanel;

/**
 * Класс, описывающий абстрактный визуализатор
 */
public abstract class Visualizable {
    /**
     * Функция, отображающая step шаг визуализации алгоритма
     * @param step Шаг алгоритма, который необходимо отобразить
     */
    public abstract void visualize(int step);

    /**
     * Функция, очищающая панель от помещенных на нее ранее функцией visualize(int) компонентов
     */
    public abstract void clear();

    /**
     * Конструктор для визуализатора алгоритма поиска подстроки в строке
     * @param text Строка
     * @param pattern Подстрока
     * @param panel Панель, на которой происходит визуализация
     */
    public Visualizable(String text, String pattern, DrawingPanel panel){
        this.text = text;
        this.pattern = pattern;
        this.panel = panel;
    }

    /**
     * Функция, возвращающая суммарное количество шагов алгоритма
     * @return Суммарное количиество шагов алгоритма
     */
    public int getStepsNumber() {
        return stepsNumber;
    }

    /**
     * Функция, возвращающая панель, на которой происходит отображение визуализации
     * @return Панель, на которой происходит отображение визуализации
     */
    public DrawingPanel getPanel() {
        return panel;
    }

    protected String text; // Текст для алгоритма
    protected String pattern; // Шаблон для алгоритма
    protected String answer; // Ответ алгоритма
    protected int stepsNumber; // Суммарное количество шагов алгоритма

    private final DrawingPanel panel; // Панель для визуализации
}
