package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.ArrayList;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Alex Varga and Ben Krett
 * @version 1.2
 * 
 * 
 */
//**** NOT FINISHED */
/* 
Need to connect everyone to the database and give access.
*/

public class Inventory {
    
    public final String DBURL; //store the database url information
    public final String USERNAME; //store the user's account username
    public final String PASSWORD; //store the user's account password
	private Connection dbConnect;
	private ResultSet results;

    public Inventory(){
    this.DBURL = "jdbc:mysql://localhost/inventory";
    this.USERNAME = "alexcode";
    this.PASSWORD = "glorycode";
    }
    public String getDburl(){
		return this.DBURL;
	}
	public String getUsername(){
		return this.USERNAME;
	}
	public String getPassword(){
		return this.PASSWORD;
	}

    /*
	This method create a conneciton between the database and a Connection Object so that it can be accessed.
	*/
	public void initializeConnection(){
		try{
			dbConnect = DriverManager.getConnection(this.DBURL, this.USERNAME, this.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

     /**
     * Takes an order, and attempts to execute it. If the inventory exists to fill the order, the cheapest combination is found,
     * a receipt is printed and the purshased furniture is removed from inventory. If not, the user is informed.
     * displayed, and
     * @param o Order to be fufilled.
     * @return $$$NOT SURE WHAT THIS SHOULD BE RETURNING BOOL OR NULL?
     */
    public String executeOrder (Order o)
    {
        ArrayList<Furniture> orderList = findCheapestCombo(o);
        if (orderList.size() == 0) // empty list indicates no combo was found
        {
            System.out.println("Order could not be filled");
        }
        else
        {
            float price = 0;
            for (Furniture f : orderList)
            {
                System.out.println(String.format("ID: %s", f.getID()));
                price += f.getPrice();
            }   
            System.out.println(String.format("Total Price: %f", price));
            ReceiptPrinter newReceipt = new ReceiptPrinter(
                o.getType() + " " + o.getFurniture() + " " + String.valueOf(o.getAmount()), orderList, price);
        }
        return "";

    }

    /**
     * Given an order, returns an ArrayList of the cheapest combination of furniture that fills the order.
     * If the order cannot be filled, returns an empty list
     * @param o
     * @return
     */
    private ArrayList<Furniture> findCheapestCombo (Order o)
    {
        ArrayList<Furniture> availableFurniture = getAvailableFurniture(o.getType(), o.getFurniture()); // get list of all furniture matching order
        ArrayList<Integer> cheapestCombo = recursiveFindCheapest(availableFurniture, new ArrayList<Integer>(), o);

        ArrayList<Furniture> orderList = new ArrayList<Furniture>();

        if (o.fillsOrder(availableFurniture, cheapestCombo)) // if order could not be filled, will return empty list
        {
            for (int i : cheapestCombo)
            {
                orderList.add(availableFurniture.get(i)); // fills arraylist of furniture with indices found to be cheapest
            }
        }
        
        return orderList;
    }

    /**
     * recursive algorithm to return a list of indices of the cheapest combination of furniture to fufill a given order.
     * @param f list of furnitures available to fill the order
     * @param currentlyUsed indices that are currently being tested. When this function is called should be empty list, will be
     * filled out recursively
     * @param o order to be filled
     * @return ArrayList of indices indicating which items in the available furniture list make the cheapest combo
     */

    private ArrayList<Integer> recursiveFindCheapest (ArrayList<Furniture> f, ArrayList<Integer> currentlyUsed, Order o)
    {
       
        if (o.fillsOrder(f, currentlyUsed))
        {
            return deepCopyArrayList(currentlyUsed); 
            // base case, if all parts needed are already contained in list, no reason to add more
        }
        
        ArrayList<Integer> cheapestSolution = deepCopyArrayList(currentlyUsed);
        float cheapestPrice = Float.MAX_VALUE;
        // 

        for (int i = 0; i < f.size(); i++)
        {
            if (currentlyUsed.contains(i))
            {
                continue; // ensure indices aren't inserted twice
            }
            else
            {
                currentlyUsed.add(i); // pushes new furniture, test if new combo is cheaper then previously used combos

                ArrayList<Integer> newSolution = recursiveFindCheapest(f, currentlyUsed, o);
                float newPrice = getFurnitureListPrice(f, newSolution);

                if (getFurnitureListPrice(f, newSolution) < cheapestPrice)
                {
                    // update cheapest with new cheaper solution found
                    cheapestPrice = newPrice;
                    cheapestSolution = newSolution;
                }
                currentlyUsed.remove(currentlyUsed.size() - 1); // pops furniture for next iteration
            }

        }
     
        return deepCopyArrayList(cheapestSolution);
        
        
    }

    private ArrayList<Integer> deepCopyArrayList (ArrayList<Integer> a){
        ArrayList<Integer> b = new ArrayList<Integer>();
        for (int i : a)
        {
            b.add(i);
        }
        return b;
    }

    /**
     * Given a list of furniture, and a list of indices, sum price of furniture at given indices
     * @param f list of furniture
     * @param c indices of furniture to be summed
     * @return sum price of furniture at indices 
     */
    private float getFurnitureListPrice (ArrayList<Furniture> f, ArrayList<Integer> c)
    {
        float o = 0;
        for (int i : c)
        {
            o += f.get(i).getPrice();
        }
        
        return o;

    }

 
    /**
     * Accesses database to return a list of all furniture from inventory that match the specified type
     * and furniture style
     * @param type type of furniture to select
     * @param furniture style of furniture to select
     * @return ArrayList of furniture that match type and style
     */

    public ArrayList<Furniture> getAvailableFurniture (String type, String furniture)
    {
        ArrayList<Furniture> output = new ArrayList<Furniture>();
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + furniture + " WHERE Type = '" + type +"'");
            System.out.println("The options for " + type + " " + furniture + " are:");
            while(results.next()){
                output.add(new Furniture(furniture, type, results));
                System.out.println(results.toString());
                selectComponents(furniture, results.getString("ID"));
			}
			myStmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}

        return output;


    }

    // public String selectType(String f, String t){
    //     StringBuffer tmp = new StringBuffer();
    //     try{
    //         Statement myStmt = dbConnect.createStatement();
    //         results = myStmt.executeQuery("SELECT * FROM " + t.toUpperCase() + " WHERE Type = '" + f +"'");
    //         System.out.println("The options for " + f + " " + t + " are:");
    //         while(results.next()){
    //             if(results.getString("Type").toLowerCase().equals(f)){
    //                 System.out.println(f + " " + t + " with ID: " + results.getString("ID") + " are options for this order.");
    //                 selectComponents(t, results.getString("ID"));
    //             }
	// 		}
	// 		myStmt.close();
	// 	} catch (SQLException i) {
	// 		i.printStackTrace();
	// 	}
    //     //System.out.println(tmp.toString());
    //     return tmp.toString();
    // }

    public String selectComponents(String type, String id){
        StringBuffer tmp = new StringBuffer();
        ResultSet rs;
        try{
            Statement stmt = dbConnect.createStatement();
            String query;

            query = "SELECT * FROM "+ type.toUpperCase() + " WHERE ID = '" + id + "'";
            rs = stmt.executeQuery(query);
            if(type.toUpperCase().equals("CHAIR")){
                while(rs.next()){
                    System.out.println("Chair " + id + ": " + englishConvert(rs.getString("Legs")) + " legs, "  +
                    englishConvert(rs.getString("Arms")) + " arms, " + englishConvert(rs.getString("Seat")) + " seat, and " 
                    + englishConvert(rs.getString("Cushion")) + " a cushion.");
                }
            }
            else if(type.toUpperCase().equals("DESK")){
                while(rs.next()){
                    System.out.println("Desk " + id + ": " + englishConvert(rs.getString("Legs")) + " legs, "  +
                    englishConvert(rs.getString("Top")) + " top, and " + englishConvert(rs.getString("Drawer")) + ".");
                }
            }
            else if(type.toUpperCase().equals("LAMP")){
                while(rs.next()){
                    System.out.println("Lamp " + id + ": " + englishConvert(rs.getString("Base")) + " base, and "  +
                    englishConvert(rs.getString("Bulb")) + " bulb.");
                }
            }
            else if(type.toUpperCase().equals("FILING")){
                while(rs.next()){
                    System.out.println("Filing " + id + ": " + englishConvert(rs.getString("Rails")) + " rails, "  +
                    englishConvert(rs.getString("Drawers")) + " drawers, and " + englishConvert(rs.getString("Cabinet"))+ 
                    " cabinet.");
                }
            }
            else{
                System.out.println("UNKNOWN.");
            }
            rs.close();
            stmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
        return tmp.toString();
    }

    public String englishConvert(String l){
        if(l.equals("Y")){
            return "has";
        }
        else { return "does not have";
        }
    }

    /*
	This method is to delete an object in the inventory.
	*/
	public void deleteInventoryItem(String type, String ID){
		try {
			String query = "DELETE FROM "+ type.toUpperCase() + " WHERE ID = '" + ID + "'";
			PreparedStatement myStmt = dbConnect.prepareStatement(query);
			
			myStmt.setString(1, ID);
			
			int rowCount = myStmt.executeUpdate();
			//System.out.println("Rows Affected: " + rowCount);
			
			myStmt.close();
		} catch (SQLException a) {
			a.printStackTrace();
		}
	}

    

}
