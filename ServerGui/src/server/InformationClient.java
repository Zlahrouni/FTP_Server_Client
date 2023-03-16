package server;

import java.net.Socket;

public class InformationClient {
	String workingdir;
	Socket mySock;
	private String username;
	public boolean root;
	public String host;
	public int Port;
	
	
	public InformationClient(Socket sock, String wd) {
		this.mySock =sock;
		this.workingdir=wd;
		this.setUsername("");
		this.root = true;

	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public boolean isRoot() {
		return root;
	}


	public void setRoot(boolean root) {
		this.root = root;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public int getPort() {
		return Port;
	}


	public void setPort(int port) {
		Port = port;
	}

	


	
	
}
