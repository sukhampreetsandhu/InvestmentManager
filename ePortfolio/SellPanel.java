package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class makes a user interface for sell panel
 * it handles the user interaction of when user want to sell/reset the panel
 * it also interacts with an instance of portfolio, to call the sell method when
 * requested
 */
public class SellPanel extends JPanel {
    // instance varibales
    private JTextField symbolField, quantityField, priceField;
    private JTextArea messageArea;
    private JButton resetButton, sellButton;
    private Portfolio portfolio;
    private String resultMssg;

    /**
     * non default constructor to make the search interface
     * 
     * @param portfolio an instance of the portfolio class
     */
    public SellPanel(Portfolio portfolio) {
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

        // panel to put labels and user input fileds
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
        // symbol input field
        symbolField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(symbolField, gbc);

        // quantity label
        JLabel quantityLabel = new JLabel("Quantity");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(quantityLabel, gbc);

        // quantity input field
        quantityField = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(quantityField, gbc);

        // pric label
        JLabel priceLabel = new JLabel("Price");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priceLabel, gbc);

        // price intput field
        priceField = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(priceField, gbc);

        // setting size for the panel to get uneven halves on the window
        inputPanel.setPreferredSize(new Dimension(300, 200));
        add(inputPanel, BorderLayout.WEST);

        // right panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.WHITE);
        // making buttons
        resetButton = new JButton("Reset");
        sellButton = new JButton("Sell");
        // Set preferred size for buttons
        resetButton.setMaximumSize(new Dimension(100, 30));
        sellButton.setMaximumSize(new Dimension(100, 30));

        // setting size of the pabel to get unveven halves on the window
        buttonPanel.setPreferredSize(new Dimension(200, 200));
        // adding buttons to panel with spacing in between
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(sellButton);
        add(buttonPanel, BorderLayout.EAST);

        // bottom panel for messages
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageArea = new JTextArea(8, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        // adding scroll bars
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
     * implements action listerns on reset and sll button
     */
    private void addActionListeners() {
        // reset button clears all input fields
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                symbolField.setText("");
                quantityField.setText("");
                priceField.setText("");
                messageArea.setText("");
            }
        });

        // sell button processes the input
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String symbol = symbolField.getText().trim();
                String quantityText = quantityField.getText().trim();
                String priceText = priceField.getText().trim();

                // Validate inputs
                if (symbol.isEmpty()) {
                    messageArea.setText("Error: Symbol cannot be empty.");
                    return;
                }
                if (quantityText.isEmpty()) {
                    messageArea.setText("Error: Quantity cannot be empty.");
                    return;
                }
                if (priceText.isEmpty()) {
                    messageArea.setText("Error: Price cannot be empty.");
                    return;
                }

                resultMssg = portfolio.sellInvestement(symbol, quantityText, priceText);
                messageArea.setText(resultMssg);

            }
        });
    }
}
