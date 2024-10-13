package Admin;


public class LectureBean {

	private int num;
	private String title;
	private int grade;
	private String department;
	private int capacity;

	public LectureBean(int num, String title, int grade, String department, int capacity) {
		super();
		this.num = num;
		this.title = title;
		this.grade = grade;
		this.department = department;
		this.capacity = capacity;
	}

	public LectureBean() {
		super();
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}




}