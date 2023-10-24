import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton thinCrust;
    private JRadioButton regularCrust;
    private JRadioButton deepDishCrust;
    private JComboBox<String> sizeComboBox;
    private JCheckBox topping1;
    private JCheckBox topping2;
    private JCheckBox topping3;
    private JCheckBox topping4;
    private JCheckBox topping5;
    private JCheckBox topping6;
    private JTextArea orderTextArea;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }

    private void createGUI() {
        // Title
        JLabel titleLabel = new JLabel("Pizza Order Form");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Crust Panel
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Select Crust Type:"));
        crustPanel.setLayout(new GridLayout(3, 1));
        ButtonGroup crustGroup = new ButtonGroup();
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);
        add(crustPanel, BorderLayout.WEST);

        // Size Panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Select Pizza Size:"));
        String[] sizeOptions = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizeOptions);
        sizePanel.add(sizeComboBox);
        add(sizePanel, BorderLayout.EAST);

        // Toppings Panel
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Select Toppings:"));
        toppingsPanel.setLayout(new GridLayout(3, 2));
        topping1 = new JCheckBox("Mushrooms");
        topping2 = new JCheckBox("Pineapple");
        topping3 = new JCheckBox("Bacon");
        topping4 = new JCheckBox("Banana Peppers");
        topping5 = new JCheckBox("Pepperoni");
        topping6 = new JCheckBox("Onions");
        toppingsPanel.add(topping1);
        toppingsPanel.add(topping2);
        toppingsPanel.add(topping3);
        toppingsPanel.add(topping4);
        toppingsPanel.add(topping5);
        toppingsPanel.add(topping6);
        add(toppingsPanel, BorderLayout.CENTER);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        statsPanel.setLayout(new GridLayout(1, 4));
        orderTextArea = new JTextArea(10, 25);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        JButton clearButton = new JButton("Clear");
        JButton orderButton = new JButton("Order");
        JButton quitButton = new JButton("Quit");
        statsPanel.add(scrollPane);
        statsPanel.add(clearButton);
        statsPanel.add(orderButton);
        statsPanel.add(quitButton);
        add(statsPanel, BorderLayout.SOUTH);

        // Action Listeners
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crustGroup.clearSelection();
                sizeComboBox.setSelectedIndex(0);
                topping1.setSelected(false);
                topping2.setSelected(false);
                topping3.setSelected(false);
                topping4.setSelected(false);
                topping5.setSelected(false);
                topping6.setSelected(false);
                orderTextArea.setText("");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(PizzaGUIFrame.this, "Are you sure you want to quit?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if crust and size are selected
                if (crustGroup.getSelection() == null || sizeComboBox.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(PizzaGUIFrame.this, "Please select a crust type and pizza size.");
                    return;
                }

                double subTotal = calculateSubTotal();
                double tax = 0.07 * subTotal;
                double total = subTotal + tax;

                // Create the order text
                DecimalFormat df = new DecimalFormat("0.00");
                String orderText = String.format("Type of Crust & Size Price\n%s %s $%.2f\n\nIngredient Price\n",
                        crustGroup.getSelection().getActionCommand(),
                        sizeComboBox.getSelectedItem(),
                        subTotal);
                String ingredients = getSelectedIngredients();
                String receipt = String.format(
                        "Sub-total: $%.2f\nTax: $%.2f\n---------------------\nTotal: $%.2f",
                        subTotal, tax, total);

                // Display the order in the JTextArea
                orderTextArea.setText(orderText + ingredients + receipt);
            }
        });
    }

    private double calculateSubTotal() {
        double basePrice = 0;
        if (sizeComboBox.getSelectedIndex() == 0) {
            basePrice = 8.0;
        } else if (sizeComboBox.getSelectedIndex() == 1) {
            basePrice = 12.0;
        } else if (sizeComboBox.getSelectedIndex() == 2) {
            basePrice = 16.0;
        } else if (sizeComboBox.getSelectedIndex() == 3) {
            basePrice = 20.0;
        }
        return basePrice + getSelectedIngredientsCount() * 1.0;
    }

    private String getSelectedIngredients() {
        StringBuilder selectedIngredients = new StringBuilder();
        if (topping1.isSelected()) {
            selectedIngredients.append("Mushrooms: $1.00\n");
        }
        if (topping2.isSelected()) {
            selectedIngredients.append("Pineapple: $1.00\n");
        }
        if (topping3.isSelected()) {
            selectedIngredients.append("Bacon: $1.00\n");
        }
        if (topping4.isSelected()) {
            selectedIngredients.append("Banana Peppers: $1.00\n");
        }
        if (topping5.isSelected()) {
            selectedIngredients.append("Pepperoni: $1.00\n");
        }
        if (topping6.isSelected()) {
            selectedIngredients.append("Onions: $1.00\n");
        }
        return selectedIngredients.toString();
    }

    private int getSelectedIngredientsCount() {
        int count = 0;
        if (topping1.isSelected()) {
            count++;
        }
        if (topping2.isSelected()) {
            count++;
        }
        if (topping3.isSelected()) {
            count++;
        }
        if (topping4.isSelected()) {
            count++;
        }
        if (topping5.isSelected()) {
            count++;
        }
        if (topping6.isSelected()) {
            count++;
        }
        return count;
    }
}
