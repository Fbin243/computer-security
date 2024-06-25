package ui;

import interfaces.IFileInputEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.io.File;

public abstract class Form {
  protected JLabel header;
  protected JButton actionButton;
  protected JPanel mainPanel;
  protected FileInput fileInput;
  protected File selectedFileForm;

  public Form() {
    header = new JLabel("", JLabel.LEFT);
    header.setFont(header.getFont().deriveFont(24.0f));
    header.setAlignmentX(JLabel.LEFT_ALIGNMENT);

    fileInput = new FileInput();
    fileInput.getMainPanel().setAlignmentX(JPanel.LEFT_ALIGNMENT);

    fileInput.setFileInputEventHandler((File file) -> {
      selectedFileForm = file;
    });

    actionButton = Utils.createActionButton();
    actionButton.setAlignmentX(JButton.LEFT_ALIGNMENT);

    mainPanel = new JPanel();
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }
}
