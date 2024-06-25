package ui;

import interfaces.IFileInputEvent;
import logic.AES;
import logic.RSA;

import java.awt.Dimension;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.Box;

import constants.Common;

public class DecryptForm extends Form {
	private final FileInput fileInputPrivateKey;
	private File selectedFileForm;

	public DecryptForm() {
		super();

		header.setText("File Decryption");
		fileInput.setFileInputLabel("Upload File to Decrypt");
		fileInputPrivateKey = new FileInput(file -> {
			selectedFileForm = file;
			System.out.println("Private key file: " + file.getAbsolutePath());
		});
		fileInputPrivateKey.setFileInputLabel("Upload File Private Key");
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
        try {

			// Compare SHA-1 and HKprivate - Binh
           
            // If SHA-1 == HKprivate then
			// Decrypt the AES key
            // RSA rsa = new RSA();
            // rsa.loadPrivateKeyFromString(privateKey);
            // String aesKey = rsa.decrypt(encryptedAesKey);

            // Decrypt the file using AES - Tuan

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Decryption failed.");
        }
    }
}
