package expressivo.practice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

public class JsonReader {
    public static void main(String[] args) {
	// Relative path to your JSON file
	String filePath = "ps3/test/expressivo/test.json";

	try {
	    // Create ObjectMapper instance
	    ObjectMapper objectMapper = new ObjectMapper();

	    // Read JSON file into a JsonNode
	    JsonNode rootNode = objectMapper.readTree(new File(filePath));

	    // Iterate over the array of test cases
	    if (rootNode.isArray()) {
		for (JsonNode testNode : rootNode) {
		    String expression = testNode.get("expression").asText();
		    String variable = testNode.get("variable").asText();
		    String expected = testNode.get("expected").asText();

		    // Output the fields (you can replace this with your actual test logic)
		    System.out.println("Expression: " + expression);
		    System.out.println("Variable: " + variable);
		    System.out.println("Expected: " + expected);
		    System.out.println();
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
