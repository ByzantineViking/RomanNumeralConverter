# RomanNumeralConverter
Java program converting the basic Roman numerals MDCLXVI to numbers (1-3999) with full validation for errors in input.

Following are definitions in the Roman numerals, and validated in the program.
1. Strings can only contain the above characters, and the relative order of the characters must be the same as above, except when subtracting.
2. There may not be more than 3 of the same character in a row, or 4 if the last one is subtracted from.
3. You can subtract a single smaller value from a larger one. The larger one must be the last one in the string of it's kind. In addition only the following subtractions are allowed: IV, IX, XL, XC, CD, and CM. Also you cannot subtract from a subtraction.

The program can be run with main method as a Java program. The package also contains a .jar-file at
romannumerals/out/artifacts/romannumerals_jar/romannumerals.jar
