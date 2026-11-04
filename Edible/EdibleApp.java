import javax.swing.*;
import java.awt.*;

// f) Interface Edible
interface Edible {
    String howToEat();
}

// g) Class Animal
class Animal implements Edible {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public String howToEat() {
        return name + ". " + name + " needs to be cooked first before eating.";
    }
}

// h) Abstract Class Fruit
abstract class Fruit implements Edible {
    protected String name;

    public Fruit(String name) {
        this.name = name;
    }
}

// i) Class Orange
class Orange extends Fruit {
    public Orange(String name) {
        super(name);
    }

    @Override
    public String howToEat() {
        return name + ". " + name + " can make orange butter cake.";
    }
}

// j) Class Apple
class Apple extends Fruit {
    public Apple(String name) {
        super(name);
    }

    @Override
    public String howToEat() {
        return name + ". " + name + " can make apple pie.";
    }
}

// Helper Class Person
class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String eating(Edible eat) {
        return name + " is eating " + eat.howToEat();
    }
}

// Main Driver Class with Java Swing UI
public class EdibleApp extends JFrame {
    private JComboBox<String> personSelect;
    private JComboBox<String> itemTypeSelect;
    private JTextField nameInput;
    private JTextArea consoleOutput;

    public EdibleApp() {
        setTitle("Edible Polymorphism Visualizer");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form Controls Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Simulate Person Eating"));

        inputPanel.add(new JLabel(" Select Person:"));
        personSelect = new JComboBox<>(new String[]{"Ali", "Sarah"});
        inputPanel.add(personSelect);

        inputPanel.add(new JLabel(" Select Edible Type:"));
        itemTypeSelect = new JComboBox<>(new String[]{"Animal", "Apple", "Orange"});
        inputPanel.add(itemTypeSelect);

        inputPanel.add(new JLabel(" Item Name / Label:"));
        nameInput = new JTextField();
        inputPanel.add(nameInput);

        JButton eatBtn = new JButton("Trigger eating()");
        JButton runDemoBtn = new JButton("Run Main Assignment Demo");
        inputPanel.add(eatBtn);
        inputPanel.add(runDemoBtn);

        add(inputPanel, BorderLayout.NORTH);

        // Display Area
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
        eatBtn.addActionListener(e -> triggerEating());
        runDemoBtn.addActionListener(e -> runMainDemo());
        clearBtn.addActionListener(e -> consoleOutput.setText(""));
    }

    private void triggerEating() {
        String personName = (String) personSelect.getSelectedItem();
        String type = (String) itemTypeSelect.getSelectedItem();
        String customName = nameInput.getText().trim();

        String defaultName = type.equals("Animal") ? "Chicken" : (type.equals("Apple") ? "Red Apple" : "Navel Orange");
        String finalName = customName.isEmpty() ? defaultName : customName;

        Edible item;
        if ("Animal".equals(type)) {
            item = new Animal(finalName);
        } else if ("Apple".equals(type)) {
            item = new Apple(finalName);
        } else {
            item = new Orange(finalName);
        }

        Person p = new Person(personName);
        consoleOutput.append(p.eating(item) + "\n");
        nameInput.setText("");
    }

    private void runMainDemo() {
        consoleOutput.append("=== RUNNING EDIBLE APP MAIN DEMO LOGIC ===\n");
        
        Edible[] objects = {
            new Animal("Chicken"),
            new Apple("Red Apple"),
            new Orange("Navel Orange")
        };

        Person p1 = new Person("Ali");
        Person p2 = new Person("Sarah");

        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof Animal) {
                consoleOutput.append(p1.eating(objects[i]) + "\n");
            } else {
                consoleOutput.append(p2.eating(objects[i]) + "\n");
            }
        }
        consoleOutput.append("=========================================\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EdibleApp().setVisible(true);
        });
    }
}