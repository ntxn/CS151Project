import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignIn extends JPanel{
	private Hashtable guests;
	private JLabel usernameErrorLabel;
	private JLabel passwordErrorLabel;
	private JLabel existingAccountErrorLabel;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	
	public SignIn(Hashtable g){
		guests = g;
		
		JLabel label = new JLabel("SIGN IN");
		JLabel usernameLabel = new JLabel("Username");
		JLabel passwordLabel = new JLabel("Password");
		usernameErrorLabel = new JLabel("");
		passwordErrorLabel = new JLabel("");
		existingAccountErrorLabel = new JLabel("");
		usernameTextField =  new JTextField();
		passwordTextField = new JTextField();
		
		usernameTextField.setPreferredSize(new Dimension(110, 30));
		passwordTextField.setPreferredSize(new Dimension(110, 30));
		
		existingAccountErrorLabel.setForeground(Color.RED);
		usernameErrorLabel.setForeground(Color.RED);
		passwordErrorLabel.setForeground(Color.RED);
		
		add(label);
		add(usernameLabel);
		add(usernameTextField);
		add(usernameErrorLabel);
		add(passwordLabel);
		add(passwordTextField);
		add(passwordErrorLabel);
		add(existingAccountErrorLabel);
		
		
	}
	
	public Guest verifyAccount(){
		// Reset labels
		usernameErrorLabel.setText("");
		passwordErrorLabel.setText("");
		existingAccountErrorLabel.setText("");
		
		String username = usernameTextField.getText().trim();
		String password = passwordTextField.getText().trim();
		
		if(username.equals("") || password.equals("")){
			existingAccountErrorLabel.setText("username & password cannot be empty");
			return null;
		}
			
		Guest guest = (Guest)guests.get(username);
		
		if(guest == null){
			usernameErrorLabel.setText("This username does not exist");
			return null;
		}
		if(!guest.getPassword().equals(passwordTextField.getText().trim())){
			passwordErrorLabel.setText("Incorrect Password");
			return null;
		}
		return guest;
	}
}
