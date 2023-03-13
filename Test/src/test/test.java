package test;


import java.io.File;
import java.io.IOException;

public class test {

	public static void main(String[] args) throws IOException {
			String directoryPath = "C:\\Users\\PC\\Documents\\dev\\";
	        File directory = new File(directoryPath);
	        if (directory.exists()) {
	            System.setProperty("user.dir", directory.getAbsolutePath());
	            System.out.println("Le répertoire courant est maintenant : " + System.getProperty("user.dir"));
	        } else {
	            System.out.println("Le répertoire spécifié n'existe pas.");
	        }
	        
	     // Déclaration des variables
	        File repertoire;
	        String nomRepertoire;
	        
	        String arg = "";
	        // Si aucun argument n'est fourni, on utilise le répertoire courant
	        if (arg.isEmpty()) {

	            File fichier = new File(".");
	            String pathAbsolue =  fichier.getParent();
	            String[] repertoireCourantArray = pathAbsolue.split(File.separator);
	            nomRepertoire = repertoireCourantArray[0] + File.separator;

	        } else {

	            // Utiliser le répertoire spécifié dans les arguments
	            nomRepertoire = arg;
	        }

	        // Créer un objet File pour le répertoire
	        repertoire = new File(nomRepertoire);

	        // Vérifier si le répertoire existe et s'il s'agit d'un répertoire
	        if (repertoire.exists() && repertoire.isDirectory()) {
	            // Si le répertoire existe, changer le répertoire courant du processus
	            System.out.print("1 commande en cours: le repertoire existe");
	            System.setProperty("user.dir", repertoire.getAbsolutePath());
	            System.out.print("0 Le répertoire courant est maintenant : " + System.getProperty("user.dir"));
	        } else {
	            // Si le répertoire n'existe pas, afficher un message d'erreur
	        	System.out.print("2 le repertoire n'existe pas ");
	        }
         
	}

}

