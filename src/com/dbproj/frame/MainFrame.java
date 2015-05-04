package com.dbproj.frame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dbproj.util.DBToolbox;


public class MainFrame extends MyFrame {
	
	
	public MainFrame() throws HeadlessException {
		super();
		
	}
	
	@Override
	protected void init(){
		super.init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DBToolbox.closeConnection();
			}
		});
		
		
		setLayout(new BorderLayout());

		JPanel pLogo = new JPanel(null);
		add(pLogo, BorderLayout.CENTER);
		
		
		JLabel name = new JLabel("NewarkMedicalAssociates");
		name.setFont(new Font("Broadway", Font.BOLD, 55));
		name.setBounds(100, 100, 1024, 200);
		name.setBackground(Color.RED);
		pLogo.add(name);
		
		
		ImageIcon image = new ImageIcon("image\\main_bg.jpg");
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(0, 0, 1024, 700);
		pLogo.add(imageLabel);
		
		
		
		JPanel panelLogin = new JPanel();
		add(panelLogin, BorderLayout.SOUTH);
		panelLogin.setLayout(new GridLayout(1, 3));
		panelLogin.setPreferredSize(new Dimension(0, 70));
		JButton btn = new JButton("Manage Patient");
		panelLogin.add(btn);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManagePatientFrame().setVisible(true);
			}
		});
		btn = new JButton("Manage In-Patient");
		panelLogin.add(btn);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManageInPatientFrame().setVisible(true);
			}
		});
		btn = new JButton("Manage Staff");
		panelLogin.add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManageStaffFrame().setVisible(true);
			}
		});
		
	} 
	
	
	@Override
	protected int[] getWH() {
		return new int[]{1024, 768};
	}
	
	@Override
	protected String title() {
		return "NewarkMedicalAssociates";
	}
}
