package com.avk.coffer.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.avk.coffer.CofferReferences;

@SuppressWarnings("serial")
public class CofferPopupFrame extends JDialog {

	private final JPanel popupFrame;
	private int xPressed, yPressed;
	private int frameWidth = 300, frameHeight = 300;

	Container contentPanel;
	private static JPanel disablePanel;
	private static JLabel lblStatus, titleLbl;
	private static JButton focusGrab;

	public CofferPopupFrame(Window owner, boolean isModal, Dimension d) {
		super(owner);

		if (isModal) {
			CofferPopupFrame.this.setModal(true);
			CofferPopupFrame.this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		}

		frameWidth = d.width != 0 ? d.width : frameWidth;
		frameHeight = d.height != 0 ? d.height : frameWidth;

		JLayeredPane frmContent = new JLayeredPane();
		frmContent.setLocation(0, 0);
		frmContent.setSize(new Dimension(frameWidth, frameHeight));
		frmContent.setPreferredSize(new Dimension(frameWidth, frameHeight));

		disablePanel = new JPanel();
		disablePanel.setBackground(new Color(50, 50, 50, 100));
		disablePanel.setBounds(0, 0, frameWidth, frameHeight);
		disablePanel.setVisible(false);
		disablePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				disablePanel.grabFocus();
			}
		});
		frmContent.add(disablePanel);

		popupFrame = new JPanel();
		popupFrame.setBorder(new LineBorder(CofferReferences.CofferVeryLightGrey));
		popupFrame.setBackground(CofferReferences.CofferLightGrey);
		popupFrame.setSize(new Dimension(frameWidth, frameHeight));

		JLayeredPane titleBar = new JLayeredPane();
		titleBar.setPreferredSize(new Dimension(frameWidth - 2, 40));

		JLabel lblX = new JLabel("X");
		lblX.setForeground(Color.WHITE);
		lblX.setFont(CofferReferences.Antipasto_Bold_15);
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setHorizontalTextPosition(SwingConstants.CENTER);
		lblX.setBounds(frameWidth - 35, 5, 30, 30);
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CofferPopupFrame.this.dispose();
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

		titleBar.add(lblX);

		titleLbl = new JLabel("");
		titleLbl.setOpaque(true);
		titleLbl.setBorder(new EmptyBorder(0, 25, 0, 0));
		titleLbl.setSize(new Dimension(frameWidth, 40));
		titleLbl.setBackground(CofferReferences.CofferDarkGrey);
		titleLbl.setForeground(Color.WHITE);
		titleLbl.setFont(CofferReferences.Comfortaa_Bold_Italic_16);
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

				CofferPopupFrame.this.setLocation((x - xPressed), (y - yPressed));
			}
		});

		titleBar.add(titleLbl);

		focusGrab = new JButton("");
		focusGrab.setBounds(0, 0, 0, 0);
		focusGrab.grabFocus();
		popupFrame.setLayout(new BorderLayout(0, 0));
		titleBar.add(focusGrab);

		popupFrame.add(titleBar, BorderLayout.NORTH);

		contentPanel = new JPanel();
		((JComponent) contentPanel).setOpaque(false);
		contentPanel.setBounds(0, 40, frameWidth, frameHeight - 40);
		popupFrame.add(contentPanel, BorderLayout.CENTER);

		lblStatus = new JLabel("");
		lblStatus.setForeground(CofferReferences.CofferVeryLightGrey);
		lblStatus.setBorder(new EmptyBorder(0, 20, 0, 0));
		lblStatus.setPreferredSize(new Dimension(frameWidth, 20));
		lblStatus.setFont(CofferReferences.Comfortaa_Italic_13);

		popupFrame.add(lblStatus, BorderLayout.SOUTH);

		frmContent.add(popupFrame);

		CofferPopupFrame.super.setContentPane(frmContent);

		setMinimumSize(new Dimension(300, 300));
		setSize(frameWidth, frameHeight);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setResizable(false);
		setUndecorated(true);
		setLocationRelativeTo(null);

	}

	@Override
	public Container getContentPane() {
		return contentPanel;
	}

	@Override
	public void setContentPane(Container contentPane) {
		contentPane.setBounds(0, 40, frameWidth, frameHeight - 40);
		contentPanel = contentPane;
		popupFrame.add(contentPanel);
		CofferPopupFrame.this.repaint();

	}

	public void setSize(Dimension d) {
		frameWidth = d.width;
		frameHeight = d.height;
		CofferPopupFrame.super.setSize(d);
		CofferPopupFrame.this.repaint();
	}

	public static void setDisable(boolean flag) {
		disablePanel.setVisible(flag);
	}

	public void setTitle(String title) {
		titleLbl.setText(title);
	}

	public String getTitle() {
		return titleLbl.getText();
	}

	protected void setStatus(String string) {
		lblStatus.setText(string);
	}

	protected String getStatus() {
		return lblStatus.getText();
	}

}
