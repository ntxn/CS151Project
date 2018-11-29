import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Ngan Nguyen
 *
 */
public class SignIn extends JPanel{
	private Hashtable guests;
	private JLabel warningLabel;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	
	public SignIn(Hashtable g){
		setLayout(new GridBagLayout());
		
	    // GRIDBAG CONSTRAINT
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
		guests = g;
		
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		
		
		usernameTextField =  new JTextField();
		passwordTextField = new JTextField();
		usernameTextField.setPreferredSize(new Dimension(125, 30));
		passwordTextField.setPreferredSize(new Dimension(125, 30));
		
		warningLabel = new JLabel("");
		warningLabel.setForeground(Color.RED);
		
		JPanel usernamePanel = new JPanel();
		JPanel passwordPanel = new JPanel();
		JPanel errorPanel = new JPanel();
		
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTextField);
		
		errorPanel.add(warningLabel);
		
		add(usernamePanel, gbc);
		add(passwordPanel, gbc);
		add(warningLabel, gbc);
	}
	
	public Guest verifyAccount(){
		// Reset labels
		warningLabel.setText("");
		
		String username = usernameTextField.getText().trim();
		String password = passwordTextField.getText().trim();
		
		if(username.equals("") || password.equals("")){
			warningLabel.setText("username & password cannot be empty");
			return null;
		}
			
		Guest guest = (Guest)guests.get(username);
		
		if(guest == null){
			warningLabel.setText("This username does not exist");
			return null;
		}
		if(!guest.getPassword().equals(passwordTextField.getText().trim())){
			warningLabel.setText("Incorrect Password");
			return null;
		}
		
		// Reset TextField
		usernameTextField.setText("");
		passwordTextField.setText("");
		
		return guest;
	}
	
	
}
