package processor;

import controler.ServerCommunicator;
import enumeration.Header;
import exception.ServerConnectionException;

public class JoinProcessor {
	private final ServerCommunicator COMMUNICATOR;
	public JoinProcessor(ServerCommunicator communicator) {
		COMMUNICATOR = communicator;
	}
	
	public void join(String id, String pw, String name) throws ServerConnectionException {
		COMMUNICATOR.sendData(Header.JOIN, new String[]{id, pw, name});
	}
}
