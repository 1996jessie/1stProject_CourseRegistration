package Admin;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import LoginType.LoginMain;

public class FirstTabPanel extends JPanel implements ActionListener {
	private Object[] columnNames = {"학수번호", "강의명", "권장학년", "구분", "정원"};
	private Object[][] rowData;
	private JTable table = null;
	private JScrollPane scrollPane = null;
	private JButton btnInsert = new JButton("강좌 등록");
	private JButton btnUpdate = new JButton("강좌 수정");
	private JButton btnDelete = new JButton("강좌 삭제");
	private JButton btnLogout = new JButton("로그아웃");
	private JTextField txtNum = new JTextField(15);
	private JTextField txtTitle = new JTextField(15);
	private JTextField txtCapacity = new JTextField(15);
	LectureDao ldao = new LectureDao();
	ArrayList<LectureBean> lists = null;
	private JComboBox<Integer> gradeCb;
	private JComboBox<String> departmentCb;

	public FirstTabPanel(String adminId) {

		if(adminId == null) {
			int option = JOptionPane.showOptionDialog(null, "유효하지 않은 접근입니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"확인"}, null);
			if (option == JOptionPane.OK_OPTION) {
				LoginMain login = new LoginMain();
			}
		} else {
			lists = ldao.select();
			rowData = new Object[lists.size()][5];
			fillData();
			table = new JTable(rowData, columnNames);
			table.setDefaultEditor(Object.class, null);
			scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(300, 200));
			setLayout(null);
			scrollPane.setBounds(10, 170, 560, 150);
			add(scrollPane);
			compose();
			setEvent();
			addListeners();
		}
	}

	private void setEvent() {
		txtNum.addKeyListener(new KeyHandler());
		txtCapacity.addKeyListener(new KeyHandler());
		table.addMouseListener(new MouseHandler());
	}

	private void fillData() {
		int j = 0;
		for (int i = 0; i < lists.size(); i++) {
			rowData[i][j++] = lists.get(i).getNum();
			rowData[i][j++] = lists.get(i).getTitle();
			rowData[i][j++] = lists.get(i).getGrade();
			rowData[i][j++] = lists.get(i).getDepartment();
			rowData[i][j++] = lists.get(i).getCapacity();
			j = 0;
		}
	}

	private void compose() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBounds(400, 10, 150, 200);
		add(buttonPanel);
		btnInsert.setBounds(10, 0, 120, 30);
		btnUpdate.setBounds(10, 40, 120, 30);
		btnDelete.setBounds(10, 80, 120, 30);
		btnLogout.setBounds(10, 120, 120, 30);
		buttonPanel.add(btnInsert);
		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnLogout);
		JPanel pCenter = new JPanel();
		pCenter.setLayout(null);
		pCenter.setBounds(20, 10, 350, 150);
		add(pCenter);
		JLabel lbNum = new JLabel("학수번호");
		JLabel lbTitle = new JLabel("강좌명");
		JLabel lbGrade = new JLabel("권장학년");
		Integer[] gradeOptions = {0, 1, 2, 3, 4};
		gradeCb = new JComboBox<>(gradeOptions);
		gradeCb.setBackground(Color.WHITE);
		JLabel lbdepartment = new JLabel("구분");
		String[] departmentOptions = {"----", "필수교양", "일반교양", "전공필수", "전공선택"};
		departmentCb = new JComboBox<>(departmentOptions);
		departmentCb.setBackground(Color.WHITE);
		JLabel lbCapacity = new JLabel("정원");
		lbNum.setBounds(20, 10, 100, 20);
		lbTitle.setBounds(20, 38, 100, 20);
		lbGrade.setBounds(20, 66, 100, 20);
		lbdepartment.setBounds(20, 94, 100, 20);
		lbCapacity.setBounds(20, 122, 100, 20);
		pCenter.add(lbNum);
		pCenter.add(lbTitle);
		pCenter.add(lbGrade);
		pCenter.add(lbdepartment);
		pCenter.add(lbCapacity);
		txtNum.setBounds(140, 10, 100, 20);
		txtTitle.setBounds(140, 38, 100, 20);
		gradeCb.setBounds(140, 66, 100, 20);
		departmentCb.setBounds(140, 94, 100, 20);
		txtCapacity.setBounds(140, 122, 100, 20);
		pCenter.add(txtNum);
		pCenter.add(txtTitle);
		pCenter.add(gradeCb);
		pCenter.add(departmentCb);
		pCenter.add(txtCapacity);
	}

	private void addListeners() {
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnLogout.addActionListener(this);
	}

	public static void main(String[] args) {
		new FirstTabPanel(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnInsert) {
			System.out.println("강좌 등록");
			insert();
		} else if (obj == btnUpdate) {
			System.out.println("강좌 수정");
			update();
		} else if (obj == btnDelete) {
			System.out.println("강좌 삭제");
			delete();
		} else if (obj == btnLogout) {
			int choice = JOptionPane.showConfirmDialog(null, "로그아웃하시겠습니까?", "로그아웃 확인", JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				// 로그아웃 처리
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // 현재 창을 얻기 위해
				frame.dispose(); // 현재 창 닫기
				LoginMain select = new LoginMain(); // LoginTypeSelection 창 열기 // LoginSelectionType 창 열기
				select.setVisible(true);
			}
		}
	}

	private void insert() {
		boolean check = checkData();
		if (check) {
			int num = Integer.parseInt(txtNum.getText());
			String title = txtTitle.getText();
			int grade = (int) gradeCb.getSelectedItem();
			String department = (String) departmentCb.getSelectedItem();
			int capacity = Integer.parseInt(txtCapacity.getText());
			if (capacity > 50) {
				JOptionPane.showMessageDialog(this, "강의실 정원(50명)을 초과했습니다.", "에러발생", JOptionPane.ERROR_MESSAGE);
				txtCapacity.setText("");
				txtCapacity.requestFocus();
				return;
			} else if (grade == 0) {
				JOptionPane.showMessageDialog(this, "권장학년을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			} else if ("----".equals(department)) {
				JOptionPane.showMessageDialog(this, "구분을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			}
			LectureBean lb = new LectureBean(num, title, grade, department, capacity);
			int result = 0;
			try {
				result = ldao.insert(lb);
				if (result != 0) {
					clearTextField();
					getAllLectures();
					JOptionPane.showMessageDialog(this, "강의가 등록되었습니다", "YES", JOptionPane.INFORMATION_MESSAGE);
					txtNum.setEditable(true);
					gradeCb.setSelectedItem(0);
					departmentCb.setSelectedIndex(0);
				}
			} catch (SQLIntegrityConstraintViolationException e) {
				JOptionPane.showMessageDialog(this, "학수번호가 중복되었습니다.", "에러발생", JOptionPane.ERROR_MESSAGE);
				txtNum.setText("");
				txtNum.requestFocus();
				txtNum.setEnabled(true);
				txtNum.setEditable(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void update() {
		boolean check = checkData();
		if (check) {
			int num = Integer.parseInt(txtNum.getText());
			String title = txtTitle.getText();
			int grade = (int) gradeCb.getSelectedItem();
			String department = (String) departmentCb.getSelectedItem();
			int capacity = Integer.parseInt(txtCapacity.getText());
			if (capacity > 50) {
				JOptionPane.showMessageDialog(this, "강의실 정원(50명)을 초과했습니다.", "에러발생", JOptionPane.ERROR_MESSAGE);
				txtCapacity.setText("");
				txtCapacity.requestFocus();
				return;
			}
			LectureBean lb = new LectureBean(num, title, grade, department, capacity);
			int result = 0;
			try {
				result = ldao.update(lb);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (result == -1) {

			} else if (result == 0) {

			} else {

				clearTextField();
				txtNum.setEnabled(true);
				getAllLectures();
				JOptionPane.showMessageDialog(this, "강의가 수정되었습니다", "YES", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private boolean checkData() {
		if (txtNum.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "학수번호를 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
			txtNum.requestFocus();
			return false;
		}
		if (txtNum.getText().length() != 5) {
			JOptionPane.showMessageDialog(this, "학수번호는 5자리로 입력해야 합니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtNum.setText("");
			txtNum.requestFocus();
			return false;
		}
		if (txtTitle.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "과목명을 입력하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtTitle.requestFocus();
			return false;
		}
		if (txtCapacity.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "정원을 입력하세요", "에러발생", JOptionPane.PLAIN_MESSAGE);
			txtCapacity.requestFocus();
			return false;
		}
		return true;
	}

	private void getAllLectures() {
		lists = ldao.select();
		rowData = new Object[lists.size()][5];
		fillData();
		table = new JTable(rowData, columnNames);
		table.addMouseListener(new MouseHandler());
		scrollPane.setViewportView(table);
	}

	private void delete() {
		int row = table.getSelectedRow();
		String department = (String) departmentCb.getSelectedItem();
		if (row != -1) {
			if ("전공필수".equals(department)) {
				JOptionPane.showMessageDialog(this, "전공필수과목은 삭제할 수 없습니다.", "에러발생", JOptionPane.INFORMATION_MESSAGE);
				clearTextField();
				return;
			} else if ("필수교양".equals(department)) {
				JOptionPane.showMessageDialog(this, "필수교양과목은 삭제할 수 없습니다.", "에러발생", JOptionPane.INFORMATION_MESSAGE);
				clearTextField();
				return;
			} else {
				int num = Integer.parseInt(txtNum.getText());
				String title = txtTitle.getText();
				int grade = (int) gradeCb.getSelectedItem();
				department = (String) departmentCb.getSelectedItem();
				int capacity = Integer.parseInt(txtCapacity.getText());
				LectureBean lb = new LectureBean(num, title, grade, department, capacity);
				int cnt = ldao.delete(lb);
				if (cnt == -1) {

				} else if (cnt == 0) {

				} else {

					clearTextField();
					txtNum.setEnabled(true);
					getAllLectures();
					JOptionPane.showMessageDialog(this, "강의가 삭제되었습니다", "YES", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "삭제할 레코드를 선택하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void clearTextField() {
		txtNum.setText("");
		txtTitle.setText("");
		gradeCb.setSelectedItem(0);
		departmentCb.setSelectedItem("----");
		txtCapacity.setText("");
	}

	class KeyHandler extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			Object obj = e.getSource();
			if (obj == txtNum) {
				try {
					Integer.parseInt(txtNum.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(txtNum, "학수번호는 숫자로 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
					txtNum.setText("");
					txtNum.requestFocus();
				}
			} else if (obj == txtCapacity) {
				try {
					Integer.parseInt(txtCapacity.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(txtCapacity, "정원은 숫자로 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
					txtCapacity.setText("");
					txtCapacity.requestFocus();
				}
			}
		}
	}

	class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			if (row != -1) {
				txtNum.setText(table.getValueAt(row, 0).toString());
				txtTitle.setText(table.getValueAt(row, 1).toString());
				int grade = Integer.parseInt(table.getValueAt(row, 2).toString());
				String department = table.getValueAt(row, 3).toString();
				txtCapacity.setText(table.getValueAt(row, 4).toString());
				txtNum.setEnabled(false);
				if ("전공필수".equals(department) || "필수교양".equals(department)) {
					departmentCb.setEnabled(false);
				} else {
					departmentCb.setEnabled(true);
				}
				gradeCb.setSelectedItem(grade);
				departmentCb.setSelectedItem(department);
			}
		}
	}
}
