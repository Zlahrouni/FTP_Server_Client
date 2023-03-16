
import java.net.Socket;

public class InformationClient {
	String workingdir;
	Socket mySock;
	
	public InformationClient(Socket sock, String wd) {
		this.mySock =sock;
		this.workingdir=wd;
	}
}
