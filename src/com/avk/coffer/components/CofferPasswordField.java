package com.avk.coffer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.avk.coffer.CofferReferences;
import com.avk.coffer.CofferRoundBorder;

@SuppressWarnings("serial")
public class CofferPasswordField extends JPanel {

	private JPasswordField passwordField;
	private JLabel passwordToggleImg;

	private boolean isPasswordHidden = true;

	private String placeHolder = "";
	private int borderThickness = 2;
	private int borderRoundness = 5;

	public CofferPasswordField(String placeHolder, String password) {
		setOpaque(false);
		setBackground(null);
		setLayout(null);
		setPreferredSize(new Dimension(320, 40));
		setBorder(new CofferRoundBorder(CofferReferences.CofferBlue, borderThickness, borderRoundness));

		this.placeHolder = placeHolder;

		passwordField = new JPasswordField((password != null) ? password : this.placeHolder);
		passwordField.setBorder(new EmptyBorder(5, 20, 5, 20));
		passwordField.setOpaque(false);
		passwordField.setCaretColor(CofferReferences.CofferVeryLightGrey);
		passwordField.setForeground((password != null) ? Color.WHITE : CofferReferences.CofferVeryLightGrey);
		passwordField.setBackground(null);
		passwordField.setEchoChar((password == null) ? (char) 0 : '#');
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setFont(CofferReferences.Comfortaa_Plain_14);
		passwordField.setBounds(0, 0, 320, 40);
		passwordField.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent e) {
				if (passwordField.getText().equals(CofferPasswordField.this.placeHolder)) {
					passwordField.setForeground(Color.WHITE);
					passwordField.setEchoChar('#');
					passwordField.setText("");
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent e) {
				if (passwordField.getText().equals("")) {
					passwordField.setEchoChar((char) 0);
					passwordField.setForeground(CofferReferences.CofferVeryLightGrey);
					passwordField.setText(CofferPasswordField.this.placeHolder);
				}
			}

		});

		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				passwordToggleImg.setVisible(true);

				if (passwordField.getPassword().length <= 0)
					passwordToggleImg.setVisible(false);
			}

		});

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

			// @Override
			// public void mouseClicked(MouseEvent e) {
			// isPasswordHidden = !isPasswordHidden;
			// passwordField.setEchoChar(isPasswordHidden? '#' : (char)0 );
			// passwordToggleImg.setIcon(isPasswordHidden? CofferReferences.SHOW
			// : CofferReferences.HIDE);
			// }
		});
		passwordToggleImg.setBounds(280, 5, 30, 30);
		passwordToggleImg.setVisible((password != null) ? true : false);
		add(passwordToggleImg);
		add(passwordField);

	}

	public void setPlaceHolder(String placeholder) {
		this.placeHolder = placeholder;
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setValid(boolean flag) {
		setBorder(new CofferRoundBorder(flag ? CofferReferences.CofferBlue : CofferReferences.CofferRed, borderThickness, borderRoundness));
	}

	public void setBorderThickness(int thickness) {
		this.borderThickness = thickness;
	}

	public int getBorderThickness() {
		return borderThickness;
	}

	public int getBorderRoundness() {
		return borderRoundness;
	}

	public void setBorderRoundness(int borderRoundness) {
		this.borderRoundness = borderRoundness;
	}

	@Override
	public void addMouseListener(MouseListener ml) {
		super.addMouseListener(ml);
		passwordField.addMouseListener(ml);
	}

	@SuppressWarnings("deprecation")
	public String getText() {
		String temp = passwordField.getText();
		if (temp.equals(this.placeHolder))
			return "";
		return temp;
	}

	public void setText(String text) {
		passwordField.setText(text);
	}

	public void grabFocus() {
		passwordField.grabFocus();
	}

}