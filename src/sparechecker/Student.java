package sparechecker;

import java.util.Arrays;

/**
 * The Student class stores the first, last names, spares and student number of a student.
 * @author Eric Pang
 * @version 1.0
 */
public class Student {
	private boolean[] spares;
	private String firstName;
	private String lastName;
	private int studentNumber;
	
	/**
	 * Student constructor is initialized with the first spare, names and student number
	 * @param period - the spare period
	 * @param firstName 
	 * @param lastName
	 * @param studentNumber
	 */
	public Student(int period, String firstName, String lastName, int studentNumber) {
		spares = new boolean[8];
		//No spares initially
		Arrays.fill(spares, false);
		spares[period] = true;
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentNumber = studentNumber;
	}
	
	/**
	 * Adds a spare period
	 * @param period
	 */
	public void addSpare(int period) {
		spares[period] = true;
	}
	
	/**
	 * Returns if student has spare during period.
	 * @param period
	 * @return true - there is a spare; false - no spare
	 */
	public boolean hasSpare(int period) {
		return spares[period];
	}

	/**
	 * Gets String firstName
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Gets String lastName
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets int studentNumber
	 * @return studentNumber
	 */
	public int getStudentNumber() {
		return studentNumber;
	}
	
	/**
	 * Overrides toString
	 * @return first and last name with a space
	 */
	@Override 
	public String toString() {
		return firstName + " " + lastName;
	}
}
