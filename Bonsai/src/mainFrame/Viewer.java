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
				taskViewer = new TaskViewer(mainFrame.getLocation().x + FRAME_WIDTH + 50, mainFrame.getLocation().y);
				taskViewer.runViewer();
			}
		});
		buttonPanel.add(viewTasks);

		// add "reset" button
		JButton reset = new JButton("RESET");
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(
			            null,
			            "WARNING!!! THIS WILL RESET ALL\n"
			            + "WRITTEN DATA BEYOND RECOVERY!!\n"
			            + "DO YOU WISH TO STILL CONTINUE?",
			            "",
			            JOptionPane.YES_NO_OPTION);
				switch(n) {
				case JOptionPane.YES_OPTION:
			         overwriteToAssignments("ID,Assignment Name,Class Name,Priority");
			         break;
			         case JOptionPane.NO_OPTION:
			         break;
				}
			}
		});
		buttonPanel.add(reset);

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

}
