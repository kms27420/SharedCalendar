package processor;

import controler.ServerCommunicator;
import data.Login;
import enumeration.Header;
import exception.ServerConnectionException;

public class LoginProcessor {
	private static boolean isLogin;
	private final ServerCommunicator COMMUNICATOR;
	
	public LoginProcessor(ServerCommunicator communicator) {
		this.COMMUNICATOR = communicator;
	}
	/**
	 * Process login with input id and password.
	 * @param info You want to sign in.
	 * @throws ServerConnectionException An Exception that occurs when the connection with the server is unstable.
	 */
	public void login(Login info) throws ServerConnectionException {
		COMMUNICATOR.sendData(Header.LOGIN, new String[]{info.getID(), info.getPW()});
	}
	
	public void loginSuccess() {
		isLogin = true;
	}
	/**
	 * Process logout.
	 */
	public void logout() {
		COMMUNICATOR.disconnect();
		isLogin = false;
	}
	/**
	 * Get current login status.
	 * @return Whether or not you are currently logged in.
	 */
	public static boolean isLogin() {
		return isLogin;
	}
}
