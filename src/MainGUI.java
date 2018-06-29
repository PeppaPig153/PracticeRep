import javax.swing.*;

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
    }
    private JPanel MajorPanel;
    private JTextField textField;
    private JTextField patternField;
    private JButton visualizeButton;
    private JButton prevButton;
    private JButton startButton;
    private JButton nextButton;
    private JComboBox comboBox1;
}
