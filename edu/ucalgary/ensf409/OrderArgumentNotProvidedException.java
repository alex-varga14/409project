package edu.ucalgary.ensf409;
/**
 * ENSF409 FINAL PROJECT GROUP 40
 * Authors:
 * 
 * Version 1.0
 * 
 * 
 */
/*
This class serves to throw an exception.
*/
public class OrderArgumentNotProvidedException extends RuntimeException{
    public OrderArgumentNotProvidedException() {
		super("ERROR: ORDER NOT PROVIDED.");
	}
}
