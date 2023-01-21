/**
 * abstract base class representing a generic computer with generic computer properties
 */

public abstract class Computer {
    private String category;
    private String type;
    private String ID;
    private String brand;
    private String CPU;
    private double price;

    public Computer(String category, String type, String ID, String brand, String CPU, double price){
        this.category = category;
        this.type = type;
        this.ID = ID;
        this.brand = brand;
        this.CPU = CPU;
        this.price = price;
    }

    //getters and setters...
    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getType (){
        return type;
    }

    public void setType(String type){this.type = type;}

    public String getID (){
        return ID;
    }

    public void setID(String ID){this.ID = ID;}

    public String getBrand (){
        return brand;
    }

    public void setBrand(String brand){this.brand = brand;}

    public String getCPU (){
        return CPU;
    }

    public void setCPU(String CPU){this.CPU = CPU;}

    public double getPrice (){
        return price;
    }

    public void setPrice(Double price){this.price = price;}
}
