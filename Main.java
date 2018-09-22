package application;
/*
*
*
*
Created by Krzysztof Gadek
*
*
*
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

	private static Stage stage;
	@Override
	public synchronized void start(Stage primaryStage) throws Exception{
			FXMLLoader fx  = new FXMLLoader(this.getClass().getResource("/application/fxml/Start.fxml"));
			StackPane stackPane = fx.load();
			stackPane.setId("pane");
			Scene scene = new Scene(stackPane);
			stackPane.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Encrypting/Decrypting");
			primaryStage.setScene(scene);
			stage = primaryStage;
			primaryStage.setResizable(false);
			primaryStage.show();
	}

	private static  int id = 0;
	/* Method that handle errors in FX Thread. If error occurs more than one time,
	 the application shuts down to not allow the thread to freeze and make users more annoyed. */
	/*private static synchronized void showError(Thread t, Throwable e) {
		System.err.println("***Default exception handler***");
		e.printStackTrace();
		if (Platform.isFxApplicationThread()){
			synchronized (e) {
				id++;
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Something went wrong, try again or read 'help section'");
				if(id > 1){
					Platform.exit();
				}
			}
		} else {
			System.err.println("An unexpected error occurred in "+t);

		}

	}*/
	/*Getter of current stage*/
	public static Stage getStage(){
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
