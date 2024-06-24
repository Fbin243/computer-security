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
        String systemMetadataFileName = FileGenerator.generateUniqueString() + ".metadata";

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(Common.SYSTEM_PATH + systemMetadataFileName, false));) {
            // Step 1: AES generate key Ks
            // Need to generate AES key right here .......

            // Step 2: AES encrypt file with Ks to get file C and save to ./user
            AES.encrypt(key, inputFile, encryptedFile);
            System.out.println("File encrypted successfully.");

            // Step 3: RSA generate key pair
            RSA rsa = new RSA();
            rsa.generateKeys();

            // Step 4: RSA encrypt Ks with public key to get Kx
            String encryptedAesKey = rsa.encrypt(key);
            System.out.println("AES Key encrypted successfully");
            System.out.println("Encrypted AES Key (Kx): " + encryptedAesKey);

            // Step 5: SHA hash private key and save with Kx to ./system
            SHA sha = new SHA(Algorithm.SHA1);
            String hashedPrivateKey = sha.hash(rsa.getPrivateKey());
            System.out.println("Private key hashed successfully");
            System.out.println("Hashed Private Key: " + hashedPrivateKey);
            bw.write(hashedPrivateKey + "\n" + encryptedAesKey);

            // Step 5: Save private key to ./user
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
