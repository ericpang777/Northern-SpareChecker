package sparechecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReadData {
	private static ArrayList<Student> students;
	
	private static String pathSpareFile1 = "data/spare file day 1.csv";
	private static String pathSpareFile2 = "data/spare file day 2.csv";
	
	public static void read() {
		students = new ArrayList<>();
		try {
			FileReader fr = new FileReader(pathSpareFile1);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] values;
			br.readLine(); // Skip header
			while ((line = br.readLine()) != null) {
				values = line.split(",");

				if (students.size() == 0) {
					students.add(new Student(Integer.parseInt(values[0]) - 1, values[1], values[2],
							Integer.parseInt(values[3])));
				} else {
					boolean found = false;
					for (int i = 0; i < students.size() && !found; i++) {
						if (students.get(i).getStudentNumber() == Integer.parseInt(values[3])) {
							// Spares numbered from 1 - 4, index is from 0-3
							students.get(i).addSpare(Integer.parseInt(values[0]) - 1);
							found = true;
						}
					}
					if (!found) {
						students.add(new Student(Integer.parseInt(values[0]) - 1, values[1], values[2],
								Integer.parseInt(values[3])));
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("spare file day 1.csv not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileReader fr = new FileReader(pathSpareFile2);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] values;
			br.readLine(); // Skip header
			while ((line = br.readLine()) != null) {
				values = line.split(",");

				if (students.size() == 0) {
					students.add(new Student(Integer.parseInt(values[0]) + 3, values[1], values[2],
							Integer.parseInt(values[3])));
				} else {
					boolean found = false;
					for (int i = 0; i < students.size() && !found; i++) {
						if (students.get(i).getStudentNumber() == Integer.parseInt(values[3])) {
							// Spares numbered from 1-4, index is from 4-7
							students.get(i).addSpare(Integer.parseInt(values[0]) + 3);
							found = true;
						}
					}
					if (!found) {
						students.add(new Student(Integer.parseInt(values[0]) + 3, values[1], values[2],
								Integer.parseInt(values[3])));
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("spare file day 2.csv not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sortByPeriod(ArrayList<Student> list, int period) {
		for(Student s : students) {
			if(s.hasSpare(period)) {
				list.add(s);
			}
		}
	}
	
	public static void sortByFirstName(ArrayList<Student> list) {
		Collections.sort(list, Comparator.comparing(Student::getFirstName));
		for(Student s: list) {
			System.out.println(s);
		}
	}
}
