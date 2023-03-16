package server;
import java.io.PrintStream;

import javafx.scene.control.TextArea;

public class CommandExecutor {
	
	public boolean userOk = false ;
	public boolean pwOk = false ;
	
	public void executeCommande(PrintStream ps, String commande, InformationClient cl, CommandExecutor ce, TextArea logArea) {
		if(userOk && pwOk) {
			// Changer de repertoire. Un (..) permet de revenir au repertoire superieur
			if(commande.split(" ")[0].equals("cd")) {
				(new CommandeCD(ps, commande, cl, ce)).execute();
			}
			// Telecharger un fichier
			else if(commande.split(" ")[0].equals("get")) {
				(new CommandeGET(ps, commande, cl, ce, logArea)).execute();
			}
			// Afficher la liste des fichiers et des dossiers du repertoire courant
			else if(commande.split(" ")[0].equals("ls")) {
				(new CommandeLS(ps, commande, cl, ce)).execute();
			}
			// Afficher le repertoire courant
			else if(commande.split(" ")[0].equals("pwd")) {
				(new CommandePWD(ps, commande, cl, ce)).execute();
			}	 
			// Envoyer (uploader) un fichier
			else if(commande.split(" ")[0].equals("stor")) {
				(new CommandeSTOR(ps, commande, cl, ce, logArea)).execute();
			}
			// Creer un dossier
			else if(commande.split(" ")[0].equals("mkdir")) {
				(new CommandeMKDIR(ps, commande, cl, ce)).execute();
			}
			// Supprimer un dossier
			else if(commande.split(" ")[0].equals("rmdir")) {
				(new CommandeRMDIR(ps, commande, cl, ce)).execute();
			} 
			// L'utilisateur est deja connecter
			else if(commande.split(" ")[0].equals("user") || commande.split(" ")[0].equals("pass")) {
				ps.println("0 You are already connected");
			}
			// Commande inkonnu
			else {
				ps.println("2 I don't understand :(");
			}
		}
		else {
			if(commande.split(" ")[0].equals("pass") || commande.split(" ")[0].equals("user")) {
				// Le mot de passe pour l'authentification
				if(commande.split(" ")[0].equals("pass")) (new CommandePASS(ps, commande, cl, ce)).execute();
	
				// Le login pour l'authentification
				if(commande.split(" ")[0].equals("user")) (new CommandeUSER(ps, commande, cl, ce)).execute();
			}
			else
				ps.println("2 You are not connected (set your user authenficiation ) !");
		}
	}

}
