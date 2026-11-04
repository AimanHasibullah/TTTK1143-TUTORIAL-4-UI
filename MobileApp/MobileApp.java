package MobileApp;

import javax.swing.*;
import java.awt.*;

// a) Interface RadioTuner
interface RadioTuner {
    void setStation(String station);
    void setFrequency(double frequency);
}

// b) Class MobileDevice
class MobileDevice implements RadioTuner {
    protected String manufacturer;
    protected String model;
    public String station;
    public double frequency;

    public MobileDevice(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public void printDetails() {
        System.out.println("Manufacturer: " + manufacturer);
        System.out.println("Model: " + model);
    }

    public String getDetailsString() {
        return "Manufacturer: " + manufacturer + "\nModel: " + model;
    }

    @Override
    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}

// c) Class SmartPhone
class SmartPhone extends MobileDevice {
    public String type;

    public SmartPhone(String manufacturer, String model, String type) {
        super(manufacturer, model);
        this.type = type;
    }

    @Override
    public void printDetails() {
        System.out.println("Smart Phone Detail:");
        super.printDetails();
        System.out.println("Type: " + type);
    }

    @Override
    public String getDetailsString() {
        return "Smart Phone Detail:\n" + super.getDetailsString() + "\nType: " + type;
    }
}

// d) Class BasicPhone
class BasicPhone extends MobileDevice {
    public BasicPhone(String manufacturer, String model) {
        super(manufacturer, model);
    }

    @Override
    public void printDetails() {
        System.out.println("Basic Phone Detail:");
        super.printDetails();
    }

    @Override
    public String getDetailsString() {
        return "Basic Phone Detail:\n" + super.getDetailsString();
    }
}

// Main Driver Class with Java Swing UI
public class MobileApp extends JFrame {
    private JComboBox<String> deviceTypeSelect;
    private JTextField manufacturerInput;
    private JTextField modelInput;
    private JTextField typeInput;
    private JTextField stationInput;
    private JTextField frequencyInput;
    private JTextArea consoleOutput;

    public MobileApp() {
        setTitle("MobileDevice & RadioTuner Visualizer");
        setSize(580, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form Controls Panel
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Mobile Device & Radio Configuration"));

        inputPanel.add(new JLabel(" Device Type:"));
        deviceTypeSelect = new JComboBox<>(new String[]{"SmartPhone", "BasicPhone"});
        inputPanel.add(deviceTypeSelect);

        inputPanel.add(new JLabel(" Manufacturer:"));
        manufacturerInput = new JTextField();
        inputPanel.add(manufacturerInput);

        inputPanel.add(new JLabel(" Model:"));
        modelInput = new JTextField();
        inputPanel.add(modelInput);

        inputPanel.add(new JLabel(" Type (SmartPhone only):"));
        typeInput = new JTextField();
        inputPanel.add(typeInput);

        inputPanel.add(new JLabel(" Radio Station:"));
        stationInput = new JTextField();
        inputPanel.add(stationInput);

        inputPanel.add(new JLabel(" Frequency (MHz):"));
        frequencyInput = new JTextField();
        inputPanel.add(frequencyInput);

        JButton addBtn = new JButton("Tune & Display Device");
        JButton runDemoBtn = new JButton("Run Assignment Demo");
        inputPanel.add(addBtn);
        inputPanel.add(runDemoBtn);

        add(inputPanel, BorderLayout.NORTH);

        // Display Console Area
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Console Log Output"));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Actions Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clearBtn = new JButton("Clear Console Log");
        bottomPanel.add(clearBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        deviceTypeSelect.addActionListener(e -> {
            boolean isSmart = "SmartPhone".equals(deviceTypeSelect.getSelectedItem());
            typeInput.setEnabled(isSmart);
        });

        addBtn.addActionListener(e -> processDevice());
        runDemoBtn.addActionListener(e -> runAssignmentDemo());
        clearBtn.addActionListener(e -> consoleOutput.setText(""));
    }

    private void processDevice() {
        try {
            String typeSel = (String) deviceTypeSelect.getSelectedItem();
            String mfr = manufacturerInput.getText().trim();
            String model = modelInput.getText().trim();
            String subType = typeInput.getText().trim();
            String station = stationInput.getText().trim();
            String freqStr = frequencyInput.getText().trim();

            if (mfr.isEmpty()) mfr = "Generic";
            if (model.isEmpty()) model = "Model-X";
            if (station.isEmpty()) station = "Default FM";
            double freq = freqStr.isEmpty() ? 88.0 : Double.parseDouble(freqStr);

            MobileDevice device;
            if ("SmartPhone".equals(typeSel)) {
                device = new SmartPhone(mfr, model, subType.isEmpty() ? "General" : subType);
            } else {
                device = new BasicPhone(mfr, model);
            }

            device.setStation(station);
            device.setFrequency(freq);

            consoleOutput.append(device.getDetailsString() + "\n");
            consoleOutput.append("Station:" + device.station + "\n");
            consoleOutput.append("Frequency:" + device.frequency + "\n");
            consoleOutput.append("-----------------------------------\n");

            manufacturerInput.setText("");
            modelInput.setText("");
            typeInput.setText("");
            stationInput.setText("");
            frequencyInput.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric frequency.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runAssignmentDemo() {
        consoleOutput.append("=== RUNNING MOBILE APP MAIN DEMO LOGIC ===\n");

        MobileDevice m1 = new SmartPhone("Samsung", "S20", "Galaxy");
        MobileDevice m2 = new BasicPhone("Sonny Ericson", "Walkman");

        m1.setStation("Hot FM");
        m1.setFrequency(97.60);

        m2.setStation("Fly FM");
        m2.setFrequency(109.8);

        consoleOutput.append(m1.getDetailsString() + "\n");
        consoleOutput.append("Station:" + m1.station + "\n");
        consoleOutput.append("Frequency:" + m1.frequency + "\n\n");

        consoleOutput.append(m2.getDetailsString() + "\n");
        consoleOutput.append("Station:" + m2.station + "\n");
        consoleOutput.append("Frequency:" + m2.frequency + "\n");
        consoleOutput.append("=========================================\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MobileApp().setVisible(true);
        });
    }
}