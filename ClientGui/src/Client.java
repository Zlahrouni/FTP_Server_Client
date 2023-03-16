import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client{
	boolean userOk = false;
	boolean passOk = false;
	public String username = "";
	private JTextArea resultArea;
	
	public Client(JTextArea resultArea) {
		this.resultArea = resultArea;
	}
	/**
	 * Cette méthode permet d'afficher les messages envoyés par le serveur à travers
	 * un BufferedReader.
	 * 
	 * @param br Le BufferedReader à partir duquel les messages doivent être lus.
	 */
	public void affichage(BufferedReader br) {
		String message = "";
		try {
			while ((message = br.readLine()) != null) {
				resultArea.append(">> " +message + "\n");
				//System.out.println(">> " + message);
				if (message.startsWith("0") || message.startsWith("2")) {
					break;
				}
			}

			// System.out.println(message);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void handleStor(BufferedReader br, PrintStream ps, String commande) {
		String filename = commande.split(" ")[1];
		try {
			FileInputStream fis = null;
			String line;
			while ((line = br.readLine()) != null) {

				if (line.startsWith("1 Reading in")) {
					int startIndex = line.indexOf("[") + 1;
					int endIndex = line.indexOf("]");
					int GETPORT = Integer.parseInt(line.substring(startIndex, endIndex));

					// recuperation du fichier :
					// Création d'une nouvelle connexion sur le port récupéré
					@SuppressWarnings("resource")
					Socket dataSocket = new Socket("localhost", GETPORT); 
					OutputStream out = dataSocket.getOutputStream();
					// BufferedWriter dataWriter = new BufferedWriter(new
					// OutputStreamWriter(dataSocket.getOutputStream()));
					fis = new FileInputStream(filename);

					byte[] buffer = new byte[8192]; // 8 Ko
					int count;
					while ((count = fis.read(buffer)) != -1) {
						out.write(buffer, 0, count);
					}
					System.out.println("PORT: " + GETPORT);
					resultArea.append("Connexion to the port " +GETPORT + " to stor the file...");
					out.flush(); // noy necessairy
					fis.close(); // fermeture de la 
					out.close(); // fermeture du fichier ecrit
					dataSocket.close(); // fermeture de socket service GET
				} else {
					//System.out.println(">> " + line);
					resultArea.append(">> " +line + "\n");
					if (line.startsWith("0") || line.startsWith("2")) {
						break;
					}
				}
			}

			// System.out.println("File saved as " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void handleConnection(BufferedReader br, String commande) throws IOException{
		String message = "";
		if (br != null) {
			if (commande.startsWith("user")) {
				while ((message = br.readLine()) != null) {
					//System.out.println(">> "+message);
					resultArea.append(">> " +message+ "\n");
					if(message.startsWith("0")) {
						userOk = true;
						break;
					} else if (message.startsWith("2")) {
						userOk = false;
						break;
					}
				}
			} else if(commande.startsWith("pass")) {
				while ((message = br.readLine()) != null) {
					//System.out.println(">> "+message);
					resultArea.append(">> " +message + "\n");
					if(message.startsWith("0")) {
						passOk = true;
						break;
					} else if(message.startsWith("2")) {
						passOk = false;
						break;
					}
				}
			}
		} else {
			resultArea.append("Can't get the message from the server, check your connection first\n");
		}
	}

	/**
	 * Cette fonction permet de gérer la commande "get" en enregistrant le contenu
	 * du fichier spécifié dans les arguments de la commande dans un fichier local.
	 * 
	 * 
	 * @param br       Le flux d'entrée où les résultats de la commande doivent être
	 *                 lus.
	 * @param ps       Le flux de sortie où les résultats de la commande doivent
	 *                 être imprimés.
	 * @param commande La chaîne de caractères qui représente la commande "get" avec
	 *                 ses arguments.
	 */
	public void handleGet(BufferedReader br, PrintStream ps, String commande) {
		String filename = commande.split(" ")[1];
		boolean first = false;

		try {
			FileOutputStream fos = null;
			String line;
			while ((line = br.readLine()) != null) {

				if (line.startsWith("1 Reading in")) {
					int startIndex = line.indexOf("[") + 1;
					int endIndex = line.indexOf("]");
					int GETPORT = Integer.parseInt(line.substring(startIndex, endIndex));

					// recuperation du fichier :
					// Création d'une nouvelle connexion sur le port récupéré
					@SuppressWarnings("resource")
					Socket dataSocket = new Socket("localhost", GETPORT);
					resultArea.append("Connexion to the port " +GETPORT + " to get the file...");
					BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));

					// Récupération du contenu du fichier
	                String fileContent = "";
	                String dataLine;
	                while ((dataLine = dataReader.readLine()) != null) {
	                    if (!first) {
	                        fos = new FileOutputStream(new File(filename));
	                        resultArea.append(">> " + filename);
	                        first = true;
	                    }
	                    if (first) {
	                        fos.write(dataLine.getBytes(StandardCharsets.ISO_8859_1));
	                        fos.write(System.lineSeparator().getBytes()); // add line separator
	                    }
	                }
					//System.out.println(fileContent);
					resultArea.append(">> " +fileContent);

					
					dataSocket.close();
				} else {
					resultArea.append(">> " + line +"\n");
					
					if (line.startsWith("0") || line.startsWith("2")) {
						break;
					}
				}
			}

			// System.out.println("File saved as " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Cette méthode permet d'exécuter une commande donnée en argument, en la
	 * transmettant au serveur. La méthode gère les commandes "get" et l'affichage
	 * des résultats des autres commandes.
	 * 
	 * @param br       Le BufferedReader associé au socket.
	 * @param ps       Le PrintStream associé au socket.
	 * @param commande La chaîne de caractères représentant la commande à exécuter.
	 */
	public void commande(BufferedReader br, PrintStream ps, String commande) {
		ps.println(commande);
		if (commande.startsWith("get")) {
			handleGet(br, ps, commande);
		} else if (commande.startsWith("stor")) {
			handleStor(br, ps, commande);
		} else if(commande.startsWith("user") || commande.startsWith("pass")) {
			try {
				handleConnection(br, commande);
			} catch (IOException e) {
				System.out.println("Error in connection : "+e.getCause());
			}
		} else {
			affichage(br);
		}

		ps.flush();

	}
	
	public void showLoginDialog(BufferedReader br, PrintStream ps) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            commande(br, ps, "user " + username);
            commande(br, ps, "pass " + password);
        }
    }
}
