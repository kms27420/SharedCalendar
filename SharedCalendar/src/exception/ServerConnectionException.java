package exception;

public class ServerConnectionException extends Exception{
	public ServerConnectionException() {}
	public ServerConnectionException(String msg) {
		super(msg);
	}
}
