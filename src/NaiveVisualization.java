import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Класс-визуализатор для наивного алгоритма
 */
public class NaiveVisualization extends Visualizable {
    // Поле для хранения информации для визуализации шагов
    private ArrayList<Step> steps;
    // Строки из JLabel для отображения шагов:
    private LabeledString labeledText;
    private LabeledString labeledPattern;

    public NaiveVisualization(String text, String pattern, JPanel panel, JLabel answer) {
        super(text, pattern);
        answer.setText("Answer: " + NaiveAlgorithm(text, pattern));
        labeledText = new LabeledString(text, 20, 20, 20);
        labeledPattern = new LabeledString(pattern, 20, 20, 40);
        labeledText.addToPanel(panel);
        labeledPattern.addToPanel(panel);
    }

    private String NaiveAlgorithm(String text, String pattern) {
        int[] textColors = new int[text.length()]; // Хранит цвета символов текста
        int[] patternColors = new int[pattern.length()]; // Шаблона
        int patternPosition; // Под каким символом начинается шаблон(это можно удалить, если не нужно)
        String answer = new String(); // Строка, хранящая результат работы алгоритма

        /*
            TODO: Реализовать эту функцию, выполняющую алгоритм и сохраняющую шаги в массив Step'ов
            Пример добавления очередного шага: steps.add(new Step(textColors, patternColors, patternPosition));
        */


        return answer;
    }

    @Override
    public void visualize(int step) {
        // TODO
    }


    class Step {
        private final int[] textColors; // Цвета символов текста
        private final int[] patternColors; // Цвета символов шаблона
        private final int patternPosition; // Позиция шаблона относительно символов текста

        public Step(int[] textColors, int[] patternColors, int patternPosition) {
            this.textColors = textColors;
            this.patternColors = patternColors;
            this.patternPosition = patternPosition;
        }

        public int[] getTextColors() {
            return textColors;
        }

        public int[] getPatternColors() {
            return patternColors;
        }

        public int getPatternPosition() {
            return patternPosition;
        }
    }
}
