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
    private ArrayList<Step> steps; // Поле для хранения информации для визуализации шагов
    private ArrayList<Integer> prefix; // Результат вычислений префикс-функции:
    // Строки из JLabel для отображения шагов:
    private LabeledString labeledText; // Для отображения текста
    private LabeledString labeledPattern; // Для отображения шаблона
    private LabeledString labeledPatternForPrefix; // Для отображения шаблона в префикс функции
    private NumeratedString labeledPrefix; // Для отображения значений префикс функции
    private NumeratedString numeration; // Для отображения индексов текста
    private NumeratedString numerationPrefix; // Для отображения индексов шаблона в префикс функции
    private JLabel infoText; // Для отображения "Text:"
    private JLabel infoPattern; // Для отображения "Pattern:"
    private JLabel infoNumeration; // Для отображения "Index:"
    private JLabel infoPrefix; // Для отображения "Prefix Function:"
    private JLabel infoPatternForPrefix; // Для отображения "Pattern:" в префикс функции
    private JLabel infoNumerationForPrefix; // Для отображения "Index:" в префикс функции
    private JLabel infoPi; // Для отображения "π:" в префикс функции
    private DrawingPanel panel; // Панель на которой происходит отображение
    private String text; // Текст
    private String pattern; // Шаблон
    private DrawingLayer drawingLayer; // Слой для прорисовки овалов

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


        // Для отображения префикс-функции шаблона:
        // Нумерация:
        numerationPrefix = new NumeratedString(pattern.length(), 20, panel, 80, 150);
        // Шаблон:
        labeledPatternForPrefix = new LabeledString(pattern, 20, panel, 80, 170);
        // Значения префикс-функции:
        labeledPrefix = new NumeratedString(prefix, 20, panel, 80, 190);
        // Подписи:
        infoPrefix = new JLabel("Prefix Function:", SwingConstants.RIGHT);
        panel.add(infoPrefix);
        infoPrefix.setBounds(20,120,90,20);
        infoPatternForPrefix = new JLabel("Pattern:", SwingConstants.RIGHT);
        panel.add(infoPatternForPrefix);
        infoPatternForPrefix.setBounds(10,170,60,20);
        infoNumerationForPrefix = new JLabel("Index:", SwingConstants.RIGHT);
        panel.add(infoNumerationForPrefix);
        infoNumerationForPrefix.setBounds(10,150,60,20);
        infoPi = new JLabel("π:", SwingConstants.RIGHT);
        panel.add(infoPi);
        infoPi.setBounds(10,190,60,20);
        panel.setVisible(true);
    }

    private ArrayList<Integer> PrefixFunction(String line){ // Вычисление префикс функции
        ArrayList<Integer> result = new ArrayList<>();
        int[] numerationColors = new int[text.length()]; // нумерации текста
        for(int k=0; k<numerationColors.length; ++k)
            numerationColors[k] = Color.BLACK.getRGB();
        result.add(0); // Для первого символа значение префикс функции равно 0
        Step step;
        int[] textColors = new int[text.length()]; // Хранит цвета символов текста
        int[] patternColors = new int[pattern.length()]; //Хранит цвета символов шаблона
        int[] prefixColors = new int[pattern.length()]; // Хранит цвета символов префикс функции
        int[] prefixNumerationColors = new int[pattern.length()]; // Хранит цвета символов нумерации шаблона
      
        // Инициализация строк начальными значениями (черный цвет)
        for(int i : textColors)
            i = Color.BLACK.getRGB();
        for(int i : patternColors)
            i = Color.BLACK.getRGB();
        for(int i : prefixColors)
            i = panel.getParent().getBackground().getRGB();
        for(int i : prefixNumerationColors)
            i = Color.BLACK.getRGB();
          
        prefixColors[0] = Color.GREEN.getRGB(); // Выделение результата вычисления
        step = new Step(textColors,patternColors,0,prefixColors,prefixNumerationColors, numerationColors);
        prefixColors[0] = Color.BLACK.getRGB();
        steps.add(step);
        for(int i = 1 ; i < line.length() ; ++i){
            Integer k = result.get(i-1);
            while(k > 0 && line.charAt(i) != line.charAt(k)) // Сравниваем текущий символ из строки с символом из шаблона
                k = result.get(k - 1);
            if(line.charAt(i) == line.charAt(k)) // Если совпали, то значение префикс функции на 1 больше индекса
                ++k;
            result.add(k); // Сохраняем результат
            prefixColors[i] = Color.GREEN.getRGB(); // Выделение результата вычисления
            step = new Step(textColors,patternColors,0,prefixColors,prefixNumerationColors, numerationColors);
            prefixColors[i] = Color.BLACK.getRGB();

            step.prefixIndex = i; // Параметры для выделения подстрок при вычислении
            step.prefixLength = k;
            steps.add(step);
        }
        return result;
    }

    private String KMPAlgorithm(String text, String pattern) {
        StringBuilder answer = new StringBuilder(); // Результат – строка с индексами
        steps = new ArrayList<>();

        int[] textColors = new int[text.length()]; // Хранит цвета символов текста
        int[] patternColors = new int[pattern.length()]; // Шаблона
        int[] prefixColors = new int[pattern.length()]; // Хранит цвета символов префикс функции
        int[] prefixNumerationColors = new int[pattern.length()]; // Хранит цвета символов нумерации шаблона
        int[] numerationColors = new int[text.length()]; // нумерации текста
        int indexInText =0; //индекс приложения шаблона к тексту
        int indexInPattern=0; //текущий обрабатываемый символ в тексте

        prefix = PrefixFunction(pattern); // Нахождение префикс функции

        for(int k=0; k<textColors.length; ++k){ // Красим все символы текста в чёрный
            textColors[k]=Color.BLACK.getRGB();
            numerationColors[k] = Color.BLACK.getRGB();
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
                steps.add(new Step(textColors, patternColors, indexInText,prefixColors,prefixNumerationColors, numerationColors)); // Добавили изменения
            }

            for (int k = 0; k < prefixColors.length; ++k) { // Красим значения префикс функции и индексы в чёрный
                prefixColors[k] = Color.BLACK.getRGB();
                prefixNumerationColors[k]=Color.BLACK.getRGB();
            }

            while(indexInPattern<pattern.length() && pattern.charAt(indexInPattern)==text.charAt(indexInPattern+indexInText)) { // Сравнение символов в соответствующих индексах
                textColors[indexInPattern+indexInText]= Color.GREEN.getRGB(); // Покрасили совпавший символ в тексте зелёным
                patternColors[indexInPattern] = Color.GREEN.getRGB(); // Покрасили совпавший символ в шаблоне зелёным
                steps.add(new Step(textColors, patternColors, indexInText,prefixColors,prefixNumerationColors, numerationColors)); // Добавили изменения
                indexInPattern++;
            }
            if(indexInPattern==pattern.length()){ // Если был найден индекс вхождения
                if(answer.length()==0)
                    answer.append(indexInText); // Добавляем индекс в строку с результатом
                else

                    answer.append(", ").append(indexInText); // Добавляем индекс в строку с результатом
 
                numerationColors[indexInText]=Color.MAGENTA.getRGB(); // Подстветка добавленного индекса
              
                if(indexInText+pattern.length()==text.length())
                    steps.add(new Step(textColors, patternColors, indexInText,prefixColors,prefixNumerationColors, numerationColors)); // Добавили изм
                prefixColors[indexInPattern-1]=Color.MAGENTA.getRGB();
                indexInPattern=prefix.get(indexInPattern-1);
                indexInText+=pattern.length()-indexInPattern;
                prefixNumerationColors[indexInPattern]=Color.MAGENTA.getRGB();
            }
            else {
                textColors[indexInPattern+indexInText]= Color.RED.getRGB(); // Покрасили не совпавший символ в тексте красным
                patternColors[indexInPattern] = Color.RED.getRGB(); // Покрасили не совпавший символ в шаблоне красным
                steps.add(new Step(textColors, patternColors, indexInText,prefixColors,prefixNumerationColors, numerationColors)); // Добавили изменения

                if(indexInPattern==0)
                    indexInText++;
                else {
                    prefixColors[indexInPattern-1]=Color.MAGENTA.getRGB(); // Красим значение префикс функции
                    indexInText+=indexInPattern - prefix.get(indexInPattern - 1); // Двигаем шаблон
                    indexInPattern = prefix.get(indexInPattern - 1);
                    prefixNumerationColors[indexInPattern]=Color.MAGENTA.getRGB(); // Красим индекс префикс функции
                }
            }
        }
        if(answer.length()==0)
            answer.append("-1"); // Ответ -1 если не найдено вхождений

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
        infoPatternForPrefix.getParent().remove(infoPatternForPrefix);
        infoNumeration.getParent().remove(infoNumeration);
        infoNumerationForPrefix.getParent().remove(infoNumerationForPrefix);
        infoPi.getParent().remove(infoPi);
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
        for(int i = 0; i < numeration.getElementsNumber(); ++i) {
            numeration.setColor(step.numerationColors[i], i);
        }

        labeledPattern.setX(labeledText.getX() + labeledText.getElementSize()*step.patternPosition);
        drawingLayer.setPrefixLength(step.prefixLength); // Устанавливаем параметры
        drawingLayer.setPrefixIndex(step.prefixIndex);
        drawingLayer.repaint(); // Перерисовываем
    }

    class Step {
        final int[] textColors; // Цвета символов текста
        final int[] patternColors; // Цвета символов шаблона
        final int[] prefixColors; // Цвета значений префикс-функции
        final int[] prefixNumerationColors; // Цвета нумерации шаблона в префикс-функции
        final int patternPosition; // Позиция шаблона относительно символов текста
        final int[] numerationColors; // Цвета индексов текста
        int prefixLength; // Длина совпавшего префикса и суффикса
        int prefixIndex; // Текущий индекс в шаблоне

        Step(int[] textColors, int[] patternColors, int patternPosition, int[] prefixColors, int[] prefixNumerationColors, int[] numerationColors) {
            this.textColors = textColors.clone();
            this.patternColors = patternColors.clone();
            this.patternPosition = patternPosition;
            this.prefixColors=prefixColors.clone();
            this.prefixNumerationColors=prefixNumerationColors.clone();
            this.numerationColors = numerationColors.clone();
            prefixIndex = 0;
        }
    }

    class DrawingLayer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // Отрисовка всей панели
            if(prefixLength != 0){
                g.setColor(Color.RED); // Отрисовка красного овала
                g.drawRoundRect(80, 168, 40 + 20*prefixLength - 40, 25, 20, 15);
                g.setColor(Color.BLUE); // Отрисовка синего овала
                g.drawRoundRect(80 + 20*(prefixIndex - prefixLength + 1), 168, 40 + 20*prefixLength - 40, 25, 20, 15);
                panel.getDrawingLayer().repaint();
            }
        }
        private int prefixLength;// Длина совпавшего префикса и суффикса
        private int prefixIndex; // Текущий индекс в шаблоне

        void setPrefixIndex(int prefixIndex) {
            this.prefixIndex = prefixIndex;
        }

        void setPrefixLength(int prefixLength) {
            this.prefixLength = prefixLength;
        }
    }
}
