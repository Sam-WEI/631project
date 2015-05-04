package com.dbproj.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.dbproj.util.Toolbox;

public class ConsultationListPanel extends DataListPanel {

	final static String sql = "select c.id, c.date, e.name, p.name, i.description, d.comment from consultation c "
			+ " left join diagnosis d on c.id = d.consultation_id"
			+ " left join employee e on e.id = c.physician_id "
			+ " left join patient p on p.id = c.patient_id "
			+ " left join illness i on d.illness_code = i.code order by c.date";
	
	public ConsultationListPanel() {
		super(sql);
	}
	@Override
	void initUI() {
		Vector<String> columnName = new Vector<>();
		columnName.add("ConsultationID");
		columnName.add("Date");
		columnName.add("PhysicianName");
		columnName.add("PatientName");
		columnName.add("IllnessDescription");
		columnName.add("DiagnosisComment");
		setColumnName(columnName);
		
		super.initUI();
		
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
		
	}
}
