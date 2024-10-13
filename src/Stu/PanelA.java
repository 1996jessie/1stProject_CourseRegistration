package Stu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class PanelA extends JPanel {


	private JTable table1;
	private DefaultTableModel tableModel;

	int credit;
	public PanelA(StudentBean sb) {

		if(sb.getGpa() == 4) {
			credit = 21;
		} else {
			credit = 18;
		}
		this.setBackground(Color.WHITE);
		this.setBounds(10,10, 560, 87);
		this.setLayout(new GridLayout()); 

		String[] columnNames = {"구분", "*","구분","*"};
		Object[][] rowData = {{"학번", sb.getId(),"학년",sb.getGrade()}, {"이름", sb.getName(),"평점",sb.getGpa()}, {"단과대",sb.getCollege(),"최대수강가능학점",credit},{"전공",sb.getMajor()}};
		tableModel = new DefaultTableModel(rowData, columnNames);
		table1 = new JTable(tableModel);
		table1.setBackground(Color.WHITE); 

		getTable1().setDefaultEditor(Object.class, null);

		this.add(table1);

		JScrollPane scrollPane = new JScrollPane(table1);
		this.add(scrollPane);
	}

	public JTable getTable1() {
		return table1;
	}

}



