package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TablaProductos extends AbstractTableModel {
    private String[] columnNames = {"Tipo", "Nombre", "Precio", "Ingredientes"};
    private List<String[]> usuarios = new ArrayList<>();


    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return usuarios.get(rowIndex)[columnIndex];
    }

    public void clearData() {
    	usuarios.clear();
        fireTableDataChanged();

    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public List<String[]> getPedidos() {
        return usuarios;
    }
    
    public void addProducto(String tipo, String nombre, Double precio, String ingredientes) {
        usuarios.add(new String[]{
            tipo,
        	nombre,
            String.valueOf(precio),
            ingredientes
        });
        fireTableDataChanged();
    }

    public void setPedidos(List<String[]> pedidos) {
        this.usuarios = pedidos;
    }
}