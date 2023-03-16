import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client FTP qui permet de se connecter à un serveur FTP et d'exécuter des
 * commandes comme "ls", "pwd", "cd", "get" et "bye". Les résultats des
 * commandes sont affichés sur la console et les fichiers téléchargés sont
 * enregistrés localement.
 * 
 * @author Ziad Lahrouni
 * @author Hanane Erraji
 * 
 * @see <a href="https://ziadlahrouni.com">https://ziadlahrouni.com</a>
 * @see <a href="http://hananeerraji.info/">http://hananeerraji.info/</a>
 */
public class Main {
	

	public static void main(String[] args) {
		Client cl = new Client();
		cl.userOk = cl.passOk = false;
		System.out.println("Client FTP");
		String serverName = "localhost";
		int port = 2121;

		try {
			Socket socket = new Socket(serverName, port);

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream ps = new PrintStream(socket.getOutputStream());
			cl.affichage(br);

			Scanner scanner = new Scanner(System.in);


				while (!cl.userOk) {
					System.out.print("Enter username: ");
					String username = scanner.nextLine();
					cl.commande(br, ps, "user " + username);
				}
			


				while (!cl.passOk) {
					System.out.print("Enter password: ");
					String password = scanner.nextLine();
					cl.commande(br, ps, "pass " + password);
				}
			

			while (true) {
				System.out.print("$ ");
			    String commande = scanner.nextLine();
			    if (commande.startsWith("bye")) {
			        break;
			    }
			    cl.commande(br, ps, commande);

			}
			cl.commande(br, ps, "bye");
			scanner.close();
			// ps.flush();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
