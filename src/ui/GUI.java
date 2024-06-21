package ui;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class GUI {

    private static JFrame mainFrame;

    public static void initGUI() {
        mainFrame = new JFrame("GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 500);
        mainFrame.setLayout(new GridLayout(1, 2));

        EncryptForm encryptForm = new EncryptForm();
        DecryptForm decryptForm = new DecryptForm();

        mainFrame.add(encryptForm.getMainPanel());
        mainFrame.add(decryptForm.getMainPanel());
        mainFrame.setVisible(true);
    }
}
