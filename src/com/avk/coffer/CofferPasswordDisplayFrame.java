package com.avk.coffer;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.avk.coffer.components.CofferButton;
import com.avk.coffer.components.CofferDialog;
import com.avk.coffer.components.CofferPasswordBlank;
import com.avk.coffer.components.CofferPopupFrame;
import com.avk.coffer.components.CofferTextBlank;
import com.avk.coffer.components.CofferToggleSwitch;
import com.avk.coffer.components.listeners.CofferPasswordBlankEditListener;
import com.avk.coffer.components.listeners.CofferTextBlankEditListener;
import com.avk.coffer.components.listeners.CofferToggleSwitchListener;

@SuppressWarnings("serial")
public class CofferPasswordDisplayFrame extends CofferPopupFrame {

	private JPanel contentPanel;

	private static JButton focusGrab;
	private static CofferTextBlank titleBlank, usernameBlank, urlBlank;
	private static CofferPasswordBlank passwordBlank, confirmPasswordBlank;
	private static CofferToggleSwitch editSwitch;
	private static CofferButton submit;

	private String defaultStatus = "All the details of the entry are here Boss.";

	private static int frameWidth = 500, frameHeight = 550;

	private boolean anyBlankEdited, titleEdited, urlEdited, usernameEdited, passwordEdited;

	public CofferPasswordDisplayFrame(CofferPasswordEntry p) {
		super(Coffer.frmcoffer, true, new Dimension(frameWidth, frameHeight));

		setTitle(p.getTitle() + " Details");
		setStatus(defaultStatus);

		contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		contentPanel.setLayout(null);

		focusGrab = new JButton("");
		focusGrab.setBounds(0, 0, 0, 0);
		focusGrab.grabFocus();
		contentPanel.add(focusGrab);

		editSwitch = new CofferToggleSwitch("Edit");
		editSwitch.setBounds(frameWidth - 100, 30, 80, 20);
		editSwitch.addCofferToggleSwitchListener(new CofferToggleSwitchListener() {

			@Override
			public void onToggle() {
				if (editSwitch.isOn()) {
					titleBlank.setEditable(true);
					urlBlank.setEditable(true);
					usernameBlank.setEditable(true);
					passwordBlank.setEditable(true);
					submit.setVisible(true);
					anyBlankEdited = titleEdited = urlEdited = usernameEdited = passwordEdited = false;
				} else {
					if (anyBlankEdited) {
						String[] msgs = { "Your modifications will be discarded.", "Do you want to continue?" };
						setDisable(true);
						CofferDialog discardDialog = new CofferDialog(CofferPasswordDisplayFrame.this, true, "Discard Confirmation", msgs, CofferDialog.YES_NO_OPTIONS);
						setDisable(false);
						if (discardDialog.selectedOption == CofferDialog.YES_OPTION) {
							if (titleEdited)
								titleBlank.reset();
							if (urlEdited)
								urlBlank.reset();
							if (usernameEdited)
								usernameBlank.reset();
							if (passwordEdited) {
								passwordBlank.reset();
								confirmPasswordBlank.setVisible(false);
								confirmPasswordBlank.setEditable(true);
							}
						} else {
							editSwitch.toggle();
							return;
						}
					}
					titleBlank.setEditable(false);
					urlBlank.setEditable(false);
					usernameBlank.setEditable(false);
					passwordBlank.setEditable(false);
					submit.setVisible(false);
				}
			}

		});
		contentPanel.add(editSwitch);

		titleBlank = new CofferTextBlank("Title", p.getTitle());
		titleBlank.setBounds((frameWidth - 320) / 2, 80, 320, 50);
		titleBlank.setEditable(false);
		titleBlank.addCofferTextBlankEditListener(new CofferTextBlankEditListener() {

			@Override
			public void onValueChange() {
				anyBlankEdited = titleEdited = true;
			}

		});
		contentPanel.add(titleBlank);

		urlBlank = new CofferTextBlank("URL", p.getUrl());
		urlBlank.setBounds((frameWidth - 320) / 2, 140, 320, 50);
		urlBlank.setEditable(false);
		urlBlank.addCofferTextBlankEditListener(new CofferTextBlankEditListener() {

			@Override
			public void onValueChange() {
				anyBlankEdited = urlEdited = true;
			}

		});
		contentPanel.add(urlBlank);

		usernameBlank = new CofferTextBlank("Username", p.getUsername());
		usernameBlank.setBounds((frameWidth - 320) / 2, 200, 320, 50);
		usernameBlank.setEditable(false);
		usernameBlank.addCofferTextBlankEditListener(new CofferTextBlankEditListener() {

			@Override
			public void onValueChange() {
				anyBlankEdited = usernameEdited = true;
			}

		});
		contentPanel.add(usernameBlank);

		passwordBlank = new CofferPasswordBlank("Password", p.getPassword());
		passwordBlank.setBounds((frameWidth - 320) / 2, 260, 320, 50);
		passwordBlank.setEditable(false);
		passwordBlank.addCofferPasswordBlankEditListener(new CofferPasswordBlankEditListener() {

			@Override
			public void onValueChange() {
				anyBlankEdited = passwordEdited = true;
				confirmPasswordBlank.setVisible(true);
				confirmPasswordBlank.setEditable(true);
			}

		});
		contentPanel.add(passwordBlank);

		confirmPasswordBlank = new CofferPasswordBlank("Confirm Password", p.getPassword());
		confirmPasswordBlank.setBounds((frameWidth - 320) / 2, 320, 320, 50);
		confirmPasswordBlank.setEditable(false);
		confirmPasswordBlank.setVisible(false);
		contentPanel.add(confirmPasswordBlank);

		submit = new CofferButton("Submit");
		submit.setBounds((frameWidth - 200) / 2, 400, 200, 40);
		submit.setVisible(false);
		submit.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					focusGrab.grabFocus();
					defaultStatus = CofferPasswordDisplayFrame.this.getStatus();

					String title = titleBlank.getText().trim();
					String url = urlBlank.getText().trim();
					String username = usernameBlank.getText().trim();
					String password = passwordBlank.getText().trim();
					String conPass = confirmPasswordBlank.getText().trim();

					url = url.equals("") ? "no_url" : url;

					if (title.equals("")) {
						setStatus("I guess you don't know that Title is mandatory.");
						titleBlank.setValid(false);
						usernameBlank.setValid(true);
						passwordBlank.setValid(true);
						confirmPasswordBlank.setValid(true);
					} else if (username.equals("")) {
						setStatus("Comm'on you forgot entering your username?    O.o");
						titleBlank.setValid(true);
						usernameBlank.setValid(false);
						passwordBlank.setValid(true);
						confirmPasswordBlank.setValid(true);
					} else if (password.equals("")) {
						setStatus("Pick a strong password, empty ones don't serve the purpose.");
						titleBlank.setValid(true);
						usernameBlank.setValid(true);
						passwordBlank.setValid(false);
						confirmPasswordBlank.setValid(true);
					} else if (conPass.equals("")) {
						setStatus("All that's needed is to retype your password!");
						titleBlank.setValid(true);
						usernameBlank.setValid(true);
						passwordBlank.setValid(true);
						confirmPasswordBlank.setValid(false);
					} else if (!password.equals(conPass)) {
						setStatus("Try again! Your passwords didn't match.    :(");
						passwordBlank.setText("");
						passwordBlank.grabFocus();
						confirmPasswordBlank.setText("");
						titleBlank.setValid(true);
						usernameBlank.setValid(true);
						passwordBlank.setValid(false);
						confirmPasswordBlank.setValid(false);
					} else {
						titleBlank.setValid(true);
						usernameBlank.setValid(true);
						passwordBlank.setValid(true);
						confirmPasswordBlank.setValid(true);

						p.setTitle(title);
						p.setUrl(url);
						p.setUsername(username);
						p.setPassword(password);
						p.writeToFile();

						String[] msgs = { "Congratulations!", "Password entry edited successfully." };
						setDisable(true);
						new CofferDialog(CofferPasswordDisplayFrame.this, true, "Success Notification", msgs, CofferDialog.OK_OPTION);
						setDisable(false);
						CofferPasswordDisplayFrame.this.dispose();

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		contentPanel.add(submit);

		setContentPane(contentPanel);

		setVisible(true);
	}

}
