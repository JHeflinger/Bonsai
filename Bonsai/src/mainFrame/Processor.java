package mainFrame;

public class Processor {

	public static String getAssignmentNameFromLine(String line) {
		String name = line.substring(line.indexOf(",") + 1);
		return name.substring(0, name.indexOf(","));
	}
	
}
