package Stu;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import LoginType.LoginMain;

public class StuLogin extends JFrame implements ActionListener {

	private JLabel labelId, labelPassword;
	private JTextField textFieldId;
	private JPasswordField passwordField;
	private JButton buttonLogin, buttonBack;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String id = "sqlid";
	private String pw = "sqlpw";


	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	private StuMain stu;


	public StuLogin(String stu) {

		try {
			Class.forName(driver);
			setTitle("학생 로그인");
			setSize(300, 150);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);

			JPanel panel = new JPanel();
			panel.setLayout(null);

			labelId = new JLabel("학번");
			labelId.setBounds(20, 10, 80, 25);
			panel.add(labelId);

			textFieldId = new JTextField(20);
			textFieldId.setBounds(100, 10, 165, 25);
			panel.add(textFieldId);

			labelPassword = new JLabel("비밀번호");
			labelPassword.setBounds(20, 40, 80, 25);
			panel.add(labelPassword);

			passwordField = new JPasswordField(20);
			passwordField.setBounds(100, 40, 165, 25);
			panel.add(passwordField);

			buttonLogin = new JButton("로그인");
			buttonLogin.setBounds(40, 75, 80, 25);
			buttonLogin.addActionListener(this);
			panel.add(buttonLogin);

			buttonBack = new JButton("돌아가기");
			buttonBack.setBounds(140,75,100,25);
			buttonBack.addActionListener(this);
			panel.add(buttonBack);

			add(panel);
			setVisible(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		setEvent();


	}


	private void setEvent() {
		// TODO Auto-generated method stub
		textFieldId.addKeyListener(new KeyHandler());
		passwordField.addKeyListener(new KeyHandler());
	}



	public void connect() {
		try {
			conn = DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new StuLogin(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonBack) {
			dispose();
			LoginMain select = new LoginMain(); // LoginTypeSelection 창 열기
			select.setVisible(true);
		} else {
			boolean check = checkData();
			if(check) {
				if (e.getSource() == buttonLogin) {
					int id = Integer.parseInt(textFieldId.getText());
					int password = Integer.parseInt(new String(passwordField.getPassword()));
					connect();
					try {
						String sql = "select * from students where id = ? and password = ?";

						ps = conn.prepareStatement(sql);
						ps.setInt(1, id);
						ps.setInt(2, password);

						rs = ps.executeQuery();

						if(rs.next()) {
							JOptionPane.showMessageDialog(this, "로그인 성공!");
							dispose();
							StudentBean sb = getStudentInfo(id);
							stu = new StuMain(sb);
						} else {
							JOptionPane.showMessageDialog(this, "로그인 실패. 학번 또는 비밀번호를 확인하세요.");
							textFieldId.setText("");
							passwordField.setText("");
							textFieldId.requestFocus();

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} finally {
						try {
							if(rs != null) {
								rs.close();
							}
							if(ps != null) {
								ps.close();
							}
							if(conn != null) {
								conn.close();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}

	}

	private StudentBean getStudentInfo(int id) {
		// TODO Auto-generated method stub
		connect();
		StudentBean sb = null;

		try {
			String sql = "select * from students where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();
			while(rs.next()) {
				sb = new StudentBean();
				sb.setId(rs.getInt("id"));
				sb.setName(rs.getString("name"));
				sb.setGrade(rs.getInt("grade"));
				sb.setMajor(rs.getString("major"));
				sb.setCollege(rs.getString("college"));
				sb.setGpa(rs.getInt("gpa"));


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}if(ps != null) {
					ps.close();
				}if(conn != null) {
					conn.close();
				}
			}  catch ( SQLException e) {
				e.printStackTrace();
			}
		}
		return sb;
	}

	private boolean checkData() {
		if (textFieldId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "학번을 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
			textFieldId.requestFocus();
			return false;
		}
		if (textFieldId.getText().length() != 6) {
			JOptionPane.showMessageDialog(this, "학번은 여섯자리로 입력해야 합니다.", "에러발생", JOptionPane.ERROR_MESSAGE);
			textFieldId.setText("");
			textFieldId.requestFocus();
			return false;
		}
		if (passwordField.getPassword().length == 0) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
			passwordField.requestFocusInWindow();
			return false;
		}
		if (passwordField.getPassword().length != 4 ) {
			JOptionPane.showMessageDialog(this, "비밀번호는 4자리로 입력해야 합니다", "에러발생", JOptionPane.PLAIN_MESSAGE);
			passwordField.setText("");
			passwordField.requestFocus();
			return false;
		}
		return true;
	}

	class KeyHandler extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			Object obj = e.getSource();
			if (obj == textFieldId) {
				try {
					Integer.parseInt(textFieldId.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(textFieldId, "학번은 숫자로 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
					textFieldId.setText("");
					textFieldId.requestFocus();
				}
			} else if (obj == passwordField) {
				try {
					Integer.parseInt(new String(passwordField.getPassword()));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(passwordField, "비밀번호는 숫자로 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
					passwordField.setText("");
					passwordField.requestFocus();
				}
			}
		}
	}
}