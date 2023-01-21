import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * class representing a computer store
 */

public class Store {
    private String storeName;
    private ArrayList<Computer> computers;
    private ArrayList<User> users;

    public Store(String storeName){
        this.storeName = storeName;
        this.computers = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addUser(String name, String userName, String password, String role){
        this.users.add(new User(name, userName,password,role));
    }

    public void addDesktop(String type, String ID, String brand, String CPU, double memoryGB, double ssdGB, double price){
        this.computers.add(new Desktop(type, ID, brand, CPU, memoryGB, ssdGB, price));
    }

    public void addLaptop(String type, String ID, String brand, String CPU, double memoryGB, double ssdGB, double screenSize, double price){
        this.computers.add(new Laptop(type, ID, brand, CPU, memoryGB, ssdGB, screenSize, price));
    }

    public void addTablet(String type, String ID, String brand, String CPU, double screenSize, double price){
        this.computers.add(new Tablet(type, ID, brand, CPU, screenSize, price));
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public ArrayList<Computer> getComputers(){
        return computers;
    }

    public ArrayList<Computer>getComputers(String category, String type){
        ArrayList<Computer> resultComputers = new ArrayList<>();

        //case: return all computers
        if (category.equals("All") && type.equals("All")){
            return computers;
        }

        //case: return all categories, selected types
        if (category.equals("All")){
            for (Computer computer : computers){
                if (computer.getType().equals(type)){
                    resultComputers.add(computer);
                }
            }
        }

        //case: return selected categories, all types
        if (type.equals("All")){
            for (Computer computer : computers){
                if (computer.getCategory().equals(category)){
                    resultComputers.add(computer);
                }
            }
        }

        //case: return selected categories and types
        for (Computer computer : computers){
            if (computer.getCategory().equals(category) && computer.getType().equals(type)){
                resultComputers.add(computer);
            }
        }

        return resultComputers;
    }

    public void addComputers(String fileName) {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader(fileName));
            try{
                String line;
                while (((line = inputStream.readLine()))!=null){
                    String str[] = line.split(",");
                    if (str[0].equals("Desktop PC")){
                        addDesktop(str[1], str[2], str[3], str[4], Double.parseDouble(str[5]), Double.parseDouble(str[6]), Double.parseDouble(str[7]));
                    }
                    if (str[0].equals("Laptop")){
                        addLaptop(str[1], str[2], str[3], str[4], Double.parseDouble(str[5]), Double.parseDouble(str[6]), Double.parseDouble(str[7]), Double.parseDouble(str[8]));
                    }
                    if (str[0].equals("Tablet")){
                        addTablet(str[1], str[2], str[3], str[4], Double.parseDouble(str[5]), Double.parseDouble(str[6]));
                    }
                }
            }
            finally {
                inputStream.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public HashSet<String> getComputerCategories(){
        HashSet<String> uniqueCategories = new HashSet<String>();
        uniqueCategories.add("All");
        for (Computer computer : computers){
            uniqueCategories.add(computer.getCategory());
        }
        return uniqueCategories;
    }

    public HashSet<String> getComputerTypes(){
        HashSet<String> uniqueTypes = new HashSet<String>();
        uniqueTypes.add("All");
        for (Computer computer : computers){
            uniqueTypes.add(computer.getType());
        }
        return uniqueTypes;
    }

    public boolean checkUniqueComputer(String ID){
        for (Computer computer : computers){
            if (computer.getID().equals(ID)){
                return false;
            }
        }
        return true;
    }

    public void deleteComputer(Computer selectedComputer){
        computers.remove(selectedComputer);
    }

    public Computer getComputer(String ID){
        for (Computer computer : computers){
            if (computer.getID().equals(ID)){
                return computer;
            }
        }
        return null;
    }
}
