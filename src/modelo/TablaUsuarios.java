package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablaUsuarios extends AbstractTableModel {
    private String[] columnNames = {"Nombre", "Contraseña", "Admin"};
    private List<String[]> usuarios = new ArrayList<>();

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return usuarios.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addUsuario(String nombre, String contrasena, boolean isAdmin) {
        usuarios.add(new String[]{
            nombre,
            contrasena,
            String.valueOf(isAdmin)
        });
        fireTableDataChanged();
    }

    public void clearData() {
        usuarios.clear();
        fireTableDataChanged();
    }
}