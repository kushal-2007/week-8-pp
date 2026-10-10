import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;

abstract class Room {
    String name;

    Room(String name) {
        this.name = name;
    }

    abstract double pricePerDay();
}

class DeluxeRoom extends Room {
    DeluxeRoom(String name) {
        super(name);
    }

    double pricePerDay() {
        return 200;
    }
}

class StandardRoom extends Room {
    StandardRoom(String name) {
        super(name);
    }

    double pricePerDay() {
        return 150;
    }
}

class Reservation {
    Room room;
    LocalDate start;
    LocalDate end;
    LocalDate cancellationDeadline;
    boolean active = true;

    Reservation(Room room, LocalDate start, LocalDate end) {
        this.room = room;
        this.start = start;
        this.end = end;
        this.cancellationDeadline = start;
    }

    double price() {
        long days = ChronoUnit.DAYS.between(start, end);
        return days * room.pricePerDay();
    }

    boolean overlaps(LocalDate s, LocalDate e) {
        return active && s.isBefore(end) && e.isAfter(start);
    }
}

class BookingManager {
    List<Reservation> reservations = new ArrayList<>();

    void book(Room room, LocalDate start, LocalDate end) {
        if (!start.isBefore(end)) {
            System.out.println("Booking failed: Invalid dates.");
            return;
        }

        for (Reservation r : reservations) {
            if (r.room == room && r.overlaps(start, end)) {
                System.out.println("Booking failed: " + room.name
                        + " is not available for " + start + " to " + end);
                return;
            }
        }

        Reservation r = new Reservation(room, start, end);
        reservations.add(r);

        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n",
                room.name, start, end, r.price());
    }

    void cancel(Room room) {
        for (Reservation r : reservations) {
            if (r.room == room && r.active) {
                if (LocalDate.now().isBefore(r.cancellationDeadline)) {
                    r.active = false;
                    System.out.println("Reservation for " + room.name
                            + " cancelled successfully.");
                } else {
                    System.out.println("Cancellation deadline has passed.");
                }
                return;
            }
        }

        System.out.println("No active reservation found.");
    }
}

public class week8ppQ3 {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();

        Room r1 = new DeluxeRoom("Deluxe Room 101");
        Room r2 = new StandardRoom("Standard Room 205");

        LocalDate d1 = LocalDate.of(2024, 12, 1);
        LocalDate d2 = LocalDate.of(2024, 12, 5);
        LocalDate d3 = LocalDate.of(2024, 12, 3);
        LocalDate d4 = LocalDate.of(2024, 12, 7);

        manager.book(r1, d1, d2);
        manager.book(r2, d3, d4);
        manager.book(r1, d3, d4);
        manager.cancel(r1);
    }
}