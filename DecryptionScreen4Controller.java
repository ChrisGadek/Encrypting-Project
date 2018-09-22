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

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*This controller controls the third (or fourth) step of decrypting process.
  Gets from the user information about the name of decrypted file.*/

public class DecryptionScreen4Controller {
    File thisOne = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    String path1 = thisOne.getPath();
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
    public static String Dname;
    //the same like in the previous screen.
    public void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }
    /*method, which gets information about the name of decrypted file.*/
    public void getText() throws IOException {
        if (nextButton.isVisible() == true) {
            Dname = textLabel.getText();
            Dname = Dname.trim();
            //converts the path to the right format.
            Pattern pat = Pattern.compile("%20");
            Matcher mat = pat.matcher(path1);
            System.out.println(mat.find());
            if (mat.find()) {
                path1 = path1.replace("%20", " ");
            }
            System.out.println(path1);
            //gets the path of directory, where the application already is.
            //Creates the new directory, where the decrypted files will be stored.
            String lolek = path1.substring(0, path1.length() - 16);
            lolek += "/Decrypted";
            System.out.println(lolek);
            File theDir = new File(lolek);
            // if the directory does not exist, create it
            if (!theDir.exists()) {
                try {
                    theDir.mkdir();
                } catch (SecurityException se) {
                    se.printStackTrace();
                }
            }
            File testFile = new File(lolek + "//" + Dname + ".txt");
            //test, if the name of the new file was already chosen.
            if (testFile.createNewFile()) {
                testFile.delete();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("File already exist");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen4.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    DecryptionScreen4Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            }
            if (nextButton.isVisible() == true) {
                //executes, where everything's OK.
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen5.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DecryptionScreen5Controller encryptSc4 = loader.getController();
                encryptSc4.setMainController(mainController);
                mainController.setScreen(pane);
            }
        }
    }

    public void getTextByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            try {
                getText();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    //the same like in the previous screen.
    public void repeat() {
        if (wrongButton.isVisible() == true) {
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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen3.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecryptionScreen3Controller menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }
}
