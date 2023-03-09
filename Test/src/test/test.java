package test;


import java.io.File;
import java.io.IOException;

public class test {

	public static void main(String[] args) throws IOException {
		System.out.println("Le répertoire courant est maintenant : " + System.getProperty("user.dir"));
		File fichier = new File(".");
		String pathAbsolue =  fichier.getAbsolutePath();
		String[] repertoireCourantArray = pathAbsolue.split("/");
         String repertoireCourant = repertoireCourantArray[repertoireCourantArray.length - 1];
         File repertoire = new File(repertoireCourant);
         String [] listeFichiers = repertoire.list();
         
         for (String f : listeFichiers) {
         	System.out.println(f);
         }
         
         String directoryPath = "C:\\";
         File directory = new File(directoryPath);
         if (directory.exists()) {
             System.setProperty("user.dir", directory.getAbsolutePath());
             System.out.println("Le répertoire courant est maintenant : " + System.getProperty("user.dir"));
         } else {
             System.out.println("Le répertoire spécifié n'existe pas.");
         }

         String currentDirectory = System.getProperty("user.dir");
         File directory1;
         
         if (args.length > 0) {
             directory1 = new File(args[0]);
         } else {
             directory1 = new File(currentDirectory);
         }
         
         String[] fileList = directory1.list();

         for (String file : fileList) {
             System.out.println(file);
         }
         
	}

}

