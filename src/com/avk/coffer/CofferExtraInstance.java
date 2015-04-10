package com.avk.coffer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

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
		contentPanel.setBorder(new LineBorder(new Color(0,175,210), 1));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(0, 40, 400, 80);
		contentPanel.add(panel);
		panel.setLayout(null);
		{
			JLabel lblLogo = new JLabel(CofferRef.COFFER_LOGO_64X64);
			lblLogo.setBounds(30, 0, 80, 80);
			panel.add(lblLogo);
		}
		
		JLabel lbl1 = new JLabel("An Instance of Coffer os already running.");
		lbl1.setBounds(95, 10, 280, 30);
		panel.add(lbl1);
		lbl1.setForeground(new Color(0,175,210));
		lbl1.setFont(new Font("Comfortaa", Font.PLAIN, 13));
		{
			JLabel lbl2 = new JLabel("Check in the system's task tray.");
			lbl2.setBounds(95, 40, 280, 30);
			panel.add(lbl2);
			lbl2.setForeground(new Color(100,100,100));
			lbl2.setFont(new Font("Comfortaa", Font.PLAIN, 13));
		}

		JLabel lblOk = new JLabel("Ok");
		contentPanel.add(lblOk);
		lblOk.setForeground(Color.WHITE);
		lblOk.setFont(new Font("Comfortaa", Font.PLAIN, 13));
		lblOk.setHorizontalAlignment(SwingConstants.CENTER);
		lblOk.setHorizontalTextPosition(SwingConstants.CENTER);
		lblOk.setBounds(125, 140, 150, 30);
		lblOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		{
			JLabel okButtonImg = new JLabel(CofferRef.SMALL_BUTTON_IMG);
			contentPanel.add(okButtonImg);
			okButtonImg.setBounds(125, 140, 150, 30);
		}
		

	}
}
