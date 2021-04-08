package edu.ucalgary.ensf409;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Dominic Vandekerkhove
 * @version 1.0
 * @since 1.0
 * 
 */
/* 
This class tests all the possible scenarios 
*/

public class FinalProjectTest{
	public final static Inventory inventory = new Inventory();
	
	
	@Before
	public static void setUp(){
		inventory.initializeConnection();
	}
	
	
	@Test
	public void testReceiptPrinterConstructorAndGetOrigRequest(){
		inventory.initializeConnection();
		Order o = new Order("mesh chair, 1");
		ArrayList<Furniture> list = inventory.findCheapestCombo(o);
		ReceiptPrinter test = new ReceiptPrinter("mesh chair, 1", list, 150);
		assertEquals("mesh chair, 1", test.getOrigRequest());
	}
	
	@Test
	public void testReceiptPrinterValidWriteToFile(){
		File remove = new File("orderForm.txt");
		if (remove.exists())//ensures file does not exist before testing
			remove.delete();
		Order o = new Order("mesh chair, 1");
		ArrayList<Furniture> list = inventory.findCheapestCombo(o);
		ReceiptPrinter test = new ReceiptPrinter("mesh chair, 1", list, 150);
		File file = new File("orderForm.txt");
		boolean exists = file.exists();
		assertTrue(exists);
	}

	/*
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
		ArrayList<Furniture> list = inventory.findCheapestCombo(o);
		ReceiptPrinter test = new ReceiptPrinter("mesh chair, 1", list, 150);
		assertEquals(150, test.getPrice());
	}
	
	@Test 
	public void testReceiptPrinterFormatReceipt(){
		Order o = new Order("mesh chair, 1");
		ArrayList<Furniture> list = inventory.findCheapestCombo(o);
		ReceiptPrinter test = new ReceiptPrinter("mesh chair, 1", list, 150);
		String shouldBe =	"Furniture Order Form\n"
							+"\n"
							+"Faculty Name: \n"
							+"Contact: \n"
							+"Date: \n"
							+"\n"
							+"Original Request: mesh chair, 1" 
							+"\n\n"
							+"Items Ordered\n"
							+"ID: C9890\n"
							+"ID: C0942\n"
							+"\n" +"Total Price: $150";
		
		assertEquals(shouldBe, test.getReceipt());
	}
	*/
}