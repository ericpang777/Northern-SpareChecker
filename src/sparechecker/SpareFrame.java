package sparechecker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;

public class SpareFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	//Lists of students in each period
	private static ArrayList<ArrayList<Student>> periods = new ArrayList<ArrayList<Student>>();
	
	//0 for A, 7 for H
	private static int activePeriod = 0;

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
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		ReadData.read();
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
		textField.setBounds(412, 13, 334, 33);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(412, 111, 334, 604);
		contentPane.add(scrollPane);
		
		JTextPane txtpn = new JTextPane();
		txtpn.setBackground(Color.LIGHT_GRAY);
		txtpn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtpn.setText("Period " + (char)((activePeriod % 8) + 65));
		txtpn.setEditable(false);
		txtpn.setBounds(164, 13, 77, 33);
		contentPane.add(txtpn);
		
		JList<String> list = new JList<String>(new DefaultListModel<String>());
		list.setCellRenderer(new StripeRenderer());
		list.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		scrollPane.setViewportView(list);	
		for(Student s : periods.get(activePeriod)) {
			((DefaultListModel<String>)list.getModel()).addElement(s.getFirstName() +" "+ s.getLastName());
		}
		
		BasicArrowButton leftArrowButton = new BasicArrowButton(BasicArrowButton.WEST);
		leftArrowButton.setBounds(253, 13, 37, 33);
		leftArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Prevent negatives
				if(activePeriod == 0)
					activePeriod += 8;
				activePeriod--;
				txtpn.setText("Period " + (char)(Math.abs((activePeriod % 8)) + 65));
				((DefaultListModel<String>)list.getModel()).removeAllElements();
				for(Student s : periods.get(Math.abs(activePeriod % 8))) {
					((DefaultListModel<String>)list.getModel()).addElement(s.getFirstName() +" "+ s.getLastName());
				}
			}
		});
		contentPane.add(leftArrowButton);
		
		BasicArrowButton rightArrowButton = new BasicArrowButton(BasicArrowButton.EAST);
		rightArrowButton.setBounds(290, 13, 37, 33);
		rightArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activePeriod++;
				txtpn.setText("Period " + (char)(Math.abs((activePeriod % 8)) + 65));
				((DefaultListModel<String>)list.getModel()).removeAllElements();
				for(Student s : periods.get(Math.abs(activePeriod % 8))) {
					((DefaultListModel<String>)list.getModel()).addElement(s.getFirstName() +" "+ s.getLastName());
				}
			}
		});
		contentPane.add(rightArrowButton);
				
		contentPane.setVisible(true);
		
	}
	
	private static void sortStudents() {
		for(int i = 0; i < 8; i++) {
			periods.add(new ArrayList<Student>());
		}
		for(int i = 0; i < periods.size(); i++) {
			ReadData.sortByPeriod(periods.get(i), i);
			ReadData.sortByFirstName(periods.get(i));
		}
	}
}
