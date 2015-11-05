package com.avk.coffer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.avk.coffer.components.CofferButton;

@SuppressWarnings("serial")
public class CofferExtraInstance extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */

	public CofferExtraInstance() {
		setBounds(0, 0, 400, 200);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new LineBorder(CofferReferences.CofferBlue, 1));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(0, 40, 400, 80);
		contentPanel.add(panel);
		panel.setLayout(null);
		{
			JLabel lblLogo = new JLabel(CofferReferences.COFFER_LOGO_64X64);
			lblLogo.setBounds(30, 0, 80, 80);
			panel.add(lblLogo);
		}

		JLabel lbl1 = new JLabel("An Instance of Coffer is already running.");
		lbl1.setBounds(95, 10, 280, 30);
		panel.add(lbl1);
		lbl1.setForeground(CofferReferences.CofferBlue);
		lbl1.setFont(CofferReferences.Comfortaa_Plain_13);
		{
			JLabel lbl2 = new JLabel("Check in the system's task tray.");
			lbl2.setBounds(95, 40, 280, 30);
			panel.add(lbl2);
			lbl2.setForeground(CofferReferences.CofferLightGrey);
			lbl2.setFont(CofferReferences.Comfortaa_Plain_13);
		}

		CofferButton lblOk = new CofferButton("Ok");
		contentPanel.add(lblOk);
		lblOk.setSize(100, 30);
		lblOk.setFont(CofferReferences.Comfortaa_Plain_13);
		lblOk.setBounds(150, 140, 100, 30);
		lblOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

	}
}
