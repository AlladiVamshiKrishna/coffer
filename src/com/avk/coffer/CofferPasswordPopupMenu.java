package com.avk.coffer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class CofferPasswordPopupMenu extends CofferPopupMenu {

	public CofferPasswordPopupMenu(CofferPasswordEntry p) {
		
		CofferMenuItem loginMenuItem = new CofferMenuItem("Go to Login Page");
		add(loginMenuItem);
		
		CofferMenuItem editMenuItem = new CofferMenuItem("Edit Entry");
		add(editMenuItem);
		
		CofferMenuItem deleteMenuItem = new CofferMenuItem("Delete Entry");
		deleteMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(JOptionPane.showConfirmDialog(deleteMenuItem, "You are about to delete the password entry.\nDo you want to continue?",
							"Delete password Confirmation",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
					{
						String new_user_coffer="", user_coffer = CofferCrypt.decryptFromFile_Index(CofferReferences.getCofferKeyIndex(), new File("./Coffer/user's.coffer"));
						StringTokenizer st = new StringTokenizer(user_coffer,"\n");
						while(st.hasMoreTokens()){
							String token =st.nextToken();
							if(token.contains(p.getTitle())){
								StringTokenizer strTknzr = new StringTokenizer(token,"|");
								strTknzr.nextToken();
								String fileNo = strTknzr.nextToken();
								new File("./Coffer/" + fileNo + ".cofferpass").delete();
							}
							else{ new_user_coffer += st.hasMoreTokens()? token + "\n" : token; }
						}
						CofferCrypt.encrypt2File_Index(CofferReferences.getCofferKeyIndex(), new_user_coffer, new File("./Coffer/user's.coffer"));
						DashBoard.swapTo("AllPasswordsPage");
					}
				} catch (Exception e1) { e1.printStackTrace(); }				
			}
		});
		add(deleteMenuItem);
		
	}
}
