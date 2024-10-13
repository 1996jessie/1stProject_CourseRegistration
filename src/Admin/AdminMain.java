package Admin;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import LoginType.LoginMain;

public class AdminMain extends JFrame {
	public AdminMain(String adminId) {

		if(adminId == null) {
			int option = JOptionPane.showOptionDialog(null, "유효하지 않은 접근입니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"확인"}, null);
			if (option == JOptionPane.OK_OPTION) {
				LoginMain login = new LoginMain();
			}
		} else {
			setTitle("관리자 페이지");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JTabbedPane tabbedPane = new JTabbedPane();

			tabbedPane.addTab("강좌 관리", new FirstTabPanel(adminId));
			tabbedPane.addTab("학생 관리", new SecondTabPanel(adminId));

			getContentPane().add(tabbedPane);

			setSize(600, 400);
			setLocationRelativeTo(null);
			setVisible(true);

		}
	}

	public static void main(String[] args) {
		new AdminMain(null);
	}
}