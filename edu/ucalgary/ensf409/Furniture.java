package edu.ucalgary.ensf409;
import java.util.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Furniture
{
    public enum Part
    {
        Legs, Arms, Seat, Cushion, Top, Drawer, Rails, Drawers, Cabinet, Base, Bulb
    }
    public String furniture;
    public String type;

    private EnumMap <Part, Boolean> partsList = new EnumMap<Part, Boolean>(Part.class);

    private String ID;

    private float Price;

    private String ManuID;
    private String Name;
    private String Phone;
    private String Province;

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

    public Furniture (String furniture, String type, ResultSet r) throws SQLException
    {
        this.furniture = furniture;
        this.type = type;
        Price   = Float.parseFloat(r.getString("Price"));
        ManuID  = r.getString("ManuID");
        ID  = r.getString("ID");


        for (Part p :Part.values())
        {
            partsList.put(p, false);
        }
        switch(furniture.toUpperCase()){
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