package ui;

import constants.Common;
import logic.AES;
import utils.Helpers;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DecryptForm extends Form {
    private final FileInput fileInputPrivateKey;
    private File privateKeySelectedFile;

    public DecryptForm() {
        super();

        header.setText("File Decryption");
        fileInput.setFileInputLabel("Upload File to Decrypt");
        fileInputPrivateKey = new FileInput();
        fileInputPrivateKey.setFileInputLabel("Upload File Private Key");

        fileInputPrivateKey.setFileInputEventHandler((File file) -> {
            privateKeySelectedFile = file;
        });

        actionButton.setText("Decrypt");
        actionButton.addActionListener(e -> handleDecrypt());

        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(fileInput.getMainPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(fileInputPrivateKey.getMainPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(actionButton);
    }

    private void handleDecrypt() {
		// if (privateKeySelectedFile == null || super.selectedFileForm == null) {
		// 	System.out.println("Please select decrypt file and key private file.");
		// 	return;
		// }

        try {

            // Compare SHA-1 and HKprivate - Binh

            // If SHA-1 == HKprivate then
            // Decrypt the AES key
            // RSA rsa = new RSA();
            // rsa.loadPrivateKeyFromString(privateKey);
            // String aesKey = rsa.decrypt(encryptedAesKey);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Decryption failed.");
        }

        // Decrypt the file using AES - Tuan
        try {
            AES aes = new AES();
            String plainText = aes.decrypt("I76jiti1+82ZOif9ip7khQ==",
                    super.selectedFileForm.getAbsolutePath());

            String exportedFileName = Common.USER_PATH +
                    Common.DECRYPTED_SUB_NAME
                    + Helpers.getFileName(super.selectedFileForm.getName()) + "." + aes.getFileExtension();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(exportedFileName))) {
                bw.write(plainText);
                System.out.println("Decryption successful.");
            } catch (IOException internalEx) {
                internalEx.printStackTrace();
                throw new IOException("Error during file export", internalEx);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Decryption failed.");
        }
    }


}
