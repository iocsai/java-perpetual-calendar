package perpetualcalendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PerpetualCalendar {

    private final int year;
    private final int month;
    private final int day;
    
    public static void main(String[] args) {
        PerpetualCalendar pc = new PerpetualCalendar(input());
        System.out.printf("%swas/will be on %s.%n", pc, Day.valueOf(pc.solve()));
    }

    public PerpetualCalendar(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public PerpetualCalendar(int[] args) {
        this(args[0], args[1], args[2]);
    }
    
    private static int[] input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the date in YYYY-MM-DD format: ");
        String inStr = sc.nextLine().replaceAll("\\.", "-");
        String[] splitter = inStr.split(findSeparator(inStr));
        int[] result = new int[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(splitter[i].trim());
        }
        return result;
    }

    private static String findSeparator(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)) || str.charAt(i) == '.') {
                return String.valueOf(str.charAt(i));
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return String.format("The day %d-%d-%d ", year, month, day);
    }

    private int solve() {
        int corrYear = this.month < 3 ? this.year - 1 : this.year;
        int corrMonth = this.month == 1 ? 14 : this.month == 2 ? 15 : month + 1;
        int y = (int) (365.25 * corrYear) + (int) (30.6 * corrMonth) +
                this.day - 694066;
        int x = (int) Math.round((y / 7.0 - (int) (y / 7)) * 7);
        int dayOfWeek = (int) (x * 1000 + .5) / 1000;
        return dayOfWeek;
    }
    
    public enum Day {
        MONDAY      (1),
        TUESDAY     (2), 
        WEDNAESDAY  (3), 
        THURSDAY    (4), 
        FRIDAY      (5), 
        SATURDAY    (6), 
        SUNDAY      (0);
        
        private final int dayOfWeek;
        private static final Map map = new HashMap<>();
        
        private Day(int dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }
        
        static {
            for (Day pageType : Day.values()) {
                map.put(pageType.dayOfWeek, pageType);
            }
        }
        
        private static Day valueOf(int number) {
            return (Day) map.get(number);
        }
    }
}