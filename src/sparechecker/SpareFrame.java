package sparechecker;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SpareFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpareFrame frame = new SpareFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		String pathSpareFile1 = "data/spare file day 1.csv"; 
		String pathSpareFile2 = "data/spare file day 2.csv"; 
		ArrayList<Student> day1Per1= new ArrayList<>();
		ArrayList<Student> day1Per2= new ArrayList<>();
		ArrayList<Student> day1Per3= new ArrayList<>();
		ArrayList<Student> day1Per4= new ArrayList<>();
		ArrayList<Student> day2Per1= new ArrayList<>();
		ArrayList<Student> day2Per2= new ArrayList<>();
		ArrayList<Student> day2Per3= new ArrayList<>();
		ArrayList<Student> day2Per4= new ArrayList<>();
		
		try { 
			FileReader fr = new FileReader(pathSpareFile1);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] values;
			br.readLine(); //Skip header
			while((line = br.readLine()) != null) {
				values = line.split(",");
				//Sort into different periods
				switch(Integer.parseInt(values[0])) {
					case 1:
						day1Per1.add(new Student(1, 1, values[1], values[2], Integer.parseInt(values[3])));
						break;
					case 2:
						day1Per2.add(new Student(1, 2, values[1], values[2], Integer.parseInt(values[3])));
						break;
					case 3:
						day1Per3.add(new Student(1, 3, values[1], values[2], Integer.parseInt(values[3])));
						break;
					case 4:
						day1Per4.add(new Student(1, 4, values[1], values[2], Integer.parseInt(values[3])));
						break;
				}			
			}
			br.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("spare file day 1.csv not found");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		try { 
			FileReader fr = new FileReader(pathSpareFile2);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] values;
			br.readLine(); //Skip header
			while((line = br.readLine()) != null) {
				values = line.split(",");
				//Sort into different periods
				switch(Integer.parseInt(values[0])) {
					case 1:
						day2Per1.add(new Student(2, 1, values[1], values[2], Integer.parseInt(values[3])));
						break;
					case 2:
						day2Per2.add(new Student(2, 2, values[1], values[2], Integer.parseInt(values[3])));
						break;
					case 3:
						day2Per3.add(new Student(2, 3, values[1], values[2], Integer.parseInt(values[3])));
						break;
					case 4:
						day2Per4.add(new Student(2, 4, values[1], values[2], Integer.parseInt(values[3])));
						break;
				}
			}
			br.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("spare file day 2.csv not found");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		for(Student s : day1Per1) {
			System.out.println(s);
		}
		for(Student s : day1Per2) {
			System.out.println(s);
		}
		for(Student s : day1Per3) {
			System.out.println(s);
		}
		for(Student s : day1Per4) {
			System.out.println(s);
		}
		for(Student s : day2Per1) {
			System.out.println(s);
		}
		for(Student s : day2Per2) {
			System.out.println(s);
		}
		for(Student s : day2Per3) {
			System.out.println(s);
		}
		for(Student s : day2Per4) {
			System.out.println(s);
		}
	}

	/**
	 * Create the frame.
	 */
	public SpareFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
