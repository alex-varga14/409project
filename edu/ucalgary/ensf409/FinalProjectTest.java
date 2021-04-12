package edu.ucalgary.ensf409;
import static org.junit.Assert.*;
import org.junit.*;
import jdk.jfr.Timestamp;
import java.math.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Dominic Vandekerkhove, Alex Varga and Ben Krett
 * @version 1.1
 * @since 1.0
 * 
 */
/* FinalProjectTest Class (Unit Testing)
This class tests all the possible scenarios 
*/

public class FinalProjectTest{

	public FinalProjectTest(){
	}

	public final static Inventory inventory = new Inventory();

	/*** INPUT CLASS TESTS ***/

	@Test 
	// Test input constructor
	// tests to see if input correctly forms the order
	public void testInputConstructor(){
		Input in = new Input();
		in.setInput("desk lamp, 1");
		Order e = new Order(in.getInput());
		assertTrue("Input instance is not equal:", in.getInput().equals(e.getType() + " " + e.getFurniture() + ", " + e.getAmount()));
	} 

	/*** ORDER CLASS TESTS ***/
	@Test
	//Constructor create with an order
	//tests if getters work properly to retreive order
	public void testOrderConstructorGetElements() {
		Order testOrder = new Order("mesh chair, 1");
	
		String type = testOrder.getType();
		String furniture = testOrder.getFurniture();
		int amount = testOrder.getAmount();
	
		// Retrieve order, check if it matches 
		String order = type + " " + furniture + " " + String.valueOf(amount);
		assertTrue("Instantiating Order Instance and using getters to determing if constructor matches the correct order failed:", 
		order.equals(testOrder.getType() + " " + testOrder.getFurniture() + " " + testOrder.getAmount()));
	}



	/*** INVENTORY CLASS TESTS ***/
	@Test
	public void testInventoryContructorAndDBAccess ()
	{
		inventory.initializeConnection();
		int len = inventory.getAvailableFurniture("desk", "lamp").size();
		assertEquals("Wrong number of inventory items fetched", len, 7);
	}

	@Test
	public void testInvalidOrderDBAccess ()
	{
		inventory.initializeConnection();
		Order o = new Order("mosh chair, 1");
		ArrayList<Furniture> combo = inventory.findCheapestCombo(o);
		assertEquals("DB Access did not fail correctly on incorrect order", 0, combo.size());
	}

	@Test 
	//Test invalid order amount, expect
	public void testInvalidOrderAmount(){
		inventory.initializeConnection();
		Order o = new Order("mesh chair, 0");
		ArrayList<Furniture> combo = inventory.findCheapestCombo(o);
		assertEquals("DB Access did not fail correctly on incorrect order with invalid amount", 0, combo.size());
	}

	@Test
	public void testUnfillableOrderResponse ()
	{
		inventory.initializeConnection();
		Order o = new Order("mesh chair, 30");
		ArrayList<Furniture> combo = inventory.findCheapestCombo(o);
		assertEquals("Order could not be filled but non-empty list was returned", 0, combo.size());
	}

	@Test
	public void testExcessCounter ()
	{
		inventory.initializeConnection();
		Order o = new Order("executive chair, 1");
		ArrayList<Furniture> combo = inventory.findCheapestCombo(o);
		EnumMap<Furniture.Part, Integer> excess = inventory.excessFurnitureParts(combo, o);
		assertEquals("Incorrect number of excess parts caculated", 1, excess.get(Furniture.Part.Legs).intValue());
	}

	@Test
	public void testGetAvailableFurniture ()
	{
		inventory.initializeConnection();
		Order o = new Order("desk lamp, 1");
		ArrayList<Furniture> combo = inventory.getAvailableFurniture(o.getType(), o.getFurniture());
		assertEquals("Wrong count of available furniture", 7, combo.size());
	}

	@Test
	public void testShowManu ()
	{
		inventory.initializeConnection();
		Order o = new Order("desk lamp, 1");
		String manu = inventory.showManu(o);
		System.out.println(manu);
	}

	@Test
	public void testFindCheapestCombo ()
	{
		inventory.initializeConnection();
		Order o = new Order("mesh chair, 1");
		ArrayList<Furniture> combo = inventory.findCheapestCombo(o);
		float sum = 0;
		for (Furniture f : combo)
		{
			sum += f.getPrice();
		}
		assertEquals("Calculated min combo was incorrect", Math.round(sum), 200);
	}

	@Test
	public void testCleanList()
	{
		ArrayList<String> fin = new ArrayList<String>();
		fin.add("001"); fin.add("002"); fin.add("003"); fin.add("004"); fin.add("005");
		ArrayList<String> test = new ArrayList<String>();
		test.add("003"); test.add("001"); test.add("001"); test.add("002"); test.add("003");
		test.add("004"); test.add("002"); test.add("004"); test.add("005");
		Collections.sort(test);
		test = cleanList(test);
		assertEquals("The sorted and claned array did not match", test, fin );
	}

	/*** ReceiptPrinter CLASS TESTS ***/

	@Test
	public void testReceiptPrinterConstructorAndGetOrigRequest(){
		inventory.initializeConnection();
		Order o = new Order("mesh chair, 1");
		ArrayList<Furniture> list = inventory.findCheapestCombo(o);
		ReceiptPrinter test = new ReceiptPrinter("mesh chair, 1", list, 150);
		assertEquals("mesh chair, 1", test.getOrigRequest());
	}
	
	@Test
	public void testReceiptPrinterGetList(){
		Order o = new Order("mesh chair, 1");
		ArrayList<Furniture> list = inventory.findCheapestCombo(o);
		ReceiptPrinter test = new ReceiptPrinter("mesh chair, 1", list, 150);
		assertEquals(list, test.getItems());
	}
	
	@Test
	public void testReceiptPrinterGetPrice(){
		Order o = new Order("mesh chair, 1");
		inventory.initializeConnection();
		ArrayList<Furniture> list = inventory.findCheapestCombo(o);
		ReceiptPrinter test = new ReceiptPrinter("mesh chair, 1", list, 200);
		assertEquals(200, test.getPrice());
	}
	
	@Test 
	public void testReceiptPrinterFormatReceipt(){
		Order o = new Order("mesh chair, 1");
		ArrayList<Furniture> list = inventory.findCheapestCombo(o);
		ReceiptPrinter test = new ReceiptPrinter("mesh chair, 1", list, 200);
		String shouldBe =	"Furniture Order Form\n"
							+"\n"
							+"Faculty Name: \n"
							+"Contact: \n"
							+"Date: \n"
							+"\n"
							+"Original Request: mesh chair, 1" 
							+"\n\n"
							+"Items Ordered\n"
							+"ID: C6748\n"
							+"ID: C8138\n"
							+"ID: C9890\n"
							+"\n" +"Total Price: $200";
		
		assertEquals(shouldBe, test.getReceipt());
	}

	/*
	*  Utility methods to perform common routines
	*/

	public ArrayList<String> cleanList(ArrayList<String> l){
        ArrayList<String> tmp = new ArrayList<String>();
        boolean one = false;
        boolean two = false;
        boolean three = false;
        boolean four = false;
        boolean five = false;
        for(int i = 0; i < l.size(); i++){
            if(l.get(i).equals("001")){
                one = true;
            } if(l.get(i).equals("002")){
                two = true;
            } if(l.get(i).equals("003")){
                three = true;
            } if(l.get(i).equals("004")){
                four = true;
            } if(l.get(i).equals("005")){
                five = true;
            }
        } if(one == true){
            tmp.add("001");
        } if(two == true){
            tmp.add("002");
        } if(three == true){
            tmp.add("003");
        } if(four == true){
            tmp.add("004");
        } if(five == true){
            tmp.add("005");
        }
        return tmp;
    }

}