package application;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/* This file contains the main encrypting algorithm of the application. */

class Progress extends Thread {
    /*This code steers the progressBar, which shows the progress of encrypting.*/
    double liczba;
    ProgressBar bar;

    Progress(ProgressBar bar, double liczba) {
        this.liczba = liczba;
        this.bar = bar;
    }

    public void run() {
        for (double i = liczba; i < (liczba + 45); i++) {
            System.out.println("wykonywanie");
            bar.setProgress(i / 100);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (bar.getProgress() >= (liczba + 45) / 100) {
                break;
            }
        }
    }
}


public class Algorithm {
    /*This method return the random number. The length of the number (in bits) is
        specified by the parameter*/
    static BigInteger randomNumber(int n) {
        Random rand = new Random();
        byte[] bity = new byte[n];
        BigInteger bi = new BigInteger("0");
        for (int i = 0; i < bity.length; i++) {
            bity[i] = (byte) rand.nextInt(2);
        }
        for (int i = 0; i < bity.length; i++) {
            if (bity[i] == 1) {
                bi = bi.add(new BigInteger("2").pow(i));
            }
        }
        return bi;
    }

    /*This method converts a BigInteger into an Array of bits*/
    static ArrayList<Byte> bity(BigInteger bi) {
        ArrayList<Byte> lista = new ArrayList<Byte>();

        for (int i = 0; true; i++) {
            if (bi.equals(new BigInteger("0"))) {
                break;
            }
            if (bi.remainder(new BigInteger("2")).equals(new BigInteger("1"))) {
                lista.add((byte) 1);
            } else {
                lista.add((byte) 0);
            }
            bi = bi.divide(new BigInteger("2"));
        }
        return lista;
    }

    /*This method implements the Miller-Rabin primality test. Returns true if the BigInteger in the
      parameter is prime, or false if not. It's a probability algorithm, but chance to get not right result
      is similar to zero.*/
    public static boolean primarityTest(BigInteger b) {
        int a = 0;
        BigInteger bi1 = b.subtract(new BigInteger("1"));
        BigInteger t = new BigInteger("2");
        int s = 0;
        //counts how many powers of 2 the number contains.
        int ile_dwojek = Algorithm.bity(b).size();

        while ((bi1.remainder(t)).equals(new BigInteger("0"))) {
            t = t.multiply(new BigInteger("2"));
            s++;
        }
        t = t.divide(new BigInteger("2"));
        BigInteger d = b.divide(t);
        zew:
	/*tests 60 times the primarity. This rate makes the alghoritm longer,
	 but more efficient*/
        for (byte k = 0; k < 60; k++) {
            if (k == 4) {
                if (a == 0) {
                    a++;
                }
            }
            BigInteger losowa = Algorithm.randomNumber(ile_dwojek);
            while ((losowa.equals(new BigInteger("1")) || (losowa.equals(new BigInteger("0")) || (losowa.equals(b)) || (losowa.compareTo(b) == 1)))) {
                losowa = Algorithm.randomNumber(ile_dwojek);
            }
            //test
            if ((losowa.modPow(d, b)).equals(new BigInteger("1"))) {
                k--;
                continue zew;
            } else {
                for (byte r = 0; r < s; r++) {
                    if (losowa.modPow(d.multiply(new BigInteger("2").pow(r)), b).equals(bi1)) {
                        continue zew;
                    } else {
                        if (r == (s - 1)) {
                            if (k == 4) {
                            }
                            return false;

                        }
                    }
                }
            }
        }
        return true;
    }

    /*This method connects previous methods to generate a prime number*/
    public static BigInteger generateAPrimeNumber(int dlugosc) {
        BigInteger bi = Algorithm.randomNumber(dlugosc);
        bi = bi.multiply(new BigInteger("2"));
        bi = bi.add(new BigInteger("1"));
        int id = 0;
        while (true) {
            id++;
            if (Algorithm.primarityTest(bi)) {
                return bi;
            } else {
                bi = bi.add(new BigInteger("2"));
            }
        }
    }

    static BigInteger liczba1;
    static BigInteger liczba2;
    static BigInteger liczbaN;

    /*This method implements the Euclides algorithm to
        find the greatest common divisor of the two Big Integers*/
    public static BigInteger NWD(BigInteger a, BigInteger b) {
        BigInteger c;
        while (!b.equals(new BigInteger("0"))) {
            c = a.remainder(b);
            a = b;
            b = c;
        }
        return a;
    }

    /*This method implements the extended Euclides algorithm to finds
      number d in equation d*e = 1 mod euler */
    public static BigInteger NWDR(BigInteger a, BigInteger b) {
        BigInteger u = new BigInteger("1");
        BigInteger w = a;
        BigInteger x = new BigInteger("0");
        BigInteger z = b;
        BigInteger q;
        while (!(w.equals(new BigInteger("0")))) {
            if (w.compareTo(z) == -1) {
                q = u;
                u = x;
                x = q;
                q = w;
                w = z;
                z = q;
            }
            q = w.divide(z);
            u = u.subtract(q.multiply(x));
            w = w.subtract(q.multiply(z));
        }
        if (!(z.equals(new BigInteger("1")))) {
            return new BigInteger("0");
        } else {

            if (x.compareTo(new BigInteger("0")) == -1) {
                x = x.add(b);
            }
            return x;
        }
    }

    /*This method uses previous methods to generate a private and public key*/
    public static ArrayList<BigInteger> generateAKey(int bit, ProgressBar bar) {
        Progress progress = new Progress(bar, 0.0);
        progress.start();
        liczba1 = Algorithm.generateAPrimeNumber(bit / 2);
        progress.interrupt();
        bar.setProgress(0.45);
        Progress progress1 = new Progress(bar, 45.0);
        progress1.start();
        liczba2 = Algorithm.generateAPrimeNumber(bit / 2);
        progress1.interrupt();
        bar.setProgress(0.90);
        liczbaN = liczba1.multiply(liczba2);
        BigInteger euler1 = liczba1.subtract(new BigInteger("1"));
        BigInteger euler2 = liczba2.subtract(new BigInteger("1"));
        BigInteger euler = euler1.multiply(euler2);
        Random rand = new Random();
        int iloscBitow = rand.nextInt(bit / 4);
        BigInteger e = Algorithm.randomNumber(iloscBitow);
        while (!((Algorithm.NWD(e, euler)).equals(new BigInteger("1")))) {
            e = Algorithm.randomNumber(iloscBitow);
        }
        BigInteger d = Algorithm.NWDR(e, euler);
        bar.setProgress(1.00);
        ArrayList<BigInteger> wynik = new ArrayList<>();
        wynik.add(liczbaN);
        wynik.add(e);
        wynik.add(d);
        return wynik;
    }

    /*Method created by my own idea. It uses 105 characters from ASCII format
      to create the new number system. This system contains 105 characters (compared to 10 characters,
      which contains the decimal system). Numbers written in this system are more than two times shorter
      than in decimal system. If you try to write these numbers into a file, they would
       need two times less place than numbers written by traditional decimal system.*/
    public static String getCompressed(BigInteger b) {
        BigInteger lol78 = new BigInteger("105");
        BigInteger big = b;
        char[] numbersOfNewSystem = new char[lol78.intValue()];
        for (int i = 23; i < numbersOfNewSystem.length + 23; i++) {
            numbersOfNewSystem[i - 23] = (char) i;
        }
        String newNumber = "";
        BigInteger newBig = big;
        List<Character> characterList = new LinkedList<>();
        while ((newBig.compareTo(BigInteger.ONE) == 1) || (newBig.compareTo(BigInteger.ONE)) == 0) {
            int els = newBig.mod(lol78).intValue();
            char ch = numbersOfNewSystem[els];
            characterList.add(ch);
            newBig = newBig.divide(lol78);
        }
        for (char c : characterList) {
            newNumber += c;
        }
        return newNumber;
    }

    /*Method to write a number again with decimal system.*/
    public static BigInteger getDecompressed(String s) {
        BigInteger lol78 = new BigInteger("105");
        String newNumber1 = s;
        char[] charr = newNumber1.toCharArray();
        BigInteger bigInt = new BigInteger("0");
        for (int i = 0; i < s.toCharArray().length; i++) {
            BigInteger b = BigInteger.valueOf((long) charr[i] - 23);
            bigInt = bigInt.add(b.multiply(lol78.pow(i)));
        }
        return bigInt;

    }

    /*Method used to set Labels in GUI.*/
    static void setLabelText(String s, Label label) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(s);
            }
        });
    }

    /*This method converts any file into the byte array.*/
    public static byte[] getBytes(File file) {
        try {
            File fi = file;
            byte[] fileContent = Files.readAllBytes(fi.toPath());
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}


