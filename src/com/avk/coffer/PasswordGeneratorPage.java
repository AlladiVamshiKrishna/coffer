package com.avk.coffer;

import java.awt.Dimension;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.avk.coffer.components.CofferButton;
import com.avk.coffer.components.CofferCheckBox;
import com.avk.coffer.components.CofferNumberField;

@SuppressWarnings("serial")
public class PasswordGeneratorPage extends JPanel {
	private JTextField passwordDisp;
	private CofferNumberField numChars;

	private static final int pageWidth = CofferSettings.COFFER_PAGE_SIZE.width;
	private static final int pageHeight = CofferSettings.COFFER_PAGE_SIZE.height;

	/**
	 * Create the panel.
	 */
	public PasswordGeneratorPage() {
		setOpaque(false);
		setLayout(null);
		setPreferredSize(new Dimension(pageWidth, pageHeight));

		JLabel lblTitle = new JLabel("Password Factory");
		lblTitle.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblTitle.setFont(CofferReferences.Comfortaa_Bold_Italic_20);
		lblTitle.setForeground(CofferReferences.CofferBlue);
		lblTitle.setBounds(50, 25, 300, 50);
		add(lblTitle);

		JLabel lblText1 = new JLabel("Your Password will have ");
		lblText1.setForeground(CofferReferences.CofferVeryLightGrey);
		lblText1.setFont(CofferReferences.Comfortaa_Plain_15);
		lblText1.setBounds(130, 100, 185, 30);
		add(lblText1);

		numChars = new CofferNumberField();
		numChars.setBounds(315, 90, 140, 30);
		numChars.setNum(15);
		numChars.setMinNum(5);
		numChars.setMaxNum(50);
		add(numChars);

		JLabel lblText2 = new JLabel("characters and will include");
		lblText2.setForeground(CofferReferences.CofferVeryLightGrey);
		lblText2.setFont(CofferReferences.Comfortaa_Plain_15);
		lblText2.setBounds(455, 100, 215, 30);
		add(lblText2);

		CofferCheckBox caps = new CofferCheckBox(true, "Uppercase letters");
		caps.setBounds((pageWidth - 400) / 2, 150, 200, 50);
		add(caps);

		CofferCheckBox lower = new CofferCheckBox(true, "Lowercase letters");
		lower.setBounds((pageWidth - 400) / 2 + 200, 150, 200, 50);
		add(lower);

		CofferCheckBox nums = new CofferCheckBox(true, "Digits");
		nums.setBounds((pageWidth - 400) / 2, 200, 200, 50);
		add(nums);

		CofferCheckBox syms = new CofferCheckBox(true, "Special Characters");
		syms.setBounds((pageWidth - 400) / 2 + 200, 200, 200, 50);
		add(syms);

		passwordDisp = new JTextField();
		passwordDisp.setText("Your Password will be shown here.    :)");
		passwordDisp.setEditable(false);
		passwordDisp.setForeground(CofferReferences.CofferVeryLightGrey);
		passwordDisp.setFont(CofferReferences.Comfortaa_Bold_15);
		passwordDisp.setHorizontalAlignment(SwingConstants.CENTER);
		passwordDisp.setOpaque(false);
		passwordDisp.setBorder(null);
		passwordDisp.setBounds((pageWidth - 400) / 2, 270, 400, 30);
		add(passwordDisp);

		JLabel passwordBlank = new JLabel(CofferReferences.TEXT_BLANK);
		passwordBlank.setBounds((pageWidth - 400) / 2, 290, 400, 10);
		add(passwordBlank);

		JLabel lblClipBoard = new JLabel(CofferReferences.CLIPBOARD);
		lblClipBoard.setBounds((pageWidth - 400) / 2 + 410, 270, 30, 30);
		lblClipBoard.setVisible(false);
		lblClipBoard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CofferReferences.SYS_CLIPBOARD.setContents(new StringSelection(passwordDisp.getText()), null);
				Coffer.setStatus("Password saved to clipboard.    :)");
			}
		});
		add(lblClipBoard);

		CofferButton genButton = new CofferButton("Generate");
		genButton.setBounds((pageWidth - 200) / 2, 340, 200, 40);
		genButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (caps.isChecked() || lower.isChecked() || nums.isChecked() || syms.isChecked()) {
					int n = numChars.getNum();
					Random random = new Random();
					String ranPass = "";
					while (ranPass.length() < n) {
						int ran = random.nextInt(126);
						if (ran < 33)
							continue;
						else if ((!caps.isChecked()) && Character.isUpperCase(ran)) {
							continue;
						} else if ((!lower.isChecked()) && Character.isLowerCase(ran)) {
							continue;
						} else if ((!nums.isChecked()) && Character.isDigit(ran)) {
							continue;
						} else if ((!syms.isChecked()) && !Character.isLetterOrDigit(ran)) {
							continue;
						}
						ranPass += (char) ran;
					}
					passwordDisp.setText(ranPass);
					lblClipBoard.setVisible(true);
					Coffer.setStatus("Click on the icon to save the password to clipboard.    :]");
				} else {
					Coffer.setStatus("Choose atleast one of the checkboxes.    :|");
				}
			}
		});
		add(genButton);

		JLabel lblClear = new JLabel("Reset all fields");
		lblClear.setHorizontalAlignment(SwingConstants.CENTER);
		lblClear.setFont(CofferReferences.Comfortaa_Plain_13);
		lblClear.setForeground(CofferReferences.CofferBlue);
		lblClear.setBounds(590, 450, 150, 20);
		lblClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DashBoard.setSelection(DashBoard.password_generator_page, true);
			}
		});
		add(lblClear);

	}
}
