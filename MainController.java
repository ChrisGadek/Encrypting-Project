package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;

/*This file contains the main controller of every stage in application.*/
public class MainController  {
	File thisFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	String thisFile1 = thisFile.getAbsolutePath();

	@FXML
	private StackPane mainStackPane;
	@FXML
	public void initialize() {
		/*This way of changing screen is used in every controller.
		  It uses setScreen method to clear previous Pane and add new one.*/
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/Menu.fxml"));
		Pane pane1 = null;
		try {
			pane1 = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setScreen(pane1);
		MenuController menuController = loader.getController();
		menuController.setMainController(this);
	}
	/*Method uses to set scenes*/
	public void setScreen(Pane pane2) {
		mainStackPane.getChildren().clear();
		mainStackPane.getChildren().add(pane2);
	}
}
