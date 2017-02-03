package sparechecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SpareFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	private static String pathSpareFile1 = "data/spare file day 1.csv";
	private static String pathSpareFile2 = "data/spare file day 2.csv";

	private static ArrayList<Student> students;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		 * EventQueue.invokeLater(new Runnable() { public void run() { try {
		 * SpareFrame frame = new SpareFrame(); frame.setVisible(true);
		 * frame.setResizable(false); } catch (Exception e) {
		 * e.printStackTrace(); } } });
		 */

		readCsvFiles();

	}

	/**
	 * Create the frame.
	 */
	public SpareFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(450, 13, 200, 33);
		contentPane.add(textField);
		textField.setColumns(10);
	}

	private static void readCsvFiles() {
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
		for (Student s : students) {
			System.out.println(s);
		}
	}
}
