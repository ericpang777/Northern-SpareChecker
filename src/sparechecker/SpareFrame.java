package sparechecker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
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
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class SpareFrame extends JFrame {
	private JPanel contentPane;
	private JButton btnSignIn;
	private static JFrame spareFrame;
	private JList<Student> list; 
	
	//Lists of students in each period
	private static ArrayList<ArrayList<Student>> periods = new ArrayList<ArrayList<Student>>();
	
	//0 for A, 7 for H
	private static int activePeriod = 0;
	private static int truePeriod = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		StudentIO.createPath();
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
		spareFrame = this;
		this.setTitle("Spare Checker");
		
		//Menu bar and items
		JMenuBar menu = new JMenuBar();
		JMenu edit = new JMenu("Edit");
		edit.setFont(edit.getFont().deriveFont(Font.PLAIN, 14));
		JMenuItem file1 = new JMenuItem("Edit Spare File Day 1");
		file1.setFont(file1.getFont().deriveFont(Font.PLAIN, 14));
		file1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(spareFrame, "Choose a day 1 file", FileDialog.LOAD);
				fd.setFile("*.csv");
				fd.setVisible(true);
				String pathName = fd.getDirectory() + fd.getFile();
				if(fd.getFile() != null) {
					StudentIO.setPath(pathName, 1);
					StudentIO.read();
					sortStudents();
					((DefaultListModel<Student>)list.getModel()).removeAllElements();
					for(Student s : periods.get(truePeriod)) {
						((DefaultListModel<Student>)list.getModel()).addElement(s);
					}
				}
			}
		});
		JMenuItem file2 = new JMenuItem("Edit Spare File Day 2");
		file2.setFont(file2.getFont().deriveFont(Font.PLAIN, 14));
		file2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(spareFrame, "Choose a day 2 file", FileDialog.LOAD);
				fd.setFile("*.csv");
				fd.setVisible(true);
				String pathName = fd.getDirectory() + fd.getFile();
				if(fd.getFile() != null) {
					StudentIO.setPath(pathName, 2);
					StudentIO.read();
					sortStudents();
					((DefaultListModel<Student>)list.getModel()).removeAllElements();
					for(Student s : periods.get(truePeriod)) {
						((DefaultListModel<Student>)list.getModel()).addElement(s);
					}
				}
			}
		});
		edit.add(file1);
		edit.add(file2);
		menu.add(edit);
		setJMenuBar(menu);
		
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
		//Set transparent
		stuNumText.setOpaque(false);
		stuNumText.setBorder(BorderFactory.createEmptyBorder());
		stuNumText.setBackground(new Color(0,0,0,0));
		contentPane.add(stuNumText);
		
		//Shows last sign in time of the student
		JTextPane signInTime = new JTextPane();
		signInTime.setEditable(false);
		signInTime.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		signInTime.setBounds(12, 353, 214, 57);
		//Horizontally Center
		doc = signInTime.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		//Set transparent
		signInTime.setOpaque(false);
		signInTime.setBorder(BorderFactory.createEmptyBorder());
		signInTime.setBackground(new Color(0,0,0,0));
		contentPane.add(signInTime);
		
		//Makes list scrollable
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(246, 136, 334, 324);
		contentPane.add(scrollPane);
		
		//Shows the active period
		JTextPane periodtxtpn = new JTextPane();
		periodtxtpn.setBackground(Color.LIGHT_GRAY);
		periodtxtpn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		periodtxtpn.setText("Day 1 Period 1");
		periodtxtpn.setEditable(false);
		periodtxtpn.setBounds(297, 13, 123, 33);
		contentPane.add(periodtxtpn);
		
		//Shows list of all spare students
		list = new JList<Student>(new DefaultListModel<Student>());
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting())
					return;
				if(!list.isSelectionEmpty()) {
					btnSignIn.setVisible(true);
					Student s = list.getSelectedValue();
					nameText.setText(s.toString());
					stuNumText.setText(String.valueOf(s.getStudentNumber()));
					String spares = "";
					for(int i = 0; i < 8; i++) {
						if(s.hasSpare(i))
							spares += (char)(i + 65);
					}
					spareText.setText("Period " + spares);
					signInTime.setText("Last Sign In: \n" + StudentIO.lastSignIn(s));
					try {
						Image image = ImageIO.read(new File("images/" + s.getStudentNumber() + ".BMP"));
						imageLabel.setIcon(new ImageIcon(image));
					} catch (IOException e1) {
						try {
							imageLabel.setIcon(new ImageIcon(ImageIO.read(new File("images/default.png"))));
						} catch (IOException e2) {
							e2.printStackTrace();
						}
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
		for(Student s : periods.get(truePeriod)) {
			((DefaultListModel<Student>)list.getModel()).addElement(s);
		}
			
		//Sign in button that has the space hotkey
		btnSignIn = new JButton("Sign In (SPACE)");
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSignIn.setBounds(43, 413, 146, 31);
		btnSignIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					StudentIO.log(activePeriod, list.getSelectedValue());
					signInTime.setText("Last Sign In: \n" + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a").format(new Date()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}	
		});
		Action action = new AbstractAction("sign in") {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSignIn.doClick();
			}
		};
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke((char)KeyEvent.VK_SPACE));
		btnSignIn.getActionMap().put("sign in", action);
		btnSignIn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put((KeyStroke)action.getValue(Action.ACCELERATOR_KEY), "sign in");
		contentPane.add(btnSignIn);
		btnSignIn.setVisible(false);
		
		//Field to search for student
		JTextField searchField = new JTextField();
		searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		searchField.setBounds(246, 73, 334, 33);
		searchField.setColumns(10);
		searchField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {	}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if(searchField.getText() != null) {
					btnSignIn.setVisible(false);
					((DefaultListModel<Student>)list.getModel()).removeAllElements();
					for(Student s : periods.get(truePeriod)) {
						if(s.getFirstName().toLowerCase().contains(searchField.getText().toLowerCase()) 	|| 
								s.getLastName().toLowerCase().contains(searchField.getText().toLowerCase()) ||
								(s.getFirstName() + " " + s.getLastName()).toLowerCase().contains(searchField.getText().toLowerCase())) {
							((DefaultListModel<Student>)list.getModel()).addElement(s);
						}
					}
				}	
			}

			@Override
			public void keyTyped(KeyEvent e) { }	
		});
		contentPane.add(searchField);
		
		//Left arrow button that moves the period back by one
		BasicArrowButton leftArrowButton = new BasicArrowButton(BasicArrowButton.WEST);
		leftArrowButton.setBounds(432, 13, 37, 33);
		leftArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Prevent negatives
				if(activePeriod == 0)
					activePeriod += 16;
				activePeriod--;
				int day = (activePeriod / 4) + 1;
				periodtxtpn.setText("Day " + day + " Period " + (activePeriod % 4 + 1));
				adjustTruePeriod();
				((DefaultListModel<Student>)list.getModel()).removeAllElements();
				for(Student s : periods.get(truePeriod)) {
					((DefaultListModel<Student>)list.getModel()).addElement(s);
				}
				btnSignIn.setVisible(false);
			}
		});
		contentPane.add(leftArrowButton);
		
		//Right arrow button that moves the period forward by one
		BasicArrowButton rightArrowButton = new BasicArrowButton(BasicArrowButton.EAST);
		rightArrowButton.setBounds(468, 13, 37, 33);
		rightArrowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Don't go over 15
				if(activePeriod == 15)
					activePeriod -= 16;
				activePeriod++;
				int day = (activePeriod / 4) + 1;
				periodtxtpn.setText("Day " + day + " Period " + (activePeriod % 4 + 1));
				adjustTruePeriod();
				((DefaultListModel<Student>)list.getModel()).removeAllElements();
				for(Student s : periods.get(truePeriod)) {
					((DefaultListModel<Student>)list.getModel()).addElement(s);
				}
				btnSignIn.setVisible(false);
			}
		});
		contentPane.add(rightArrowButton);	
	}

	/**
	 * Sorts students into the 2d arraylist of size 8. For the 8 periods
	 */
	private static void sortStudents() {
		periods.clear();
		for(int i = 0; i < 8; i++) {
			periods.add(new ArrayList<Student>());
		}
		for(int i = 0; i < periods.size(); i++) {
			StudentIO.sortByPeriod(periods.get(i), i);
			StudentIO.sortByFirstName(periods.get(i));
		}
	}
	
	/**
	 * Gets JFrame of app
	 * @return spareFrame
	 */
	public static JFrame getFrame() {
		return spareFrame;
	}
	
	/**
	 * Changes the true period(used for StudentIO calculations) based on the current "active" period seen in app.
	 */
	private void adjustTruePeriod() {
		if(activePeriod > 7) {
			truePeriod = activePeriod - 8;
			switch(activePeriod) {
				case 10:
					truePeriod = 3;
					break;
				case 11:
					truePeriod = 2;
					break;
				case 14:
					truePeriod = 7;
					break;
				case 15:
					truePeriod = 6;
					break;
			}
		} else {
			truePeriod = activePeriod;
		}
	}
}
