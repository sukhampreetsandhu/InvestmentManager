package ePortfolio;

import java.util.Objects;
/**
 * The Stock class is a sub class of Investement represents a Stock investment in a financial portfolio.
 * It contains attributes such as the symbol, name, quantity, price, and book value of the stock.
 * The class also includes a constant commision fee, which is applied when selling and buying the stock.
 *
 * It provides methods for buy and printing the stock's attributes.
 *
 * This class does not allow for changes to the commision fee once it is set, as it is a constant.
 */
public class Stock extends Investement {

    /**
     * This is constant fee for stock (applied while buying and selling) and cannot be changed
     */
    public static final double COMMISSION = 9.99;

    /**
     * Non default constructor. Requires all 4 instance fields to be provided as
     * input
     * 
     * @param symbol   stock symbol
     * @param name     stock name
     * @param quantity stock quantity
     * @param price    sock price
     */
    public Stock(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        // finds the bookValue
        this.bookValue = (price * quantity) + COMMISSION;
        this.fee = COMMISSION;
    }
    
    /**
     * Buy additional shares of stock
     * 
     * @param quantity  more quantity that is bought
     * @param price     the price at which they are bought
     */
    @Override
    public void buy(int quantity, double price) {
        double moreCost = quantity * price + COMMISSION;
        this.bookValue += moreCost;
        this.quantity += quantity;
        this.price = price;
    }

    /**
     * calcuates the gain on the stock
     * 
     * @return gain of the stock
     */
    @Override
    public double getGain() {
        double gain = ((this.price * this.quantity) - COMMISSION) - this.bookValue;
        return gain;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stock temp = (Stock) obj;
        // checks the symbol attribute of both stocks
        if (!Objects.equals(this.symbol, temp.symbol)) {
            return false;
        }
        return true;
    }

    /**
     * Adding word Stock for the Stock toString beofre the super toString call
     */
    @Override
    public String toString() {
        return "Stock\n" + super.toString();
    }
}