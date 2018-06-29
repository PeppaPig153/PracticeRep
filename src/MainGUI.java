import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * Класс графического интерфейса
 */
public class MainGUI {
    private static final int FRAME_WIDTH  = 640;
    private static final int FRAME_HEIGHT = 480;
    public MainGUI() {
        // Создаем окно:
        JFrame frame = new JFrame("KMP Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        // Добавляем в него форму, сконструированную с помощью IDEA:
        frame.add(MajorPanel);

        // Устанавливаем абсолютный менеждер размещения компонентов для панели drawingPanel:
        drawingPanel.setLayout(null);

//        if (textField.getText().length() > 0 && textField.getText().length() > 0) {
//            visualizeButton.setEnabled(true);
//        }
//        else {
//            visualizeButton.setEnabled(false);
//        }
        // Добавляем обработчик события при нажатии на кнопку Visualize:
        visualizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedItem().equals("Naive")) {
                    NaiveVisualization naiveVisualization = new NaiveVisualization(textField.getText(),
                            patternField.getText());
                    drawingPanel.setVisualization(naiveVisualization);
                } else if (comboBox.getSelectedItem().equals("KMP")) {
                    KMPVisualization kmpVisualization = new KMPVisualization(textField.getText(),
                            patternField.getText());
                    drawingPanel.setVisualization(kmpVisualization);
                }
            }
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
}
