package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class makes user interface for buy panel
 * it intercats with an instance of the portfolio to call the buy method
 * it shows the user a summary of what actions were done on the panel
 */
public class BuyPanel extends JPanel {
    // instance varibles
    private JComboBox<String> investmentType;
    private JTextField symbolField, nameField, quantityField, priceField;
    private JTextArea messageArea;
    private JButton resetButton, buyButton;
    private Portfolio portfolio;
    private String resultMssg;
    // private int quantity;
    // private double price;

    /**
     * This constructor puts all the labels, textFields and buttons on the Buy Panel
     * interface
     * 
     * @param portfolio an instance of the portfolio class
     */
    public BuyPanel(Portfolio portfolio) {
        this.portfolio = portfolio;

        // set layout for the panel
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        // header label
        JLabel header = new JLabel("Buying an Investment", JLabel.LEFT);
        header.setFont(new Font("Serif", Font.BOLD, 16));
        header.setBackground(Color.WHITE);
        add(header, BorderLayout.NORTH);

        // panel to put input fileds along with labels using GridBag layout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(true);
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // investment Type label
        JLabel typeLabel = new JLabel("Type");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(typeLabel, gbc);

        // investment type dropdown selection
        investmentType = new JComboBox<>(new String[] { "Stock", "MutualFund" });
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(investmentType, gbc);

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

        // name field
        JLabel nameLabel = new JLabel("Name");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(nameLabel, gbc);

        // name input field
        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(nameField, gbc);

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

        // to have unequal halves for the main panel
        inputPanel.setPreferredSize(new Dimension(300, 200));
        add(inputPanel, BorderLayout.WEST);

        // right panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.WHITE);
        resetButton = new JButton("Reset");
        buyButton = new JButton("Buy");

        // setting preferred size for buttons
        resetButton.setMaximumSize(new Dimension(100, 30));
        buyButton.setMaximumSize(new Dimension(100, 30));

        // setting the size for the button panel to get unequal halves of the window
        buttonPanel.setPreferredSize(new Dimension(200, 200));

        // adding buttons with spacing between them
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(buyButton);
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
     * implemetns action listerner for both reset and buy button for the buy panel
     * interface
     */
    private void addActionListeners() {
        // reset button clears all input fields
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                investmentType.setSelectedIndex(0);
                symbolField.setText("");
                nameField.setText("");
                quantityField.setText("");
                priceField.setText("");
                messageArea.setText("");
            }
        });

        // buy button processes the input
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) investmentType.getSelectedItem();
                String symbol = symbolField.getText().trim();
                String name = nameField.getText().trim();
                String quantityText = quantityField.getText().trim();
                String priceText = priceField.getText().trim();

                // input validations for all the fields, cannot be empty
                if (symbol.isEmpty()) {
                    messageArea.setText("Error: Symbol cannot be empty.");
                    return;
                }
                if (name.isEmpty()) {
                    messageArea.setText("Error: Name cannot be empty.");
                    return;
                }
                if (quantityText.isEmpty()) {
                    messageArea.setText("Error: quantity cannot be empty.");
                    return;
                }
                if (priceText.isEmpty()) {
                    messageArea.setText("Error: price cannot be empty.");
                    return;
                }

                // calls the buy method and prints what was bought to the messages area
                resultMssg = portfolio.buyInvestement(type, symbol, name, quantityText, priceText);
                messageArea.setText(resultMssg);

            }
        });
    }
}
