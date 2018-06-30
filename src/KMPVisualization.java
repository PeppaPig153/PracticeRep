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
    //private ArrayList<Integer> prefix;
    // Строки из JLabel для отображения шагов:
    private LabeledString labeledText;
    private LabeledString labeledPattern;
    private NumeratedString numeratedString;

    enum MessageTypes {
        RES_FOUND,
        SHIFT_AFTER_RESULT,
        EQUAL_CHARS,
        NOT_EQUAL_CHARS,
        SHIFT_AFTER_COMPARE
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


        //нахождение префикс функции
        ArrayList<Integer> prefix = new ArrayList<Integer>();
        prefix.add(new Integer(0));
        for(int i = 1 ; i < text.length() ; ++i){
            Integer k = prefix.get(i-1);
            while(k > 0 && text.charAt(i) != text.charAt(k)){
                k = prefix.get(k - 1);
            }
            if(text.charAt(i) == text.charAt(k)) ++k;
            prefix.add(k);
        }

        //алгоритм КМП
        ArrayList<Integer> result = new ArrayList<Integer>();
        steps = new ArrayList<Step>();
        int k = 0;
        for (int i = 0; i < text.length(); ++i) {
            while (pattern.charAt(k) != text.charAt(i) && k > 0) {
                steps.add(new Step(i,k,0,0,MessageTypes.NOT_EQUAL_CHARS,i-k));
                steps.add(new Step(i,k,k-1,prefix.get(k - 1),MessageTypes.SHIFT_AFTER_COMPARE,i-prefix.get(k - 1)));
                k = prefix.get(k - 1);
            }
            steps.add(new Step(i,k,0,0,MessageTypes.EQUAL_CHARS,i-k));
            if (pattern.charAt(k) == text.charAt(i)) {
                k = k + 1;
                if (k == pattern.length()) {
                    steps.add(new Step(i,k,0,0,MessageTypes.RES_FOUND,i-k));
                    steps.add(new Step(i,k,k-1,prefix.get(k - 1),MessageTypes.SHIFT_AFTER_RESULT,i-prefix.get(k-1)));
                    result.add(i + 1 - k);
                    k = prefix.get(k - 1);

                }
            }
            else {
                k = 0;
            }
        }





        //Результат работы алгоритма
        StringBuilder builder = new StringBuilder();
        for (int item : result) {
            builder.append(item);
            builder.append(", ");
        }
        builder.setLength(builder.length() - 2);
        return builder.toString();



        /*
            TODO: Реализовать эту функцию, выполняющую алгоритм и сохраняющую шаги в массив Step'ов
            Пример добавления очередного шага: steps.add(new Step(textColoredSymbolIndex, patternColoredSymbolIndex,
            prefixSymbolFrom, prefixSymbolTo, MessageTypes.EXAMPLE, patternPosition));
        */
    }

    @Override
    public void visualize(int step) {

    }

    class Step {
        private int textColoredSymbolIndex;
        private int patternColoredSymbolIndex;
        private int color;
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
        public String getMessage(){
            switch (typeOfMessage){
                case RES_FOUND:
                    return "";
                case EQUAL_CHARS:
                    return "";
                case NOT_EQUAL_CHARS:
                    return "";
                case SHIFT_AFTER_COMPARE:
                    return "";
                case SHIFT_AFTER_RESULT:
                    return "";
                default:
                    return "";
            }
        }
        public String getColor(){
            switch (typeOfMessage) {
                case EQUAL_CHARS:
                    return "GREEN";
                case NOT_EQUAL_CHARS:
                    return "RED";
                default:
                    return "BLACK";
            }
        }
    }
}
