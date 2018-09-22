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

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*This second screen is optional and allows to enter the second number of
  the private key by the user, if the user chose manually entering the key.*/

public class EncryptScreen2Controller {
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
    public static String liczbaE;

    /*This method sets the background color of some nodes.*/
    public void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    /*Main method, which collects the data from the user.*/
    public void getText() {
        if (nextButton.isVisible() == true) {
            liczbaE = textLabel.getText();
            liczbaE = liczbaE.trim();
            Pattern pat = Pattern.compile("\\D");
            Matcher mat = pat.matcher(liczbaE);
            if (mat.find()) {
                //This code executes, when the user enter something
                //different data than the number.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid data. You must enter a number.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    //repeating the entering screen.
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen2.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    EncryptScreen2Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }

            } else {
                //This code executes, when everything's OK.
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
    }

    /*This method allows the user to enter the data by the enter key.*/
    public void getTextByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            getText();
        }
    }

    /*This method allows to repeat the screen, when something happens wrong.*/
    public void repeat() {
        if (wrongButton.isVisible() == true) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen2.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            EncryptScreen2Controller encryptSc2 = loader.getController();
            encryptSc2.setMainController(mainController);
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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen1.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EncryptScreen1Controller encryptScreen1Controller = loader.getController();
        encryptScreen1Controller.setMainController(mainController);
        mainController.setScreen(pane);
    }
}
