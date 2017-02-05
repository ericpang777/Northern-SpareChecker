package sparechecker;

import java.util.Arrays;

public class Student {
	private boolean[] spares;
	private String firstName;
	private String lastName;
	private int studentNumber;
	
	public Student(int period, String firstName, String lastName, int studentNumber) {
		spares = new boolean[8];
		Arrays.fill(spares, false);
		spares[period] = true;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentNumber = studentNumber;
	}
	
	public void addSpare(int period) {
		spares[period] = true;
	}

	public boolean hasSpare(int period) {
		return spares[period];
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
		String spareString = "";
		for(int i = 0; i < spares.length; i++) {
			if (spares[i]) {
				spareString += (char)(65+i);
			}
		}
		return firstName + " " + lastName + " " + studentNumber + " " + spareString;
	}
}
