package GUIs;

import Visualizators.KMPVisualization;
import Visualizators.NaiveVisualization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Класс графического интерфейса
 */
public class MainGUI {
    // Константы:
    private static final int FRAME_WIDTH  = 640;
    private static final int FRAME_HEIGHT = 480;
    private static final int TIME_BETWEEN_STEPS = 500;
    private static final int TEXT_MAX_SIZE = 24;

    public MainGUI() {
        // Инициализируем GUI
        GUIInit();
        // Добавляем обработчики событий к кнопкам и таймеру
        addActionListeners();
    }

    // ------------------------------------------------------------------------
    // Вспомогательные функции

    // Инициализация графического интерфейса
    private void GUIInit() {
        // Создаем окно:
        JFrame frame = new JFrame("Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Добавляем в него форму, сконструированную с помощью IDEA:
        frame.setContentPane(MajorPanel);
        // Устанавливаем менеджер размещения компонентов для панели drawingPanel на абсолютный:
        drawingPanel.setLayout(null);
        // Приветственное сообщение:
        infoLabel = new JLabel( "<html>" +
                "Эта программа визуализирует наивный алгоритм и алгоритм КМП поиска шаблона в строке.<br>" +
                "Строка вводится в поле \"Text\", шаблон - в поле \"Pattern\".<br>" +
                "Длина вводимых строк не должна превышать " + TEXT_MAX_SIZE + " символов;<br>" +
                "Слишком длинные строки обрезаются до максимально допустимого размера.<br>" +
                "Для того, чтобы сгенерировать визуализацию, необходимо нажать кнопку \"Visualize\".<br>" +
                "Для автоматического показа визуализации нужно необходимо нажать кнопку \"Start\";<br>" +
                "Для того, чтобы остановить автоматический показ, нажмите кнопку \"Pause\".<br>" +
                "Кнопка для перехода на следующий шаг - \"Next\", на предыдущий - \"Prev\".<br>" +
                "</html>");
        drawingPanel.add(infoLabel);
        infoLabel.setBounds(20, 20, FRAME_WIDTH-40, 160);
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
        // Метка для ответа:
        answerLabel = new JLabel("");
        drawingPanel.add(answerLabel);
        answerLabel.setBounds(20, drawingPanel.getBounds().height-30, FRAME_WIDTH-40, 20);
        frame.repaint();
    }

    // Функция инициализации обработчиков нажатий кнопок
    private void addActionListeners() {
        // Добавляем обработчик события при нажатии на кнопку Visualize:
        visualizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().length() == 0 || patternField.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null, "One of fields is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    drawingPanel.remove(infoLabel); // Удаляем приветственное сообщение
                    if (drawingPanel.getVisualization() != null) {
                        // Очищаем окно от прошлой визуализации
                        drawingPanel.getVisualization().clear();
                        drawingPanel.revalidate();
                        drawingPanel.repaint();
                    }
                    currentStep = -1;
                    // Обрезаем строки, если они длиннее TEXT_MAX_SIZE символов
                    if (textField.getText().length() > TEXT_MAX_SIZE || patternField.getText().length() > TEXT_MAX_SIZE) {
                        JOptionPane.showMessageDialog(null, "Data will be cut off to 24 symbols", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    String text = textField.getText().length()>TEXT_MAX_SIZE ?
                            textField.getText().substring(0,TEXT_MAX_SIZE) : textField.getText();
                    String pattern = patternField.getText().length()>TEXT_MAX_SIZE ?
                            patternField.getText().substring(0,TEXT_MAX_SIZE) : patternField.getText();

                    // Выбираем нужную визуализацию:
                    if (comboBox.getSelectedItem().equals("Naive")) {
                        NaiveVisualization naiveVisualization = new NaiveVisualization(text,
                                pattern, drawingPanel, answerLabel);
                        drawingPanel.setVisualization(naiveVisualization);
                        stepsNumber = naiveVisualization.getStepsNumber();
                    } else if (comboBox.getSelectedItem().equals("KMP")) {
                        KMPVisualization kmpVisualization = new KMPVisualization(text,
                                pattern, drawingPanel, answerLabel);
                        drawingPanel.setVisualization(kmpVisualization);
                        stepsNumber = kmpVisualization.getStepsNumber();
                    }
                    drawingPanel.repaint();
                    update();
                }
            }
        });
        // Обработчик на нажатие кнопки Prev:
        prevButton.addActionListener(e -> {
            --currentStep;
            update();
        });
        // Обработчик на нажатие кнопки Start/Pause:
        startButton.addActionListener(e -> {
            // Включаем автоматический переход на следующий шаг:
            if (!isAutomatic()) {
                startButton.setText("Pause");
                timer.start();
            } else {
                startButton.setText("Start");
                timer.stop();
            }
            updateButtons();
        });
        // Обработчик на нажатие кнопки Next:
        nextButton.addActionListener(e -> {
            ++currentStep;
            update();
        });
        // Обработчик на сигнал таймера:
        timer = new Timer(TIME_BETWEEN_STEPS, e -> {
            ++currentStep;
            update();
        });
    }

    // Функция возвращает true, если переход на следующий шаг выполняется автоматически
    private boolean isAutomatic() {
        return startButton.getText().equals("Pause");
    }
    private boolean isFirstStep() {
        return currentStep <= 0;
    }
    private boolean isLastStep() {
        return currentStep+1 >= stepsNumber;
    }

    // Функция, обновляющая состояние кнопок и отображение визуализации в соответствии с текущим шагом currentStep.
    private void update() {
        updateButtons();
        currentStepLabel.setText(currentStep+1+"/"+stepsNumber);
        drawingPanel.getVisualization().visualize(currentStep);
    }

    // Функция, обновляющая кнопки - можем мы их нажимать или нет
    private void updateButtons() {
        checkPrevButton();
        checkStartNextButtons();
        checkPrevButton();
    }
    // Проверка для кнопки Prev на то, что текущий шаг - первый, если он первый, то нажимать ее нельзя:
    private void checkPrevButton() {
        if (!isFirstStep() && !isAutomatic()) // Если алгоритм не находится в процессе автоматического показа визуализации и не на первом шаге
            prevButton.setEnabled(true);
        else
            prevButton.setEnabled(false);
    }
    // Проверка для кнопок Next/Start на то, что текущий шаг - последний:
    private void checkStartNextButtons(){
        if (isAutomatic() || isLastStep()) {
            nextButton.setEnabled(false);
            if (isLastStep()) {
                startButton.setEnabled(false);
                startButton.setText("Start");
                timer.stop();
            }
        } else {
            startButton.setEnabled(true);
            nextButton.setEnabled(true);
        }
    }

    // Переменные:
    private int currentStep = -1; // Текущий шаг
    private int stepsNumber; // Суммарное количество шагов
    private Timer timer; // Таймер, включающийся по нажатию кнопки Start

    // Элементы формы:
    private JPanel MajorPanel;
    private JTextField textField; // Поле для ввода текста
    private JTextField patternField; // Поле для ввода шаблона
    private JButton visualizeButton; // Кнопка "Visualize"
    private JButton prevButton; // Кнопка "Prev"
    private JButton startButton; // Кнопка "Start"/"Pause"
    private JButton nextButton; // Кнопка "Next"
    private JComboBox comboBox; // Выпадающий список, позволяющий выбрать визуализируемый алгоритм
    private DrawingPanel drawingPanel; // Панель, на которой происходит отображение визуализации
    private JLabel currentStepLabel; // Метка, отображающая текущий шаг и кол-во шагов
    private JLabel answerLabel; // Метка, отображающая результат работы алгоритма(ответ)
    private JLabel infoLabel; // Метка, отображающая приветственное сообщение
}
