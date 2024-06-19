package bank.model;

public class Register {

	
	private int RegNO;
	private String Name;
	private String username;
	private String password;
	private float balance;
	public int getRegNO() {
		return RegNO;
	}
	public void setRegNO(int regNO) {
		RegNO = regNO;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public Register(int regNO, String name, String username, String password,float balance) {
		super();
		RegNO = regNO;
		Name = name;
		this.username = username;
		this.password = password;
		this.balance = balance;
	}
	
	
}