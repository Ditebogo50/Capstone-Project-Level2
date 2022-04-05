import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


class Customer {

    String name;
    String contact_number;
    String address;
    String email;
    String location;


}

class Restaurant {
    public Restaurant() {
        this.meals = new ArrayList<>();
    }

    String name;
    String location;
    String contact_number;
    ArrayList<Meal> meals;
    String special_prep;
    String total_amount;

}

class Meal {
    String name;
    String quantity;
    String price;

    public String lineItem() {
        return name + " X" + quantity + " R" + price;
    }
}

class Driver {
    String name;
    String location;
    int number_of_loads;

    public Driver(String name, String location, int number_of_loads) {
        this.name = name;
        this.location = location;
        this.number_of_loads = number_of_loads;
    }
}


public class Main {

    public static void main(String[] args) throws IOException {

        Customer c1 = new Customer();
        c1.name = JOptionPane.showInputDialog("Enter customer name: ");
        c1.contact_number = JOptionPane.showInputDialog("Enter customer contact number: ");
        c1.address = JOptionPane.showInputDialog("Enter customer address: ");
        c1.email = JOptionPane.showInputDialog("Enter customer email address: ");
        c1.location = JOptionPane.showInputDialog("Enter customer location: ");

        Restaurant r1 = new Restaurant();
        r1.name = JOptionPane.showInputDialog("Enter restaurant name: ");
        r1.location = JOptionPane.showInputDialog("Enter restaurant location: ");
        r1.contact_number = JOptionPane.showInputDialog("Enter restaurant contact number: ");
        String newMeal;
        do {
            Meal currentMeal = new Meal();
            currentMeal.name = JOptionPane.showInputDialog("Enter meal name: ");
            currentMeal.quantity = JOptionPane.showInputDialog("Enter how many meals: ");
            currentMeal.price = JOptionPane.showInputDialog("Enter the price of the meal: ");

            r1.meals.add(currentMeal);
            newMeal = JOptionPane.showInputDialog("Do you want to add more meals? (Yes/No) ");

        }
        while (newMeal.toLowerCase(Locale.ROOT).equals("yes"));

        r1.special_prep = JOptionPane.showInputDialog("Enter any special preparations: ");
        r1.total_amount = JOptionPane.showInputDialog("Enter total amount: ");


        File x = new File("drivers.txt");

        Scanner sc = new Scanner(x);

        int driverLoads;
        Driver currentDriver = null;
        while (sc.hasNextLine()) {
            String[] driverLine = sc.nextLine().split(",");

            String driverName = driverLine[0].strip();
            String driverLocation = driverLine[1].strip();
            driverLoads = Integer.parseInt(driverLine[2].strip());

            if (r1.location.toLowerCase(Locale.ROOT).equals(driverLocation.toLowerCase(Locale.ROOT))) {
                if (currentDriver == null || driverLoads < currentDriver.number_of_loads) {
                    currentDriver = new Driver(driverName, driverLocation, driverLoads);
                }
            }
        }
        //new file to write out updated drivers without losing the original text file
        FileWriter fw = new FileWriter("tmp.txt");

        sc = new Scanner(x);
        while (sc.hasNextLine()) {
            String[] driverLine = sc.nextLine().split(",");

            String driverName = driverLine[0].strip();
            String driverLocation = driverLine[1].strip();
            driverLoads = Integer.parseInt(driverLine[2].strip());

            String newLine;
            if (r1.location.toLowerCase(Locale.ROOT).equals(driverLocation.toLowerCase(Locale.ROOT)) && driverLoads == currentDriver.number_of_loads) {
                //after the location is matched add a load to the chosen driver
                newLine = currentDriver.name + ", " + currentDriver.location + ", " + (currentDriver.number_of_loads + 1) + "\n";
                //rewrite the line with new load
            } else {
                //keep the original line without the new load
                newLine = driverName + ", " + driverLocation + ", " + driverLoads + "\n";
            }
            fw.write(newLine);

        }
        fw.close();

        //rename the temporary file to the drivers text
        File tmp = new File("tmp.txt");
        tmp.renameTo(new File("drivers_new.txt"));


        String orderNumber = "1";

        FileWriter myWriter = new FileWriter("D:\\Java\\Capstone Project ll\\invoice.txt");
        if (currentDriver == null || !currentDriver.location.equals(c1.location)) {
            myWriter.write("Sorry! Our drivers are too far away from you to be able to deliver to your location.");
        } else {
            String mealOutput = "";
            for (Meal meal : r1.meals) {
                mealOutput += meal.lineItem() + "\n";

            }
            myWriter.write("Order number 1\n" +
                    "Customer: " + c1.name + "\n" +
                    "Email: " + c1.email + "\n" +
                    "Phone number: " + c1.contact_number + "\n" +
                    "Location: " + c1.location + "\n" +
                    "You have ordered the following from Aesop’s Pizza in Cape Town:\n" +
                    mealOutput +
                    "Special instructions: " + r1.special_prep + "\n" +
                    "Total: R" + r1.total_amount + "\n" +
                    currentDriver.name + " is nearest to the restaurant and so he will be delivering your\n" +
                    "order to you at :\n" +
                    c1.address + "\n" +
                    "If you need to contact the restaurant, their number is " + r1.contact_number);
        }
        myWriter.close();
        System.out.println("Successfully wrote to the file.");

        //array to add the customers names
        List<String> customerList = new ArrayList<>();
        customerList.add(c1.name + " " + orderNumber);

         //sort out the names
        Collections.sort(customerList);

        //make array a string
        String separator = ", ";
        String result = String.join(separator, customerList);


        //Outputs a text file that shows a list of the customer’s names and order numbers in alphabetical order
        try {
            FileWriter customerNames = new FileWriter("D:\\Java\\Capstone Project ll\\customerList.txt");
            customerNames.write("This is the list: " + result);
            customerNames.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            System.out.println();
            System.out.println(e.getMessage());
        }


        //Outputs a text file that shows customers names sorted by location
        try {
            FileWriter customerLocation = new FileWriter("D:\\Java\\Capstone Project ll\\customerLocation.txt");

            switch (c1.location) {
                case "Durban":
                    customerLocation.write("Durban customers: " +
                            c1.name);
                    break;
                case "Johannesburg":
                    customerLocation.write("Johannesburg customers: " +
                            c1.name);

                    break;
                case "Potchefstroom":
                    customerLocation.write("Potchefstroom customers: " +
                            c1.name);
                    break;
                case "Witbank":
                    customerLocation.write("Witbank customers: " +
                            c1.name);
                    break;
                case " Bloemfontein":
                    customerLocation.write(" Bloemfontein customers: " +
                            c1.name);
                    break;
                case " Springbok":
                    customerLocation.write(" Springbok customers: " +
                            c1.name);
                    break;
                case " Cape Town":
                    customerLocation.write(" Cape Town customers: " +
                            c1.name);
                    break;
                case " Port Elizabeth":
                    customerLocation.write(" Port Elizabeth customers: " +
                            c1.name);
                    break;
                default:
                    customerLocation.write("Unknown Location");
                    break;
            }
            customerLocation.close();

        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred");
            System.out.println();
            System.out.println(e.getMessage());
        }

    }
}


