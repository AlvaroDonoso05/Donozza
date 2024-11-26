package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TablaUsuarios extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
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

    public void addUsuarioFirst(String nombre, String password, boolean isAdmin) {
        String[] usuario = new String[3];
        usuario[0] = nombre;
        usuario[1] = password;
        usuario[2] = String.valueOf(isAdmin);
        usuarios.add(usuario);
        fireTableDataChanged();
    }

    // Agregar un nuevo usuario
    public void addUsuario() {
        fireTableDataChanged();
    }

    // Eliminar un usuario por índice
    public void removeUsuario(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < usuarios.size()) {
            usuarios.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
        fireTableDataChanged();
    }

    // Actualizar un usuario existente
    public void updateUsuario(int rowIndex, String nombre, String password, boolean isAdmin) {
        String[] usuario = new String[3];
        usuario[0] = nombre;
        usuario[1] = password;
        usuario[2] = String.valueOf(isAdmin);
        usuarios.add(usuario);
        if (rowIndex >= 0 && rowIndex < usuarios.size()) {
            usuarios.set(rowIndex, usuario);
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
