package sparechecker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
					StudentIO.read();
					sortStudents();
					SpareFrame frame = new SpareFrame();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SpareFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 633, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Contains student image
		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(43, 73, 142, 180);
		contentPane.add(imageLabel);
		
		//Shows student's first and last names
		JTextPane nameText = new JTextPane();
		nameText.setEditable(false);
		nameText.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		nameText.setBounds(12, 262, 214, 33);
		//Horizontally Center
		StyledDocument doc = nameText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		//Set transparent
		nameText.setOpaque(false);
		nameText.setBorder(BorderFactory.createEmptyBorder());
		nameText.setBackground(new Color(0,0,0,0));
		contentPane.add(nameText);
		
		//Shows spare periods
		JTextPane spareText = new JTextPane();
		spareText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		spareText.setBounds(47, 286, 142, 33);
		spareText.setEditable(false);
		//Horizontally Center
		doc = spareText.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		//Set transparent
		spareText.setOpaque(false);
		spareText.setBorder(BorderFactory.createEmptyBorder());
		spareText.setBackground(new Color(0,0,0,0));
		contentPane.add(spareText);
		
		//Shows student's student number
		JTextPane stuNumText = new JTextPane();
		stuNumText.setEditable(false);
		stuNumText.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		stuNumText.setBounds(43, 318, 146, 22);
		//Horizontally Center
		doc = stuNumText.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		stuNumText.setOpaque(false);
		stuNumText.setBorder(BorderFactory.createEmptyBorder());
		stuNumText.setBackground(new Color(0,0,0,0));
		contentPane.add(stuNumText);
		
		//Shows last sign in time of the student
		JTextPane signInTime = new JTextPane();
		signInTime.setEditable(false);
		signInTime.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		signInTime.setBounds(12, 353, 214, 40);
		//Horizontally Center
		doc = signInTime.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		signInTime.setOpaque(false);
		signInTime.setBorder(BorderFactory.createEmptyBorder());
		signInTime.setBackground(new Color(0,0,0,0));
		contentPane.add(signInTime);
		
		//Makes list scrollable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(246, 136, 334, 297);
		contentPane.add(scrollPane);
		
		//Shows the active period
		JTextPane periodtxtpn = new JTextPane();
		periodtxtpn.setBackground(Color.LIGHT_GRAY);
		periodtxtpn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		periodtxtpn.setText("Period " + (char)((activePeriod % 8) + 65));
		periodtxtpn.setEditable(false);
		periodtxtpn.setBounds(295, 13, 77, 33);
		contentPane.add(periodtxtpn);
		
		//Shows list of all spare students
		JList<Student> list = new JList<Student>(new DefaultListModel<Student>());
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())
					return;
				if(!list.isSelectionEmpty()) {
					Student s = list.getSelectedValue();
					nameText.setText(s.toString());
					stuNumText.setText(String.valueOf(s.getStudentNumber()));
					String spares = "";
					for(int i = 0; i < 8; i++) {
						if(s.hasSpare(i))
							spares += (char)(i + 65);
					}
					spareText.setText("Period " + spares);
					signInTime.setText("Last Sign In: " + StudentIO.lastSignIn(s));
					try {
						Image image = ImageIO.read(new File("images/" + s.getStudentNumber() + ".BMP"));
						imageLabel.setIcon(new ImageIcon(image));
					} catch (IOException e1) {
						imageLabel.setIcon(null);
					}
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
				
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSignIn.setBounds(64, 402, 108, 31);
		btnSignIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					StudentIO.log(activePeriod, list.getSelectedValue());
					signInTime.setText("Last Sign In: \n" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}	
		});
		contentPane.add(btnSignIn);
		
		//Field to search for student
		JTextField searchField = new JTextField();
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		searchField.setBounds(246, 73, 334, 33);
		searchField.setColumns(10);
		searchField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent e) {
				if(searchField.getText() != null) {
					((DefaultListModel<Student>)list.getModel()).removeAllElements();
					for(Student s : periods.get(activePeriod % 8)) {
						if(s.getFirstName().toLowerCase().contains(searchField.getText().toLowerCase()) 	|| 
								s.getLastName().toLowerCase().contains(searchField.getText().toLowerCase()) ||
								(s.getFirstName() + " " + s.getLastName()).toLowerCase().contains(searchField.getText().toLowerCase())) {
							((DefaultListModel<Student>)list.getModel()).addElement(s);
						}
					}
				}	
			}	
		});
		contentPane.add(searchField);
		
		//Left arrow button that moves the period back by one
		BasicArrowButton leftArrowButton = new BasicArrowButton(BasicArrowButton.WEST);
		leftArrowButton.setBounds(384, 13, 37, 33);
		leftArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Prevent negatives
				if(activePeriod == 0)
					activePeriod += 8;
				activePeriod--;
				periodtxtpn.setText("Period " + (char)((activePeriod % 8) + 65));
				((DefaultListModel<Student>)list.getModel()).removeAllElements();
				for(Student s : periods.get(activePeriod % 8)) {
					((DefaultListModel<Student>)list.getModel()).addElement(s);
				}
			}
		});
		contentPane.add(leftArrowButton);
		
		//Right arrow button that moves the period forward by one
		BasicArrowButton rightArrowButton = new BasicArrowButton(BasicArrowButton.EAST);
		rightArrowButton.setBounds(423, 13, 37, 33);
		rightArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activePeriod++;
				periodtxtpn.setText("Period " + (char)((activePeriod % 8) + 65));
				((DefaultListModel<Student>)list.getModel()).removeAllElements();
				for(Student s : periods.get(activePeriod % 8)) {
					((DefaultListModel<Student>)list.getModel()).addElement(s);
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
			StudentIO.sortByPeriod(periods.get(i), i);
			StudentIO.sortByFirstName(periods.get(i));
		}
	}
}
