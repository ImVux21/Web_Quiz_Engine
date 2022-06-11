package regex_learning;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class C1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = "scanner.nextLine()";

        // write your code here
        String theKeyIs = "the\\s+key\\s+is\\s+";
        String digitAndConsonant = "[\\d\\w&&[^aeiouAEIOU]]+";
        String specialCharAndVowels = "[aeiouAEIOU#!?]+";



        Pattern theKeyIsPattern = Pattern.compile(theKeyIs, Pattern.CASE_INSENSITIVE);
        Pattern pattern1 = Pattern.compile(theKeyIs + digitAndConsonant, Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile(theKeyIs + specialCharAndVowels, Pattern.CASE_INSENSITIVE);


        text = text.trim();

        Matcher matcher1 = pattern1.matcher(text);

        while (matcher1.find()) {
            String substring = matcher1.group();
            System.out.println("substring: " + substring);

            Matcher subMatcher = theKeyIsPattern.matcher(substring);

            if (subMatcher.find()) {
                int keyStart = subMatcher.end();
                System.out.println(substring.substring(keyStart));
            }
        }

    }
}
