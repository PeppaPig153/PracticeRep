import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Класс графического интерфейса
 */
public class MainGUI {
    private static final int FRAME_WIDTH  = 640;
    private static final int FRAME_HEIGHT = 480;
    private int currentStep = 0;
    public MainGUI() {
        // Инициализируем GUI
        GUIInit();
        // Добавляем обработчики событий к кнопкам
        addActionListeners();

//        LabeledString labeledString = new LabeledString("Hello World", 20, 20, 20);
//        labeledString.addToPanel(drawingPanel);
//        NumeratedString numLabString = new NumeratedString(labeledString.getElementsNumber());
        NumeratedString numeratedString = new NumeratedString(new int[] {0, 1, 2, 3, 10, 20}, 12, 20,20);
        numeratedString.addToPanel(drawingPanel);
//        Timer timer = new Timer(100, e -> {
//            labeledString.setX(labeledString.getX()+labeledString.getElementSize());
//        });
//        timer.start();
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
        frame.add(MajorPanel);
        // Устанавливаем менеджер размещения компонентов для панели drawingPanel на абсолютный:
        drawingPanel.setLayout(null);
        answerLabel = new JLabel();
        answerLabel.setBounds(drawingPanel.getBounds().height-40, 40, 200, 20);
        drawingPanel.add(answerLabel);
    }


    // Функция инициализации обработчиков нажатий кнопок
    private void addActionListeners() {
        // Добавляем обработчик события при нажатии на кнопку Visualize:
        // TODO
        visualizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().length() == 0 && textField.getText().length() == 0) {
                    // TODO: Выводим сообщение об ошибке
                } else {
                    if (comboBox.getSelectedItem().equals("Naive")) {
                        NaiveVisualization naiveVisualization = new NaiveVisualization(textField.getText(),
                                patternField.getText(), drawingPanel, answerLabel);
                        drawingPanel.setVisualization(naiveVisualization);
                    } else if (comboBox.getSelectedItem().equals("KMP")) {
//                        KMPVisualization kmpVisualization = new KMPVisualization(textField.getText(),
//                                patternField.getText());
//                        drawingPanel.setVisualization(kmpVisualization);
                    }
                    startButton.setEnabled(true);
                    nextButton.setEnabled(true);
                }
            }
        });
        // Обработчик на нажатие кнопки Prev:
        prevButton.addActionListener(e -> {

        });
        // Обработчик на нажатие кнопки Start/Pause:
        startButton.addActionListener(e -> {

        });
        // Обработчик на нажатие кнопки Next:
        nextButton.addActionListener(e -> {
            ++currentStep;
//            drawingPanel.getVisualization().visualize(currentStep);
        });
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
