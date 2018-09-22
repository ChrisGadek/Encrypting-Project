package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.math.BigInteger;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*This controller controls the first screen of getting data from the user.
  This data is needed to decrypt information. */
public class DecryptionScreen1Controller {
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
    @FXML
    private Label askLabel;
    @FXML
    private ChoiceBox chooseBox;
    @FXML
    private Button browseButton;
    /*This boolean informs, which way to load the data chose the user.*/
    private static boolean loadFile = false;
    public static String DliczbaN;

    /*void initialize changes background of some nodes and adds ChangeListener
      to ChoiceBox, to get information how user wants to load private key. Manually, or by
       loading a text file.*/
    @FXML
    void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        askLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        browseButton.setVisible(false);
        chooseBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue.equals(new String("load text file"))) {
                    label.setText("         Choose File");
                    browseButton.setVisible(true);
                } else {
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen1.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DecryptionScreen1Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            }
        });
    }

    /*Method, which handles choosing file to load a private key.*/
    @FXML
    void browse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to decrypt");
        File toEncrypt = fileChooser.showOpenDialog(Main.getStage());
        String pathOfEncrypted = toEncrypt.getAbsolutePath();
        textLabel.setText(pathOfEncrypted);
        loadFile = true;
    }

    public static boolean isLoadFile() {
        return loadFile;
    }

    /*This data is collected from user. 'liczbaD' and 'liczbaN' are used in
      decryption as a private key.*/
    private static BigInteger liczbaD;
    private static BigInteger liczbaN;

    public static BigInteger getLiczbaD() {
        return liczbaD;
    }

    public static BigInteger getLiczbaN() {
        return liczbaN;
    }

    /*Main method, starts when 'next' button is pressed. It collects data from the file, or
      from the text field, depends which way to load the data chose the user.*/
    @FXML
    public void getText() {
        if (isLoadFile()) {
            //this code executes, when user chose to load the key by the text file.
            try {
                String[] stringArray = ReadingFile.read(new File(textLabel.getText())).split("n=|e=|d=");
                liczbaN = new BigInteger(stringArray[1]);
                liczbaD = new BigInteger(stringArray[3]);
                for (String s : stringArray) {
                    System.out.println(s);
                }
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen3.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DecryptionScreen3Controller encrypt2c = loader.getController();
                encrypt2c.setMainController(mainController);
                mainController.setScreen(pane);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
				/*If every exception is thrown there, this code
				  handles it.*/
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Your're trying to load invalid file. Check 'Help' section.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    loadFile = false;
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen1.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    DecryptionScreen1Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            }
        }
        //this code executes when user chose to load the key manually.
        else {
            if (nextButton.isVisible() == true) {
                DliczbaN = textLabel.getText();
                DliczbaN = DliczbaN.trim();
                Pattern pat = Pattern.compile("\\D");
                Matcher mat = pat.matcher(DliczbaN);
                if (mat.find()) {
                    /*This code executes, when user puts in the text field
                      some other data than the numbers, which is wrong of course.
                      Private key contains only numbers.*/
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid data. Please enter a number.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen1.fxml"));
                        Pane pane = null;
                        try {
                            pane = loader.load();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        DecryptionScreen1Controller encrypt2c = loader.getController();
                        encrypt2c.setMainController(mainController);
                        mainController.setScreen(pane);
                    }
                } else {
                    /*If everything's alright, the applications sets the
                      screen to allow user put the second part of the private key. */
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen2.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DecryptionScreen2Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            }
        }
    }
    /*This method allows user to confirm input also by the enter key.*/
    public void getTextByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            getText();
        }
    }
    /*This method restarts the screen.*/
    public void repeat() {
        if (wrongButton.isVisible() == true) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen1.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DecryptionScreen1Controller decryptSc1 = loader.getController();
            decryptSc1.setMainController(mainController);
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

    @FXML
    /*This method returns to the Menu screen, when the button 'back' is clicked.*/
    public void back() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/Menu.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuController menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }
}
