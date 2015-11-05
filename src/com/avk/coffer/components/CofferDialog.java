package com.avk.coffer.components;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.avk.coffer.Coffer;
import com.avk.coffer.CofferReferences;
import com.avk.coffer.CofferScrollbarUI;

@SuppressWarnings("serial")
public class CofferDialog extends JDialog {

	// CofferDialog Button pane - Options
	public final static int CLOSE_OPTION = 0;
	public final static int YES_OPTION = 1;
	public final static int NO_OPTION = 2;
	public final static int CANCEL_OPTION = 3;
	public final static int OK_OPTION = 4;

	// CofferDialog Button pane - Option Types
	public final static int YES_NO_CANCEL_OPTIONS = 123;
	public final static int YES_NO_OPTIONS = 12;
	public final static int OK_CANCEL_OPTIONS = 43;

	public int selectedOption;
	private int xPressed, yPressed;

	private Dimension dialogDimensions;

	public CofferDialog(Window parent, boolean isModal, String title, String[] messages, int Option_type) {
		super(parent);
		if (isModal) {
			CofferDialog.this.setModal(true);
			CofferDialog.this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		}

		int length = messages.length;
		int scrollPaneHeight = Math.max(40, Math.min(length * 21, 125));
		selectedOption = 0;

		dialogDimensions = new Dimension(450, (110 + scrollPaneHeight));

		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(CofferReferences.CofferLightGrey);
		contentPanel.setLayout(null);

		JLabel lblX = new JLabel("X");
		lblX.setForeground(Color.WHITE);
		lblX.setFont(CofferReferences.Antipasto_Bold_15);
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setHorizontalTextPosition(SwingConstants.CENTER);
		lblX.setBounds(410, 5, 30, 30);
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CofferDialog.this.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblX.setForeground(CofferReferences.CofferBlue);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblX.setForeground(Color.WHITE);
			}
		});
		contentPanel.add(lblX);

		JLabel titleLbl = new JLabel(title);
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setBounds(0, 0, 450, 40);
		titleLbl.setOpaque(true);
		titleLbl.setBackground(CofferReferences.CofferDarkGrey);
		titleLbl.setForeground(Color.WHITE);
		titleLbl.setFont(CofferReferences.Comfortaa_Bold_15);
		titleLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xPressed = e.getX();
				yPressed = e.getY();
			}
		});
		titleLbl.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();

				CofferDialog.this.setLocation((x - xPressed), (y - yPressed));
			}
		});
		contentPanel.add(titleLbl);

		JPanel panel = new JPanel();
		panel.setBackground(CofferReferences.CofferLightGrey);
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		for (int i = 0; i < length; i++) {
			JLabel lbl = new JLabel(messages[i]);
			lbl.setForeground(Color.WHITE);
			lbl.setFont(CofferReferences.Comfortaa_Plain_14);
			panel.add(lbl);
			if (i != length - 1)
				panel.add(Box.createRigidArea(new Dimension(0, 5)));
		}

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(20, 55, 420, scrollPaneHeight);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setViewportBorder(null);

		JScrollBar vsb = scrollPane.getVerticalScrollBar();
		vsb.setUI(new CofferScrollbarUI());
		vsb.setPreferredSize(new Dimension(10, 125));
		vsb.setOpaque(false);
		vsb.setUnitIncrement(10);

		contentPanel.add(scrollPane);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setBackground(Color.WHITE);
		FlowLayout fl_buttonPanel = (FlowLayout) buttonPanel.getLayout();
		fl_buttonPanel.setAlignment(FlowLayout.TRAILING);
		buttonPanel.setBounds(5, dialogDimensions.height - 45, 440, 40);
		contentPanel.add(buttonPanel);

		Dimension d = new Dimension(100, 30);
		Font f = CofferReferences.Comfortaa_Plain_13;

		CofferButton okButton = new CofferButton("OK");
		okButton.setFont(f);
		okButton.setPreferredSize(d);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				selectedOption = OK_OPTION;
				CofferDialog.this.dispose();
			}
		});
		buttonPanel.add(okButton);

		CofferButton yesButton = new CofferButton("Yes");
		yesButton.setFont(f);
		yesButton.setPreferredSize(d);
		yesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				selectedOption = YES_OPTION;
				CofferDialog.this.dispose();
			}
		});
		buttonPanel.add(yesButton);

		CofferButton noButton = new CofferButton("No");
		noButton.setFont(f);
		noButton.setPreferredSize(d);
		noButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				selectedOption = NO_OPTION;
				CofferDialog.this.dispose();
			}
		});
		buttonPanel.add(noButton);

		CofferButton cancelButton = new CofferButton("Cancel");
		cancelButton.setFont(f);
		cancelButton.setPreferredSize(d);
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				selectedOption = CANCEL_OPTION;
				CofferDialog.this.dispose();
			}
		});
		buttonPanel.add(cancelButton);

		switch (Option_type) {

			case YES_NO_CANCEL_OPTIONS: {
				okButton.setVisible(false);
				break;
			}

			case YES_NO_OPTIONS: {
				okButton.setVisible(false);
				cancelButton.setVisible(false);
				break;
			}

			case OK_CANCEL_OPTIONS: {
				yesButton.setVisible(false);
				noButton.setVisible(false);
				break;
			}

			case OK_OPTION: {
				yesButton.setVisible(false);
				noButton.setVisible(false);
				cancelButton.setVisible(false);
				break;
			}
		}

		setContentPane(contentPanel);

		setBounds(0, 0, dialogDimensions.width, dialogDimensions.height);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(Coffer.frmcoffer);
		setModal(true);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);

	}

}