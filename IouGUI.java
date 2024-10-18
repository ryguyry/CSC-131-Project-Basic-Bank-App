import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IouGUI implements ActionListener, KeyListener {
	//here are our variables for the GUI
    private JLabel label;
    private JButton requestButton;
    private JButton manageButton;
    private JFrame frame;
    private JPanel panel;
    private JTextField amountField;
    private JTextField creditorField;
    private JLabel amountLabel;
    private JLabel creditorLabel;
    private double amount;
    private String creditor;
    
    //here is our constructor for the GUI
    public IouGUI() {
        frame = new JFrame();
        panel = new JPanel();       
        //boxLayout for vertical alignment
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 50)); // Padding around the panel        
        //label at the top
        label = new JLabel("IOU Manager");
        label.setFont(new Font("Arial", Font.BOLD, 24)); // set font size to 24
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT); // center the label
        //buttons and their labels
        requestButton = new JButton("Create a new IOU");
        manageButton = new JButton("Manage Existing IOU");
        //resize buttons
        requestButton.setPreferredSize(new Dimension(400, 50));
        manageButton.setPreferredSize(new Dimension(400, 50));
        //align the buttons
        requestButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        manageButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        //action listeners check for buttons being pressed
        requestButton.addActionListener(this);
        manageButton.addActionListener(this);
        //components are added to the window panel
        panel.add(label); // Add the label first
        panel.add(Box.createVerticalStrut(50)); // Vertical space between label and buttons
        panel.add(requestButton); // Add request button
        panel.add(Box.createVerticalStrut(15)); // Vertical space between buttons
        panel.add(manageButton); // Add manage button
        //set up the windows frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Basic Banking");
        frame.pack();
        frame.setVisible(true);
        //mouseListener to transfer focus away from a text box on panel click
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });
    }
    //here is the main function
    public static void main(String[] args) { 
        new IouGUI();
    }
    //button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == requestButton) {
            if (requestButton.getText().equals("Create a new IOU")) {
                label.setText("Request Money");
                requestButton.setText("Send Request");
                manageButton.setText("Back");             
                //add text fields and labels for "Amount" and "Creditor" if they are not there
                if (amountField == null && creditorField == null) {
                	requestButton.setEnabled(false); //here we have disabled the request button
                    amountLabel = new JLabel("Amount:");
                    creditorLabel = new JLabel("Creditor's Name:");
                    amountField = new JTextField(15);
                    creditorField = new JTextField(15);
                    //added KeyListeners to enable button only if text fields are filled
                    amountField.addKeyListener(this);
                    creditorField.addKeyListener(this);
                    //align components
                    amountLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                    amountField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
                    creditorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                    creditorField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
                    //add components in the correct order (below buttons)
                    panel.add(Box.createVerticalStrut(20)); //space between label and fields
                    panel.add(amountLabel);
                    panel.add(amountField);
                    panel.add(Box.createVerticalStrut(10)); //space between Amount and Creditor
                    panel.add(creditorLabel);
                    panel.add(creditorField);
                    //refresh layout
                    panel.revalidate();
                    panel.repaint();
                }
            } else if (requestButton.getText().equals("Send Request")) {
                //save values when "Send Request" is pressed
                amount = Double.parseDouble(amountField.getText().trim());
                creditor = creditorField.getText().trim();                
                //update label and buttons for confirmation
                label.setText("Confirm IOU:");
                requestButton.setText("Confirm");
                manageButton.setVisible(false); //hide the manage button
                //display the amount and creditor for confirmation
                amountLabel.setText("Amount: $" + amount);
                creditorLabel.setText("Creditor: " + creditor);
                //remove text fields since we're showing confirmed values
                panel.remove(amountField);
                panel.remove(creditorField);
                panel.revalidate();
                panel.repaint();
            } else if (requestButton.getText().equals("Confirm")) {
                //final confirmation scenario
                label.setText("Thank you!");
                amountLabel.setText("Request Sent");
                creditorLabel.setText(creditor + " has been informed.");
                requestButton.setText("Return to IOU Manager");
                manageButton.setVisible(false);
            }else if (requestButton.getText().equals("Return to IOU Manager")) {
                //go back to the initial state
                label.setText("IOU Manager");
                requestButton.setText("Create a new IOU");
                manageButton.setVisible(true);
                manageButton.setText("Manage Existing IOU");
                panel.remove(amountLabel);
                panel.remove(amountField);
                panel.remove(creditorLabel);
                panel.remove(creditorField);
                amountField = null;
                creditorField = null;
                panel.revalidate();
                panel.repaint();
            }       
        }
        //manage button functionality
        	if (e.getSource() == manageButton) {
        		if (manageButton.getText().equals("Manage Existing IOU")) {
        			if (amount != 0.0 && creditor != null) {
        				label.setText("Existing IOU");
        				requestButton.setText(creditor + " $" + amount);
        	            manageButton.setText("Back");
        			}
        			else {
        				label.setText("Existing IOU");
        				creditorLabel = new JLabel("No IOU has been found");
        				creditorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        				panel.add(creditorLabel);
        				requestButton.setVisible(false);
        				manageButton.setText("Back");
        			}
        		}
        		else if (manageButton.getText().equals("Back")) {
        			if (requestButton.getText().equals("Send Request")) {
        				//go back to the initial state       	
        				label.setText("IOU Manager");
        				requestButton.setText("Create a new IOU");
        				requestButton.setEnabled(true);
        				manageButton.setText("Manage Existing IOU");
        				panel.remove(amountLabel);
        				panel.remove(amountField);
        				panel.remove(creditorLabel);
        				panel.remove(creditorField);
        				amountField = null;
        				creditorField = null;
        				panel.revalidate();
        				panel.repaint();
        			}
        			else {
        				//go back to the initial state       	
        				label.setText("IOU Manager");
                     	requestButton.setText("Create a new IOU");
                        requestButton.setVisible(true);
                        manageButton.setText("Manage Existing IOU");
                        panel.remove(creditorLabel);
        			}
                }
        			
          	
        }
    }  
  

    @Override
    public void keyReleased(KeyEvent e) {
        //enable "Send Request" only when both fields are filled
        if (!amountField.getText().trim().isEmpty() && !creditorField.getText().trim().isEmpty()) {
            requestButton.setEnabled(true);
        } else {
            requestButton.setEnabled(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //not used
    }
}

