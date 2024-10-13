package Admin;
import javax.swing.*;

import LoginType.LoginMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends JFrame implements ActionListener {

	private JLabel labelId, labelPassword;
	private JTextField textFieldId;
	private JPasswordField passwordField;
	private JButton buttonLogin, buttonBack;

	private final String ADMIN_ID = "admin";
	private final String ADMIN_PASSWORD = "admin1234";

	private AdminMain admin;

	public AdminLogin(String admin) {
		if(admin == null) {
			int option = JOptionPane.showOptionDialog(null, "유효하지 않은 접근입니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"확인"}, null);
			if (option == JOptionPane.OK_OPTION) {
				LoginMain login = new LoginMain();
			}
		}else {
			setTitle("관리자 로그인");
			setSize(300, 150);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);

			JPanel panel = new JPanel();
			panel.setLayout(null);

			labelId = new JLabel("아이디");
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
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == buttonBack) {
			dispose();
			LoginMain select = new LoginMain(); // LoginTypeSelection 창 열기
			select.setVisible(true);
		} else {

			boolean check = checkData();
			String id = textFieldId.getText();
			String password = new String(passwordField.getPassword());

			if(check) {

				if (id.equals(ADMIN_ID)) {
					if (password.equals(ADMIN_PASSWORD)) {
						JOptionPane.showMessageDialog(this, "관리자 로그인 성공!");
						dispose();
						admin = new AdminMain(ADMIN_ID);
						admin.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(this, "비밀번호가 다릅니다.");
						passwordField.setText("");
						passwordField.requestFocus();
					}
				} else {
					JOptionPane.showMessageDialog(this, "관리자 아이디가 아닙니다.");
					textFieldId.setText("");
					passwordField.setText("");
					textFieldId.requestFocus();
				}
			}
		}
	}

	private boolean checkData() {
		if (textFieldId.getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "아이디를 입력하세요", "에러발생", JOptionPane.INFORMATION_MESSAGE);
			textFieldId.requestFocus();
			return false;
		}
		if (passwordField.getPassword().length == 0) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요", "에러발생", JOptionPane.ERROR_MESSAGE);
			passwordField.requestFocusInWindow();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		new AdminLogin(null);
	}
}