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
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
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



public class SecondTabPanel extends JPanel implements ActionListener {

	private Object[] columnNames = { "학번", "이름", "학년", "대학", "전공", "평점" };
	private Object[][] rowData;
	private JTable table = null;
	private JScrollPane scrollPane = null;

	private JButton btnInsert = new JButton("학생 등록");
	private JButton btnUpdate = new JButton("학생 수정");
	private JButton btnDelete = new JButton("학생 삭제");
	private JButton btnLogout = new JButton("로그아웃");

	private JTextField txtId = new JTextField(15);
	private JTextField txtName = new JTextField(15);
	private JComboBox<Integer> StuGradeCb;
	private JComboBox<String> collegeCb;
	private JComboBox<String> majorCb;
	private JComboBox<Integer> gpaCb;

	Scanner sc = new Scanner(System.in);
	StudentDao sdao = new StudentDao();

	ArrayList<StudentBean> lists = null;
	private String[] collegeOptions = { "----", "인문과학대학", "사회과학대학", "자연과학대학", "공과대학", "조예대학", "음악대학" };
	private String[][] majorOptions = { { "----", "국어국문학과", "영어영문학과", "사학과" }, 
			{ "----", "경제학과", "심리학과", "정치외교학과" },
			{ "----", "수학과", "물리학과", "생명과학과" }, 
			{ "----", "기계공학과", "화학공학과", "산업공학과" },
			{ "----", "동양화과", "조소과", "디자인과" }, 
			{ "----", "관현악과", "성악과", "작곡과" }, 
	};
	public SecondTabPanel(String adminId) {

		if(adminId == null) {
			int option = JOptionPane.showOptionDialog(null, "유효하지 않은 접근입니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"확인"}, null);
			if (option == JOptionPane.OK_OPTION) {
				LoginMain login = new LoginMain();
			}
		} else {

			lists = sdao.select();
			rowData = new Object[lists.size()][6];
			fillData();

			table = new JTable(rowData, columnNames);
			table.setDefaultEditor(Object.class, null);
			scrollPane = new JScrollPane(table);

			scrollPane.setPreferredSize(new Dimension(300, 200));
			collegeCb = new JComboBox<>(collegeOptions);
			collegeCb.setBackground(Color.WHITE);
			collegeCb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateMajorComboBox();
				}
			});

			setLayout(null);

			scrollPane.setBounds(10, 170, 560, 150);
			add(scrollPane);

			compose();
			setEvent();
			addListeners();
		}
	}
	protected void updateMajorComboBox() {
		// TODO Auto-generated method stub
		String selectedCollege = (String) collegeCb.getSelectedItem();
		if (selectedCollege != null) {
			switch (selectedCollege) {
			case "인문과학대학":
				majorCb.setModel(new DefaultComboBoxModel<>(majorOptions[0]));
				break;
			case "사회과학대학":
				majorCb.setModel(new DefaultComboBoxModel<>(majorOptions[1]));
				break;
			case "자연과학대학":
				majorCb.setModel(new DefaultComboBoxModel<>(majorOptions[2]));
				break;
			case "공과대학":
				majorCb.setModel(new DefaultComboBoxModel<>(majorOptions[3]));
				break;
			case "조예대학":
				majorCb.setModel(new DefaultComboBoxModel<>(majorOptions[4]));
				break;
			case "음악대학":
				majorCb.setModel(new DefaultComboBoxModel<>(majorOptions[5]));
				break;
			default:
				majorCb.setModel(new DefaultComboBoxModel<>());
				break;
			}
		}
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnLogout.addActionListener(this);
	}
	private void setEvent() {
		// TODO Auto-generated method stub
		txtId.addKeyListener(new KeyHandler());
		table.addMouseListener(new MouseHandler());


	}
	private void compose() {
		// TODO Auto-generated method stub
		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(null);
		buttonPanel.setBounds(400, 10, 150, 200); // 버튼 패널의 위치와 크기를 직접 설정
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
		pCenter.setBounds(20,10,350,150);
		add(pCenter);

		JLabel lbId = new JLabel("학번");
		JLabel lbName = new JLabel("이름");
		JLabel lbGrade = new JLabel("학년");
		Integer[] gradeOptions = { 0, 1, 2, 3, 4 };
		StuGradeCb = new JComboBox<>(gradeOptions);
		StuGradeCb.setBackground(Color.WHITE);
		JLabel lbCollege = new JLabel("소속대학");
		JLabel lbMajor = new JLabel("전공");
		majorCb = new JComboBox<>();
		majorCb.setBackground(Color.WHITE);
		JLabel lbGpa = new JLabel("평점");
		gpaCb = new JComboBox<>(new Integer[] { 0, 1, 2, 3, 4 });
		gpaCb.setBackground(Color.WHITE);


		lbId.setBounds(20, 0, 100, 20);
		lbName.setBounds(20, 25, 100, 20);
		lbGrade.setBounds(20, 50, 100, 20);
		lbCollege.setBounds(20, 75, 100, 20);
		lbMajor.setBounds(20, 100, 100, 20);
		lbGpa.setBounds(20, 125, 100, 20);

		pCenter.add(lbId);
		pCenter.add(lbName);
		pCenter.add(lbGrade);
		pCenter.add(lbCollege);
		pCenter.add(lbMajor);
		pCenter.add(lbGpa);

		txtId.setBounds(140, 0, 100, 20);
		txtName.setBounds(140, 25, 100, 20);
		StuGradeCb.setBounds(140, 50, 100, 20);
		collegeCb.setBounds(140, 75, 100, 20);
		majorCb.setBounds(140, 100, 100, 20);
		gpaCb.setBounds(140, 125, 100, 20);

		pCenter.add(txtId);
		pCenter.add(txtName);
		pCenter.add(StuGradeCb);
		pCenter.add(collegeCb);
		pCenter.add(majorCb);
		pCenter.add(gpaCb);
	}



	public static void main(String[] args) {
		new SecondTabPanel(null);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnInsert) {
			insert();
		} else if (obj == btnUpdate) {
			update();
		} else if (obj == btnDelete) {
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
	private void delete() {

		int row = table.getSelectedRow();
		if (row != -1) {

			int id = Integer.parseInt(txtId.getText());
			String name = txtName.getText();
			int grade = (int) StuGradeCb.getSelectedItem();
			String college = (String) collegeCb.getSelectedItem();
			String major = (String) majorCb.getSelectedItem();
			int gpa = (int) gpaCb.getSelectedItem();

			StudentBean sb = new StudentBean(id, name, grade, college, major, gpa);

			int cnt = sdao.delete(sb);
			if (cnt == -1) {

			} else if (cnt == 0) {

			} else {

				clearTextField();
				txtId.setEnabled(true);
				getAllLectures();
				JOptionPane.showMessageDialog(this, "학생이 삭제되었습니다.", "YES", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "삭제할 레코드를 선택하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
		}
		txtId.setEditable(true);
		StuGradeCb.setSelectedItem(0);
		collegeCb.setSelectedIndex(0);
	}
	private void update() {

		boolean check = checkData();
		if (check) {
			int id = Integer.parseInt(txtId.getText());
			String name = txtName.getText();
			int grade = (int) StuGradeCb.getSelectedItem();
			String college = (String) collegeCb.getSelectedItem();
			String major = (String) majorCb.getSelectedItem();
			int gpa = (int) gpaCb.getSelectedItem();

			if (grade == 0) {
				JOptionPane.showMessageDialog(this, "학년을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			} else if ("----".equals(college)) {
				JOptionPane.showMessageDialog(this, "소속 대학을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			} else if ("----".equals(major)) {
				JOptionPane.showMessageDialog(this, "전공을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (gpa == 0) {
				JOptionPane.showMessageDialog(this, "평점을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			}

			StudentBean sb = new StudentBean(id, name, grade, college, major, gpa);
			int result = 0;
			result = sdao.update(sb);

			if (result == -1) {

			} else if (result == 0) {

			} else {

				clearTextField();
				txtId.setEnabled(true);
				getAllLectures();
				JOptionPane.showMessageDialog(this, "학생이 수정되었습니다.", "YES", JOptionPane.INFORMATION_MESSAGE);
			}
			txtId.setEditable(true);

		}
	}
	private void insert() {

		boolean check = checkData();
		if (check) {
			int num = Integer.parseInt(txtId.getText());
			String name = txtName.getText();
			int grade = (int) StuGradeCb.getSelectedItem();
			String college = (String) collegeCb.getSelectedItem();
			String major = (String) majorCb.getSelectedItem();
			int gpa = (int) gpaCb.getSelectedItem();

			if (grade == 0) {
				JOptionPane.showMessageDialog(this, "학년을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			} else if ("----".equals(college)) {
				JOptionPane.showMessageDialog(this, "소속 대학을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			} else if ("----".equals(major)) {
				JOptionPane.showMessageDialog(this, "전공을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (gpa == 0) {
				JOptionPane.showMessageDialog(this, "평점을 선택하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
				return;
			}

			StudentBean sb = new StudentBean(num, name, grade, college, major, gpa);
			int result = 0;

			try {
				result = sdao.insert(sb);
				if (result != 0) {
					clearTextField();
					getAllLectures();
					JOptionPane.showMessageDialog(this, "학생이 등록되었습니다.", "YES", JOptionPane.INFORMATION_MESSAGE);
					txtId.setEditable(true);

				}
			} catch (SQLIntegrityConstraintViolationException e) {
				JOptionPane.showMessageDialog(this, "학번이 중복되었습니다.", "에러발생", JOptionPane.ERROR_MESSAGE);
				txtId.setText(""); 
				txtId.requestFocus(); 
				txtId.setEnabled(true);
				txtId.setEditable(true); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private void getAllLectures() {

		lists = sdao.select();
		rowData = new Object[lists.size()][6];
		fillData();

		table = new JTable(rowData, columnNames);
		table.addMouseListener(new MouseHandler());
		scrollPane.setViewportView(table);
	}
	private void clearTextField() {

		txtId.setText("");
		txtName.setText("");
		StuGradeCb.setSelectedItem(0);
		collegeCb.setSelectedItem("----");
		majorCb.setSelectedItem("----");
		gpaCb.setSelectedItem(0);
	}
	private boolean checkData() {
		// TODO Auto-generated method stub
		if (txtId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "학번을 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
			txtId.requestFocus();
			return false;
		}
		if (txtId.getText().length() != 6) {
			JOptionPane.showMessageDialog(this, "학번은 6자리로 입력해야 합니다", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtId.setText("");
			txtId.requestFocus();
			return false;
		}
		if (txtName.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "이름을 입력하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
			txtName.requestFocus();
			return false;
		}
		return true;
	}
	private void fillData() {

		int j = 0;
		for (int i = 0; i < lists.size(); i++) {
			rowData[i][j++] = lists.get(i).getId();
			rowData[i][j++] = lists.get(i).getName();
			rowData[i][j++] = lists.get(i).getGrade();
			rowData[i][j++] = lists.get(i).getCollege();
			rowData[i][j++] = lists.get(i).getMajor();
			rowData[i][j++] = lists.get(i).getGpa();
			j = 0;
		}
	}
	class KeyHandler extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			Object obj = e.getSource();
			if (obj == txtId) {
				try {
					Integer.parseInt(txtId.getText());
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(txtId, "학번은 숫자로 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
					txtId.setText("");
					txtId.requestFocus();
				}
			}
			if (obj == txtId) {
				String idText = txtId.getText();
				if (idText.length() > 1) {
					char firstDigit = idText.charAt(0);
					char secondDigit = idText.charAt(1);
					if (firstDigit == '2') {
						if (secondDigit >= '5') {
							JOptionPane.showMessageDialog(txtId, "학번의 첫 두자리에는 24 이하의 숫자만 입력해야 합니다.", "오류", JOptionPane.INFORMATION_MESSAGE);
							txtId.setText("");
						} else if (secondDigit == '4') {
							StuGradeCb.setSelectedItem(1);

							if ((int) StuGradeCb.getSelectedItem() == 1) {
								gpaCb.setSelectedItem(3);

							}
						}

					} else if (firstDigit >= '3') {
						JOptionPane.showMessageDialog(txtId, "학번의 첫 번째 숫자는 2 이하여야 합니다.", "오류", JOptionPane.INFORMATION_MESSAGE);
						txtId.setText("");
					} else {
						StuGradeCb.setSelectedIndex(0);
						StuGradeCb.setEnabled(true);
					}
				}
			}
		}
	}
	class MouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			if (row != -1) {
				txtId.setText(table.getValueAt(row, 0).toString());
				txtName.setText(table.getValueAt(row, 1).toString());
				int grade = Integer.parseInt(table.getValueAt(row, 2).toString());
				String college = table.getValueAt(row, 3).toString();
				String major = table.getValueAt(row, 4).toString();
				int gpa = Integer.parseInt(table.getValueAt(row, 5).toString());
				txtId.setEnabled(false);
				StuGradeCb.setSelectedItem(grade);
				collegeCb.setSelectedItem(college);
				majorCb.setSelectedItem(major);
				gpaCb.setSelectedItem(gpa);
			}
		}
	}

}