package ui;

import constants.Algorithm;
import constants.Common;
import logic.AES;
import utils.AlgorithmGeneratorUtils;
import utils.FileGenerator;

import java.awt.Dimension;

import javax.swing.Box;

public class EncryptForm extends Form {
    public EncryptForm() {
        super();

        header.setText("File Encryption");
        fileInput.setFileInputLabel("Upload File to Encrypt");
        actionButton.setText("Encrypt");
        actionButton.addActionListener(e -> handleEncrypt());

        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(fileInput.getMainPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(actionButton);
    }

    public void handleEncrypt() {
        String key = AlgorithmGeneratorUtils.generateSymmetryKey(Algorithm.AES, 128);
        String inputFile = selectedFileForm.getAbsolutePath();
        String encryptedFile = FileGenerator.generateDecryptedFileName(inputFile, Common.ENCRYPTED);

        try {
            AES.encrypt(key, inputFile, encryptedFile);
            System.out.println("File encrypted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
