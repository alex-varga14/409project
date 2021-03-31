package edu.ucalgary.ensf409;

import java.sql.*;
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

    public String selectType(String f, String t){
        StringBuffer tmp = new StringBuffer();
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + t.toUpperCase() + " WHERE Type = '" + f +"'");
            System.out.println("The options for " + f + " " + t + " are:");
            while(results.next()){
                if(results.getString("Type").toLowerCase().equals(f)){
                    System.out.println(f + " " + t + " with ID: " + results.getString("ID") + " are options for this order.");
                    selectComponents(t, results.getString("ID"));
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
