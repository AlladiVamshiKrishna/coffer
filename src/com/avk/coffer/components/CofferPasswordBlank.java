package com.avk.coffer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.avk.coffer.CofferReferences;
import com.avk.coffer.components.listeners.CofferPasswordBlankEditListener;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class CofferPasswordBlank extends JPanel {
	private JPasswordField passwordField;
	private JLabel lblTitle, blank, passwordToggleImg;

	private ArrayList<CofferPasswordBlankEditListener> listeners = new ArrayList<CofferPasswordBlankEditListener>();

	private boolean isPasswordHidden = true;

	private int width = 320;
	private String defaultPassword;

	public CofferPasswordBlank(String placeHolder, String password) {
		defaultPassword = password;
		setOpaque(false);
		setPreferredSize(new Dimension(width, 50));
		setMaximumSize(new Dimension(width, 50));
		setLayout(new BorderLayout(0, 0));

		lblTitle = new JLabel(placeHolder);
		lblTitle.setForeground(CofferReferences.CofferBlue);
		lblTitle.setBorder(new EmptyBorder(0, 5, 0, 0));
		lblTitle.setFont(CofferReferences.Comfortaa_Italic_13);
		lblTitle.setPreferredSize(new Dimension(width, 20));

		add(lblTitle, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(null);

		passwordToggleImg = new JLabel(isPasswordHidden ? CofferReferences.SHOW : CofferReferences.HIDE);
		passwordToggleImg.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				isPasswordHidden = false;
				passwordField.setEchoChar((char) 0);
				passwordToggleImg.setIcon(CofferReferences.HIDE);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				isPasswordHidden = true;
				passwordField.setEchoChar('#');
				passwordToggleImg.setIcon(CofferReferences.SHOW);
			}

		});
		passwordToggleImg.setBounds(width - 35, 0, 30, 30);
		passwordToggleImg.setVisible((password != null) ? true : false);
		panel.add(passwordToggleImg);

		passwordField = new JPasswordField(password);
		passwordField.setEchoChar('#');
		passwordField.setSelectedTextColor(CofferReferences.CofferLightGrey);
		passwordField.setSelectionColor(CofferReferences.CofferBlue);
		passwordField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				for (CofferPasswordBlankEditListener listener : listeners) {
					listener.onValueChange();
				}
			}

		});

		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(CofferReferences.Comfortaa_Plain_14);
		passwordField.setBounds(0, 0, width, 30);
		passwordField.setOpaque(false);
		passwordField.setBackground((Color) null);
		passwordField.setBorder(new EmptyBorder(0, 15, 0, 0));
		panel.add(passwordField);

		blank = new JLabel();
		blank.setBounds(0, 25, width, 5);
		blank.setBorder(new MatteBorder(0, 2, 2, 2, (Color) CofferReferences.CofferBlue));
		panel.add(blank);

		add(panel);

	}

	public void reset() {
		passwordField.setText(defaultPassword);
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setEditable(boolean flag) {
		passwordField.setEditable(flag);
	}

	public void setText(String text) {
		passwordField.setText(text);
	}

	@SuppressWarnings("deprecation")
	public String getText() {
		return passwordField.getText();
	}

	public void setValid(boolean flag) {
		blank.setBorder(new MatteBorder(0, 2, 2, 2, flag ? (Color) CofferReferences.CofferBlue : CofferReferences.CofferRed));
	}

	public void addCofferPasswordBlankEditListener(CofferPasswordBlankEditListener cpbel) {
		listeners.add(cpbel);
	}

}
