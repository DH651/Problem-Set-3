/**
 * 
 */
package expressivo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * An immutable data type representing a monomial expression representing a
 * nonnegative integer or floating-point number
 */
public class Number implements Expression {
    // fields
    private final BigDecimal number;

    // Abstraction Function:
    // AF(variable) = An monomial expression that contains a nonnegative integer or
    // a floating-point number

    // Rep Invariant:
    // variable doesn't point to null
    // variable is a non-empty string

    // Safety from rep exposure:
    // All the fields private and final
    // Observers like toString, hashCode, equals don't expose representation to the
    // client
    // Mutators like derivative, simplify don't expose representation to the client

    // constructor
    public Number(long integer) {
	BigDecimal number = new BigDecimal(integer);
	this.number = number.setScale(5, RoundingMode.HALF_UP);
    }

    public Number(double floatingPointNumber) {
	BigDecimal number = new BigDecimal(floatingPointNumber);
	this.number = number.setScale(5, RoundingMode.HALF_UP);

    }

    public Number(String constant) {
	BigDecimal number = new BigDecimal(constant);
	this.number = number.setScale(5, RoundingMode.HALF_UP);

    }

    @Override
    public String toString() {
	return number.stripTrailingZeros().toPlainString();
    }

    @Override
    public boolean equals(Object thatObject) {
	if (thatObject instanceof Expression && thatObject instanceof Number) {
	    Number anotherNumber = (Number) thatObject;

	    return sameValue(anotherNumber);
	}
	return false;
    }

    @Override
    public int hashCode() {
	return number.hashCode();
    }

    @Override
    public Expression derivative(String variable) {
	return new Number(0);
    }

    @Override
    public Expression simplify(Map<String, Double> mapping) {
	return new Number(number.floatValue());
    }

    @Override
    public int getprecedenceDisparity(String operator) {
	return 1;
    }

    @Override
    public boolean isNumber() {
	return true;
    }

    /**
     * Returns the monomial which may be an integer or a floating point number as a
     * float value
     * 
     * @return a monomial as a float value
     */
    private float floatValue() {
	return number.floatValue();
    }

    /**
     * Compares two monomial and returns true if both of them are equals
     * 
     * @param thatObject, a monomial representing a integer or floating-point number
     * @return true if thatObject is equal to this monomial
     */
    private boolean sameValue(Number thatObject) {

	return number.floatValue() == thatObject.floatValue();

    }

}
