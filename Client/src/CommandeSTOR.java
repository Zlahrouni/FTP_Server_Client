import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

public class CommandeSTOR extends Commande {
	
	public CommandeSTOR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		if(!this.commandeArgs[0].isEmpty()) {
			File fichier = new File(this.commandeArgs[0]);
			try {
				FileInputStream in = new FileInputStream(fichier);
				BufferedInputStream bis = new BufferedInputStream(in);
				
				// Lire le contenu du fichier et l'écrire dans le PrintStream
		        byte[] buffer = new byte[1024];
		        int count;
		        while ((count = bis.read(buffer)) != -1) {
		            ps.write(buffer, 0, count);
		        }
		        
		        ps.print("0 chargement terminé");
		        bis.close();
		        
			} catch ( IOException e) {
				ps.print("2 erreur ");
				e.printStackTrace();
			}
		
			
		}else {
			ps.print("2 USAGE : stor [File]");
		}
	}

}
