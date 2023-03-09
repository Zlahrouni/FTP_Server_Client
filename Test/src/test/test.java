package test;


import java.io.File;
import java.io.IOException;

public class test {

	public static void main(String[] args) throws IOException {
			String directoryPath = "C:\\Users\\PC\\Documents\\dev\\projects\\Java\\FTP\\Test\\src\\test";
	        File directory = new File(directoryPath);
	        if (directory.exists()) {
	            System.setProperty("user.dir", directory.getAbsolutePath());
	            System.out.println("Le répertoire courant est maintenant : " + System.getProperty("user.dir"));
	        } else {
	            System.out.println("Le répertoire spécifié n'existe pas.");
	        }
	        
	        String arg = System.getProperty("user.dir")+File.separator+"folder";
			String chemin;
			if (arg.contains(File.separator))  {
				chemin = arg;
			} else {
				chemin = System.getProperty("user.dir")+File.separator+"folder";
			}


         // Créer un objet File pour le répertoire
         File repertoire = new File(chemin);
         
         // Vérifier si le répertoire existe et s'il est vide
         if (repertoire.exists() && repertoire.isDirectory() && repertoire.list().length == 0) {
             // Supprimer le répertoire vide
             if (repertoire.delete()) {
                 System.out.println("0 rmdir : The folder '" + chemin.substring(chemin.lastIndexOf(File.separator)+1) + "' is deleted");
             } else {
            	 System.out.println("2 rmdir :  '" + chemin+"'");
             }
         } else if (!repertoire.exists()){
        	 System.out.println("2 rmdir : The folder '" + chemin + "' doesn't exist");
         } else if (!repertoire.isDirectory()) {
        	 System.out.println("2 rmdir : '" + chemin.substring(chemin.lastIndexOf(File.separator)+1) + "' is not a folder");
         } else if (repertoire.list().length != 0) {
        	 System.out.println("2 rmdir : The folder '" + chemin.substring(chemin.lastIndexOf(File.separator)+1) + "' is not empty");
         }  else {
        	 System.out.println("2 rmdir : Unkown Error");
         }
         
	}

}

