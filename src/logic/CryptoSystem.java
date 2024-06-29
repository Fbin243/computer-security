package logic;

import java.io.File;

import constants.Algorithm;
import constants.Common;

public class CryptoSystem {
    public final AES aes;
    public final RSA rsa;
    public final SHA sha1;
    public final SHA sha256;

    public CryptoSystem() {
        this.createSystemDirectory();
        this.aes = new AES();
        this.rsa = new RSA();
        this.sha1 = new SHA(Algorithm.SHA1);
        this.sha256 = new SHA(Algorithm.SHA256);
    }

    private void createSystemDirectory() {
        // Create system directory if not exists
        File systemDir = new File(Common.SYSTEM_PATH);
        if (!systemDir.exists()) {
            systemDir.mkdir();
        }
    }
}
