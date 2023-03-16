package server;

import java.io.File;
import java.io.PrintStream;

/**
 * Cette classe est une implémentation de la commande "cd" qui permet de changer
 * de répertoire courant dans le système de fichiers. Elle étend la classe
 * abstraite Commande qui définit une structure de base pour toutes les
 * commandes qui seront implémentées.
 *
 * @author Hanane Erraji, Ziad Lahrouni.
 */
public class CommandeCD extends Commande {

	/**
	 * Constructeur de la classe CommandeLS.
	 * 
	 * @param ps          Le flux de sortie où les résultats de la commande doivent
	 *                    être imprimés.
	 * @param commandeStr La chaîne de caractères qui représente la commande "ls".
	 */
	public CommandeCD(PrintStream ps, String commandeStr, InformationClient cl, CommandExecutor ce) {
		super(ps, commandeStr, cl, ce);
	}

	/**
	 * Cette méthode execute la commande "cd" en changeant le répertoire courant du
	 * système de fichiers en fonction des arguments fournis. Si aucun argument
	 * n'est fourni, le répertoire courant est utilisé.
	 */
	public void execute() {
		// Déclaration des variables
		File repertoire;
		String nomRepertoire = null;

		// Si aucun argument n'est fourni, on utilise le répertoire courant
		if (this.commandeArgs.length == 0) {

			ps.println("2 You dont have permition to acces to the root folder");
			return;

		} else if(this.commandeArgs.length != 0) {

			if (commandeArgs[0].equals("..")) {
				System.out.println("Is root 1: " + cl.root);
				if (cl.root == false) {
					// Go to parent directory
					File parentDir = new File(this.cl.workingdir).getParentFile();
					System.out.println("My username : " + cl.getUsername());
					System.out.println("Parrent : " + parentDir.getName());
					System.out.println("Is root 2 : " + cl.root);
					if (parentDir.getName().equals(cl.getUsername())) {
						cl.root = true;
						System.out.println("Is root 3 : " + cl.root);
						System.out.println("we in root");
					}
					nomRepertoire = parentDir.getAbsolutePath();
				} else {
					ps.println("2 you dont have the acces to the parrent directory");
					return;
				}
			} else {
				if (commandeArgs[0].startsWith("..")) {
					ps.println("2 The path can't start with '..'");
					return;
				} else {
					// Utiliser le répertoire spécifié dans les arguments
					nomRepertoire = this.cl.workingdir + File.separator + this.commandeArgs[0];
					cl.root = false;
				}

			}

			// Créer un objet File pour le répertoire
			repertoire = new File(nomRepertoire);

			// Vérifier si le répertoire existe et s'il s'agit d'un répertoire
			if (repertoire.exists() && repertoire.isDirectory()) {
				// Si le répertoire existe, changer le répertoire courant du processus
				this.cl.workingdir = nomRepertoire;
				ps.println("0 Le répertoire courant est maintenant : " + this.cl.workingdir);
				return;

			} else {
				// Si le répertoire n'existe pas, afficher un message d'erreur
				ps.println("2 le repertoire n'existe pas ");
				cl.root = true;
				return;
			}

		}

		ps.println("2 CD Unknown Error");
		return;
	}

}
