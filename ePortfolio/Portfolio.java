package ePortfolio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * The Portfolio class manages a collection of both stocks and mutual funds.
 * It provides functionality to add, sell,update, find gain and search
 * investements
 * 
 * This class uses two ArrayLists to store stocks and mutual funds separately.
 * It includes methods
 * for implementation of selling, buying, updating, calculatig gains across
 * portfolio and searching.
 */
public class Portfolio {

    // arraylists for both investements
    private ArrayList<Investement> investments = new ArrayList<>();
    // hashmap for searching
    private HashMap<String, ArrayList<Integer>> keywordIndex = new HashMap<String, ArrayList<Integer>>();

    /**
     * @param fileName text file to read the data from
     */
    public void loadInvestements(String fileName) {
        // varibales to use
        String symbol = "";
        String name = "";
        double price = 0;
        int quantity = 0;
        double bookValue = 0;
        String line = "";
        String type = "";

        Scanner inputStream = null;

        // error checking for opening the file
        try {
            inputStream = new Scanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open the file " + fileName + " for input");
            System.exit(0);
        }

        // read till the end of the file
        while (inputStream.hasNextLine()) {
            line = inputStream.nextLine().trim();
            // if empty line read skip
            if (line.isEmpty()) {
                continue;
            }
            // sequential scanning of the data
            // parsing each line based on the keyboards like type, symbol,quanitty,price and
            // bookValue
            // based on the keywords extract the information and store it in the
            // corresponding varibales
            if (line.startsWith("type")) {
                type = line.split("=")[1].trim().replaceAll("[“”\"]", "");
                // reads next stuff
                line = inputStream.nextLine().trim();
            }
            if (line.startsWith("symbol")) {
                symbol = line.split("=")[1].trim().replaceAll("[“”\"]", "");
                // reads next stuff
                line = inputStream.nextLine().trim();
            }
            if (line.startsWith("name")) {
                name = line.split("=")[1].trim().replaceAll("[“”\"]", "");
                // reads next stuff
                line = inputStream.nextLine().trim();
            }
            if (line.startsWith("quantity")) {
                quantity = Integer.parseInt(line.split("=")[1].trim().replaceAll("[“”\"]", ""));
                // reads next stuff
                line = inputStream.nextLine().trim();
            }
            if (line.startsWith("price")) {
                price = Double.parseDouble(line.split("=")[1].trim().replaceAll("[“”\"]", ""));
                // reads next stuff
                line = inputStream.nextLine().trim();
            }
            if (line.startsWith("bookValue")) {
                bookValue = Double.parseDouble(line.split("=")[1].trim().replaceAll("[“”\"]", ""));
            }

            // Once we have all attributes for an investment, create the appropriate object
            if (!line.isEmpty() && line.startsWith("bookValue")) {
                // new varibale of investement type to store the sotock or mutual fund to later
                // add it to the list
                Investement currentInvestment;
                if (type.equalsIgnoreCase("stock")) {
                    currentInvestment = new Stock(symbol, name, quantity, price);
                } else if (type.equalsIgnoreCase("mutualfund")) {
                    currentInvestment = new MutualFund(symbol, name, quantity, price);
                } else {
                    // not a stock or mutual fund
                    continue;
                }
                // Add the current investment to the arraylist
                currentInvestment.updateBookValue(bookValue);
                investments.add(currentInvestment);
                int position = investments.size() - 1;
                // add name of the investement to the hasmap
                addToKeywordIndex(name, position);
            }
        }
        // close input stream
        inputStream.close();
    }

    /**
     * @param fileName the text file to save the data in
     */
    public void saveInvestements(String fileName) {
        PrintWriter outputStream = null;
        // error checking for opening the file
        try {
            outputStream = new PrintWriter(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " + fileName);
            System.exit(0);
        }

        // writing to the file
        for (Investement current : investments) {
            // prints the class of the investement, stock or mutualfund
            String type = current.getClass().getSimpleName().toLowerCase();
            // rest print all the attributes of the investements
            outputStream.println("type = \"" + type + "\"");
            outputStream.println("symbol = \"" + current.getSymbol() + "\"");
            outputStream.println("name = \"" + current.getName() + "\"");
            outputStream.println("quantity = \"" + current.getQuantity() + "\"");
            outputStream.println("price = \"" + current.getPrice() + "\"");
            outputStream.println("bookValue = \"" + current.getBookValue() + "\"");
            outputStream.println(); // Blank line between investments
        }
        // close the output stream
        outputStream.close();
    }

    /**
     * Creates a new stock and adds it to the investement arryalist
     * 
     * @param symbol   stock symbol
     * @param name     stock name
     * @param quantity stock quantity
     * @param price    stock price
     */
    public void addStock(String symbol, String name, int quantity, double price) {
        Stock newStock = new Stock(symbol, name, quantity, price);
        investments.add(newStock);
    }

    /**
     * Creates a new mutualfund and adds it to the investement arraylist
     * 
     * @param symbol   mutualfund symbol
     * @param name     mutualfund name
     * @param quantity mutualfund quantity
     * @param price    mutualfund price
     */
    public void addFund(String symbol, String name, int quantity, double price) {
        MutualFund newMutualFund = new MutualFund(symbol, name, quantity, price);
        investments.add(newMutualFund);
    }

    /**
     * checks stock arraylists for finding the symbol and then perfoms
     * selling of what price and how much of it
     * 
     * @param symbol     symbol of the investement
     * @param sellAmount what quantity selling of the investement
     * @param sellPrice  the price selling the investement for
     */
    public void sellInv(String symbol, int sellAmount, double sellPrice) {
        // goes thorugh the arraylist
        for (Investement inv : investments) {
            // is current stock symbol same as requested
            if (inv.getSymbol().equalsIgnoreCase(symbol)) { // Using ignore equal metod to compare symbols
                // is sellingAmount resonable
                if (sellAmount > inv.getQuantity()) {
                    System.out.println("Not enough quantity");
                } else {
                    // selling all quantity
                    // then removes it from the stock araylist
                    if (inv instanceof Stock) {
                        ((Stock) inv).sell(sellAmount, sellPrice);
                    } else if (inv instanceof MutualFund) {
                        ((MutualFund) inv).sell(sellAmount, sellPrice);
                    }
                    if (sellAmount == inv.getQuantity()) {
                        investments.remove(inv); // Remove stock
                    }
                }
                return;
            }
        }
        // error on stock not found
        System.out.println("Investement not found");
    }

    /**
     * Allows user to buy investment(a stock or mutualfund) by providing necessary
     * details through input.
     * If the symbol already exists, it updates the existing one. Otherwise, it
     * add new investement to the profile.
     * The method also does input validation.
     * 
     * @param investment type of investement
     * @param symbol     symbol of investement
     * @param name       name of investement
     * @param amount    amount of investement to be bought
     * @param priceTag  price at which amount is bought
     * @return          a String of sumary what was bought and bookvalue
     */
    public String buyInvestement(String investment,String symbol,String name,String amount, String priceTag) {
        // varibles to use
        int quantity = 0;
        double price = 0;
        investment = investment.toLowerCase().trim();
        // helper vairbales
        Investement foundInvestement = null;
        boolean existingInvestement = false;

        // useing this for formatting of the price and the bookvalue
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");

        // checks for all input cases
        if (investment.equals("stock") || investment.equals("s") || investment.equals("mutualfund")
                || investment.equals("m")) {
            if(symbol.isEmpty()){
                return "Symbol cannot be emtpy";
            }

            // Check if the symbol already exists
            foundInvestement = findInvestement(symbol);
            if (foundInvestement != null) {
                existingInvestement = true;
            }

            // Reject if the symbol exists in the opposite investment type
            if (foundInvestement != null) {
                boolean isStock = foundInvestement instanceof Stock;
                if ((investment.equals("stock") || investment.equals("s")) && !isStock) {
                    return "Invalid input. Symbol already exists as a mutual fund.";
                    
                } else if ((investment.equals("mutualfund") || investment.equals("m")) && isStock) {
                    return "Invalid input. Symbol already exists as a stock.";
                    
                }
            }

            // If symbol exists in the same investment type, update it
            if (existingInvestement) {
                //price and quantity plus input validation
                    try { // to catch stirng input for integer
                         quantity = Integer.parseInt(amount); // Read input as a string and parse it
                        if (quantity <= 0) {
                            return "Quantity must be greater than zero. Try again.";
                        }
                    } catch (NumberFormatException e) {
                        return "Invalid input. Please enter a valid integer for quantity.";
                        
                    }
                    try { // to catch String input for integer
                        price = Double.parseDouble(priceTag);
                        if (price <= 0) {
                            return "price must be greator than 0";
                        }
                    } catch (NumberFormatException e) {
                        return "Invalid input. Please enter a valid number for price.";
                    }
              

                // updates the corresdonping investement if it already existed
                if (existingInvestement) {
                    foundInvestement.buy(quantity, price);
                    return "Updated " + foundInvestement.getClass().getSimpleName() + "\n"
                            + foundInvestement.toString();
                }
            } else {
                // If the symbol is new, ask for all details
                if(name.isEmpty()){
                    return "Name cannot be empty";
                }
                // doing user input for quantity and price as well as input validation
                    try { // to catch string input for integers
                        //System.out.print("Quantity: ");
                        quantity = Integer.parseInt(amount); // Read input as a string and parse it
                        if (quantity <= 0) {
                            return "Quantity must be greater than zero. Try again.";
                        }
                    } catch (NumberFormatException e) {
                        quantity = 0; // Reset quantity
                        return "Invalid input. Please enter a valid integer for quantity.";                
                    }

                    try {// to catch string input for integers
                        price = Double.parseDouble(priceTag); // Read input as a string and parse it
                        if (price <= 0) {
                            return "price must be greator than 0";
                        }
                    } catch (NumberFormatException e) {
                        return "Invalid input. Please enter a valid number for price.";
                    }
                

                // Add new stock or mutual fund to the profile
                // displays the bookValue to the terminal of the corresponding investement that
                // is added
                if (investment.equals("stock") || investment.equals("s")) {
                    addStock(symbol, name, quantity, price); // Add new stock
                    // get postiion of the new investment in the arraylist
                    int position = investments.size() - 1;
                    // add name of the investement to the hasmap
                    addToKeywordIndex(name, position);
                    for (Map.Entry<String, ArrayList<Integer>> entry : keywordIndex.entrySet()) {
                        String keyword = entry.getKey();
                        ArrayList<Integer> positions = entry.getValue();
                        System.out.println("Keyword: " + keyword + " -> Positions: " + positions);
                    }
                    return "Added new Stock, with bookValue of "
                            + formatter.format(investments.get(investments.size() - 1).getBookValue());

                } else if (investment.equals("mutualfund") || investment.equals("m")) {
                    addFund(symbol, name, quantity, price); // Add new mutual fund
                   
                    // get postiion of the new investment in the arraylist
                    int position = investments.size() - 1;
                    // add name of the investement to the hasmap
                    addToKeywordIndex(name, position);
                    return "Added new Mutual Fund, with bookValue of "
                    + formatter.format(investments.get(investments.size() - 1).getBookValue());
                }
            }
        } else {
            return "Invalid input";
        }
        return "Invalid";
    }

    /**
     * Allows the user to sell an investment, by providing the symbol, price, and
     * quantity.
     * If no investments are available, the method informs the user, returns to
     * main. It vaildates the input for the symbol,
     * price, and quantity. The method handles
     * input errors and performs a sell operation if the specified investment
     * exists.
     * 
     * @param symbol   symbol of the investement
     * @param amount   quantity to be sold of the investement
     * @param priceTag price at whihc investement will be sold
     * @return a String summary of what was sold and correspognind gain, payement,
     *         book values
     */
    public String sellInvestement(String symbol, String amount, String priceTag) {
        // varibales to be used
        int quantity = 0;
        double price = 0;
        Investement foundInvestement = null;
        StringBuilder message = new StringBuilder();

        // if the arraylist is empty initialy no point of doing rest
        if (investments.isEmpty()) {
            return "There are no investements to sell";
        }

        // input is valided for symbol input from user
        // cannot leave blank
        if (symbol.isEmpty()) {
            return "Symbol cannot be empty";
        }

        // following code is used to take input for price and quantity from the user
        // user validation is performed while also doign try-cath for string input for
        // integer
        try {
            price = Double.parseDouble(priceTag); // Read input as a string and parse it
            if (price <= 0) {
                return "Price must be greator than 0";
            }
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter a valid number for price.";
        }

        try {
            quantity = Integer.parseInt(amount); // Read input as a string and parse it
            if (quantity <= 0) {
                return "Quantity must be greater than zero. Try again.";
            }
        } catch (NumberFormatException e) {
            quantity = 0; // reset quanitty
            return "Invalid input. Please enter a valid integer for quantity.";
        }

        // check if such investement exists
        foundInvestement = findInvestement(symbol);
        if (foundInvestement != null) {
            int position = investments.indexOf(foundInvestement);
            message.append(foundInvestement.toString());
            // selling in full
            if (foundInvestement.getQuantity() == quantity) {
                // selling in full remove from the investement arraylist
                // int position = investments.size() - 1;
                removeFromKeywordIndex(foundInvestement.getName(), position);
                investments.remove(foundInvestement);
                updateKeywordIndices(position);
                message.append(foundInvestement.sell(quantity, price));
            } else { // partiabl selling
                message.append(foundInvestement.sell(quantity, price));
                message.toString();
            }
        } else {
            return "No investement exist with that symbol\n";
        }
        return message.toString();
    }

    /**
     * Ask for users for new price and updates it for that investement.
     * iterates thorugh the arraylist
     * 
     * @param input takes scanner form main
     */
    public void update(Scanner input) {
        // goes thorugh the arraylist
        for (Investement temp : investments) {
            // displays the currrent investement and then asks for new price
            System.out.println(temp);
            System.out.print("Please enter a new price: ");
            double newPrice = input.nextDouble();
            input.nextLine(); // consume new line
            // perfomrs update price
            temp.update(newPrice);
        }
    }

    /**
     * gets the total gain of the portfolio
     * 
     * @return totalGain
     */
    public double getGain() {
        double totalGain = 0;
        // finds gain for each investement and sums them up
        for (Investement temp : investments) {
            totalGain += temp.getGain();
        }

        return totalGain;
    }

    /**
     * @return individuals gains in a String
     */
    public String getIndividualGains() {
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        StringBuilder returnMessage = new StringBuilder();
        // goes thorugh each investement and keeps adding to the reutrning string
        for (Investement temp : investments) {
            returnMessage.append("For " + temp.getSymbol() + " gain is " + formatter.format(temp.getGain()) + "\n");
        }
        return returnMessage.toString();
    }

    /**
     * this method takes user input
     * searches for investement arraylist based
     * on what user wants and returns it in a string
     * 
     * @param symbol     symbol of the investement
     * @param word       keyword of the investement
     * @param lowerBound low price for investement
     * @param upperBound high price for investement
     * @return results in a String
     */
    public String searchInvestement(String symbol, String word, String lowerBound, String upperBound) {
        StringBuilder result = new StringBuilder();
        // first check, if arraylist is emppty do not need to do rest
        // return it to main
        if (investments.isEmpty()) {
            return "Portfolio is empty currently";
        }
        // build the price String
        String priceString = "";
        if (lowerBound.isEmpty() && upperBound.isEmpty()) {
            priceString = "";
        } else if (!lowerBound.isEmpty() && upperBound.isEmpty()) {
            priceString = lowerBound + "-";
        } else if (lowerBound.isEmpty() && !upperBound.isEmpty()) {
            priceString = "-" + upperBound;
        } else if (!lowerBound.isEmpty() && !upperBound.isEmpty()) {
            if (lowerBound.equals(upperBound)) {
                priceString = lowerBound;
            } else {
                priceString = lowerBound + "-" + upperBound;
            }
        }

        String priceRange = priceString;
        // helepr varibales to be used
        boolean symbolMatch = false;
        boolean wordMatch = false;
        boolean priceMatch = false;
        boolean symbolCheck = true;
        boolean wordCheck = true;
        boolean priceCheck = true;
        // sets correspoding boolean
        // that later determines which action to be perfromed
        if (symbol.isEmpty()) {
            symbolCheck = false;
            symbolMatch = true;
        }
        if (word.isEmpty()) {
            wordCheck = false;
            wordMatch = true;
        }
        if (priceRange.isEmpty()) {
            priceCheck = false;
            priceMatch = true;
        }

        // check for words - hashmap search
        if (wordCheck) {
            // converts word into a string to get the arrylist of positions from
            // IntersectionOfIndecies method
            String[] wordSearch = word.toLowerCase().split("[ ,]+");
            ArrayList<Integer> commonPositions = IntersectionOfIndecies(wordSearch);

            if (commonPositions != null) {
                wordMatch = true;
            } else {
                return "Nothing found\n";
            }
            // goes thorugh all the indices stored in the commonPositions
            for (int pos : commonPositions) {
                // check symbol
                if (symbolCheck) {
                    if (investments.get(pos).getSymbol().equalsIgnoreCase(symbol)) {
                        symbolMatch = true;
                    }
                }
                // check for price
                if (priceCheck) {
                    double investementPrice = investments.get(pos).getPrice();
                    if (priceRange.startsWith("-")) {
                        // Case: "-100.00" -> prices <= 100.00
                        double upper = Double.parseDouble(priceRange.substring(1));
                        priceMatch = investementPrice <= upper;
                    } else if (priceRange.endsWith("-")) {
                        // Case: "100.00-" -> prices >= 100.00
                        double lower = Double.parseDouble(priceRange.substring(0, priceRange.length() - 1));
                        priceMatch = investementPrice >= lower;
                    } else if (priceRange.contains("-")) {
                        // Case: "10.00-100.00" -> prices between 10.00 and 100.00
                        String[] splitRange = priceRange.split("-");
                        double lower = Double.parseDouble(splitRange[0]);
                        double upper = Double.parseDouble(splitRange[1]);
                        priceMatch = (investementPrice >= lower && investementPrice <= upper);
                    } else {
                        // Exact price match
                        double exactPrice = Double.parseDouble(priceRange);
                        priceMatch = (investementPrice == exactPrice);
                    }
                }

                // If all criteria match, print the investement in string
                if (symbolMatch && wordMatch && priceMatch) {
                    result.append(investments.get(pos).toString());
                }
            }
        }

        // loop through the arrylist -- sequential search (only when no word check)
        if (wordCheck == false) {

            for (Investement temp : investments) {
                boolean currentMatch = false;
                if (symbolCheck == false && priceCheck == false) {
                    currentMatch = true;
                }
                if (temp.getSymbol().equalsIgnoreCase(symbol) && symbolCheck) {
                    symbolMatch = true;
                    currentMatch = true;
                }
                // check price
                if (priceCheck) {
                    if (!(priceRange.startsWith("-") || priceRange.endsWith("-") || priceRange.contains("-")
                            || priceRange.matches("\\d+(\\.\\d+)?"))) {
                        return "Error: Invalid prince range input format, use -100,100-, 100-200 or 100";
                    }
                    double investementPrice = temp.getPrice();
                    if (priceRange.startsWith("-")) {
                        // Case: "-100.00" -> prices <= 100.00
                        double upper = Double.parseDouble(priceRange.substring(1));
                        priceMatch = investementPrice <= upper;
                        currentMatch = true;
                    } else if (priceRange.endsWith("-")) {
                        // Case: "100.00-" -> prices >= 100.00
                        double lower = Double.parseDouble(priceRange.substring(0, priceRange.length() - 1));
                        priceMatch = investementPrice >= lower;
                        currentMatch = true;
                    } else if (priceRange.contains("-")) {
                        // Case: "10.00-100.00" -> prices between 10.00 and 100.00
                        String[] splitRange = priceRange.split("-");
                        double lower = Double.parseDouble(splitRange[0]);
                        double upper = Double.parseDouble(splitRange[1]);
                        priceMatch = (investementPrice >= lower && investementPrice <= upper);
                        currentMatch = true;
                    } else {
                        // Exact price match
                        double exactPrice = Double.parseDouble(priceRange);
                        priceMatch = (investementPrice == exactPrice);
                        currentMatch = true;
                    }
                }
                // If all criteria match, print the investement to a string builder
                if ((symbolMatch && priceMatch) && currentMatch) {
                    result.append(temp.toString());
                }
            }
        }
        return result.toString();
    }

    /**
     * @param name     name of the investement
     * @param position positon in the arraylist investement
     */
    public void addToKeywordIndex(String name, int position) {
        // getting the words form the name of the investement
        String[] keywords = name.toLowerCase().split("[ ,]+");

        // go thorugh the keyword array made above
        for (String keyword : keywords) {
            keywordIndex.putIfAbsent(keyword, new ArrayList<>()); // Add keyword if absent
            keywordIndex.get(keyword).add(position); // Append position to the keyword's list
        }
    }

    /**
     * @param name     name of the investement
     * @param position position in the arrayList investement
     */
    public void removeFromKeywordIndex(String name, int position) {
        // split the name into words on space, comma, tab
        String[] keywords = name.toLowerCase().split("[ ,+]");

        // go thorugh the keyword array made above
        for (String keyword : keywords) {
            // get the list of associated indecies with this keyword
            ArrayList<Integer> positions = keywordIndex.get(keyword);
            // check if there is not such indecies for the keyword
            if (positions != null) {
                // remove the position that is passed
                positions.remove(Integer.valueOf(position));
                // if empty list, remove the keyword from the hashmap keywordIndex
                if (positions.isEmpty()) {
                    keywordIndex.remove(keyword);
                }
            }
        }
    }

    /**
     * updates the keyword indices after sell takes palce
     * 
     * @param removedPosition poitions to be removed from hasmap
     */
    public void updateKeywordIndices(int removedPosition) {
        for (Map.Entry<String, ArrayList<Integer>> entry : keywordIndex.entrySet()) {
            ArrayList<Integer> indices = entry.getValue();
            // updates indices greater than the removed position
            for (int i = 0; i < indices.size(); i++) {
                if (indices.get(i) > removedPosition) {
                    indices.set(i, indices.get(i) - 1);
                }
            }
        }
    }

    /**
     * @param keywords words to be searched for
     * @return arraylist of the common indices of the searching words
     */
    public ArrayList<Integer> IntersectionOfIndecies(String[] keywords) {
        // make arrylist
        ArrayList<Integer> intersections = new ArrayList<>();
        // empty string passed
        if (keywords.length == 0) {
            return intersections;
        }
        // check if the first word of the keyword is in the hashmap
        if (keywordIndex.containsKey(keywords[0])) {
            intersections.addAll(keywordIndex.get(keywords[0]));
        } else {
            // first word from keyword has no positions, no match return empty arraylist
            return intersections;
        }
        // intersection with other postions of the words
        for (int i = 1; i < keywords.length; i++) {
            ArrayList<Integer> positions = keywordIndex.get(keywords[i]);
            if (positions == null) {
                return new ArrayList<>();
            }
            // store the positions that are common
            intersections.retainAll(positions);
        }
        return intersections;
    }

    /**
     * finds the investement if it is in the arraylist
     * 
     * @param symbol investement to check for
     * @return ths found investement from the list
     */
    public Investement findInvestement(String symbol) {
        for (Investement temp : investments) {
            // compares current investement symbol to requested one
            if (temp.getSymbol().equalsIgnoreCase(symbol)) {
                // true, returns the investement
                return temp;
            }
        }
        // not found, return null
        return null;
    }

    /**
     * @return arraylist of the investements
     */
    public List<Investement> getInvestments() {
        return this.investments;
    }
}
