package application;
	
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("TransferEase");
			primaryStage.setScene(scene);
			// Ajouter une action personnalisée lors de la fermeture de la fenêtre
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent event) {
			        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir fermer la fenêtre ?", ButtonType.YES, ButtonType.NO);
			        alert.showAndWait();

			        if (alert.getResult() == ButtonType.NO) {
			            event.consume();
			        } else if (alert.getResult() == ButtonType.YES) {
			        	//
			            System.exit(0); 
			        }
			    }
			});

			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
