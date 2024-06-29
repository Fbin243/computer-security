package ui;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.Box;

import constants.Common;
import logic.CryptoSystem;
import utils.FileGenerator;
import utils.Helpers;

public class EncryptForm extends Form {
    private String aesCFile;
    private String kPrivateKFile;
    private CryptoSystem crypto;

    public EncryptForm(CryptoSystem cryptoSystem) {
        super();
        this.crypto = cryptoSystem;

        header.setText("File Encryption");

        actionButton.setText("Encrypt");
        actionButton.addActionListener(e -> {
            if (handleEncrypt()) {
                fileInput.resetFileDialog();
                System.out.println("File encrypted successfully.");
                setSuccessText(
                        "<html>File encrypted successfully.<br><br>"
                                + "Encrypted file: <br>"
                                + selectedFileForm.getParent() + "/" + aesCFile + "<br><br>"
                                + "Private key file: <br>"
                                + selectedFileForm.getParent() + "/" + kPrivateKFile + "</html>");
            } else {
                System.out.println("File encryption failed.");
                setErrorText("File encryption failed.");
            }
        });

        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(fileInput.getMainPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(actionButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(result);
    }

    public boolean handleEncrypt() {
        if (selectedFileForm == null) {
            System.out.println("Please select a file to encrypt.");
            return false;
        }

        String inputFile = selectedFileForm.getAbsolutePath();
        String inputFileName = utils.Helpers.getFileName(selectedFileForm.getName());
        String fileNameUID = "-" + FileGenerator.generateUniqueString();

        this.aesCFile = inputFileName + fileNameUID + Common.AES_FILE_EXTENSION;
        this.kPrivateKFile = inputFileName + fileNameUID + Common.K_PRIVATE_FILE_EXTENSION;
        String extensionInfoFileName = inputFileName + fileNameUID + Common.INFO_FILE_EXTENSION;
        String systemMetadataFileName = inputFileName + fileNameUID + Common.METADATA_EXTENSION;

        try {
            // Step 1: Generate AES key and encrypt file
            crypto.aes.generateKey(128);
            byte[] hashedAESKey = crypto.aes.encrypt(inputFile);
            Path hashFilePath = Paths.get(selectedFileForm.getParent() + "/" + aesCFile);
            Path infoFileExtensionPath = Paths.get(Common.SYSTEM_PATH + "/" + extensionInfoFileName);

            try (BufferedWriter bw = Files.newBufferedWriter(infoFileExtensionPath, StandardOpenOption.CREATE)) {
                bw.write(Helpers.getFileExtension(inputFile) + "\n");
            }
            Files.write(hashFilePath, hashedAESKey, StandardOpenOption.CREATE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(Common.SYSTEM_PATH + systemMetadataFileName, false))) {
            // Step 3: RSA generate key pair
            crypto.rsa.generateKeys();

            // Step 4: RSA encrypt Ks with public key to get Kx
            String encryptedAesKey = crypto.rsa.encrypt(crypto.aes.getAesKey());

            // Step 5: SHA hash private key and save with Kx to ./system
            String hashedPrivateKey = crypto.sha1.hash(crypto.rsa.getPrivateKey());
            bw.write(hashedPrivateKey + "\n" + encryptedAesKey);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(selectedFileForm.getParent() + "/" + kPrivateKFile, false))) {
            // Step 6: Save private key to ./user
            bw.write(crypto.rsa.getPrivateKey());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
