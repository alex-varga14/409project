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

    JTextField in; // Initialize a textfield that allows for user input in the GUI.
    JLabel l;      // Initialize a label that gives the user directions on what to input.
    JButton b;     // Initialize a button that can be clicked on.
    JButton exit;  // Initalize a button that can be clicked on.
    private String input; // String to store user input 
    private int counter = 1; // Counter for button clicks

    public Input(){ // sets up GUI
        in = new JTextField();
        in.setBounds(300,300,150,20);   // Sets position and size of TextField
        l = new JLabel("<html> Welcome to the University of Calgary Supply Chain Management (SCM) Furniture Recycling Program\n<br>Please choose a furniture category:</html>");    // Set text shown in label
        l.setBounds(50,50,800,200); // Sets position and size of Label
        b = new JButton("Enter"); 
        exit = new JButton("Exit");
        exit.setBounds(325,550,95,30);  // Sets position and size of Exit Button
        b.setBounds(325,450,95,30); // Sets position and size of TextField
        b.addActionListener(this);  // Adds ActionListener to the button
        exit.addActionListener(this); // Adds ActionListener to the button
        add(b); //Adds button b to GUI
        add(in); //Adds TextField in to GUI
        add(l); //Adds Label l to GUI
        add(exit); //Adds button exit to GUI
        setSize(800,800); // Sets size of window
        setLayout(null); 
        setVisible(true); // Enables window to be seen

    }

    
    public void actionPerformed(ActionEvent e){ // Function that determines what the user should input based on the state of the counter
        try{

            if(counter == 1){
                input = in.getText();
                l.setText("Now Choose a furniture type:");
                in.setText("");
                counter = 2;

            }

            else if (counter == 2){
                input += " " + in.getText();
                l.setText("Finally, please request the amount of the specified item you want:");
                in.setText("");
                counter = 3;
            }

            else if (counter == 3){
                input += ", " + in.getText();
                l.setText("Order Complete! Click enter for new order");


                counter = 4;
            }

            else if (counter == 4){
                input = "";
                l.setText("Choose a furniture category");
                in.setText("");
                counter = 1;
            }

            if(e.getSource() == exit){
                System.exit(0);
            }
        } catch(Exception ex){System.out.println(ex);}

    }


    public String getInput(){

        return input.toString();

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

	public static void main(String[] args) { //throws OrderArgumentNotProvidedException {
           Input input = new Input(); 

           // StringBuilder tmp = new StringBuilder();
           //tmp.append(args[0].strip() + " " + args[1].strip() + " " + args[2].strip());
            //Inventory inventory = new Inventory();
            //inventory.initializeConnection();
			//Order example = new Order(input.getInput());

           //if (input.getInput == null) {
                //throw new OrderArgumentNotProvidedException();
           // }
            
            //String result = inventory.executeOrder(example);
       
               System.out.println(input.getInput());
           

		}

	}  

