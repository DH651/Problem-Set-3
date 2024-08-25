package expressivo.practice;

import java.io.File;

public class CurrentDirectory {
    public static void main(String[] args) {
	String workingDir = System.getProperty("user.dir");

	System.out.println("Current working directory: " + workingDir);

	File file = new File("ps3/test/expressivo/test.json");
	if (file.exists()) {
	    System.out.println("File loaded successfully: " + file.getAbsolutePath());
	} else {
	    System.out.println("File not found.");
	}

    }
}