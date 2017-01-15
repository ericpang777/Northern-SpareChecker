package sparechecker;

public class Student {
	private int day;
	private int period;
	private String firstName;
	private String lastName;
	private int studentNumber;
	
	public Student(int day, int period, String firstName, String lastName, int studentNumber) {
		this.day = day;
		this.period = period;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentNumber = studentNumber;
	}

	public int getDay() {
		return day;
	}

	public int getPeriod() {
		return period;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getStudentNumber() {
		return studentNumber;
	}
	
	@Override
	public String toString() {
		return day + "," + period + "," + firstName + "," + lastName + "," + studentNumber;
	}
}
