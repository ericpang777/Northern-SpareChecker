package sparechecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JOptionPane;

public class StudentIO {
	private static ArrayList<Student> students;
	private static FileWriter logFW;
	private static BufferedWriter logBW;
	
	private static String pathSpareFile1 = "data/spare file day 1.csv";
	private static String pathSpareFile2 = "data/spare file day 2.csv";
	private static String pathFile = "path.txt";
	private static String pathLog = "log.csv";

	public static void createPath() {
		try {
			FileReader pathFR = new FileReader(pathFile);
			BufferedReader pathBR = new BufferedReader(pathFR);
			pathSpareFile1 = pathBR.readLine();
			pathSpareFile2 = pathBR.readLine();
			pathBR.close();
		} catch (FileNotFoundException e) {
			try {
				FileWriter pathFW = new FileWriter(pathFile);
				BufferedWriter pathBW = new BufferedWriter(pathFW);
				pathBW.append(pathSpareFile1 + "\n");
				pathBW.append(pathSpareFile2);
				pathBW.flush();
				pathBW.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch(IOException e) {
			
		}	
	}
	
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
			JOptionPane.showMessageDialog(SpareFrame.getFrame(), "Cannot find day 1 spare file", "Error", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(SpareFrame.getFrame(), "Cannot find day 2 spare file", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sortByPeriod(ArrayList<Student> list, int period) {
		for (Student s : students) {
			if (s.hasSpare(period)) {
				list.add(s);
			}
		}
	}

	public static void sortByFirstName(ArrayList<Student> list) {
		Collections.sort(list, new Comparator<Student>() {
			@Override
			public int compare(Student s1, Student s2) {
				return s1.getFirstName().compareTo(s2.getFirstName());
			}
		});
	}

	public static String lastSignIn(Student s) {
		String lastDate = "";
		try {
			FileReader fr = new FileReader(pathLog);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] values;
			while ((line = br.readLine()) != null) {
				values = line.split(",");
				if (values[3].equals(String.valueOf(s.getStudentNumber()))) {
					lastDate = values[4];
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			return lastDate;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastDate;
	}
	
	public static void log(int period, Student s) throws IOException {
		if(new File(pathLog).isFile()) {
			try {
				logFW = new FileWriter(pathLog, true);
				logBW = new BufferedWriter(logFW);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(SpareFrame.getFrame(), "log.csv is currently open", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			try {
				logFW = new FileWriter(pathLog);
				logBW = new BufferedWriter(logFW);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Period " + (char)(period + 65) + ",");
		sb.append(s.getFirstName() + ",");
		sb.append(s.getLastName() + ",");
		sb.append(s.getStudentNumber() + ",");
		sb.append(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a").format(new Date()));
		logBW.append(sb.toString() + "\n");
		logBW.flush();
	}
	
	public static void setPath(String path, int day) {
		if(day == 1) 
			pathSpareFile1 = path;
		if(day == 2)
			pathSpareFile2 = path;
		try {
			FileWriter pathFW = new FileWriter(pathFile);
			BufferedWriter pathBW = new BufferedWriter(pathFW);
			pathBW.append(pathSpareFile1);
			pathBW.newLine();
			pathBW.append(pathSpareFile2);
			pathBW.flush();
			pathBW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
