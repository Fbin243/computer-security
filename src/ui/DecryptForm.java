package ui;

import interfaces.IFileInputEvent;

import java.awt.Dimension;
import java.io.File;

import javax.swing.Box;

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
		actionButton.addActionListener(e -> {
			System.out.println("Decrypting file...");
			System.out.println("File: " + selectedFileForm.getAbsolutePath());
		});

		mainPanel.add(header);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		mainPanel.add(fileInput.getMainPanel());
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(fileInputPrivateKey.getMainPanel());
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(actionButton);
	}
}
