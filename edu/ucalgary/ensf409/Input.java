package edu.ucalgary.ensf409;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
/**
 * @ ENSF409 FINAL PROJECT GROUP 40
 * @authors: Kenny Jeon, Alex Varga and Ben Krett
 * @version 1.2
 * @since 1.0
 * 
 */
/* Input Class Documentation:
This class serves to receive an user input and instantiate an Order instance. Checks if the order is valid according to
database furniture items and type.

Methods:
main()
	* Accept a user-input argument which specifies a user input for 1) furniture category, 2) its type,
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
public class Input {
	public static void main(String[] args) throws OrderArgumentInvalidException {
        try{
            String t, f, n;
            t = JOptionPane.showInputDialog(
            "Welcome to the University of Calgary Supply Chain Management (SCM) Furniture Recycling Program\n Please choose a furniture category:");
            if((t.strip().toLowerCase().equals("mesh")) || (t.strip().toLowerCase().equals("kneeling")) || 
            (t.strip().toLowerCase().equals("task")) || (t.strip().toLowerCase().equals("executive")) || (t.strip().toLowerCase().equals("ergonomic")) ||
            (t.strip().toLowerCase().equals("standing")) || (t.strip().toLowerCase().equals("traditional")) || (t.strip().toLowerCase().equals("adjustable") )
             || (t.strip().toLowerCase().equals("desk")) || (t.strip().toLowerCase().equals("study")) || (t.strip().toLowerCase().equals("swing arm")) ||
             (t.strip().toLowerCase().equals("small")) || (t.strip().toLowerCase().equals("medium")) || (t.strip().toLowerCase().equals("large"))){
                f = JOptionPane.showInputDialog("Now Choose a furniture type:");
                f.strip();
                if( ((t.strip().toLowerCase().equals("desk")) && (!(f.strip().toLowerCase().equals("desk"))))|| 
                ((!(t.strip().toLowerCase().equals("desk"))) && (f.strip().toLowerCase().equals("desk"))) ||
                (f.strip().toLowerCase().equals("chair")) || (f.strip().toLowerCase().equals("lamp")) || 
                (f.strip().toLowerCase().equals("filing"))){ 
                    n = JOptionPane.showInputDialog("Finally, please request the amount of the specified item you want:");
                    int j = Integer.parseInt(n.strip());
                    if(j > 0){
                        Inventory inventory = new Inventory();
                        inventory.initializeConnection();
                        Order example = new Order(t.strip() + " " + f.strip() + ", " + n.strip());
                        String result = inventory.executeOrder(example);
                        System.out.println(result);
                    } else {
                        throw new OrderArgumentInvalidException();
                    }
                } else {
                    throw new OrderArgumentInvalidException();
                }
             } else {
                 throw new OrderArgumentInvalidException();
             }
           // StringBuilder tmp = new StringBuilder();
           //tmp.append(args[0].strip() + " " + args[1].strip() + " " + args[2].strip());
        } catch (OrderArgumentInvalidException e){
            System.err.println("ERROR: INVALID INPUT");
            System.exit(1);
        }    
	}
}  
