package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablaIngredientes extends AbstractTableModel {
    private final String[] columnNames = {"Nombre", "Precio", "Stock", "URL"};
    private final List<String[]> ingredientes = new ArrayList<>();

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

    // Agregar un nuevo ingrediente
    public void addIngrediente(String nombre, double precio, int stock, String url) {
        ingredientes.add(new String[]{
            nombre,
            String.valueOf(precio),
            String.valueOf(stock),
            url
        });
        fireTableRowsInserted(ingredientes.size() - 1, ingredientes.size() - 1);
    }

    // Eliminar un ingrediente por índice
    public void removeIngrediente(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < ingredientes.size()) {
            ingredientes.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    // Actualizar un ingrediente existente
    public void updateIngrediente(int rowIndex, String nombre, double precio, int stock, String url) {
        if (rowIndex >= 0 && rowIndex < ingredientes.size()) {
            ingredientes.set(rowIndex, new String[]{
                nombre,
                String.valueOf(precio),
                String.valueOf(stock),
                url
            });
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }

    // Obtener un ingrediente por índice
    public String[] getIngredienteAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < ingredientes.size()) {
            return ingredientes.get(rowIndex);
        }
        return null;
    }

    // Limpiar todos los datos
    public void clearData() {
        ingredientes.clear();
        fireTableDataChanged();
    }
}
