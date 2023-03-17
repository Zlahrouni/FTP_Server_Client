package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import server.Server;


/**
 * Controller class for the FTP server GUI.  
 */
public class ServerController implements Initializable {
		
	int port = 2121;
	private Server server;

	
	@FXML
    private TextArea log;

    @FXML
    private VBox root;

    @FXML
    private Button startBtn;
    /*
    @FXML
    private Button stopBtn;
    */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		log("Welcome to TransferEase, to start the server click on start button");
		

		log.setStyle("-fx-font-size: 14px; -fx-font-family: monospace;");
		
	}

    @FXML
    void onStart(ActionEvent event) throws IOException  {
    	
    	disableStartBtn();
    	//enableStopBtn();
    	server = new Server(port, log);
    	server.start(); 
 
    	
    }
    /*

    @FXML
    void onStop(ActionEvent event) {
        try {
        	log("INFO : Try to stop the server");
        	server.stopThread(); // stop the server

            disableStopBtn();
            enableStartBtn();
            //Server.isRunning = false;
            log("good bye :)");
        } catch (Exception e) {
        	
            e.printStackTrace();
            log("Error in stopping server");
            disableStartBtn();
        	enableStopBtn();
        }
    }
    */

    /**
     * Logs a message to the text area.
     * @param message the message to log.
     */
    private void log(String message) {
        Platform.runLater(() -> log.appendText(message + "\n"));
    }

    /**
     * Enables the Start button.
     */
    private void enableStartBtn() {
        Platform.runLater(() -> startBtn.setDisable(false));
    }

    /**
     * Disables the Start button.
     */
    private void disableStartBtn() {
        Platform.runLater(() -> startBtn.setDisable(true));
    }
    
    /**
     * Enables the Stop button.
     */
    /*
    private void enableStopBtn() {
        Platform.runLater(() -> stopBtn.setDisable(false));
    }*/

    /**
     * Disables the Stop button.
     *//*
    private void disableStopBtn() {
        Platform.runLater(() -> stopBtn.setDisable(true));
    }*/
    
    protected void close() {
    	server.stopThread();
    }
    


	

}
