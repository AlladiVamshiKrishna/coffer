package com.avk.coffer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

import com.avk.coffer.CofferReferences;

@SuppressWarnings("serial")
public class CofferTabLabel extends JLabel {

	public boolean isSelected = false;

	public CofferTabLabel(String text, Icon icon) {
		super(text);
		setBorder(new MatteBorder(1, 10, 1, 1, (Color) null));
		setPreferredSize(new Dimension(250, 35));
		setOpaque(true);
		setIcon(icon);
		setIconTextGap(10);
		setFont(CofferReferences.Comfortaa_Plain_13);
		setBackground(CofferReferences.CofferDarkGrey);
		setForeground(Color.WHITE);
		setCursor(new Cursor(Cursor.HAND_CURSOR));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!isSelected) {
					setBackground(CofferReferences.CofferLightGrey);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!isSelected) {
					setBackground((Color) null);
				}
			}
		});

	}
}
