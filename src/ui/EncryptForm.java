package ui;

import java.awt.Dimension;

import javax.swing.Box;

public class EncryptForm extends Form {
	public EncryptForm() {
		super();

		header.setText("File Encryption");
		fileInput.setFileInputLabel("Upload File to Encrypt");
		actionButton.setText("Encrypt");
		actionButton.addActionListener(e -> System.out.println("Encrypting file..."));

		mainPanel.add(header);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		mainPanel.add(fileInput.getMainPanel());
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(actionButton);
	}
}
