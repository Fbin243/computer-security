package ui;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private static JFrame mainFrame;

    public static void initGUI() {
        mainFrame = new JFrame("GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(1, 2));

        Form encryptForm = new Form();
        encryptForm.setHeader("File Encryption");
        encryptForm.setFileInputLabel("Upload File to Encrypt");
        encryptForm.setActionButton("Encrypt");
        encryptForm.setActionButtonListener(() -> {
            System.out.println("Encrypting file...");
        });

        Form decryptForm = new Form();
        decryptForm.setHeader("File Decryption");
        decryptForm.setFileInputLabel("Upload File to Decrypt");
        decryptForm.setActionButton("Decrypt");
        decryptForm.setActionButtonListener(() -> {
            System.out.println("Decrypting file...");
        });

        mainFrame.add(encryptForm.getPanel());
        mainFrame.add(decryptForm.getPanel());
        mainFrame.setVisible(true);
    }
}
