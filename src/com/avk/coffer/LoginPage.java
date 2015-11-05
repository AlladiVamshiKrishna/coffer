package com.avk.coffer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.MessageDigest;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.avk.coffer.components.CofferButton;
import com.avk.coffer.components.CofferDialog;
import com.avk.coffer.components.CofferPasswordField;
import com.avk.coffer.components.CofferTextField;

@SuppressWarnings("serial")
public class LoginPage extends JPanel {
	private CofferTextField usernameField;
	private CofferPasswordField passwordField;
	private String defaultStatus;

	private static final int pageWidth = CofferSettings.COFFER_DASHBOARD_SIZE.width;
	private static final int pageHeight = CofferSettings.COFFER_DASHBOARD_SIZE.height;

	/**
	 * Create the panel.
	 */
	public LoginPage() {
		setPreferredSize(new Dimension(pageWidth, pageHeight));
		setOpaque(false);
		setLayout(null);
		defaultStatus = Coffer.getStatus();

		JButton focusGrab = new JButton("");
		focusGrab.setBounds(0, 0, 0, 0);
		focusGrab.grabFocus();
		add(focusGrab);

		JLabel lblCoffer = new JLabel("Coffer");
		lblCoffer.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoffer.setVerticalTextPosition(SwingConstants.TOP);
		lblCoffer.setForeground(Color.WHITE);
		lblCoffer.setIconTextGap(90);
		lblCoffer.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblCoffer.setFont(CofferReferences.Comfortaa_Bold_80);
		lblCoffer.setBounds(0, 90, pageWidth, 80);
		add(lblCoffer);

		usernameField = new CofferTextField("Username", null);
		usernameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (usernameField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Your Username please.    :|");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		usernameField.setBounds(((pageWidth - 320) / 2), 230, 320, 40);
		add(usernameField);

		passwordField = new CofferPasswordField("Password", null);
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (passwordField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Your Password please.    :|");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		passwordField.setBounds(((pageWidth - 320) / 2), 280, 320, 40);
		add(passwordField);

		CofferButton submit = new CofferButton("Submit");
		submit.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					focusGrab.grabFocus();
					defaultStatus = Coffer.getStatus();
					String username = usernameField.getText().trim();
					String password = passwordField.getText().trim();
					String passPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=|\\/,.;:])(?=\\S+$).{10,}$";

					if (username.equalsIgnoreCase("")) {
						Coffer.setStatus("Username please.    :|");
						usernameField.setValid(false);
						passwordField.setValid(true);
					} else if (password.equalsIgnoreCase("")) {
						Coffer.setStatus("Did you forget entering password?    :/");
						usernameField.setValid(true);
						passwordField.setValid(false);
					} else if (!password.matches(passPattern)) {
						Coffer.setStatus("Don't you remember the Password Policy?    O.o");
						usernameField.setValid(true);
						passwordField.setValid(false);
						passwordField.setText("");
						passwordField.grabFocus();
					} else {
						MessageDigest digest = MessageDigest.getInstance("SHA-256");
						byte[] hash = digest.digest(password.getBytes("UTF-8"));

						StringTokenizer st = new StringTokenizer(CofferCrypt.decryptFromFile_Key(new String(hash).substring(0, 16), new File("./Coffer/.cofferkey")), "|");
						String actUsername = st.nextToken();

						CofferCrypt.setCofferSeed(Long.parseLong(st.nextToken()));

						if (username.equals(actUsername)) {
							Coffer.login();
							Coffer.setStatus("Good to see you back.    :)");
						} else {
							usernameField.setText("");
							passwordField.setText("");
							usernameField.grabFocus();
							usernameField.setValid(false);
							passwordField.setValid(false);
							Coffer.setStatus("Sorry can't log you in. Wrong credentials.    :'(");
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		submit.setBounds(((pageWidth - 200) / 2), 330, 200, 40);
		add(submit);

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
