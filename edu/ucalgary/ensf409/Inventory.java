package edu.ucalgary.ensf409;

import java.sql.*;

/* NOT FINISHED 
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

    /*
	This method uses the connection instance created and a statement to access the type of any table within the database.
	The results from the table are put into a results instance and appended to a string buffer so that it can be returned.
	*/
	public String selectAllTypes(String table){
		StringBuffer listofNames = new StringBuffer();
		
		try {
			Statement myStmt = dbConnect.createStatement();
			results = myStmt.executeQuery("SELECT * FROM " + table);
			
			while(results.next()){
                System.out.println(results.next());
				listofNames.append(results.getString("Type"));
				listofNames.append("\n");
			}
			myStmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
		return listofNames.toString();
	}


    public String selectType(String f, String t){
        System.out.println(t.toUpperCase().equals("CHAIR"));
        if(t.toUpperCase().equals("CHAIR")){
           return selectChair(f); 
        }
        else if (t.toUpperCase().equals("DESK")){
            return selectDesk(f); 
        }
        else if (t.toUpperCase().equals("LAMP")){
            return selectLamp(f); 
        }
        else if (t.toUpperCase().equals("FILING")){
            return selectFiling(f);
        }
        else {
            throw new IllegalArgumentException("IMPROPER FURNITURE TYPE PROVIDED");
        }
    }

    /* Good 
    public String selectChair(String f){
        StringBuffer tmp = new StringBuffer();
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM CHAIR");
            while(results.next()){
                if(results.getString("Type").toLowerCase().equals(f)){
				    tmp.append(results.getString("ID"));
				    tmp.append("\n");
                }
			}
			myStmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
        //System.out.println(tmp.toString());
        return tmp.toString();
    } */
    public String selectChair(String f){
        StringBuffer tmp = new StringBuffer();
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM CHAIR");
            while(results.next()){
                if(results.getString("Type").toLowerCase().equals(f)){
                    System.out.println("Chair with ID: "+ results.getString("ID") +" has " + results.getString("Legs") +
                    " Legs, " + results.getString("Arms") + " Arms, " + results.getString("Seat") + " Seat, " + results.getString("Cushion") + 
                    " Cushion with price " + results.getString("Price"));
				    tmp.append(results.getString("ID"));
				    tmp.append("\n");
                }
			}
			myStmt.close();
		} catch (SQLException i) {
			i.printStackTrace();
		}
        //System.out.println(tmp.toString());
        return tmp.toString();
    }



    public String selectDesk(String f){
        System.out.println("DESK");
        StringBuffer tmp = new StringBuffer();
        return tmp.toString();
    }
    public String selectLamp(String f){
        System.out.println("LAMP");
        StringBuffer tmp = new StringBuffer();
        return tmp.toString();
    }
    public String selectFiling(String f){
        StringBuffer tmp = new StringBuffer();
        return tmp.toString();
    }
}
