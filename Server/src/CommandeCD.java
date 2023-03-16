
import java.io.File;
import java.io.PrintStream;

/**
 * Cette classe est une implémentation de la commande "cd" qui permet de changer de
 * répertoire courant dans le système de fichiers. Elle étend la classe abstraite
 * Commande qui définit une structure de base pour toutes les commandes qui seront
 * implémentées.
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
	public CommandeCD(PrintStream ps, String commandeStr, InformationClient cl) {
		super(ps, commandeStr, cl);
	}

    /**
     * Cette méthode execute la commande "cd" en changeant le répertoire courant du
     * système de fichiers en fonction des arguments fournis. Si aucun argument n'est
     * fourni, le répertoire courant est utilisé.
     */
    public void execute() {
        // Déclaration des variables
        File repertoire;
        String nomRepertoire;

        // Si aucun argument n'est fourni, on utilise le répertoire courant
        if (this.commandeArgs.length == 0) {

            File fichier = new File(".");
            String pathAbsolue =  fichier.getAbsolutePath();
            String[] repertoireCourantArray = pathAbsolue.split("\\\\");
            nomRepertoire = repertoireCourantArray[0] + "\\";

        } else {
        	
            // Utiliser le répertoire spécifié dans les arguments
            nomRepertoire = this.cl.workingdir+ "\\" + this.commandeArgs[0];
            
        }

        // Créer un objet File pour le répertoire
        repertoire = new File(nomRepertoire);

        // Vérifier si le répertoire existe et s'il s'agit d'un répertoire
        if (repertoire.exists() && repertoire.isDirectory()) {
            // Si le répertoire existe, changer le répertoire courant du processus
            this.cl.workingdir =nomRepertoire;
            ps.println("0 Le répertoire courant est maintenant : " + this.cl.workingdir);
        } else {
            // Si le répertoire n'existe pas, afficher un message d'erreur
            ps.println("2 le repertoire n'existe pas ");
        }
    }
}
