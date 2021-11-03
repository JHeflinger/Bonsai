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

}
