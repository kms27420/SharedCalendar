package processor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import data.Login;

public class AutoLoginInfoGetter {
	private static final String AUTO_LOGIN_FILE_PATH = "bin/internal/AutoLogin.txt";
	
	public AutoLoginInfoGetter() {}
	
	/**
	 * Check the current automatic login information.
	 * @return Whether it is currently set to auto-login.
	 */
	public boolean isAutoLogin() {
		try {
			File f = new File(AUTO_LOGIN_FILE_PATH);
			FileReader r = new FileReader(f);
			char[] c = new char[1];
			r.read(c);
			r.close();
			return c[0]=='y' || c[0]=='Y';
		} catch (IOException e) {return false;}
	}
	/**
	 * Get a Login instance with id and password information for automatic login.
	 * @return A Login instance with id and password information.
	 * @throws IOException Exception raised when attempting to get automatic login information fails.
	 */
	public Login getLoginInfo() throws IOException {
		Scanner sc = new Scanner(new File(AUTO_LOGIN_FILE_PATH));
		sc.nextLine();
		Login rv = new Login(sc.nextLine(), sc.nextLine());
		sc.close();
		return rv;
	}
}
