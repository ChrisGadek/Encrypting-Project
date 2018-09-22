package application;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Final controller, which collects the data from previous controllers
  and starts the decryption process.*/

public class DecryptionScreen5Controller {
    private static Thread thread;

    public static Thread getThread() {
        return thread;
    }

    //the same like in the previous screen.
    @FXML
    void initialize() {
        timeLabel.setText("Average time Left: ");
        progressLabel.setText("Progress: ");
        succesLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        timeLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        process1.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        progressLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    @FXML
    private Label progressLabel;
    @FXML
    private Label timeLabel;

    private MainController mainController;
    @FXML
    private Label succesLabel;
    @FXML
    private Button succesButton;
    @FXML
    private Button startButton;
    @FXML
    private ProgressBar process;
    @FXML
    private ProgressIndicator process1;

    /*Main method, which implements the RSA algorithm.*/
    public void startEn() throws IOException {
        /*decrypting process could take a lot of time, so
          it's implemented in other thread.*/
        Task<Void> task = new Task() {
            @Override
            protected Void call() throws Exception {
                thread = Thread.currentThread();
                //first, the method collect data from previous screens.
                updateTitle("Progress: processing data");
                BigInteger N;
                BigInteger D;
                if (DecryptionScreen1Controller.isLoadFile()) {
                    N = DecryptionScreen1Controller.getLiczbaN();
                    D = DecryptionScreen1Controller.getLiczbaD();
                } else {
                    N = new BigInteger(DecryptionScreen1Controller.DliczbaN);
                    D = new BigInteger(DecryptionScreen2Controller.DliczbaE);
                }
                String path = DecryptionScreen3Controller.Dpath;
                String name = DecryptionScreen4Controller.Dname;
                File thisFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                String thisFile1 = thisFile.getAbsolutePath();
                //converts the path to the right format without '%20'
                Pattern pat = Pattern.compile("%20");
                Matcher mat = pat.matcher(thisFile1);
                System.out.println(mat.find());
                if (mat.find()) {
                    thisFile1 = thisFile1.replace("%20", " ");
                }
                String lolek = thisFile1.substring(0, thisFile1.length() - 15);
                //gets the path to the directory, where decrypted files are stored.
                lolek += "/Decrypted";
                if (MenuController.isChoseText()) {
                    //uses the method Algorithm.decryptTextFromAFile to decrypt the text.
                    try {
                        File plik = new File(lolek + "//" + name + ".txt");
                        plik.createNewFile();
                        Scanner test = new Scanner(new BufferedReader(new FileReader(new File(path))));
                        List<String> lista = new ArrayList<>();
                        while (test.hasNextLine()) {
                            String s = test.nextLine();
                            if (!s.startsWith("n=")) {
                                lista.add(s);
                            }
                        }
                        test.close();
                        int i = 0;
                        List<BigInteger> big = new ArrayList<>();
                        for (String s : lista) {
                            big.add(Algorithm.getDecompressed(s));
                        }
                        ArrayList<BigInteger> dane = new ArrayList<>();
                        List<BigInteger> test1 = new ArrayList<>();
                        //variables used by the system, which measures the decrypting time.
                        double percentage;
                        double id = 0;
                        double dlugosc = lista.size();
                        Instant start = null;
                        Instant end = null;
                        final double[] sec = {0};
                        final int[] minu = {0};
                        for (BigInteger bi : big) {
                            if (id == 0) {
                                start = Instant.now();
                                updateTitle("Progress: decryption started");
                            }
                            if (id % 7 == 0) {
                                //here the system measures the time.
                                sec[0] = 0;
                                end = Instant.now();
                                String timeNotConv = Duration.between(start, end).toString();
                                Pattern pat1 = Pattern.compile("\\dM");
                                Matcher mat1 = pat1.matcher(timeNotConv);
                                if (mat1.find()) {
                                    int index = timeNotConv.indexOf("M");
                                    int index2 = timeNotConv.indexOf("T");
                                    int index3 = timeNotConv.indexOf("S");
                                    int m = Integer.valueOf(timeNotConv.substring(index2 + 1, index));
                                    double se = Double.valueOf(timeNotConv.substring(index + 1, index3));
                                    sec[0] = 60 * m + se;
                                } else {
                                    String time = Duration.between(start, end).toString().substring(2, Duration.between(start, end).toString().length() - 1);
                                    sec[0] = Double.valueOf(time);
                                }
                                double min = sec[0];
                                sec[0] /= id + 1;
                                sec[0] *= lista.size();
                                sec[0] -= min;
                                minu[0] = (int) sec[0] / 60;
                                sec[0] %= 60;
                            }
                            if (id > 2000) {
                                updateMessage("Average time left: " + minu[0] + " minutes " + (int) sec[0] + " seconds ");
                            }
                            id++;
                            //these operation decrypts the information:
                            /*->*/
                            BigInteger lol1 = bi.modPow(D, N);
                            percentage = id / dlugosc;
                            dane.add(lol1);
                        }
                        char[] znaki = new char[dane.size()];
                        int i0 = 0;
                        for (BigInteger bi : dane) {
                            znaki[i0] = (char) bi.intValue();
                            i0++;
                        }
                        String odsz = "";
                        for (char c : znaki) {
                            odsz += c;
                        }
                        FileWriter fw = new FileWriter(plik.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(odsz);
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (MenuController.isChoseImage() || MenuController.isChoseOther()) {
                    //executes it's own version of algorithm
                    String extension = "";
                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(new BufferedReader(new FileReader(new File(path))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<String> gotten = new ArrayList<>();
                    int lo = 0;
                    //reads encrypted bits from the file
                    while (scanner.hasNextLine()) {
                        String s = scanner.nextLine();
                        if (!s.startsWith("n=") && !(s.length() == 4 && s.startsWith(".") && lo == 0)) {
                            gotten.add(s);
                        }
                        //gets the extension of the encrypted file if it isn't known.
                        if ((s.length() == 4 || s.length() == 5) && s.startsWith(".") && lo == 0) {
                            extension = s;
                        }
                        lo++;
                    }
                    scanner.close();
                    //gets information, if the encrypted bit was negative or positive.
                    List<String> negId = new ArrayList<>();
                    for (int b = 0; b < gotten.size(); b++) {
                        negId.add(gotten.get(b).substring(gotten.get(b).length() - 5, gotten.get(b).length()));
                        gotten.set(b, gotten.get(b).substring(0, gotten.get(b).length() - 5));
                    }
                    int i = 0;
                    List<BigInteger> bi = new ArrayList<>();
                    double percentage;
                    double id = 0;
                    double dlugosc = gotten.size();
                    for (String s : gotten) {
                        bi.add(Algorithm.getDecompressed(s));
                    }
                    List<BigInteger> decrypted = new ArrayList<>();
                    //here the method begins to implement the measuring time of remaining decryption.
                    Instant start = null;
                    Instant end = null;
                    final double[] sec = {0};
                    final int[] minu = {0};
                    for (BigInteger b : bi) {
                        if (id == 0) {
                            start = Instant.now();
                            updateTitle("Progress: decryption started");
                        }
                        if (id % 7 == 0) {
                            //every 7 cycles this algorithm measures time between
                            //these cycles and converts it into a minutes and seconds.
                            sec[0] = 0;
                            end = Instant.now();
                            String timeNotConv = Duration.between(start, end).toString();
                            Pattern pat1 = Pattern.compile("\\dM");
                            Matcher mat1 = pat1.matcher(timeNotConv);
                            if (mat1.find()) {
                                int index = timeNotConv.indexOf("M");
                                int index2 = timeNotConv.indexOf("T");
                                int index3 = timeNotConv.indexOf("S");
                                int m = Integer.valueOf(timeNotConv.substring(index2 + 1, index));
                                double s = Double.valueOf(timeNotConv.substring(index + 1, index3));
                                sec[0] = 60 * m + s;
                            } else {
                                String time = Duration.between(start, end).toString().substring(2, Duration.between(start, end).toString().length() - 1);
                                sec[0] = Double.valueOf(time);
                            }
                            double min = sec[0];
                            sec[0] /= id + 1;
                            sec[0] *= gotten.size();
                            sec[0] -= min;
                            minu[0] = (int) sec[0] / 60;
                            sec[0] %= 60;
                        }
                        if (id > 2000) {
                            //after 2000 cycles the measuring mistake is so small, that
                            //the application can show it to the user
                            updateMessage("Average time left: " + minu[0] + " minutes " + (int) sec[0] + " seconds ");
                        }
                        id++;
                        //here the aplication decrypts the information
                        BigInteger lol1 = b.modPow(D, N);
                        percentage = id / dlugosc;
                        updateProgress(id, dlugosc);
                        decrypted.add(lol1);
                    }
                    byte[] decryptedBytes = new byte[decrypted.size()];
                    int id1 = 0;
                    for (BigInteger b : decrypted) {
                        decryptedBytes[id1] = b.byteValue();
                        id1++;
                    }
                    //here the application changes these bits, which where written as 'negative'.
                    int id2 = 0;
                    for (String s : negId) {
                        switch (s) {
                            case "ngtv0": {
                                decryptedBytes[id2] -= 127;
                                id2++;
                                break;
                            }
                            case "ngtv1": {
                                decryptedBytes[id2] -= 128;
                                id2++;
                                break;
                            }
                            case "ngtv2": {
                                decryptedBytes[id2] -= 130;
                                id2++;
                                break;
                            }
                            default: {
                                id2++;
                                break;
                            }
                        }
                    }
                    if (MenuController.isChoseImage()) {
                        //here the application creates an image from decrypted bits.
                        try {
                            ByteArrayInputStream bis = new ByteArrayInputStream(decryptedBytes);
                            BufferedImage bImage2 = ImageIO.read(bis);
                            ImageIO.write(bImage2, "jpg", new File(lolek + "//" + name + ".jpg"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (MenuController.isChoseOther()) {
                        try {
                            Files.write(new File(lolek + "//" + name + extension).toPath(), decryptedBytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                succesLabel.setVisible(true);
                succesButton.setVisible(true);
                updateProgress(0, 0);
                updateTitle("Progress: finished");
                return null;
            }
        };
        progressLabel.textProperty().bind(task.titleProperty());
        timeLabel.textProperty().bind(task.messageProperty());
        process1.progressProperty().bind(task.progressProperty());
        process.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    //the same like in the previous screen.
    public void backToMenu() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/Menu.fxml"));

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuController menContr = loader.getController();
        menContr.setMainController(mainController);
        mainController.setScreen(pane);
    }

    //the same like in the previous screen.
    @FXML
    public void back() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen4.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecryptionScreen4Controller menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    //the same like in the previous screen.
    public void repeat() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/DecryptionScreen5.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecryptionScreen5Controller menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }
}
