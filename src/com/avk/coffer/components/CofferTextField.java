package com.avk.coffer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.avk.coffer.CofferReferences;
import com.avk.coffer.CofferRoundBorder;

@SuppressWarnings("serial")
public class CofferTextField extends JTextField {

	private String placeHolder = "";

	private int borderThickness = 2;
	private int borderRoundness = 5;

	public CofferTextField(String placeHolder, String text) {
		super((text != null) ? text : placeHolder);

		this.placeHolder = placeHolder;

		setOpaque(false);
		setBackground(null);
		setPreferredSize(new Dimension(320, 40));
		setBorder(new CompoundBorder(new CofferRoundBorder(CofferReferences.CofferBlue, borderThickness, borderRoundness), new EmptyBorder(5, 20, 5, 20)));
		setCaretColor(CofferReferences.CofferVeryLightGrey);
		setForeground((text != null) ? Color.WHITE : CofferReferences.CofferVeryLightGrey);
		setHorizontalAlignment(SwingConstants.LEFT);
		setFont(CofferReferences.Comfortaa_Plain_14);
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (CofferTextField.super.getText().equals(CofferTextField.this.placeHolder)) {
					setForeground(Color.WHITE);
					CofferTextField.super.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (CofferTextField.super.getText().equals("")) {
					setForeground(CofferReferences.CofferVeryLightGrey);
					CofferTextField.super.setText(CofferTextField.this.placeHolder);
				}
			}

		});
	}

	public void setValid(boolean flag) {
		setBorder(new CompoundBorder(new CofferRoundBorder(flag ? CofferReferences.CofferBlue : CofferReferences.CofferRed, borderThickness, borderRoundness), new EmptyBorder(5, 20, 5, 20)));
	}

	@Override
	public String getText() {
		String temp = CofferTextField.super.getText();
		if (temp.equals(this.placeHolder))
			return "";
		return temp;
	}

	public void setPlaceHolder(String placeholder) {
		this.placeHolder = placeholder;
	}

	public String getPlaceHolder() {
		return placeHolder;
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

}