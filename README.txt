ePortfolio
*************************
General Problem: Sometimes it hard to maintain your investments and keep track of them. This project aims to provide a basic GUI and simplified tool to track and manage these investments. 
                 Creates a portfolio that contains 2 kinds of investments stocks and mutual funds. It allows you to buy and/or sell investments, search for investments,
		 update prices, and compute total gain of the whole portfolio. And in terms of the learning, this programs aims to utilie the basic object oriented structure for java
                 and deploy defensive programming in place. 
*************************
Assumptions/Limitations: The program only works for any investment that has a symbol, name, price and quantity. It gives more functionality if the investements are stocks or mutual funds as they are child classes of investment. It does 		 not handle real-time data, the user have to do price updates every day, depending on the investment price change frequency.
*************************
Note this application does not support file I/O
How to build and run: Run the following commands from the main folder (i.e where ePortfolio folder is kept)
		      javac ePortfolio/*.java
                      jar cvfe A3.jar  ePortfolio.Portfolio ePortfolio/*.class 
                      java -jar A3.jar 

*************************
Known issues: One of the known issues is that when a stock is bought and then sold full right after it at a higher price than buying price, the portfolio gain shows 0 since 
              if selling in full quantity of a stock or mutual fund, the program removes it from the array list. And when finding the gain of portfolio it go throughs the array list again
              which does not have the investment that we bought and sold right after it. (Although assignment never mentioned such case, but I just came across it while testing).
*************************
Possible Improvements: A lot have improved since the Assignment 2 but there are still some aspects that I believe could have improved, like hainv to clear the fields when user switches between panels, or like storing the user history of actiosn within the messages area of each panel
*************************
Test Plan: 
        - Confirm proper error messaging when navigating or updating an empty portfolio
        - able to switch between differe user interface panels
        - reset buttons clears all the fields on each of the panels
        - correct erro messages showed in the messages area
        - buttons are clickable
        - text fileds suach as for update, only price is editable
        - scroll bars appear when the messages exeed the viewable are in the windwo for each of the panels
        - Prevent the user from entering special characters or invalid formats in numeric fields.
        - for update Panel, Prev and Next buttons to navigate through investments and ensure fields update correctly with investment details.
        - Test edge cases (first and last investments) to confirm proper messaging when navigation limits are reached.
        - Add multiple investments with overlapping keywords to check correct search
        - Delete an investment and confirm that keywords update in the index, removing empty lists if necessary from the hashmap
	    - Able to add a stock into an empty investment arraylist
	    - Able to add a mutual fund into an empty investment arraylist
	    - When buying and inputting an existing stock/mutual fund, should not ask for name, update that investment's attributes
	    - Cannot skip symbol/name/price/quantity field input for either buying or selling
	    - negative inputs for price and quantity, re-ask user
	    - handles and re-ask when string inputs are done where integers are needed
	    - Selling a stock in full, remove from investement arraylist
        - Selling a mutualfund in full, remove from investment arraylist
        - Test partial sales of stocks and mutual funds to confirm correct quantity and book value adjustments.
        - Attempt to sell a quantity higher than owned and confirm that the program prompts correctly without allowing it.
        - Enter mixed-case keywords in the search (e.g., "toroNTo BAnk") to ensure case-insensitive matching works
        - Use keywords that do not exist in any investment names to verify that no results are shown.
        - Conduct a search with a non-existent symbol to confirm no matches found
        - Search with overlapping price ranges to validate correct boundary handling
        - Search with a combination of symbol, keywords, and price range to verify complex query handling
	    - Symbol field is skippable for search command
        - Word field is skippable for search command
        - Price field is skippable for search command
	    - shows all investments when a lower price bound is given in search with other fields empty
	    - shows all investments when a upper bound price is given in search with other fields empty
	    - both upper/lower price bounds input
	    - update call goes through all investments with prompt for new price
        - gain on empty portfolio is 0
        - gain calculates all profits on the portfolio when different stocks and mutualfounds are present in the arraylist
        - Enter non-numeric symbols or special characters for price and quantity fields to confirm error messages prompt re-entry
        - Handle maximum integer inputs for quantity and price to test program limits.
        - Handle zero and one as minimum quantity inputs for buying or selling to ensure logical operations
