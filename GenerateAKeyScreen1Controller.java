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

/*This controller collects the name of the file with the new key from the user.*/

public class GenerateAKeyScreen1Controller {
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
    public static String KPath;

    /*This method sets background of some nodes.*/
    public void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    //the main method that collects the data from the user.
    public void getText() throws IOException {
        if (nextButton.isVisible() == true) {
            KPath = textLabel.getText();
            KPath = KPath.trim();
            Pattern pat = Pattern.compile("%20");
            Matcher mat = pat.matcher(path1);
            if (mat.find()) {
                path1 = path1.replace("%20", " ");
            }
            String lolek = path1.substring(0, path1.length() - 16);
            lolek += "/keys";
            System.out.println(lolek);
            File theDir = new File(lolek);
            // if the directory does not exist, create it
            if (!theDir.exists()) {
                System.out.println("creating directory: " + theDir.getName());
                try {
                    theDir.mkdir();
                } catch (SecurityException se) {
                    se.printStackTrace();
                }
            }
            File testFile = new File(lolek + "//" + KPath + ".txt");
            if (testFile.createNewFile()) {
                testFile.delete();
            } else {
                nextButton.setVisible(false);
                //executes, when the file already exists.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The file already exists");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/GenerateAKeyScreen1.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    GenerateAKeyScreen1Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            }

            if (nextButton.isVisible() == true) {
                //executes, when everything's OK.
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/GenerateAKeyScreen2.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GenerateAKeyScreen3Controller encryptSc4 = loader.getController();
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

    /*loads the same screen.*/
    public void repeat() {
        if (wrongButton.isVisible() == true) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/GenerateAKeyScreen1.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GenerateAKeyScreen1Controller encryptSc5 = loader.getController();
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
}

