package com.dbproj.panel;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.dbproj.util.DBToolbox;
import com.dbproj.util.Toolbox;

public class DiagnosePanel extends DataListPanel {

	final static String sql = "select * from illness";
	
	int cons_id;
	
	public DiagnosePanel(int cons_id) {
		super(sql);
		this.cons_id = cons_id;
	}
	@Override
	void initUI() {
		super.initUI();
		
		JPanel pTop = new JPanel(new BorderLayout());
		add(pTop, BorderLayout.NORTH);
		final JTextArea ta = new JTextArea(3, 30);
		ta.setText("Drink more water");
		pTop.add(ta, BorderLayout.CENTER);
		pTop.add(new Label("Write comment: "), BorderLayout.NORTH);
		pTop.add(new Label("Select illness: "), BorderLayout.SOUTH);
		
		generateControlPanel(false);
		
		final JCheckBox cbNeedOp = new JCheckBox("Hospitalize", false);
		addToControlPanel(cbNeedOp);
		
		JButton bSubmit = new JButton("Submit");
		addToControlPanel(bSubmit);
		bSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					int illCode = (int) tableModel.getValueAt(row, 0);
					String needOp = (cbNeedOp.isSelected() ? "H" : "O");
					commitDiagnosis(cons_id, illCode, needOp, ta.getText().trim());
				}
			}
		});
	}
	
	void commitDiagnosis(int consID, int illCode, String needOp, String comment){
		try {
			Statement st = DBToolbox.connection.createStatement();
			st.execute(String.format("insert into diagnosis values (%d, %d, '%s', '%s')", consID, illCode, needOp, comment));
			
			JOptionPane.showMessageDialog(this, "Diagnosis is submitted!", "done", JOptionPane.PLAIN_MESSAGE);
			
			Toolbox.closeDialog(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
