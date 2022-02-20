package mainFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Processor {

	public static int getIDFromLine(String line) {
		return Integer.parseInt(line.substring(0, line.indexOf(",")));
	}

	public static String getAssignmentNameFromLine(String line) {
		String name = line.substring(line.indexOf(",") + 1);
		return name.substring(0, name.indexOf(","));
	}

	public static String getClassNameFromLine(String line) {
		String name = line.substring(line.indexOf(",") + 1);
		name = name.substring(name.indexOf(",") + 1);
		return name.substring(0, name.indexOf(","));
	}

	public static int getPriorityFromLine(String line) {
		String name = line.substring(line.indexOf(",") + 1);
		name = name.substring(name.indexOf(",") + 1);
		name = name.substring(name.indexOf(",") + 1);
		return Integer.parseInt(name);
	}

	public static void replaceTask(int id, String newTask) {
		
		// add scanner
		Scanner scanner;
		try {
			scanner = new Scanner(new File("Data/Assignments.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		ArrayList<String> lines = new ArrayList<String>();
		String line = scanner.nextLine();
		lines.add(line);
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (getIDFromLine(line) == id) {
				line = newTask;
			}
			lines.add(line);
		}

		for (int i = 0; i < lines.size(); i++) {
			System.out.println(lines.get(i));
		}
		scanner.close();
		
		// add printer
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("Data/Assignments.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		for(int i = 0; i < lines.size(); i++) {
			pw.println(lines.get(i));
		}
		pw.close();
		
	}

	/**
	 * ensures: moves or deletes a task
	 * @param ID is the task that is being deleted or moved
	 * @param delete is true if task is being deleted, false if is being moved to Completed.txt
	 */
	public static void completeTask(String ID, boolean delete) {
		//get all lines
		Scanner scanner;
		ArrayList<String> lines = new ArrayList<String>();
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
			lines.add(line);
			line = scanner.nextLine();
		}
		lines.add(line);
		scanner.close();
		
		//write all lines except for the ID
		PrintWriter pw = null;
		PrintWriter pwCompleted = null;
		try {
			pw = new PrintWriter("Data/Assignments.txt");
			pwCompleted = new PrintWriter(new FileOutputStream(new File("Data/Completed.txt"), true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		pw.println("ID,Assignment Name,Class Name,Priority");
		for(int i = 0; i < lines.size(); i++) {
			if(!lines.get(i).substring(0, 7).equals(ID)) {
				pw.println(lines.get(i));
			}else {
				System.out.println(lines.get(i));
				if(!delete) {
					System.out.println(lines.get(i));
					pwCompleted.append(lines.get(i));
					pwCompleted.println();
				}
			}
		}
		pw.close();
		pwCompleted.close();
	}
	
}
