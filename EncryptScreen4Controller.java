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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*This final controller controls the process of encrypting.*/

public class EncryptScreen4Controller {
    private static Thread thread;

    public static Thread getThread() {
        return thread;
    }

    private static byte[] testOfBytes;
    private static ArrayList<String> testOfGotten = new ArrayList<>();

    public static ArrayList<String> getTestOfGotten() {
        return testOfGotten;
    }

    public static byte[] getTestOfBytes() {
        return testOfBytes;
    }

    /*This method sets the background of some nodes.*/
    @FXML
    public void initialize() {
        timeLabel.setText("Average Time left: ");
        progressLabel.setText("Progress: ");
        succesLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        process1.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        timeLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        progressLabel.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
    }

    private MainController mainController;
    @FXML
    private Label progressLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label succesLabel;
    @FXML
    private Button succesButton;
    @FXML
    private Label startLabel;
    @FXML
    private ProgressBar progress;
    @FXML
    private ProgressIndicator process1;

    /*Main method, which controls the process of encrypting.*/
    public void startEn() throws IOException {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            /*The encrypting process is very long, so it was
                          implemented in other thread.*/
                thread = Thread.currentThread();
                BigInteger N;
                BigInteger E;
                updateTitle("Progress: loading data");
                if (EncryptScreen1Controller.isChoseLoadByFile()) {
                    N = EncryptScreen1Controller.getLiczbaN();
                    E = EncryptScreen1Controller.getLiczbaE();
                } else {
                    N = new BigInteger(EncryptScreen1Controller.liczbaN);
                    E = new BigInteger(EncryptScreen2Controller.liczbaE);
                }
                String path = EncryptScreen3Controller.path;
                String name = EncryptScreen5Controller.name;
                String extension = path.substring(path.indexOf("."), path.length());
                File thisFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                String thisFile1 = thisFile.getAbsolutePath();
                //converts the path to the right format.
                Pattern pat = Pattern.compile("%20");
                Matcher mat = pat.matcher(thisFile1);
                if (mat.find()) {
                    thisFile1 = thisFile1.replace("%20", " ");
                }
                String lolek = thisFile1.substring(0, thisFile1.length() - 16);
                lolek = lolek + "/Encrypted";
                if (MenuController.isChoseText()) {
                    //the process of encrypting implemented for the text data.
                    try {
                        String doZaszyfrowania = ReadingFile.read(new File(path));
                        File plik = new File(lolek + "//" + name + ".txt");
                        plik.createNewFile();
                        FileWriter fw = new FileWriter(plik.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);
                        ArrayList<BigInteger> lista = new ArrayList<>();
                        //these variables are used by the system, which measures the encrypting time.
                        double percentage;
                        double id = 0;
                        char[] znaki = doZaszyfrowania.toCharArray();
                        double dlugosc = znaki.length;
                        Instant start = null;
                        Instant end = null;
                        double sec = 0;
                        int minu = 0;
                        //here encryption begins.
                        for (char c : znaki) {
                            if (id == 0) {
                                start = Instant.now();
                                updateTitle("Progress: encryption started");
                            }
                            if (id % 7 == 0) {
                                //here the system measures the encrypting time
                                sec = 0;
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
                                    sec = 60 * m + se;
                                } else {
                                    String time = Duration.between(start, end).toString().substring(2, Duration.between(start, end).toString().length() - 1);
                                    sec = Double.valueOf(time);
                                }
                                double min = sec;
                                sec /= id + 1;
                                sec *= znaki.length;
                                sec -= min;
                                minu = (int) sec / 60;
                                sec %= 60;
                            }
                            if (id > 2000) {
                                updateMessage("Average Time left: " + minu + " minutes " + (int) sec + " seconds ");
                            }
                            final double id1[] = {0};
                            id++;
                            //these operations encrypt the information:
                            percentage = id / dlugosc;
                            long a = (long) c;
                            BigInteger d = BigInteger.valueOf(a);
                            /* -> */
                            BigInteger szyfr = d.modPow(E, N);
                            updateProgress(id, dlugosc);
                            lista.add(szyfr);
                        }

                        String liczbaN = N.toString();
                        //method creates a new file and writes every encrypted character into a file.
                        liczbaN += "\n";
                        bw.write("n=" + liczbaN);
                        List<String> compress = new ArrayList<>();
                        for (BigInteger b : lista) {
                            compress.add(Algorithm.getCompressed(b));
                        }
                        id = 0;
                        start = null;
                        end = null;
                        for (String bi : compress) {
                            if (id == 0) {
                                start = Instant.now();
                                updateTitle("Progress: writing data into a file");
                            }
                            if (id % 7 == 0) {
                                //here the system measures the encrypting time
                                sec = 0;
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
                                    sec = 60 * m + se;
                                } else {
                                    String time = Duration.between(start, end).toString().substring(2, Duration.between(start, end).toString().length() - 1);
                                    sec = Double.valueOf(time);
                                }
                                double min = sec;
                                sec /= id + 1;
                                sec *= znaki.length;
                                sec -= min;
                                minu = (int) sec / 60;
                                sec %= 60;
                            }
                            if (id > 2000) {
                                updateMessage("Average Time left: " + minu + " minutes " + (int) sec + " seconds ");
                            }
                            bw.write(bi);
                            bw.newLine();
                        }
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (MenuController.isChoseImage() || MenuController.isChoseOther()) {
                    //the process of encrypting implemented for other type of data.
                    byte[] bytes = Algorithm.getBytes(new File(path));
                    testOfBytes = bytes;
                    List<BigInteger> listOfEnc = new ArrayList<>();
                    double id = 0;
                    double percentage;
                    double dlugosc = bytes.length;
                    ArrayList<String> neg = new ArrayList<>();
                    //the implementation of time measuring system starts here.
                    Instant start = null;
                    Instant end = null;
                    final double[] sec = {0};
                    final int[] minu = {0};
                    for (byte b : bytes) {
                        if (id == 0) {
                            start = Instant.now();
                            updateTitle("Progress: encryption started");
                        }
                        if (id % 7 == 0) {
                            //in this loop, the time between the start and stop references
                            //is beeing measured after every 7 loop repeatings and updated.
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
                            sec[0] *= bytes.length;
                            sec[0] -= min;
                            minu[0] = (int) sec[0] / 60;
                            sec[0] %= 60;
                        }
                        if (id > 2000) {
                            updateMessage("Average Time: " + minu[0] + " minutes " + (int) sec[0] + " seconds");
                        }
                        if (b <= 0 && b > -127) {
                            neg.add((int) id, "ngtv0");
                            b += 127;
                        } else if (b == -127) {
                            neg.add((int) id, "ngtv1");
                            b += 128;
                        } else if (b == -128) {
                            neg.add((int) id, "ngtv2");
                            b += 130;
                        } else if (b > 0) {
                            neg.add((int) id, "pls00");
                        }
                        percentage = id / dlugosc;
                        long a = (long) b;
                        BigInteger d = BigInteger.valueOf(a);
                        //here begins the process of encrypting:
                        BigInteger szyfr = d.modPow(E, N);
                        updateProgress(id, dlugosc);
                        listOfEnc.add(szyfr);
                        id++;
                    }
                    BufferedWriter bw = null;
                    try {
                        File plik = new File(lolek + "//" + name + ".txt");
                        plik.createNewFile();
                        FileWriter fw = new FileWriter(plik.getAbsoluteFile());
                        bw = new BufferedWriter(fw);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int id1 = 0;
                    if (MenuController.isChoseOther()) {
                        try {
                            bw.write(extension);
                            bw.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    start = null;
                    end = null;
                    id = 0;
                    dlugosc = listOfEnc.size();
                    for (BigInteger s1 : listOfEnc) {
                        //another implementation of the measuring time system.
                        //this one measures the time of writing the data in to the file.
                        if (id == 0) {
                            start = Instant.now();
                            updateTitle("Progress: loading data into a file");
                        }
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
                        sec[0] *= bytes.length;
                        sec[0] -= min;
                        minu[0] = (int) sec[0] / 60;
                        sec[0] %= 60;
                        if (id > 2000) {
                            updateMessage("Average Time left: " + minu[0] + " minutes " + (int) sec[0] + " seconds ");
                        }
                        updateProgress(id, dlugosc);
                        id++;
                        testOfGotten.add(Algorithm.getCompressed(s1));
                        try {
                            //compressing and writing the data in to the file.
                            bw.write(Algorithm.getCompressed(s1) + neg.get(id1));
                            bw.newLine();
                            id1++;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                succesLabel.setVisible(true);
                succesButton.setVisible(true);
                updateTitle("Progress: encryption finished");
                return null;
            }
        };
        progressLabel.textProperty().bind(task.titleProperty());
        timeLabel.textProperty().bind(task.messageProperty());
        progress.progressProperty().bind(task.progressProperty());
        process1.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /*Ths method returns to the Menu screen.*/
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

    /*This method loads previous screen.*/
    @FXML
    public void back() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/application/fxml/EncryptScreen5.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EncryptScreen5Controller menuController = loader.getController();
        menuController.setMainController(mainController);
        mainController.setScreen(pane);
    }
}