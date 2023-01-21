import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * panel shown when user clicks on "check/update product details" tab in MainWindow
 */

public class UpdatePanel extends JPanel {
    // components
    private JLabel IDLabel;
    private JTextField IDText;
    private JLabel categoryLabel;
    private JComboBox categoryComboBox;
    private JLabel typeLabel;
    private JComboBox typeComboBox;
    private JLabel brandLabel;
    private JTextField brandText;
    private JLabel CPULAbel;
    private JTextField CPUText;
    private JLabel memoryLabel;
    private JTextField memoryText;
    private JLabel SSDLabel;
    private JTextField SSDText;
    private JLabel screenLabel;
    private JTextField screenText;
    private JLabel priceLabel;
    private JTextField priceText;
    private JButton add;
    private JButton update;
    private JButton delete;
    private JButton clear;

    //other objects
    private final Store store;
    private Computer selectedComputer;
    private final MainWindow mainWindow;

    public UpdatePanel(Store store, MainWindow parentFrame){
        this.store = store;
        this.mainWindow = parentFrame;

        createLabels();
        createTextAndComboBox();
        createButtons();
        addToPanel();
        checkUserAccess();
    }

    private void createLabels(){
        IDLabel = new JLabel("Model ID:", SwingConstants.RIGHT);
        categoryLabel = new JLabel("Category:", SwingConstants.RIGHT);
        typeLabel = new JLabel("Type:", SwingConstants.RIGHT);
        brandLabel = new JLabel("Brand:", SwingConstants.RIGHT);
        CPULAbel = new JLabel("CPU Family", SwingConstants.RIGHT);
        memoryLabel = new JLabel("Memory Size:", SwingConstants.RIGHT);
        SSDLabel = new JLabel("SSD Capacity:", SwingConstants.RIGHT);
        screenLabel = new JLabel("Screen Size:", SwingConstants.RIGHT);
        priceLabel = new JLabel("Price:", SwingConstants.RIGHT);
    }

    private void createTextAndComboBox(){
        IDText = new JTextField();
        categoryComboBox = new JComboBox(store.getComputerCategories().toArray());
        typeComboBox = new JComboBox(store.getComputerTypes().toArray());
        brandText = new JTextField();
        CPUText = new JTextField();
        memoryText = new JTextField();
        SSDText = new JTextField();
        screenText = new JTextField();
        priceText = new JTextField();
    }

    private void createButtons(){
        add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addComputer();
            }
        });
        update = new JButton("Update");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateComputer();
            }
        });
        delete = new JButton("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteComputer();
            }
        });
        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    //add components to updatePanel
    private void addToPanel(){
        setLayout(new GridLayout(0, 2));
        add(IDLabel);
        add(IDText);
        add(categoryLabel);
        add(categoryComboBox);
        add(typeLabel);
        add(typeComboBox);
        add(brandLabel);
        add(brandText);
        add(CPULAbel);
        add(CPUText);
        add(memoryLabel);
        add(memoryText);
        add(SSDLabel);
        add(SSDText);
        add(screenLabel);
        add(screenText);
        add(priceLabel);
        add(priceText);
        add(add);
        add(update);
        add(delete);
        add(clear);
    }

    public void setSelectedComputer(Computer computer){
        selectedComputer = computer;
    }

    //load computer data into components
    public void loadComputer(){
        IDText.setText(selectedComputer.getID());
        categoryComboBox.setSelectedItem(selectedComputer.getCategory());
        typeComboBox.setSelectedItem(selectedComputer.getType());
        brandText.setText(selectedComputer.getBrand());
        CPUText.setText(selectedComputer.getCPU());
        priceText.setText(Double.toString(selectedComputer.getPrice()));

        if (selectedComputer instanceof Desktop){
            memoryText.setText(Double.toString(((Desktop) selectedComputer).getMemoryGB()));
            SSDText.setText(Double.toString(((Desktop) selectedComputer).getSsdGB()));
            screenText.setText("N/A");
        }

        if (selectedComputer instanceof Laptop){
            memoryText.setText(Double.toString(((Laptop) selectedComputer).getMemoryGB()));
            SSDText.setText(Double.toString(((Laptop) selectedComputer).getSsdGB()));
            screenText.setText(Double.toString(((Laptop) selectedComputer).getScreenSize()));
        }

        if (selectedComputer instanceof Tablet){
            memoryText.setText("N/A");
            SSDText.setText("N/A");
            screenText.setText(Double.toString(((Tablet) selectedComputer).getScreenSize()));
        }
        this.revalidate();
    }

    // enable/disable editing access depending on logged-in user
    public void checkUserAccess(){
        if (mainWindow.getLoggedInUser().getRole().equals("Salesperson")){
            IDText.setEnabled(false);
            categoryComboBox.setEnabled(false);
            typeComboBox.setEnabled(false);
            brandText.setEnabled(false);
            CPUText.setEnabled(false);
            memoryText.setEnabled(false);
            SSDText.setEnabled(false);
            screenText.setEnabled(false);
            priceText.setEnabled(false);
            add.setEnabled(false);
            update.setEnabled(false);
            delete.setEnabled(false);
            clear.setEnabled(false);
        }
        if (mainWindow.getLoggedInUser().getRole().equals("Manager")){
            IDText.setEnabled(true);
            categoryComboBox.setEnabled(true);
            typeComboBox.setEnabled(true);
            brandText.setEnabled(true);
            CPUText.setEnabled(true);
            memoryText.setEnabled(true);
            SSDText.setEnabled(true);
            screenText.setEnabled(true);
            priceText.setEnabled(true);
            add.setEnabled(true);
            update.setEnabled(true);
            delete.setEnabled(true);
            clear.setEnabled(true);
        }
    }

    //add a new computer to mainWindow (if unique model ID)
    private void addComputer(){
        if (!store.checkUniqueComputer(IDText.getText())){
            //not a unique computer
            JOptionPane.showMessageDialog(mainWindow, "Please enter unique model ID");
            return;
        }

        if (categoryComboBox.getSelectedItem().toString().equals("All") || typeComboBox.getSelectedItem().toString().equals("All")){
            JOptionPane.showMessageDialog(mainWindow, "Select a category and type");
            return;
        }

        String category = categoryComboBox.getSelectedItem().toString();
        if (category.equals("Desktop PC")){
            try{
                checkComputerInput();
                checkDesktopInput();
                store.addDesktop(
                        Objects.requireNonNull(typeComboBox.getSelectedItem()).toString(),
                        IDText.getText(),brandText.getText(),
                        CPUText.getText(),
                        Double.parseDouble(memoryText.getText()),
                        Double.parseDouble(SSDText.getText()),
                        Double.parseDouble(priceText.getText())
                );
            }
            catch (DesktopInputException | ComputerInputException e){
                JOptionPane.showMessageDialog(mainWindow, e.getMessage());
                return;
            }
        }

        if (category.equals("Laptop")){
            try{
                checkComputerInput();
                checkLaptopInput();
                store.addLaptop(
                        Objects.requireNonNull(typeComboBox.getSelectedItem()).toString(),
                        IDText.getText(),brandText.getText(),
                        CPUText.getText(),
                        Double.parseDouble(memoryText.getText()),
                        Double.parseDouble(SSDText.getText()),
                        Double.parseDouble(screenText.getText()),
                        Double.parseDouble(priceText.getText())
                );
            }
            catch (ComputerInputException | LaptopInputException e){
                JOptionPane.showMessageDialog(mainWindow, e.getMessage());
                return;
            }
        }

        if (category.equals("Tablet")){
            try{
                checkComputerInput();
                checkTabletInput();
                store.addTablet(
                        Objects.requireNonNull(typeComboBox.getSelectedItem()).toString(),
                        IDText.getText(),brandText.getText(),
                        CPUText.getText(),
                        Double.parseDouble(screenText.getText()),
                        Double.parseDouble(priceText.getText())
                );
            }
            catch (ComputerInputException | TabletInputException e){
                JOptionPane.showMessageDialog(mainWindow, e.getMessage());
                return;
            }
        }

        mainWindow.revalidate();
        JOptionPane.showMessageDialog(mainWindow,"Successfully added new computer");
    }

    //update an entry based on what is in the Model ID textField
    private void updateComputer() {
        if (IDText.getText().isBlank()){
            JOptionPane.showMessageDialog(mainWindow, "A model ID must be entered to update a computer");
            return;
        }

        //check id model is already in system
        selectedComputer = store.getComputer(IDText.getText());
        if (selectedComputer == null){
            JOptionPane.showMessageDialog(mainWindow,"Model ID not found in system");
            return;
        }

        try{
            checkComputerInput();
            selectedComputer.setCategory(categoryComboBox.getSelectedItem().toString());
            selectedComputer.setType(typeComboBox.getSelectedItem().toString());
            selectedComputer.setBrand(brandText.getText());
            selectedComputer.setCPU(CPUText.getText());
            selectedComputer.setPrice(Double.parseDouble(priceText.getText()));
        }
        catch (ComputerInputException e){
            JOptionPane.showMessageDialog(mainWindow, e.getMessage());
        }

        if (selectedComputer instanceof Desktop){
            try{
                checkDesktopInput();
                ((Desktop) selectedComputer).setMemoryGB(Double.parseDouble(memoryText.getText()));
                ((Desktop) selectedComputer).setSsdGB(Double.parseDouble(SSDText.getText()));
            }
            catch (DesktopInputException e){
                JOptionPane.showMessageDialog(mainWindow, e.getMessage());
                return;
            }
        }
        if (selectedComputer instanceof Laptop){
            try{
                checkLaptopInput();
                ((Laptop) selectedComputer).setMemoryGB(Double.parseDouble(memoryText.getText()));
                ((Laptop) selectedComputer).setSsdGB(Double.parseDouble(SSDText.getText()));
                ((Laptop) selectedComputer).setScreenSize(Double.parseDouble(SSDText.getText()));
            }
            catch (LaptopInputException e){
                JOptionPane.showMessageDialog(mainWindow, e.getMessage());
                return;
            }
        }
        if (selectedComputer instanceof Tablet){
            try{
                checkTabletInput();
                ((Tablet) selectedComputer).setScreenSize(Double.parseDouble(screenText.getText()));
            }
            catch (TabletInputException e){
                JOptionPane.showMessageDialog(mainWindow, e.getMessage());
                return;
            }
        }
        JOptionPane.showMessageDialog(mainWindow, "Successfully updated computer");
        mainWindow.revalidate();
    }

    //delete a computer from MainWindow
    private void deleteComputer(){
        selectedComputer = store.getComputer(IDText.getText());
        if (selectedComputer == null){
            JOptionPane.showMessageDialog(mainWindow,"Model ID not found in system");
            return;
        }
        int result = JOptionPane.showConfirmDialog(
                mainWindow,
                "Are you sure you want to delete this computer?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );
        if (result == 0){
            store.deleteComputer(selectedComputer);
            mainWindow.revalidate();
            JOptionPane.showMessageDialog(mainWindow, "Successfully deleted computer");
        }
    }

    private void clearFields(){
        IDText.setText(null);
        categoryComboBox.setSelectedItem("All");
        typeComboBox.setSelectedItem("All");
        brandText.setText(null);
        CPUText.setText(null);
        memoryText.setText(null);
        SSDText.setText(null);
        screenText.setText(null);
        priceText.setText(null);
    }

    private void checkDesktopInput() throws DesktopInputException{
        if (SSDText.getText().isBlank()){throw new DesktopInputException(); }
        if (memoryText.getText().isBlank()){throw new DesktopInputException(); }
    }

    private void checkLaptopInput() throws LaptopInputException{
        if (SSDText.getText().isBlank()){throw new LaptopInputException(); }
        if (memoryText.getText().isBlank()){throw new LaptopInputException(); }
        if (screenText.getText().isBlank()){throw new LaptopInputException(); }
    }

    private void checkTabletInput() throws TabletInputException{
        if (screenText.getText().isBlank()){throw new TabletInputException(); }
    }

    private void checkComputerInput() throws ComputerInputException{
        if (IDText.getText().isBlank()) { throw new ComputerInputException();}
        if (brandText.getText().isBlank()) { throw new ComputerInputException();}
        if (CPUText.getText().isBlank()) { throw new ComputerInputException();}
        if (priceText.getText().isBlank()) { throw new ComputerInputException();}
    }

    private static class DesktopInputException extends Exception{
        private DesktopInputException(){
            super("Ensure SSD and memory has been entered");
        }
    }

    private static class LaptopInputException extends Exception{
        private LaptopInputException(){
            super("Ensure SSD, memory, and screen size has been entered");
        }
    }

    private static class TabletInputException extends Exception{
        private TabletInputException(){
            super("Ensure screen size has been entered");
        }
    }

    private static class ComputerInputException extends Exception{
        private ComputerInputException(){
            super("Ensure ID, Brand, CPU and price has been entered");
        }
    }
}
