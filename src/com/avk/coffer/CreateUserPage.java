package com.avk.coffer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.MessageDigest;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.avk.coffer.components.CofferButton;
import com.avk.coffer.components.CofferDialog;
import com.avk.coffer.components.CofferPasswordField;
import com.avk.coffer.components.CofferTextField;

@SuppressWarnings("serial")
public class CreateUserPage extends JPanel {
	private CofferTextField usernameField;
	private CofferPasswordField passwordField;
	private CofferPasswordField confirmPasswordField;
	private String defaultStatus;

	private static final int pageWidth = CofferSettings.COFFER_DASHBOARD_SIZE.width;
	private static final int pageHeight = CofferSettings.COFFER_DASHBOARD_SIZE.height;

	/**
	 * Create the panel.
	 */
	public CreateUserPage() {
		setPreferredSize(new Dimension(pageWidth, pageHeight));
		setOpaque(false);
		setLayout(null);

		JButton focusGrab = new JButton("");
		focusGrab.setBounds(0, 0, 0, 0);
		focusGrab.grabFocus();
		add(focusGrab);

		usernameField = new CofferTextField("Username", null);
		usernameField.setBounds(((pageWidth - 320) / 2), 175, 320, 40);
		usernameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (usernameField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Enter your Username here.    O:)");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		add(usernameField);

		passwordField = new CofferPasswordField("Password", null);
		passwordField.setBounds(((pageWidth - 320) / 2), 225, 320, 40);
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (passwordField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Enter your Password here.    O:)");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		add(passwordField);

		confirmPasswordField = new CofferPasswordField("Confirm Password", null);
		confirmPasswordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (confirmPasswordField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Retype your password here.    :)");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		confirmPasswordField.setBounds(((pageWidth - 320) / 2), 275, 320, 40);
		add(confirmPasswordField);

		CofferButton submit = new CofferButton("Submit");
		submit.setBounds(((pageWidth - 200) / 2), 325, 200, 40);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					focusGrab.grabFocus();
					defaultStatus = Coffer.getStatus();
					String username = usernameField.getText().trim();
					String password = passwordField.getText().trim();
					String conPass = confirmPasswordField.getText().trim();
					String passPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=|\\/,.;:])(?=\\S+$).{10,}$";

					if (username.equals("")) {
						Coffer.setStatus("Pick a username.    :|");
						usernameField.setValid(false);
						passwordField.setValid(true);
						confirmPasswordField.setValid(true);
					} else if (password.equals("")) {
						Coffer.setStatus("Pick a password.    :|");
						usernameField.setValid(true);
						passwordField.setValid(false);
						confirmPasswordField.setValid(true);
					} else if (!password.matches(passPattern)) {
						Coffer.setStatus("Pssst!, Have a look at Password Policy.    O:)");
						usernameField.setValid(true);
						passwordField.setValid(false);
						confirmPasswordField.setValid(true);
						passwordField.setText("");
						passwordField.grabFocus();
					} else if (conPass.equals("")) {
						Coffer.setStatus("Please confirm your password.    :)");
						usernameField.setValid(true);
						passwordField.setValid(true);
						confirmPasswordField.setValid(false);
					} else if (!password.equals(conPass)) {
						Coffer.setStatus("Passwords did not match.    :(");
						passwordField.setText("");
						passwordField.grabFocus();
						confirmPasswordField.setText("");
						usernameField.setValid(true);
						passwordField.setValid(false);
						confirmPasswordField.setValid(false);
					} else {

						MessageDigest digest = MessageDigest.getInstance("SHA-256");
						byte[] hash = digest.digest(password.getBytes("UTF-8"));

						Coffer.setStatus("Credentials Submited.    :w");

						long timeStamp = System.currentTimeMillis();
						int key = (int) timeStamp % 100000;

						CofferCrypt.encrypt2File_Key(new String(hash).substring(0, 16), username + "|" + timeStamp, new File("./Coffer/.cofferkey"));
						CofferCrypt.setCofferSeed(timeStamp);
						CofferCrypt.encrypt2File_Index(key, "no_passwords", new File("./Coffer/user's.coffer"));

						Coffer.login();
						Coffer.setStatus("You can make entries in \"Add a Password\" tab.");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(submit);

		JLabel lblCofferlogo = new JLabel();
		lblCofferlogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCofferlogo.setForeground(Color.WHITE);
		lblCofferlogo.setIconTextGap(90);
		lblCofferlogo.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblCofferlogo.setFont(CofferReferences.Comfortaa_Bold_80);
		lblCofferlogo.setText("Coffer");
		lblCofferlogo.setBounds(0, 60, pageWidth, 80);
		add(lblCofferlogo);

		JLabel lblPolicy = new JLabel("Password Policy");
		lblPolicy.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolicy.setFont(CofferReferences.Comfortaa_Plain_13);
		lblPolicy.setForeground(CofferReferences.CofferBlue);
		lblPolicy.setBounds(pageWidth - 200, pageHeight - 75, 150, 20);
		lblPolicy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] strs = { "-> Master password should be atleast 10 characters.", "-> It should include atleast 1 for each of", "        * Upper Case Letter", "        * Lower Case Letter",
						"        * Number and", "        * Symbol" };
				new CofferDialog(Coffer.frmcoffer, true, "Password Policy", strs, CofferDialog.OK_OPTION);
			}
		});

		add(lblPolicy);
	}
}
