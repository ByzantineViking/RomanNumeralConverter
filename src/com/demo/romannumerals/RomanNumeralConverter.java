package com.demo.romannumerals;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class RomanNumeralConverter {

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
        else
            return -1;
    }
    /**
     *  There is only 6 different possible subtractions.
    *   @return the result of subtraction, or -4 if the subtraction is not valid.
    *   @param c number to subtract from
     *  @param s number to subtract
     */
    private int validSubtraction(int c, int s) {
        if(c == 5 && s == 1)
            return 4;
        if(c == 10 && s == 1)
            return 9;
        if(c == 50 && s == 10)
            return 40;
        if(c == 100 && s == 10)
            return 90;
        if(c == 500 && s == 100)
            return 400;
        if(c == 1000 && s == 100)
            return 900;
        else
            return -1;
    }

    /**
     *  @param  str The string of Roman numerals to be converted to decimal form.
     *  @return int Roman numeral in decimal form, or
     *              -1 if a character in the numeral is not valid,
     *              -2 if the order of characters is not valid,
     *              -3 if there is more than 3 of the same character in a row,
     *              -4 if a invalid subtraction is used,
     *              -5 if subtraction was not on the last same-valued character.
     *              Only one error is responded to at any input, and such they take the following priority: -1,-2,-3,-4,-5
     */
    private int convert(@NotNull String str) {
        int res = 0;
        int currentSmallest = 1000;
        int subsequentSame = 0;
        boolean lastOfNumber = false;
        for ( int index=0; index < str.length(); index++) {
            // Current symbol in decimal form
            int currentSymbol = processRoman(str.charAt(index));
            // Validation that character is valid
            if (currentSymbol == -1) return -1;
            // Not the last symbol
            if (index+1 <str.length()) {
                int nextSymbol = processRoman(str.charAt(index+1));
                if (nextSymbol == -1) return -1;
                if ( nextSymbol <= currentSymbol ) {
                    // Current symbol is an additive symbol e.g. XI, where I is added to X
                    // Validation that it cannot be larger than preceding characters, as roman numerals are written in descending order
                    if (currentSymbol > currentSmallest) {
                        return -2;
                    } else if (currentSmallest == currentSymbol) {
                        subsequentSame += 1;
                        // Example XXXIX is valid but XXXIXX is not
                        if(lastOfNumber) return -5;
                        // Example XXX is valid but XXXX is not
                        if(subsequentSame > 3) return -3;
                    } else {
                        subsequentSame = 1;
                        lastOfNumber = false;
                        currentSmallest = currentSymbol;
                    }
                    res = res + currentSymbol;
                } else {
                    // Current symbol is part  of subtractive notation e.g. IV, where I subtracts from V

                    // Validation that we can only subtract from the last number of any size
                    // When subtracting, the number from which we subtract must be last one of its size.
                    if (!lastOfNumber) {
                        lastOfNumber = true;
                    } else {
                        return -5;
                    }

                    if (nextSymbol > currentSmallest) {
                        // Validation that the order is valid.
                        return -2;
                    } else {
                        // Next symbol is valid, smaller than current, roman numeral
                        currentSmallest = nextSymbol;
                        subsequentSame = 1;
                    }
                    int addition = validSubtraction(nextSymbol, currentSymbol);
                    if(addition == -1)
                        return -4;
                    res = res + addition;
                    // Because there can be only one subtractive symbol,
                    // we jump over it and the symbol it subtracts from, to consider the next possibly unprocessed symbol
                    index++;
                }
            } else {
                // Last symbol can only be additive
                if(str.length() > 1) {
                    if(lastOfNumber && processRoman(str.charAt(index - 1)) == currentSymbol) {
                        return -5;
                    }
                }
                res = res + currentSymbol;
                index++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        RomanNumeralConverter object = new RomanNumeralConverter();
        Scanner scanner = new Scanner(System.in);
        // We continue until the user wants to exit
        while(true) {
            System.out.println("Available characters M, D, C, L, X, V, I");
            System.out.println("Enter a Roman numeral (1-3999) you want to convert: ");
            String input = scanner.next();
            int converted = object.convert(input);
            // Converted values -1 to -5 are error messages, and positive values are valid numbers.
            if(converted == -1) {
                System.out.println("One of the characters present is not a valid Roman numeral.");
                System.out.println("Please check your input for characters not present in the above list of characters: " + input);
            } else if (converted == -2) {
                System.out.println("The order of the characters is wrong.\n");
                System.out.println("Please note that characters appear in the order presented in the above list of characters, except when you'd input 4 of the same character in a row.");
                System.out.println("In that case, you can subtract a single smaller character from a larger character to achieve the desired result: e.g. IIII => IV == 4\n");
                System.out.println("Please check your input's order from the above list of characters: " + input);
            } else if (converted == -3) {
                System.out.println("A Roman numeral may not contain more than 3 of the same character in a row.");
                System.out.println("Please check your input for characters appearing 4 times in a row: " + input);
            } else if (converted == -4) {
                System.out.println("In Roman numerals, there exists only 6 different possible subtractions:\nIV\nIX\nXL\nXC\nCD\nCM");
                System.out.println("Check your input for subtraction errors: " + input);
            } else if (converted == -5) {
                System.out.println("Subtraction must appear on the last character of the same size e.g. XXXIX is valid but XXIXIX or XXIXX is not");
                System.out.println("Check your input for subtraction errors: " + input);
            } else {
                System.out.println("Roman numeral in decimal form: " +  converted);
            }

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
