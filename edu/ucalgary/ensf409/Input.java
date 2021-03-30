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

public class Input{
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

    //**** NOT FINISHED */

	public static void main(String[] args) {

		if(args.length > 0) {
            StringBuilder tmp = new StringBuilder();
            tmp.append(args[0].strip() + " " + args[1].strip() + " " + args[2].strip());
			Order example = new Order(new String(tmp));
		}
	}  
}
