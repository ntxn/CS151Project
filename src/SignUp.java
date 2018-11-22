import java.awt.Color;
import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignUp extends JPanel{
	private Hashtable guests;
	private JLabel nameErrorLabel;
	private JLabel usernameErrorLabel;
	private JLabel passwordErrorLabel;
	private JLabel existingAccountErrorLabel;
	private JTextField nameTextField;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	
	public SignUp(Hashtable g){
		guests = g;
		
		JLabel label = new JLabel("SIGN UP");
		
		JLabel nameLabel = new JLabel("Full Name");
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		nameErrorLabel = new JLabel("");
		usernameErrorLabel = new JLabel("");
		passwordErrorLabel = new JLabel("");
		existingAccountErrorLabel = new JLabel("");
		nameTextField =  new JTextField();
		usernameTextField =  new JTextField();
		passwordTextField = new JTextField();
		
		Dimension dimension = new Dimension(110, 30);
		
		nameTextField.setPreferredSize(dimension);
		usernameTextField.setPreferredSize(dimension);
		passwordTextField.setPreferredSize(dimension);
		
		
		existingAccountErrorLabel.setForeground(Color.RED);
		usernameErrorLabel.setForeground(Color.RED);
		passwordErrorLabel.setForeground(Color.RED);
		nameErrorLabel.setForeground(Color.RED);
		
		add(label);
		add(nameLabel);
		add(nameTextField);
		add(nameErrorLabel);
		add(usernameLabel);
		
		add(usernameTextField);
		add(usernameErrorLabel);
		add(passwordLabel);
		add(passwordTextField);
		add(passwordErrorLabel);
		add(existingAccountErrorLabel);
		
		
	}
	
	public Guest createAccount(){
		existingAccountErrorLabel.setText("");
		usernameErrorLabel.setText("");
		passwordErrorLabel.setText("");
		
		String name = nameTextField.getText().trim();
		String username = usernameTextField.getText().trim();
		String password = passwordTextField.getText().trim();
		
		
		if(name.equals("") || username.equals("") || password.equals("")){
			existingAccountErrorLabel.setText("Name, username or password cannot be empty");
			return null;
		}
		
		Guest guest = (Guest)guests.get(username);
		
		if(guest != null){
			usernameErrorLabel.setText("This username already exists");
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
