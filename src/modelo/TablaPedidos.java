package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TablaPedidos extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Producto", "Información/Cantidad", "Precio"};
    private List<String[]> pedidos = new ArrayList<>();


    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return pedidos.size();
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return pedidos.get(rowIndex)[columnIndex];
    }

    public void clearData() {
        pedidos.clear();
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
        return pedidos;
    }

    public void setPedidos(List<String[]> pedidos) {
        this.pedidos = pedidos;
    }


}
