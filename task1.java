import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class task1 extends JFrame {

    private ArrayList<String> studentNames;
    private ArrayList<Double> studentGrades;

    private JTextField nameField;
    private JTextField gradeField;
    private JTextArea outputArea;

    public task1() {

        studentNames = new ArrayList<>();
        studentGrades = new ArrayList<>();

        setTitle("Student Grade Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
        setVisible(true);
    }

    private void setupUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Student Grade Tracker", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(heading, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade (0-100):"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addBtn = new JButton("Add Student");
        JButton reportBtn = new JButton("Show Report");
        inputPanel.add(addBtn);
        inputPanel.add(reportBtn);

        mainPanel.add(inputPanel, BorderLayout.WEST);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(outputArea);
        mainPanel.add(scroll, BorderLayout.CENTER);

        add(mainPanel);

        addBtn.addActionListener(e -> addStudent());
        reportBtn.addActionListener(e -> generateReport());
    }

    private void addStudent() {

        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);

            if (grade < 0 || grade > 100) {
                JOptionPane.showMessageDialog(this, "Grade should be between 0 and 100.");
                return;
            }

            studentNames.add(name);
            studentGrades.add(grade);

            nameField.setText("");
            gradeField.setText("");
            nameField.requestFocus();

            JOptionPane.showMessageDialog(this, name + " added!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number for grade.");
        }
    }

    private void generateReport() {

        if (studentNames.isEmpty()) {
            outputArea.setText("No students added yet.");
            return;
        }

        double total = 0;
        double highest = studentGrades.get(0);
        double lowest = studentGrades.get(0);

        StringBuilder report = new StringBuilder();
        report.append("======= Student Report =======\n\n");

        for (int i = 0; i < studentNames.size(); i++) {
            double g = studentGrades.get(i);
            total += g;

            if (g > highest) highest = g;
            if (g < lowest) lowest = g;

            report.append(studentNames.get(i))
                  .append("  ->  ")
                  .append(g)
                  .append("\n");
        }

        double avg = total / studentNames.size();

        report.append("\n------------------------------\n");
        report.append("Total Students : ").append(studentNames.size()).append("\n");
        report.append("Average        : ").append(String.format("%.2f", avg)).append("\n");
        report.append("Highest        : ").append(highest).append("\n");
        report.append("Lowest         : ").append(lowest).append("\n");

        outputArea.setText(report.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new task1());
    }
}
