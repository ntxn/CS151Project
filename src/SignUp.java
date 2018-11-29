import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Ngan Nguyen
 *
 */
public class SignUp extends JPanel{
	private Hashtable guests;
	private JLabel warningLabel;
	private JTextField nameTextField;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	
	public SignUp(Hashtable g){
		setLayout(new GridBagLayout());
		
	    // GRIDBAG CONSTRAINT
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
		guests = g;
		
		
		JLabel nameLabel = new JLabel("Full Name");
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		warningLabel = new JLabel("");
		nameTextField =  new JTextField();
		usernameTextField =  new JTextField();
		passwordTextField = new JTextField();
		
		Dimension dimension = new Dimension(125, 30);
		
		nameTextField.setPreferredSize(dimension);
		usernameTextField.setPreferredSize(dimension);
		passwordTextField.setPreferredSize(dimension);
		
		
		warningLabel.setForeground(Color.RED);
		
		JPanel namePanel = new JPanel();
		JPanel usernamePanel = new JPanel();
		JPanel passwordPanel = new JPanel();
		JPanel errorPanel = new JPanel();
		
		namePanel.add(nameLabel);
		namePanel.add(nameTextField);
		
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTextField);
		
		errorPanel.add(warningLabel);
		
		add(namePanel, gbc);
		add(usernamePanel, gbc);
		add(passwordPanel, gbc);
		add(warningLabel, gbc);
		
	}
	
	public Guest createAccount(){
		warningLabel.setText("");
		
		String name = nameTextField.getText().trim();
		String username = usernameTextField.getText().trim();
		String password = passwordTextField.getText().trim();
		
		
		if(name.equals("") || username.equals("") || password.equals("")){
			warningLabel.setText("Name, username or password cannot be empty");
			return null;
		}
		
		Guest guest = (Guest)guests.get(username);
		
		if(guest != null){
			warningLabel.setText("This username already exists");
			return null;
		}
		
		guest = new Guest(nameTextField.getText().trim(), 
						  username,
						  passwordTextField.getText().trim());
		guests.put(username, guest);
		
		// Reset TextField
		nameTextField.setText("");
		usernameTextField.setText("");
		passwordTextField.setText("");
						  
		return guest;
	}
}
