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
		if (selectedFileForm == null || privateKeySelectedFile == null) {
            System.out.println("Please select both a file to decrypt and a private key file.");
            return;
        }

        try {
			// Load the private key
            String privateKey = new String(Files.readAllBytes(Paths.get(privateKeySelectedFile.getAbsolutePath())));
            RSA rsa = new RSA();
            rsa.loadPrivateKeyFromString(privateKey);

			// Compare SHA-1 and HKprivate - Binh
           
			// Decrypt the AES key
            // String aesKey = rsa.decrypt(encryptedAesKey);

            // Decrypt the file using AES - Tuan
			String aseKey = "678z2MlNaPjJDRMk/eZV+w==";
			AES aes = new AES();
			aes.decrypt(aseKey, selectedFileForm.getAbsolutePath(), "decrypted_" + selectedFileForm.getName());
			System.out.println("Decryption successful.");


        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Decryption failed.");
        }
    }
}
