package taskFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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
	private JPanel buttonPanel;

	public TaskViewer(int x, int y) {
		buttons = new ArrayList<String>();
		frameSetup(x, y);
		makeDefaultButtonList();
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
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, 600));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(Color.BLACK);

		// dropdown panel for sorting
		JPanel dropdownPanelOrder = new JPanel();
		dropdownPanelOrder.setBackground(Color.RED);
		JLabel dropdownLabel = new JLabel("Sort by: ");
		dropdownPanelOrder.add(dropdownLabel);
		buttonPanel.add(dropdownPanelOrder);
		String[] sortOptions = { "Order Added", "Alphabetical Order", "Priority" };
		JComboBox<String> addOrderSelector = new JComboBox<String>(sortOptions);
		addOrderSelector.setMaximumSize(addOrderSelector.getPreferredSize());
		addOrderSelector.setAlignmentX(Component.LEFT_ALIGNMENT);
		dropdownPanelOrder.add(addOrderSelector);
		dropdownPanelOrder.setMaximumSize(dropdownPanelOrder.getPreferredSize());

		// dropdown panel for grouping
		JPanel dropdownPanelGroup = new JPanel();
		dropdownPanelGroup.setBackground(Color.RED);
		JLabel dropdownLabel2 = new JLabel("Group by: ");
		dropdownPanelGroup.add(dropdownLabel2);
		buttonPanel.add(dropdownPanelGroup);
		String[] groupOptions = { "Class", "Priority" };
		JComboBox<String> addGroupSelector = new JComboBox<String>(groupOptions);
		addGroupSelector.setMaximumSize(addGroupSelector.getPreferredSize());
		addGroupSelector.setAlignmentX(Component.LEFT_ALIGNMENT);
		dropdownPanelGroup.add(addGroupSelector);
		dropdownPanelGroup.setMaximumSize(dropdownPanelGroup.getPreferredSize());

		// add "regroup" button addNumberSelector.getSelectedItem());
		JButton regroup = new JButton("SORT");
		regroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//reSortButtons((String)(addOrderSelector.getSelectedItem()), (String)(addGroupSelector.getSelectedItem()));
			}
		});
		buttonPanel.add(regroup);

		// add "task" buttons
		for(int i = 0; i < buttons.size(); i++) {
			JButton tempButton = new JButton(Processor.getAssignmentNameFromLine(buttons.get(i)));
			TaskButton taskButton = new TaskButton(buttons.get(i));
			tempButton.addActionListener(taskButton);
			buttonPanel.add(tempButton);
		}
		
		// add buttons to panel
		taskFrame.add(buttonPanel);
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
		}
		while (scanner.hasNextLine()) {
			buttons.add(line);
			line = scanner.nextLine();
		}
		buttons.add(line);
		scanner.close();
	}
	
	private void reSortButtons(String group, String order) {
		System.out.println(group + ", " + order);
	}
	
}
