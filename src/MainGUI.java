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
    private int currentStep = -1;
    private int stepsNumber;
    private Timer timer;
    public MainGUI() {
        // Инициализируем GUI
        GUIInit();
        // Добавляем обработчики событий к кнопкам и таймеру
        addActionListeners();

//        LabeledString labeledString = new LabeledString("This is a test string", 12, drawingPanel, 20, 20);
//        labeledString.addToPanel(drawingPanel);
//        labeledString.setPosition(20,200);
//        NumeratedString numLabString = new NumeratedString(labeledString.getElementsNumber());
//        NumeratedString numeratedString = new NumeratedString(new int[] {0, 1, 2, 3, 10, 20}, 12, 20,20);
//        numeratedString.addToPanel(drawingPanel);

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
        // TODO: проверка на то, что поля ввода пустые?
        visualizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().length() == 0 && textField.getText().length() == 0) {
                    // TODO: Выводим сообщение об ошибке
                } else {
                    if (drawingPanel.getVisualization() != null) {
                        // Очищаем окно от прошлой визуализации
                        drawingPanel.getVisualization().clear();
                        drawingPanel.revalidate();
                        drawingPanel.repaint();
//                        drawingPanel.remove(drawingPanel.getVisualization())
                    }
                    currentStep = -1;
                    if (comboBox.getSelectedItem().equals("Naive")) {
                        NaiveVisualization naiveVisualization = new NaiveVisualization(textField.getText(),
                                patternField.getText(), drawingPanel, answerLabel);
                        drawingPanel.setVisualization(naiveVisualization);
                        stepsNumber = naiveVisualization.getStepsNumber();
                    } else if (comboBox.getSelectedItem().equals("KMP")) {
                        KMPVisualization kmpVisualization = new KMPVisualization(textField.getText(),
                                patternField.getText(), drawingPanel, answerLabel);
                        drawingPanel.setVisualization(kmpVisualization);
                        stepsNumber = kmpVisualization.getStepsNumber();
                        System.out.println(stepsNumber);
                    }
                    startButton.setEnabled(true);
                    nextButton.setEnabled(true);
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
            if (startButton.getText().equals("Start")) {
                prevButton.setEnabled(false);
                nextButton.setEnabled(false);
                visualizeButton.setEnabled(false);
                startButton.setText("Pause");
                timer.start();
            } else {
                prevButton.setEnabled(true);
                nextButton.setEnabled(true);
                visualizeButton.setEnabled(true);
                startButton.setText("Start");
                timer.stop();
            }
        });
        // Обработчик на нажатие кнопки Next:
        nextButton.addActionListener(e -> {
            ++currentStep;
            update();
        });
        // Обработчик на сигнал таймера:
        timer = new Timer(TIME_BETWEEN_STEPS, e -> {
            nextButton.doClick();
        });
    }

    private void update() {
        updateButtons();
        drawingPanel.getVisualization().visualize(currentStep);
    }

    // Функция, обновляющая кнопки - можем мы их нажимать или нет
    private void updateButtons() {
        checkPrevButton();
        checkStartNextButtons();
    }
    // Проверка для кнопки Prev на то, что текущий шаг - первый, если он первый, то нажимать ее нельзя:
    private void checkPrevButton(){
        if (currentStep <= 0 && prevButton.isEnabled())
            prevButton.setEnabled(false);
        else if (currentStep >0 && startButton.getText().equals("Start")) // Если алгоритм не находится в процессе автоматического показа визуализации
            prevButton.setEnabled(true);
    }
    // Проверка для кнопок Next/Start на то, что текущий шаг - последний:
    // TODO
    private void checkStartNextButtons(){
        if (currentStep == stepsNumber) {
            startButton.setText("Start");
            startButton.setEnabled(false);
            nextButton.setEnabled(false);
            timer.stop();
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
    private JLabel answerLabel;
}
