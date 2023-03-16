package server;
import java.io.File;
import java.io.PrintStream;

/**
 * Cette classe représente une commande "ls" qui affiche la liste des fichiers
 * et répertoires dans le répertoire courant ou dans un répertoire spécifié, en 
 * plus, elle donnes aussi le nombre de repertoires/fichiers qui existe.
 * Cette commande hérite de la classe Commande et implémente la méthode
 * execute().
 * 
 * @author Ziad Lahrouni, Hanane Erraji
 */
public class CommandeLS extends Commande {

	/**
	 * Constructeur de la classe CommandeLS.
	 * 
	 * @param ps          Le flux de sortie où les résultats de la commande doivent
	 *                    être imprimés.
	 * @param commandeStr La chaîne de caractères qui représente la commande "ls".
	 */
	public CommandeLS(PrintStream ps, String commandeStr, InformationClient cl, CommandExecutor ce) {
		super(ps, commandeStr, cl, ce);
	}

	/**
	 * Cette méthode exécute la commande "ls" en utilisant la méthode list() de la
	 * classe File pour obtenir la liste des fichiers et répertoires dans le
	 * répertoire courant ou dans un répertoire spécifié. Elle affiche le résultat
	 * de la commande dans le flux de sortie spécifié dans le constructeur.
	 */
	public void execute() {
		// Obtenir le répertoire courant
		String currentDirectory =this.cl.workingdir;
		File directory = null;
		
		// Vérifier si un argument a été spécifié pour la commande
		if (this.commandeArgs.length!= 0) {
				directory = new File(this.cl.workingdir+"\\"+this.commandeArgs[0]);
		} else {
			directory = new File(currentDirectory);
		}
		//wSystem.out.println(directory.getAbsolutePath());
		
		// Obtenir la liste des fichiers et répertoires dans le répertoire spécifié
		String[] fileList = directory.list();
		
		// Index qui va compter le nombre de fichiers et repertoires qui existe
		int idx = 0;
		String tabulation = "            ";
		// Vérifier si la liste n'est pas nulle
		if (fileList != null) {
			for (String f : fileList) {
				File file = new File(this.cl.workingdir+"\\"+f);
				if(file.isFile()) {
					ps.println("1 " + f + tabulation +" (file size :" + file.length() + " bytes)");
				} else {
					ps.println("1 " + f + tabulation + " (directory)");
				}
				idx++;
			}
			if (idx == 0) {
				ps.println("0 No files or directories were found.");
			} else {
				ps.println("0 Found " + idx + " file(s)/directory(s)");
			}
		} else {
			ps.println("The path specified in the arguments does not exist.");
		}
	}

}
