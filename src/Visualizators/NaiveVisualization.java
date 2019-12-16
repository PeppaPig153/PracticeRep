package Visualizators;

import CoolStrings.LabeledString;
import CoolStrings.NumeratedString;
import GUIs.DrawingPanel;

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
    private LabeledString labeledText; // Для отражения текста
    private LabeledString labeledPattern; // Для отражения шаблона
    private JLabel infoText; // Для отражения "Text:"
    private JLabel infoPattern; // Для отражения "Pattern:"
    private JLabel infoNumeration; // Для отражения "Index:"
    private NumeratedString numeration; // Для отражения индексов текста

    public NaiveVisualization(String text, String pattern, DrawingPanel panel, JLabel answer) {
        super(text, pattern, panel);
        answer.setText("Answer: " + NaiveAlgorithm(text, pattern));
        stepsNumber = steps.size();
        // Нумерация символов строки:
        numeration = new NumeratedString(text.length(), 20, panel, 80, 20);
        infoNumeration = new JLabel("Index:", SwingConstants.RIGHT);
        panel.add(infoNumeration);
        infoNumeration.setBounds(10,20,60,20);
        // Строка и шаблон:
        labeledText = new LabeledString(text, 20, panel, 80, 40);
        infoText = new JLabel("Text:", SwingConstants.RIGHT);
        panel.add(infoText);
        infoText.setBounds(10,40,60,20);
        labeledPattern = new LabeledString(pattern, 20, panel, 80, 60);
        infoPattern = new JLabel("Pattern:", SwingConstants.RIGHT);
        panel.add(infoPattern);
        infoPattern.setBounds(10,60,60,20);
    }

    private String NaiveAlgorithm(String text, String pattern) {
        int[] textColors = new int[text.length()]; // Хранит цвета символов текста
        int[] patternColors = new int[pattern.length()]; // Шаблона
        int[] numerationColors = new int[text.length()]; // нумерации текста
        steps = new ArrayList<>();
        StringBuilder answer = new StringBuilder(); // Строка, хранящая результат работы алгоритма

        for(int k=0; k<numerationColors.length; ++k)
            numerationColors[k] = Color.BLACK.getRGB();

        for (int i = 0; i <= text.length() - pattern.length(); ++i) { // Перебор всех возможных вариантов вхождения шаблона в строку
            if(i!=0) {
                for (int k = 0; k < textColors.length; ++k) { // Красим все символы текста в чёрный
                    textColors[k] = Color.BLACK.getRGB();
                }
                for (int k = 0; k < patternColors.length; ++k) { // Красим все символы шаблона в чёрный
                    patternColors[k] = Color.BLACK.getRGB();
                }
                steps.add(new Step(textColors, patternColors, i, numerationColors)); // Добавили изменения
            }

            int j=0;
            while(j<pattern.length() && pattern.charAt(j)==text.charAt(j+i)) { // Сравнение символов в соответствующих индексах
                textColors[j+i]= Color.GREEN.getRGB(); // Покрасили совпавший символ в тексте зелёным
                patternColors[j] = Color.GREEN.getRGB(); // Покрасили совпавший символ в шаблоне зелёным
                steps.add(new Step(textColors, patternColors, i, numerationColors)); // Добавили изменения
                j++;
            }

            if(j==pattern.length()){ // Если был найден индекс вхождения, добавляем его в ответ
                if(answer.length()==0)
                    answer.append(i);
                else
                    answer.append(", ").append(i);
                numerationColors[i]=Color.MAGENTA.getRGB(); // Красим найденный индекс в малиновый цвет
                if(i+pattern.length()==text.length())
                    steps.add(new Step(textColors, patternColors, i, numerationColors)); // Добавили изменения
            }
            else {
                textColors[j+i]= Color.RED.getRGB(); // Покрасили не совпавший символ в тексте красным
                patternColors[j] = Color.RED.getRGB(); // Покрасили не совпавший символ в шаблоне красным
                steps.add(new Step(textColors, patternColors, i, numerationColors)); // Добавили изменения
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
    public void visualize(int stepNumber) {
        if (stepNumber < 0)
            return;
        Step step = steps.get(stepNumber);
        for (int i = 0; i < labeledText.getElementsNumber(); i++) {
            labeledText.setColor(step.textColors[i], i);
        }
        for (int i = 0; i < labeledPattern.getElementsNumber(); i++) {
            labeledPattern.setColor(step.patternColors[i], i);
        }
        for(int i = 0; i < numeration.getElementsNumber(); ++i) {
            numeration.setColor(step.numerationColors[i], i);
        }
        labeledPattern.setX(labeledText.getX() + labeledText.getElementSize()*step.patternPosition);
    }

    class Step {
        final int[] textColors; // Цвета символов текста
        final int[] patternColors; // Цвета символов шаблона
        final int[] numerationColors; // Цвета индексов текста
        final int patternPosition; // Позиция шаблона относительно символов текста

        Step(int[] textColors, int[] patternColors, int patternPosition, int[] numerationColors) {
            this.textColors = textColors.clone();
            this.patternColors = patternColors.clone();
            this.numerationColors = numerationColors.clone();
            this.patternPosition = patternPosition;
        }
    }
}
