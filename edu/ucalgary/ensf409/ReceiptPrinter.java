package edu.ucalgary.ensf409;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ENSF409 FINAL PROJECT GROUP 40
 * @team members: Alex Varga, Ben Krett, Domninic Vanderkerkhove, and Kenny Jeon
 * @author: Dominic Vandekerkhove
 * @version 1.2
 * @since 1.0
 * 
 */

/* ReceiptPrinter Class Documentation:
This class serves to format a "receipt" for an order placed if it can be fulfilled. Ouputs a .txt file when order is 
completed.
Fields:
private String origRequest;
	- A string containing the origal order request.
private ArrayList<Furniture> items;
	- An ArrayList of type Furniture to contain all the items required to complete the order.
private int price;
	- An int to store the cheapest price to complete the order
private String receipt = "";
	- A string formatted to the desired receipt with the completed order.
private String file = "orderForm.txt";
	- A string for the name of the .txt file

Methods:
	-public ReceiptPrinter(String origRequest, ArrayList<Furniture> items, float price)
	-public String getOrigRequest()
	-public ArrayList<Furniture> getItems()
	-public int getPrice()
	-public String getReceipt()
	-public void writeToFile(String input)
	-public void formatReceipt()
*/
public class ReceiptPrinter{
	private String origRequest;
	private ArrayList<Furniture> items;
	private int price;
	private String receipt = "";
	private String file = "orderForm.txt";
	
   /**
	* Constructor
	* @param origRequest the original order request 
	* @param items list of Furniture required to complete order
	* @param price the price of the order
	*/
	public ReceiptPrinter(String origRequest, ArrayList<Furniture> items, float price){
		this.origRequest = origRequest;
		this.items = items;
		this.price = Math.round(price);
		formatReceipt();
		writeToFile(receipt);
	}
	
   /**
	* Getter for origRequest
	* @return the original order request
	*/
	public String getOrigRequest(){
		return this.origRequest;
	}
	
   /**
	* Getter for items
	* @return the list of furniture required to complete order
	*/
	public ArrayList<Furniture> getItems(){
		return this.items;
	}
	
   /**
	* Getter for price
	* @return the price of the order
	*/
	public int getPrice(){
		return this.price;
	}
	
   /**
	* Getter for receipt
	* @return the receipt String to be written into the file
	*/
	public String getReceipt(){
		return this.receipt;
	}
	
   /**
	* Writes String argument into output file
	* @param input the String to be written into the file
	*/
	public void writeToFile(String input){
		FileWriter myWriter = null;
		try {
			File myFile = new File(file);
			myWriter = new FileWriter(file);
			myWriter.write(input);
		} catch(IOException e){
			System.err.println("Failed to write output file");
		}
		finally {
			try {
				myWriter.close();
			} catch(IOException e){
				System.err.println("Failed to close output file");
			}
		}
	}
	
   /**
	* Formats the receipt String
	*/
	public void formatReceipt(){
		receipt +=	"Furniture Order Form\n"
					+"\n"
					+"Faculty Name: \n"
					+"Contact: \n"
					+"Date: \n"
					+"\n"
					+"Original Request: " + origRequest
					+"\n\n"
					+"Items Ordered\n";
		for (int i = 0; i < items.size(); i++){
			receipt += "ID: " + items.get(i).getID() +"\n";
		}
		receipt += "\n" + "Total Price: $" +price;
	}
}