import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * a table model used by Jtable (computerTable) in ComputerTablePanel
 */

public class ComputerTableModel extends AbstractTableModel {
    private ArrayList<Computer> computers;

    public ComputerTableModel(ArrayList<Computer> computers){
        this.computers = computers;
    }

    @Override
    public int getRowCount() {
        return computers.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Computer c= computers.get(rowIndex);

        switch (columnIndex){
            case 0:
                return c.getCategory();
            case 1:
                return c.getType();
            case 2:
                return c.getID();
            case 3:
                return c.getBrand();
            case 4:
                return c.getCPU();
            case 5:
                return c.getPrice();
        }
        return null;
    }

    @Override
    public String getColumnName (int column){
        switch (column){
            case 0:
                return  "Category";
            case 1:
                return "Type";
            case 2:
                return "ID";
            case 3:
                return "Brand";
            case 4:
                return "CPU Family";
            case 5:
                return "Price($)";
        }
        return null;
    }
}