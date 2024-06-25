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
//		if (privateKeySelectedFile == null || super.selectedFileForm == null) {
//			System.out.println("Please select decrypt file and key private file.");
//			return;
//		}

        try {

			// Compare SHA-1 and HKprivate - Binh
           
            // If SHA-1 == HKprivate then
			// Decrypt the AES key
            // RSA rsa = new RSA();
            // rsa.loadPrivateKeyFromString(privateKey);
            // String aesKey = rsa.decrypt(encryptedAesKey);

            // Decrypt the file using AES - Tuan
			AES aes = new AES();
			aes.decrypt2("hKDHl3bTmOseojw07o98zQ==",
					getDesktopDirectory() + "115566e5-c7ce-49f1-8d5a-b5c705950cef.aes");
//			System.out.println("Decryption successful. " + fileP);
//			kmr/l6ZRW7cmNx9wMuojLH8zf


        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Decryption failed.");
        }
    }

	private String getDesktopDirectory() {
		String home = System.getProperty("user.home");
		return home + "/Desktop/";
	}
}
