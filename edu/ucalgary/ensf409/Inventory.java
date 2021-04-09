package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.*;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Alex Varga and Ben Krett
 * @version 1.2
 * @since 1.0
 * 
 */

/* Inventory Class Documentation:
This class serves to connect the database and check if an order can be filled and if so,
provide the cheapest option for the desired furniture type, and if it cannot be completed, returns a list of manufacturers.
Fields:
public final String DBURL; 
    -string to store the database url information
public final String USERNAME; 
    -string to store the user's account username
public final String PASSWORD; 
    -string to store the user's account password
private Connection dbConnect;
    -Connection instance to create a connection with the database
private ResultSet results;
    -ResultSet instance to store results generated from SQL statements.

Methods:
    -public Inventory()
    -public void initializeConnection()
    -public String executeOrder (Order o)
    -public String showManu(Order p)
    -public String manuName(String manuID)
    -public ArrayList<String> cleanList(ArrayList<String> l)
    -private ArrayList<Furniture> findCheapestCombo (Order o)
    -private ArrayList<Integer> recursiveFindCheapest (ArrayList<Furniture> f, ArrayList<Integer> currentlyUsed, Order o)
    -private ArrayList<Integer> deepCopyArrayList (ArrayList<Integer> a)
    -private float getFurnitureListPrice (ArrayList<Furniture> f, ArrayList<Integer> c)
    -public ArrayList<Furniture> getAvailableFurniture (String type, String furniture)
    -public String selectComponents(String type, String id)
    -public String englishConvert(String l)
    -public void deleteInventoryItem(String furniture, String ID)
    -public String getDburl()
    -public String getUsername()
    -public String getPassword()
*/

public class Inventory {
    
    public final String DBURL; 
    public final String USERNAME; 
    public final String PASSWORD; 
	private Connection dbConnect;
	private ResultSet results;
    
    /** CONSTRUCTOR
     * Instantiates an Inventory instance with the corredt DBURL, Username and Password.
     */
    public Inventory(){
    this.DBURL = "jdbc:mysql://localhost/inventory";
    this.USERNAME = "ben";
    this.PASSWORD = "bbbb";
    }

    /**
     * Simple getter for the DBURL string
     * @return DBURL
     */
    public String getDburl(){
		return this.DBURL;
	}

    /**
     * Simple getter for the USERNAME string
     * @return USERNAME
     */
	public String getUsername(){
		return this.USERNAME;
	}

    /**
     * Simple getter for the PASSWORD string
     * @return PASSWORD
     */
    public String getPassword()
	{
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
     * a receipt is printed and the purshased furniture is removed from inventory. If not, the user is informed that
     * the order cannot be completed and the suggested manufacturers are provided.
     * @param o Order to be fufilled.
     * @return mes String.
     */
    public String executeOrder (Order o){
        StringBuilder mes = new StringBuilder();
        ArrayList<Furniture> orderList = findCheapestCombo(o);
        if (orderList.size() == 0){ // empty list indicates no combo was found
           mes.append(showManu(o));
        }
        else {
            float price = 0;
            for (Furniture f : orderList) {
                System.out.println(String.format("ID: %s", f.getID()));
                deleteInventoryItem(o.getFurniture(), f.getID());
                price += f.getPrice();
            }   
            System.out.println(String.format("Total Price: %f", price));
            ReceiptPrinter newReceipt = new ReceiptPrinter(
                o.getType() + " " + o.getFurniture() + ", " + String.valueOf(o.getAmount()), orderList, price);
        }
        return new String(mes);
    }

    /**
     * Takes in order, and determines all the possible manufacturers of the type of furniture.
     * @param p Order of type of furniture
     * @return listofManus.toString() String of the suggested manufacturers of the type of furniture.
     */
    public String showManu(Order p){
        ArrayList<String> tmp = new ArrayList<String>();
		StringBuffer listofManus = new StringBuffer();
		try {
			Statement myStmt = dbConnect.createStatement();
			results = myStmt.executeQuery("SELECT * FROM " + p.getFurniture().toUpperCase());
            listofManus.append("Order cannot be fulfilled based on current inventory. Suggested manufacturers include: \n");
			while(results.next()){
                tmp.add(results.getString("ManuID"));
			}
			myStmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
        Collections.sort(tmp);
        tmp = cleanList(tmp);
        for(int i = 0; i < tmp.size(); i++){
            if(i == tmp.size()-1){
                listofManus.append("and " + manuName(tmp.get(i)) + ".");
                break;
            }
            listofManus.append(manuName(tmp.get(i)) + ", ");
        }
		return listofManus.toString();
	}

    /**
     * Takes in Manufacturer ID's and returns correspodning name of manufacturer.
     * @param manuID The string of the manufacturer ID provided by showManu
     * @return manuName Returns manufacturer name
     */
    public String manuName(String manuID){
        StringBuilder manuName = new StringBuilder();
        switch(manuID.toUpperCase()){
            case "001":
                manuName.append("Academic Desks");
                break;
            case "002":
                manuName.append("Office Furnishings");
                break;
            case "003":
                manuName.append("Chairs R Us");
                break;
            case "004":
                manuName.append("Furniture Goods");
                break;
            case "005":
                manuName.append("Fine Office Supplies");
                break;
        }
        return new String(manuName);
    }
   
    /**
     * Removes all duplicate manufacturer ID's from a list for the specified type. Probably a better way to implement
     * @param l An ArrayList of strings containing duplicate manufacturer ID's
     * @return tmp An ArrayList of unique manufacturers of the furniture
     */
    public ArrayList<String> cleanList(ArrayList<String> l){
        ArrayList<String> tmp = new ArrayList<String>();
        boolean one = false;
        boolean two = false;
        boolean three = false;
        boolean four = false;
        boolean five = false;
        for(int i = 0; i < l.size(); i++){
            if(l.get(i).equals("001")){
                one = true;
            }
            if(l.get(i).equals("002")){
                two = true;
            }
            if(l.get(i).equals("003")){
                three = true;
            }
            if(l.get(i).equals("004")){
                four = true;
            }
            if(l.get(i).equals("005")){
                five = true;
            }
        }
        if(one == true){
            tmp.add("001");
        }
        if(two == true){
            tmp.add("002");
        }
        if(three == true){
            tmp.add("003");
        }
        if(four == true){
            tmp.add("004");
        }
        if(five == true){
            tmp.add("005");
        }
        return tmp;

    }

    /**
     * Given an order, returns an ArrayList of the cheapest combination of furniture that fills the order.
     * If the order cannot be filled, returns an empty list
     * @param o
     * @return
     */
    public ArrayList<Furniture> findCheapestCombo (Order o){
        ArrayList<Furniture> availableFurniture = getAvailableFurniture(o.getType(), o.getFurniture()); 
        // get list of all furniture matching order
        ArrayList<Integer> cheapestCombo = recursiveFindCheapest(availableFurniture, new ArrayList<Integer>(), o);
        ArrayList<Furniture> orderList = new ArrayList<Furniture>();

        if (o.fillsOrder(availableFurniture, cheapestCombo)){ // if order could not be filled, will return empty list
            for (int i : cheapestCombo){
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

    private ArrayList<Integer> recursiveFindCheapest (ArrayList<Furniture> f, ArrayList<Integer> currentlyUsed, Order o){
        if (o.fillsOrder(f, currentlyUsed)){
            return deepCopyArrayList(currentlyUsed); 
            // base case, if all parts needed are already contained in list, no reason to add more
        }
        ArrayList<Integer> cheapestSolution = deepCopyArrayList(currentlyUsed);
        float cheapestPrice = Float.MAX_VALUE;

        for (int i = 0; i < f.size(); i++){
            if (currentlyUsed.contains(i)){
                continue; // ensure indices aren't inserted twice
            }
            else {
                currentlyUsed.add(i); // pushes new furniture, test if new combo is cheaper then previously used combos
                ArrayList<Integer> newSolution = recursiveFindCheapest(f, currentlyUsed, o);
                float newPrice = getFurnitureListPrice(f, newSolution);

                if (getFurnitureListPrice(f, newSolution) < cheapestPrice) {
                    // update cheapest with new cheaper solution found
                    cheapestPrice = newPrice;
                    cheapestSolution = newSolution;
                }
                currentlyUsed.remove(currentlyUsed.size() - 1); // pops furniture for next iteration
            }
        }
        return deepCopyArrayList(cheapestSolution);
    }
    /**
     * Deep copies an ArrayList when needed.
     * @param a An ArrayList of integers representing the cheapest solution
     * @return b An deep copied Arraylist containing the cheapest solutions
     */

    private ArrayList<Integer> deepCopyArrayList (ArrayList<Integer> a){
        ArrayList<Integer> b = new ArrayList<Integer>();
        for (int i : a){
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
    private float getFurnitureListPrice (ArrayList<Furniture> f, ArrayList<Integer> c){
        float o = 0;
        for (int i : c){
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

    public ArrayList<Furniture> getAvailableFurniture (String type, String furniture){
        ArrayList<Furniture> output = new ArrayList<Furniture>();
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + furniture + " WHERE Type = '" + type +"'");
            System.out.println("The options for " + type + " " + furniture + " are:");
            while(results.next()){
                output.add(new Furniture(furniture, type, results));
               // System.out.println(results.toString());
                selectComponents(furniture, results.getString("ID"));
			}
			myStmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
        return output;
    }
 
    /**
     * Provideds User with a system print displaying all the possible furniture pieces for the type of furniture, displaying
     * which pieces it has and does not have.
     * @param type Takes in furniture type
     * @param id Takes in furniture ID
     * @return String
     */
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

    /**
     * Simple helper function to aid the function of selectComponents. 
     * Converts 'Y' to "has" and 'N' to "does not have"
     * @param l Takes in either Y or N
     */
    public String englishConvert(String l){
        if(l.equals("Y")){
            return "has";
        }
        else { return "does not have";
        }
    }

    /**
     * This method functions to delete an object in the inventory when an order can be made. 
     * Database updated accordingly.
     * @param furniture Takes in furniture type of completed order
     * @param ID Takes in ID of the furniture items used to complete the order
     */
	public void deleteInventoryItem(String furniture, String ID){
		try {
			String query = "DELETE FROM "+ furniture.toUpperCase() + " WHERE ID = ?";
			PreparedStatement myStmt = dbConnect.prepareStatement(query);
			
			myStmt.setString(1, ID);
			int rowCount = myStmt.executeUpdate();
			System.out.println("Rows Affected: " + rowCount);
			System.out.println("ID: "+ ID + " deleted.");
			myStmt.close();
		} catch (SQLException a) {
			a.printStackTrace();
		}
	}
}
