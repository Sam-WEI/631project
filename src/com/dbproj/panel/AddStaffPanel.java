package com.dbproj.panel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.dbproj.module.StaffType;
import com.dbproj.util.DBToolbox;

public class AddStaffPanel extends MyPanel {
	
	StaffType type;
	
	JTextField tfNo;
	JTextField tfName;
	JTextField tfSSN;
	JTextField tfDOB;
	JTextField tfAddr;
	JTextField tfPhone;
	JTextField tfSalary;
	
	JRadioButton rbGenderF;
	JRadioButton rbGenderM;
	
	JRadioButton rbTypePhy;
	JRadioButton rbTypeSurg;
	JRadioButton rbTypeNurse;
	JRadioButton rbBloodO;
	

	JLabel lbGrade = new JLabel("Grade:");
	JTextField tfGrade = new JTextField(20);
	JLabel lbYear = new JLabel("Year:");
	JTextField tfYear = new JTextField(20);
	
	JLabel lbSpecialty = new JLabel("Specialty:");
	JTextField tfSpecialty = new JTextField(20);
	
	JLabel lbContractDur = new JLabel("Contract Duration:");
	JTextField tfContractDur = new JTextField(20);
	
	
	JButton btnSave;
	JButton btnCancel;
	
	public AddStaffPanel(StaffType type){
		super();
		this.type = type;
		init();
	}
	
	
	private void init() {
		setLayout(new GridLayout(22, 1));
		
		tfNo = new JTextField(20);
		tfName = new JTextField(20);
		tfSSN = new JTextField(20);
		tfDOB = new JTextField(20);
		tfAddr = new JTextField(20);
		tfPhone = new JTextField(20);
		tfSalary = new JTextField(20);
		
		
		add(new JLabel("ID:"));
		add(tfNo);
		
		add(new JLabel("Name:"));
		add(tfName);
		add(new JLabel("SSN:"));
		add(tfSSN);
		
		add(new JLabel("Gender:"));
		JPanel gender = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbGenderF = new JRadioButton("Female");
		rbGenderM = new JRadioButton("Male");
		ButtonGroup bGroupGender = new ButtonGroup();
		bGroupGender.add(rbGenderF);
		bGroupGender.add(rbGenderM);
		gender.add(rbGenderF);
		gender.add(rbGenderM);
		add(gender);
		
		add(new JLabel("Phone:"));
		add(tfPhone);
		
		add(new JLabel("Salary:"));
		add(tfSalary);
		
		add(new JLabel("Address:"));
		add(tfAddr);
		
		add(new JLabel("Type:"));
		JPanel type = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbTypePhy = new JRadioButton("Pysician");
		rbTypeSurg = new JRadioButton("Surgeon");
		rbTypeNurse = new JRadioButton("Nurse");
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(rbTypePhy);
		bGroup.add(rbTypeSurg);
		bGroup.add(rbTypeNurse);
		type.add(rbTypePhy);
		type.add(rbTypeSurg);
		type.add(rbTypeNurse);
		
		add(type);
		
		
		btnSave = new JButton("Save");
		btnCancel = new JButton("Cancel");
		
		JPanel btnPanel = new JPanel(new FlowLayout());
		btnPanel.add(btnSave);
		btnPanel.add(btnCancel);
		
		add(btnPanel);
		
		rbTypePhy.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED){
					remove(tfSpecialty);
					remove(lbSpecialty);
				} else if(e.getStateChange() == ItemEvent.SELECTED){
					add(lbSpecialty, 16);
					add(tfSpecialty, 17);
				}
				updateUI();
			}
		});
		
		rbTypeNurse.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED){
					remove(tfGrade);
					remove(lbGrade);
					remove(tfYear);
					remove(lbYear);
				} else if(e.getStateChange() == ItemEvent.SELECTED){
					add(lbGrade, 16);
					add(tfGrade, 17);
					add(lbYear, 18);
					add(tfYear, 19);
				}
				updateUI();
			}
		});
		
		rbTypeSurg.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.DESELECTED){
					remove(tfContractDur);
					remove(lbContractDur);
				} else if(e.getStateChange() == ItemEvent.SELECTED){
					add(lbContractDur, 16);
					add(tfContractDur, 17);
				}
				updateUI();
			}
		});
		
		rbTypePhy.doClick();
		
		
		
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveToSQL();
			}
		});
		
		
		tfNo.setText(getNextEmpNo() + "");
		tfNo.setEditable(false);
	}
	
	private void saveToSQL(){
		try {
			String gender = (rbGenderM.isSelected()? "M" : "F");
			String type;
			if(rbTypeNurse.isSelected()){
				type = "nurse";
			} else if(rbTypePhy.isSelected()){
				type = "physician";
			} else {
				type = "surgeon";
			}
			
			int id = Integer.valueOf(tfNo.getText().trim());
			
			PreparedStatement st = DBToolbox.connection.prepareStatement("insert into employee values(?, ?, ?, ?, ?, ?, ?, ?)");
			st.setInt(1, id);
			st.setInt(2, Integer.valueOf(tfSSN.getText().trim()));
			st.setString(3, tfName.getText().trim());
			st.setString(4, gender);
			st.setString(5, type);
			st.setString(6, tfPhone.getText().trim());
			st.setInt(7, Integer.valueOf(tfSalary.getText().trim()));
			st.setString(8, tfAddr.getText().trim());
			
			st.execute();
			
			PreparedStatement st2;
			if(type.equals("nurse")){
				st2 = DBToolbox.connection.prepareStatement("insert into nurse values(?, ?, ?)");
				st2.setInt(1, id);
				st2.setInt(2, Integer.valueOf(tfGrade.getText().trim()));
				st2.setInt(3, Integer.valueOf(tfYear.getText().trim()));
				
			} else if(type.equals("physician")){
				st2 = DBToolbox.connection.prepareStatement("insert into physician values(?, ?, ?)");
				st2.setInt(1, id);
				st2.setString(2, tfSpecialty.getText().trim());
				st2.setString(3, null);
			} else {
				st2 = DBToolbox.connection.prepareStatement("insert into surgeon values(?, ?)");
				st2.setInt(1, id);
				st2.setInt(2, Integer.valueOf(tfContractDur.getText().trim()));
			}
			st2.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private int getNextEmpNo(){
		int no = -1;
		try {
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery("select max(id) from employee");
			if(rs.next()){
				no = rs.getInt(1) + 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return no;
	}
}
