package edu.ucalgary.ensf409;
import java.util.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @author: Ben Krett
 * @version 1.0
 * 
 * 
 */
/** 
This Furniture class manages all the Furniture types from inventory.sql and determines
which ID's have corresponding parts for the order.

*/
public class Furniture
{
    public enum Part
    {
        Legs, Arms, Seat, Cushion, Top, Drawer, Rails, Drawers, Cabinet, Base, Bulb
        // All existing furniture parts
    }

    public String furniture;
    public String type;

    private EnumMap <Part, Boolean> partsList = new EnumMap<Part, Boolean>(Part.class);
    // maps a part to a boolean value, so each part is either true (on furniture) or false
 
    private String ID;

    private float Price;

    private String ManuID;

    public boolean hasPart (Part p)
    {
        return partsList.get(p);
    }
    
    public String getID() {
        return ID;
    }

    public float getPrice ()
    {
        return Price;
    }
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

    public Furniture (String furniture, String type, ResultSet r) throws SQLException
    {
        this.furniture = furniture;
        this.type = type;
        Price   = Float.parseFloat(r.getString("Price"));
        ManuID  = r.getString("ManuID");
        ID  = r.getString("ID");


        for (Part p :Part.values())
        {
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