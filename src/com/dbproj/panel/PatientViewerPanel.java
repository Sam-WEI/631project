package com.dbproj.panel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.util.JDatePickerUtil;


public class PatientViewerPanel extends MyPanel {
	
	JTextField tfNo;
	JTextField tfName;
	JTextField tfSSN;
	JTextField tfDOB;
	JTextField tfAddr;
	JDatePicker datePicker;
	
	
	JRadioButton rbBloodA;
	JRadioButton rbBloodB;
	JRadioButton rbBloodAB;
	JRadioButton rbBloodO;
	
	JRadioButton rbGenderF;
	JRadioButton rbGenderM;
	
	boolean editable = true;
	
	public PatientViewerPanel(boolean editable) {
		super();
		this.editable = editable;
		initUI();
	}
	
	protected void initUI() {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new GridLayout(20, 1));
		
		tfNo = new JTextField(20);
		tfName = new JTextField(20);
		tfSSN = new JTextField(20);
		tfDOB = new JTextField(20);
		tfAddr = new JTextField(20);
		datePicker = new JDateComponentFactory().createJDatePicker();
		
		setTextFieldsEnabled(editable);
		
		add(new JLabel("Patient No:"));
		add(tfNo);
		
		add(new JLabel("Name:"));
		add(tfName);
		add(new JLabel("SSN:"));
		add(tfSSN);
		add(new JLabel("Date of Birth:"));
		
		add((JDatePickerImpl)datePicker);
		
		add(new JLabel("Blood Type:"));
		JPanel bloodType = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbBloodA = new JRadioButton("A");
		rbBloodB = new JRadioButton("B");
		rbBloodAB = new JRadioButton("AB");
		rbBloodO = new JRadioButton("O");
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(rbBloodO);
		bGroup.add(rbBloodA);
		bGroup.add(rbBloodB);
		bGroup.add(rbBloodAB);
		
		bloodType.add(rbBloodO);
		bloodType.add(rbBloodA);
		bloodType.add(rbBloodB);
		bloodType.add(rbBloodAB);
		
		add(bloodType);
		
		add(new JLabel("Address:"));
		add(tfAddr);
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
	}
	
	private void setTextFieldsEnabled(boolean editable){
		tfAddr.setEditable(editable);
		tfDOB.setEditable(editable);
		tfSSN.setEditable(editable);
		tfNo.setEditable(editable);
		tfName.setEditable(editable);
		((JDatePickerImpl)datePicker).setEnabled(editable);
	}

	public void setResultSet(ResultSet rs) {
		try {
			if(rs != null && rs.next()){
				tfNo.setText(rs.getInt(1) + "");
				tfName.setText(rs.getString(3));
				tfSSN.setText(rs.getInt(2) + "");
				Date date = rs.getDate(4);
				datePicker.getModel().setDate(1900 + date.getYear(), date.getMonth(), date.getDate());
				
				String sex = rs.getString(7);
				if("F".equals(sex)){
					rbGenderF.setSelected(true);
				} else {
					rbGenderM.setSelected(true);
				}
				
				String addr = rs.getString(6);
				tfAddr.setText(addr);
				
				String blood = rs.getString(5);
				
				if("A".equals(blood)){
					rbBloodA.setSelected(true);
				} else if("B".equals(blood)){
					rbBloodB.setSelected(true);
				} else if("AB".equals(blood)){
					rbBloodAB.setSelected(true);
				} else {
					rbBloodO.setSelected(true);
				}
				
			} else {
				JOptionPane.showMessageDialog(this, "Person does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
