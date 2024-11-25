package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TablaProductos extends AbstractTableModel {

    private final String[] columnNames = {"Tipo", "Nombre", "Precio", "Extras"};
    private final List<Object[]> data;

    public TablaProductos() {
        data = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data.get(rowIndex)[columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void addProducto(String tipo, String nombre, double precio, String extras) {
        Object[] row = {tipo, nombre, precio, extras};
        data.add(row);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void removeProducto(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void updateProducto(int rowIndex, String tipo, String nombre, double precio, String extras) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.set(rowIndex, new Object[]{tipo, nombre, precio, extras});
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }

    public Object[] getProductoAt(int rowIndex) {
        return data.get(rowIndex);
    }
}
