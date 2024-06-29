package ui;

import java.awt.GridLayout;

import javax.swing.JFrame;

import logic.CryptoSystem;

public class GUI {

    private static JFrame mainFrame;

    public static void initGUI(CryptoSystem cryptoSystem) {
        mainFrame = new JFrame("GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 500);
        mainFrame.setLayout(new GridLayout(1, 2));

        EncryptForm encryptForm = new EncryptForm(cryptoSystem);
        DecryptForm decryptForm = new DecryptForm(cryptoSystem);

        mainFrame.add(encryptForm.getMainPanel());
        mainFrame.add(decryptForm.getMainPanel());
        mainFrame.setVisible(true);
    }
}
