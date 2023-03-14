/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe principale du serveur FTP.
 * Cette classe instancie le serveur FTP sur le port 2121 et attend les connexions des clients.
 * Elle gère l'authentification et l'exécution des commandes envoyées par les clients.
 *
 * @author Ziad Lahrouni.      
 * @author Hanane Erraji.
 * 
 * @see <a href="https://ziadlahrouni.com">https://ziadlahrouni.com</a>
 * @see <a href="http://hananeerraji.info/">http://hananeerraji.info/</a>
 */
public class Main {
	

	/**
	 * Méthode principale qui démarre le serveur FTP, accepte les connexions entrantes, 
	 * traite les commandes reçues, et ferme les connexions à la fin.
	 * 
	 * @param args Les arguments de la ligne de commande (non utilisés).
	 * @throws Exception En cas d'erreur lors de l'exécution.
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Le Serveur FTP");
		
		ServerSocket serveurFTP = new ServerSocket(2121);
		Socket socket = serveurFTP.accept();
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream ps = new PrintStream(socket.getOutputStream());
		InformationClient client = new InformationClient(socket,".");
		
		
		
		ps.println("1 Bienvenue ! ");
		ps.println("1 Serveur FTP Personnel.");
		ps.println("0 Authentification : ");
		
		String commande = "";
		
		// Attente de reception de commandes et leur execution
		while(!(commande=br.readLine()).equals("bye")) {
			System.out.println(">> "+commande);
			CommandExecutor.executeCommande(ps, commande,client);
		}
		System.out.println("Client quit");
		serveurFTP.close();
		socket.close();
	}

}
