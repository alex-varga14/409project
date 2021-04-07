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
	public Inventory inventory;
	@Before
	public void setUp(){
		inventory = new Inventory();
		inventory.initializeConnection();
	}
}