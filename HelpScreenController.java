package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*This controller controls the 'Help' screen*/

public class HelpScreenController {
    @FXML
    private TextArea menuText;
    @FXML
    private ChoiceBox helpMenu;

    private MainController mainController;
    @FXML
    private Button MenuButton;

    /*This method reads the text file and converts it nto a simple String.*/
    private String readFile(File file) {
        String result = "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(file)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            s += "\n";
            result += s;
        }
        return result;
    }

    /*In Initialize method this controller listens to changes in ChoiceBox.*/
    @FXML
    void initialize() {
        menuText.setWrapText(true);
        helpMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            //By every change of value, the menuText
            //Changes it's content.
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue.equals(new String("errors"))) {
                    String s = null;
                    try {
                        s = readFile(new File("src\\application\\help\\errors.txt"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    menuText.setText(s);
                } else if (newValue.equals(new String("about algorithm"))) {
                    String s = null;
                    try {
                        s = readFile(new File("src\\application\\help\\aboutAlghoritm.txt"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    menuText.setText(s);
                } else if (newValue.equals(new String("general"))) {
                    String s = null;
                    try {
                        s = readFile(new File("src\\application\\help\\general.txt"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    menuText.setText(s);
                } else if (newValue.equals(new String("load File"))) {
                    String s = null;
                    try {
                        s = readFile(new File("src\\application\\help\\loadFile.txt"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    menuText.setText(s);
                } else if (newValue.equals(new String("Author"))) {
                    String s = null;
                    try {
                        s = readFile(new File("src\\application\\help\\Author.txt"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    menuText.setText(s);
                }
            }
        });
    }

    /*Method that handles the click ont the 'Back' button.*/
    public void backMenu() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/Menu.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuController menuContr = loader.getController();
        menuContr.setMainController(mainController);
        mainController.setScreen(pane);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
