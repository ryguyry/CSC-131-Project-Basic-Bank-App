import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IouGUI implements ActionListener {

    private JLabel label;
    private JButton requestButton;
    private JButton manageButton;
    private JFrame frame;
    private JPanel panel;
    private JTextField amountField;
    private JTextField creditorField;
    private JLabel amountLabel;
    private JLabel creditorLabel;

    public IouGUI() {
        frame = new JFrame();
        panel = new JPanel();
        
        //BoxLayout for vertical alignment
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Padding around the panel

        //here is the label at the top of the window
        label = new JLabel("IOU Manager");
        label.setFont(new Font("Cornerstone", Font.BOLD, 24)); //set font size to 24
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT); //center the label

        //buttons and their labels
        requestButton = new JButton("Request Money with IOU");
        manageButton = new JButton("Manage Existing IOU");

        //resize buttons
        requestButton.setPreferredSize(new Dimension(200, 40));
        manageButton.setPreferredSize(new Dimension(220, 60));

        //align the buttons
        requestButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        manageButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        //these check if a button has been pressed
        requestButton.addActionListener(this);
        manageButton.addActionListener(this);

        //components to the window panel
        panel.add(label); //the label first if its on top
        panel.add(Box.createVerticalStrut(50)); //vertical space between label and buttons
        panel.add(requestButton); //the request button takes us to a request IOU UI scenario
        panel.add(Box.createVerticalStrut(15)); //vertical space between buttons
        panel.add(manageButton); //the manage button takes us to a IOU management UI scenario

        // Set up the window frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Basic Banking");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new IouGUI();
    }

    //create actions for when the buttons are pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        //if the request button is pressed
        if (e.getSource() == requestButton) {
            label.setText("Request Money");
            requestButton.setText("Send Request");
            requestButton.setEnabled(false); //disable the button until the required fields are filled out
            manageButton.setText("Back");
           // Add text fields and labels for "Amount" and "Creditor"
           if (amountField == null && creditorField == null) {
               // Create the text fields and labels
               amountLabel = new JLabel("Amount:");
               creditorLabel = new JLabel("Creditor:");
               amountField = new JTextField(15);
               creditorField = new JTextField(15);  
               amountLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);//set alignment for the new components
               amountField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
               creditorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
               creditorField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
               panel.add(amountLabel, panel.getComponentCount() - 2);//put the amount label bellow the main label
               panel.add(amountField, panel.getComponentCount() - 2);//put the text box bellow it
               panel.add(Box.createVerticalStrut(10));  //space between Amount and Creditor
               panel.add(creditorLabel, panel.getComponentCount() - 2);//creditor label
               panel.add(creditorField, panel.getComponentCount() - 2);//text box bellow it             
               panel.revalidate(); //refresh the panel
               panel.repaint();
            }
        }
        //if the manage button is pressed    
        if (e.getSource() == manageButton) {
            //check if we are in a Request Money scenerio first
            if (manageButton.getText().equals("Back")) {
                requestButton.setText("Request Money with IOU");//reset request button
                requestButton.setEnabled(true);//enable the button
                manageButton.setText("Manage Existing IOU");//reset manage button
                label.setText("IOU Manager");
                 //remove the text fields and labels if they were added
                if (amountField != null && creditorField != null) {
                    panel.remove(amountLabel);
                    panel.remove(amountField);
                    panel.remove(creditorLabel);
                    panel.remove(creditorField);
                    amountField = null;//set the text fields to null
                    creditorField = null;
                    panel.revalidate();
                    panel.repaint();//refresh the layout
                }
            }
            else {
            label.setText("Current IOU:"); //set the label to the IOU management scenario
            }
        }
     }
}