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

/*This controllers controls the second screen of getting data from user.
  It's used only, when the user chose manually as a way to enter the private key.*/

public class DecryptionScreen2Controller {
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
    public static String DliczbaE;

    /*In initialize method the background of some nodes is changed.*/
    public void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }
    /*very similar to the method in the first decrypt screen.*/
    public void getText() {
        if (nextButton.isVisible() == true) {
            DliczbaE = textLabel.getText();
            DliczbaE = DliczbaE.trim();
            Pattern pat = Pattern.compile("\\D");
            Matcher mat = pat.matcher(DliczbaE);
            if (mat.find()) {
                //executes, when user put other data, than only numbers.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid data. Please enter a number.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen2.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    DecryptionScreen2Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            } else {
                //if everything's OK, the application changes the screen.
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
    }
    //the same, like in the first decrypt screen.
    public void getTextByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            getText();
        }
    }

    public void repeat() {
        if (wrongButton.isVisible() == true) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen2.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DecryptionScreen2Controller encryptSc2 = loader.getController();
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

    //the same like in the first decrypt screen.
    @FXML
    public void back() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen1.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecryptionScreen1Controller menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }
}
