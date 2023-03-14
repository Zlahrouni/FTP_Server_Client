import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeSTOR extends Commande {
	
	public CommandeSTOR(PrintStream ps, String commandeStr, InformationClient cl) {
		super(ps, commandeStr, cl);
	}

	public void execute() {
		if(this.commandeArgs.length != 0) {
			File fichier = new File(this.commandeArgs[0]);

				try {
					FileInputStream in = new FileInputStream(System.getProperty("user.dir") +"\\"+fichier);
					BufferedInputStream bis = new BufferedInputStream(in);
					
					// Créer une nouvelle socket pour envoyer le contenu du fichier
			        @SuppressWarnings("resource")
					ServerSocket serverSocket = new ServerSocket(0); // Créer une nouvelle socket avec un port aléatoire
			        int port = serverSocket.getLocalPort(); // Récupérer le port de la socket
			        
					// Envoyer le port au client
			        ps.println("1 Reading in [" + port+"]");

			        Socket clientSocket = serverSocket.accept(); // Attendre la connexion du client

			        // Lire le contenu du fichier et l'écrire dans la Socket du client
			        OutputStream out = clientSocket.getOutputStream();
			        byte[] buffer = new byte[1024];
			        int count;
			        while ((count = bis.read(buffer)) != -1) {
			            out.write(buffer, 0, count);
			        }
			        clientSocket.close();
			        out.close();
			        ps.println("0 File download finished successfully.");
			        System.out.println("0 end");
			        bis.close();
			        
				} catch ( IOException e) {
					if(e instanceof FileNotFoundException) {
						ps.println("2 File does not exist.");
					} else {
						ps.println("2 Unable to complete command due to error.");
					}
				}

			
		} else {
			ps.println("2 USAGE : GET [file]");
		}

	}

}
