import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Panel displayed in MainWindow.
 * Panel contains list of computer details and options for search
 */

public class ComputerTablePanel extends JPanel {
    private final Store store;
    private final MainWindow mainWindow;
    private ArrayList<Computer> computers;
    private ComputerTableModel computerTableModel;
    private JTable computerTable;
    private JLabel labelCategory;
    private JLabel labelType;
    private JScrollPane scrollPane;
    private JComboBox comboBoxCategory;
    private String selectedCategory;
    private JComboBox comboBoxType;
    private String selectedType;
    private JPanel searchPanel;

    public ComputerTablePanel(Store store, MainWindow parentFrame){
        //setup panel
        this.store = store;
        this.mainWindow = parentFrame;
        computers = store.getComputers();

        //create components
        createComputerTable();
        createLabels();
        createComboBoxes();
        createSearchPanel();

        //final panel setup
        this.setLayout(new BorderLayout());
        this.add(searchPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void updateTable(){
        //probably more efficient way of doing this...
        this.remove(scrollPane);
        computers = store.getComputers(selectedCategory, selectedType);
        computerTableModel = new ComputerTableModel(computers);
        computerTable = new JTable(computerTableModel);
        scrollPane = new JScrollPane(computerTable);
        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();

        //none found with search
        if (computers.size() == 0){
            JOptionPane.showMessageDialog(mainWindow, "No computers found with specified search");
        }
    }

    private void createComputerTable(){
        //setup computer table listing computer details
        computerTableModel = new ComputerTableModel(computers);
        computerTable = new JTable(computerTableModel);

        //add ListSelectionListener to computerTable
        computerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    mainWindow.loadUpdatePanel(computers.get(computerTable.getSelectedRow()));
                }

            }
        });
        scrollPane = new JScrollPane(computerTable);
    }

    private void createLabels(){
        labelCategory = new JLabel("Computer Category");
        labelType = new JLabel("Computer Type");
    }

    private void createComboBoxes(){
        comboBoxCategory = new JComboBox(store.getComputerCategories().toArray());
        selectedCategory = comboBoxCategory.getSelectedItem().toString();
        comboBoxCategory.addActionListener(e -> {
            selectedCategory = comboBoxCategory.getSelectedItem().toString();
            updateTable();
        });

        comboBoxType = new JComboBox(store.getComputerTypes().toArray());
        selectedType = comboBoxType.getSelectedItem().toString();
        comboBoxType.addActionListener(e -> {
            selectedType = comboBoxType.getSelectedItem().toString();
            updateTable();
        });
    }

    private void createSearchPanel(){
        searchPanel = new JPanel();
        searchPanel.add(labelCategory);
        searchPanel.add(comboBoxCategory);
        searchPanel.add(labelType);
        searchPanel.add(comboBoxType);
        searchPanel.setLayout(new GridLayout(2,2));
    }
}
