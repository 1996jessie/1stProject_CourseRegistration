package Stu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


public class PanelC extends JPanel {
	private JTable table3;
	private JScrollPane scrollPane3;
	private JLabel creditLabel;

	StuMain stuMain;
	LectureSave lecSave = new LectureSave();
	StudentBean sb;
	PanelA panela;
	int result;
	int credit = 0;

	public PanelC(Object[] columnNames, StudentBean sb) {
		this.setBackground(Color.WHITE);
		this.setBounds(400, 110, 370, 190);
		this.setLayout(null);

		JLabel label2 = new JLabel("신청한 과목");
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setFont(label2.getFont().deriveFont(12.5f));

		label2.setBounds(0, 0, 370, 20); 
		this.add(label2); 

		ArrayList<LectureBean> lists = lecSave.saveLecturebyId(sb.getId());
		int initialRowCount = lists == null ? 0 : lists.size() * 3;

		JLabel creditLabel = new JLabel("신청한 학점 : " + initialRowCount + "학점");
		creditLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		creditLabel.setFont(creditLabel.getFont().deriveFont(14.0f));
		creditLabel.setBounds(0, 165, 370, 18); 
		this.add(creditLabel); 

		if(lists == null) {
			setTable3(new JTable(new DefaultTableModel(columnNames, 0)));
			scrollPane3 = new JScrollPane(getTable3());
			scrollPane3.setPreferredSize(new Dimension(370, 140));
			this.add(scrollPane3, BorderLayout.CENTER);
			scrollPane3.setBounds(0, 18, scrollPane3.getPreferredSize().width, scrollPane3.getPreferredSize().height); 
		} else {
			Object[][] rowData = new Object[lists.size()][5];
			fillData(lists,rowData);

			DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
			setTable3(new JTable(model));
			model.addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {

					String credit = updateCredit();
					creditLabel.setText(credit);
				}
			});
			scrollPane3 = new JScrollPane(getTable3());
			getTable3().setDefaultEditor(Object.class, null);
			scrollPane3.setPreferredSize(new Dimension(370, 140));
			this.add(scrollPane3, BorderLayout.CENTER);
			scrollPane3.setBounds(0, 18, scrollPane3.getPreferredSize().width, scrollPane3.getPreferredSize().height); // 위치 조정
		}

	}


	void fillData(ArrayList<LectureBean> lists, Object[][] rowData) {
		// TODO Auto-generated method stub
		int j = 0;
		for (int i = 0; i < lists.size(); i++) {
			rowData[i][j++] = lists.get(i).getNum();
			rowData[i][j++] = lists.get(i).getTitle();
			rowData[i][j++] = lists.get(i).getGrade();
			rowData[i][j++] = lists.get(i).getDepartment();
			rowData[i][j++] = 3;
			j = 0;
		}
	}

	public JTable getTable3() {
		return table3;
	}

	public void setTable3(JTable table3) {
		this.table3 = table3;
	}

	public JLabel getCreditLabel() {
		return creditLabel;
	}

	public void setCreditLabel(JLabel creditLabel) {
		this.creditLabel = creditLabel;
	}

	private String updateCredit() {
		int rowCount = table3.getRowCount();
		int nowCredit = rowCount * 3;
		return "신청한 학점 : " + nowCredit + "학점";
	}
}