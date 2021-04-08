package edu.ucalgary.ensf409;
import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;

/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Dominic Vandekerkhove, Alex Varga
 * @version 1.0
 * @since 1.0
 * 
 */
/* 
This class tests all the possible 
*/

public class FinalProjectTest{

	public FinalProjectTest(){
	}


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

	@TEST
	//
	//
	//
	




	/*** INVENTORY CLASS TESTS ***/

	/*** FURNITURE CLASS TESTS ***/

	/*** ReceiptPrinter CLASS TESTS ***/



}