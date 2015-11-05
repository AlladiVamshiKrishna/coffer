package com.avk.coffer.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.avk.coffer.CofferReferences;

@SuppressWarnings("serial")
public class CofferCheckBox extends JLabel {

	private boolean checked = false;
	private String text = "";

	public CofferCheckBox() {
		super();
		createUI(false, "");
	}

	public CofferCheckBox(boolean check, String text) {
		super();
		this.text = text;
		this.checked = check;
		createUI(check, text);
	}

	private void createUI(boolean checked, String text) {
		if (checked) {
			setIcon(CofferReferences.CHECKEDBOX);
		} else {
			setIcon(CofferReferences.CHECKBOX);
		}
		this.setText(text);
		setForeground(CofferReferences.CofferBlue);
		setFont(CofferReferences.Comfortaa_Plain_15);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				toggleCheck();
			}
		});
	}

	public void setText(String text) {
		this.text = text;
		super.setText(text);
	}

	public String getText() {
		return text;
	}

	public void toggleCheck() {
		if (checked) {
			setIcon(CofferReferences.CHECKBOX);
			checked = false;
		} else {
			setIcon(CofferReferences.CHECKEDBOX);
			checked = true;
		}
	}

	public boolean isChecked() {
		return checked;
	}
}
