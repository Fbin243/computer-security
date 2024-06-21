package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class Utils {
	public static JButton createActionButton() {
		JButton button = new JButton("");
		button.setFont(new Font("San-serif", Font.BOLD, 15));
		button.setBackground(Color.BLACK);
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.setForeground(Color.WHITE);
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		button.setPreferredSize(new Dimension(80, 30));
		button.setMinimumSize(new Dimension(80, 30));
		button.setMaximumSize(new Dimension(80, 30));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.GRAY);
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(Color.BLACK);
			}
		});
		return button;
	}

	public static JButton createFileInputDialogButton() {
		JButton button = new JButton("Choose File");
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(new BevelBorder(BevelBorder.RAISED));
		button.setPreferredSize(new Dimension(100, 30));
		button.setMinimumSize(new Dimension(100, 30));
		button.setMaximumSize(new Dimension(100, 30));
		button.setBackground(Color.white);
		button.setOpaque(true);
		return button;
	}
}
