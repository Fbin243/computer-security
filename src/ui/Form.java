package ui;

import javax.swing.*;

public class Form {
    private JLabel header;
    private JButton showFileDialogButton;
    private JLabel fileInputLabel;
    private JButton actionButton;
    private JFileChooser fileDialog;
    private JPanel panel;

    public Form() {
        header = new JLabel("", JLabel.LEFT);
        showFileDialogButton = new JButton("Choose File");
        fileInputLabel = new JLabel("", JLabel.LEFT);
        handleShowFileDialogButton();
        actionButton = new JButton("");

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(header);
        panel.add(fileInputLabel);
        panel.add(showFileDialogButton);
        panel.add(actionButton);
    }

    public void setHeader(String text) {
        header.setText(text);
    }

    public void setFileInputLabel(String text) {
        fileInputLabel.setText(text);
    }

    public void setActionButton(String text) {
        actionButton.setText(text);
    }

    public void setActionButtonListener(Runnable listener) {
        actionButton.addActionListener(e -> listener.run());
    }

    public JPanel getPanel() {
        return panel;
    }

    public void handleShowFileDialogButton() {
        fileDialog = new JFileChooser();
        showFileDialogButton.addActionListener(e -> {
            fileDialog.setDialogTitle("Choose a file to open");
            int returnVal = fileDialog.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                java.io.File file = fileDialog.getSelectedFile();
                System.out.println("Opening: " + file.getName());
            } else {
                System.out.println("Open command cancelled by user.");
            }
        });
    }
}
