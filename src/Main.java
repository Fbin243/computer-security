import logic.CryptoSystem;
import ui.GUI;

public class Main {
    public static void main(String[] args) {
        CryptoSystem cryptoSystem = new CryptoSystem();
        GUI.initGUI(cryptoSystem);
    }
}
