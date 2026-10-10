import java.time.*;
import java.time.temporal.ChronoUnit;

abstract class Employee {
    String name;

    Employee(String name) {
        this.name = name;
    }

    abstract int maxLeaveDays();
}

class FullTime extends Employee {
    FullTime(String name) {
        super(name);
    }

    int maxLeaveDays() {
        return 30;
    }
}

class PartTime extends Employee {
    PartTime(String name) {
        super(name);
    }

    int maxLeaveDays() {
        return 15;
    }
}

class LeaveRequest {
    Employee employee;
    LocalDate start;
    LocalDate end;
    String status = "Pending";

    LeaveRequest(Employee employee, LocalDate start, LocalDate end) {
        this.employee = employee;
        this.start = start;
        this.end = end;
    }

    void submit() {
        if (start.isAfter(end)) {
            System.out.println("Invalid leave dates.");
            return;
        }

        long days = ChronoUnit.DAYS.between(start, end) + 1;

        if (days > employee.maxLeaveDays()) {
            System.out.println("Leave request exceeds allowed days.");
            return;
        }

        System.out.println("Leave request submitted by " + employee.name
                + " for " + start + " to " + end
                + ". Status: " + status);
    }

    void review(String newStatus) {
        if (!status.equals("Pending")) {
            System.out.println("Cannot change status: " + status
                    + " request cannot revert to Pending.");
            return;
        }

        if (newStatus.equals("Approved") || newStatus.equals("Rejected")) {
            status = newStatus;
            System.out.println("Leave request for " + employee.name
                    + " " + status.toLowerCase() + ". Status: " + status);
        } else {
            System.out.println("Invalid status.");
        }
    }
}

public class week8ppQ4 {
    public static void main(String[] args) {
        Employee e1 = new FullTime("John Doe");
        Employee e2 = new PartTime("Jane Smith");

        LeaveRequest l1 = new LeaveRequest(e1,
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 12));

        LeaveRequest l2 = new LeaveRequest(e2,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5));

        l1.submit();
        l1.review("Approved");

        l2.submit();

        l1.review("Pending");
    }
}