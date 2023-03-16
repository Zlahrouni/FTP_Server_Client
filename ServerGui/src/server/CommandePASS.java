package server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class CommandePASS extends Commande {
	
	public CommandePASS(PrintStream ps, String commandeStr, InformationClient cl, CommandExecutor ce) {
		super(ps, commandeStr, cl, ce);
	}

	public void execute() {
		if (commandeArgs.length != 0) {
			System.out.println(cl.getUsername());
			File password = new File(cl.getUsername() + File.separator + "pw.txt");
			if (password.exists()) {
				FileInputStream in = null;
				BufferedReader reader = null;
				try {
		            in = new FileInputStream(password);
		            reader = new BufferedReader(new InputStreamReader(in));
		            String passwordStr = reader.readLine(); // read the first line of the file
		            // do something with the password, e.g. print it out
		            System.out.println(passwordStr);
		            System.out.println("Reading password");
		            if (passwordStr.equals(commandeArgs[0])) {
		            	System.out.println("Match pwd");
		            	ce.pwOk = true;
		    			ps.println("1 Commande pass OK");
		    			ps.println("0 Vous êtes bien connecté sur notre serveur");
		            } else {
		            	ps.println("2 password is incorrect");
		            }
		            
		        } catch (FileNotFoundException e) {
		            ps.println("2 Error in configuration, contact the team to solve the problem");
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (reader != null) {
		                    reader.close();
		                }
		                if (in != null) {
		                    in.close();
		                }
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
				
			}
		} else {
			ps.println("2 Le mode de passe est faux");
		}
	
		/*
		// Le mot de passe est : abcd
		if(commandeArgs[0].toLowerCase().equals("abcd")) {
			CommandExecutor.pwOk = true;
			ps.println("1 Commande pass OK");
			ps.println("0 Vous êtes bien connecté sur notre serveur");

		}
		else {
			ps.println("2 Le mode de passe est faux");
		}
		*/
	}

}
