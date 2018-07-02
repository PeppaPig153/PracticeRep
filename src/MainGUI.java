import Visualizators.KMPVisualization;
import Visualizators.NaiveVisualization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Класс графического интерфейса
 */
public class MainGUI {
    private static final int FRAME_WIDTH  = 640;
    private static final int FRAME_HEIGHT = 480;
    private static final int TIME_BETWEEN_STEPS = 500;
    private static final int TEXT_MAX_SIZE = 24;
    private int currentStep = -1;
    private int stepsNumber;
    private Timer timer;
    public MainGUI() {
        // Инициализируем GUI
        GUIInit();
        // Добавляем обработчики событий к кнопкам и таймеру
        addActionListeners();
    }

    // Инициализация графического интерфейса
    private void GUIInit() {
        // Создаем окно:
        JFrame frame = new JFrame("KMP Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        // Добавляем в него форму, сконструированную с помощью IDEA:
        frame.setContentPane(MajorPanel);
        // Устанавливаем менеджер размещения компонентов для панели drawingPanel на абсолютный:
        drawingPanel.setLayout(null);
        answerLabel = new JLabel("It's an answer!(this text is for testing)");
        drawingPanel.add(answerLabel);
        answerLabel.setBounds(20, drawingPanel.getBounds().height-40, FRAME_WIDTH-20, 20);
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
    // TODO
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

    private JPanel MajorPanel;
    private JTextField textField;
    private JTextField patternField;
    private JButton visualizeButton;
    private JButton prevButton;
    private JButton startButton;
    private JButton nextButton;
    private JComboBox comboBox;
    private DrawingPanel drawingPanel;
    private JLabel currentStepLabel;
    private JLabel answerLabel;
}
