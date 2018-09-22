package application;

/*The final controller, which generates the private and public key
  and writes them in to a text file.*/

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateAKeyScreen3Controller {
    private MainController mainController;
    @FXML
    private Label succesLabel;
    @FXML
    private Button startButton;
    @FXML
    private Label startLabel;
    @FXML
    private Button succesButton;
    @FXML
    private ProgressBar keyProgressBar;

    /*Main method, which implements the key generating process.*/
    public void start() throws IOException, InterruptedException {
        new Thread(new Runnable() {
            /*Is process could be very long, so it was
              implemented in other Thread.*/
            @Override
            public void run() {
                succesLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                startLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                startLabel.setVisible(true);
                //the mathematical process of generating a key was
                //implemented there:
                ArrayList<BigInteger> listOfKeys = Algorithm.generateAKey(GenerateAKeyScreenController.bity, keyProgressBar);
                //collects the data.
                String Key = "n=" + listOfKeys.get(0) + "\n";
                String Key1 = "e=" + listOfKeys.get(1) + "\n";
                String Key2 = "d=" + listOfKeys.get(2);
                File thisFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                String thisFile1 = thisFile.getAbsolutePath();
                String lolek = thisFile1.substring(0, thisFile1.length() - 16);
                lolek += "/keys";
                //converts the file's path in to the right format.
                Pattern pat = Pattern.compile("%20");
                Matcher mat = pat.matcher(thisFile1);
                if (mat.find()) {
                    thisFile1 = thisFile1.replace("%20", " ");
                }
                File keyFile = new File(lolek + "/" + GenerateAKeyScreen1Controller.KPath + ".txt");
                try {
                    keyFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileWriter fw = null;
                try {
                    fw = new FileWriter(keyFile.getAbsoluteFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BufferedWriter bw = new BufferedWriter(fw);
                try {
                    //writes the keys in to a text file.
                    bw.write(Key);
                    bw.newLine();
                    bw.write(Key1);
                    bw.newLine();
                    bw.write(Key2);
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                succesLabel.setVisible(true);
                succesButton.setVisible(true);
            }
        }).start();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    /*loads the menu screen.*/
    public void toMenu() {
        if (succesButton.isVisible()) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/Menu.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            MenuController menu = loader.getController();
            menu.setMainController(mainController);
            mainController.setScreen(pane);
        }
    }
}