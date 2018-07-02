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
    private LabeledString labeledPatternForPrefix;//
    private NumeratedString labeledPrefix;///
    private NumeratedString numeration;
    private NumeratedString numerationPrefix;//
    private JLabel infoText;
    private JLabel infoPattern;
    private JLabel infoNumeration;
    private JLabel infoPrefix;

    public KMPVisualization(String text, String pattern, JPanel panel, JLabel answer) {
        super(text, pattern, panel);
        answer.setText("Answer: " + KMPAlgorithm(text, pattern));
        stepsNumber = steps.size();
        // Нумерация символов строки:
        numeration = new NumeratedString(text.length(), 20, panel, 80, 20);
        numerationPrefix = new NumeratedString(pattern.length(), 20, panel, 80, 160);
        infoNumeration = new JLabel("i:", SwingConstants.RIGHT);
        panel.add(infoNumeration);
        infoNumeration.setBounds(20,20,60,20);
        // Строка и шаблон:
        labeledText = new LabeledString(text, 20, panel, 80, 40);
        infoText = new JLabel("Text:", SwingConstants.RIGHT);
        panel.add(infoText);
        infoText.setBounds(20,40,60,20);
        labeledPattern = new LabeledString(pattern, 20, panel, 80, 60);
        labeledPatternForPrefix = new LabeledString(pattern, 20, panel, 80, 180);
        infoPattern = new JLabel("Pattern:", SwingConstants.RIGHT);
        panel.add(infoPattern);
        infoPattern.setBounds(20,60,60,20);
        // Значения префикс-функции:
        labeledPrefix = new NumeratedString(prefix, 20, panel, 80, 200);
        infoPrefix = new JLabel("Prefix:", SwingConstants.RIGHT);
        panel.add(infoPrefix);
        infoPrefix.setBounds(20,150,60,20);
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

    private String KMPAlgorithm(String text, String pattern) {
        StringBuilder answer = new StringBuilder();
        steps = new ArrayList<Step>();
        int[] textColors = new int[text.length()]; // Хранит цвета символов текста
        int[] patternColors = new int[pattern.length()]; // Шаблона
        int[] prefixColors = new int[pattern.length()]; // Хранит цвета символов префикс функции
        int[] prefixNumerationColors = new int[pattern.length()]; // Хранит цвета символов нумерации шаблона
        int indexInText =0; //индекс приложения шаблона к тексту
        int indexInPattern=0; //текущий обрабатываемый символ в тексте

        prefix = PrefixFunction(pattern); // Нахождение префикс функции

        for(int k=0; k<textColors.length; ++k){ // Красим все символы текста в чёрный
            textColors[k]=Color.BLACK.getRGB();
        }
        for(int k=0; k<patternColors.length; ++k){ // Красим все символы шаблона, нумерацию шаблона и значений префикс функции в чёрный
            patternColors[k]=Color.BLACK.getRGB();
            prefixColors[k]=Color.BLACK.getRGB();
            prefixNumerationColors[k]=Color.BLACK.getRGB();
        }

        while(indexInText<=text.length()-pattern.length()){ // Пока можно прикладывать
            if(indexInText!=0) {
                for (int k = 0; k < textColors.length; ++k) { // Красим все символы текста в чёрный
                    if (k < indexInText || k >= indexInPattern + indexInText || indexInPattern == 0)
                        textColors[k] = Color.BLACK.getRGB();
                }
                for (int k = indexInPattern; k < patternColors.length; ++k) { // Красим символы текущего индекса и до конца в чёрный шаблона в чёрный
                    patternColors[k] = Color.BLACK.getRGB();
                }
                steps.add(new Step(textColors, patternColors, indexInText,prefixColors,prefixNumerationColors)); // Добавили изменения
            }

            for (int k = 0; k < prefixColors.length; ++k) { // Красим значения префикс функции и индексы в чёрный
                prefixColors[k] = Color.BLACK.getRGB();
                prefixNumerationColors[k]=Color.BLACK.getRGB();
            }

            while(indexInPattern<pattern.length() && pattern.charAt(indexInPattern)==text.charAt(indexInPattern+indexInText)) { // Сравнение символов в соответствующих индексах
                textColors[indexInPattern+indexInText]= Color.GREEN.getRGB(); // Покрасили совпавший символ в тексте зелёным
                patternColors[indexInPattern] = Color.GREEN.getRGB(); // Покрасили совпавший символ в шаблоне зелёным
                steps.add(new Step(textColors, patternColors, indexInText,prefixColors,prefixNumerationColors)); // Добавили изменения
                indexInPattern++;
            }

            if(indexInPattern==pattern.length()){ // Если был найден индекс вхождения
                prefixColors[indexInPattern-1]=Color.BLUE.getRGB();
                indexInPattern=prefix.get(indexInPattern-1);
                indexInText+=pattern.length()-indexInPattern;
                prefixNumerationColors[indexInPattern]=Color.BLUE.getRGB();
                if(answer.length()==0)
                    answer.append(indexInText);
                else
                    answer.append(", "+indexInText);
            }
            else {
                textColors[indexInPattern+indexInText]= Color.RED.getRGB(); // Покрасили не совпавший символ в тексте красным
                patternColors[indexInPattern] = Color.RED.getRGB(); // Покрасили не совпавший символ в шаблоне красным
                steps.add(new Step(textColors, patternColors, indexInText,prefixColors,prefixNumerationColors)); // Добавили изменения

                if(indexInPattern==0)
                    indexInText++;
                else {
                    prefixColors[indexInPattern-1]=Color.BLUE.getRGB();
                    indexInText+=indexInPattern - prefix.get(indexInPattern - 1);
                    indexInPattern = prefix.get(indexInPattern - 1);
                    prefixNumerationColors[indexInPattern]=Color.BLUE.getRGB();
                }
            }
        }

        return answer.toString();
    }

    @Override
    public void clear() {
        labeledPattern.removeFromPanel(getPanel());
        labeledPatternForPrefix.removeFromPanel(getPanel());
        labeledText.removeFromPanel(getPanel());
        numeration.removeFromPanel(getPanel());
        numerationPrefix.removeFromPanel(getPanel());
        labeledPrefix.removeFromPanel(getPanel());
        infoPrefix.getParent().remove(infoPrefix);
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
        private final int[] prefixColors; // Цвета значений префикс-функции
        private final int[] prefixNumerationColors; // Цвета нумерации шаблона в префикс-функции
        private final int patternPosition; // Позиция шаблона относительно символов текста

        public Step(int[] textColors, int[] patternColors, int patternPosition, int[] prefixColors, int[] prefixNumerationColors) {
            this.textColors = textColors.clone();
            this.patternColors = patternColors.clone();
            this.patternPosition = patternPosition;
            this.prefixColors=prefixColors;
            this.prefixNumerationColors=prefixNumerationColors;
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
