package listener.login;

public interface LoginListener {
	public void onLogin(String id, String pw);
	public void onLogout();
	public void onLoginSuccess();
}
