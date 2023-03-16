package server;
import java.io.File;
import java.io.PrintStream;

public class CommandePWD extends Commande {
	
	public CommandePWD(PrintStream ps, String commandeStr, InformationClient cl, CommandExecutor ce) {
		super(ps, commandeStr, cl, ce);
	}

	public void execute() {
		
		File file = new File(this.cl.workingdir);
		String s = file.getAbsoluteFile().toString();
		this.cl.workingdir = s;
		//System.out.println(this.cl.workingdir);
		ps.println("0 " + s);
	}

}
