package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Platform;
import javafx.scene.control.TextArea;


/**
 * The main FTP server class. It listens for client connections and starts a new
 * conversation thread for each client.
 */
public class Server extends Thread {
	
	final static boolean LOG_ERROR = true;
	final static boolean LOG_INFO = false;
	private Socket clientSocket;
	private final int port;
    private ServerSocket serverSocket;
    private boolean isRunning;
    private Set<Conversation> activeConversations = new HashSet<>();
    private final TextArea logArea;

    public Server(int port, TextArea logArea) {
        this.port = port;
        this.isRunning = false;
        this.logArea = logArea;
    }

    /**
     * Stop the server thread safely
     */
    public void stopThread() {
        // Stop all active conversations before stopping the server
        if (isRunning) {
        	for (Conversation conversation : activeConversations) {
                conversation.stopThread();
            }
            isRunning = false;
        }
       
        
    }
    /*
    public void log(String message) {
        Platform.runLater(() -> logArea.appendText(message + "\n"));
    }*/
    
    public void log(String message, boolean isError) {
        Platform.runLater(() -> {
            String logMessage = message + "\n";
            if (isError) {
            	logMessage = "Error :" + logMessage;
            }
           
            logArea.appendText(logMessage);
        });
    }

    @Override
    public void run() {
        log("FTP Server started on port " + port, LOG_INFO);
        try {
			this.serverSocket = new ServerSocket(port);
			log("The server socket is created", LOG_INFO);
			this.isRunning = true;
			while (isRunning) {
	            try {
	            	clientSocket = serverSocket.accept();
	            	
	                log("Accepted connection from " + clientSocket.getInetAddress().getHostAddress()+ " " + clientSocket.getPort() ,LOG_INFO );
	                System.out.println("hoho " + activeConversations.size());
	                Conversation conversation = new Conversation(clientSocket, logArea, this);
	                conversation.start();
	                //Thread conversationThread = new Thread(conversation);
	                //conversationThread.start();

	                // Add the new conversation to the set of active conversations
	                activeConversations.add(conversation);
	            } catch (IOException e) {
	                log("Failed to accept client connection: " + e.getMessage(), LOG_ERROR);
	            }
	        }
			
	        // Stop all active conversations before closing the server socket
	        for (Conversation conversation : activeConversations) {
	            conversation.stopThread();
	        }
	        serverSocket.close();
		} catch (IOException e1) {
			this.isRunning = false;
			if (serverSocket != null) {
				log("The serverSocket is not null ", LOG_INFO);
				if (!serverSocket.isClosed()) {
					log("The serverSocket is not closed ", LOG_INFO);
					try {
						serverSocket.close();
					} catch (IOException e) {
						log("Try to close the socket on the catch creation " + e.getCause(), LOG_ERROR);
					}
				}
			} else {
				log("T", LOG_INFO);
			}
			log("Can't create the socket server", LOG_ERROR );
		}
        
        
    }



    /**
     * The thread that handles a single client conversation.
     */
    /**
     * The thread that handles a single client conversation.
     */
    private class Conversation extends Thread {

        private final Socket mysocket;
        private boolean isRunning;
        private final TextArea logArea;

        public Conversation(Socket clientSocket, TextArea logArea, Server server) {
            this.mysocket = clientSocket;
            this.isRunning = true;
            this.logArea = logArea;
        }

        public void log(String message, boolean isError) {
            Platform.runLater(() -> {
                String logMessage = message + "\n";
                if (isError) {
                	logMessage = "Error :" + logMessage;
                }
               
                logArea.appendText(logMessage);
            });
        }

                
        
        @Override
        public void run() {
        	CommandExecutor CE = new CommandExecutor();
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
                    PrintStream writer = new PrintStream(mysocket.getOutputStream())
            ) {
                InformationClient client = new InformationClient(mysocket, ".");
                writer.println("1 Bienvenue ! ");
                writer.println("1 Serveur FTP Personnel.");
                writer.println("0 Authentification : ");

                String command;
                while (isRunning && !(command= reader.readLine()).equals("bye")) {
                    String logMessage = "Received command from " + mysocket.getInetAddress().getHostAddress() + " " + mysocket.getPort() + ": " + command;
                    //Platform.runLater(() -> logArea.appendText(logMessage + "\n"));
                    log(logMessage, LOG_INFO);
                    client.setHost(mysocket.getInetAddress().getHostAddress());
                    client.setPort(mysocket.getPort());
                    CE.executeCommande(writer, command, client, CE, logArea);
                }
            } catch (IOException e) {
                String errorMessage = "Failed to read from client: " + e.getMessage();
                Platform.runLater(() -> logArea.appendText(errorMessage + "\n"));
            } finally {
                try {
                	mysocket.close();
                    
                    String logMessage = "Connection closed with " + mysocket.getInetAddress().getHostAddress();
                    Platform.runLater(() -> logArea.appendText(logMessage + "\n"));
                } catch (IOException e) {
                    String errorMessage = "Failed to close client connection: " + e.getMessage();
                    //Platform.runLater(() -> logArea.appendText(errorMessage + "\n"));
                    log(errorMessage, LOG_ERROR);
                }
                this.stopThread();
            }
        }

        public void stopThread() {
            	
            this.isRunning = false;
        }
    }
}