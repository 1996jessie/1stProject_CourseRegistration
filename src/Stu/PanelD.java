package Stu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanelD extends JPanel{
	private JButton logoutButton;
	private JButton exitButton;
	public PanelD() {
		this.setBackground(Color.WHITE);
		this.setBounds(570, 7, 200, 100);
		this.setLayout(null); 


		logoutButton = new JButton("로그아웃");
		logoutButton.setBounds(10, 10, 180, 30);
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃 확인", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelD.this);
					frame.dispose(); 

					String stu = "로그아웃";
					StuLogin stuLogin = new StuLogin(stu);
					stuLogin.setVisible(true);
				}
			}
		});
		this.add(logoutButton);

		exitButton = new JButton("종료");
		exitButton.setBounds(10, 50, 180, 30);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(0); 
				}
			}
		});
		this.add(exitButton);
	}
}
