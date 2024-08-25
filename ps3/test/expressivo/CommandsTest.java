/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Tests for the static methods of Commands.
 */
public class CommandsTest {

    // Testing strategy
    // same as ExpressionTest

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
	assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // TODO tests for Commands.differentiate() and Commands.simplify()
    @Test
    public void testDerivativeSimple() {
	String filePath = "ps3/test/expressivo/test-data/derivative1.json";
	testDerivativeHelper(filePath);
    }

    @Test
    public void testDerivativeComplex() {
	String filePath = "ps3/test/expressivo/test-data/derivative2.json";
	testDerivativeHelper(filePath);
    }

    @Test
    public void testSimplifySimple() {
	String filePath = "ps3/test/expressivo/test-data/simplify1.json";
	testSimplifyHelper(filePath);
    }

    @Test
    public void testSimplifyComplex() {
	String filePath = "ps3/test/expressivo/test-data/simplify2.json";
	testSimplifyHelper(filePath);
    }

    private void testDerivativeHelper(String filePath) {
	try {
	    // Create a ObjectMapper Instance
	    ObjectMapper mapper = new ObjectMapper();

	    // Read JSON file into a JsonNode
	    JsonNode rootNode = mapper.readTree(new File(filePath));

	    if (rootNode.isArray()) {
		for (JsonNode testNode : rootNode) {
		    String expression = testNode.get("input").asText();
		    String variable = testNode.get("variable").asText();
		    String expectedOutput = testNode.get("expectedOutput").asText();

		    String returnedOutput = Commands.differentiate(expression, variable);

		    CompareThisWithThat(returnedOutput, expectedOutput);

		    // Print the results
		    System.out.println("Input: " + expression);
		    System.out.println("Variable: " + variable);
		    System.out.println("Actual Output: " + returnedOutput);
		    System.out.println("Expected Output: " + expectedOutput);
		    System.out.println("----");
		}
	    }

	} catch (IOException e) {
	    // Handle the exception (e.g., fail the test with a message)
	    fail("Failed to read JSON file: " + e.getMessage());

	}

    }

    private void testSimplifyHelper(String filePath) {
	try {
	    // Create a ObjectMapper Instance
	    ObjectMapper mapper = new ObjectMapper();

	    // Read JSON file into a JsonNode
	    JsonNode rootNode = mapper.readTree(new File(filePath));

	    if (rootNode.isArray()) {
		for (JsonNode testNode : rootNode) {
		    String expression = testNode.get("input").asText();

		    JsonNode mappingNode = testNode.get("mapping");
		    Map<String, Double> mapping = mapper.convertValue(mappingNode,
			    new TypeReference<Map<String, Double>>() {
			    });

		    String expectedOutput = testNode.get("expectedOutput").asText();

		    String returnedOutput = Commands.simplify(expression, mapping);

		    CompareThisWithThat(returnedOutput, expectedOutput);

		    // Print the results
		    System.out.println("Input: " + expression);
		    System.out.println("Mapping: " + mapping);
		    System.out.println("Actual Output: " + returnedOutput);
		    System.out.println("Expected Output: " + expectedOutput);
		    System.out.println("----");
		}
	    }

	} catch (IOException e) {
	    // Handle the exception (e.g., fail the test with a message)
	    fail("Failed to read JSON file: " + e.getMessage());

	}

    }

    private void CompareThisWithThat(String thisObject, String thatObject) {

	assertTrue(String.format("expected '%s' to be equal to '%s' but it is not.", thisObject, thatObject),
		thisObject.equals(thatObject));
	assertTrue(String.format("expected hashCode() of '%s' to be equal to hashCode() '%s' but it is not.",
		thisObject, thatObject), thisObject.hashCode() == thatObject.hashCode());
	assertTrue(String.format("expected toString() of '%s' to be equal to toString() '%s' but it is not.",
		thisObject, thatObject), thisObject.toString().equals(thatObject.toString()));
    }
}
