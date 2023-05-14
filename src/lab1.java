import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class lab1 extends JFrame {

    private JLabel filenameLabel;
    private JTextField filenameField;
    private JButton loadButton;
    private JTable wordsTable;

    public lab1() {
        super("Words in Lexicographic Order");

    filenameLabel = new JLabel("Enter filename:");
    filenameField = new JTextField();
    loadButton = new JButton("Load");
    loadButton.setPreferredSize(new Dimension(100, 35));
    wordsTable = new JTable();


    DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Word"});
    wordsTable.setModel(tableModel);

    loadButton.addActionListener(new LoadButtonListener());

    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    topPanel.add(filenameLabel);
    topPanel.add(Box.createRigidArea(new Dimension(10, 10)));
    topPanel.add(filenameField);
    topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    topPanel.add(loadButton);
    topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(new JScrollPane(wordsTable), BorderLayout.CENTER);


    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(400, 400));
    getContentPane().add(mainPanel);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
}

private void loadWordsFromFile(String filename) throws IOException, InvalidDataFormatException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {
        line = line.trim(); // видаляємо пробіли з початку і кінця рядка
        if (line.isEmpty()) {
            continue; // пропускаємо порожні рядки
        }
        String[] words = line.toLowerCase().split(" ");
        for (String word : words) {
            if (word.matches(".*\\d.*")) { // перевірка на наявність цифр у слові
                throw new InvalidDataFormatException("Invalid word format: " + word);
            }
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String sortedWord = new String(chars);
            if (word.equals(sortedWord)) {
                ((DefaultTableModel) wordsTable.getModel()).addRow(new Object[]{word});
            } 
        }
    }
    reader.close();
}

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filename = filenameField.getText();
            try {
                loadWordsFromFile(filename);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(lab1.this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidDataFormatException ex) {
                JOptionPane.showMessageDialog(lab1.this, "Invalid data format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class InvalidDataFormatException extends Exception {
        public InvalidDataFormatException(String message) {
            super(message);
        }
    }
    public static void main(String[] args) {
        lab1 frame = new lab1();
        frame.setVisible(true);
    }
}