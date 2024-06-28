package ui;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.Box;

import constants.Algorithm;
import constants.Common;
import logic.AES;
import logic.RSA;
import logic.SHA;
import utils.FileGenerator;
import utils.Helpers;

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
        if (selectedFileForm == null) {
            System.out.println("Please select a file to encrypt.");
            return;
        }

        String inputFile = selectedFileForm.getAbsolutePath();
        String AESPhaseFileName = FileGenerator.generateUniqueString();
        String aesCFile = AESPhaseFileName + Common.AES_FILE_EXTENSION;
        String extensionInfoFileName = AESPhaseFileName + Common.INFO_FILE_EXTENSION;
        String kPrivateKFile = FileGenerator.generateUniqueString() + Common.K_PRIVATE_FILE_EXTENSION;
        String systemMetadataFileName = FileGenerator.generateUniqueString() + Common.METADATA_EXTENSION;

        // Step 1: AES generate key Ks
        AES aes = new AES();
        RSA rsa = new RSA();
        SHA sha = new SHA(Algorithm.SHA1);

        try {
            byte[] hashedAESKey = aes.encrypt(inputFile);
            Path hashFilePath = Paths.get(Common.USER_PATH + aesCFile);
            Path infoFileExtensionPath = Paths.get(Common.USER_PATH + extensionInfoFileName);


            // bw.write(Helpers.getFileExtension(inputFile) + "\n");
            // Files.write(outputPath, Helpers.getFileExtension(inputFile) + "\n", StandardOpenOption.CREATE);
            try (BufferedWriter bw = Files.newBufferedWriter(infoFileExtensionPath, StandardOpenOption.CREATE)) {
                bw.write(Helpers.getFileExtension(inputFile) + "\n");
            }
            Files.write(hashFilePath, hashedAESKey, StandardOpenOption.CREATE);

            System.out.println("Ks key: " + aes.getAesKey());
            System.out.println("File: " + aesCFile);
            System.out.println("AES Key encrypted successfully.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(Common.SYSTEM_PATH + systemMetadataFileName, false))) {
            // Step 3: RSA generate key pair
            rsa.generateKeys();

            // Step 4: RSA encrypt Ks with public key to get Kx
            String encryptedAesKey = rsa.encrypt(aes.getAesKey());
            System.out.println("AES Key encrypted by RSA successfully");
            System.out.println("Encrypted AES Key (Kx): " + encryptedAesKey);

            // Step 5: SHA hash private key and save with Kx to ./system
            String hashedPrivateKey = sha.hash(rsa.getPrivateKey());
            System.out.println("Private key hashed successfully");
            System.out.println("Hashed Private Key: " + hashedPrivateKey);
            bw.write(hashedPrivateKey + "\n" + encryptedAesKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(Common.USER_PATH + kPrivateKFile, false))) {
            // Step 6: Save private key to ./user
            bw.write(rsa.getPrivateKey());
            System.out.println("File K-private stored.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
