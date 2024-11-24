package modelo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TablaProductos extends AbstractTableModel {
    private String[] columnNames = {"Tipo", "Nombre", "Precio", "Ingredientes"};
    private List<Object[]> productos = new ArrayList<>();

    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return productos.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void clearData() {
        productos.clear();
        fireTableDataChanged();
    }

    public void addProducto(String tipo, String nombre, Double precio, String ingredientes) {
        productos.add(new Object[]{tipo, nombre, precio, ingredientes});
        fireTableDataChanged();
    }

    public void setProductos(List<Object[]> productos) {
        this.productos = productos;
        fireTableDataChanged();
    }

    public List<Object[]> getProductos() {
        return productos;
    }
}
