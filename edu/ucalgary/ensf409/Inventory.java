package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.ArrayList;
/**
 * ENSF409 FINAL PROJECT GROUP 40
 * Authors:
 * 
 * Version 1.02
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
    this.USERNAME = "Ken";
    this.PASSWORD = "ensf409";
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

    public String executeOrder (Order o)
    {
        ArrayList<Furniture> orderList = findCheapestCombo(o);
        if (orderList.size() == 0)
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
        }
        return "";

    }

    private ArrayList<Furniture> findCheapestCombo (Order o)
    {
        ArrayList<Furniture> availableFurniture = getAvailableFurniture(o.getType(), o.getFurniture());
        ArrayList<Integer> cheapestCombo = recursiveFindCheapest(availableFurniture, new ArrayList<Integer>(), o);
        ArrayList<Furniture> orderList = new ArrayList<Furniture>();
        if (o.fillsOrder(availableFurniture, cheapestCombo)) // if not valid, will return empty list
        {
            for (int i : cheapestCombo)
            {
                orderList.add(availableFurniture.get(i));
            }
        }
        
        return orderList;
    }

    private ArrayList<Integer> recursiveFindCheapest (ArrayList<Furniture> f, ArrayList<Integer> currentlyUsed, Order o)
    {
       
        if (o.fillsOrder(f, currentlyUsed))
        {
            return deepCopyArrayList(currentlyUsed);
        }
        
        ArrayList<Integer> cheapestSolution = deepCopyArrayList(currentlyUsed);
        float cheapestPrice = Float.MAX_VALUE;

        for (int i = 0; i < f.size(); i++)
        {
            if (currentlyUsed.contains(i))
            {
                continue;
            }
            else
            {
                
                currentlyUsed.add(i);
                ArrayList<Integer> newSolution = recursiveFindCheapest(f, currentlyUsed, o);
                float newPrice = getFurnitureListPrice(f, newSolution);
                if (getFurnitureListPrice(f, newSolution) < cheapestPrice)
                {
                    cheapestPrice = newPrice;
                    cheapestSolution = newSolution;
                }
                currentlyUsed.remove(currentlyUsed.size() - 1);
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

    private float getFurnitureListPrice (ArrayList<Furniture> f, ArrayList<Integer> c)
    {
        float o = 0;
        for (int i : c)
        {
            o += f.get(i).getPrice();
        }
        
        return o;

    }

    // public ArrayList<Furniture> executeOrder (Order o)
    // {

    //     switch (o.getFurniture().toUpperCase())
    //     {
    //         case "CHAIR":

    //             break;
    //         case "DESK":

    //             break;
    //         case "FILING":
                    
    //             break;
    //         case "LAMP":

    //             break;
    //     }
    //     ArrayList<Furniture> availableFurniture = getAvailableFurniture(o.getType(), o.getFurniture());
    //     float min_price;
    //     for (int i = 0; i < availableFurniture.length; i++)
    //     {
            
    //     }
    //     return "";
    // }

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

    public String selectType(String type, String furniture){
        StringBuffer tmp = new StringBuffer();
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + furniture + " WHERE Type = '" + type +"'");
            System.out.println("The options for " + type + " " + furniture + " are:");
            while(results.next()){
                if(results.getString("Type").toLowerCase().equals(type)){
                    System.out.println(type + " " + furniture + " with ID: " + results.getString("ID") + " are options for this order.");
                    selectComponents(furniture, results.getString("ID"));
                }
			}
			myStmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
        //System.out.println(tmp.toString());
        return tmp.toString();
    }

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
