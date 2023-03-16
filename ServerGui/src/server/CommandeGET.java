package server;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Cette classe représente une commande "get" qui permet de récupérer le contenu d'un fichier.
 * Cette commande hérite de la classe Commande et implémente la méthode execute().
 * 
 * @author Ziad Lahrouni
 */
public class CommandeGET extends Commande {
	private final TextArea logArea;
	
	public void log(String message) {
        Platform.runLater(() -> {
            String logMessage = message + "\n";
            
            logArea.appendText(logMessage);
        });
    }
	/**
	 * Constructeur de la classe CommandeGET.
	 * 
	 * @param ps          Le flux de sortie où les résultats de la commande doivent
	 *                    être imprimés.
	 * @param commandeStr La chaîne de caractères qui représente la commande "get".
	 */
	public CommandeGET(PrintStream ps, String commandeStr, InformationClient cl, CommandExecutor ce, TextArea logArea) {
		super(ps, commandeStr, cl, ce);
		this.logArea = logArea;
	}

	/**
	 * Cette méthode exécute la commande "get" en affichant le contenu du fichier spécifié
	 * dans les arguments de la commande dans le flux de sortie spécifié dans le constructeur.
	 */
	public void execute() {
		if(this.commandeArgs.length != 0) {
			File fichier = new File(this.cl.workingdir +File.separator+this.commandeArgs[0]);
				
				try {
					
					FileInputStream in = new FileInputStream(fichier);
					BufferedInputStream bis = new BufferedInputStream(in);
					
					// Créer une nouvelle socket pour envoyer le contenu du fichier
			        @SuppressWarnings("resource")
					ServerSocket serverSocket = new ServerSocket(0); // Créer une nouvelle socket avec un port aléatoire
			        int port = serverSocket.getLocalPort(); // Récupérer le port de la socket
			        
					// Envoyer le port au client
			        ps.println("1 Reading in [" + port+"]");

			        Socket clientSocket = serverSocket.accept(); // Attendre la connexion du client
			        long fileSize = fichier.length();
			        long totalBytesSent = 0;
			        int count;
			        int percentage=0;
			        // Lire le contenu du fichier et l'écrire dans la Socket du client
			        OutputStream out = clientSocket.getOutputStream();
			        
			        if(fileSize > 0) {
			        	byte[] buffer = new byte[8192]; // 8 Ko
				        log("Start transfering the file " + fichier.getName() + " for the client ["+cl.getHost()+", "+cl.getPort()+"]\n");
				        while ((count = bis.read(buffer)) != -1) {
				            out.write(buffer, 0, count);
				            totalBytesSent += count;
				            percentage = (int) ((totalBytesSent * 100) / fileSize);
				            log("Sent " + percentage + "% of the file.");
				            ps.println("1 sent " + percentage +"% of the file");
				        }

				        if(percentage == 100) {
				        	log("The file is transfered Completely");
				        	ps.println("0 File download finished successfully.");
				        } else {
				        	log("The file is not tranfered completely");
				        	
				        }
			        } else {
			        	ps.println("2 The file is empty");
			        }
			        clientSocket.close();
			        out.close();
			        bis.close();
			        
				} catch ( IOException e) {
					if(e instanceof FileNotFoundException) {
						ps.println("2 File does not exist.");
						return;
					} else {
						ps.println("2 Unable to complete command due to error.");
						return;
					}
				}

			
		} else {
			ps.println("2 USAGE : GET [file]");
			return;
		}
		
	}

}
