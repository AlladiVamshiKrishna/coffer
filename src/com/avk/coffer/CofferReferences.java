package com.avk.coffer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class CofferReferences {

	public static final Dimension COFFER_FRAME_SIZE = new Dimension(1000, 600);

	// Images used for Coffer logos
	public static final ImageIcon COFFER_SAFE_LOGO_16X16 = new ImageIcon(Coffer.class.getResource("/CofferSafeLogo16x16.png"));
	public static final ImageIcon COFFER_SAFE_LOGO_32X32 = new ImageIcon(Coffer.class.getResource("/CofferSafeLogo32x32.png"));
	public static final ImageIcon COFFER_SAFE_LOGO_64X64 = new ImageIcon(Coffer.class.getResource("/CofferSafeLogo64x64.png"));
	public static final ImageIcon COFFER_SAFE_LOGO_128X128 = new ImageIcon(Coffer.class.getResource("/CofferSafeLogo128x128.png"));
	public static final ImageIcon COFFER_SAFE_BLACK_LOGO_16X16 = new ImageIcon(Coffer.class.getResource("/CofferSafeBlackLogo16x16.png"));

	public static final ImageIcon COFFER_LOGO_64X64 = new ImageIcon(Coffer.class.getResource("/Coffer_Logo_64x64.png"));
	public static final ImageIcon COFFER_LOGO_SMALL = new ImageIcon(Coffer.class.getResource("/Coffer_Logo_Blue.png"));

	public static final ArrayList<Image> COFFER_LOGOS = new ArrayList<Image>();

	// Images used for icons
	public static final ImageIcon SINGLE_KEY = new ImageIcon(Coffer.class.getResource("/key.png"));
	public static final ImageIcon MULTIPLE_KEYS = new ImageIcon(Coffer.class.getResource("/keys.png"));
	public static final ImageIcon ADD_KEY = new ImageIcon(Coffer.class.getResource("/addKey.png"));
	public static final ImageIcon KEY_GEN = new ImageIcon(Coffer.class.getResource("/keyGen.png"));
	public static final ImageIcon CLIPBOARD = new ImageIcon(Coffer.class.getResource("/clipBoard.png"));
	public static final ImageIcon MENU_BUTTON = new ImageIcon(Coffer.class.getResource("/menuButton.png"));
	public static final ImageIcon LAUNCH = new ImageIcon(Coffer.class.getResource("/launch.png"));
	public static final ImageIcon EDIT = new ImageIcon(Coffer.class.getResource("/edit.png"));
	public static final ImageIcon DELETE = new ImageIcon(Coffer.class.getResource("/delete.png"));
	public static final ImageIcon SHOW = new ImageIcon(Coffer.class.getResource("/show.png"));
	public static final ImageIcon HIDE = new ImageIcon(Coffer.class.getResource("/hide.png"));
	public static final ImageIcon EXPORT = new ImageIcon(Coffer.class.getResource("/export.png"));
	public static final ImageIcon GEAR = new ImageIcon(Coffer.class.getResource("/gear.png"));

	// Images used in Frame
	public static final ImageIcon BLUE_STREAK = new ImageIcon(Coffer.class.getResource("/blueStreak.png"));

	// Images used in Custom Components
	public static final ImageIcon CHECKBOX = new ImageIcon(Coffer.class.getResource("/checkBox.png"));
	public static final ImageIcon CHECKEDBOX = new ImageIcon(Coffer.class.getResource("/checkedBox.png"));
	public static final ImageIcon TOGGLE_SWITCH = new ImageIcon(Coffer.class.getResource("/toggleSwitch.png"));
	public static final ImageIcon TOGGLED_SWITCH = new ImageIcon(Coffer.class.getResource("/toggledSwitch.png"));
	public static final ImageIcon TEXT_BLANK = new ImageIcon(Coffer.class.getResource("/textBlank.png"));
	public static final ImageIcon MEDIUM_TEXT_BLANK = new ImageIcon(Coffer.class.getResource("/mediumTextBlank.png"));
	public static final ImageIcon PLUS = new ImageIcon(Coffer.class.getResource("/plus.png"));
	public static final ImageIcon MINUS = new ImageIcon(Coffer.class.getResource("/minus.png"));

	// Images used in System's Tray Icon
	public static final ImageIcon COFFER_LOCK = new ImageIcon(Coffer.class.getResource("/lock.png"));
	public static final ImageIcon COFFER_FRAME_RESTORE = new ImageIcon(Coffer.class.getResource("/restore.png"));

	public static Font 	Comfortaa_Plain_13, Comfortaa_Plain_14, Comfortaa_Plain_15,
						Comfortaa_Bold_15, Comfortaa_Bold_16, Comfortaa_Bold_80,
						Comfortaa_Bold_Italic_16, Comfortaa_Bold_Italic_20,
						Comfortaa_Italic_13,
						Antipasto_Plain_15, Antipasto_Plain_26,
						Antipasto_Bold_15;

	public static final Color CofferRed = new Color(230, 75, 60);
	public static final Color CofferBlue = new Color(0, 175, 210);
	public static final Color CofferDarkGrey = new Color(75, 75, 75);
	public static final Color CofferLightGrey = new Color(100, 100, 100);
	public static final Color CofferVeryLightGrey = new Color(150, 150, 150);

	public static final Clipboard SYS_CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

	static {
		try {
			Font Comfortaa = Font.createFont(Font.TRUETYPE_FONT, Coffer.class.getResourceAsStream("/Comfortaa.ttf"));
			Font Antipasto = Font.createFont(Font.TRUETYPE_FONT, Coffer.class.getResourceAsStream("/Antipasto.otf"));

			Comfortaa_Plain_13 = Comfortaa.deriveFont(Font.PLAIN, 13);
			Comfortaa_Plain_14 = Comfortaa.deriveFont(Font.PLAIN, 14);
			Comfortaa_Plain_15 = Comfortaa.deriveFont(Font.PLAIN, 15);

			Comfortaa_Italic_13 = Comfortaa.deriveFont(Font.ITALIC, 13);

			Comfortaa_Bold_15 = Comfortaa.deriveFont(Font.BOLD, 15);
			Comfortaa_Bold_16 = Comfortaa.deriveFont(Font.BOLD, 16);
			Comfortaa_Bold_80 = Comfortaa.deriveFont(Font.BOLD, 80);

			Comfortaa_Bold_Italic_16 = Comfortaa.deriveFont(Font.ITALIC | Font.BOLD, 16);
			Comfortaa_Bold_Italic_20 = Comfortaa.deriveFont(Font.ITALIC | Font.BOLD, 20);

			Antipasto_Bold_15 = Antipasto.deriveFont(Font.BOLD, 15);
			Antipasto_Plain_15 = Antipasto.deriveFont(Font.PLAIN, 15);
			Antipasto_Plain_26 = Antipasto.deriveFont(Font.PLAIN, 26);

			COFFER_LOGOS.add(COFFER_SAFE_LOGO_128X128.getImage());
			COFFER_LOGOS.add(COFFER_SAFE_LOGO_64X64.getImage());
			COFFER_LOGOS.add(COFFER_SAFE_LOGO_32X32.getImage());
			COFFER_LOGOS.add(COFFER_SAFE_LOGO_16X16.getImage());
			COFFER_LOGOS.add(COFFER_SAFE_BLACK_LOGO_16X16.getImage());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}