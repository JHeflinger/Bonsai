package taskFrame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import mainFrame.Central;
import mainFrame.Processor;

public class TaskViewer {

	public static final int DELAY = 10;
	public static final double VERSION = 0.01;

	private JFrame taskFrame; // the main frame game is viewed in
	private Central central;
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGHT = 600;

	public TaskViewer(int x, int y) {
		frameSetup(x, y);
		addButtons();
		this.central = new Central();
	}

	private void frameSetup(int x, int y) {

		// basic setup
		taskFrame = new JFrame();
		taskFrame.setTitle("Bonsai v" + VERSION + " - TASKFRAME");
		taskFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		taskFrame.setLocation(x, y);

		// add timer
		Timer timer = new Timer(DELAY, central);
		timer.start();

	}

	public void runViewer() {
		taskFrame.setVisible(true);
	}

	private void addButtons() {
		
		// set up panel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(Color.BLACK);
		
		// add "task" buttons
		Scanner scanner;
		try {
			scanner = new Scanner(new File("Data/Assignments.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		String line = scanner.nextLine();
		if(scanner.hasNextLine()) {
			line = scanner.nextLine();
		}
		while (scanner.hasNextLine()) {
			JButton tempButton = new JButton(Processor.getAssignmentNameFromLine(line));
			TaskButton taskButton = new TaskButton(line);
			tempButton.addActionListener(taskButton);
			buttonPanel.add(tempButton);
			line = scanner.nextLine();
		}
		scanner.close();
		
		// add buttons to panel
		taskFrame.add(buttonPanel);
		
	}

	
	
}
