package data;

import java.util.HashMap;
import java.util.Map;

public class Login {
	private final Map<LoginAttributes, String> DATA = new HashMap<>();
	
	public Login() {}
	
	public Login(String id, String pw) {
		setID(id);
		setPW(pw);
	}
	
	public Login(String id, String pw, String name) {
		this(id, pw);
		setName(name);
	}
	
	public void setID(String id) {
		DATA.put(LoginAttributes.ID, id);
	}
	
	public void setPW(String pw) {
		DATA.put(LoginAttributes.PW, pw);
	}
	
	public void setName(String name) {
		DATA.put(LoginAttributes.NAME, name);
	}
	
	public String getID() {
		return DATA.get(LoginAttributes.ID);
	}
	
	public String getPW() {
		return DATA.get(LoginAttributes.PW);
	}
	
	public String getName() {
		return DATA.get(LoginAttributes.NAME);
	}
	
	public static enum LoginAttributes {
		ID, PW, NAME;
	}
}
