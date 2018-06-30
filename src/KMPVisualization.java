import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Класс-визуализатор для алгоритма КМП
 */
public class KMPVisualization extends Visualizable {
    // Поле для хранения информации для визуализации шагов
    private ArrayList<Step> steps;
    // Результат вычислений префикс-функции:
    private ArrayList<Integer> prefix;
    // Строки из JLabel для отображения шагов:
    private LabeledString labeledText;
    private LabeledString labeledPattern;
    private NumeratedString numeratedString;

    enum MessageTypes {
        INFO,
        EXAMPLE
    };

    public KMPVisualization(String text, String pattern, JPanel panel, JLabel answer/*, JLabel info?? */) {
        super(text, pattern);
        answer.setText("Answer: " + KMPAlgorithm(text, pattern));
        labeledText = new LabeledString(text, 20, 20, 20);
        labeledPattern = new LabeledString(pattern, 20, 20, 40);
        labeledText.addToPanel(panel);
        labeledPattern.addToPanel(panel);
    }

    private String KMPAlgorithm(String text, String pattern) {
        int patternPosition; // Под каким символом начинается шаблон(это можно удалить, если не нужно)
        String answer = new String(); // Строка, хранящая результат работы алгоритма

        /*
            TODO: Реализовать эту функцию, выполняющую алгоритм и сохраняющую шаги в массив Step'ов
            Пример добавления очередного шага: steps.add(new Step(textColoredSymbolIndex, patternColoredSymbolIndex,
            prefixSymbolFrom, prefixSymbolTo, MessageTypes.EXAMPLE, patternPosition));
        */

        return answer;
    }

    @Override
    public void visualize(int step) {

    }

    class Step {
        private int textColoredSymbolIndex;
        private int patternColoredSymbolIndex;
        private int prefixSymbolFrom;
        private int prefixSymbolTo;
        private MessageTypes typeOfMessage;
        private int patternPosition;

        public Step(int textColoredSymbolIndex, int patternColoredSymbolIndex, int prefixSymbolFrom,
                    int prefixSymbolTo, MessageTypes typeOfMessage, int patternPosition) {
            this.textColoredSymbolIndex = textColoredSymbolIndex;
            this.patternColoredSymbolIndex = patternColoredSymbolIndex;
            this.prefixSymbolFrom = prefixSymbolFrom;
            this.prefixSymbolTo = prefixSymbolTo;
            this.typeOfMessage = typeOfMessage;
            this.patternPosition = patternPosition;
        }
    }
}
