package taskFrame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private ArrayList<String> buttons;
	private ArrayList<Integer> buttonIDs;
	private JPanel buttonPanel;
	private JPanel filterPanel;
	private JPanel completePanel;
	private JPanel deletePanel;

	public TaskViewer(int x, int y) {
		buttons = new ArrayList<String>();
		buttonIDs = new ArrayList<Integer>();
		frameSetup(x, y);
		makeDefaultButtonList();
		addFilterButtons();
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

	public void resetViewer() {
		buttonPanel.removeAll();
		completePanel.removeAll();
		buttons = new ArrayList<String>();
		buttonIDs = new ArrayList<Integer>();
		makeDefaultButtonList();
		addButtons();
		buttonPanel.revalidate();
		completePanel.revalidate();
		buttonPanel.repaint();
		completePanel.repaint();
	}

	public void runViewer() {
		taskFrame.setVisible(true);
	}

	private void addFilterButtons() {

		// set up panel for filter buttons
		filterPanel = new JPanel();
		filterPanel.setPreferredSize(new Dimension(200, 100));
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

		// dropdown panel for sorting
		JPanel dropdownPanelOrder = new JPanel();
		JLabel dropdownLabel = new JLabel("Filter by: ");
		dropdownPanelOrder.add(dropdownLabel);
		filterPanel.add(dropdownPanelOrder);
		String[] sortOptions = { "Order Added", "Alphabetical Order", "Priority (low to high)", "Priority (high to low)" }; // also add osrting by classes later
		JComboBox<String> addOrderSelector = new JComboBox<String>(sortOptions);
		addOrderSelector.setMaximumSize(addOrderSelector.getPreferredSize());
		addOrderSelector.setAlignmentX(Component.LEFT_ALIGNMENT);
		dropdownPanelOrder.add(addOrderSelector);
		dropdownPanelOrder.setMaximumSize(dropdownPanelOrder.getPreferredSize());

		// add "regroup" button addNumberSelector.getSelectedItem());
		JButton regroup = new JButton("REFRESH");
		regroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetViewer();
			}
		});
		filterPanel.add(regroup);

		// add "add assignments" button
		JButton addAssignments = new JButton("ADD ASSIGNMENT");
		addAssignments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField name = new JTextField();
				JTextField className = new JTextField(); // turn this into a dropdown menu
				JTextField priority = new JTextField(); // turn this into a dropdown menu or a slider or something
				Object[] options = { "Name of assignment:", name, "Which class is this assignment for?", className,
						"What priority value is this?", priority, };
				name.setText("My New Assignment");
				className.setText("POO101");
				priority.setText(Integer.toString(10));
				int option = JOptionPane.showConfirmDialog(null, options, "Enter your new assignment!",
						JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					try {
						// generate ID and add on to it
						String text = getNewIDNumber() + "," + name.getText() + "," + className.getText() + ","
								+ priority.getText();
						addWriteToAssignments(text);
						resetViewer();
					} catch (Exception excep) {
						JOptionPane.showMessageDialog(null, "ERROR: INVALID INPUT");
					}
				}
			}
		});
		filterPanel.add(addAssignments);

		// add buttons to panel
		taskFrame.add(filterPanel, "North");

	}

	private void addButtons() {

		// set up panel for buttons
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, 600));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		completePanel = new JPanel();
		completePanel.setPreferredSize(new Dimension(200, 600));
		completePanel.setLayout(new BoxLayout(completePanel, BoxLayout.Y_AXIS));
		
		if(buttons.size() <= 0) {
			System.out.println("poo");
			taskFrame.add(buttonPanel, "West");
			taskFrame.add(completePanel, "Center");
			return;
		}
		
		// add "task" buttons
		for (int i = 0; i < buttons.size(); i++) {
			JButton tempButton = new JButton(Processor.getAssignmentNameFromLine(buttons.get(i)));
			TaskButton taskButton = new TaskButton(buttons.get(i), this);
			tempButton.addActionListener(taskButton);
			buttonIDs.add(Integer.parseInt(buttons.get(i).substring(0, 7)));
			buttonPanel.add(tempButton);
		}

		// add buttons to panel
		taskFrame.add(buttonPanel, "West");

		// add "complete task" buttons
		for (int i = 0; i < buttons.size(); i++) {
			JButton tempButton = new JButton("COMPLETE TASK");
			CompleteListener tempListener = new CompleteListener(buttons.get(i).substring(0, 7));
			tempButton.addActionListener(tempListener);
			completePanel.add(tempButton);
		}

		// add buttons to panel
		taskFrame.add(completePanel, "Center");

	}

	private void makeDefaultButtonList() {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("Data/Assignments.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		String line = scanner.nextLine();
		if (scanner.hasNextLine()) {
			line = scanner.nextLine();
		}else {
			buttons = new ArrayList<String>();
			scanner.close();
			return;
		}
		while (scanner.hasNextLine()) {
			buttons.add(line);
			line = scanner.nextLine();
		}
		buttons.add(line);
		scanner.close();
	}

	private void addWriteToAssignments(String text) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File("Data/Assignments.txt"), true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		pw.append(text);
		pw.println();
		pw.close();
	}

	private int getNewIDNumber() {
		int id = (int) ((Math.random() * 8999999) + 1000000);
		if (!checkIfIDExists(id)) {
			return id;
		} else {
			return getNewIDNumber();
		}
	}

	// test if this works eventually lol
	private boolean checkIfIDExists(int id) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File("Data/Assignments.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		String line = scanner.nextLine();
		if (scanner.hasNextLine()) {
			line = scanner.nextLine();
		}
		while (scanner.hasNextLine()) {
			try {
				if (Integer.parseInt(line.substring(0, line.indexOf(","))) == id) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
			line = scanner.nextLine();
		}
		scanner.close();
		return false;
	}

	private class CompleteListener implements ActionListener{

		private String ID;
		
		public CompleteListener(String ID) {
			this.ID = ID;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Processor.completeTask(ID, false);
			resetViewer();
		}
		
	}
	
}
