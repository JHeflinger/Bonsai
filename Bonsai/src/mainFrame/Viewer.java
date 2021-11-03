package mainFrame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import taskFrame.TaskViewer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Viewer {

	public static final int DELAY = 10;
	public static final double VERSION = 0.01;

	private JFrame mainFrame; // the main frame game is viewed in
	private TaskViewer taskViewer;
	private Central central;
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGHT = 600;

	public Viewer() {
		frameSetup();
		addButtons();
	}

	private void frameSetup() {

		// basic setup
		mainFrame = new JFrame();
		mainFrame.setTitle("Bonsai v" + VERSION + " - MAINFRAME");
		mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		mainFrame.setLocation(100, 100);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add central controller
		central = new Central();
		mainFrame.add(central);

		// add timer
		Timer timer = new Timer(DELAY, central);
		timer.start();
	}

	public void runViewer() {
		mainFrame.setVisible(true);
	}

	private void addButtons() {

		// set up panel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(Color.BLACK);

		// add "view tasks" button
		JButton viewTasks = new JButton("VIEW TASKS");
		viewTasks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				taskViewer = new TaskViewer(mainFrame.getLocation().x + FRAME_WIDTH + 50, 
											mainFrame.getLocation().y);
				taskViewer.runViewer();
			}
		});
		buttonPanel.add(viewTasks);
		
		// add "add assignments" button
		JButton addAssignments = new JButton("ADD ASSIGNMENT");
		addAssignments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField name = new JTextField();
				JTextField className = new JTextField(); //turn this into a dropdown menu
				JTextField priority = new JTextField(); //turn this into a dropdown menu or a slider or something
				Object[] options = { "Name of assignment:", name,
						"Which class is this assignment for?", className,
						"What priority is this? (the lower the more important)", priority,
						};
				name.setText("My New Assignment");
				className.setText("POO101");
				priority.setText(Integer.toString(10));
				int option = JOptionPane.showConfirmDialog(null, options, "Enter your new assignment!",
						JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					try {
						//generate ID and add on to it
						String text = getNewIDNumber()
									  + "," + name.getText() + "," 
									  + className.getText() + "," 
									  + priority.getText();
						addWriteToAssignments(text);
					} catch (Exception excep) {
						JOptionPane.showMessageDialog(null,
								"ERROR: INVALID INPUT");
					}
				}
			}
		});
		buttonPanel.add(addAssignments);

		// add buttons to frame
		mainFrame.add(buttonPanel);
	}

	private void overwriteToAssignments(String text) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("Data/Assignments.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		pw.println(text);
		pw.close();
	}
	
	private void addWriteToAssignments(String text) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File("Data/Assignments.txt"),true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		pw.println();
		pw.append(text);
		pw.close();
	}
	
	private int getNewIDNumber() {
		int id = (int)((Math.random() * 8999999) + 1000000);
		if(!checkIfIDExists(id)) {
			return id;
		}else {
			return getNewIDNumber();
		}
	}
	
	//test if this works eventually lol
	private boolean checkIfIDExists(int id) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("Data/Assignments.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		String line = scanner.nextLine();
		if(scanner.hasNextLine()) {
			line = scanner.nextLine();
		}
		while (scanner.hasNextLine()) {
			try {
				if(Integer.parseInt(line.substring(0,line.indexOf(","))) == id) {
					return true;
				}
			}catch(Exception e) {
				return false;
			}
			line = scanner.nextLine();
		}
		scanner.close();
		return false;
	}
	
}
