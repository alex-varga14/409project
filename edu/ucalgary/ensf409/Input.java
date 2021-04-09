package edu.ucalgary.ensf409;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.concurrent.CountDownLatch; 
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
Fields:
JTextField in;
    - Initialize a textfield that allows for user input in the GUI.
JLabel l;
    - Initialize a label that gives the user directions on what to input.
JButton b;
    - Initialize a button that can be clicked on.
JButton exit; 
    - Initalize a button that can be clicked on.
private String input;
    - String to store user input 
private int counter = 1;
    - Counter for button clicks
private String input1;
private String input2;
private String input3;
    - all three receive various input strings for the order
private String regex = "[0-9]+";
    - regex for amount of items being ordered
public CountDownLatch loginSignal = new CountDownLatch(1);
    - Waits until order has been input before instantiating order object

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
public Input()
public void actionPerformed(ActionEvent e)
public String getInput()
*/

public class Input extends JFrame implements ActionListener {
    JTextField in; 
    JLabel l;      
    JButton b;     
    JButton exit;  
    private String input; 
    private int counter = 1; 
    private String input1;
    private String input2;
    private String input3;
    private String regex = "[0-9]+";
    public CountDownLatch loginSignal = new CountDownLatch(1);
    public Input(){ // sets up GUI
        in = new JTextField();
        in.setText("");
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800); // Sets size of window
        setLayout(null); 
        setVisible(true); // Enables window to be seen
    }
    
    public void actionPerformed(ActionEvent e){ // Function that determines what the user should input based on the state of the counter
        try{
            
            if(counter == 1){
                input1 = in.getText();
                input1 = input1.toLowerCase().strip();
                if (input1.compareTo("kneeling") == 0 || input1.compareTo("task") == 0 || input1.compareTo("mesh") == 0 
                || input1.compareTo("executive") == 0  || input1.compareTo("ergonomic") == 0  || input1.compareTo("standing") == 0 
                ||input1.compareTo("adjustable") == 0 ||input1.compareTo("traditional") == 0  || input1.compareTo("desk") == 0  
                || input1.compareTo("study") == 0  || input1.compareTo("swing Arm") == 0 || input1.compareTo("small") == 0 
                || input1.compareTo("medium") == 0 || input1.compareTo("large") == 0){
                input = input1;
                l.setText("Now Choose a furniture type:");
                in.setText("");
                counter = 2;
                }
            else{
                l.setText("Incorrect input, press Enter to try again please");
                in.setText("");
                input = "";
                counter = 4;
                
            }
            }
            else if (counter == 2){
                input2 = in.getText();
                input2 = input2.toLowerCase().strip();
                if (input1.compareTo("kneeling") == 0 && input2.compareTo("chair") == 0|| 
                input1.compareTo("task") == 0 && input2.compareTo("chair") == 0
                || input1.compareTo("mesh") == 0 && input2.compareTo("chair") == 0 || input1.compareTo("executive") == 0 && input2.compareTo("chair") == 0 
                || input1.compareTo("ergonomic") == 0 && input2.compareTo("chair") == 0 || input1.compareTo("standing") == 0 && input2.compareTo("desk") == 0 
                ||input1.compareTo("adjustable") == 0 && input2.compareTo("desk") == 0 ||input1.compareTo("traditional") == 0 && input2.compareTo("desk") == 0  
                || input1.compareTo("desk") == 0 && input2.compareTo("lamp") == 0 || input1.compareTo("study") == 0 && input2.compareTo("lamp") == 0   
                || input1.compareTo("swing Arm") == 0  && input2.compareTo("lamp") == 0 || input1.compareTo("small") == 0  && input2.compareTo("filing") == 0  || input1.compareTo("medium") == 0 && input2.compareTo("filing") == 0 
                || input1.compareTo("large") == 0 && input2.compareTo("filing") == 0 ){
                input += " " + input2;
                l.setText("Finally, please request the amount of the specified item you want:");
                in.setText("");
                counter = 3;
                }
                else{
                    l.setText("Incorrect input, press Enter to try again please");
                    in.setText("");
                    counter = 4;
                }
            }
            else if (counter == 3){
                input3 = in.getText().strip();
                if (input3.matches(regex)){
                input += ", " + input3;
                l.setText("Order Complete!");
                loginSignal.countDown();
                }
                else {
                    l.setText("Incorrect input, press Enter to try again please");
                    in.setText("");
                    counter = 4;
                }
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

    public static void main(String[] args) throws InterruptedException { //throws OrderArgumentNotProvidedException {
        Input input = new Input(); 
        input.loginSignal.await();
        Inventory inventory = new Inventory();
        inventory.initializeConnection();
        if (input.getInput() == null) {
            throw new OrderArgumentInvalidException();
        }
        Order example = new Order(input.getInput());
        String result = inventory.executeOrder(example);
        System.out.println(result);
    }
}  


/*
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
}  */
