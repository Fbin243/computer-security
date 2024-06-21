package ui;

import java.awt.Dimension;

import javax.swing.Box;

public class DecryptForm extends Form {
	private FileInput fileInputPrivateKey;

	public DecryptForm() {
		super();

		header.setText("File Decryption");
		fileInput.setFileInputLabel("Upload File to Decrypt");
		actionButton.setText("Decrypt");
		actionButton.addActionListener(e -> System.out.println("Decrypting file..."));
		fileInputPrivateKey = new FileInput();
		fileInputPrivateKey.setFileInputLabel("Upload File Private Key");

		mainPanel.add(header);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		mainPanel.add(fileInput.getMainPanel());
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(fileInputPrivateKey.getMainPanel());
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(actionButton);
	}
}
