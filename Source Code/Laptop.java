/**
 * laptop class extended from computer class...
 */

public class Laptop extends Computer {
    private double memoryGB;
    private double ssdGB;
    private double screenSize;

    public Laptop(String type, String ID, String brand, String CPU, double memoryGB, double ssdGB, double screenSize, double price){
        super("Laptop", type, ID, brand, CPU, price);
        this.memoryGB = memoryGB;
        this.ssdGB = ssdGB;
        this.screenSize = screenSize;
    }

    public double getScreenSize(){
        return screenSize;
    }

    public void  setScreenSize(Double screenSize){ this.screenSize = screenSize;}

    public double getMemoryGB(){
        return memoryGB;
    }

    public void setMemoryGB(Double memoryGB){this.memoryGB = memoryGB;}

    public double getSsdGB(){
        return ssdGB;
    }

    public void setSsdGB(Double screenSize){ this.screenSize = screenSize;}
}
