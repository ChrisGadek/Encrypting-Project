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

/*This controller allows the user to enter the name for the encrypted file.*/

public class EncryptScreen5Controller {
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
    public static String name;
    /*This method sets the background of some nodes.*/
    public void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    /*Main method, which collects data from the user.*/
    public void getText() throws IOException {
        if (nextButton.isVisible() == true) {
            name = textLabel.getText();
            name = name.trim();
            //converts the path to the file.
            Pattern pat = Pattern.compile("%20");
            Matcher mat = pat.matcher(path1);
            if (mat.find()) {
                path1 = path1.replace("%20", " ");
            }
            String lolek = path1.substring(0, path1.length() - 16);
            lolek += "/Encrypted";
            File theDir = new File(lolek);
            // if the directory does not exist, create it
            if (!theDir.exists()) {
                System.out.println("creating directory: " + theDir.getName());
                boolean result = false;
                try {
                    theDir.mkdir();
                    result = true;
                } catch (SecurityException se) {
                    //handle it
                }
                if (result) {
                    System.out.println("DIR created");
                }
            }
            File testFile = new File(lolek + "/" + name + ".txt");
            if (testFile.createNewFile()) {
                testFile.delete();
            } else {
                //executes, when the file already exists.
                nextButton.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The file already exists");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen5.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    EncryptScreen5Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            }

            if (nextButton.isVisible() == true) {
                //If everything's OK, this codes executes.
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen4.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EncryptScreen4Controller encryptSc4 = loader.getController();
                encryptSc4.setMainController(mainController);
                mainController.setScreen(pane);
            }
        }
    }

    /*The same like int he previous screen.*/
    public void getTextByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            try {
                getText();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*The same like in the previous screen.*/
    public void repeat() {
        if (wrongButton.isVisible() == true) {
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

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void repeatByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            repeat();
        }
    }

    /*The same like in the previous screen.*/
    @FXML
    public void back() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen3.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EncryptScreen3Controller menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }
}
