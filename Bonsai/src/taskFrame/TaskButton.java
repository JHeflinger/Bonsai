package taskFrame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mainFrame.Processor;

public class TaskButton implements ActionListener {

	private String line;
	private TaskViewer taskViewer;
	
	public TaskButton(String line, TaskViewer taskViewer) {
		this.line = line;
		this.taskViewer = taskViewer;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JTextField name = new JTextField();
		JTextField className = new JTextField(); //turn this into a dropdown menu
		JTextField priority = new JTextField(); //turn this into a dropdown menu or a slider or something
		Object[] options = { "Edit name of assignment:", name,
				"Edit class this assignment is for:", className,
				"Edit priority level", priority,
				};
		name.setText(Processor.getAssignmentNameFromLine(line));
		className.setText(Processor.getClassNameFromLine(line));
		priority.setText(Integer.toString(Processor.getPriorityFromLine(line)));
		int option = JOptionPane.showConfirmDialog(null, options, "Enter your new assignment!",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			try {
				//generate ID and add on to it
				String text = Processor.getIDFromLine(line)
							  + "," + name.getText() + "," 
							  + className.getText() + "," 
							  + priority.getText();
				Processor.replaceTask(Processor.getIDFromLine(line), text);
				taskViewer.resetViewer();
			} catch (Exception excep) {
				JOptionPane.showMessageDialog(null,
						"ERROR: INVALID INPUT");
			}
		}
	}

}
