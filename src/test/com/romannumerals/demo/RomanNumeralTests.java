package test.com.romannumerals.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import main.com.romannumerals.demo.RomanNumeralConverter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RomanNumeralTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void conversionTest() {
        RomanNumeralConverter tester = new RomanNumeralConverter();
        assertEquals(1239, tester.convert("MCCXXXIX"), "MMMCMXCIX must equal 1239");
        assertEquals(3999, tester.convert("MMMCMXCIX"), "MMMCMXCIX must equal 3999");
        assertEquals(499, tester.convert("CDXCIX"), "CDXCIX must equal 499");
    }
    @Test
    public void faultyInputShouldThrowInvalidArgumentException() throws IllegalArgumentException {
        RomanNumeralConverter tester = new RomanNumeralConverter();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Not a valid Roman numeral");
        tester.convert("MMMMM");
    }
}
