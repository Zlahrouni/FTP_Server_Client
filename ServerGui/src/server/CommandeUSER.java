package server;
import java.io.File;
import java.io.PrintStream;

public class CommandeUSER extends Commande {
	static int used = 0;
	public CommandeUSER(PrintStream ps, String commandeStr, InformationClient cl, CommandExecutor ce) {
		super(ps, commandeStr, cl, ce);
	}

	public void execute() {

		// Ce serveur accepte uniquement le user personne
		if (commandeArgs.length != 0) {
			File username = new File(commandeArgs[0]);
			if(username.exists()) {
				this.ce.userOk = true;
				
				ps.println("0 Commande user OK");
				cl.setUsername(commandeArgs[0]);
				cl.workingdir = commandeArgs[0];
			} else {
				ps.println("2 username doesnt exist");
			}
		}
		/*
		if(commandeArgs[0].toLowerCase().equals("personne")) {
			CommandExecutor.userOk = true;
			ps.println("0 Commande user OK");
		}
		else {
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}*/
		
	}

}
