package com.avk.coffer;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.avk.coffer.components.CofferDialog;
import com.avk.coffer.components.CofferPopupFrame;

@SuppressWarnings("serial")
public class CofferPasswordEntryElement extends JPanel {

	private JLabel entry, launch, export, delete;
	private JPanel optionsPanel;
	private JPanel entryPanel;

	public CofferPasswordEntryElement(CofferPasswordEntry p) {

		super();
		setBackground(CofferReferences.CofferDarkGrey);
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setPreferredSize(new Dimension(600, 40));
		setMaximumSize(new Dimension(600, 40));
		setSize(new Dimension(600, 40));

		entryPanel = new JPanel();
		entryPanel.setPreferredSize(new Dimension(500, 40));
		entryPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		entryPanel.setOpaque(false);

		entry = new JLabel(p.getTitle() + " [" + p.getUsername() + "]", CofferReferences.SINGLE_KEY, SwingConstants.LEFT);
		entry.setPreferredSize(new Dimension(450, 30));
		entry.setMinimumSize(new Dimension(150, 30));
		entry.setMaximumSize(new Dimension(150, 30));
		entry.setForeground(Color.WHITE);
		entry.setFont(CofferReferences.Comfortaa_Plain_15);
		entry.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {
				optionsPanel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				optionsPanel.setVisible(false);
			}

			public void mouseClicked(MouseEvent e) {
				if (e.isControlDown() && SwingUtilities.isLeftMouseButton(e)) {
					CofferReferences.SYS_CLIPBOARD.setContents(new StringSelection(p.getUsername()), null);
					Coffer.setStatus("Username copied to clipboard");
				} else if (e.isAltDown() && SwingUtilities.isLeftMouseButton(e)) {
					CofferReferences.SYS_CLIPBOARD.setContents(new StringSelection(p.getPassword()), null);
					Coffer.setStatus("Password copied to clipboard");
				} else if (e.getClickCount() == 1) {
					Coffer.setDisable(true);
					new CofferPasswordDisplayFrame(p);
					Coffer.setDisable(false);
				}
			}

		});

		entryPanel.add(entry);

		optionsPanel = new JPanel();
		optionsPanel.setPreferredSize(new Dimension(150, 40));
		optionsPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
		optionsPanel.setVisible(false);
		optionsPanel.setOpaque(false);

		export = new JLabel(CofferReferences.EXPORT);
		export.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {
				optionsPanel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				optionsPanel.setVisible(false);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				CofferPopupFrame cpf = new CofferPopupFrame(Coffer.frmcoffer, true, new Dimension(500, 500));
				cpf.setVisible(true);
			}
		});
		optionsPanel.add(export);

		launch = new JLabel(CofferReferences.LAUNCH);
		launch.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {
				optionsPanel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				optionsPanel.setVisible(false);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					String url = p.getUrl();
					if (!url.equals("no_url"))
						Desktop.getDesktop().browse(URI.create(url));

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		if (!p.getUrl().equals("no_url"))
			optionsPanel.add(launch);

		delete = new JLabel(CofferReferences.DELETE);
		delete.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {
				optionsPanel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				optionsPanel.setVisible(false);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					String[] msgs = { "You are about to delete \"" + p.getTitle() + "\" entry.", "Do you want to continue?" };
					Coffer.setDisable(true);
					CofferDialog deleteDialog = new CofferDialog(Coffer.frmcoffer, true, "Delete Confirmation", msgs, CofferDialog.YES_NO_OPTIONS);
					Coffer.setDisable(false);

					if (deleteDialog.selectedOption == CofferDialog.YES_OPTION) {
						String new_user_coffer = "", user_coffer = CofferCrypt.decryptFromFile_Index(CofferCrypt.getCofferKeyIndex(), new File("./Coffer/user's.coffer"));
						StringTokenizer st = new StringTokenizer(user_coffer, "\n");

						while (st.hasMoreTokens()) {
							String token = st.nextToken();
							if (token.contains(p.getId())) {
								StringTokenizer strTknzr = new StringTokenizer(token, "|");
								String fileNo = strTknzr.nextToken();
								new File("./Coffer/" + fileNo + ".cofferpass").delete();
							} else {
								new_user_coffer = new_user_coffer.equals("") ? token : new_user_coffer + "\n" + token;
							}
						}

						if (new_user_coffer.equals(""))
							new_user_coffer = "no_passwords";

						CofferCrypt.encrypt2File_Index(CofferCrypt.getCofferKeyIndex(), new_user_coffer, new File("./Coffer/user's.coffer"));
						DashBoard.setSelection(DashBoard.all_passwords_page, true);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		optionsPanel.add(delete);

		add(entryPanel);
		add(optionsPanel);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {
				optionsPanel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				optionsPanel.setVisible(false);
			}

		});
	}

}
