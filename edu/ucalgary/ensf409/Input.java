package edu.ucalgary.ensf409;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
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
public class Input extends JFrame implements ActionListener {

    JTextField in; 
    JLabel l;
    JButton b;
    private String input;
    private int counter = 1;

    public Input(){
        in = new JTextField();
        in.setBounds(300,300,150,20);
        l = new JLabel("<html> Welcome to the University of Calgary Supply Chain Management (SCM) Furniture Recycling Program\n<br>Please choose a furniture category:</html>");
        l.setBounds(50,50,800,200);
        b = new JButton("Enter");
        b.setBounds(325,450,95,30);
        b.addActionListener(this);
        add(b);
        add(in);
        add(l);
        setSize(800,800);
        setLayout(null);
        setVisible(true);

    }

    
    public void actionPerformed(ActionEvent e){
        try{

            if(counter == 1){
                input = in.getText();
                l.setText("Now Choose a furniture type");
                in.setText("");
                counter = 2;

            }

            else if (counter == 2){
                input += " " + in.getText();
                l.setText("Finally, please request the amount of the specified item you want");
                in.setText("");
                counter = 3;
            }

            else if (counter == 3){
                input += ", " + in.getText();
                l.setText("Order Complete!");

                counter = 0; 
                System.out.println(input);
            }
        } catch(Exception ex){System.out.println(ex);}

    }

    public String getInput(){

        return input;
    }


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

	public static void main(String[] args){ //throws OrderArgumentNotProvidedException {
		//if(args.length > 0) {
           new Input(); 

           // StringBuilder tmp = new StringBuilder();
           //tmp.append(args[0].strip() + " " + args[1].strip() + " " + args[2].strip());
           //Inventory inventory = new Inventory();
            //inventory.initializeConnection();
			//Order example = new Order(new String(tmp));
            
            //String result = inventory.executeOrder(example);
            //System.out.println(result);
		}
        //else {
			//throw new OrderArgumentNotProvidedException();
		//}
	}  
//}
