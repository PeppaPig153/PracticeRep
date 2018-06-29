import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainGUI {
    private static final int FRAME_WIDTH  = 640;
    private static final int FRAME_HEIGHT = 480;
    public MainGUI() {
        JFrame frame = new JFrame("KMP Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(MajorPanel);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
//        if (textField.getText().length() > 0 && textField.getText().length() > 0) {
//            visualizeButton.setEnabled(true);
//        }
//        else {
//            visualizeButton.setEnabled(false);
//        }
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
    private JPanel minorPanel;
}
