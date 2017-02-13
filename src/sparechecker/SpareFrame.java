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
import java.util.ArrayList;

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

	private static final int IMG_WIDTH = 142;
	private static final int IMG_HEIGHT = 180;

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
		setBounds(100, 100, 633, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSignIn.setBounds(472, 75, 108, 31);
		contentPane.add(btnSignIn);
		
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
		nameText.setOpaque(false);
		nameText.setBorder(BorderFactory.createEmptyBorder());
		nameText.setBackground(new Color(0,0,0,0));
		contentPane.add(nameText);
		
		//Shows student's student number
		JTextPane stuNumText = new JTextPane();
		stuNumText.setEditable(false);
		stuNumText.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		stuNumText.setBounds(39, 286, 146, 22);
		//Horizontally Center
		doc = stuNumText.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		stuNumText.setOpaque(false);
		stuNumText.setBorder(BorderFactory.createEmptyBorder());
		stuNumText.setBackground(new Color(0,0,0,0));
		contentPane.add(stuNumText);
		
		JTextPane signInTime = new JTextPane();
		signInTime.setBounds(22, 308, 190, 40);
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
					nameText.setText(list.getSelectedValue().toString());
					stuNumText.setText(String.valueOf(list.getSelectedValue().getStudentNumber()));
					try {
						Image image = ImageIO.read(new File("images/" + list.getSelectedValue().getStudentNumber() + ".BMP"));
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
		
		//Field to search for student
		JTextField searchField = new JTextField();
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		searchField.setBounds(246, 73, 214, 33);
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
			ReadData.sortByPeriod(periods.get(i), i);
			ReadData.sortByFirstName(periods.get(i));
		}
	}
}
