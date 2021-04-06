package edu.ucalgary.ensf409;
import java.util.*;
import java.io.*;
/**
 * ENSF409 FINAL PROJECT GROUP 40
 * Authors:
 * 
 * Version 1.0
 * 
 * 
 */
/*
This class serves to receive command line input for an order request.
Basic for now, implementation can be made better and more efficient possibly.
*/
//**** NOT FINISHED */
public class Input {
    /* main()
	   * Accept a command-line argument which specifies a user input for 1) furniture category, 2) its type,
       * and 3) the number of items requested.
	   * The argument should be in order stated above as three strings.
       * EXAMPLE INPUTs:
       * mesh chair, 1
       * executive chair, 2
       * Therefore, the category followed by a space then its type followed by a comma then a space and 
       * finally the requested amount.
	   * If no argument is specified, it throws a custom exception, 
       * OrderArugmentNotProvidedException. Additional arguments are ignored.
	  */

	public static void main(String[] args) throws OrderArgumentNotProvidedException {
		if(args.length > 0) {
       
            StringBuilder tmp = new StringBuilder();
            tmp.append(args[0].strip() + " " + args[1].strip() + " " + args[2].strip());
            Inventory inventory = new Inventory();
            inventory.initializeConnection();
			Order example = new Order(new String(tmp));
            
            String result = inventory.executeOrder(example);
            System.out.println(result);
		}
        else {
			throw new OrderArgumentNotProvidedException();
		}
	}  
}
