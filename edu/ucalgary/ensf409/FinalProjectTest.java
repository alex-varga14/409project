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
 * @authors: Dominic Vandekerkhove, Alex Varga
 * @version 1.0
 * @since 1.0
 * 
 */
/* 
This class tests all the possible scenarios 
*/

public class FinalProjectTest{

	public FinalProjectTest(){
	}

	public final static Inventory inventory = new Inventory();
	


	// public Inventory inventory;
	// @Before
	// public void setUp(){
	// 	inventory = new Inventory();
	// 	inventory.initializeConnection();
	// }



	/*** INPUT CLASS TESTS ***/


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

	//@TEST
	//
	//
	//
	




	/*** INVENTORY CLASS TESTS ***/
	@Test
	public void testInventoryContructorAndDBAccess ()
	{
		inventory.initializeConnection();
		int len = inventory.getAvailableFurniture("desk", "lamp").size();
		assertEquals("Wrong number of inventory items fetched", len, 7);
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

	/*** FURNITURE CLASS TESTS ***/



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

}