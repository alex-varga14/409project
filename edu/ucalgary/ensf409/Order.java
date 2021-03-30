package edu.ucalgary.ensf409;

/**
 * ENSF409 FINAL PROJECT GROUP 40
 * Authors:
 * 
 * Version 1.0
 * 
 * 
 */

 public class Order{

    private String furniture;
    private String type;
    private String amount;
    //private int amount;
    private static String REGEX 
	= "([a-zA-z]{4,10}) ([a-zA-Z]{4,10}), ([0-9]{1,2})";
    private static Pattern PATTERN= Pattern.compile(REGEX);

    public Order(String order){
        Matcher m = PATTERN.matcher(order);
		m.find();
		this.furniture = m.group(1);
		this.type = m.group(2);
        if(this.type.endsWith(",")){
            stripComma(this.type);
        }
        this.amount = m.group(3);
		//this.amount = Integer.parseInt(m.group(3));
		System.out.println("The order is as follows: "+ this.furniture + " " + this.type +" " + this.amount);
    }

    public void stripComma(String x){
        StringBuilder tmp = new StringBuilder(x);
        tmp.deleteCharAt(x.length()-1);
        this.type = new String(tmp);
    }







 }