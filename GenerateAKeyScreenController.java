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

/*This controller collects from the user the length of the key (in bits).*/

public class GenerateAKeyScreenController {
    private MainController mainController;
    @FXML
    private TextField textField;
    @FXML
    private Button goodButton;
    @FXML
    private Label wrongLabel;
    @FXML
    private Button wrongButton;
    @FXML
    private Label goodLabel;
    @FXML
    private Label label;

    /*This method sets the background of some nodes.*/
    public void initialize() {
        goodLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    static String liczbaN;
    public static int bity;

    /*This method collects the data from the user.*/
    public void getText() {
        if (goodButton.isVisible() == true) {
            liczbaN = textField.getText();
            liczbaN = liczbaN.trim();
            Pattern pat = Pattern.compile("\\D");
            Matcher mat = pat.matcher(liczbaN);
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
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/GenerateAKeyScreen.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    GenerateAKeyScreenController encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }

            } else {
                //executes, when everything's OK.
                bity = Integer.valueOf(liczbaN);
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/GenerateAKeyScreen1.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GenerateAKeyScreen1Controller encrypt2c = loader.getController();
                encrypt2c.setMainController(mainController);
                mainController.setScreen(pane);
            }
        }
    }

    public void getTextByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            getText();
        }
    }

    /*Loads the same screen*/
    public void repeat() {
        if (wrongButton.isVisible() == true) {
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