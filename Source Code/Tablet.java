/**
 * tablet class extended from computer class...
 */

public class Tablet extends Computer {
    private double screenSize;

    public Tablet(String type, String ID, String brand, String CPU, double screenSize, double price){
        super("Tablet", type, ID, brand, CPU, price);
        this.screenSize = screenSize;
    }

    //unique properties for a tablet...
    public double getScreenSize(){
        return screenSize;
    }

    //unique properties for a tablet...
    public void setScreenSize(Double screenSize) {this.screenSize = screenSize;}

}
