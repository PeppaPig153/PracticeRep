package Visualizators;

import CoolStrings.LabeledString;

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
        super(text, pattern, panel);
        answer.setText("Answer: " + NaiveAlgorithm(text, pattern));
        stepsNumber = steps.size();
        labeledText = new LabeledString(text, 20, panel, 20, 20);
        labeledPattern = new LabeledString(pattern, 20, panel, 20, 40);
    }

    private String NaiveAlgorithm(String text, String pattern) {
        int[] textColors = new int[text.length()]; // Хранит цвета символов текста
        int[] patternColors = new int[pattern.length()]; // Шаблона
        steps = new ArrayList<Step>();
        StringBuilder answer = new StringBuilder(); // Строка, хранящая результат работы алгоритма

        for (int i = 0; i <= text.length() - pattern.length(); ++i) { // Перебор всех возможных вариантов вхождения шаблона в строку
            for(int k=0; k<text.length(); ++k){ // Красим все символы текста в чёрный
                textColors[k]=Color.BLACK.getRGB();
            }
            for(int k=0; k<pattern.length(); ++k){ // Красим все символы шаблона в чёрный
                patternColors[k]=Color.BLACK.getRGB();
            }
            steps.add(new Step(textColors, patternColors, i)); // Добавили изменения

            int j=0;
            while(j<pattern.length() && pattern.charAt(j)==text.charAt(j)) { // Сравнение символов в соответствующих индексах
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
    }

    @Override
    public void visualize(int step) {
        if (step < 0)
            return;
        for (int i = 0; i < labeledText.getElementsNumber(); i++) {
            labeledText.setColor(steps.get(step).textColors[i], i);
        }
        for (int i = 0; i < labeledPattern.getElementsNumber(); i++) {
            labeledPattern.setColor(steps.get(step).textColors[i], i);
        }
        labeledPattern.setX(labeledText.getX()+steps.get(step).patternPosition*labeledText.getElementSize());
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
