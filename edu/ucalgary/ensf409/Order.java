package edu.ucalgary.ensf409;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * ENSF409 FINAL PROJECT GROUP 40
 * Authors:
 * 
 * Version 1.0
 * 
 * 
 */
//**** NOT FINISHED */
 public class Order{

    private String furniture;
    private String type;
    private int amount;
    private Inventory inventory;
    private double cheapest;

    private static String REGEX 
	= "([a-zA-z]{4,12}) ([a-zA-Z]{4,12}), ([0-9]{1,2})";
    private static Pattern PATTERN= Pattern.compile(REGEX);

    public Order(String order){
        Matcher m = PATTERN.matcher(order);
		m.find();
		this.furniture = m.group(1);
		this.type = m.group(2);
        if(this.type.endsWith(",")){
            stripComma(this.type);
        }
		this.amount = Integer.parseInt(m.group(3));
		System.out.println("The order is as follows: "+ this.furniture + " " + this.type +" " + this.amount);
        main();
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

    public void main(){
        inventory = new Inventory();
        inventory.initializeConnection();
        
        System.out.println(inventory.selectType(this.furniture, this.type));
    }

 }