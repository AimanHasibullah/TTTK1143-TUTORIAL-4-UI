import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

// a) Interface Measurable
interface Measurable {
    double getMeasure();
    String getName();
}

// b) Class BankAccount
class BankAccount implements Measurable {
    private int accNumber;
    private double balance;
    private static int LastAssignedNumber = 1000;

    public BankAccount() {
        this(0.0);
    }

    public BankAccount(double balance) {
        this.balance = balance;
        LastAssignedNumber++;
        this.accNumber = LastAssignedNumber;
    }

    public int getAccountNumber() {
        return accNumber;
    }

    @Override
    public double getMeasure() {
        return balance;
    }

    @Override
    public String getName() {
        return "Bank Account #" + accNumber;
    }
}

// c) Class Coin
class Coin implements Measurable {
    private double value;
    private String name;

    public Coin(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public double getMeasure() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}

// d) Class DataSet
class DataSet implements Measurable {
    private double sum;
    private Measurable max;
    private int count;
    private String name;

    public DataSet() {
        sum = 0;
        count = 0;
        max = null;
        name = "DataSet";
    }

    public void add(Measurable x) {
        sum += x.getMeasure();
        if (count == 0 || x.getMeasure() > max.getMeasure()) {
            max = x;
        }
        count++;
    }

    public Measurable getMax() {
        return max;
    }

    public double getAverage() {
        if (count == 0) return 0.0;
        return sum / count;
    }

    @Override
    public double getMeasure() {
        return getAverage();
    }

    @Override
    public String getName() {
        return name;
    }
}

// e) Class Student
class Student implements Measurable {
    private String name;
    private double height;

    public Student(String name, double height) {
        this.name = name;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double getMeasure() {
        return height;
    }

    @Override
    public String getName() {
        return name;
    }
}

// Main Driver Class with Java Swing UI
public class DataSetApp extends JFrame {
    private JComboBox<String> typeSelect;
    private JTextField nameInput;
    private JTextField valueInput;
    private JTextArea consoleOutput;
    private JLabel avgLabel;
    private JLabel maxLabel;

    private DataSet currentDataSet;
    private DecimalFormat df = new DecimalFormat("0.00");

    public DataSetApp() {
        currentDataSet = new DataSet();

        setTitle("DataSet Polymorphism Visualizer");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form Controls Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Measurable Item"));

        inputPanel.add(new JLabel(" Object Type:"));
        typeSelect = new JComboBox<>(new String[]{"BankAccount", "Coin", "Student"});
        inputPanel.add(typeSelect);

        inputPanel.add(new JLabel(" Name / Label:"));
        nameInput = new JTextField();
        inputPanel.add(nameInput);

        inputPanel.add(new JLabel(" Measure (Balance/Value/Height):"));
        valueInput = new JTextField();
        inputPanel.add(valueInput);

        JButton addBtn = new JButton("Add Item");
        JButton runDemoBtn = new JButton("Run Full Tutorial Demo");
        inputPanel.add(addBtn);
        inputPanel.add(runDemoBtn);

        add(inputPanel, BorderLayout.NORTH);

        // Center Output Display Area
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output Display"));
        add(scrollPane, BorderLayout.CENTER);

        // Summary Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel statsPanel = new JPanel(new GridLayout(2, 1));
        statsPanel.setBorder(BorderFactory.createTitledBorder("DataSet Summary"));
        avgLabel = new JLabel(" Average: 0.00");
        maxLabel = new JLabel(" Maximum Item: N/A");
        statsPanel.add(avgLabel);
        statsPanel.add(maxLabel);

        JButton clearBtn = new JButton("Clear Output");
        bottomPanel.add(statsPanel, BorderLayout.CENTER);
        bottomPanel.add(clearBtn, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        addBtn.addActionListener(e -> addItem());
        runDemoBtn.addActionListener(e -> runAssignmentDemo());
        clearBtn.addActionListener(e -> clearData());
    }

    private void addItem() {
        try {
            double val = Double.parseDouble(valueInput.getText().trim());
            String type = (String) typeSelect.getSelectedItem();
            String name = nameInput.getText().trim();

            Measurable item;
            if ("BankAccount".equals(type)) {
                item = new BankAccount(val);
            } else if ("Coin".equals(type)) {
                item = new Coin(val, name.isEmpty() ? "Coin" : name);
            } else {
                item = new Student(name.isEmpty() ? "Student" : name, val);
            }

            currentDataSet.add(item);
            consoleOutput.append("Added: " + item.getName() + " -> Measure: " + df.format(item.getMeasure()) + "\n");
            updateSummary();

            nameInput.setText("");
            valueInput.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric value for measure.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runAssignmentDemo() {
        clearData();
        consoleOutput.append("=== RUNNING TUTORIAL SAMPLE OUTPUT ===\n\n");

        // 1. BankAccount Dataset
        DataSet bankData = new DataSet();
        bankData.add(new BankAccount(0));
        bankData.add(new BankAccount(10000));
        bankData.add(new BankAccount(2000));
        consoleOutput.append("Bank Average Balance: " + bankData.getAverage() + "\n");
        Measurable max = bankData.getMax();
        consoleOutput.append("Bank Highest Balance: " + max.getMeasure() + "\n\n");

        // 2. Coin Dataset
        DataSet coinData = new DataSet();
        coinData.add(new Coin(0.25, "Quarter"));
        coinData.add(new Coin(0.1, "Dime"));
        coinData.add(new Coin(0.05, "Nickel"));
        consoleOutput.append("Average Coin Value: " + df.format(coinData.getAverage()) + "\n");
        max = coinData.getMax();
        consoleOutput.append("Highest Coin Balance: " + df.format(max.getMeasure()) + "\n");
        consoleOutput.append("Highest Coin's Name: " + max.getName() + "\n\n");

        // 3. Student Dataset
        DataSet student1 = new DataSet();
        student1.add(new Student("Ali", 157.50));
        student1.add(new Student("Alex", 165.00));
        student1.add(new Student("Maria", 147.00));
        consoleOutput.append("Average Student's Height: " + df.format(student1.getAverage()) + "\n");
        max = student1.getMax();
        consoleOutput.append("Tallest Height: " + df.format(max.getMeasure()) + "\n");
        consoleOutput.append("Tallest Student's Name: " + max.getName() + "\n");
        consoleOutput.append("======================================\n");
    }

    private void updateSummary() {
        avgLabel.setText(" Average: " + df.format(currentDataSet.getAverage()));
        Measurable max = currentDataSet.getMax();
        if (max != null) {
            maxLabel.setText(" Maximum Item: " + max.getName() + " (" + df.format(max.getMeasure()) + ")");
        } else {
            maxLabel.setText(" Maximum Item: N/A");
        }
    }

    private void clearData() {
        currentDataSet = new DataSet();
        consoleOutput.setText("");
        updateSummary();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DataSetApp().setVisible(true);
        });
    }
}