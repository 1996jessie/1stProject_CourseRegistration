package Admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class StudentDao {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String id = "sqlid";
	private String pw = "sqlpw";
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;


	public StudentDao() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} //StudentDao

	public void connect() {
		try {
			conn = DriverManager.getConnection(url, id, pw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} //connect

	public ArrayList<StudentBean> select() {
		// TODO Auto-generated method stub
		connect();
		ArrayList<StudentBean> slists = new ArrayList<StudentBean>();
		try {
			String sql = "select * from students order by id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next()) {
				StudentBean sb = new StudentBean();
				sb.setId(rs.getInt("id"));
				sb.setName(rs.getString("name"));
				sb.setGrade(rs.getInt("grade"));
				sb.setMajor(rs.getString("major"));
				sb.setCollege(rs.getString("college"));
				sb.setGpa(rs.getInt("gpa"));

				slists.add(sb);
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
		return slists;
	}

	public int insert(StudentBean sb) throws SQLIntegrityConstraintViolationException {
		connect();
		int cnt = 0;
		try {
			String sql = "insert into students (id, name, grade, major, college, gpa) values (?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, sb.getId());
			ps.setString(2, sb.getName());
			ps.setInt(3, sb.getGrade());
			ps.setString(4, sb.getMajor());
			ps.setString(5, sb.getCollege());
			ps.setInt(6, sb.getGpa());

			cnt = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw e; 
		} catch (SQLException e) {
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
				e.printStackTrace();
			}
		}
		return cnt;
	}



	public int update(StudentBean sb) {
		// TODO Auto-generated method stub
		connect();
		int cnt = -1;

		try {
			String sql = "update students set college = ?, major = ?, gpa = ? where id = ?";
			ps = conn.prepareStatement(sql);

			ps.setString(1, sb.getCollege());
			ps.setString(2, sb.getMajor());
			ps.setInt(3, sb.getGpa());
			ps.setInt(4, sb.getId());

			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return cnt;
	}

	public int delete(StudentBean sb) {
		// TODO Auto-generated method stub
		connect();
		int cnt = -1;

		try {
			String sql = "delete from students where id = ? and name = ? and grade = ? and college = ? and major = ? and gpa = ?";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, sb.getId());
			ps.setString(2, sb.getName());
			ps.setInt(3,sb.getGrade());
			ps.setString(4, sb.getCollege());
			ps.setString(5, sb.getMajor());
			ps.setInt(6,sb.getGpa());

			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cnt;
	}


}
