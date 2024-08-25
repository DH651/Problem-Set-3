/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy for parse, toString, derivative, and simplify.

    // Partition on number of characters in the variable: single character, multiple
    // character.

    // Partition on type of number: integer, decimal.
    // Partition on sign of number: positive, negative.

    // Partition on type of binary operation: Division, Subtraction, Addition,
    // Multiply.
    // Partition on precedence of left operand of a binary operation: not
    // applicable, lower, higher, same.
    // Partition on precedence of right operand of a binary operation:not
    // applicable, lower, higher, same.
    // Partition on type of left operand of a Binary operation: variable, number,
    // binary operation.
    // Partition on type of right operand of a Binary operation: variable, number,
    // binary operation.

    // Partition on derivative:
    // Partition on type of input string: a variable, a number, an binary operation.

    // Partition on simplify:
    // Partition on overlap between variables in expression and variables in
    // mapping: no overlap, some overlap, all overlap.

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
	assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // tests for Expression
    @Test
    public void testObjectContract1() {
	Expression y = new Variable("y");
	testObjectContract(y, new Variable("y"), new Variable("y"));

	Expression ten = new Number(10);
	testObjectContract(ten, new Number(10), new Number(10));

	Expression simpleExpression1 = new Division(new Variable("x"), new Variable("Foo"));
	Expression simpleExpression1Copy1 = new Division(new Variable("x"), new Variable("Foo"));
	Expression simpleExpression1Copy2 = new Division(new Variable("x"), new Variable("Foo"));
	testObjectContract(simpleExpression1, simpleExpression1Copy1, simpleExpression1Copy2);

	Expression simpleExpression2 = new Subtraction(new Variable("z"), new Number(64.89));
	Expression simpleExpression2Copy1 = new Subtraction(new Variable("z"), new Number(64.89));
	Expression simpleExpression2Copy2 = new Subtraction(new Variable("z"), new Number(64.89));
	testObjectContract(simpleExpression2, simpleExpression2Copy1, simpleExpression2Copy2);
    }

    @Test
    public void testObjectContract2() {
	Expression USA = new Variable("USA");
	testObjectContract(USA, new Variable("USA"), new Variable("USA"));

	Expression flotingPointNumber = new Number(10.987);
	testObjectContract(flotingPointNumber, new Number(10.987), new Number(10.987));

	Expression simpleExpression1 = new Addition(new Variable("Luffy"), new Number(100.476));
	Expression simpleExpression1Copy1 = new Addition(new Variable("Luffy"), new Number(100.476));
	Expression simpleExpression1Copy2 = new Addition(new Variable("Luffy"), new Number(100.476));
	testObjectContract(simpleExpression1, simpleExpression1Copy1, simpleExpression1Copy2);

	Expression simpleExpression2 = new Multiplication(new Variable("Garp"), new Number(98.987));
	Expression simpleExpression2Copy1 = new Multiplication(new Variable("Garp"), new Number(98.987));
	Expression simpleExpression2Copy2 = new Multiplication(new Variable("Garp"), new Number(98.987));
	testObjectContract(simpleExpression2, simpleExpression2Copy1, simpleExpression2Copy2);

    }

    @Test
    public void testObjectContract3() {
	Expression fiveInteger = new Number(5);
	Expression fiveDecimal = new Number(5.0000);
	Expression AnotherFiveDecimal = new Number(5.0000);

	assertTrue("5 should be equal to 5.0000", fiveInteger.equals(fiveDecimal));
	assertTrue("5 should be equal to 5.0000", fiveInteger.equals(AnotherFiveDecimal));
	assertTrue("hashCode of 5 and 5.000 should be same", fiveInteger.hashCode() == fiveDecimal.hashCode());

	assertTrue("5 should be equal to 5.0000", AnotherFiveDecimal.equals(fiveInteger));
	assertTrue("5 should be equal to 5.0000", fiveDecimal.equals(fiveInteger));
	assertTrue("hashCode of 5 and 5.000 should be same", fiveInteger.hashCode() == AnotherFiveDecimal.hashCode());

	Expression sixtynine = new Number(69);
	Expression someDecimal = new Number(5.789);
	assertFalse("5 should not be equal to 5.6790", fiveInteger.equals(new Number(5.6790)));
	assertFalse("5.000 should not be equal to 5.6790", fiveDecimal.equals(new Number(5.6790)));
	assertFalse("69 should not be equal to 5", sixtynine.equals(fiveInteger));
	assertFalse("5.789 should not be equal to 5.000", someDecimal.equals(fiveDecimal));
    }

    // outer operation is division,
    // left operand is a number, a variable, another binary operation.
    // right operand is a number, a variable, another binary operation.
    // left operand has same precedence.
    // right operand has same , higher precedence.

    @Test
    public void testParseANDToString1() {
	// Test Expression 1
	Expression testExpression1 = Expression.parse("x / 4.55");
	Expression expectedExpression1 = new Division(new Variable("x"), new Number(4.55));
	testParse("x / 4.55", expectedExpression1);
	testToString(testExpression1, "x / 4.55");

	// Test Expression 2
	Expression testExpression2 = Expression.parse("56 / Foo");
	Expression expectedExpression2 = new Division(new Number(56), new Variable("Foo"));
	testParse("56 / Foo", expectedExpression2);
	testToString(testExpression2, "56 / Foo");

	// Test Expression 3
	Expression testExpression3 = Expression.parse("z / x");
	Expression expectedExpression3 = new Division(new Variable("z"), new Variable("x"));
	testToString(testExpression3, "z / x");
	testParse("z / x", expectedExpression3);

	// Test Expression 4
	Expression testExpression4 = Expression.parse("(Foo / 9) / (x * 90)");
	Expression leftOperandTestExpression4 = new Division(new Variable("Foo"), new Number(9));
	Expression rightOperandTestExpression4 = new Multiplication(new Variable("x"), new Number(90));
	Expression expectedExpression4 = new Division(leftOperandTestExpression4, rightOperandTestExpression4);
	testToString(testExpression4, "Foo / 9 / (x * 90)");
	testParse("(Foo / 9) / (x * 90)", expectedExpression4);

	// Test Expression 5
	Expression testExpression5 = Expression.parse("(Bar * Foo) / (34 - 37)");
	Expression leftOperandTestExpression5 = new Multiplication(new Variable("Bar"), new Variable("Foo"));
	Expression rightOperandTestExpression5 = new Subtraction(new Number(34), new Number(37));
	Expression expectedExpression5 = new Division(leftOperandTestExpression5, rightOperandTestExpression5);
	testToString(testExpression5, "Bar * Foo / (34 - 37)");
	testParse("(Bar * Foo) / (34 - 37)", expectedExpression5);
    }

    // outer operation is multiplication,
    // left operand is a number, a variable, another binary operation.
    // right operand is a number, a variable, another binary operation.
    // left operand has same precedence, lower precedence.
    // right operand has same precedence.

    @Test
    public void testParseANDToString2() {
	// Test Expression 1
	Expression testExpression1 = Expression.parse("34.56 * x");
	Expression expectedExpression1 = new Multiplication(new Number(34.56), new Variable("x"));
	testParse("34.56 * x", expectedExpression1);
	testToString(testExpression1, "34.56 * x");

	// Test Expression 2
	Expression testExpression2 = Expression.parse("Foo * 45.76");
	Expression expectedExpression2 = new Multiplication(new Variable("Foo"), new Number(45.76));
	testParse("Foo * 45.76", expectedExpression2);
	testToString(testExpression2, "Foo * 45.76");

	// Test Expression 3
	Expression testExpression3 = Expression.parse("x * Foo");
	Expression expectedExpression3 = new Multiplication(new Variable("x"), new Variable("Foo"));
	testParse("x * Foo", expectedExpression3);
	testToString(testExpression3, "x * Foo");

	// Test Expression 4
	Expression testExpression4 = Expression.parse("(34 + x) * 98 * 76");
	Expression additionTestExpression4 = new Addition(new Number(34), new Variable("x"));
	Expression multiplicationTestExpression4 = new Multiplication(additionTestExpression4, new Number(98));
	Expression expectedExpression4 = new Multiplication(multiplicationTestExpression4, new Number(76));
	testParse("(34 + x) * 98 * 76", expectedExpression4);
	testToString(testExpression4, "(34 + x) * 98 * 76");

	// Test Expression 5
	Expression testExpression5 = Expression.parse("67 * y * (z / 4.666)");
	Expression rightOperandTestExpression5 = new Division(new Variable("z"), new Number(4.666));
	Expression leftOperandTestExpression5 = new Multiplication(new Number(67), new Variable("y"));
	Expression expectedExpression5 = new Multiplication(leftOperandTestExpression5, rightOperandTestExpression5);
	testParse("67 * y * (z / 4.666)", expectedExpression5);
	testToString(testExpression5, "67 * y * z / 4.666");

    }

    // outer operation is addition,
    // left operand is a number, a variable, another binary operation.
    // right operand is a number, a variable, another binary operation.
    // left operand has same precedence, higher precedence.
    // right operand has same precedence, higher precedence.

    @Test
    public void testParseANDToString3() {
	// Test Expression 1
	Expression testExpression1 = Expression.parse("BAR + 45.67");
	Expression expectedExpression1 = new Addition(new Variable("BAR"), new Number(45.67));
	testParse("BAR + 45.67", expectedExpression1);
	testToString(testExpression1, "BAR + 45.67");

	// Test Expression 2
	Expression testExpression2 = Expression.parse("4 + z");
	Expression expectedExpression2 = new Addition(new Number(4), new Variable("z"));
	testParse("4 + z", expectedExpression2);
	testToString(testExpression2, "4 + z");

	// Test Expression 3
	Expression testExpression3 = Expression.parse("(4.66 - 34) + zf + y");
	Expression subtractionTestExpression3 = new Subtraction(new Number(4.66), new Number(34));
	Expression additionTestExpression3 = new Addition(subtractionTestExpression3, new Variable("zf"));
	Expression expectedExpression3 = new Addition(additionTestExpression3, new Variable("y"));
	testParse("(4.66 - 34) + zf + y", expectedExpression3);
	testToString(testExpression3, "4.66 - 34 + zf + y");

	// Test Expression 4
	Expression testExpression4 = Expression.parse("34 + gh + 283 / x");
	Expression divisionTestExpression4 = new Division(new Number(283), new Variable("x"));
	Expression additionTestExpression4 = new Addition(new Number(34), new Variable("gh"));
	Expression expectedExpression4 = new Addition(additionTestExpression4, divisionTestExpression4);
	testParse("34 + gh + 283 / x", expectedExpression4);
	testToString(testExpression4, "34 + gh + 283 / x");

	// Test Expression 5
	Expression testExpression5 = Expression.parse("34 * x + (45 - b)");
	Expression multiplicationPartExpression5 = new Multiplication(new Number(34), new Variable("x"));
	Expression subtractionPartExpression5 = new Subtraction(new Number(45), new Variable("b"));
	Expression expectedExpression5 = new Addition(multiplicationPartExpression5, subtractionPartExpression5);
	testParse("34 * x + (45 - b)", expectedExpression5);
	testToString(testExpression5, "34 * x + 45 - b");

    }

    // outer operation is subtraction,
    // left operand is a number, a variable, another binary operation.
    // right operand is a number, a variable, another binary operation.
    // left operand has same precedence, higher precedence.
    // right operand has same precedence.

    @Test
    public void testParseANDToString4() {
	// Test Expression 1
	Expression testExpression1 = Expression.parse("Foo - 56.78");
	Expression expectedExpression1 = new Subtraction(new Variable("Foo"), new Number(56.78));
	testParse("Foo - 56.78", expectedExpression1);
	testToString(testExpression1, "Foo - 56.78");

	// Test Expression 2
	Expression testExpression2 = Expression.parse("34.5 - x");
	Expression expectedExpression2 = new Subtraction(new Number(34.5), new Variable("x"));
	testParse("34.5 - x", expectedExpression2);
	testToString(testExpression2, "34.5 - x");

	// Test Expression 3
	Expression testExpression3 = Expression.parse("(3 - z) - (x - 56)");
	Expression leftOperandTestExpression3 = new Subtraction(new Number(3), new Variable("z"));
	Expression rightOperandTestExpression3 = new Subtraction(new Variable("x"), new Number(56));
	Expression expectedExpression3 = new Subtraction(leftOperandTestExpression3, rightOperandTestExpression3);
	testParse("(3 - z) - (x - 56)", expectedExpression3);
	testToString(testExpression3, "3 - z - (x - 56)");

	// Test Expression 4
	Expression testExpression4 = Expression.parse("(3 - g) - 34 * y");
	Expression leftOperandTestExpression4 = new Subtraction(new Number(3), new Variable("g"));
	Expression rightOperandTestExpression4 = new Multiplication(new Number(34), new Variable("y"));
	Expression expectedExpression4 = new Subtraction(leftOperandTestExpression4, rightOperandTestExpression4);
	testParse("(3 - g) - 34 * y", expectedExpression4);
	testToString(testExpression4, "3 - g - 34 * y");

	// Test Expression 5
	Expression testExpression5 = Expression.parse("913 / 56 - fg + 45");
	Expression divisionPartExpression5 = new Division(new Number(913), new Number(56));
	Expression subtractionPartExpression5 = new Subtraction(divisionPartExpression5, new Variable("fg"));
	Expression expectedExpression5 = new Addition(subtractionPartExpression5, new Number(45));
	testParse("913 / 56 - fg + 45", expectedExpression5);
	testToString(testExpression5, "913 / 56 - fg + 45");

    }

    // expression is a variable, a number, a binary operation
    // binary operation is addition, subtraction, division
    // left and right operands are variable, and number.

    @Test
    public void testDerivative1() {
	// Test data: simple derivative 1

	// Input: y Answer =d/dx = 0
	Expression testExpression1 = new Variable("y");
	Expression derivedtestExpression1 = new Number(0);
	testDerivative(testExpression1, "x", derivedtestExpression1); // d/dx of y is 0

	// Input: x Answer =d/dx = 1
	Expression testExpression2 = new Variable("x");
	Expression derivedtestExpression2 = new Number(1);
	testDerivative(testExpression2, "x", derivedtestExpression2); // d/dx of x is 1

	// Input: Any integer or floating point number Answer=d/dx = 0
	Expression testExpression3 = new Number(23.56);
	Expression derivedtestExpression3 = new Number(0);
	testDerivative(testExpression3, "x", derivedtestExpression3); // d/dx of a number is 0

	// Left operand is a variable - right operand is a number
	// Input: Foo + 34.55 ANSWER = d/d(x) = 0
	Expression testExpression4 = new Addition(new Variable("Foo"), new Number(34.55));
	Expression derivedtestExpression4 = new Number(0);
	testDerivative(testExpression4, "x", derivedtestExpression4); // d/dx of (Foo + 34.55) is 0

	// Left operand is a number - right operand is a variable
	// Input: 56.0 - y ANSWER = d/d(y) = 1
	Expression testExpression5 = new Subtraction(new Number(56.0), new Variable("y"));
	Expression derivedtestExpression5 = new Number(-1);
	testDerivative(testExpression5, "y", derivedtestExpression5); // d/dy of (56.0 - y) is -1

	// Left operand is a variable - right operand is a number
	// Input: x / 34 Answer = d/d(x) = 1 / 34
	Expression testExpression6 = new Division(new Variable("x"), new Number(34));
	Expression derivedtestExpression6 = new Number(0.02941);
	testDerivative(testExpression6, "x", derivedtestExpression6); // d/dx of (x / 34) is 1/34

    }

    // expression is a variable, a number, a binary operation
    // binary operation is addition, subtraction, division, multiplication
    // left and right operands are variable, and number.

    @Test
    public void testDerivative2() {
	// Test simple derivative 2

	// Left operand is variable - right operand is number
	// Foo - 34.55 ANSWER = d/d(x) = 0;
	Expression testExpression1 = new Subtraction(new Variable("Foo"), new Number(34.55));
	Expression derivedtestExpression1 = new Number(0);
	testDerivative(testExpression1, "x", derivedtestExpression1); // d/dx of (Foo - 34.55) is 0

	// Left Operand is a number - Right Operand is a variable
	// 56.00 - y ANSWER = d/d(y) = -1;
	Expression testExpression2 = new Subtraction(new Number(56.00), new Variable("y"));
	Expression derivedtestExpression2 = new Number(-1);
	testDerivative(testExpression2, "y", derivedtestExpression2); // d/dy of (56.00 - y) is -1

	// Left operand is a variable - Right operand is a number
	// x * 45 Answer = d / d(x) = 45;
	Expression testExpression3 = new Multiplication(new Variable("x"), new Number(45));
	Expression derivedtestExpression3 = new Number(45);
	testDerivative(testExpression3, "x", derivedtestExpression3); // d/dx of (x * 45) is 45

	// Left operand is a variable - Right operand is a number
	// d(2 * x) / dx = 2;
	Expression testExpression4 = new Multiplication(new Number(2), new Variable("x"));
	Expression derivedtestExpression4 = new Number(2);
	testDerivative(testExpression4, "x", derivedtestExpression4); // d/dx of (2 * x) is 2

	// Left operand is a variable - Right operand is a number
	// d(y / 34) / dx = 0;
	Expression testExpression5 = new Division(new Variable("y"), new Number(34));
	Expression derivedtestExpression5 = new Number(0);
	testDerivative(testExpression5, "x", derivedtestExpression5); // d/dx of (y / 34) is 0

    }

    // expression is a binary operation
    // binary operation is division, multiplication
    // left and right operands are expressions.
    // left and right operand have same or higher precedence than the outer
    // operator.

    @Test
    public void testDerivative3() {
	// Test simple derivative 3

	// Left operand is var - right operand is var
	// d(x * x) / dx = x * 1 + 1 * x = x + x = 2 * x
	Expression testExpression1 = new Multiplication(new Variable("x"), new Variable("x"));
	Expression derivedtestExpression1 = new Multiplication(new Number(2), new Variable("x"));
	testDerivative(testExpression1, "x", derivedtestExpression1); // d/dx of (x * x) is 2 * x

	// Left operand is a variable - right operand is a variable
	// d(x * y) / dx = y
	Expression testExpression2 = new Multiplication(new Variable("x"), new Variable("y"));
	Expression derivedtestExpression2 = new Variable("y");
	testDerivative(testExpression2, "x", derivedtestExpression2); // d/dx of (x * y) is y

	// Left operand is a variable - right operand is a variable
	// d(y * y) / dx = 0
	Expression testExpression3 = new Multiplication(new Variable("y"), new Variable("y"));
	Expression derivedtestExpression3 = new Number(0);
	testDerivative(testExpression3, "x", derivedtestExpression3); // d/dx of (y * y) is 0

	// Left operand is a number - right operand is a variable
	// d(45 / x) / dx = (x * 0 - 45 * 1) / x^2 = -45 / x^2
	Expression testExpression4 = new Division(new Number(45), new Variable("x"));
	Expression derivedtestExpression4 = new Division(new Number(-45),
		new Multiplication(new Variable("x"), new Variable("x")));
	testDerivative(testExpression4, "x", derivedtestExpression4); // d/dx of (45 / x) is -45 / x^2

	// Left operand is a number - right operand is a variable
	// d(985 / y) / dx = (y * 0 - 985 * dy / dx) / y^2 = 0
	Expression testExpression5 = new Division(new Number(985), new Variable("y"));
	Expression derivedtestExpression5 = new Number(0);
	testDerivative(testExpression5, "x", derivedtestExpression5); // d/dx of (985 / y) is 0

	// // Left operand is a variable - right operand is a variable
	// d(u / v) / dv = - u / v^2 here variable u is treated as constant
	// Assuming u and v are variables
	Expression u = new Variable("u");
	Expression v = new Variable("v");
	Expression testExpression6 = new Division(u, v);
	Expression derivedtestExpression6 = new Division(new Subtraction(new Number(0), u), new Multiplication(v, v));
	// d/ d(x) of u/v is -u / v^2
	testDerivative(testExpression6, "v", derivedtestExpression6); // d/dv of (u /
	// v) is -u / v^2

    }

    // expression is a variable, a number, a binary operation
    // binary operation is multiplication, addition
    // left and right operands are binary operations.
    // left and right operand have same or lower precedence than the outer
    // expression.

    @Test
    public void testDerivative4() {
	// Test Complex derivatives 1

	// 1. d(x / 34 + x * x) / dx = 1/34 + x * 1 + 1 * x = 1/34 + 2 * x
	// Explanation: The derivative of x / 34 is 1 / 34, and the derivative of x * x
	// is 2 * x.
	// So, the total derivative is 1 / 34 + 2 * x.
	Expression testExpression1 = new Addition(new Division(new Variable("x"), new Number(34)),
		new Multiplication(new Variable("x"), new Variable("x")));
	Expression derivedtestExpression1 = new Addition(new Number(0.02941),
		new Multiplication(new Number(2), new Variable("x")));
	testDerivative(testExpression1, "x", derivedtestExpression1); // d/dx of (x / 34 + x * x) is 1 / 34 + 2 * x

	// 2. d((x - y) * (34 + 78.8)) / dx = 112.8
	// Explanation: The derivative of (x - y) with respect to x is 1, and (34 +
	// 78.8) is a constant.
	// Thus, the derivative of the product is just the constant 34 + 78.8, which
	// equals 112.8.
	Expression testExpression2 = new Multiplication(new Subtraction(new Variable("x"), new Variable("y")),
		new Addition(new Number(34), new Number(78.8)));
	Expression derivedtestExpression2 = new Number(112.8);
	testDerivative(testExpression2, "x", derivedtestExpression2); // d/dx of ((x - y) * (34 + 78.8)) is 112.8

	// 3. d((x * y) * (x / y)) / dx = 2x
	// Explanation: The derivative of (x * y) * (x / y) with respect to x is
	// calculated using the product rule.
	// After simplifying, it results in (x * y * (y / (y * y))) + (y * (x / y))
	Expression x = new Variable("x");
	Expression y = new Variable("y");
	Expression testExpression3 = new Multiplication(new Multiplication(x, y), new Division(x, y));

	Expression alternatederivedtestExpression3 = new Addition(
		new Multiplication(new Multiplication(x, y), new Division(y, new Multiplication(y, y))),
		new Multiplication(y, new Division(x, y)));

	testDerivative(testExpression3, "x", alternatederivedtestExpression3);
	// d/dx of ((x* y) * (x / y)) is (x * y * (y / (y * y))) + (y * (x / y))

	// 4. d(x - Yoo / 45 + 98) / d(Yoo) = -1 / 45
	// Explanation: The derivative of x with respect to Yoo is 0, the derivative of
	// -Yoo / 45 with respect to Yoo is -1 / 45, and 98 is a constant with respect
	// to Yoo.
	// Thus, the total derivative is -1 / 45.
	Expression testExpression4 = new Addition(
		new Subtraction(new Variable("x"), new Division(new Variable("Yoo"), new Number(45))), new Number(98));
	Expression derivedtestExpression4 = new Number(-0.02222);
	testDerivative(testExpression4, "Yoo", derivedtestExpression4); // d/dYoo of (x - Yoo / 45 + 98) is -1 / 45

    }

    // expression is a binary operation
    // binary operation is addition and subtraction
    // left and right operand is a variable, a number.
    // all, some , no variables in mapping are present in expression
    @Test
    public void testSimplify1() {

	// 1. Input: y - 20; mapping { y = 27 } => Answer: 7
	Expression testExpression1 = new Subtraction(new Variable("y"), new Number(20));
	Map<String, Double> mapping1 = new HashMap<>();
	mapping1.put("y", Double.valueOf(27));
	Expression resultExpression1 = new Number(7); // Simplified result
	testSimplify(testExpression1, mapping1, resultExpression1);

	// 2. Input: y + 84; mapping { y = 7 } => Answer: 91
	Expression testExpression2 = new Addition(new Variable("y"), new Number(84));
	Map<String, Double> mapping2 = new HashMap<>();
	mapping2.put("y", Double.valueOf(7));
	Expression resultExpression2 = new Number(91); // Simplified result
	testSimplify(testExpression2, mapping2, resultExpression2);

	// 3. Input: foo + bar; mapping { foo = 89, bar = 90 } => Answer: 179
	Expression testExpression3 = new Addition(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping3 = new HashMap<>();
	mapping3.put("foo", Double.valueOf(89));
	mapping3.put("bar", Double.valueOf(90));
	Expression resultExpression3 = new Number(179); // Simplified result
	testSimplify(testExpression3, mapping3, resultExpression3);

	// 4. Input: foo + bar; mapping { bar = 89, dff = 499 } => Answer: foo + 89
	Expression testExpression4 = new Addition(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping4 = new HashMap<>();
	mapping4.put("bar", Double.valueOf(89));
	mapping4.put("dff", Double.valueOf(499));
	Expression resultExpression4 = new Addition(new Variable("foo"), new Number(89)); // Simplified result
	testSimplify(testExpression4, mapping4, resultExpression4);

	// 5. Input: foo + bar; mapping { x = 78 } => Answer: foo + bar
	Expression testExpression5 = new Addition(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping5 = new HashMap<>();
	mapping5.put("x", Double.valueOf(78));
	Expression resultExpression5 = new Addition(new Variable("foo"), new Variable("bar")); // No simplification as
											       // foo and bar are not
											       // mapped
	testSimplify(testExpression5, mapping5, resultExpression5);

    }

    // expression is a binary operation
    // binary operation is a multiplication.
    // left and right operand is a variable, a number.
    // all, some , no variables in mapping are present in expression
    @Test
    public void testSimplify2() {

	// 1. Input: x * 89; mapping { x = 45 } => Answer: 45 * 89 = 4005
	Expression testExpression1 = new Multiplication(new Variable("x"), new Number(89));
	Map<String, Double> mapping1 = new HashMap<>();
	mapping1.put("x", Double.valueOf(45));
	Expression resultExpression1 = new Number(4005); // Simplified result
	testSimplify(testExpression1, mapping1, resultExpression1);

	// 2. Input: 76 * y; mapping { wex = 45, yrr = 90 } => Answer: 76 * y
	Expression testExpression2 = new Multiplication(new Number(76), new Variable("y"));
	Map<String, Double> mapping2 = new HashMap<>();
	mapping2.put("wex", Double.valueOf(45));
	mapping2.put("yrr", Double.valueOf(90));
	Expression resultExpression2 = new Multiplication(new Number(76), new Variable("y")); // No change due to
											      // mapping
	testSimplify(testExpression2, mapping2, resultExpression2);

	// 3. Input: foo * bar; mapping { foo = 89, bar = 90 } => Answer: 89 * 90 = 8010
	Expression testExpression3 = new Multiplication(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping3 = new HashMap<>();
	mapping3.put("foo", Double.valueOf(89));
	mapping3.put("bar", Double.valueOf(90));
	Expression resultExpression3 = new Number(8010); // Simplified result
	testSimplify(testExpression3, mapping3, resultExpression3);

	// 4. Input: foo * bar; mapping { bar = 89, dff = 499 } => Answer: foo * 89
	Expression testExpression4 = new Multiplication(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping4 = new HashMap<>();
	mapping4.put("bar", Double.valueOf(89));
	mapping4.put("dff", Double.valueOf(499));
	Expression resultExpression4 = new Multiplication(new Variable("foo"), new Number(89)); // Simplified result
	testSimplify(testExpression4, mapping4, resultExpression4);

	// 5. Input: foo * bar; mapping { x = 78 } => Answer: foo * bar
	Expression testExpression5 = new Multiplication(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping5 = new HashMap<>();
	mapping5.put("x", Double.valueOf(78));
	Expression resultExpression5 = new Multiplication(new Variable("foo"), new Variable("bar")); // No
												     // simplification
												     // as foo and bar
												     // are not mapped
	testSimplify(testExpression5, mapping5, resultExpression5);
    }

    // expression is a binary operation
    // binary operation is a division.
    // left and right operand is a variable, a number.
    // all, some , no variables in mapping are present in expression

    @Test
    public void testSimplify3() {
	// 1. Input: x / 98.43; mapping { foo = 34 } => Answer: x / 98.43
	Expression testExpression1 = new Division(new Variable("x"), new Number(98.4300));
	Map<String, Double> mapping1 = new HashMap<>();
	mapping1.put("foo", Double.valueOf(34));
	Expression resultExpression1 = new Division(new Variable("x"), new Number(98.4300)); // No change due to mapping
	testSimplify(testExpression1, mapping1, resultExpression1);

	// 2. Input: 49.30 / Foo; mapping { Foo = 3, seie = 38 } => Answer: 49.30 / 3
	Expression testExpression2 = new Division(new Number(49.30), new Variable("Foo"));
	Map<String, Double> mapping2 = new HashMap<>();
	mapping2.put("Foo", Double.valueOf(3));
	mapping2.put("seie", Double.valueOf(38));
	Expression resultExpression2 = new Number(16.43333); // Simplified result
	testSimplify(testExpression2, mapping2, resultExpression2);

	// 3. Input: foo / bar; mapping { foo = 89, bar = 90 } => Answer: 89 / 90
	Expression testExpression3 = new Division(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping3 = new HashMap<>();
	mapping3.put("foo", Double.valueOf(89));
	mapping3.put("bar", Double.valueOf(90));
	Expression resultExpression3 = new Number(0.98889); // Simplified result
	testSimplify(testExpression3, mapping3, resultExpression3);

	// 4. Input: foo / bar; mapping { bar = 89, dff = 499 } => Answer: foo / 89
	Expression testExpression4 = new Division(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping4 = new HashMap<>();
	mapping4.put("bar", Double.valueOf(89));
	mapping4.put("dff", Double.valueOf(499));
	Expression resultExpression4 = new Division(new Variable("foo"), new Number(89)); // Simplified result
	testSimplify(testExpression4, mapping4, resultExpression4);

	// 5. Input: foo / bar; mapping { x = 78 } => Answer: foo / bar
	Expression testExpression5 = new Division(new Variable("foo"), new Variable("bar"));
	Map<String, Double> mapping5 = new HashMap<>();
	mapping5.put("x", Double.valueOf(78));
	Expression resultExpression5 = new Division(new Variable("foo"), new Variable("bar")); // No simplification as
											       // foo and bar are not
											       // mapped
	testSimplify(testExpression5, mapping5, resultExpression5);
    }

    // expression is a binary operation.
    // binary operation is a division, addition, subtraction, multiplication.
    // left and right operand is binary operations with same, higher, lower
    // precedences.
    // all, some , no variables in mapping are present in expression.

    @Test
    public void testSimplify4() {
	// 1. Input: (foo + bar) * y + 56 - (z * 45); mapping {foo: 45, z: 45} =>
	// Answer: 90 * y - 1969
	Expression testExpression1 = new Addition(
		new Multiplication(new Addition(new Variable("foo"), new Variable("bar")), new Variable("y")),
		new Subtraction(new Number(56), new Multiplication(new Variable("z"), new Number(45))));
	Map<String, Double> mapping1 = new HashMap<>();
	mapping1.put("foo", Double.valueOf(45));
	mapping1.put("z", Double.valueOf(45));

	Expression resultExpression1 = new Addition(
		new Multiplication(new Addition(new Number(45), new Variable("bar")), new Variable("y")),
		new Number(-1969));
	testSimplify(testExpression1, mapping1, resultExpression1);

	// 2. Input: (34.55 / (5 – x + y)); mapping {z: 45, g:98} => Answer: (34.55 / (5
	// – x + y))
	Expression testExpression2 = new Division(new Number(34.55),
		new Subtraction(new Number(5), new Addition(new Variable("x"), new Variable("y"))));
	Map<String, Double> mapping2 = new HashMap<>();
	mapping2.put("z", Double.valueOf(45));
	mapping2.put("g", Double.valueOf(98));
	Expression resultExpression2 = testExpression2; // No change due to mapping
	testSimplify(testExpression2, mapping2, resultExpression2);

	// 3. Input: (x * y) + x * x * 76 - 45 / x + y; mapping {x: 56, y: 39} =>
	// Answer: 240559
	Expression testExpression3 = new Addition(
		new Addition(new Multiplication(new Variable("x"), new Variable("y")),
			new Multiplication(new Multiplication(new Variable("x"), new Variable("x")), new Number(76))),
		new Subtraction(new Addition(new Variable("y"), new Division(new Number(45), new Variable("x"))),
			new Number(45 / 56)));
	Map<String, Double> mapping3 = new HashMap<>();
	mapping3.put("x", Double.valueOf(56));
	mapping3.put("y", Double.valueOf(39));
	Expression resultExpression3 = new Number(240559.79688); // Simplified result
	testSimplify(testExpression3, mapping3, resultExpression3);

	// 4. Input: ((5 - x) - x - 45) * (55 - (x - y)); mapping {x: 56, y: 78} =>
	// Answer: -11704
	Expression testExpression4 = new Multiplication(
		new Subtraction(new Subtraction(new Subtraction(new Number(5), new Variable("x")), new Variable("x")),
			new Number(45)),
		new Subtraction(new Number(55), new Subtraction(new Variable("x"), new Variable("y"))));
	Map<String, Double> mapping4 = new HashMap<>();
	mapping4.put("x", Double.valueOf(56));
	mapping4.put("y", Double.valueOf(78));
	Expression resultExpression4 = new Number(-11704); // Simplified result
	testSimplify(testExpression4, mapping4, resultExpression4);

	// 5. Input: 45 / (45 – x) * (foo + foo * foo) – (56 – (x – y)); mapping {foo:
	// 78, x: 56, y: 90} => Answer: -25298.18750
	Expression testExpression5 = new Subtraction(
		new Multiplication(new Division(new Number(45), new Subtraction(new Number(45), new Variable("x"))),
			new Addition(new Variable("foo"),
				new Multiplication(new Variable("foo"), new Variable("foo")))),
		new Subtraction(new Number(56), new Subtraction(new Variable("x"), new Variable("y"))));
	Map<String, Double> mapping5 = new HashMap<>();
	mapping5.put("foo", Double.valueOf(78));
	mapping5.put("x", Double.valueOf(56));
	mapping5.put("y", Double.valueOf(90));
	Expression resultExpression5 = new Number(-25298.18750); // Simplified result
	testSimplify(testExpression5, mapping5, resultExpression5);
    }

    // private helpers
    private void testToString(Expression thisObject, String expectedString) {
	String returnedString = thisObject.toString();

	assertEquals(String.format("Expected toString() to return '%s' but got '%s'. ", expectedString, returnedString),
		expectedString, returnedString);
    }

    private void testObjectContract(Expression thisObject, Expression thatObject, Expression anotherObject) {

	assertTrue(String.format("equals() is not reflexive, expected '%s' to be equal to '%s' but it didn't.",
		thisObject, thisObject), thisObject.equals(thisObject));
	assertTrue(String.format(
		"equals() is not symmetric, expected '%s' should be equal to '%s' and '%s' should be equal to '%s'  but it is not.",
		thisObject, thatObject, thisObject, thatObject),
		thisObject.equals(thatObject) && thatObject.equals(thisObject));
	assertTrue(String.format(
		"equals() is not transitive, expected '%s' should be equal to '%s' and '%s' should be equal to '%s' and '%s' should be equal to '%s' but it is not.",
		thisObject, thatObject, thatObject, anotherObject, thisObject, anotherObject),
		thisObject.equals(thatObject) && thatObject.equals(anotherObject) && thisObject.equals(thatObject));
	assertFalse("For non-null references of '%s', %s.equals(null) should return false", thisObject.equals(null));
	assertTrue(String.format("Expected equals() of '%s' to be consistent but it is not", thisObject),
		thisObject.equals(thatObject));
	assertTrue(
		String.format("Expected hashCode of '%s' and '%s' to be same but it is not.", thisObject, thatObject),
		thisObject.hashCode() == thatObject.hashCode());
	assertTrue(String.format("Expected hashCode of '%s' and '%s' to be same but it is not.", thatObject,
		anotherObject), thatObject.hashCode() == anotherObject.hashCode());
	assertTrue(String.format("Expected hashCode of '%s' and '%s' to be same but it is not.", anotherObject,
		thisObject), anotherObject.hashCode() == thisObject.hashCode());
    }

    private void CompareThisWithThat(Expression thisObject, Expression thatObject) {

	assertTrue(String.format("expected '%s' to be equal to '%s' but it is not.", thisObject, thatObject),
		thisObject.equals(thatObject));
	assertTrue(String.format("expected hashCode() of '%s' to be equal to hashCode() '%s' but it is not.",
		thisObject, thatObject), thisObject.hashCode() == thatObject.hashCode());
	assertTrue(String.format("expected toString() of '%s' to be equal to toString() '%s' but it is not.",
		thisObject, thatObject), thisObject.toString().equals(thatObject.toString()));
    }

    private void testParse(String expression, Expression expectedObject) {
	Expression returnedObject = Expression.parse(expression);

	assertTrue(String.format("expected '%s' to be equal to '%s' but it is not.", returnedObject, expectedObject),
		returnedObject.equals(expectedObject));
	assertTrue(String.format("expected hashCode() of '%s' to be equal to hashCode() '%s' but it is not.",
		returnedObject, expectedObject), returnedObject.hashCode() == expectedObject.hashCode());
	assertTrue(String.format("expected toString() of '%s' to be equal to toString() '%s' but it is not.",
		returnedObject, expectedObject), returnedObject.toString().equals(expectedObject.toString()));
    }

    private void testDerivative(Expression testExpression, String variable, Expression expectedDerivative) {
	Expression actualDerivative = testExpression.derivative(variable);
	CompareThisWithThat(actualDerivative, expectedDerivative);

    }

    private void testSimplify(Expression thisObject, Map<String, Double> mapping, Expression expectedExpression) {
	Expression simplifiedExpression = thisObject.simplify(mapping);
	CompareThisWithThat(simplifiedExpression, expectedExpression);
    }
}
