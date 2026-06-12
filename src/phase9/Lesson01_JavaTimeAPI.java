package phase9;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * LESSON 1: JAVA TIME API
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Java Time API (java.time) is the modern date/time library
 * introduced in Java 8. It replaces the old Date/Calendar
 * classes with immutable, thread-safe types.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Old Date/Calendar were mutable and confusing
 * - Not thread-safe
 * - Poor API design
 * - Timezone handling was error-prone
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * E-commerce order:
 * - Order date: LocalDateTime
 * - Shipping date: LocalDate
 * - Delivery estimate: Period
 * - Processing time: Duration
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API timestamps:
 * - Request time: Instant
 * - User timezone: ZoneId
 * - Formatted output: DateTimeFormatter
 * - Expiration: Duration
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What's the difference between LocalDate, LocalTime, LocalDateTime?"
 * Answer: Date only, Time only, Date+Time (no timezone)
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using Date/Calendar (old API)
 * - Confusing Instant with LocalDateTime
 * - Not handling timezones properly
 * - Using wrong temporal unit
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Immutable objects (no synchronization needed)
 * - Thread-safe by design
 * - DateTimeFormatter should be cached
 * - Parsing is expensive (cache formatters)
 */

public class Lesson01_JavaTimeAPI {

    public static void main(String[] args) {
        System.out.println("=== JAVA TIME API ===\n");

        // ============================================================
        // EXAMPLE 1: LocalDate, LocalTime, LocalDateTime
        // ============================================================
        System.out.println("--- Example 1: Basic Types ---\n");

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println("LocalDate: " + date);
        System.out.println("LocalTime: " + time);
        System.out.println("LocalDateTime: " + dateTime);
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Creating specific dates
        // ============================================================
        System.out.println("--- Example 2: Creating Dates ---\n");

        LocalDate birthday = LocalDate.of(1990, 5, 15);
        LocalTime meetingTime = LocalTime.of(14, 30);
        LocalDateTime appointment = LocalDateTime.of(2024, 6, 15, 10, 0);

        System.out.println("Birthday: " + birthday);
        System.out.println("Meeting: " + meetingTime);
        System.out.println("Appointment: " + appointment);
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Parsing and formatting
        // ============================================================
        System.out.println("--- Example 3: Parsing and Formatting ---\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = dateTime.format(formatter);
        System.out.println("Formatted: " + formatted);

        LocalDateTime parsed = LocalDateTime.parse("2024-06-15 10:00:00", formatter);
        System.out.println("Parsed: " + parsed);
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Date arithmetic
        // ============================================================
        System.out.println("--- Example 4: Date Arithmetic ---\n");

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate lastMonth = today.minusMonths(1);

        System.out.println("Today: " + today);
        System.out.println("Next week: " + nextWeek);
        System.out.println("Last month: " + lastMonth);

        // Period
        Period period = Period.between(lastMonth, today);
        System.out.println("Period: " + period.getMonths() + " months, " + period.getDays() + " days");
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Duration
        // ============================================================
        System.out.println("--- Example 5: Duration ---\n");

        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 30);

        Duration duration = Duration.between(start, end);
        System.out.println("Duration: " + duration.toHours() + " hours");
        System.out.println("Minutes: " + duration.toMinutes());
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Instant (timestamp)
        // ============================================================
        System.out.println("--- Example 6: Instant ---\n");

        Instant now = Instant.now();
        System.out.println("Now (epoch millis): " + now.toEpochMilli());
        System.out.println("Now (seconds): " + now.getEpochSecond());

        Instant later = now.plusSeconds(3600);
        System.out.println("1 hour later: " + later);
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Time zones
        // ============================================================
        System.out.println("--- Example 7: Time Zones ---\n");

        ZonedDateTime utc = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime ny = ZonedDateTime.now(ZoneId.of("America/New_York"));
        ZonedDateTime tokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));

        System.out.println("UTC: " + utc);
        System.out.println("New York: " + ny);
        System.out.println("Tokyo: " + tokyo);
        System.out.println();

        // ============================================================
        // EXAMPLE 8: Common operations
        // ============================================================
        System.out.println("--- Example 8: Common Operations ---\n");

        LocalDate date2 = LocalDate.of(2024, 6, 15);

        System.out.println("Day of week: " + date2.getDayOfWeek());
        System.out.println("Day of month: " + date2.getDayOfMonth());
        System.out.println("Month: " + date2.getMonth());
        System.out.println("Year: " + date2.getYear());
        System.out.println("Is leap year: " + date2.isLeapYear());
        System.out.println();

        // ============================================================
        // EXAMPLE 9: Comparing dates
        // ============================================================
        System.out.println("--- Example 9: Comparing Dates ---\n");

        LocalDate date1 = LocalDate.of(2024, 6, 15);
        LocalDate date3 = LocalDate.of(2024, 6, 20);

        System.out.println("isBefore: " + date1.isBefore(date3));
        System.out.println("isAfter: " + date1.isAfter(date3));
        System.out.println("isEqual: " + date1.isEqual(date3));
        System.out.println("compareTo: " + date1.compareTo(date3));
        System.out.println();
    }
}
