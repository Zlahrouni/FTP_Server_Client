import java.io.File;
import java.io.PrintStream;

/**
 * Cette classe représente une commande "rmdir" qui supprime un répertoire vide.
 * Cette commande hérite de la classe Commande et implémente la méthode
 * execute().
 * 
 * @author Ziad Lahrouni
 */
public class CommandeRMDIR extends Commande {

	/**
	 * Constructeur de la classe CommandeRMDIR.
	 * 
	 * @param ps          Le flux de sortie où les résultats de la commande doivent
	 *                    être imprimés.
	 * @param commandeStr La chaîne de caractères qui représente la commande "rmdir".
	 */
	public CommandeRMDIR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	/**
	 * Cette méthode exécute la commande "rmdir" en supprimant le répertoire spécifié 
	 * dans les arguments de la commande s'il est vide. Elle affiche le résultat de la
	 * commande dans le flux de sortie spécifié dans le constructeur.
	 */
	@Override
	public void execute() {
		String arg = this.commandeArgs[0];
		String chemin;
		if (arg.contains(File.separator))  {
			chemin = arg;
		} else {
			chemin = System.getProperty("user.dir")+File.pathSeparator+arg;
		}

		// Créer un objet File pour le répertoire
		File repertoire = new File(chemin);

		// Vérifier si le répertoire existe et s'il est vide
		if (repertoire.exists() && repertoire.isDirectory() && repertoire.list().length == 0) {
			// Supprimer le répertoire vide
			if (repertoire.delete()) {
				ps.println("0 rmdir : The folder '" + chemin.substring(chemin.lastIndexOf(File.separator)+1) + "' is deleted");
			} else {
				ps.println("2 rmdir :  '" + chemin+"'");
			}
		} else if (!repertoire.exists()){
			ps.println("2 rmdir : The folder '" + chemin + "' doesn't exist");
		} else if (!repertoire.isDirectory()) {
			ps.println("2 rmdir : '" + chemin.substring(chemin.lastIndexOf(File.separator)+1) + "' is not a folder");
		} else if (repertoire.list().length != 0) {
			ps.println("2 rmdir : The folder '" + chemin.substring(chemin.lastIndexOf(File.separator)+1) + "' is not empty");
		}  else {
			ps.println("2 rmdir : Unkown Error");
		}
	}
}