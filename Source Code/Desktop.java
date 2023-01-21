/**
 * desktop class extended from computer class...
 */

public class Desktop extends Computer {
    private double memoryGB;
    private double ssdGB;

    public Desktop(String type, String ID, String brand, String CPU, double memoryGB, double ssdGB, double price){
        super("Desktop PC", type, ID, brand, CPU, price);
        this.memoryGB = memoryGB;
        this.ssdGB = ssdGB;
    }

    public double getMemoryGB(){
        return memoryGB;
    }

    public void setMemoryGB(Double memoryGB){this.memoryGB = memoryGB;}

    public double getSsdGB(){
        return ssdGB;
    }

    public void setSsdGB(Double ssdGB){this.ssdGB = ssdGB;}

}
