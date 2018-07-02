package Visualizators;

import CoolStrings.LabeledString;
import CoolStrings.NumeratedString;

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
    private JLabel infoText;
    private JLabel infoPattern;
    private JLabel infoNumeration;
    private NumeratedString numeration;

    public NaiveVisualization(String text, String pattern, JPanel panel, JLabel answer) {
        super(text, pattern, panel);
        answer.setText("Answer: " + NaiveAlgorithm(text, pattern));
        stepsNumber = steps.size();
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
        labeledPattern = new LabeledString(pattern, 20, panel, 80, 60);
        infoPattern = new JLabel("Pattern:", SwingConstants.RIGHT);
        panel.add(infoPattern);
        infoPattern.setBounds(20,60,60,20);
    }

    private ArrayList<Integer> PrefixFunction(String line){
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(new Integer(0)); // Для первого символа значение префикс функции равно 0

        for(int i = 1 ; i < line.length() ; ++i){
            Integer k = result.get(i-1);
            while(k > 0 && line.charAt(i) != line.charAt(k)){
                k = result.get(k - 1);
            }
            if(line.charAt(i) == line.charAt(k))
                ++k;
            result.add(k);
        }

        return result;
    }

    private String NaiveAlgorithm(String text, String pattern) {
        int[] textColors = new int[text.length()]; // Хранит цвета символов текста
        int[] patternColors = new int[pattern.length()]; // Шаблона
        steps = new ArrayList<Step>();
        StringBuilder answer = new StringBuilder(); // Строка, хранящая результат работы алгоритма

        for (int i = 0; i <= text.length() - pattern.length(); ++i) { // Перебор всех возможных вариантов вхождения шаблона в строку
            if(i!=0) {
                for (int k = 0; k < textColors.length; ++k) { // Красим все символы текста в чёрный
                    textColors[k] = Color.BLACK.getRGB();
                }
                for (int k = 0; k < patternColors.length; ++k) { // Красим все символы шаблона в чёрный
                    patternColors[k] = Color.BLACK.getRGB();
                }
                steps.add(new Step(textColors, patternColors, i)); // Добавили изменения
            }

            int j=0;
            while(j<pattern.length() && pattern.charAt(j)==text.charAt(j+i)) { // Сравнение символов в соответствующих индексах
                textColors[j+i]= Color.GREEN.getRGB(); // Покрасили совпавший символ в тексте зелёным
                patternColors[j] = Color.GREEN.getRGB(); // Покрасили совпавший символ в шаблоне зелёным
                steps.add(new Step(textColors, patternColors, i)); // Добавили изменения
                j++;
            }

            if(j==pattern.length()){ // Если был найден индекс вхождения
                if(answer.length()==0)
                    answer.append(i);
                else
                    answer.append(", "+i);
            }
            else {
                textColors[j+i]= Color.RED.getRGB(); // Покрасили не совпавший символ в тексте красным
                patternColors[j] = Color.RED.getRGB(); // Покрасили не совпавший символ в шаблоне красным
                steps.add(new Step(textColors, patternColors, i)); // Добавили изменения
            }
        }

        if(answer.length()==0){
            answer.append("-1");
        }

        return answer.toString();
    }


    @Override
    public void clear() {
        labeledPattern.removeFromPanel(getPanel());
        labeledText.removeFromPanel(getPanel());
        numeration.removeFromPanel(getPanel());
        infoPattern.getParent().remove(infoPattern);
        infoNumeration.getParent().remove(infoNumeration);
        infoText.getParent().remove(infoText);
    }

    @Override
    public void visualize(int step) {
        if (step < 0)
            return;
        for (int i = 0; i < labeledText.getElementsNumber(); i++) {
            labeledText.setColor(steps.get(step).textColors[i], i);
        }
        for (int i = 0; i < labeledPattern.getElementsNumber(); i++) {
            labeledPattern.setColor(steps.get(step).patternColors[i], i);
        }
        labeledPattern.setX(labeledText.getX() + labeledText.getElementSize()*steps.get(step).getPatternPosition());
    }


    class Step {
        private final int[] textColors; // Цвета символов текста
        private final int[] patternColors; // Цвета символов шаблона
        private final int patternPosition; // Позиция шаблона относительно символов текста

        public Step(int[] textColors, int[] patternColors, int patternPosition) {
            this.textColors = textColors.clone();
            this.patternColors = patternColors.clone();
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
