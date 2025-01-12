package ePortfolio;

import java.text.DecimalFormat;

public abstract class Investement {
    // instance varibles
    protected String symbol;
    protected String name;
    protected int quantity;
    protected double price;
    protected double bookValue;
    protected double fee;

    /**
     * @param symbol   investement symbol
     * @param name     investement name
     * @param quantity investement quantity
     * @param price    investement price
     */
    public Investement(String symbol, String name, int quantity, double price) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = price * quantity;
    }

    /**
     * @return investement symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return investement name
     */
    public String getName() {
        return name;
    }

    /**
     * @return investement quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return investement price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return investement bookValue
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * changes the price of the Investement
     * 
     * @param newPrice updated price
     */
    public void update(double newPrice) {
        this.price = newPrice;
    }

    /**
     * @param bookvalue bookValue if different form calculation (read form file)
     */
    public void updateBookValue(double bookvalue) {
        this.bookValue = bookvalue;
    }

    public abstract void buy(int quantity, double price);
    // public abstract void sell(int sellAmount, double sellPrice);

    /**
     * sells specified amount and price for investement, adjusting the quanity and
     * calulates gains
     * 
     * @param sellAmount the amount of how much to be sold
     * @param sellPrice  the price at which the quantity will be sold
     */
    public String sell(int sellAmount, double sellPrice) {
        StringBuilder returnMessage = new StringBuilder();
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");

        // return if selling amount is greater than what is owned
        if (sellAmount > this.quantity) {
            return returnMessage.append("Not enough quantity to sell").toString();
        }
        // is selling amount less than the current holding shares (partially seling
        // funds)
        if (sellAmount < this.quantity) {
            // calcuates payement
            double payement = sellAmount * sellPrice - fee;
            // safe storing of decrease in share holdings
            int remain = this.quantity - sellAmount;
            double bookValueRemaining = this.bookValue * ((double) remain / this.quantity);
            // display to the terminal
            returnMessage.append("BookValue Remaining is " + formatter.format(bookValueRemaining) + "\n");
            double bookValueSold = this.bookValue - bookValueRemaining;
            double gain = payement - bookValueSold;
            // display to the terminal
            returnMessage.append("Payement: " + formatter.format(payement) + "\nAnd Gain: " + formatter.format(gain));
            // updates the quantity and the price(based on selling price)
            this.quantity = remain;
            update(sellPrice);
            return returnMessage.toString();
        } else {
            // selling all of the fund holdings
            double payement = this.quantity * sellPrice - fee;
            double gain = payement - this.bookValue;
            return returnMessage
                    .append("Sold all quantity and your\nPayement: " + formatter.format(payement) + "\nAnd Gain: "
                            + formatter.format(gain))
                    .toString();
        }
        // return "Error";
    }

    public abstract double getGain();

    /**
     * Custom toString method for the Investement
     */
    @Override
    public String toString() {
        // decimal formatting for the price and booValue
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        // no decimal for quantity, it is an integer
        DecimalFormat formatQty = new DecimalFormat("#,##0");
        return "symbol = " + this.symbol + "\nname = " + this.name + "\nquantity = "
                + formatQty.format(this.quantity)
                + "\nprice = " + formatter.format(this.price) + "\nbookvalue = " + formatter.format(this.bookValue)
                + "\n";
    }
}