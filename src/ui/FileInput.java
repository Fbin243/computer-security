package ui;

import constants.Common;
import interfaces.IFileInputEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class FileInput {
	private final JButton fileInputDialogButton;
	private final JLabel fileInputLabel;
	private final JLabel fileInputStatusLabel;
	private JFileChooser fileDialog;
	private final JPanel fileInputPanel;
	private final JPanel mainPanel;
	private IFileInputEvent fileInputEventHandler;

	public FileInput() {

		fileInputLabel = new JLabel("", JLabel.LEFT);
		fileInputLabel.setFont(fileInputLabel.getFont().deriveFont(15.0f));
		fileInputLabel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

		fileInputDialogButton = Utils.createFileInputDialogButton();

		fileInputStatusLabel = new JLabel("No File Chosen", JLabel.LEFT);
		fileInputStatusLabel.setBorder(new EmptyBorder(0, 20, 0, 20));
		handleShowFileDialogButton();

		// Make a panel for input file status label and button
		fileInputPanel = new JPanel();
		fileInputPanel.setLayout(new BoxLayout(fileInputPanel, BoxLayout.X_AXIS));
		fileInputPanel.add(fileInputDialogButton);
		fileInputPanel.add(fileInputStatusLabel);
		fileInputPanel.setBorder(new LineBorder(Color.BLACK, 1, true));
		fileInputPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(fileInputLabel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		mainPanel.add(fileInputPanel);
	}

	public void setFileInputLabel(String text) {
		fileInputLabel.setText(text);
	}

	public void setFileInputEventHandler(IFileInputEvent fileInputEventHandler) {
		this.fileInputEventHandler = fileInputEventHandler;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	private void handleShowFileDialogButton() {
		fileDialog = new JFileChooser();
		String currentDirectoryPath = System.getProperty(Common.USER_PATH);
		File currentDirectory = new File(currentDirectoryPath);
		fileDialog.setCurrentDirectory(currentDirectory);

		fileInputDialogButton.addActionListener(e -> {
			fileDialog.setDialogTitle("Choose a file to open");
			int returnVal = fileDialog.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileDialog.getSelectedFile();
				fileInputStatusLabel.setText(file.getName());

				if (fileInputEventHandler != null) {
					fileInputEventHandler.onItemClick(file);
				}
			} else {
				fileInputStatusLabel.setText("No File Chosen");
			}
		});
	}
}
