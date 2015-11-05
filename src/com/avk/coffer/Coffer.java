package com.avk.coffer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.avk.coffer.components.CofferDialog;

public class Coffer {

	public static JFrame frmcoffer;
	private static JPanel contentPanel, disablePanel, statusPanel;
	private static CardLayout cl;
	private static JLabel titleBar, titleBar2, frmTitleLabel, frmStatusLabel, frmIconImg, lbl_, lblX, frmDragger;
	private static TrayIcon trayIcon;
	private static SystemTray tray;

	private static File MUTEX_FILE = new File("./Coffer/Coffer.mutex");
	public static File KEY_FILE = new File("./Coffer/.cofferkey");
	private static Scanner MUTEX_SCANNER;
	private static Thread Sleeper;

	public static boolean user_logged_in = false;
	private int xPressed, yPressed;

	// constants for page redirections
	protected static final String create_user_page = "CreateUserPage";
	protected static final String login_page = "LoginPage";
	protected static final String dash_board = "DashBoard";

	private static final int frameWidth = CofferSettings.COFFER_FRAME_SIZE.width;
	private static final int frameHeight = CofferSettings.COFFER_FRAME_SIZE.height;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (!MUTEX_FILE.exists()) {
						File cofferFolders = new File("./Coffer");
						cofferFolders.mkdirs();
						MUTEX_FILE.createNewFile();
						MUTEX_SCANNER = new Scanner(MUTEX_FILE);
						MUTEX_FILE.deleteOnExit();
						new Coffer();
					} else {
						new CofferExtraInstance();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Coffer() { initialize(); }

	private void initialize() {
		try {
			if (SystemTray.isSupported()) {
				tray = SystemTray.getSystemTray();
				Image image = CofferReferences.COFFER_SAFE_BLACK_LOGO_16X16.getImage();

				CofferTrayPopup popup = new CofferTrayPopup();
				JDialog hiddenDialog = new JDialog();
				hiddenDialog.addWindowFocusListener(new WindowAdapter() {
					@Override
					public void windowLostFocus(WindowEvent we) {
						hiddenDialog.setVisible(false);
					}
				});
				hiddenDialog.setSize(10, 10);
				hiddenDialog.setUndecorated(true);

				trayIcon = new TrayIcon(image, "Coffer - A portable password manager.", null);
				trayIcon.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						if (e.isPopupTrigger()) {
							popup.setLocation(e.getX(), e.getY());
							hiddenDialog.setLocation(e.getX(), e.getY());
							popup.setInvoker(hiddenDialog);
							hiddenDialog.setVisible(true);
							popup.setVisible(true);
						}
					}
				});
				trayIcon.setImageAutoSize(true);
			} else {
				System.out.println("system tray not supported");
			}

			frmcoffer = new JFrame();
			frmcoffer.setIconImages(CofferReferences.COFFER_LOGOS);
			frmcoffer.setTitle("Coffer");
			frmcoffer.setUndecorated(true);
			frmcoffer.setSize(CofferSettings.COFFER_FRAME_SIZE);
			frmcoffer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frmcoffer.getContentPane().setLayout(null);
			frmcoffer.setLocationRelativeTo(null);
			frmcoffer.addWindowListener(new WindowAdapter() {
				@Override
				public void windowOpened(WindowEvent e) {
					Coffer.setStatus("Hey there! Welcome to Coffer.    :)");
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
					Coffer.setStatus("Welcome Back.    :)");
				}

				@Override
				public void windowClosing(WindowEvent e) {
					clearAndExit();
				}
			});
			frmcoffer.addWindowStateListener(new WindowStateListener() {
				public void windowStateChanged(WindowEvent e) {
					if (e.getNewState() == JFrame.ICONIFIED) {
						try {
							tray.add(trayIcon);
							frmcoffer.setVisible(false);
							Sleeper = new Thread(new Runnable() {
								@Override
								public void run() {
									try {
										Thread.sleep(60 * 1000);
										if (frmcoffer.getState() == JFrame.ICONIFIED)
											logout();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							});
							Sleeper.start();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					if (e.getNewState() == JFrame.NORMAL) {
						Coffer.makeAppear();
					}
				}
			});
			frmcoffer.setVisible(true);

			lbl_ = new JLabel("_");
			lbl_.setVerticalAlignment(SwingConstants.TOP);
			lbl_.setForeground(Color.WHITE);
			lbl_.setFont(CofferReferences.Antipasto_Plain_26);
			lbl_.setHorizontalTextPosition(SwingConstants.CENTER);
			lbl_.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String[] msgs = { "Your Coffer will be locked in a minute.", "Do you want to lock it right away?" };
					setDisable(true);
					CofferDialog lockDialog = new CofferDialog(Coffer.frmcoffer, true, "Lock Confirmation", msgs, CofferDialog.YES_NO_CANCEL_OPTIONS);
					setDisable(false);

					switch (lockDialog.selectedOption) {
						case CofferDialog.YES_OPTION: {
							logout();
						}

						case CofferDialog.NO_OPTION: {
							frmcoffer.setState(Frame.ICONIFIED);
							break;
						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					lbl_.setForeground(CofferReferences.CofferBlue);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lbl_.setForeground(Color.WHITE);
				}
			});
			lbl_.setBounds(frameWidth - 90, 10, 40, 40);

			lblX = new JLabel("X");
			lblX.setForeground(Color.WHITE);
			lblX.setHorizontalAlignment(SwingConstants.CENTER);
			lblX.setFont(CofferReferences.Antipasto_Plain_26);
			lblX.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String[] msgs = { "You are about to exit.", "Do you want to continue?" };
					setDisable(true);
					CofferDialog exitDialog = new CofferDialog(Coffer.frmcoffer, true, "Exit Confirmation", msgs, CofferDialog.YES_NO_OPTIONS);
					setDisable(false);
					if (exitDialog.selectedOption == CofferDialog.YES_OPTION)
						clearAndExit();
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
			lblX.setBounds(frameWidth - 50, 10, 40, 40);

			frmIconImg = new JLabel(CofferReferences.COFFER_LOGO_SMALL);
			frmIconImg.setMinimumSize(new Dimension(30, 30));
			frmIconImg.setBounds(10, 10, 40, 40);
			frmIconImg.setBackground(Color.WHITE);

			frmDragger = new JLabel();
			frmDragger.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					xPressed = e.getX();
					yPressed = e.getY();
				}
			});
			frmDragger.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					int x = e.getXOnScreen();
					int y = e.getYOnScreen();

					frmcoffer.setLocation((x - xPressed), (y - yPressed));
				}
			});
			frmDragger.setBounds(0, 0, frameWidth, 60);

			frmTitleLabel = new JLabel();
			frmTitleLabel.setIconTextGap(0);
			frmTitleLabel.setBackground(CofferReferences.CofferDarkGrey);
			frmTitleLabel.setBounds(10, 10, frameWidth - 20, 40);
			frmTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
			frmTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			frmTitleLabel.setForeground(Color.WHITE);
			frmTitleLabel.setFont(CofferReferences.Comfortaa_Bold_16);
			frmTitleLabel.setText("Coffer");

			titleBar2 = new JLabel();
			titleBar2.setBackground(CofferReferences.CofferDarkGrey);
			titleBar2.setOpaque(true);
			titleBar2.setBounds(10, 10, frameWidth - 20, 40);

			titleBar = new JLabel();
			titleBar.setOpaque(true);
			titleBar.setBackground(CofferReferences.CofferLightGrey);
			titleBar.setBounds(0, 0, frameWidth, 60);

			disablePanel = new JPanel();
			disablePanel.setBackground(new Color(50, 50, 50, 100));
			disablePanel.setBounds(0, 60, frameWidth, frameHeight - 60);
			disablePanel.setVisible(false);
			disablePanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					disablePanel.grabFocus();
				}
			});

			frmStatusLabel = new JLabel();
			frmStatusLabel.setBounds(200, 0, 800, 40);
			frmStatusLabel.setFont(CofferReferences.Comfortaa_Bold_Italic_16);
			frmStatusLabel.setForeground(CofferReferences.CofferVeryLightGrey);

			statusPanel = new JPanel();
			statusPanel.setBorder(new MatteBorder(1, 0, 0, 0, CofferReferences.CofferDarkGrey));
			statusPanel.setBackground(CofferReferences.CofferLightGrey);
			statusPanel.setBounds(0, frameHeight - 40, frameWidth, 40);
			statusPanel.setLayout(null);
			statusPanel.add(frmStatusLabel);

			cl = new CardLayout(0, 0);
			contentPanel = new JPanel(cl);
			contentPanel.setBackground(CofferReferences.CofferLightGrey);
			contentPanel.setBounds(0, 60, frameWidth, frameHeight - 100);

			frmcoffer.getContentPane().add(lbl_);
			frmcoffer.getContentPane().add(lblX);
			frmcoffer.getContentPane().add(frmIconImg);
			frmcoffer.getContentPane().add(frmDragger);
			frmcoffer.getContentPane().add(frmTitleLabel);
			frmcoffer.getContentPane().add(titleBar2);
			frmcoffer.getContentPane().add(titleBar);
			frmcoffer.getContentPane().add(disablePanel);
			frmcoffer.getContentPane().add(statusPanel);
			frmcoffer.getContentPane().add(contentPanel);

			if (!KEY_FILE.exists()) {
				Coffer.swapTo(Coffer.create_user_page);
			} else {
				Coffer.swapTo(Coffer.login_page);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setStatus(String msg) {
		frmStatusLabel.setText(msg);
	}

	public static String getStatus() {
		return frmStatusLabel.getText();
	}

	public static void setTitle(String title) {
		frmTitleLabel.setText(title);
	}

	public static String getTitle() {
		return frmTitleLabel.getText();
	}

	public static void setDisable(boolean flag) {
		disablePanel.setVisible(flag);
	}

	public static boolean isDisabled() {
		return disablePanel.isVisible();
	}

	public static void login() {
		user_logged_in = true;
		swapTo(dash_board);
	}

	public static void logout() {
		user_logged_in = false;

		if (!KEY_FILE.exists()) {
			swapTo(create_user_page);
		} else {
			swapTo(login_page);
		}
	}

	public static void swapTo(String page) {

		contentPanel.removeAll();

		switch (page) {
			case dash_board: {
				Coffer.setStatus("All your passwords are here.    :)");
				contentPanel.add(new DashBoard(), dash_board);
				break;
			}
			case login_page: {
				Coffer.setStatus("Prove this coffer is your's.    :/");
				contentPanel.add(new LoginPage(), login_page);
				break;
			}
			case create_user_page: {
				Coffer.setStatus("Register with credentials for creating a new Coffer.    ^_^");
				contentPanel.add(new CreateUserPage(), create_user_page);
				break;
			}
		}
		cl.show(contentPanel, page);
	}

	public static void makeAppear() {
		frmcoffer.setState(Frame.NORMAL);
		frmcoffer.setVisible(true);
		frmcoffer.setAlwaysOnTop(true);
		frmcoffer.setAlwaysOnTop(false);
		tray.remove(trayIcon);
	}

	public static void clearAndExit() {
		if (MUTEX_FILE.exists()) {
			CofferReferences.SYS_CLIPBOARD.setContents(new StringSelection(""), null);
			MUTEX_SCANNER.close();
			MUTEX_FILE.deleteOnExit();
			System.exit(0);
		}
	}
}