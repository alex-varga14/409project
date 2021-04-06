<<<<<<< HEAD
package edu.ucalgary.ensf409;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @author: Dominic Vanderkerkhove
 * @version 1.1
 * 
 * 
 */
/*
This class creates an order receipt in an output file
*/
//**** NOT FINISHED */

public class ReceiptPrinter{
	private String origRequest;
    private ArrayList<Furniture> items;
	private float price;
	private String receipt = "";
	private String file = "orderForm.txt";
	
	/**
	Constructor
	*/
	public ReceiptPrinter(String origRequest, ArrayList<Furniture> items, float price){
		this.origRequest = origRequest;
		this.items = items;
		this.price = price;
		formatReceipt();
		writeToFile(receipt);
	}
   
	
	/**
	Getter for origRequest
	*/
	public String getOrigRequest(){
		return this.origRequest;
	}
	
	/**
	Getter for items
	*/
	// public Furniture[] getItems(){
	// 	return this.items;
	// }
    public ArrayList<Furniture> getItems(){
		return this.items;
	}
	
	/**
	Getter for cost
	*/
	public float getPrice(){
		return this.price;
	}
	
	/**
	Getter for receipt
	*/
	public String getReceipt(){
		return this.receipt;
	}
	
	/**
	Writes String argument into output file
	*/
	public void writeToFile(String input){
		FileWriter myWriter = null;
		try{
			File myFile = new File(file);
			myWriter = new FileWriter(file);
			myWriter.write(input);
			
		}catch(IOException e){
			System.err.println("Failed to write output file");
		}
		finally{
			try{
				myWriter.close();
			}catch(IOException e){
				System.err.println("Failed to close output file");
			}
		}
	}
	
	/**
	Formats the receipt String
	*/
	public void formatReceipt(){
		receipt +=	"Furniture Order Form\n"
					+"\n"
					+"Faculty Name: \n"
					+"Contact: \n"
					+"Date: \n"
					+"\n"
					+"Original Request: \n"+origRequest
					+"\n"
					+"Items Ordered\n";
		for (int i = 0; i < items.size(); i++){
		receipt += items.get(i).getID() +"\n";
		}
		receipt += "Total Price: $"; //+price;
	}
	
}
=======
package edu.ucalgary.ensf409;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * ENSF409 FINAL PROJECT GROUP 40
 * Authors:
 * 
 * Version 1.0
 * 
 * 
 */
/*
This class creates an order receipt in an output file
*/
//**** NOT FINISHED */

public class ReceiptPrinter{
	private String origRequest;
	private ArrayList<Furniture> items;
	private int price;
	private String receipt = "";
	private String file = "orderForm.txt";
	
	/**
	Constructor
	*/
	public ReceiptPrinter(String origRequest, ArrayList<Furniture> items, int price){
		this.origRequest = origRequest;
		this.items = items;
		this.price = price;
		formatReceipt();
		writeToFile(receipt);
	}
	
	/**
	Getter for origRequest
	*/
	public String getOrigRequest(){
		return this.origRequest;
	}
	
	/**
	Getter for items
	*/
	public ArrayList<Furniture> getItems(){
		return this.items;
	}
	
	/**
	Getter for cost
	*/
	public int getPrice(){
		return this.price;
	}
	
	/**
	Getter for receipt
	*/
	public String getReceipt(){
		return this.receipt;
	}
	
	/**
	Writes String argument into output file
	*/
	public void writeToFile(String input){
		FileWriter myWriter = null;
		try{
			File myFile = new File(file);
			myWriter = new FileWriter(file);
			myWriter.write(input);
			
		}catch(IOException e){
			System.err.println("Failed to write output file");
		}
		finally{
			try{
				myWriter.close();
			}catch(IOException e){
				System.err.println("Failed to close output file");
			}
		}
	}
	
	/**
	Formats the receipt String
	*/
	public void formatReceipt(){
		receipt +=	"Furniture Order Form\n"
					+"\n"
					+"Faculty Name: \n"
					+"Contact: \n"
					+"Date: \n"
					+"\n"
					+"Original Request: \n" +origRequest
					+"\n"
					+"Items Ordered\n";
		for (int i = 0; i < items.size(); i++){
			receipt += items.get(i).getID() +"\n";
		}
		receipt += "Total Price: $" +price;
	}
	
}
>>>>>>> c53a10be463d5f6907e13ebd9ff09058d8732a24
