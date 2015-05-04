package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePickerImpl;

import com.dbproj.util.DBToolbox;
import com.dbproj.util.Toolbox;

public class ConsultationListPanel extends DataListPanel {

	JDatePicker datePicker;
	JComboBox<String> phyDropList;
	
	LinkedHashMap<String, Integer> physicianMap;
	
	JCheckBox cbDate;
	JCheckBox cbName;
	
	
	final static String sql = "select c.id, c.date, e.name, p.name, i.description, d.comment from consultation c "
			+ " left join diagnosis d on c.id = d.consultation_id"
			+ " left join employee e on e.id = c.physician_id "
			+ " left join patient p on p.id = c.patient_id "
			+ " left join illness i on d.illness_code = i.code";
	
	final static String ORDER_BY = " order by c.date";
	
	public ConsultationListPanel() {
		super(sql + ORDER_BY);
	}
	@Override
	void initUI() {
		Vector<String> columnName = new Vector<>();
		columnName.add("C_ID");
		columnName.add("Date");
		columnName.add("PhysicianName");
		columnName.add("PatientName");
		columnName.add("IllnessDescription");
		columnName.add("DiagnosisComment");
		setColumnName(columnName);
		
		super.initUI();
		
		TableColumn c = table.getColumn("C_ID");
		c.setMaxWidth(40);
		
		generateControlPanel(true);
		JButton bDiagnose = new JButton("Diagnose");
		addToControlPanel(bDiagnose);
		bDiagnose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					int consID = (int) tableModel.getValueAt(row, 0);
					JDialog dialog = Toolbox.createDialog(ConsultationListPanel.this, "Diagnose", 270, 400);
					dialog.setContentPane(new DiagnosePanel(consID));
					dialog.setVisible(true);
					
				}
			}
		});
		
		
		JPanel pTop = new JPanel(new FlowLayout());
		add(pTop, BorderLayout.NORTH);
		
		cbDate = new JCheckBox("Specify Date:");
		pTop.add(cbDate);
		cbDate.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				getFilteredResult();
			}
		});
		
		datePicker = Toolbox.getDatePicker();
		pTop.add((JDatePickerImpl)datePicker);
		
		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getFilteredResult();
			}
		});
		
		cbName = new JCheckBox("Specify Doctor:");
		pTop.add(cbName);
		cbName.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				getFilteredResult();
			}
		});
		
		phyDropList = new JComboBox<>();
		pTop.add(phyDropList);
		
		phyDropList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getFilteredResult();
			}
		});;
		
		
		getPhysicianList();
	}
	
	private void getFilteredResult(){
		String sql2 = sql;
		
		if(cbName.isSelected()){
			String name = (String) phyDropList.getSelectedItem();
			int phID = physicianMap.get(name);
			sql2 += (" where e.id = " + phID);
			
		}
		if(cbDate.isSelected()){
			Date date = Toolbox.getDateFromDatePicker(datePicker);
			sql2 += (cbName.isSelected() ? " and " : " where ");
			sql2 += ("c.date = '" + date + "'");
		}
		sql2 += ORDER_BY;
		getDataFromDBAndShowInList(sql2);
	}
	
	private void getPhysicianList(){
		try {
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery("select e.id, e.name from physician p, employee e where p.emp_id = e.id order by e.id");
			
			physicianMap = new LinkedHashMap<>();
			
			while(rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString(2);
				
				physicianMap.put(name, id);
			}
			
			String[] phys = physicianMap.keySet().toArray(new String[0]);
			DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(phys);
			phyDropList.setModel(comboBoxModel);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
