package ePortfolio;

/**
 * this class makes an object of portfolio gui class.
 * this sets jframe visible
 */
public class EPortfolioMain {

    /**
     * Main method that makes a new portfolio object and runs the calls on it based
     * on the user input. It has the main command loop
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         String choice;
         PortfolioGUI gui = new PortfolioGUI();
         gui.setVisible(true);
    }

}
