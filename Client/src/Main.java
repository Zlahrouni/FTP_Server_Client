import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Client FTP qui permet de se connecter à un serveur FTP et d'exécuter des 
 * commandes comme "ls", "pwd", "cd", "get" et "bye".
 * Les résultats des commandes sont affichés sur la console et les fichiers téléchargés
 * sont enregistrés localement.
 * @author Ziad Lahrouni 
 * @author Hanane Erraji 
 * 
 *@see <a href="https://ziadlahrouni.com">https://ziadlahrouni.com</a>
 *@see <a href="http://hananeerraji.info/">http://hananeerraji.info/</a>
 */
public class Main {
	
	/**
	 * Cette méthode permet d'afficher les messages envoyés par le serveur à travers 
	 * un BufferedReader.
	 * 
	 * @param br Le BufferedReader à partir duquel les messages doivent être lus.
	 */
	public static void affichage(BufferedReader br) {
		 String message = "";
       try {
			while ((message = br.readLine()) != null) {
			     System.out.println(">> " + message);
			     if (message.startsWith("0") || message.startsWith("2")) {
			    	 break;
			     }
			 }
			
			//System.out.println(message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	/**
	 * Cette fonction permet de gérer la commande "get" en enregistrant le contenu du fichier
	 * spécifié dans les arguments de la commande dans un fichier local.
	 * 
	 * 
	 * @param br Le flux d'entrée où les résultats de la commande doivent être lus.
	 * @param ps Le flux de sortie où les résultats de la commande doivent être imprimés.
	 * @param commande La chaîne de caractères qui représente la commande "get" avec ses arguments.
	 */
	public static void handleGet(BufferedReader br, PrintStream ps , String commande) {
		    String filename = commande.split(" ")[1];
		    boolean first =false;
		    
		    try {
		    	FileOutputStream fos = null;
		        String line;
		        while ((line = br.readLine() )!= null) {
		        	
		        	if (line.startsWith("1 Reading in")) {
		        	    int startIndex = line.indexOf("[") + 1;
		        	    int endIndex = line.indexOf("]");
		        	    int GETPORT = Integer.parseInt(line.substring(startIndex, endIndex));
		        	    
		        	    //recuperation du fichier :
		        	    // Création d'une nouvelle connexion sur le port récupéré
		        	    @SuppressWarnings("resource")
						Socket dataSocket = new Socket("localhost", GETPORT);
		        	    BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));


		        	    // Récupération du contenu du fichier
		        	    String fileContent = "";
		        	    String dataLine;
		        	    while ((dataLine = dataReader.readLine()) != null) {
		        	    	//System.out.println(dataLine);
		        	        //fileContent += dataLine + "\n";
		        	    	if(!first) {
			            		fos = new FileOutputStream(filename);
			            		first = true;
			            	}
			            	if(first) {
			            		fos.write(dataLine.getBytes());
					            fos.write(System.lineSeparator().getBytes()); // add line separator
			            	}
		        	    }
		        	    System.out.println(fileContent);
		        	    
		        	    System.out.println("PORT: " + GETPORT);
		        	    dataSocket.close();
		        	} else {
		        		System.out.println(">> " + line);
				    	 if (line.startsWith("0") || line.startsWith("2")) {
				    		 System.out.println("End");
					    	 break;
					     }
		        	}
		        }
		        
		        //System.out.println("File saved as " + filename);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    
		
	}
	
	/**
	 * Cette méthode permet d'exécuter une commande donnée en argument, en la 
	 * transmettant au serveur.
	 * La méthode gère les commandes "get" et l'affichage des résultats des autres commandes.
	 * 
	 * @param br Le BufferedReader associé au socket.
	 * @param ps Le PrintStream associé au socket.
	 * @param commande La chaîne de caractères représentant la commande à exécuter.
	 */
	public static void commande(BufferedReader br, PrintStream ps , String commande) {
		System.out.println("$ "+commande);
		ps.println(commande);
		if (commande.startsWith("get")) {
			handleGet(br,  ps , commande);
		} else {
			affichage(br);
		}
		
		ps.flush();
		
	}
	

	public static void main(String[] args) {
		
		System.out.println("Client FTP");
		String serverName = "localhost";
       int port = 2121;
       
       try {
           Socket socket = new Socket(serverName, port);
           
           BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           PrintStream ps = new PrintStream(socket.getOutputStream());
           affichage(br);
           
           commande(br,ps,"user personne");
           
           commande(br,ps,"pass abcd");
           

           //commande(br,ps,"pwd");
           //commande(br,ps,"get CommandeGET.java");
           //commande(br,ps,"cd src");
           
           commande(br,ps,"ls");
           //commande(br,ps,"get CommandeSTOR.java");

           //commande(br,ps,"cd src");

           commande(br,ps,"bye");
           
           
           //ps.flush();
           socket.close();
       } catch(Exception  e) {
    	   e.printStackTrace();
       }

	}

}
