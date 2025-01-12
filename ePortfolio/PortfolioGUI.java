package ePortfolio;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PortfolioGUI class implemts user interface.
 * handles the switching between interfaces
 * makes an instance of the portfolio so that other panels can interact with it
 */
public class PortfolioGUI extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    // instance varibales
    private CardLayout cardLayout;
    private JLabel message;
    private JTextArea intro;
    private JPanel mainPanel;
    Portfolio profile = new Portfolio();
    GainPanel gainPanel = new GainPanel(profile);
    UpdatePanel updatePanel = new UpdatePanel(profile);
    BuyPanel buyPanel = new BuyPanel(profile);
    SellPanel sellPanel = new SellPanel(profile);
    SearchPanel searchPanel = new SearchPanel(profile);

    /**
     * implements action lister to the jmeanu bar options
     */
    private class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "Buy":
                    cardLayout.show(mainPanel, "Buy");
                    break;
                case "Sell":
                    cardLayout.show(mainPanel, "Sell");
                    break;
                case "Update":
                    cardLayout.show(mainPanel, "Update");
                    if (profile.getInvestments().size() > 0) {
                        updatePanel.updateFields();
                    }
                    break;
                case "getGain":
                    cardLayout.show(mainPanel, "getGain");
                    gainPanel.findGain();
                    break;
                case "Search":
                    cardLayout.show(mainPanel, "Search");
                    break;
                case "quit":
                    System.exit(0);
            }
        }
    }

    /**
     * non default constructer that makes the main windows of the application
     * adds all the different user interface panels to the window
     */
    public PortfolioGUI() {
        super("ePortfolio");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // menue bar
        JMenu commandsMenu = new JMenu("Commands");
        JMenuItem buyCommand = new JMenuItem("Buy");
        JMenuItem sellCommand = new JMenuItem("Sell");
        JMenuItem updateCommand = new JMenuItem("Update");
        JMenuItem getGainCommand = new JMenuItem("getGain");
        JMenuItem searchCommand = new JMenuItem("Search");
        JMenuItem quitCommand = new JMenuItem("quit");
        commandsMenu.add(buyCommand);
        commandsMenu.add(sellCommand);
        commandsMenu.add(updateCommand);
        commandsMenu.add(getGainCommand);
        commandsMenu.add(searchCommand);
        commandsMenu.add(quitCommand);

        // create an instance of MenuActionListener
        MenuActionListener menuActionListener = new MenuActionListener();

        // add the action listener to each menu item
        buyCommand.addActionListener(menuActionListener);
        sellCommand.addActionListener(menuActionListener);
        updateCommand.addActionListener(menuActionListener);
        getGainCommand.addActionListener(menuActionListener);
        searchCommand.addActionListener(menuActionListener);
        quitCommand.addActionListener(menuActionListener);

        JMenuBar bar = new JMenuBar();
        bar.add(commandsMenu);

        setJMenuBar(bar);

        // create intro panel
        JPanel introPanel = new JPanel();
        introPanel.setLayout(new BorderLayout());

        // title label
        message = new JLabel("Welcome to ePortfolio", SwingConstants.LEFT);
        message.setFont(new Font("Serif", Font.BOLD, 18)); // Larger font for title
        message.setBorder(BorderFactory.createEmptyBorder(50, 10, 20, 30)); // Padding
        message.setOpaque(true);
        message.setBackground(Color.WHITE);
        introPanel.add(message, BorderLayout.NORTH);

        // text for main panel
        intro = new JTextArea();
        intro.setText(
                "Choose a command from the “Commands” menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.");
        intro.setFont(new Font("Serif", Font.BOLD, 12));
        intro.setLineWrap(true);
        intro.setWrapStyleWord(true);
        intro.setEditable(false);

        // wrap JTextArea in a scroll pane
        JScrollPane scrollPane = new JScrollPane(intro);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        introPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the intro panel to the main frame
        mainPanel.add(introPanel, "Intro Panel");
        mainPanel.add(buyPanel, "Buy");
        mainPanel.add(sellPanel, "Sell");
        mainPanel.add(searchPanel, "Search");
        mainPanel.add(updatePanel, "Update");
        mainPanel.add(gainPanel, "getGain");

        add(mainPanel);
    }

}
