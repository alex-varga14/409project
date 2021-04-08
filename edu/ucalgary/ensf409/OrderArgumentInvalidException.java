package edu.ucalgary.ensf409;
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @author: Alex Varga 
 * @version 1.1
 * @since 1.0
 * 
 */
/* OrderArgumentInvalidExcepton Class Documentation:
This class serves to throw an exception in Input.java if the user input is invalid. Extends RuntimeException
*/
public class OrderArgumentInvalidException extends RuntimeException{
    public OrderArgumentInvalidException() {
		super("ERROR: ORDER INVALID.");
	}
}
