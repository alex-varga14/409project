package edu.ucalgary.ensf409;
import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;

/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Dominic Vandekerkhove
 * @version 1.0
 * @since 1.0
 * 
 */
/* 
This class tests all the possible 
*/

public class FinalProjectTest{
	/*** INPUT CLASS TESTS ***/
	public Inventory inventory;
	@Before
	public void setUp(){
		inventory = new Inventory();
		inventory.initializeConnection();
	}


	/*** ORDER CLASS TESTS ***/
	@Test
	//Constructor create with an order
	public void testConstructorOrderGetElements() {
		Order testOrder = new Order("mesh chair, 1");
	
		String type = testOrder.getType();
		String furniture = testOrder.getFurniture();
		int amount = testOrder.getAmount();
	
		// Retrieve order, check if it matches 
		String order = type + " " + furniture + " " + String.valueOf(amount);
		assertTrue("Creating order and using getters to deteming if its placed properly failed", 
		order.equals(testOrder.getType() + " " + testOrder.getFurniture() + " " + testOrder.getAmount()));
	  }


	/*** INVENTORY CLASS TESTS ***/

	/*** FURNITURE CLASS TESTS ***/

	/*** ReceiptPrinter CLASS TESTS ***/

}