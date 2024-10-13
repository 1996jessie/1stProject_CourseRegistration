package Stu;

public class StudentBean {
	private int id;
	private String name;
	private int grade;
	private String college;
	private String major;
	private int gpa;
	private int password;

	public StudentBean() {
		super();
	}

	public StudentBean(int id, String name, int grade, String college, String major, int gpa, int password) {
		super();
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.college = college;
		this.major = major;
		this.gpa = gpa;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getGpa() {
		return gpa;
	}

	public void setGpa(int gpa) {
		this.gpa = gpa;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}




}
