import java.time.LocalTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        String[] firstLineTimes = scanner.nextLine().split(" ");
        String[] secondLineTimes = scanner.nextLine().split(" ");

        System.out.println(isIntersect(firstLineTimes, secondLineTimes));
    }

    private static boolean isIntersect(String[] firstLineTimes, String[] secondLineTimes) {
        LocalTime startTimeInFirstLine = LocalTime.parse(firstLineTimes[0]);
        LocalTime endTimeInFirstLine = LocalTime.parse(firstLineTimes[1]);
        LocalTime startTimeInSecondLine = LocalTime.parse(secondLineTimes[0]);
        LocalTime endTimeInSecondLine = LocalTime.parse(secondLineTimes[1]);

        return !endTimeInSecondLine.isBefore(startTimeInFirstLine) && !startTimeInSecondLine.isAfter(endTimeInFirstLine);
    }
}