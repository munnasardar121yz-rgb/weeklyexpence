import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WeeklyExpenseTracker extends JFrame {

    // Store expenses
    private ArrayList<Expense> expenses = new ArrayList<>();

    // GUI Components
    private JComboBox<String> dayBox;
    private JComboBox<String> categoryBox;
    private JTextField amountField;
    private JTextField descriptionField;
    private JTextArea outputArea;

    // Constructor
    public WeeklyExpenseTracker() {

        // Frame settings
        setTitle("Weekly Expense Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();

        // FIXED: Changed GridLayout rows
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        // Day dropdown
        JLabel dayLabel = new JLabel("Select Day:");

        String[] days = {
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        };

        dayBox = new JComboBox<>(days);

        // Category dropdown
        JLabel categoryLabel =
                new JLabel("Select Category:");

        String[] categories = {
                "Groceries",
                "Eating Out",
                "Petrol",
                "Taxi",
                "Bills",
                "Rent",
                "Others"
        };

        categoryBox = new JComboBox<>(categories);

        // Amount
        JLabel amountLabel =
                new JLabel("Enter Amount:");

        amountField = new JTextField();

        // Description
        JLabel descriptionLabel =
                new JLabel("Description:");

        descriptionField = new JTextField();

        // Buttons
        JButton addButton =
                new JButton("Add Expense");

        JButton totalButton =
                new JButton("Total Weekly Expense");

        JButton viewButton =
                new JButton("View By Category");

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(outputArea);

        // Add components to panel
        panel.add(dayLabel);
        panel.add(dayBox);

        panel.add(categoryLabel);
        panel.add(categoryBox);

        panel.add(amountLabel);
        panel.add(amountField);

        panel.add(descriptionLabel);
        panel.add(descriptionField);

        panel.add(addButton);
        panel.add(totalButton);

        panel.add(viewButton);

        // FIXED: Empty label added
        panel.add(new JLabel(""));

        // Add panel and output area
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add Expense Button Action
        addButton.addActionListener(
                new ActionListener() {

            @Override
            public void actionPerformed(
                    ActionEvent e) {

                addExpense();
            }
        });

        // Total Expense Button Action
        totalButton.addActionListener(
                new ActionListener() {

            @Override
            public void actionPerformed(
                    ActionEvent e) {

                showTotalExpense();
            }
        });

        // View By Category Button Action
        viewButton.addActionListener(
                new ActionListener() {

            @Override
            public void actionPerformed(
                    ActionEvent e) {

                viewExpensesByCategory();
            }
        });
    }

    // Method to add expense
    private void addExpense() {

        try {

            String day =
                    (String) dayBox.getSelectedItem();

            String category =
                    (String) categoryBox.getSelectedItem();

            String description =
                    descriptionField.getText();

            // Check empty amount
            if (amountField.getText()
                    .trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount field cannot be empty."
                );

                return;
            }

            double amount =
                    Double.parseDouble(
                            amountField.getText());

            // Check negative amount
            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter Valid Amount."
                );

                return;
            }

            // Create expense object
            Expense expense =
                    new Expense(
                            day,
                            category,
                            amount,
                            description
                    );

            // Add to list
            expenses.add(expense);

            JOptionPane.showMessageDialog(
                    this,
                    "Expense Added Successfully!"
            );

            // Clear fields
            amountField.setText("");
            descriptionField.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number."
            );
        }
    }

    // Method to show total expense
    private void showTotalExpense() {

        double total = 0;

        for (Expense expense : expenses) {

            // FIXED: Getter method used
            total += expense.getAmount();
        }

        outputArea.setText(
                "Total Weekly Expense: $" + total
        );
    }

    // Method to group expenses by category
    private void viewExpensesByCategory() {

        HashMap<String, Double> categoryTotals =
                new HashMap<>();

        for (Expense expense : expenses) {

            // FIXED: Getter method used
            String category =
                    expense.getCategory();

            if (categoryTotals.containsKey(category)) {

                double oldAmount =
                        categoryTotals.get(category);

                categoryTotals.put(
                        category,
                        oldAmount + expense.getAmount()
                );

            } else {

                categoryTotals.put(
                        category,
                        expense.getAmount()
                );
            }
        }

        outputArea.setText(
                "Expenses Grouped By Category:\n\n"
        );

        for (String category :
                categoryTotals.keySet()) {

            outputArea.append(
                    category +
                    " : $" +
                    categoryTotals.get(category) +
                    "\n"
            );
        }
    }

    // Expense class
    class Expense {

        private String day;
        private String category;
        private double amount;
        private String description;

        // Constructor
        public Expense(
                String day,
                String category,
                double amount,
                String description
        ) {

            this.day = day;
            this.category = category;
            this.amount = amount;
            this.description = description;
        }

        // Getter methods
        public String getDay() {
            return day;
        }

        public String getCategory() {
            return category;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }
    }

    // Main method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                new Runnable() {

            @Override
            public void run() {

                new WeeklyExpenseTracker()
                        .setVisible(true);
            }
        });
    }
}