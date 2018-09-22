package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;

/*class, which controls the aspects of the menu screen*/

public class MenuController {
	File thisFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	String thisFile1 = thisFile.getAbsolutePath();
	@FXML
	private Label label;
	@FXML
    private ChoiceBox encryptChoose;
	@FXML
    private ChoiceBox decryptChoose;
	private MainController mainController;
	/*variables and getters used to get information, which type of
	  data to encrypt has chosen the user.*/
	private static boolean choseText = false;
	private static boolean choseImage = false;
	private static boolean choseOther = false;

    public static boolean isChoseText() {
        return choseText;
    }

    public static boolean isChoseImage() {
        return choseImage;
    }

    public static boolean isChoseOther() {
        return choseOther;
    }

    public void setChoseText(boolean choseText) {
        this.choseText = choseText;
    }

    public void setChoseImage(boolean choseImage) {
        this.choseImage = choseImage;
    }

    public void setChoseOther(boolean choseOther) {
        this.choseOther = choseOther;
    }

    @FXML
    public void initialize() {
	    label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }
	
	/*Method which starts, when user clicked on 'encrypt' button.*/
    public void encrypt() {
        if(encryptChoose.getValue().equals(new String("Text"))){
            setChoseText(true);
        }
        if(encryptChoose.getValue().equals(new String("Image"))){
            setChoseImage(true);
        }
        if(encryptChoose.getValue().equals(new String("Other file"))){
            setChoseOther(true);
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen1.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		EncryptScreen1Controller encryptSc1 = loader.getController();
		encryptSc1.setMainController(mainController);
		mainController.setScreen(pane);
	}
	@FXML
	/*Method which starts, when user clicks on 'decrypt' button.*/
	public void decrypt() {
        if(decryptChoose.getValue().equals(new String("Text"))){
            setChoseText(true);
        }
        if(decryptChoose.getValue().equals(new String("Image"))){
            setChoseImage(true);
        }
        if(decryptChoose.getValue().equals(new String("Other file"))){
            setChoseOther(true);
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen1.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		DecryptionScreen1Controller encryptSc1 = loader.getController();
		encryptSc1.setMainController(mainController);
		mainController.setScreen(pane);
	}
	@FXML
	/*Method which starts, when user clicks on 'Generate a Key' button.*/
	public void generateAKey() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/GenerateAKeyScreen.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GenerateAKeyScreenController encryptSc1 = loader.getController();
		encryptSc1.setMainController(mainController);
		mainController.setScreen(pane);
	
	}
	@FXML
	/*Method which starts, when user clicks on 'Help' button.*/
	public void help() {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/HelpScreen.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HelpScreenController encryptSc1 = loader.getController();
		encryptSc1.setMainController(mainController);
		mainController.setScreen(pane);
	}
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
