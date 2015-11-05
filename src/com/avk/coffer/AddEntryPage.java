package com.avk.coffer;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.avk.coffer.components.CofferButton;
import com.avk.coffer.components.CofferPasswordField;
import com.avk.coffer.components.CofferTextField;

@SuppressWarnings("serial")
public class AddEntryPage extends JPanel {
	private static CofferTextField titleField, usernameField, urlField;
	private static CofferPasswordField passwordField;
	private static CofferPasswordField confirmPasswordField;
	private String defaultStatus;
	private static JButton focusGrab;

	private static final int pageWidth = CofferSettings.COFFER_PAGE_SIZE.width;
	private static final int pageHeight = CofferSettings.COFFER_PAGE_SIZE.height;

	public AddEntryPage() {
		setPreferredSize(new Dimension(pageWidth, pageHeight));
		setOpaque(false);
		setLayout(null);

		focusGrab = new JButton("");
		focusGrab.setBounds(0, 0, 0, 0);
		focusGrab.grabFocus();
		add(focusGrab);

		JLabel lblTitle = new JLabel("Password Registry");
		lblTitle.setBorder(new EmptyBorder(0, 10, 0, 0));
		lblTitle.setFont(CofferReferences.Comfortaa_Bold_Italic_20);
		lblTitle.setForeground(CofferReferences.CofferBlue);
		lblTitle.setBounds(50, 25, 300, 50);
		add(lblTitle);

		titleField = new CofferTextField("Title", null);
		titleField.setBounds(((pageWidth - 320) / 2), 100, 320, 40);
		titleField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (titleField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Enter the Title of entry here...");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		add(titleField);

		urlField = new CofferTextField("URL", null);
		urlField.setBounds(((pageWidth - 320) / 2), 150, 320, 40);
		urlField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (urlField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Enter the login URL here...");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		add(urlField);

		usernameField = new CofferTextField("Username", null);
		usernameField.setBounds(((pageWidth - 320) / 2), 200, 320, 40);
		usernameField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (usernameField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Enter your Username here...");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		add(usernameField);

		passwordField = new CofferPasswordField("Password", null);
		passwordField.setBounds(((pageWidth - 320) / 2), 250, 320, 40);
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (passwordField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Enter your Password here...");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		add(passwordField);

		confirmPasswordField = new CofferPasswordField("Confirm Password", null);
		confirmPasswordField.setBounds(((pageWidth - 320) / 2), 300, 320, 40);
		confirmPasswordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (confirmPasswordField.getText().equals("")) {
					defaultStatus = Coffer.getStatus();
					Coffer.setStatus("Retype your password here...");
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Coffer.setStatus(defaultStatus);
			}
		});
		add(confirmPasswordField);

		CofferButton submit = new CofferButton("Submit");
		submit.setBounds(((pageWidth - 200) / 2), 350, 200, 40);
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					focusGrab.grabFocus();
					defaultStatus = Coffer.getStatus();

					String title = titleField.getText().trim();
					String username = usernameField.getText().trim();
					String password = passwordField.getText().trim();
					String conPass = confirmPasswordField.getText().trim();
					String url = urlField.getText().trim();

					url = url.equals("") ? "no_url" : url;

					if (title.equals("")) {
						Coffer.setStatus("I guess you don't know that Title is mandatory.");
						titleField.setValid(false);
						usernameField.setValid(true);
						passwordField.setValid(true);
						confirmPasswordField.setValid(true);
					} else if (username.equals("")) {
						Coffer.setStatus("Comm'on you forgot entering your username?    O.o");
						titleField.setValid(true);
						usernameField.setValid(false);
						passwordField.setValid(true);
						confirmPasswordField.setValid(true);
					} else if (password.equals("")) {
						Coffer.setStatus("Pick a strong password, empty ones dont serve the purpose.");
						titleField.setValid(true);
						usernameField.setValid(true);
						passwordField.setValid(false);
						confirmPasswordField.setValid(true);
					} else if (conPass.equals("")) {
						Coffer.setStatus("All that's needed is to retype your password!");
						titleField.setValid(true);
						usernameField.setValid(true);
						passwordField.setValid(true);
						confirmPasswordField.setValid(false);
					} else if (!password.equals(conPass)) {
						Coffer.setStatus("Try again! Your passwords didn't match.    :(");
						passwordField.setText("");
						passwordField.grabFocus();
						confirmPasswordField.setText("");
						titleField.setValid(true);
						usernameField.setValid(true);
						passwordField.setValid(false);
						confirmPasswordField.setValid(false);
					} else {
						titleField.setValid(true);
						usernameField.setValid(true);
						passwordField.setValid(true);
						confirmPasswordField.setValid(true);

						CofferPasswordEntry p = new CofferPasswordEntry();

						p.setTitle(title);
						p.setUrl(url);
						p.setUsername(username);
						p.setPassword(password);

						p.writeToFile();

						DashBoard.setSelection(DashBoard.add_entry_page, true);
						DashBoard.setSelection(DashBoard.all_passwords_page, true);
						Coffer.setStatus("Entry made into your coffer.");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(submit);

		JLabel lblClear = new JLabel("Reset all fields");
		lblClear.setHorizontalAlignment(SwingConstants.CENTER);
		lblClear.setFont(CofferReferences.Comfortaa_Plain_13);
		lblClear.setForeground(CofferReferences.CofferBlue);
		lblClear.setBounds(590, 450, 150, 20);
		lblClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DashBoard.setSelection(DashBoard.add_entry_page, true);
			}
		});
		add(lblClear);

	}
}
