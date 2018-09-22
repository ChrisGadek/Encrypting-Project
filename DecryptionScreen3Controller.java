package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/*Controller, which controls the second (or third) step of decrypting some
  information. Here the application collects the information about file, which user wants
  to decrypt.*/

public class DecryptionScreen3Controller {
    private MainController mainController;
    @FXML
    private TextField textLabel;
    @FXML
    private Button nextButton;
    @FXML
    private Label wrongLabel;
    @FXML
    private Button wrongButton;
    @FXML
    private Label label;
    public static String Dpath;
    private boolean goNext = true;
    /*the same like in the previous screen controller.*/
    public void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }
    /*Main method, which collects the information about file to encrypt.*/
    public void getText() {
        if (nextButton.isVisible() == true) {
            Dpath = textLabel.getText();
            Dpath = Dpath.trim();
            /*this long if statement controls the user's input. It allows to put the path
              only to the files with '.txt' extension, if the user chose decryption
              of some text in menu screen. The same with images, if
              user chose 'Image' in the Menu screen, then it allows to get path of files with '.jpg' extension.
              If the user those third option, this statement allows every extension.*/
            if (!((Dpath.substring(Dpath.length() - 4, Dpath.length()).equals(new String(".txt"))
                    && MenuController.isChoseText()) || (Dpath.substring(Dpath.length() - 4, Dpath.length()).equalsIgnoreCase(new String(".txt"))
                    && MenuController.isChoseImage()) || MenuController.isChoseOther())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Your're trying to load invalid file. Try Again.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen3.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    DecryptionScreen3Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            } else {
                //executes if everything's OK.
                File f = new File(Dpath);
                if (!f.exists()) {
                    /*executes, when the user put the path to a file, which didn't exist.*/
                    goNext = false;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("File don't exists.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen3.fxml"));
                        Pane pane = null;
                        try {
                            pane = loader.load();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        EncryptScreen3Controller encrypt2c = loader.getController();
                        encrypt2c.setMainController(mainController);
                        mainController.setScreen(pane);
                    }
                }
                if (nextButton.isVisible() == true) {
                    //executes if everything's OK.
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen4.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DecryptionScreen4Controller encryptSc5 = loader.getController();
                    encryptSc5.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            }
        }
    }

    public void getTextByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            getText();
        }
    }
    //The same like in the previous screen.
    public void repeat() {
        if (wrongButton.isVisible() == true) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen3.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DecryptionScreen3Controller encryptSc3 = loader.getController();
            encryptSc3.setMainController(mainController);
            mainController.setScreen(pane);
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void repeatByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            repeat();
        }
    }
    //the same like in the previous screen.
    @FXML
    public void back() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen2.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecryptionScreen2Controller menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    //the same like in the firs decrypt screen.
    @FXML
    public void browse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to encryptText");
        File toEncrypt = fileChooser.showOpenDialog(Main.getStage());
        String pathOfEncrypted = toEncrypt.getAbsolutePath();
        textLabel.setText(pathOfEncrypted);
    }
}
