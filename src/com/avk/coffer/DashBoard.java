package com.avk.coffer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.avk.coffer.components.CofferTabLabel;

@SuppressWarnings("serial")
public class DashBoard extends JPanel {

	protected static final String all_passwords_page = "AllPasswordsPage";
	protected static final String add_entry_page = "AddEntryPage";
	protected static final String password_generator_page = "PasswordGeneratorPage";
	protected static final String settings_page = "SettingsPage";
	protected static final String about_page = "AboutPage";

	private static JPanel tabsDisplay, displayPanel;
	private static CofferTabLabel allPasswordsTab, addPasswordTab, generatePasswordTab, settingsTab, aboutTab;
	private static CardLayout cl;

	private static final int pageWidth = CofferSettings.COFFER_DASHBOARD_SIZE.width;
	private static final int pageHeight = CofferSettings.COFFER_DASHBOARD_SIZE.height;

	private static HashMap<String, CofferTabLabel> tabs = new HashMap<String, CofferTabLabel>();

	public DashBoard() {
		super();
		setOpaque(false);
		setPreferredSize(new Dimension(pageWidth, pageHeight));
		setBorder(new MatteBorder(1, 0, 0, 0, CofferReferences.CofferDarkGrey));
		setLayout(null);

		tabsDisplay = new JPanel();
		tabsDisplay.setBackground(CofferReferences.CofferDarkGrey);
		tabsDisplay.setBounds(0, 1, 200, pageHeight);
		tabsDisplay.setLayout(null);
		add(tabsDisplay);

		allPasswordsTab = new CofferTabLabel("All My Passwords", CofferReferences.MULTIPLE_KEYS);
		allPasswordsTab.setBounds(0, 0, 200, 40);
		allPasswordsTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setSelection(all_passwords_page, true);
			}
		});
		tabsDisplay.add(allPasswordsTab);
		tabs.put(all_passwords_page, allPasswordsTab);

		addPasswordTab = new CofferTabLabel("Add a Password", CofferReferences.ADD_KEY);
		addPasswordTab.setBounds(0, 40, 200, 40);
		addPasswordTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setSelection(add_entry_page, false);
			}
		});
		tabsDisplay.add(addPasswordTab);
		tabs.put(add_entry_page, addPasswordTab);

		generatePasswordTab = new CofferTabLabel("Password Generator", CofferReferences.KEY_GEN);
		generatePasswordTab.setBounds(0, 80, 200, 40);
		generatePasswordTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setSelection(password_generator_page, false);
			}
		});
		tabsDisplay.add(generatePasswordTab);
		tabs.put(password_generator_page, generatePasswordTab);

		settingsTab = new CofferTabLabel("Settings", CofferReferences.GEAR);
		settingsTab.setBounds(0, pageHeight - 80, 200, 40);
		settingsTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setSelection(settings_page, false);
			}
		});
		tabsDisplay.add(settingsTab);
		tabs.put(settings_page, settingsTab);

		aboutTab = new CofferTabLabel("About Coffer", CofferReferences.COFFER_LOGO_SMALL);
		aboutTab.setBounds(0, pageHeight - 40, 200, 40);
		aboutTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setSelection(about_page, false);
			}
		});
		tabsDisplay.add(aboutTab);
		tabs.put(about_page, aboutTab);

		cl = new CardLayout(0, 0);

		displayPanel = new JPanel();
		displayPanel.setOpaque(false);
		displayPanel.setBounds(200, 0, pageWidth - 200, pageHeight);
		displayPanel.setLayout(cl);
		add(displayPanel);

		displayPanel.add(new AllPasswordsPage(), all_passwords_page);
		displayPanel.add(new AddEntryPage(), add_entry_page);
		displayPanel.add(new PasswordGeneratorPage(), password_generator_page);
		displayPanel.add(new SettingsPage(), settings_page);
		displayPanel.add(new AboutPage(), about_page);

		setSelection(all_passwords_page, true);

	}

	private static void swapTo(String page, boolean shouldCreateNew) {
		if (shouldCreateNew) {
			switch (page) {
				case all_passwords_page: {
					displayPanel.add(new AllPasswordsPage(), page);
					break;
				}
				case add_entry_page: {
					displayPanel.add(new AddEntryPage(), page);
					break;
				}
				case password_generator_page: {
					displayPanel.add(new PasswordGeneratorPage(), page);
					break;
				}
			}
		}
		cl.show(displayPanel, page);
		displayPanel.revalidate();
	}

	public static void setSelection(String page, boolean shouldCreateNew) {

		for (Entry<String, CofferTabLabel> entry : tabs.entrySet()) {
			String tabPage = entry.getKey();
			CofferTabLabel tab = entry.getValue();

			if (tabPage == page) {
				tab.isSelected = true;
				tab.setBackground(CofferReferences.CofferLightGrey);
			} else {
				tab.isSelected = false;
				tab.setBackground((Color) null);
			}
		}

		swapTo(page, shouldCreateNew);

	}
}