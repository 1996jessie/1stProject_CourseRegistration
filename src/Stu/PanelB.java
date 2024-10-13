package Stu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PanelB extends JPanel {
	private JTable table2;
	private JScrollPane scrollPane2;
	static LectureSave lecSave = new LectureSave();

	public PanelB(ArrayList<LectureBean> lists, Object[] columnNames) {
		this.setBackground(Color.WHITE);
		this.setBounds(10, 110, 370, 190);
		this.setLayout(new BorderLayout());

		JLabel label1 = new JLabel("강좌 목록");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(label1.getFont().deriveFont(12.5f));

		this.add(label1, BorderLayout.NORTH);


		Object[][] rowData = new Object[lists.size()][6];
		fillData(lists, rowData);

		compose(rowData, columnNames);
		this.add(scrollPane2, BorderLayout.CENTER);

	}

	void compose(Object[][] rowData, Object[] columnNames) {
		// TODO Auto-generated method stub
		setTable2(new JTable(new DefaultTableModel(rowData, columnNames)));
		getTable2().setDefaultEditor(Object.class, null);
		scrollPane2 = new JScrollPane(getTable2());
		scrollPane2.setPreferredSize(new Dimension(350, 180));

	}

	public JScrollPane getScrollPane2() {
		return scrollPane2;
	}

	public void setScrollPane2(JScrollPane scrollPane2) {
		this.scrollPane2 = scrollPane2;
	}

	void fillData(ArrayList<LectureBean> lists, Object[][] rowData) {
		int j = 0;
		int defaultcapacity = 0;
		for (int i = 0; i < lists.size(); i++) {
			rowData[i][j++] = lists.get(i).getNum();
			rowData[i][j++] = lists.get(i).getTitle();
			rowData[i][j++] = lists.get(i).getGrade();
			rowData[i][j++] = lists.get(i).getDepartment();
			int capacity = lecSave.lecCapacity(lists.get(i).getNum());
			rowData[i][j++] = (capacity == 0) ? defaultcapacity + "/" + lists.get(i).getCapacity() : capacity + "/" + lists.get(i).getCapacity();

			j = 0;
		}
	}


	public JTable getTable2() {
		return table2;
	}

	public void setTable2(JTable table2) {
		this.table2 = table2;
	}



}