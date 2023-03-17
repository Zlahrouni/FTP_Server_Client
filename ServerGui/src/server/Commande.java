package server;

import java.io.PrintStream;

public abstract class Commande {

	protected PrintStream ps;
	/**
	 * The name of command passed by the user.
	 */
	protected String commandeNom = "";
	/**
	 * The arguments passed by the user.
	 */
	protected String [] commandeArgs ;
	/**
	 * The command handler to use to run the command.
	 */
	CommandExecutor ce;
	/**
	 *  Client information, used to represent the client context
	 */
	InformationClient cl;
	
	/**
	 * Constructor for the {@code Commande} class.
	 * @param ps The output stream to write the command results to
	 * @param commandeStr The string representation of the command to parse
	 * @param cl The string representation of the command to parse
	 * @param ce The command executor to use for executing the command
	 */
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
