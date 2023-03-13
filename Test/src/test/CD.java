package test;

import java.io.File;

public class CD {

	public static void main(String[] args) {
		System.out.println("Le path courant :" + System.getProperty("user.dir"));
		// Déclaration des variables
        File repertoire;
        String nomRepertoire;
        String argc = "src";
        // Si aucun argument n'est fourni, on utilise le répertoire courant
        if (argc.length() == 0) {

            File fichier = new File(".");
            String pathAbsolue =  fichier.getAbsolutePath();
            String[] repertoireCourantArray = pathAbsolue.split("\\\\");
            nomRepertoire = repertoireCourantArray[0] + "\\";

        } else {
        	
            // Utiliser le répertoire spécifié dans les arguments
            nomRepertoire = System.getProperty("user.dir") + "\\" + argc;
            
        }

        // Créer un objet File pour le répertoire
        repertoire = new File(nomRepertoire);

        // Vérifier si le répertoire existe et s'il s'agit d'un répertoire
        if (repertoire.exists() && repertoire.isDirectory()) {
            // Si le répertoire existe, changer le répertoire courant du processus
            System.out.println("1 commande en cours: le repertoire existe");
            System.setProperty("user.dir", repertoire.getAbsolutePath());
            System.out.println("0 Le répertoire courant est maintenant : " + System.getProperty("user.dir"));
        } else {
            // Si le répertoire n'existe pas, afficher un message d'erreur
        	System.out.println("2 le repertoire n'existe pas ");
        }

	}

}
