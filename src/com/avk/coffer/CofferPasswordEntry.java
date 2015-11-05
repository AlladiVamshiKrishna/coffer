package com.avk.coffer;

import java.io.File;
import java.util.Random;
import java.util.StringTokenizer;

public class CofferPasswordEntry {
	private String id = "default_id", title, username, password, url;
	private int index, fileNo;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public void setValuesFromFile(File file, int index) {
		try {

			StringTokenizer st = new StringTokenizer(CofferCrypt.decryptFromFile_Index(index, file), "|");
			this.id = st.nextToken();
			this.title = st.nextToken();
			this.url = st.nextToken();
			this.username = st.nextToken();
			this.password = st.nextToken();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeToFile() {
		try {
			if (id.equals("default_id")) {
				Random r = new Random();

				String user_coffer = CofferCrypt.decryptFromFile_Index(CofferCrypt.getCofferKeyIndex(), new File("./Coffer/user's.coffer"));

				index = r.nextInt(CofferCrypt.MAX_KEY_INDEX);

				do {
					fileNo = r.nextInt(100000000);
				} while (fileNo < 10000000 || user_coffer.contains(Integer.toString(fileNo).subSequence(0, 8)));

				if (user_coffer.equals("no_passwords"))
					user_coffer = Integer.toString(fileNo) + "|" + Integer.toString(index);
				else
					user_coffer += "\n" + Integer.toString(fileNo) + "|" + Integer.toString(index);

				CofferCrypt.encrypt2File_Index(CofferCrypt.getCofferKeyIndex(), user_coffer, new File("./Coffer/user's.coffer"));

				CofferCrypt.encrypt2File_Index(index, fileNo + "|" + title + "|" + url + "|" + username + "|" + password, new File("./Coffer/" + fileNo + ".cofferpass"));
			} else {

				String user_coffer = CofferCrypt.decryptFromFile_Index(CofferCrypt.getCofferKeyIndex(), new File("./Coffer/user's.coffer"));

				StringTokenizer cofferTokens = new StringTokenizer(user_coffer, "\n");
				while (cofferTokens.hasMoreTokens()) {
					StringTokenizer st = new StringTokenizer(cofferTokens.nextToken(), "|");

					if (st.nextToken().equals(id)) {
						CofferCrypt.encrypt2File_Index(Integer.parseInt(st.nextToken()), id + "|" + title + "|" + url + "|" + username + "|" + password, new File("./Coffer/" + id + ".cofferpass"));
						break;
					}
				}
				DashBoard.setSelection(DashBoard.all_passwords_page, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}