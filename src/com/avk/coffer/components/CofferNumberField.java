package com.avk.coffer.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.avk.coffer.CofferReferences;

@SuppressWarnings("serial")
public class CofferNumberField extends JPanel {

	private JTextField numField;
	private JLabel blank = new JLabel(CofferReferences.MEDIUM_TEXT_BLANK);
	private JLabel plus = new JLabel(CofferReferences.PLUS);
	private JLabel minus = new JLabel(CofferReferences.MINUS);
	private Integer num = 0;
	private Integer max = Integer.MAX_VALUE;
	private Integer min = Integer.MIN_VALUE;

	public CofferNumberField() {
		super();
		setOpaque(false);
		setLayout(null);
		setPreferredSize(new Dimension(140, 30));

		numField = new JTextField();
		numField.setText(num.toString());
		numField.setForeground(Color.WHITE);
		numField.setFont(CofferReferences.Comfortaa_Plain_15);
		numField.setHorizontalAlignment(SwingConstants.CENTER);
		numField.setEditable(false);
		numField.setOpaque(false);
		numField.setBorder(null);
		numField.setBounds(30, 5, 80, 30);
		add(numField);

		plus.setBounds(115, 15, 10, 10);
		plus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (CofferNumberField.this.num < CofferNumberField.this.max) {
					incNum();
				}
			}
		});
		add(plus);

		minus.setBounds(15, 15, 10, 10);
		minus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (CofferNumberField.this.num > CofferNumberField.this.min) {
					decNum();
				}
			}
		});
		add(minus);

		blank.setBounds(10, 20, 120, 10);
		add(blank);
	}

	public void setNum(int num) {
		this.num = num;
		numField.setText(this.num.toString());
	}

	public int getNum() {
		return this.num;
	}

	public void decNum() {
		this.num -= 1;
		numField.setText(this.num.toString());
	}

	public void incNum() {
		this.num += 1;
		numField.setText(this.num.toString());
	}

	public void setMaxNum(int maxNum) {
		this.max = maxNum;
	}

	public int getMaxNum() {
		return this.max;
	}

	public void setMinNum(int minNum) {
		this.min = minNum;
	}

	public int getMinNum() {
		return this.min;
	}
}
