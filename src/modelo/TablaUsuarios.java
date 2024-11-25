package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablaUsuarios extends AbstractTableModel {
    private final String[] columnNames = {"Nombre", "Contraseña", "Admin"};
    private final List<String[]> usuarios = new ArrayList<>();

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

    // Agregar un nuevo usuario
    public void addUsuario(String nombre, String contrasena, boolean isAdmin) {
        usuarios.add(new String[]{
            nombre,
            contrasena,
            String.valueOf(isAdmin)
        });
        fireTableRowsInserted(usuarios.size() - 1, usuarios.size() - 1);
    }

    // Eliminar un usuario por índice
    public void removeUsuario(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < usuarios.size()) {
            usuarios.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    // Actualizar un usuario existente
    public void updateUsuario(int rowIndex, String nombre, String contrasena, boolean isAdmin) {
        if (rowIndex >= 0 && rowIndex < usuarios.size()) {
            usuarios.set(rowIndex, new String[]{
                nombre,
                contrasena,
                String.valueOf(isAdmin)
            });
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }

    // Obtener un usuario por índice
    public String[] getUsuarioAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < usuarios.size()) {
            return usuarios.get(rowIndex);
        }
        return null;
    }

    // Limpiar todos los datos
    public void clearData() {
        usuarios.clear();
        fireTableDataChanged();
    }
}
