package ui;

import constants.Algorithm;
import constants.Common;
import logic.AES;
import logic.RSA;
import logic.SHA;
import utils.Helpers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DecryptForm extends Form {
	private final FileInput fileInputPrivateKey;
	private File privateKeySelectedFile;

	public DecryptForm() {
		super();

		header.setText("File Decryption");
		fileInput.setFileInputLabel("Upload File to Decrypt");
		fileInputPrivateKey = new FileInput();
		fileInputPrivateKey.setFileInputLabel("Upload File Private Key");

		fileInputPrivateKey.setFileInputEventHandler((File file) -> privateKeySelectedFile = file);

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
			System.out.println("=============== DECRYPTION ===============");
			// Step 1: Load the private key
			String privateKey = new String(Files.readAllBytes(Paths.get(privateKeySelectedFile.getAbsolutePath())));
			RSA rsa = new RSA();
			rsa.loadPrivateKeyFromString(privateKey);

			// Step 2: Compare HKPrivate with the hash of the user's private key
			SHA sha = new SHA(Algorithm.SHA1);
			String userHashedPrivateKey = sha.hash(rsa.getPrivateKey());

			String CFileName = selectedFileForm.getName();
			String metadataFileName = CFileName.substring(0, CFileName.lastIndexOf("."))
					+ Common.METADATA_EXTENSION;
			String metadata = new String(Files.readAllBytes(Paths.get(Common.SYSTEM_PATH + metadataFileName)));
			String[] metadataParts = metadata.split("\n");
			String HKPrivate = metadataParts[0];
			String Kx = metadataParts[1];
			if (!userHashedPrivateKey.equals(HKPrivate)) {
				throw new Exception("Invalid private key.");
			}

		// Step 3: Decrypt Kx to get Ks by using RSA
		String Ks = rsa.decrypt(Kx);
		System.out.println("Ks: " + Ks);
            AES aes = new AES();
            byte[] decryptedBytes = aes.decrypt(Ks,
                    super.selectedFileForm.getAbsolutePath());

            String exportedFileName = selectedFileForm.getParent() + "/" +
                    Common.DECRYPTED_SUB_NAME
                    + Helpers.getFileName(super.selectedFileForm.getName()) + "." + aes.getFileExtension();

            Files.write(Paths.get(exportedFileName), decryptedBytes, StandardOpenOption.CREATE);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Decryption failed.");
        }
    }


}
