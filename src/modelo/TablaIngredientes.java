package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablaIngredientes extends AbstractTableModel {
    private String[] columnNames = {"Nombre", "Precio", "Stock", "URL"};
    private List<String[]> ingredientes = new ArrayList<>();

    @Override
    public int getRowCount() {
        return ingredientes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ingredientes.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addIngrediente(String nombre, double precio, int stock, String url) {
        ingredientes.add(new String[]{
            nombre,
            String.valueOf(precio),
            String.valueOf(stock),
            url
        });
        fireTableDataChanged();
    }

    public void clearData() {
        ingredientes.clear();
        fireTableDataChanged();
    }
}
