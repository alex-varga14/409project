package edu.ucalgary.ensf409;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.concurrent.CountDownLatch; 
import javax.imageio.ImageIO;
import java.io.*;
/**
 * @ENSF409 FINAL PROJECT GROUP 40
 * @team members: Alex Varga, Ben Krett, Domninic Vanderkerkhove, and Kenny Jeon
 * @authors: Kenny Jeon, Alex Varga and Ben Krett
 * @version 1.3
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
private String t;
private String f;
private String input3;
    - all three receive various input strings for the order
private String regex = "[0-9]+";
    - regex for amount of items being ordered
public CountDownLatch loginSignal = new CountDownLatch(1);
    - Waits until order has been input before instantiating order object

Methods:
    -public static void main(String[] args) throws InterruptedException 
        * Accept a user-input argument through GUI which specifies a user input for 1) furniture category, 
        * 2) its type, and 3) the number of items requested.
        INSTRUCTIONS TO RUN:
        1) access correct file path and run:
            - javac -cp .;lib\mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/Input.java
        2) run the program
            - java -cp .;lib\mysql-connector-java-8.0.23.jar edu.ucalgary.ensf409.Input
        3) follow the GUI and input the correct order.
        4) exit program when done

    -public Input()
    -public void actionPerformed(ActionEvent e)
    -public String getInput()
    -public void orderFailed(String x)
    -public void orderComplete(String x)
    -public void setInput(String x)

*/
public class Input implements ActionListener{

    boolean status = false;
    JTextField in;
    JLabel l;      
    JButton b;     
    JButton exit;  
    private String input; 
    private int counter = 1; 
    private String t;
    private String f;
    private String input3;
    private String regex = "[0-9]+";
    public CountDownLatch loginSignal = new CountDownLatch(1);
    public Input(){
        JFrame frame = new JFrame("University of Calgary Supply Chain Management (SCM) Furniture Recycling Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,50);
        try{
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("furniture.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        in  = new JTextField();
        in.setText("");
        l = new JLabel("<html> Welcome to the Furniture Recycling Program:</html>", JLabel.CENTER); 
        l.setForeground(Color.red);
        l.setOpaque(true);
        l.setFont(new Font("Arial", Font.BOLD, 25));
        l.setBounds(100,50,600,50); // Sets position and size of Label
        b = new JButton("Begin Order"); 
        exit = new JButton("Exit");
        exit.setForeground(Color.red);
        exit.setFont(new Font("Arial", Font.PLAIN, 40));
        b.setFont(new Font("Arial", Font.PLAIN, 20));
        exit.setBounds(600,650,150,60);  // Sets position and size of Exit Button
        b.setBounds(300,650,150,30); // Sets position and size of TextField
        b.addActionListener(this);  // Adds ActionListener to the button
        exit.addActionListener(this); // Adds ActionListener to the button
        frame.add(b); //Adds button b to GUI
        frame.add(in); //Adds TextField in to GUI
        frame.add(l); //Adds Label l to GUI
        frame.add(exit); //Adds button exit to GUI
        
        /* Kinda Broken
        //Enter feature
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"click button");
        frame.getRootPane().getActionMap().put("click button", new AbstractAction(){
            public void actionPerformed(ActionEvent ae){
                if(!status){ b.doClick(); }
            // if((t == null && counter == 2 )|| (f == null && counter == 3 ) || (input3 == null && counter == 4 )){
            //     counter = 5;
            // }
            }
        }); */


        /*Useless feature as is, can be used potentially */
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Order Menu");
        JMenu c = new JMenu("Chairs");
        menu.setMnemonic(KeyEvent.VK_O);
        
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("Chair");
        
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = new JRadioButtonMenuItem("Desk");
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = new JRadioButtonMenuItem("Lamp");
        group.add(menuItem);
        menu.add(menuItem);
        menuItem = new JRadioButtonMenuItem("Filing");
        group.add(menuItem);
        menu.add(menuItem);
        //menuItem = new JRadioButtonMenuItem(new JMenu("Chair"));

        bar.add(menu);
        frame.setJMenuBar(bar);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 800);
    }
    /**
     * Getter for the input (order) string
     * @return input
     */
    public String getInput(){
        return input.toString();
    }

    /**
     * Setter for setting input for testing
     * @param x String to set input
     */
    public void setInput(String x){
        this.input = x;
    }

    /**Method that tracks user actions ie clicks on the buttons.
     * @param e An ActionEvent such as a button click
     */
    public void actionPerformed(ActionEvent e){
        in.setBounds(250,500,250,30);   // Sets position and size of TextField
        try{
            if(counter == 1){
                l.setText("Choose a furniture category:");
                b.setText("Continue");
                counter = 2;
            } else if (counter == 2){
                t = in.getText();
                t = t.toLowerCase().strip();
                if((t.strip().toLowerCase().equals("mesh")) || (t.strip().toLowerCase().equals("kneeling")) || 
                (t.strip().toLowerCase().equals("task")) || (t.strip().toLowerCase().equals("executive")) || (t.strip().toLowerCase().equals("ergonomic")) ||
                (t.strip().toLowerCase().equals("standing")) || (t.strip().toLowerCase().equals("traditional")) || (t.strip().toLowerCase().equals("adjustable") )
                || (t.strip().toLowerCase().equals("desk")) || (t.strip().toLowerCase().equals("study")) || (t.strip().toLowerCase().equals("swing arm")) ||
                (t.strip().toLowerCase().equals("small")) || (t.strip().toLowerCase().equals("medium")) || (t.strip().toLowerCase().equals("large"))){
					input = t;

					in.setText("");
					l.setText("Now, choose a furniture type:");
					counter = 3;
				} else {
                    counter = 5;
                }

            } else if (counter == 3){
                f = in.getText();
                f = f.toLowerCase().strip();
                if( ((t.strip().toLowerCase().equals("desk")) && (!(f.strip().toLowerCase().equals("desk"))))|| 
                ((!(t.strip().toLowerCase().equals("desk"))) && (f.strip().toLowerCase().equals("desk"))) ||
                (f.strip().toLowerCase().equals("chair")) || (f.strip().toLowerCase().equals("lamp")) || 
                (f.strip().toLowerCase().equals("filing"))){ 
					input += " " + f;

					in.setText("");
					l.setText("Finally, choose the amount:");
					counter = 4;
				} else{
                    counter = 5;
                }
            } else if (counter == 4){
                input3 = in.getText().strip();
                if (input3.matches(regex)){
                    input += ", " + input3;
                } else{
                    counter = 5;
                }
                in.setText("");
                loginSignal.countDown();
            } if (counter == 5){
                input = "";
                l.setText("Invalid Order! Please Restart Order");
                counter = 1;
            } if(e.getSource() == exit){
                System.exit(0);
            }

        } catch(Exception ex){System.out.println(ex);}
    }

    /** Outputs order failed message and suggested manufacturers
     * @param x String with manufacturer list
     */
    public void orderFailed(String x){
        in.setBounds(0,0,0,0);
        l.setBounds(100,50,600,100);
        l.setFont(new Font("Arial", Font.PLAIN, 15));
        l.setText("<html> Order cannot be fulfilled based on current inventory. Manufacturers include: <br/>" + x+ "</html>");
        b.setBounds(0,0,0,0);
        exit.setFont(new Font("Arial", Font.PLAIN, 25));
        exit.setBounds(300,450,130,30);
    }

    /** Outputs order complete message 
     * @param x Empty string
     */
    public void orderComplete(String x){
        in.setBounds(0,0,0,0);
        l.setBounds(100,50,600,100);
        l.setText("Order Complete!");
        b.setBounds(0,0,0,0);
        exit.setFont(new Font("Arial", Font.PLAIN, 25));
        exit.setBounds(300,450,130,30);
        status = true;
    }



    public static void main(String[] args) throws InterruptedException { 
        Input input = new Input(); 
        input.loginSignal.await();
        Inventory inventory = new Inventory();
        inventory.initializeConnection();
        if (input.getInput() == null) {
            throw new OrderArgumentInvalidException();
        }
        Order example = new Order(input.getInput());
        String result = inventory.executeOrder(example);
        if(inventory.getComp() == true){
            input.orderComplete(result);
        } else {
            input.orderFailed(result);
        }
    }
}
