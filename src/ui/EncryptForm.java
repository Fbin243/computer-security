package ui;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.Box;

import constants.Algorithm;
import constants.Common;
import logic.AES;
import logic.RSA;
import logic.SHA;
import utils.AlgorithmGeneratorUtils;
import utils.FileGenerator;

public class EncryptForm extends Form {
    public EncryptForm() {
        super();

        header.setText("File Encryption");
        fileInput.setFileInputLabel("Upload File to Encrypt");
        fileInput.setFileInputEventHandler((file) -> {
            selectedFileForm = file;
        });

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
        String aesCFile = FileGenerator.generateUniqueString() + Common.C_FILE_EXTENSION;
        String kPrivateKFile = FileGenerator.generateUniqueString() + Common.K_FILE_EXTENSION;
        String systemMetadataFileName = FileGenerator.generateUniqueString() + Common.METADATA_EXTENSION;

        // Step 1: AES generate key Ks
        String aesKey = AlgorithmGeneratorUtils.generateSymmetryKey(Algorithm.AES, 128); // Ks
        AES aes = new AES();
        RSA rsa = new RSA();
        SHA sha = new SHA(Algorithm.SHA1);

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(Common.USER_PATH + aesCFile, false))){
            // Step 2: AES encrypt file with Ks to get file C and save to ./user
            String hashedAESKey = aes.encrypt(aesKey, inputFile);
            bw.write(hashedAESKey);
            System.out.println("File encrypted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(Common.SYSTEM_PATH + systemMetadataFileName, false))) {
            // Step 3: RSA generate key pair
            rsa.generateKeys();

            // Step 4: RSA encrypt Ks with public key to get Kx
            String encryptedAesKey = rsa.encrypt(aesKey);
            System.out.println("AES Key encrypted successfully");
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
                new FileWriter(Common.USER_PATH + kPrivateKFile, false))){
            // Step 6: Save private key to ./user
            bw.write(rsa.getPrivateKey());
            System.out.println("File K stored.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
