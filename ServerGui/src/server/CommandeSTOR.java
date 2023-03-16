package server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class CommandeSTOR extends Commande {
	private final TextArea logArea;
	
	public void log(String message) {
        Platform.runLater(() -> {
            String logMessage = message + "\n";
            
            logArea.appendText(logMessage);
        });
    }
	
	public CommandeSTOR(PrintStream ps, String commandeStr, InformationClient cl, CommandExecutor ce, TextArea logArea) {
		super(ps, commandeStr, cl, ce);
		this.logArea = logArea;
	}

	public void execute() {
		if(this.commandeArgs.length != 0) {
			File fichier = new File(this.cl.workingdir +File.separator+this.commandeArgs[0]);

				try {
				
					
					FileOutputStream out = new FileOutputStream(fichier);
					BufferedOutputStream bos = new BufferedOutputStream(out);
					
					// Créer une nouvelle socket pour envoyer le contenu du fichier
			        @SuppressWarnings("resource")
					ServerSocket serverSocket = new ServerSocket(0); // Créer une nouvelle socket avec un port aléatoire
			        int port = serverSocket.getLocalPort(); // Récupérer le port de la socket
			        
					// Envoyer le port au client
			        ps.println("1 Reading in [" + port+"]");

			        Socket clientSocket = serverSocket.accept(); // Attendre la connexion du client
			        
			        // Lire le contenu du fichier et l'écrire dans la Socket du client
			        InputStream in = clientSocket.getInputStream();
			        byte[] buffer = new byte[1024];
			        
			        int count;
			        while ((count = in.read(buffer)) != -1) {
			            bos.write(buffer, 0, count);
			        }
			        clientSocket.close();
			        in.close();
			        ps.println("0 File download finished successfully.");

			        bos.close();
			        
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
