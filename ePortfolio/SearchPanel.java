package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class makes a user interface for search panel.
 * it interacts with an instance of the portfolio to be able to call the search
 * method
 * it shows the result on the panel and has buttons for ease
 */
public class SearchPanel extends JPanel {
    // instance varibales
    private JTextField symbolField, nameField, priceFieldHigh, priceFieldLow;
    private JTextArea messageArea;
    private JButton resetButton, searchButton;
    private Portfolio portfolio;

    /**
     * non default constructor, makes a search investement interface
     * 
     * @param portfolio an instance of the portfolio class
     */
    public SearchPanel(Portfolio portfolio) {
        this.portfolio = portfolio;
        // Set layout for the panel
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        // header label
        JLabel header = new JLabel("Searching Investments", JLabel.LEFT);
        header.setFont(new Font("Serif", Font.BOLD, 16));
        header.setBackground(Color.WHITE);
        add(header, BorderLayout.NORTH);

        // panel for the labels, and user input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(true);
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 20);

        // investement Symbol label
        JLabel symbolLabel = new JLabel("Symbol");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(symbolLabel, gbc);

        // symbol input field
        symbolField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(symbolField, gbc);

        // name label
        JLabel nameLabel = new JLabel("<html>Name<br>Keyword</html>");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(nameLabel, gbc);

        // name input filed
        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(nameField, gbc);

        // lowprice label
        JLabel priceLabelLow = new JLabel("Low Price");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priceLabelLow, gbc);

        // lowprice input filed
        priceFieldLow = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priceFieldLow, gbc);

        // highprice label
        JLabel priceLabelHigh = new JLabel("High Price");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priceLabelHigh, gbc);

        // highprice input field
        priceFieldHigh = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priceFieldHigh, gbc);

        // setting size fot he panel to get uneven halves on the window
        inputPanel.setPreferredSize(new Dimension(300, 200));
        add(inputPanel, BorderLayout.WEST);

        // right panel for button, search and reset button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.WHITE);
        resetButton = new JButton("Reset");
        searchButton = new JButton("Search");

        // set preferred size for buttons
        resetButton.setMaximumSize(new Dimension(100, 30));
        searchButton.setMaximumSize(new Dimension(100, 30));

        // setting size to get uneven havlves, on the window
        buttonPanel.setPreferredSize(new Dimension(200, 200));

        // adding buttons to the panel with space in between
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createVerticalStrut(50)); // Add spacing
        buttonPanel.add(searchButton);
        add(buttonPanel, BorderLayout.EAST);

        // bottom panel for messages
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageArea = new JTextArea(8, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        // adding horizontal and vertical scrol bars
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        messagePanel.add(new JLabel("Search Results:"), BorderLayout.NORTH);
        messagePanel.setOpaque(true);
        messagePanel.setBackground(Color.WHITE);
        messagePanel.add(scrollPane, BorderLayout.CENTER);
        messagePanel.setPreferredSize(new Dimension(500, 200));

        // ddd messagePanel to the bottom of mainPanel
        add(messagePanel, BorderLayout.SOUTH);

        // add action listeners
        addActionListeners();
    }

    /**
     * implemets action listener on reset and sell button
     */
    private void addActionListeners() {
        // Reset button clears all input fields
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                symbolField.setText("");
                nameField.setText("");
                priceFieldHigh.setText("");
                priceFieldLow.setText("");
                messageArea.setText("");
            }
        });

        // search button processes the input
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String symbol = symbolField.getText().trim();
                String name = nameField.getText().trim();
                String lowerBound = priceFieldLow.getText().trim();
                String upperBound = priceFieldHigh.getText().trim();

                try {

                    // double priceLower = Double.parseDouble(lowerBound);
                    // double priceUpper = Double.parseDouble(upperBound);

                    // case for high price < low price
                    // if (priceLower > priceUpper) {
                    // messageArea.setText("Invalid high price");
                    // return;
                    // }
                    String result = portfolio.searchInvestement(symbol, name, lowerBound, upperBound);
                    messageArea.setText(result);
                    // Call backend method to process the purchase
                    // Replace with actual backend logic
                    // messageArea.setText("Bought " + quantity + " units of " + symbol + " (" +
                    // symbol + ") at $" + price);

                } catch (NumberFormatException ex) {
                    messageArea.setText("Invalid input. Input a valid integer for prices");
                }
            }
        });
    }
}
