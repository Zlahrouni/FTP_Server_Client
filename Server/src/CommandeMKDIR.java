import java.io.File;
import java.io.PrintStream;

/**
 * Cette classe représente une commande "rmdir" qui supprime un répertoire vide.
 * Cette commande hérite de la classe Commande et implémente la méthode execute().
 * 
 * @author Hanane Erraji
*/
public class CommandeMKDIR extends Commande {

	/**
	 * Constructeur de la classe CommandeRMDIR.
	 * @param ps Le flux de sortie où les résultats de la commande doivent être imprimés.
	 * @param commandeStr La chaîne de caractères qui représente la commande "rmdir".
	 */
	public CommandeMKDIR(PrintStream ps, String commandeStr) { 
		super(ps, commandeStr);
	}
	
	/**
	 * Cette méthode exécute la commande "rmdir" en supprimant le répertoire spécifié
	 * dans les arguments de la commande s'il est vide. Elle affiche le résultat de la
	 * commande dans le flux de sortie spécifié dans le constructeur.
	*/
	@Override
	public void execute() {
		
		String nomRepertoire;
		File repertoireCourant;
		File nouveauRepertoire;
		
		if(!this.commandeArgs[0].isEmpty()) {
			    nomRepertoire =this.commandeArgs[0] ; // Nom du nouveau répertoire à créer
			    repertoireCourant = new File(System.getProperty("user.dir")); // Obtenir le répertoire courant
		        nouveauRepertoire = new File(repertoireCourant, nomRepertoire);
		        
		        
		        if (nouveauRepertoire.exists()) {
		        	// Si le répertoire existe déjà, afficher un message d'erreur
		            ps.print("2 The folder '" + nomRepertoire + "' already exist");
		        }
		        
		        else {
		            // Si le répertoire n'existe pas, créer un nouveau répertoire
		            boolean creationSucces = nouveauRepertoire.mkdir(); 
		            
		            if(creationSucces) {
		            	// Si la création du répertoire s'est bien déroulée, afficher un message de confirmation
		            	ps.print("0 The folder " + nomRepertoire + " is created");
		            }else {
		            	
	                    // Si la création du répertoire a échoué, afficher un message d'erreur
		            	ps.print("2 The folder '" + nomRepertoire + "' couldn't be created");
		            }
		        }
		}
		else {
			// Si le nom du répertoire n'a pas été précisé, afficher un message d'erreur
			ps.print("2 USAGE : mkdir [folder name]");
		}
	}

}