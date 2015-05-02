package com.dbproj.frame;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;


public abstract class MyFrame extends JFrame {

	protected Container contentPane;
	
	public MyFrame() throws HeadlessException {
		super();
		init();
	}

	public MyFrame(String title) throws HeadlessException {
		super(title);
		init();
	}

	protected void init(){
		setResizable(false);
		int[] wh = getWH();
		setSize(wh[0], wh[1]);
		setTitle(title());
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		contentPane = getContentPane();
	}
	
	protected int[] getWH(){
		return new int[]{800, 600};
	}
	
	protected String title(){
		return null;
	}
}
