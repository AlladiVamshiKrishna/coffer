package com.avk.coffer.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.avk.coffer.CofferReferences;
import com.avk.coffer.components.listeners.CofferTextBlankEditListener;

@SuppressWarnings("serial")
public class CofferTextBlank extends JPanel {
	private JTextField textField;
	private JLabel lblTitle, blank;

	private ArrayList<CofferTextBlankEditListener> listeners = new ArrayList<CofferTextBlankEditListener>();

	private int width = 320;
	private String defaultText;

	public CofferTextBlank(String placeHolder, String text) {
		defaultText = text;
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

		JLayeredPane panel = new JLayeredPane();
		panel.setOpaque(false);
		panel.setLayout(null);

		blank = new JLabel();
		blank.setBounds(0, 25, width, 5);
		blank.setBorder(new MatteBorder(0, 2, 2, 2, (Color) CofferReferences.CofferBlue));
		panel.add(blank);

		textField = new JTextField(text != null ? text : placeHolder);
		textField.setSelectedTextColor(CofferReferences.CofferLightGrey);
		textField.setSelectionColor(CofferReferences.CofferBlue);
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				for (CofferTextBlankEditListener listener : listeners) {
					listener.onValueChange();
				}
			}

		});
		textField.setForeground(text != null ? Color.WHITE : CofferReferences.CofferVeryLightGrey);
		textField.setFont(CofferReferences.Comfortaa_Plain_14);
		textField.setCaretColor(CofferReferences.CofferVeryLightGrey);
		textField.setBounds(0, 0, width, 30);
		textField.setOpaque(false);
		textField.setCaretPosition(0);
		textField.setBackground((Color) null);
		textField.setBorder(new EmptyBorder(0, 15, 0, 0));
		panel.add(textField);

		add(panel);

	}

	public void reset() {
		textField.setText(defaultText);
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setEditable(boolean flag) {
		textField.setEditable(flag);
		textField.setCaretPosition(flag ? textField.getText().length() : 0);
	}

	public void setText(String text) {
		textField.setText(text);
	}

	public String getText() {
		return textField.getText();
	}

	public void setValid(boolean flag) {
		blank.setBorder(new MatteBorder(0, 2, 2, 2, flag ? (Color) CofferReferences.CofferBlue : CofferReferences.CofferRed));
	}

	public void addCofferTextBlankEditListener(CofferTextBlankEditListener ctbel) {
		listeners.add(ctbel);
	}

}
