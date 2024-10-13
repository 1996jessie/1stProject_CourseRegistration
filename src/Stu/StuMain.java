package Stu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import LoginType.LoginMain;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StuMain {
	private Object[] columnNamesb = {"학수번호", "강의명", "권장학년", "구분", "정원"};
	private Object[] columnNamesc = {"학수번호", "강의명", "권장학년", "구분", "학점"};
	private Object[][] rowData;

	private LectureDao ldao = new LectureDao();
	private LectureSave lecSave = new LectureSave();

	private PanelA panela;
	private PanelB panelb;
	private PanelC panelc;
	private PanelD paneld;

	private ArrayList<LectureBean> lists = null;
	private int result = -1;

	// 중복 체크를 위한 리스트
	private ArrayList<Integer> selectedLectureNum = new ArrayList<>();

	public StuMain(StudentBean sb) {

		if(sb.getId() == 0) {
			int option = JOptionPane.showOptionDialog(null, "유효하지 않은 접근입니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"확인"}, null);
			if (option == JOptionPane.OK_OPTION) {
				LoginMain login = new LoginMain();
			}
		}else {
			lists = ldao.select();
			rowData = new Object[lists.size()][5];

			panela = new PanelA(sb);
			panelb = new PanelB(lists, columnNamesb);
			panelc = new PanelC(columnNamesc, sb);
			paneld = new PanelD();


			JFrame frame = new JFrame();
			frame.setSize(800, 350);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setLayout(null);
			frame.add(panela);
			frame.add(panelb);
			frame.add(panelc);
			frame.add(paneld);

			frame.setVisible(true);


			panelb.getTable2().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					DefaultTableModel tableModel = (DefaultTableModel) panela.getTable1().getModel();

					int grade = Integer.parseInt(tableModel.getValueAt(0, 3).toString());
					String college = tableModel.getValueAt(2, 1).toString();
					int credit = Integer.parseInt(tableModel.getValueAt(2, 3).toString());



					if (e.getClickCount() == 2) {
						int selectedRow = panelb.getTable2().getSelectedRow();
						if (selectedRow != -1) {
							Object[] rowData = new Object[columnNamesb.length];
							for (int i = 0; i < columnNamesb.length; i++) {
								rowData[i] = panelb.getTable2().getValueAt(selectedRow, i);
							}
							int lectureNum = (int) rowData[0]; 

							if (selectedLectureNum.contains(lectureNum)) {
								JOptionPane.showMessageDialog(null, "이미 선택한 강의입니다.");
								return;
							}

							boolean alreadyAdded = false;
							for (int i = 0; i < panelc.getTable3().getRowCount(); i++) {
								if ((int) rowData[0] == (int) panelc.getTable3().getValueAt(i, 0)) {
									alreadyAdded = true;
									break;
								}
							}

							if (alreadyAdded) {
								JOptionPane.showMessageDialog(null, "이미 추가된 강의입니다.");
								return;
							}

							String cap = (String)rowData[4];
							String[] parts = cap.split("/");
							String nowCap = parts[0];
							String capacity = parts[1];

							int nowCapInt = Integer.parseInt(nowCap);
							int capacityInt = Integer.parseInt(capacity);

							if (grade != 1 && rowData[1].equals("신입생수업")) {
								JOptionPane.showMessageDialog(null, "1학년이 아닌 학생은 신입생수업을 수강할 수 없습니다.");
								return;
							} else if (grade != 4 && rowData[1].equals("4학년수업")) {
								JOptionPane.showMessageDialog(null, "4학년이 아닌 학생은 4학년수업을 수강할 수 없습니다.");
								return;
							} else if (!college.equals("공과대학") && rowData[1].equals("공학수학")) {
								JOptionPane.showMessageDialog(null, "공과대학이 아닌 학생은 공학수학을 수강할 수 없습니다.");
								return;
							} else if(nowCapInt >= capacityInt) {
								JOptionPane.showMessageDialog(null, "수강 정원에 도달하였습니다.");
								return;
							}


							boolean checkLecture = false;

							for (int i = 0; i < panelc.getTable3().getRowCount(); i++) {
								boolean same = true;
								for (int j = 0; j < columnNamesc.length; j++) {
									if (!rowData[j].equals(panelc.getTable3().getValueAt(i, j))) {
										same = false;
										break;
									}
								}
								if (same) {
									checkLecture = true;
									break;
								}
							}


							if (!checkLecture) {
								int maxLectures = credit == 21 ? 7 : 6;
								if (panelc.getTable3().getRowCount() < maxLectures) {

									Object[] newRowData = new Object[columnNamesc.length];
									newRowData[0] = rowData[0];
									newRowData[1] = rowData[1];
									newRowData[2] = rowData[2];
									newRowData[3] = rowData[3];
									newRowData[4] = 3;
									DefaultTableModel model = (DefaultTableModel) panelc.getTable3().getModel();
									model.addRow(newRowData);

									String title = (String) rowData[1];
									JOptionPane.showMessageDialog(null, title + " 수강신청 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);


									int num = (int) rowData[0];
									int id = sb.getId();
									result = lecSave.insert(num, id);

									selectedLectureNum.add(lectureNum);
									updateTable2();
								} else {
									JOptionPane.showMessageDialog(null, "최대 학점을 초과하였습니다.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "이미 추가한 강의입니다.");
							}
						}
					}

				}
			});

			panelc.getTable3().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						int selectedRow = panelc.getTable3().getSelectedRow();
						if (selectedRow != -1) {
							Object[] rowData = new Object[columnNamesc.length];
							for (int i = 0; i < columnNamesc.length; i++) {
								rowData[i] = panelc.getTable3().getValueAt(selectedRow, i);
							}

							int lectureNum = (int) rowData[0]; 


							selectedLectureNum.remove((Integer) lectureNum);

							int dialogResult = JOptionPane.showConfirmDialog(null, "강의를 삭제하시겠습니까?", "강의 삭제", JOptionPane.YES_NO_OPTION);
							if (dialogResult == JOptionPane.YES_OPTION) {
								DefaultTableModel model = (DefaultTableModel) panelc.getTable3().getModel();
								model.removeRow(selectedRow);
								String title = (String) rowData[1];
								JOptionPane.showMessageDialog(null, title + " 강의가 삭제되었습니다.");

								// DB에서 강의 삭제
								int num = (int) rowData[0];
								int id = sb.getId();
								result = lecSave.delete(num, id);
								updateTable2();
							}
						}
					}
				}
			});
		}
	}

	protected void updateTable2() {
		lists = ldao.select();
		DefaultTableModel model = (DefaultTableModel) panelb.getTable2().getModel();
		model.setRowCount(0); 

		int defaultCapacity = 0;

		for (LectureBean lecture : lists) {
			int capacity = lecSave.lecCapacity(lecture.getNum());
			String capacityStr = (capacity == 0) ? defaultCapacity + "/" + lecture.getCapacity() : capacity + "/" + lecture.getCapacity();

			Object[] rowData = {lecture.getNum(), lecture.getTitle(), lecture.getGrade(), lecture.getDepartment(), capacityStr};
			model.addRow(rowData);


		}
	}

	public static void main(String[] args) {
		StudentBean sb = new StudentBean();
		new StuMain(sb);
	}
}
