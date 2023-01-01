import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        String[] parts = scanner.nextLine().split(" ");
        String dateString = parts[0];
        long specifiedNumber = Long.parseLong(parts[1]);

        System.out.println(isNewYear(dateString, specifiedNumber));
    }

    private static boolean isNewYear(String dateString, long specifiedNumber) {
        LocalDate date = LocalDate.parse(dateString);
        LocalDate result = date.plusDays(specifiedNumber);

        return result.getYear() > date.getYear();
    }
}