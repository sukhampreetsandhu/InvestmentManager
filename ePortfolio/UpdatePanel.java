package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class makes user interface for update panel
 * it also interacts with an instance of the portfoilo in order to move back and
 * forth in the list to
 * update the prices of an investement and save.
 * It implements necessary action listeners to the buttons to able to click back
 * and forth and save
 */
public class UpdatePanel extends JPanel {
    private JTextField symbolField, nameField, priceField;
    private JTextArea messageArea;
    private JButton prevButton, nextButton, saveButton;
    private Portfolio portfolio;
    private int currentIndex = 0;

    /**
     * non default constructtor for updating price interface
     * 
     * @param portfolio an instance of the portfolio class
     */
    public UpdatePanel(Portfolio portfolio) {
        this.portfolio = portfolio;
        // Set layout for the panel
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        // header label
        JLabel header = new JLabel("Selling an Investment", JLabel.LEFT);
        header.setFont(new Font("Serif", Font.BOLD, 16));
        header.setBackground(Color.WHITE);
        add(header, BorderLayout.NORTH);

        // panel for the labels, and user input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(true);
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 20);

        // symbol label
        JLabel symbolLabel = new JLabel("Symbol");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(symbolLabel, gbc);

        // symbol input field, non editable
        symbolField = new JTextField(10);
        symbolField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(symbolField, gbc);

        // name label
        JLabel nameLabel = new JLabel("Quantity");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(nameLabel, gbc);

        // name input filed, non editable
        nameField = new JTextField(7);
        nameField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(nameField, gbc);

        // price label
        JLabel priceLabel = new JLabel("Price");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priceLabel, gbc);

        // price input field
        priceField = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priceField, gbc);

        // setting the size for the panel to get uneven halves ont he window
        inputPanel.setPreferredSize(new Dimension(300, 200));
        add(inputPanel, BorderLayout.WEST);

        // right panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.WHITE);
        // making buttons
        prevButton = new JButton("Prev");
        nextButton = new JButton("Next");
        saveButton = new JButton("Save");

        // Set preferred size for buttons
        prevButton.setMaximumSize(new Dimension(100, 30));
        nextButton.setMaximumSize(new Dimension(100, 30));
        saveButton.setMaximumSize(new Dimension(100, 30));

        // adding buttons to the panel with space in between
        buttonPanel.setPreferredSize(new Dimension(200, 200));
        buttonPanel.add(prevButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Add spacing
        buttonPanel.add(nextButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Add spacing
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.EAST);

        // bottom panel for messages
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageArea = new JTextArea(8, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        // adding horizontal and vertical scrol bars
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        messagePanel.add(new JLabel("Messages:"), BorderLayout.NORTH);
        messagePanel.setOpaque(true);
        messagePanel.setBackground(Color.WHITE);
        messagePanel.add(scrollPane, BorderLayout.CENTER);
        messagePanel.setPreferredSize(new Dimension(500, 200));

        // add messagePanel to the bottom of mainPanel
        add(messagePanel, BorderLayout.SOUTH);

        // add action listeners
        addActionListeners();
    }

    /**
     * updates the text fields of the panel
     */
    public void updateFields() {
        // gets current investement and sets filed accordingly
        Investement currentInvestment = portfolio.getInvestments().get(currentIndex);
        System.out.println(currentInvestment);
        symbolField.setText(currentInvestment.getSymbol()); // Programmatically update
        nameField.setText(String.valueOf(currentInvestment.getName())); // Programmatically update
        priceField.setText(String.valueOf(currentInvestment.getPrice()));
        messageArea.setText("Viewing investment: " + currentInvestment.getName());
    }

    /**
     * implements action listener for next, prev and svae buttons
     */
    private void addActionListeners() {
        // Prev button, decrases index
        prevButton.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateFields();
            } else {
                messageArea.setText("Already at the first investment.");
            }
        });

        // Next button, increments index
        nextButton.addActionListener(e -> {
            if (currentIndex < portfolio.getInvestments().size() - 1) {
                currentIndex++;
                updateFields();
            } else {
                messageArea.setText("Already at the last investment.");
            }
        });

        // Save button, calls the method to update the price
        saveButton.addActionListener(e -> {
            String priceText = priceField.getText().trim();

            // price input case
            if (priceText.isEmpty()) {
                messageArea.setText("Error: Price cannot be empty.");
                return;
            }

            // try-catch for invalid input
            try {
                double newPrice = Double.parseDouble(priceText);
                // neative price input case
                if (newPrice <= 0) {
                    messageArea.setText("Error: Price must be greater than 0.");
                    return;
                }
                // update the price of the current investment
                portfolio.getInvestments().get(currentIndex).update(newPrice);
                messageArea.setText("Updated price for " + portfolio.getInvestments().get(currentIndex).getSymbol()
                        + " to $" + newPrice);

            } catch (NumberFormatException ex) {
                // exception for any string input
                messageArea.setText("Error: Please eneter a valid positive number.");
            }
        });
    }

}
