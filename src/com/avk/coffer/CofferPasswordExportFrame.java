package com.avk.coffer;

import java.awt.Dimension;

import com.avk.coffer.components.CofferPopupFrame;

@SuppressWarnings("serial")
public class CofferPasswordExportFrame extends CofferPopupFrame {

	
	private static int frameWidth = 500, frameHeight = 350;

	public CofferPasswordExportFrame(CofferPasswordEntry p) {
		super(Coffer.frmcoffer, true, new Dimension(frameWidth, frameHeight));

		setTitle(p.getTitle() + " Export");
		
	}

}
