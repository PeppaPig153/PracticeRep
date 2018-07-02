package Visualizators;

import CoolStrings.LabeledString;
import CoolStrings.NumeratedString;
import GUIs.DrawingPanel;

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
    private DrawingPanel panel;
    private String text;
    private String pattern;
    private DrawingLayer drawingLayer;

    public KMPVisualization(String text, String pattern, DrawingPanel panel, JLabel answer) {
        super(text, pattern, panel);
        drawingLayer = new DrawingLayer();
        panel.setDrawingLayer(drawingLayer);
        this.panel = panel;
        this.text = text;
        this.pattern = pattern;
        answer.setText("Answer: " + KMPAlgorithm(text, pattern));
        stepsNumber = steps.size();
        // Нумерация символов текста:
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


        // Для отображения префикс-функции шаблона:
        // Нумерация:
        numerationPrefix = new NumeratedString(pattern.length(), 20, panel, 80, 100);
        // Шаблон:
        labeledPatternForPrefix = new LabeledString(pattern, 20, panel, 80, 120);
        // Значения префикс-функции:
        labeledPrefix = new NumeratedString(prefix, 20, panel, 80, 140);
        // Подпись:
        infoPrefix = new JLabel("Prefix:", SwingConstants.RIGHT);
        panel.add(infoPrefix);
        infoPrefix.setBounds(20,120,60,20);

        panel.setVisible(true);
    }

    private ArrayList<Integer> PrefixFunction(String line){
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(0); // Для первого символа значение префикс функции равно 0
        Step step;
        int[] textColors = new int[text.length()]; // Хранит цвета символов текста
        int[] patternColors = new int[pattern.length()]; // Шаблона
        int[] prefixColors = new int[pattern.length()]; // Хранит цвета символов префикс функции
        int[] prefixNumerationColors = new int[pattern.length()]; // Хранит цвета символов нумерации шаблона
        for(int i:textColors){
            i = Color.BLACK.getRGB();
        }
        for(int i:patternColors){
            i = Color.BLACK.getRGB();
        }
        for(int i:prefixColors){
            i = panel.getParent().getBackground().getRGB();
        }
        for(int i:prefixNumerationColors){
            i = Color.BLACK.getRGB();
        }
        prefixColors[0] = Color.RED.getRGB();
        step = new Step(textColors,patternColors,0,prefixColors,prefixNumerationColors);
        prefixColors[0] = Color.BLACK.getRGB();
        steps.add(step);
        for(int i = 1 ; i < line.length() ; ++i){

            Integer k = result.get(i-1);
            while(k > 0 && line.charAt(i) != line.charAt(k)){
                k = result.get(k - 1);
            }
            if(line.charAt(i) == line.charAt(k))
                ++k;
            result.add(k);
            prefixColors[i] = Color.RED.getRGB();
            step = new Step(textColors,patternColors,0,prefixColors,prefixNumerationColors);
            prefixColors[i] = Color.BLACK.getRGB();
            step.setPrefixIndex(i);
            step.setPrefixLength(k);
            steps.add(step);
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
                if(answer.length()==0)
                    answer.append(indexInText);
                else
                    answer.append(", "+indexInText);

                prefixColors[indexInPattern-1]=Color.MAGENTA.getRGB();
                indexInPattern=prefix.get(indexInPattern-1);
                indexInText+=pattern.length()-indexInPattern;
                prefixNumerationColors[indexInPattern]=Color.MAGENTA.getRGB();
            }
            else {
                textColors[indexInPattern+indexInText]= Color.RED.getRGB(); // Покрасили не совпавший символ в тексте красным
                patternColors[indexInPattern] = Color.RED.getRGB(); // Покрасили не совпавший символ в шаблоне красным
                steps.add(new Step(textColors, patternColors, indexInText,prefixColors,prefixNumerationColors)); // Добавили изменения

                if(indexInPattern==0)
                    indexInText++;
                else {
                    prefixColors[indexInPattern-1]=Color.MAGENTA.getRGB();
                    indexInText+=indexInPattern - prefix.get(indexInPattern - 1);
                    indexInPattern = prefix.get(indexInPattern - 1);
                    prefixNumerationColors[indexInPattern]=Color.MAGENTA.getRGB();
                }
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
        for(int i = 0; i < numerationPrefix.getElementsNumber(); ++i) {
            numerationPrefix.setColor(step.prefixNumerationColors[i], i);
        }
        for (int i = 0; i < labeledPrefix.getElementsNumber(); i++) {
            labeledPrefix.setColor(step.prefixColors[i], i);
        }
        labeledPattern.setX(labeledText.getX() + labeledText.getElementSize()*step.getPatternPosition());
        drawingLayer.setPrefixLength(step.getPrefixLength()); // Устанавливаем параметр
        drawingLayer.setPrefixIndex(step.getPrefixIndex());
        drawingLayer.repaint(); // Перерисовываем
    }

    class Step {
        private final int[] textColors; // Цвета символов текста
        private final int[] patternColors; // Цвета символов шаблона
        private final int[] prefixColors; // Цвета значений префикс-функции
        private final int[] prefixNumerationColors; // Цвета нумерации шаблона в префикс-функции
        private final int patternPosition; // Позиция шаблона относительно символов текста
        private int prefixLength;
        private int prefixIndex;

        public Step(int[] textColors, int[] patternColors, int patternPosition, int[] prefixColors, int[] prefixNumerationColors) {
            this.textColors = textColors.clone();
            this.patternColors = patternColors.clone();
            this.patternPosition = patternPosition;
            this.prefixColors=prefixColors.clone();
            this.prefixNumerationColors=prefixNumerationColors.clone();
            prefixIndex = 0;
            prefixIndex = 0;
        }

        public void setPrefixIndex(int prefixIndex) {
            this.prefixIndex = prefixIndex;
        }

        public void setPrefixLength(int prefixLength) {
            this.prefixLength = prefixLength;
        }

        public int getPrefixIndex() {
            return prefixIndex;
        }

        public int getPrefixLength() {
            return prefixLength;
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


    class DrawingLayer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(prefixLength != 0){
                g.setColor(Color.RED);
                g.drawRoundRect(80, 117, 40 + 20*prefixLength - 40, 25, 20, 15);
                g.setColor(Color.BLUE);
                g.drawRoundRect(80 + 20*(prefixIndex - prefixLength + 1), 117, 40 + 20*prefixLength - 40, 25, 20, 15);
                panel.getDrawingLayer().repaint();
            }
        }
        private int prefixLength;
        private int prefixIndex;

        public void setPrefixIndex(int prefixIndex) {
            this.prefixIndex = prefixIndex;
        }

        public void setPrefixLength(int prefixLength) {
            this.prefixLength = prefixLength;
        }
    }
}
