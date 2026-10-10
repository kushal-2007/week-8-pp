import java.util.*;

abstract class Vehicle {
    String name;
    boolean available = true;

    Vehicle(String name) {
        this.name = name;
    }

    abstract double calculateCharge(int days);
}

class LuxuryCar extends Vehicle {
    LuxuryCar(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return days * 100;
    }
}

class StandardCar extends Vehicle {
    StandardCar(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return days * 50;
    }
}

class Rental {
    Vehicle vehicle;
    int days;

    Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }

    double getCharge() {
        return vehicle.calculateCharge(days);
    }
}

class RentalService {
    List<Rental> rentals = new ArrayList<>();

    void rentVehicle(Vehicle v, int days) {
        if (!v.available) {
            System.out.println(v.name + " is not available.");
            return;
        }

        if (days <= 0) {
            System.out.println("Invalid rental duration.");
            return;
        }

        v.available = false;
        Rental r = new Rental(v, days);
        rentals.add(r);

        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                v.name, days, r.getCharge());
    }

    void returnVehicle(Vehicle v) {
        if (v.available) {
            System.out.println(v.name + " is not currently rented.");
            return;
        }

        v.available = true;
        System.out.println(v.name + " returned. Now available.");
    }
}

public class week8ppQ2 {
    public static void main(String[] args) {
        Vehicle v1 = new LuxuryCar("Luxury Car A");
        Vehicle v2 = new StandardCar("Standard Car B");

        RentalService service = new RentalService();

        service.rentVehicle(v1, 3);
        service.rentVehicle(v2, 5);
        service.returnVehicle(v1);
    }
}