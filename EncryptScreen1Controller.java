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

/*This controller gets the first information from the user about encrypting.
  Information about the public key, by which way the user wants to load the key
  and, of course, the key or part of the key*/

public class EncryptScreen1Controller {
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
    private ChoiceBox chooseLoadKey;
    @FXML
    private Button browseButton;
    //boolean, which has information about way, which user wants to enter the key.
    private static boolean choseLoadByFile = false;
    /*Method, which handles loading the public key from a file.*/
    private void loadFromFile() {
        label.setText("         Choose File");
        browseButton.setVisible(true);
        choseLoadByFile = true;
    }
    /*Method, which handles the file choosing*/
    @FXML
    void browse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to encryptText");
        File toEncrypt = fileChooser.showOpenDialog(Main.getStage());
        String pathOfEncrypted = toEncrypt.getAbsolutePath();
        textLabel.setText(pathOfEncrypted);
    }
    /*the initialize method changes the background of some nodes and adds
      the ChangeListener to the ChoiceBox to handle the choose from the user.*/
    public void initialize() {
        label.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        askLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        wrongLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        browseButton.setVisible(false);
        chooseLoadKey.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue.equals(new String("load text file"))) {
                    loadFromFile();
                } else if (newValue.equals(new String("manually"))) {
                    choseLoadByFile = false;
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
            }
        });
    }
    /*data gotten from the user*/
    public static String liczbaN;
    private static BigInteger liczbaN1;
    private static BigInteger liczbaE;

    public static boolean isChoseLoadByFile() {
        return choseLoadByFile;
    }

    public static BigInteger getLiczbaN() {
        return liczbaN1;
    }

    public static BigInteger getLiczbaE() {
        return liczbaE;
    }
    /*Main method, which collects data from user.*/
    public void getText() {
        if (isChoseLoadByFile()) {
            //this code executes, when the user chose entering the key by the File.
            try {
                String[] stringArray = ReadingFile.read(new File(textLabel.getText())).split("n=|e=|d=");
                liczbaN1 = new BigInteger(stringArray[1]);
                liczbaE = new BigInteger(stringArray[2]);
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen3.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EncryptScreen3Controller encrypt2c = loader.getController();
                encrypt2c.setMainController(mainController);
                mainController.setScreen(pane);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                //This code executes, when any error occurs.
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText(null);
                alert1.setContentText("Your're trying to load invalid file. Check 'Help' section.");
                Optional<ButtonType> result1 = alert1.showAndWait();
                if (result1.get() == ButtonType.OK) {
                    choseLoadByFile = false;
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen1.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    EncryptScreen1Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
                    mainController.setScreen(pane);
                }
            }
        } else {
            if (nextButton.isVisible() == true) {
                /*This code executes, when the user chose entering the key
                  manually.*/
                liczbaN = textLabel.getText();
                liczbaN = liczbaN.trim();
                Pattern pat = Pattern.compile("\\D");
                Matcher mat = pat.matcher(liczbaN);
                if (mat.find()) {
                    //This code executes when any error occurs.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid data. You must enter a number.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen1.fxml"));
                        Pane pane = null;
                        try {
                            pane = loader.load();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        EncryptScreen1Controller encrypt2c = loader.getController();
                        encrypt2c.setMainController(mainController);
                        mainController.setScreen(pane);
                    }


                } else {
                    //this code executes when everything's OK.
                    System.out.println("lol");
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen2.fxml"));
                    Pane pane = null;
                    try {
                        pane = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EncryptScreen2Controller encrypt2c = loader.getController();
                    encrypt2c.setMainController(mainController);
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
    /*This method loads the same screen, when something happens wrong.*/
    public void repeat() {
        if (wrongButton.isVisible() == true) {
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
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    /*This method allows user to enter the data by the enter key.*/
    public void repeatByEnter(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            repeat();
        }
    }

    /*This method loads the previous screen.*/
    @FXML
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

