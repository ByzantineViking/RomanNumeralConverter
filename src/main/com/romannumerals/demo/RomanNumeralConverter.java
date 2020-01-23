package main.com.romannumerals.demo;

import java.util.Scanner;
import java.util.regex.*;

/** See http://mathworld.wolfram.com/RomanNumerals.html */
public class RomanNumeralConverter {

    // Match the pattern against Regex of possible Roman numerals. Implementation difference: 4 M:s are not allowed.
    private Pattern RomanPattern = Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");

    private int processRoman(char r) {
        if (r == 'I')
            return 1;
        if (r == 'V')
            return 5;
        if (r == 'X')
            return 10;
        if (r == 'L')
            return 50;
        if (r == 'C')
            return 100;
        if (r == 'D')
            return 500;
        if (r == 'M')
            return 1000;
        return -1000000;
    }

    /**
     * @param  str  Roman numeral
     * @return      Converted integer
     * @throws      IllegalArgumentException  if entered Roman numeral is not valid.
     * @see         <a href="https://www.geeksforgeeks.org/converting-roman-numerals-decimal-lying-1-3999/">https://www.geeksforgeeks.org/converting-roman-numerals-decimal-lying-1-3999/</a>
     */
    public int convert( String str) {
        Matcher matcher = RomanPattern.matcher(str);
        boolean matches = matcher.matches();
        if(!matches)
            throw new IllegalArgumentException("Not a valid Roman numeral");
        int res = 0;
        for (int i=0; i<str.length(); i++) {
            int currentValue = processRoman(str.charAt(i));
            if (i+1 <str.length()) {
                int nextValue = processRoman(str.charAt(i+1));
                if (currentValue >= nextValue) {
                    // Value of current symbol is greater or equal to the next symbol
                    res = res + currentValue;
                } else {
                    res = res + nextValue - currentValue;
                    i++;
                    // Value of current symbol is less than the next symbol
                }
            } else {
                // Last symbol
                res = res + currentValue;
                i++;
            }
        }
        return res;
    }

    /**
     * @see  <a href="https://stackoverflow.com/questions/267399/how-do-you-match-only-valid-roman-numerals-with-a-regular-expression">https://stackoverflow.com/questions/267399/how-do-you-match-only-valid-roman-numerals-with-a-regular-expression</a>
     */
    public static void main(String[] args) throws IllegalArgumentException {
        RomanNumeralConverter object = new RomanNumeralConverter();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Available characters: MDCLXVI");
            System.out.println("Enter a Roman Numeral: ");
            String response = scanner.next();
            System.out.println(object.convert(response));

            // Ask the user if he wants to enter a new Roman numeral.
            boolean responseReceived = false;
            while(!responseReceived) {
                System.out.println("Do you want to enter a new numeral: [Y]/[N]");
                String again = scanner.next();
                if(again.equalsIgnoreCase("n")) {
                    System.exit(1);
                } else if(again.equalsIgnoreCase("y")) {
                    responseReceived = true;
                } else {
                    System.out.println("Please enter either [Y] or [N]");
                }
            }
        }

    }

}
