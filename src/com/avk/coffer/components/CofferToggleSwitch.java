package com.avk.coffer.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.avk.coffer.CofferReferences;
import com.avk.coffer.components.listeners.CofferToggleSwitchListener;

@SuppressWarnings("serial")
public class CofferToggleSwitch extends JPanel {

	private ArrayList<CofferToggleSwitchListener> listeners = new ArrayList<CofferToggleSwitchListener>();

	private boolean isOn = false;
	private static JLabel img;

	public CofferToggleSwitch(String text) {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		setMinimumSize(new Dimension(40, 40));
		setPreferredSize(new Dimension(80, 40));

		img = new JLabel(CofferReferences.TOGGLE_SWITCH);
		img.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				toggle();
				processCofferToggleSwitchEvents();
			}
		});
		add(img, BorderLayout.EAST);

		JLabel lblText = new JLabel(text);
		lblText.setFont(CofferReferences.Comfortaa_Plain_14);
		lblText.setForeground(CofferReferences.CofferVeryLightGrey);
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblText, BorderLayout.CENTER);
	}

	public boolean isOn() {
		return this.isOn;
	}

	public void toggle() {
		if (isOn) {
			img.setIcon(CofferReferences.TOGGLE_SWITCH);
			CofferToggleSwitch.this.isOn = false;
		} else {
			img.setIcon(CofferReferences.TOGGLED_SWITCH);
			CofferToggleSwitch.this.isOn = true;
		}
	}

	public synchronized void addCofferToggleSwitchListener(CofferToggleSwitchListener ctsl) {
		if (!listeners.contains(ctsl)) {
			listeners.add(ctsl);
		}
	}

	private void processCofferToggleSwitchEvents() {
		for (CofferToggleSwitchListener listener : listeners) {
			listener.onToggle();
		}
	}

}
