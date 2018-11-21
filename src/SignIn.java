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
		usernameErrorLabel = new JLabel("Username");
		passwordErrorLabel = new JLabel("Password");
		existingAccountErrorLabel = new JLabel("Password");
		usernameTextField =  new JTextField();
		passwordTextField = new JTextField();
		
		usernameTextField.setPreferredSize(new Dimension(40, 30));
		passwordTextField.setPreferredSize(new Dimension(40, 30));
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
		Guest guest = (Guest)guests.get(usernameTextField.getText().trim());
		if(guest.getPassword().equals(passwordTextField.getText().trim()))
			return guest;
		return null;
	}
}
