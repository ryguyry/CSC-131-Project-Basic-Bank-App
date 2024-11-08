import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class accountGUI implements ActionListener, KeyListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JLabel transactionLabel;
    private JTextField transactionField;
    private JLabel depositLabel;
    private JTextField depositField;
    private JButton depositButton;
    private JButton iouButton;
    private JButton backButton;
    String transaction;
    String recipient;
    double deposit;
    double ioupayment;
    boolean isValidAmount;

    public accountGUI() {
        frame = new JFrame();
        panel = new JPanel();
        
        // Set up BoxLayout for vertical alignment in the main panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 50));
        
        // Title label at the top
        label = new JLabel("Transaction Menu");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        // Transaction label and field
        transactionLabel = new JLabel("Today is a great day for banking " + " how can we help?");
        transactionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        transactionField = new JTextField(15);
        transactionField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        
        // Deposit label and field
        depositLabel = new JLabel("Your current balacne is: " + "$");
        depositLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        depositField = new JTextField(15);
        depositField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        
        // Buttons for deposit, withdrawal, and back
        depositButton = new JButton("Deposit");
        iouButton = new JButton("Pay an IOU");
        backButton = new JButton("Back");
        
        // Panel to hold deposit and withdrawal buttons side by side
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        
        // Align and add the buttons to the horizontal panel
        depositButton.setPreferredSize(new Dimension(200, 50));
        iouButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(depositButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Spacing between buttons
        buttonPanel.add(iouButton);
        
        // Center-align the horizontal panel and back button
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        backButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        
        // Add ActionListeners and KeyListeners to buttons and fields
        depositButton.addActionListener(this);
        iouButton.addActionListener(this);
        backButton.addActionListener(this);
        transactionField.addKeyListener(this);  // Add KeyListener to transactionField
        depositField.addKeyListener(this);      // Add KeyListener to depositField if needed
        
        // Add components to the main panel
        panel.add(label);
        panel.add(Box.createVerticalStrut(20));
        panel.add(transactionLabel);
        panel.add(transactionField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(depositLabel);
        panel.add(depositField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel); // Add the button panel with deposit and withdrawal
        panel.add(Box.createVerticalStrut(5));
        panel.add(backButton);
        
        // Set up the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Transaction Menu");
        frame.pack();
        frame.setVisible(true);

        transactionField.setVisible(false);
        depositField.setVisible(false);
        backButton.setVisible(false);
        
        
        // MouseListener to remove focus from text fields on panel click
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });
    }
    private void resetToDefaultState() {
        transactionLabel.setText("Today is a great day for banking " + " How can we help?");
        transactionField.setVisible(false);
        depositLabel.setVisible(true);
        depositLabel.setText("Your current balacne is: " + "$");
        depositField.setVisible(false);
        depositButton.setText("Deposit");
        iouButton.setText("Pay an IOU");
        backButton.setVisible(false);
        depositButton.setVisible(true);
        iouButton.setVisible(true);
        depositButton.setEnabled(true);
        transactionField.setText("");
        depositField.setText("");
    }
    // Implement the ActionListener method for button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == depositButton) {
            if (depositButton.getText().equals("Deposit")) {
                // Update the label and button text for deposit mode
                transactionLabel.setText("How much would you like to deposit?");
                transactionField.setVisible(true);  // Show the transaction field for user input
                depositLabel.setVisible(false);     // Hide the deposit label
                depositButton.setText("Confirm");   // Change button text to Confirm
                depositButton.setEnabled(false);
                iouButton.setText("Back");   // Change Withdraw button to act as Back
                backButton.setVisible(false);       // Hide the original Back button          
            } else if (depositButton.getText().equals("Confirm")) {
            	// Capture the deposit amount and display confirmation
            	if (transactionField.getText().equals("How much would you like to deposit?")) {
            		try {
            			// Parse the text to a double and round to two decimal places
            			double depositAmount = Double.parseDouble(transactionField.getText());
            			depositAmount = Math.round(depositAmount * 100.0) / 100.0;

            			// Display the confirmation message
            			transactionLabel.setText("Thank you, your deposit of $" + depositAmount + " has been confirmed.");

            			// Hide input fields and buttons for the confirmation screen
            			transactionField.setVisible(false);
            			depositButton.setVisible(false);
            			iouButton.setVisible(false);
            			backButton.setVisible(true); // Show the back button to return to the menu
            		} catch (NumberFormatException ex) {
                    // Handle the case where the input is not a valid number
                    transactionLabel.setText("Invalid input. Please enter a valid number.");
            		}
            	}
            	else if(transactionField.getText().equals("How much would you like to transfer?")){
            		//TRASACTION TO DATABASE CHECK GOES HERE
            	}
            	}
            System.out.println("Deposit clicked. Amount: " + depositField.getText());
        } else if (e.getSource() == iouButton) {
        	if (iouButton.getText().equals("Pay an IOU")) {
                // Update for IOU payment mode
                transactionLabel.setText("How much would you like to transfer?");
                transactionField.setVisible(true); // Show the transaction field for amount entry
                
                depositLabel.setText("Who do you want to transfer money to?");
                depositLabel.setVisible(true); // Show the deposit label as recipient prompt
                
                depositField.setVisible(true); // Show deposit field to enter recipient's name
                
                depositButton.setText("Confirm");   // Set Deposit button text to "Confirm"
                depositButton.setEnabled(false);
                iouButton.setText("Back");   // Set Withdrawal button text to "Back"
                backButton.setVisible(false);       // Hide the original Back button
            } else if (iouButton.getText().equals("Back")) {
                // Return to the original state
                resetToDefaultState();
            }
            System.out.println("Withdraw clicked. Amount: " + transactionField.getText());
        } else if (e.getSource() == backButton) {
        	resetToDefaultState();
        }
    }

    public static void main(String[] args) {
        new accountGUI(); // Create and show the Transaction GUI
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // Check if the depositButton should be enabled when in deposit mode
        if (depositButton.getText().equals("Confirm")) {
            transaction = transactionField.getText().trim();
            recipient = depositField.getText().trim();

            if (transactionLabel.getText().equals("How much would you like to deposit?")) {
                // Check if the input is a valid number with up to two decimal places
                if (transaction.matches("^\\d+(\\.\\d{1,2})?$")) {
                    depositButton.setEnabled(true);  // Enable button if valid
                } else {
                    depositButton.setEnabled(false); // Disable button if invalid
                }
            } else if (transactionLabel.getText().equals("How much would you like to transfer?")) {
                // Ensure both fields (amount and recipient) are filled in
                if (!transaction.isEmpty() && !recipient.isEmpty() &&
                        transaction.matches("^\\d+(\\.\\d{1,2})?$")) {
                    depositButton.setEnabled(true);  // Enable if both fields are filled and valid
                } else {
                    depositButton.setEnabled(false); // Disable otherwise
                }
            }
        }
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}