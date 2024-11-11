//this file will handle the login step for the Basic Bank App
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginGUI implements ActionListener, KeyListener {
	//here are our variables for the GUI
    private JLabel label;
    private JButton loginButton;
    private JButton createButton;
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField passwordConfirmField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel passwordConfirmLabel;
    private JLabel passwordMatchLabel;
    private static String username;
    int userId;
    //private String password;

    public LoginGUI() {//here is the constructor for our layout
        frame = new JFrame();
        panel = new JPanel();       
        //boxLayout for vertical alignment
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 50)); // Padding around the panel        
        //label at the top
        label = new JLabel("Basic Banking");
        //initialize and add labels and fields for account creation
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        passwordConfirmLabel = new JLabel("Confirm Password:");
        passwordMatchLabel = new JLabel("Passwords do not match");
        usernameField = new JTextField(15);
        passwordField = new JTextField(15);         // Initialize password field
        passwordConfirmField = new JTextField(15);  // Initialize password confirm field      
        passwordMatchLabel.setVisible(false); //hide initially
        passwordConfirmField.setVisible(false);
        label.setFont(new Font("Arial", Font.BOLD, 24)); // set font size to 24
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT); // center the label
        //buttons and their labels
        loginButton = new JButton("Login");
        createButton = new JButton("Create Account");
        //resize buttons
        loginButton.setPreferredSize(new Dimension(400, 50));
        createButton.setPreferredSize(new Dimension(400, 50));
        //align the buttons
        loginButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        createButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        // Center-align and add the components
        usernameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        usernameField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        passwordConfirmLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        passwordConfirmField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
        passwordMatchLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        //action listeners check for buttons being pressed
        loginButton.addActionListener(this);
        createButton.addActionListener(this);
        usernameField.addKeyListener(this);
        passwordField.addKeyListener(this); // Add KeyListener if needed
        passwordConfirmField.addKeyListener(this);
        //components are added to the window panel
        panel.add(label); // Add the label first
        panel.add(Box.createVerticalStrut(20));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordConfirmLabel);
        panel.add(passwordConfirmField);
        panel.add(passwordMatchLabel);
        passwordConfirmField.setVisible(false);
        passwordConfirmLabel.setVisible(false);
        passwordMatchLabel.setVisible(false);
        panel.add(Box.createVerticalStrut(50)); // Vertical space between label and buttons
        panel.add(loginButton); // Add login button
        loginButton.setEnabled(false);
        panel.add(Box.createVerticalStrut(15)); // Vertical space between buttons
        panel.add(createButton); // Add create button
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
    
    public static boolean checkUsername(String username) { //we could package this a class of its own
        String query = "SELECT 1 FROM users WHERE username = ?";
        try (Connection connection = bbaDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);  // Set the user name parameter in the query
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();  // If a result is returned, the user name exists
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while checking the username: " + e.getMessage());
        }
        return false;
    }
        //THE FOLLOWING IS FOR TESTING PURPOSES:
	public static void main(String[] args) {
		//generating the outline
		new LoginGUI();
		username = "test";
		new IouGUI(username);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
        //enable "Enter" only when all fields are filled and passwords match
		if (loginButton.getText().equals("Enter")) {
			if (!usernameField.getText().trim().isEmpty() && 
				!passwordField.getText().trim().isEmpty() &&
				!passwordConfirmField.getText().trim().isEmpty() &&
				passwordField.getText().equals(passwordConfirmField.getText())) {
					loginButton.setEnabled(true);
			} else {
				loginButton.setEnabled(false);
			}
			if (!passwordField.getText().trim().isEmpty() &&
				!passwordConfirmField.getText().trim().isEmpty() &&
				!passwordField.getText().equals(passwordConfirmField.getText())) {      	    
					passwordMatchLabel.setVisible(true);    
        		} else { //set label to only be visible when passwords don't match
        			passwordMatchLabel.setVisible(false);
        	}
		}
		else if (loginButton.getText().equals("Login")){
			if (!usernameField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()){
				loginButton.setEnabled(true);
			} else {
				loginButton.setEnabled(false);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            if (loginButton.getText().equals("Login")) {
            	username = usernameField.getText().trim();
                //password = passwordField.getText();
                if (checkUsername(username)) {
                    System.out.println("Login successful!");
                    passwordConfirmLabel.setVisible(true);
                    passwordConfirmLabel.setText("Login Sucessful! What do you want to do today?");
                    loginButton.setText("Deposit / Pay IOU");
                    createButton.setText("Manage IOU");
                } else {
                    System.out.println("Login failed");
                    passwordConfirmLabel.setVisible(true);
                    passwordConfirmLabel.setText("Username not found");
                }
            } else if (loginButton.getText().equals("Enter")) { // Create Account logic
                username = usernameField.getText().trim();
                //password = passwordField.getText().trim();
                label.setText("Account Created:");
                loginButton.setText("Login");  // Set the button text to "Login"
                loginButton.setVisible(false); // Make the button invisible
                passwordLabel.setVisible(false);
                createButton.setText("Back");
                if (UserRegistration.addUser(username)) {
                    System.out.println("User '" + username + "' added successfully!");
                    usernameLabel.setText(username + " your account has successfully been created");
                } else {
                    System.out.println("Failed to add user '" + username + "'.");
                }                           
                // Remove text fields since we're showing confirmed values
                usernameField.setVisible(false);
                passwordField.setVisible(false);
                passwordConfirmField.setVisible(false);
                passwordConfirmLabel.setVisible(false);
                
                // Clear the fields
                usernameField.setText(""); 
                passwordField.setText(""); 
                passwordConfirmField.setText(""); 
            } else if (loginButton.getText().equals("Deposit / Pay IOU")) {
            	new accountGUI();
            }
            
        }
        if (e.getSource() == createButton) {
    		if (createButton.getText().equals("Create Account")) {
            	loginButton.setEnabled(false); //here we have disabled the login (enter) button
                passwordConfirmField.setVisible(true); //show the password confirm field and labels
                passwordConfirmLabel.setVisible(true);
                //change the text of the buttons
                loginButton.setText("Enter");
                createButton.setText("Back");              
            	}
        	else if (createButton.getText().equals("Back")) { //back button for the create account
        		if (loginButton.getText().equals("Enter")) {
        			//go back to the initial state       	
        			label.setText("Basic Banking");
        			usernameLabel.setText("Username");
        			passwordLabel.setText("Password");
        			loginButton.setText("Login");
        			createButton.setText("Create Account");
        	        usernameField.setText(""); // Clear the user name field
        	        passwordField.setText(""); // Clear the password field
        	        passwordConfirmField.setText(""); // Clear the password confirm field
                    passwordConfirmField.setVisible(false); //show the password confirm field and labels
                    passwordConfirmLabel.setVisible(false);
                    passwordMatchLabel.setVisible(false);
        		} else if (createButton.getText().equals("Back")) {
        			new IouGUI(username);
        		}
        		else {
        			//go back to the initial state       	
        			label.setText("Basic Banking");
                    usernameLabel.setText("Username"); 
                    passwordLabel.setText("Password");
                    loginButton.setText("Login");
                    loginButton.setVisible(true);
                    createButton.setText("Create Account");
                    usernameLabel.setVisible(true);
                    usernameField.setVisible(true);
                    passwordLabel.setVisible(true);
                    passwordField.setVisible(true);
                    
        		}
        	}
        }
	}
}
