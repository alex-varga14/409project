package edu.ucalgary.ensf409;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Alex Varga and Ben Krett
 * @version 1.2
 * 
 * 
 */
//**** NOT FINISHED */
 public class Order{

    private String furniture;
    private String type;
    private int amount;
    

    private static String REGEX 
	= "([a-zA-z]{4,12}) ([a-zA-Z]{4,12}), ([0-9]{1,2})";
    private static Pattern PATTERN= Pattern.compile(REGEX);

    public Order(String order){
        System.out.println(order);
        Matcher m = PATTERN.matcher(order);
		m.find();
        this.type = m.group(1);
		this.furniture = m.group(2);
        if(this.type.endsWith(",")){
            stripComma(this.type);
        }
		this.amount = Integer.parseInt(m.group(3));
		System.out.println("The order is as follows: "+ this.furniture + " " + this.type +" " + this.amount);
        
    }

    /**
     * Checks if the furnitures, indicated by the indices given, fufill the order.
     * @param fList list of furnitures.
     * @param c list of indices of the list of furnitures, representing a potential way to fill the order
     * @return
     */
    public boolean fillsOrder(ArrayList<Furniture> fList, ArrayList<Integer> c)
    {
        EnumMap <Furniture.Part, Integer> partsList = new EnumMap<Furniture.Part, Integer>(Furniture.Part.class);
        // maps each possible Part of furniture to an integer, to count how many of each parts exist in the furniture
        for (Furniture.Part p : Furniture.Part.values())
        {
            partsList.put(p, 0);
            // count of each part starts at 0
        }
        for (int i : c)
        {
            for (Furniture.Part p : Furniture.Part.values())
            // iterate through all existing furniture parts
            {
                if (fList.get(i).hasPart(p))
                {
                    partsList.put(p, partsList.get(p) + 1); 
                    // if part exists on a piece of furniture, increment total count for that part
                }
            }
        }
        switch (furniture.toUpperCase())
        {
            // for each style of furniture, ensure total of each kind of part needed that exists in the order
            // is larger then the total amount specified by the order.
            case "CHAIR":
                return  partsList.get(Furniture.Part.Legs) >= amount && 
                        partsList.get(Furniture.Part.Arms) >= amount && 
                        partsList.get(Furniture.Part.Seat) >= amount && 
                        partsList.get(Furniture.Part.Cushion) >= amount;
            case "DESK":
                return  partsList.get(Furniture.Part.Legs) >= amount && 
                        partsList.get(Furniture.Part.Top) >= amount && 
                        partsList.get(Furniture.Part.Drawer) >= amount;
            case "FILING":
                return  partsList.get(Furniture.Part.Rails) >= amount && 
                        partsList.get(Furniture.Part.Drawers) >= amount && 
                        partsList.get(Furniture.Part.Cabinet) >= amount;
            case "LAMP":
                return  partsList.get(Furniture.Part.Base) >= amount && 
                        partsList.get(Furniture.Part.Bulb) >= amount;
                
        }
        return false;
    }

    public void stripComma(String x){
        StringBuilder tmp = new StringBuilder(x);
        tmp.deleteCharAt(x.length()-1);
        this.type = new String(tmp);
    }

    public String getFurniture(){
        return this.furniture;
    }
    public String getType(){
        return this.type;
    }
    public int getAmount(){
        return this.amount;
    }
 }