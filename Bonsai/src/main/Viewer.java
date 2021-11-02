package main;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Viewer {

	public static final int DELAY = 10;
	public static final double VERSION = 0.01;

	private JFrame mainFrame; // the main frame game is viewed in
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
		mainFrame.setTitle("Bonsai v" + VERSION);
		mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		mainFrame.setLocation(200, 200);
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
//						Person.BASE_INFECTED = Integer.parseInt(baseInfected.getText());
//						Person.INFECTION_RATE = Integer.parseInt(infectionRate.getText());
//						Person.TIME_TO_RECOVER = Integer.parseInt(recoveryRate.getText());
//						Person.NUMBER_OF_STRAINS = Integer.parseInt(numberOfStrains.getText());
//						Person.MUTATION_RATE = Integer.parseInt(mutationRate.getText());
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

}
