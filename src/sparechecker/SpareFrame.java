package sparechecker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SpareFrame extends JFrame {

	private JPanel contentPane;

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

		//Field to search for student
		JTextField searchField = new JTextField();
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		searchField.setBounds(647, 75, 214, 33);
		searchField.setColumns(10);
		searchField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(searchField.getText() != null) {
					
				}
			}
			
		});
		contentPane.add(searchField);
		
		JTextPane nameText = new JTextPane();
		nameText.setEditable(false);
		nameText.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		nameText.setBounds(35, 580, 214, 33);
		//Horizontally Center
		StyledDocument doc = nameText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		contentPane.add(nameText);
		
		JTextPane stuNumText = new JTextPane();
		stuNumText.setEditable(false);
		stuNumText.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		stuNumText.setBounds(67, 615, 146, 22);
		//Horizontally Center
		doc = stuNumText.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		contentPane.add(stuNumText);
		
		JTextPane signInTime = new JTextPane();
		signInTime.setBounds(12, 650, 263, 40);
		contentPane.add(signInTime);
		
		//Makes list scrollable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(647, 136, 334, 604);
		contentPane.add(scrollPane);
		
		//Shows the active period
		JTextPane periodtxtpn = new JTextPane();
		periodtxtpn.setBackground(Color.LIGHT_GRAY);
		periodtxtpn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		periodtxtpn.setText("Period " + (char)((activePeriod % 8) + 65));
		periodtxtpn.setEditable(false);
		periodtxtpn.setBounds(718, 13, 77, 33);
		contentPane.add(periodtxtpn);
		
		//Shows list of all spare students
		JList<Student> list = new JList<Student>(new DefaultListModel<Student>());
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())
					return;
				if(!list.isSelectionEmpty()) {
					nameText.setText(list.getSelectedValue().toString());
					stuNumText.setText(String.valueOf(list.getSelectedValue().getStudentNumber()));
					repaint();
				}	
				else
					repaint();
			}
		});
		list.setCellRenderer(new StripeRenderer());
		list.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		scrollPane.setViewportView(list);	
		for(Student s : periods.get(activePeriod)) {
			((DefaultListModel<Student>)list.getModel()).addElement(s);
		}
		
		//Left arrow button that moves the period back by one
		BasicArrowButton leftArrowButton = new BasicArrowButton(BasicArrowButton.WEST);
		leftArrowButton.setBounds(802, 13, 37, 33);
		leftArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Prevent negatives
				if(activePeriod == 0)
					activePeriod += 8;
				activePeriod--;
				periodtxtpn.setText("Period " + (char)(Math.abs((activePeriod % 8)) + 65));
				((DefaultListModel<Student>)list.getModel()).removeAllElements();
				for(Student s : periods.get(Math.abs(activePeriod % 8))) {
					((DefaultListModel<Student>)list.getModel()).addElement(s);
				}
			}
		});
		contentPane.add(leftArrowButton);
		
		//Right arrow button that moves the period forward by one
		BasicArrowButton rightArrowButton = new BasicArrowButton(BasicArrowButton.EAST);
		rightArrowButton.setBounds(837, 13, 37, 33);
		rightArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activePeriod++;
				periodtxtpn.setText("Period " + (char)(Math.abs((activePeriod % 8)) + 65));
				((DefaultListModel<Student>)list.getModel()).removeAllElements();
				for(Student s : periods.get(Math.abs(activePeriod % 8))) {
					((DefaultListModel<Student>)list.getModel()).addElement(s);
				}
			}
		});
		contentPane.add(rightArrowButton);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSignIn.setBounds(873, 75, 108, 31);
		contentPane.add(btnSignIn);
		
		
				
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
