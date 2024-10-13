package Stu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LectureSave {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String id = "sqlid";
	private String pw = "sqlpw";
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int cnt = 0;


	public LectureSave() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			conn = DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(int num, int id) {
		// TODO Auto-generated method stub
		connect();
		String sql = "insert into save values (?,?)";
		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, num);
			ps.setInt(2, id);

			int cnt = ps.executeUpdate();




		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cnt;
	}

	public int delete(int num, int id) {
		// TODO Auto-generated method stub
		connect();
		String sql = "delete from save where lecnum = ? and stuid = ?";

		try {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, num);
			ps.setInt(2, id);

			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cnt;

	}

	public ArrayList<LectureBean> saveLecturebyId(int id) {
		connect();
		ArrayList<LectureBean> lists = new ArrayList<LectureBean>();
		try {
			String sql = "select num, title, grade, department, capacity from lectures, save where num = lecnum and stuid = ?";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			rs = ps.executeQuery();

			while(rs.next()) {
				LectureBean lb = new LectureBean();
				lb.setNum(rs.getInt("num"));
				lb.setTitle(rs.getString("title"));
				lb.setGrade(rs.getInt("grade"));
				lb.setDepartment(rs.getString("department"));
				lb.setCapacity(rs.getInt("capacity"));

				lists.add(lb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			}  catch ( SQLException e) {
				e.printStackTrace();
			}
		}
		return lists;
	}

	public int lecCapacity(int num) {
		// TODO Auto-generated method stub
		int capacity = 0;
		connect();

		try {
			String sql = "select lecnum, count(*) as capacity from save where lecnum = ? group by lecnum";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, num);
			rs = ps.executeQuery();

			while(rs.next()) {
				capacity = rs.getInt("capacity");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			}  catch ( SQLException e) {
				e.printStackTrace();
			}
		}
		return capacity;

	}


}
