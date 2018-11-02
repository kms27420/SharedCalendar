package controler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import enumeration.Header;
import exception.ServerConnectionException;
import listener.view.login.LogoutListener;

public class ServerCommunicator {
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	private ReceiveDataControler RECEIVE_DATA_CONTROLER;
	private LogoutListener logoutListener;
	
	public ServerCommunicator(LogoutListener logoutListener, ReceiveDataControler receiveDataControler) {
		this.logoutListener = logoutListener;
		RECEIVE_DATA_CONTROLER = receiveDataControler;
	}
	
	public void readyToCommunication() throws ServerConnectionException {
		if(socket==null) {
			try {
				socket = new Socket("130.211.216.222", 10000);
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				new Thread(new Receiver()).start();
			} catch(IOException e) {
				disconnect();
				throw new ServerConnectionException();	
			}
		}
	}
	
	public void disconnect() {
		try {
			if(input!=null)	input.close();
			if(output!=null)	output.close();
			if(socket!=null)	socket.close();
		} catch(IOException e) {}
		output = null;
		input = null;
		socket = null;
	}
	
	public synchronized void sendData(Header header, String[] data) throws ServerConnectionException {
		try {
			String[] send = new String[data.length+1];
			send[0] = header.name();
			for(int i=0; i<data.length; i++)	send[i+1] = data[i];
			output.writeObject(send);
			output.flush();
			send = null;
		} catch(IOException e) {	throw new ServerConnectionException();	}
	}
	
	private class Receiver implements Runnable {
		@Override
		public void run() {
			try {
				Object[] receive = null;
				while(true) {
					receive = (Object[])input.readObject();
					if(RECEIVE_DATA_CONTROLER!=null)	RECEIVE_DATA_CONTROLER.onReceiveData(receive);
				}
			} catch(Exception e) {
				e.printStackTrace();
				logoutListener.onLogout();
			}
		}
	}
}
