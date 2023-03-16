package server;

import java.io.PrintStream;

public abstract class Commande {
	
	protected PrintStream ps;
	protected String commandeNom = "";
	protected String [] commandeArgs ;
	CommandExecutor ce;
	InformationClient cl;
	
	public Commande(PrintStream ps, String commandeStr,InformationClient cl, CommandExecutor ce) {
		this.ps = ps ;
		String [] args = commandeStr.split(" ");
		commandeNom = args[0];
		commandeArgs = new String[args.length-1];
		
		for(int i=0; i<commandeArgs.length; i++) {
			commandeArgs[i] = args[i+1];
		}
		this.cl = cl;
		this.ce= ce;
	}
	
	public abstract void execute();

}
