package ePortfolio;

import java.util.Objects;

/**
 * The MutualFund class represents a mutual fund investment in a financial portfolio.
 * It contains attributes such as the symbol, name, quantity, price, and book value of the mutual fund.
 * The class also includes a constant redemption fee, which is applied when selling the mutual fund.
 * 
 * It provides methods for retrieving the mutual fund's attributes.
 * 
 * This class does not allow for changes to the redemption fee once it is set, as it is a constant.
 */
public class MutualFund extends Investement{
    
    /**
     * This is constant fee for mutualfund (for selling) and cannot be changed
     */
    public static final double REDEMPTIONFEE = 45.00;

    /**
     * Non default constructor. Requires all 4 instance fields to be provided as
     * input
     *
     * @param symbol    mutualfund sybol
     * @param name      mutualfund name
     * @param quantity  mutualfund quantity
     * @param price     mutualfund price
     *
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        this.fee = REDEMPTIONFEE;
    }

    /**
     * Buy additional shares of mutual fund
     * 
     * @param quantity  more quantity that is bought
     * @param price     the price at which they are bought
     */
    @Override
    public void buy(int quantity, double price) {
        double moreCost = quantity * price;
        this.bookValue += moreCost;
        this.quantity += quantity;
        this.price = price;
    }

    /**
     * @return gain of the current mutualFund
     */
    @Override
    public double getGain() {
        double gain = ((this.price * this.quantity) - REDEMPTIONFEE) - this.bookValue;
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
        final MutualFund temp = (MutualFund) obj;
        if (!Objects.equals(this.symbol, temp.symbol)) {
            return false;
        }
        return true;
    }

    /**
     * Adding word MutualFund for the fund toString before the super toString call
     */
    @Override
    public String toString() {
        return "MutualFund\n" + super.toString();
    }

}