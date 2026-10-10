import java.util.*;

interface IPaymentMethod {
    boolean pay(double amount);
    String getName();
}

class CreditCardPayment implements IPaymentMethod {
    public boolean pay(double amount) {
        return true;
    }

    public String getName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    public boolean pay(double amount) {
        return false;
    }

    public String getName() {
        return "Digital Wallet";
    }
}

class FoodItem {
    String name;
    double price;

    FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class LineItem {
    FoodItem item;
    int quantity;

    LineItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    double getTotal() {
        return item.price * quantity;
    }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }

    void notifyCustomer(String message) {
        System.out.println("Notification: " + message);
    }
}

class Order {
    int id;
    Customer customer;
    List<LineItem> items = new ArrayList<>();
    String status = "Pending Payment";

    Order(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
        System.out.println("Order created.");
    }

    void addItem(FoodItem item, int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        items.add(new LineItem(item, quantity));
        System.out.println("Added " + item.name + " (Qty " + quantity + ")");
    }

    double total() {
        double sum = 0;
        for (LineItem item : items) {
            sum += item.getTotal();
        }
        return sum;
    }

    void placeOrder(IPaymentMethod payment) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return;
        }

        System.out.println("Order placed successfully.");

        if (payment.pay(total())) {
            status = "Paid";
            System.out.println("Payment via " + payment.getName() + " successful.");
            System.out.println("Order status: " + status);
            customer.notifyCustomer("Order #" + id + " placed and paid.");
        } else {
            System.out.println("Payment via " + payment.getName() + " failed.");
            System.out.println("Order status: " + status);
            customer.notifyCustomer("Order #" + id + " placed, awaiting payment.");
        }
    }
}

public class week8ppQ5 {
    public static void main(String[] args) {
        Customer c = new Customer("Rahul");

        FoodItem pizza = new FoodItem("Pizza", 200);
        FoodItem soda = new FoodItem("Soda", 50);
        FoodItem burger = new FoodItem("Burger", 100);

        Order o1 = new Order(123, c);
        o1.placeOrder(new CreditCardPayment());

        o1.addItem(pizza, 2);
        o1.addItem(soda, 1);
        o1.placeOrder(new CreditCardPayment());

        Order o2 = new Order(124, c);
        o2.addItem(burger, 1);
        o2.placeOrder(new DigitalWalletPayment());
    }
}