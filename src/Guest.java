/**
 * Holding guest's information
 * @author Ngan Nguyen
 *
 */
public class Guest {
	String name;
	String username;
	String password;
	
	/**
	 * Constructor to create a new Guest object
	 * @param name
	 * @param username
	 * @param password
	 */
	public Guest(String name, String username, String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Get guest's name
	 * @return guest's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * set Guest's name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get guest's username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Set username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Get password
	 * @return	password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * compare this guest info to another guest
	 * to see if there's the sanme
	 * @param g
	 * @return true/false
	 */
	public boolean equal(Guest g){
		if(name.equals(g.getName()) && username.equals(g.getUsername())
				&& password.equals(g.getPassword()))
				return true;
		return false;
	}
	
	/**
	 * Convert Guest's info to a String
	 */
	public String toString(){
		return name + " | UserName: " + username + " | Password: " + password;
	}
}
