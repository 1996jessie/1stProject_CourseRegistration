package Admin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;


public class LectureDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String id = "sqlid";
	private String pw = "sqlpw";
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public LectureDao() {
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

	public int insert(LectureBean lb) throws SQLIntegrityConstraintViolationException {
		connect();
		int cnt = 0;
		try {
			String sql = "insert into lectures values (?,?,?,?,?)";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, lb.getNum());
			ps.setString(2, lb.getTitle());
			ps.setInt(3,lb.getGrade());
			ps.setString(4, lb.getDepartment());
			ps.setInt(5, lb.getCapacity());

			cnt = ps.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("무결성 오류 발생");
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

	public int update(LectureBean lb) throws SQLException {
		connect();
		int cnt = -1;

		try {
			String sql = "update lectures set title = ?, grade = ?, department= ?, capacity = ? where num = ?";
			ps = conn.prepareStatement(sql);

			ps.setString(1, lb.getTitle());
			ps.setInt(2, lb.getGrade());
			ps.setString(3, lb.getDepartment());
			ps.setInt(4, lb.getCapacity());
			ps.setInt(5, lb.getNum());

			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("무결성 제약 조건 위반");
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

	public int delete(LectureBean lb) {
		connect();
		int cnt = -1;

		try {
			String sql = "delete from lectures where num = ? and title = ? and grade = ? and department = ? and capacity = ?";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, lb.getNum());
			ps.setString(2, lb.getTitle());
			ps.setInt(3, lb.getGrade());
			ps.setString(4, lb.getDepartment());
			ps.setInt(5, lb.getCapacity());

			cnt = ps.executeUpdate();
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

	public ArrayList<LectureBean> select() {
		connect();
		ArrayList<LectureBean> llists = new ArrayList<LectureBean>();
		try {
			String sql = "select * from lectures order by num";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next()) {
				LectureBean lb = new LectureBean();
				lb.setNum(rs.getInt("num"));
				lb.setTitle(rs.getString("title"));
				lb.setGrade(rs.getInt("grade"));
				lb.setDepartment(rs.getString("department"));
				lb.setCapacity(rs.getInt("capacity"));

				llists.add(lb);
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
		return llists;
	}
}