package LoginType;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Admin.AdminLogin;
import Stu.StuLogin;

public class LoginMain extends JFrame implements ActionListener {

	private JButton buttonAdmin, buttonStudent, buttonExit;

	public LoginMain() {
		setTitle("로그인 유형 선택");
		setSize(300, 150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		buttonAdmin = new JButton("관리자");
		buttonAdmin.setBounds(50, 20, 90, 30);
		buttonAdmin.addActionListener(this);
		panel.add(buttonAdmin);

		buttonStudent = new JButton("학생");
		buttonStudent.setBounds(150, 20, 90, 30);
		buttonStudent.addActionListener(this);
		panel.add(buttonStudent);

		buttonExit = new JButton("프로그램 종료");
		buttonExit.setBounds(50, 60, 190, 30);
		buttonExit.addActionListener(this);
		panel.add(buttonExit);

		add(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonAdmin) {
			new AdminLogin("관리자 로그인");
			dispose(); 
		} else if (e.getSource() == buttonStudent) {
			new StuLogin("학생 로그인");
			dispose();
		} else if (e.getSource() == buttonExit) {
			int choice = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				System.exit(0); 
			}
		}
	}

	public static void main(String[] args) {
		new LoginMain();
	}
}