package edu.ucalgary.ensf409;

import java.sql.*;
/**
 * ENSF409 FINAL PROJECT GROUP 40
 * Authors:
 * 
 * Version 1.0
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
            while(results.next()){
                if(results.getString("Type").toLowerCase().equals(f)){

                    System.out.println("The options for " + f + " " + t + " are:");
                    System.out.println(results.getString("ID"));
                   // selectComponents( t,results.getString("ID"));

                }
			}
			myStmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
        //System.out.println(tmp.toString());
        return tmp.toString();
    }

    /*
    public String selectComponents(String id){
        StringBuffer tmp = new StringBuffer();
        ResultSet rs;
        try{
            Statement stmt = dbConnect.createStatement();
            String query;

            query = "SELECT * FROM CHAIR WHERE ID = '" + id + "'";
            rs = stmt.executeQuery(query);
            while(rs.next()){
                System.out.println("Chair " + id + ": " + englishConvert(rs.getString("Legs")) + " legs, "  +
                englishConvert(rs.getString("Arms")) + " arms, " + englishConvert(rs.getString("Seat")) + " seat, and " 
                + englishConvert(rs.getString("Cushion")) + " a cushion.");
            }
            rs.close();
            stmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
        return tmp.toString();
    }
    */

    public String englishConvert(String l){
        if(l.equals("Y")){
            return "has";
        }
        else { return "does not have";
        }
    }

}
