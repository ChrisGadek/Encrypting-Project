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

/*This controller allows the user to enter the path to the
  encrypting file.*/

public class EncryptScreen3Controller {
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
    private Pane paneEncryptScreen3;
    @FXML
    private Label label;
    private boolean chooseFile;
    private boolean goNext = true;

    /*This method changes the background of some nodes.*/
    public void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    public static String path;

    /*Main method, that collects the data from the user.*/
    public void getText() {
        if (nextButton.isVisible() == true) {
            path = textLabel.getText();
            path = path.trim();
            /*this long if statement controls the user's input. It allows to put the path
              only to the files with '.txt' extension, if the user chose decryption
              of some text in menu screen. The same with images, if
              user chose 'Image' in the Menu screen, then it allows to get path of files with '.jpg' extension.
              If the user those third option, this statement allows every extension.*/
            try{
                if (!((path.substring(path.length() - 4, path.length()).equals(new String(".txt"))
                        && MenuController.isChoseText()) || (path.substring(path.length() - 4, path.length()).equalsIgnoreCase(new String(".JPG"))
                        && MenuController.isChoseImage()) || MenuController.isChoseOther())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Your're trying to load invalid file. Try Again.");
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
                } else {
                    /*This code executes when the chosen file don't exists.*/
                    File f = new File(path);
                    if (!f.exists()) {
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
                    if (nextButton.isVisible() == true && goNext) {
                        //This code executes, when everything's OK.
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen5.fxml"));
                        Pane pane = null;
                        try {
                            pane = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        EncryptScreen5Controller encryptSc5 = loader.getController();
                        encryptSc5.setMainController(mainController);
                        mainController.setScreen(pane);
                    }

                }
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Your're trying to load invalid file. Try Again.");
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
        }
    }
    /*The same like in the previous screen.*/
    public void getTextByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            getText();
        }
    }
    /*The same like in the previous screen.*/
    public void repeat() {
        if (wrongButton.isVisible() == true) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen3.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            EncryptScreen3Controller encryptSc3 = loader.getController();
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
    /*This method allows the user to choose the file by the FileChooser.*/
    @FXML
    public void browse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to encryptText");
        File toEncrypt = fileChooser.showOpenDialog(Main.getStage());
        String pathOfEncrypted = toEncrypt.getAbsolutePath();
        textLabel.setText(pathOfEncrypted);
        chooseFile = true;
    }
    /*The same like in the previous screen.*/
    @FXML
    public void back() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen2.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EncryptScreen2Controller menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }
}
