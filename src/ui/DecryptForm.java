package ui;

import java.awt.Dimension;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.Box;

import constants.Common;
import logic.CryptoSystem;
import utils.Helpers;

public class DecryptForm extends Form {
	private final FileInput fileInputPrivateKey;
	private File privateKeySelectedFile;
	private String decryptedFilePath;
	private CryptoSystem crypto;

	public DecryptForm(CryptoSystem cryptoSystem) {
		super();
		this.crypto = cryptoSystem;

		header.setText("File Decryption");
		fileInput.setFileInputLabel("Upload File to Decrypt");
		fileInputPrivateKey = new FileInput();
		fileInputPrivateKey.setFileInputLabel("Upload File Private Key");

		fileInputPrivateKey.setFileInputEventHandler((File file) -> privateKeySelectedFile = file);

		actionButton.setText("Decrypt");
		actionButton.addActionListener(e -> {
			if (handleDecrypt()) {
				fileInput.resetFileDialog();
				fileInputPrivateKey.resetFileDialog();
				System.out.println("File decrypted successfully.");
				setSuccessText(
						"<html>File decrypted successfully.<br><br>"
								+ "Decrypted file: <br>"
								+ decryptedFilePath + "</html>");
			} else {
				System.out.println("File decryption failed.");
				setErrorText("File decryption failed.");
			}
		});

		mainPanel.add(header);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		mainPanel.add(fileInput.getMainPanel());
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(fileInputPrivateKey.getMainPanel());
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(actionButton);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(result);
	}

	private boolean handleDecrypt() {
		if (selectedFileForm == null || privateKeySelectedFile == null) {
			System.out.println("Please select both a file to decrypt and a private key file.");
			return false;
		}

		try {
			// Step 1: Load the private key
			String privateKey = new String(Files.readAllBytes(Paths.get(privateKeySelectedFile.getAbsolutePath())));
			crypto.rsa.loadPrivateKeyFromString(privateKey);

			// Step 2: Compare HKPrivate with the hash of the user's private key
			String userHashedPrivateKey = crypto.sha1.hash(crypto.rsa.getPrivateKey());

			String CFileName = selectedFileForm.getName();
			String metadataFileName = CFileName.substring(0, CFileName.lastIndexOf("."))
					+ Common.METADATA_EXTENSION;
			String metadata = new String(Files.readAllBytes(Paths.get(Common.SYSTEM_PATH + metadataFileName)));
			String[] metadataParts = metadata.split("\n");
			String HKPrivate = metadataParts[0];
			String Kx = metadataParts[1];
			if (!userHashedPrivateKey.equals(HKPrivate)) {
				System.out.println("Invalid private key.");
				return false;
			}

			// Step 3: Decrypt Kx to get Ks by using RSA
			String Ks = crypto.rsa.decrypt(Kx);

			// Step 4: Decrypt the file using Ks by using AES
			byte[] decryptedBytes = crypto.aes.decrypt(Ks,
					super.selectedFileForm);

			String decryptedFileName = Helpers.getFileName(super.selectedFileForm.getName());
			this.decryptedFilePath = selectedFileForm.getParent() + "/"
					+ Common.DECRYPTED_SUB_NAME
					+ decryptedFileName.substring(0, decryptedFileName.length() - Common.UUID_LENGTH - 1)
					+ "." + crypto.aes.getFileExtension();

			Files.write(Paths.get(decryptedFilePath), decryptedBytes, StandardOpenOption.CREATE);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	}
}
