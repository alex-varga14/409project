package edu.ucalgary.ensf409;
import java.util.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @author: Ben Krett
 * @version 1.0
 * @since 1.0
 * 
 */
/** Furniture Class Documentation:
This Furniture class manages all the Furniture types from inventory.sql and determines
which ID's have corresponding parts to fulfill the order.
Fields:
public String furniture;
    - string to store furniture
public String type;
    - string to store furniture type
private String ID;
    - string to store a furniture ID
private float Price;
    - float to store a furnutures price
private String ManuID;
    - string to store a furnitures manufacturer ID
private EnumMap <Part, Boolean> partsList = new EnumMap<Part, Boolean>(Part.class);
    - an enum map that maps a part to a boolean value, so each part is either true (on furniture) or false

Methods:
    -public boolean hasPart (Part p)
    -public String getID()
    -public float getPrice ()
    -public String getManuID()
    -public Furniture (String furniture, String type, ResultSet r) throws SQLException
*/
public class Furniture {

    public enum Part{
        Legs, Arms, Seat, Cushion, Top, Drawer, Rails, Drawers, Cabinet, Base, Bulb
        // All existing furniture parts
    }
    public String furniture;
    public String type;
    private EnumMap <Part, Boolean> partsList = new EnumMap<Part, Boolean>(Part.class);
    private String ID;
    private float Price;
    private String ManuID;

    /**
     * Simple boolean method to determine if a piece of furniture has a part.
     * @param p Part from a piece of furniture
     * @return boolean returns true if the part is available.
     */
    public boolean hasPart (Part p)
    {
        return partsList.get(p);
    }
    
    /**
     * Simple getter for the ID string
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Simple getter for the price float
     * @return Price
     */
    public float getPrice ()
    {
        return Price;
    }

    /**
     * Simple getter for the manufacturer ID string
     * @return ManuID
     */
    public String getManuID() {
        return ManuID;
    }

    /**
     * Creates a furniture object from a furniture style, type, and database row.
     * @param furniture furniture style
     * @param type furniture type
     * @param r database row
     * @throws SQLException
     */

    public Furniture (String furniture, String type, ResultSet r) throws SQLException {
        this.furniture = furniture;
        this.type = type;
        Price   = Float.parseFloat(r.getString("Price"));
        ManuID  = r.getString("ManuID");
        ID  = r.getString("ID");

        for (Part p :Part.values()){
            partsList.put(p, false); // by default, furniture object has all parts set to falst
        }
        
        switch(furniture.toUpperCase()){
            // for each style of furniture, go through each of it's part's database entrys and save which parts exist
            case "CHAIR":
                partsList.put(Part.Legs   , r.getString("Legs").equals("Y"));
                partsList.put(Part.Arms   , r.getString("Arms").equals("Y"));
                partsList.put(Part.Seat   , r.getString("Seat").equals("Y"));
                partsList.put(Part.Cushion, r.getString("Cushion").equals("Y"));
                break;
            case "DESK":
                partsList.put(Part.Legs   , r.getString("Legs").equals("Y"));
                partsList.put(Part.Top    , r.getString("Top").equals("Y"));
                partsList.put(Part.Drawer , r.getString("Drawer").equals("Y"));
                break;
            case "FILING":
                partsList.put(Part.Rails   , r.getString("Rails").equals("Y"));
                partsList.put(Part.Drawers , r.getString("Drawers").equals("Y"));
                partsList.put(Part.Cabinet , r.getString("Cabinet").equals("Y"));
                break;
            case "LAMP":
                partsList.put(Part.Base   , r.getString("Base").equals("Y"));
                partsList.put(Part.Bulb   , r.getString("Bulb").equals("Y"));
                break;
        }
    }

}