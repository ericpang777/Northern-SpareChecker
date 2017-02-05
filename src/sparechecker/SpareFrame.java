package sparechecker;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SpareFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	private static ArrayList<Student> students;
	private static ArrayList<Student> periodA;
	private static ArrayList<Student> periodB;
	private static ArrayList<Student> periodC;
	private static ArrayList<Student> periodD;
	private static ArrayList<Student> periodE;
	private static ArrayList<Student> periodF;
	private static ArrayList<Student> periodG;
	private static ArrayList<Student> periodH;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpareFrame frame = new SpareFrame();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		students = ReadData.read();
		sortStudents();
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
		textField.setBounds(284, 13, 200, 33);
		contentPane.add(textField);
		textField.setColumns(10);
		
		String[] col = {"First Name", "Last Name", "Student Number"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);

		table = new JTable(tableModel);
		table.setBounds(61, 116, 676, 624);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);
		add(scrollPane);
		contentPane.add(table);
		
		for(Student s : students) {
			Object[] data = {s.getFirstName(), s.getLastName(), s.getStudentNumber()};
			tableModel.addRow(data);
		}
	}
	
	private static void sortStudents() {
		ReadData.sortByPeriod(periodA = new ArrayList<>(), 0);
		ReadData.sortByPeriod(periodB = new ArrayList<>(), 1);
		ReadData.sortByPeriod(periodC = new ArrayList<>(), 2);
		ReadData.sortByPeriod(periodD = new ArrayList<>(), 3);
		ReadData.sortByPeriod(periodE = new ArrayList<>(), 4);
		ReadData.sortByPeriod(periodF = new ArrayList<>(), 5);
		ReadData.sortByPeriod(periodG = new ArrayList<>(), 6);
		ReadData.sortByPeriod(periodH = new ArrayList<>(), 7);
	}
}
