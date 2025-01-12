package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * this class makes user interface for get gain panel
 * it also interacts with an instance of the portfoilo in order to display the total gain and individuals gains
 */
public class GainPanel extends JPanel {
    // instance varibales
    private JTextField totalGainField;
    private JTextArea messageArea;
    private String resultMssg;
    private Portfolio portfolio;
    private String result;

    
    /**
     * non default constructor for getgain interface
     * @param portfolio an instance of the portfolio class
     */
    public GainPanel(Portfolio portfolio) {
        this.portfolio = portfolio;
        // Set layout for the panel
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        // heading label
        JLabel header = new JLabel("Getting total gain", JLabel.LEFT);
        header.setFont(new Font("Serif", Font.BOLD, 16));
        header.setBackground(Color.WHITE);
        add(header, BorderLayout.NORTH);

        // panel for the labels, and user input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(true);
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5,20);

        // total gain label
        JLabel totalGainLabel = new JLabel("Total Gain");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(totalGainLabel, gbc);

        // total gain display field, non editable
        totalGainField = new JTextField(15);
        totalGainField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        

        // add above field to input panel
        inputPanel.add(totalGainField, gbc);
        // add inputpanel to mainpanel
        add(inputPanel,BorderLayout.WEST);

        //bottom panel for messages
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageArea = new JTextArea(8, 30);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        // adding horizontal and vertical scrol bars
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        messagePanel.add(new JLabel("Individual Gains"), BorderLayout.NORTH);
        messagePanel.setOpaque(true);
        messagePanel.setBackground(Color.WHITE);
        messagePanel.add(scrollPane, BorderLayout.CENTER);

        // setting size for the messages area,to take more space of the window
        messagePanel.setPreferredSize(new Dimension(500,300));
        resultMssg = portfolio.getIndividualGains();
        messageArea.setText(resultMssg);

        //add messagePanel to the bottom of mainPanel
        add(messagePanel, BorderLayout.SOUTH);
    }

    /**
     * puts gain on the panel
     */
    public void findGain(){
        // foramtter for $00,000
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        // gets the total gain
        result = formatter.format(portfolio.getGain());
        totalGainField.setText(result);
        // individuals gains
        resultMssg = portfolio.getIndividualGains();
        messageArea.setText(resultMssg);
    }
}

