package Visualizators;

import CoolStrings.LabeledString;
import CoolStrings.NumeratedString;

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
    private NumeratedString labeledPrefix;
    private NumeratedString numeration;
    private JLabel infoMessage;
    private JLabel infoText;
    private JLabel infoPattern;
    private JLabel infoNumeration;
    private JLabel infoPrefix;


    enum MessageTypes {
        RES_FOUND,
        SHIFT_AFTER_RESULT,
        EQUAL_CHARS,
        NOT_EQUAL_CHARS,
        SHIFT_AFTER_COMPARE
    };

    public KMPVisualization(String text, String pattern, JPanel panel, JLabel answer) {
        super(text, pattern, panel);
        answer.setText("Answer: " + KMPAlgorithm(text, pattern));
        // Нумерация символов строки:
        numeration = new NumeratedString(text.length(), 20, panel, 80, 20);
        infoNumeration = new JLabel("i:", SwingConstants.RIGHT);
        panel.add(infoNumeration);
        infoNumeration.setBounds(20,20,60,20);
        // Строка и шаблон:
        labeledText = new LabeledString(text, 20, panel, 80, 40);
        infoText = new JLabel("Text:", SwingConstants.RIGHT);
        panel.add(infoText);
        infoText.setBounds(20,40,60,20);
        labeledPattern = new LabeledString(pattern, 20, panel, 80, 80);
        infoPattern = new JLabel("Pattern:", SwingConstants.RIGHT);
        panel.add(infoPattern);
        infoPattern.setBounds(20,80,60,20);
        // Значения префикс-функции:
        labeledPrefix = new NumeratedString(prefix, 20, panel, 80, 60);
        infoPrefix = new JLabel("Prefix:", SwingConstants.RIGHT);
        panel.add(infoPrefix);
        infoPrefix.setBounds(20,60,60,20);

        infoMessage = new JLabel("");
        panel.add(infoMessage);
        infoMessage.setBounds(20, 100, 500, 40);
        infoMessage.setAutoscrolls(true);
        stepsNumber = steps.size();
    }

    private String KMPAlgorithm(String text, String pattern) {


        //нахождение префикс функции
        prefix = new ArrayList<Integer>();
        prefix.add(0);
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
                    steps.add(new Step(i,k-1,0,0,MessageTypes.RES_FOUND,i-k+1));
                    steps.add(new Step(i,k-1,k-1,prefix.get(k - 1),MessageTypes.SHIFT_AFTER_RESULT,i-prefix.get(k-1)));
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
        if (!result.isEmpty())
            builder.delete(builder.length()-2, builder.length());
        return builder.toString();
        
    }

    @Override
    public void clear() {
        labeledPattern.removeFromPanel(getPanel());
        labeledText.removeFromPanel(getPanel());
        numeration.removeFromPanel(getPanel());
        labeledPrefix.removeFromPanel(getPanel());
        infoMessage.getParent().remove(infoMessage);
        infoPrefix.getParent().remove(infoPrefix);
        infoPattern.getParent().remove(infoPattern);
        infoNumeration.getParent().remove(infoNumeration);
        infoText.getParent().remove(infoText);
    }

    @Override
    public void visualize(int step) {
    if (step < 0)
        return;
//        labeledText.setX(labeledPattern.getX() - labeledText.getElementSize()*steps.get(step).getPatternPosition());
        labeledPattern.setX(labeledText.getX() + labeledText.getElementSize()*steps.get(step).getPatternPosition());
        // Красим строки полностью в черный:
        labeledPattern.setColor(Color.BLACK.getRGB(), 0, labeledPattern.getElementsNumber());
        labeledText.setColor(Color.BLACK.getRGB(), 0, labeledText.getElementsNumber());
        // Красим необходимые символы в нужный цвет:
        labeledText.setColor(steps.get(step).getColor().getRGB(), steps.get(step).getTextColoredSymbolIndex());
        labeledPattern.setColor(steps.get(step).getColor().getRGB(), steps.get(step).getPatternColoredSymbolIndex());
        // Устанавливаем сообщение для шага:
        infoMessage.setText(steps.get(step).getMessage());
        System.out.println(infoMessage.getText());
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
                    return "Все символы совпадают, индекс найденной подстроки " + Integer.toString(textColoredSymbolIndex - patternColoredSymbolIndex);
                case EQUAL_CHARS:
                    return "Символы совпадают, переходим к следующим";
                case NOT_EQUAL_CHARS:
                    return "Символы не совпадают, двигаем строку ";
                case SHIFT_AFTER_COMPARE:
                    return "Следующий символ, который будет сравниваться из шаблона – с номером " + Integer.toString(prefixSymbolTo);
                case SHIFT_AFTER_RESULT:
                    return "Следующий символ, который будет сравниваться из шаблона – с номером " + Integer.toString(prefixSymbolTo);
                default:
                    return "";
            }
        }

        public Color getColor(){
            switch (typeOfMessage) {
                case EQUAL_CHARS:
                    return Color.GREEN;
                case NOT_EQUAL_CHARS:
                    return Color.RED;
                default:
                    return Color.BLACK;

            }
        }

        public int getTextColoredSymbolIndex() {
            return textColoredSymbolIndex;
        }

        public int getPatternColoredSymbolIndex() {
            return patternColoredSymbolIndex;
        }

        public int getPrefixSymbolFrom() {
            return prefixSymbolFrom;
        }

        public int getPrefixSymbolTo() {
            return prefixSymbolTo;
        }

        public int getPatternPosition() {
            return patternPosition;
        }
    }
}
