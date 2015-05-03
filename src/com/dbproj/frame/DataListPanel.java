package com.dbproj.frame;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.dbproj.panel.MyPanel;
import com.dbproj.util.DBToolbox;

public class DataListPanel extends MyPanel {
	
	int columnCount;
	JTable table;
	ArrayList<Object[]> dataArrayList = new ArrayList<>();
	StaffTableModel tableModel;
	String[] columnName;
	
	String tableName;
	
	public DataListPanel(String tableName){
		super();
		this.tableName = tableName;
		initUI();
	}
	
	private void initUI() {
		setLayout(new BorderLayout());
		table = new JTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		add(scrollPane, BorderLayout.CENTER);
		getDataFromDB();
		
		tableModel = new StaffTableModel();
		table.setModel(tableModel);
		
	}
	
	private void getDataFromDB(){
		try {
			Statement st = DBToolbox.connection.createStatement();
			ResultSet rs = st.executeQuery("select * from " + tableName);
			
			ResultSetMetaData metadata = rs.getMetaData();
			columnCount = metadata.getColumnCount();
			columnName = new String[columnCount];
			
			for(int i = 1; i <= columnCount; i++){
				columnName[i - 1] = metadata.getColumnName(i);
			}
			
			Object[] columns;
			while(rs.next()){
				/*int id = rs.getObject(1, Integer.class);
				int ssn = rs.getObject(2, Integer.class);
				String name = rs.getObject(3, String.class);
				String gender = rs.getObject(4, String.class);
				String type = rs.getObject(5, String.class);
				int phone = rs.getObject(6, Integer.class);
				int salary = rs.getObject(7, Integer.class);
				String address = rs.getObject(8, String.class);
				
				Employee e = new Employee();
				e.setId(id);
				e.setName(name);
				e.setSsn(ssn);
				e.setType(type);
				e.setGender(gender);
				e.setPhone(phone);
				e.setSalary(salary);
				e.setAddress(address);
				
				dataArrayList.add(e);*/
				columns = new Object[columnCount];
				for(int i = 1; i <= columnCount; i++){
					columns[i - 1] = rs.getObject(i);
				}
				
				dataArrayList.add(columns);
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private class StaffTableModel extends AbstractTableModel{

		@Override
		public int getRowCount() {
			return dataArrayList.size();
		}

		@Override
		public int getColumnCount() {
			return columnCount;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
			return dataArrayList.get(rowIndex)[columnIndex];
		}
		
		@Override
		public String getColumnName(int column) {
			return columnName[column];
		}
		
	}
}
