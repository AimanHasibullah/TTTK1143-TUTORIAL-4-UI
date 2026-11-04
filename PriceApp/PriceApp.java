package PriceApp;

import javax.swing.*;
import java.awt.*;

// f) Class Customer
class Customer {
    private String nameCust;
    private double salary;

    public Customer(String nameCust, double salary) {
        this.nameCust = nameCust;
        this.salary = salary;
    }

    public String getName() {
        return nameCust;
    }

    public double getSalary() {
        return salary;
    }
}

// a) Interface Price
interface Price {
    void getPrice(Customer c);
}

// b) Class Product
class Product implements Price {
    private String type;
    private Customer buy;

    public Product(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Customer getBuy() {
        return buy;
    }

    @Override
    public String toString() {
        return "Type: " + type;
    }

    @Override
    public void getPrice(Customer c) {
        System.out.println(toString());
        if (c.getSalary() > 4000) {
            this.buy = c;
            System.out.println("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " can buy this product.");
            System.out.println("Your salary is higher than the price's product.");
        } else {
            System.out.println("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " cannot buy this product.");
            System.out.println("The product price is more than your salary.");
        }
    }
}

// c) Class SmartPhone
class SmartPhone extends Product {
    private String nameSP;
    private double priceSP;

    public SmartPhone(String type, String nameSP, double priceSP) {
        super(type);
        this.nameSP = nameSP;
        this.priceSP = priceSP;
    }

    public double getPriceSP() {
        return priceSP;
    }

    @Override
    public String toString() {
        return "The product is " + nameSP + ".";
    }
}

// d) Class Laptop
class Laptop extends Product {
    private String nameL;
    private double priceL;

    public Laptop(String type, String nameL, double priceL) {
        super(type);
        this.nameL = nameL;
        this.priceL = priceL;
    }

    public double getPriceL() {
        return priceL;
    }

    @Override
    public String toString() {
        return "The product is " + nameL + ".";
    }
}

// e) Class Car
class Car implements Price {
    private String brand;
    private double priceCar;
    private Customer buy;

    public Car(String brand, double priceCar) {
        this.brand = brand;
        this.priceCar = priceCar;
    }

    public String getBrand() {
        return brand;
    }

    public double getPriceCar() {
        return priceCar;
    }

    public Customer getBuy() {
        return buy;
    }

    @Override
    public String toString() {
        return "The car brand is " + brand + " with price RM" + priceCar + ".";
    }

    @Override
    public void getPrice(Customer c) {
        System.out.println(toString());
        if (c.getSalary() > 8000) {
            this.buy = c;
            System.out.println("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " can buy this car. Your salary is higher than the price's car.");
        } else {
            System.out.println("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " cannot buy this car.");
        }
    }
}

// Main Driver Class with Java Swing UI
public class PriceApp extends JFrame {
    private JTextField custNameInput;
    private JTextField custSalaryInput;
    private JComboBox<String> itemTypeSelect;
    private JTextField itemNameInput;
    private JTextField itemPriceInput;
    private JTextArea consoleOutput;

    public PriceApp() {
        setTitle("Product & Car Pricing Eligibility Visualizer");
        setSize(580, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form Inputs Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Customer & Item Details"));

        inputPanel.add(new JLabel(" Customer Name:"));
        custNameInput = new JTextField("Sally");
        inputPanel.add(custNameInput);

        inputPanel.add(new JLabel(" Customer Salary (RM):"));
        custSalaryInput = new JTextField("3000.00");
        inputPanel.add(custSalaryInput);

        inputPanel.add(new JLabel(" Product Category:"));
        itemTypeSelect = new JComboBox<>(new String[]{"SmartPhone", "Laptop", "Car"});
        inputPanel.add(itemTypeSelect);

        inputPanel.add(new JLabel(" Item Name / Brand:"));
        itemNameInput = new JTextField("Iphone 14 ProMax");
        inputPanel.add(itemNameInput);

        inputPanel.add(new JLabel(" Item Price (RM):"));
        itemPriceInput = new JTextField("5500.00");
        inputPanel.add(itemPriceInput);

        JButton checkBtn = new JButton("Check Affordability");
        JButton runDemoBtn = new JButton("Run Assignment Demo");
        inputPanel.add(checkBtn);
        inputPanel.add(runDemoBtn);

        add(inputPanel, BorderLayout.NORTH);

        // Console Output Area
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(consoleOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Console Log Output"));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Clear Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clearBtn = new JButton("Clear Console Log");
        bottomPanel.add(clearBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        checkBtn.addActionListener(e -> checkEligibility());
        runDemoBtn.addActionListener(e -> runAssignmentDemo());
        clearBtn.addActionListener(e -> consoleOutput.setText(""));
    }

    private void checkEligibility() {
        try {
            String custName = custNameInput.getText().trim();
            double salary = Double.parseDouble(custSalaryInput.getText().trim());
            String itemType = (String) itemTypeSelect.getSelectedItem();
            String itemName = itemNameInput.getText().trim();
            double price = Double.parseDouble(itemPriceInput.getText().trim());

            Customer customer = new Customer(custName, salary);
            Price item;

            if ("SmartPhone".equals(itemType)) {
                item = new SmartPhone("SmartPhone", itemName, price);
            } else if ("Laptop".equals(itemType)) {
                item = new Laptop("Laptop", itemName, price);
            } else {
                item = new Car(itemName, price);
            }

            logItemPrice(item, customer);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Salary and Price.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logItemPrice(Price item, Customer c) {
        consoleOutput.append(item.toString() + "\n");
        if (item instanceof Product) {
            if (c.getSalary() > 4000) {
                consoleOutput.append("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " can buy this product.\n");
                consoleOutput.append("Your salary is higher than the price's product.\n");
            } else {
                consoleOutput.append("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " cannot buy this product.\n");
                consoleOutput.append("The product price is more than your salary.\n");
            }
        } else if (item instanceof Car) {
            if (c.getSalary() > 8000) {
                consoleOutput.append("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " can buy this car. Your salary is higher than the price's car.\n");
            } else {
                consoleOutput.append("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " cannot buy this car.\n");
            }
        }
        consoleOutput.append("-----------------------------------\n");
    }

    private void runAssignmentDemo() {
        consoleOutput.append("=== RUNNING PRICE APP MAIN DEMO LOGIC ===\n");

        SmartPhone sp = new SmartPhone("SmartPhone", "Iphone 14 ProMax", 5500.00);
        Laptop lp = new Laptop("Laptop", "Lenovo Yoga", 6000.00);
        Car c = new Car("BMW", 200000);

        Customer c1 = new Customer("Sally", 3000.00);
        Customer c2 = new Customer("Aaron", 8000.00);
        Customer c3 = new Customer("Simon", 10000.00);

        logItemPrice(sp, c1);
        logItemPrice(lp, c2);
        logItemPrice(c, c3);

        consoleOutput.append("=========================================\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PriceApp().setVisible(true);
        });
    }
}