package util;

public class LoginUtil {
	private static String loginID = "kms";
	private static boolean isLoginState = true;
	
	private LoginUtil() {}
	
	public static String getLoginID() {
		return loginID;
	}
	
	public static boolean isLoginState() {
		return isLoginState;
	}
	
	public static void setLoginID(String loginID) {
		LoginUtil.loginID = loginID;
	}
	
	public static void setLoginState(boolean isLoginState) {
		LoginUtil.isLoginState = isLoginState;
	}
}
