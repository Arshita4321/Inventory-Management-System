package com.mycompany.app;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class InventoryTableModel extends AbstractTableModel {

    private final String[] columns = { "ID", "Name", "Quantity", "Price" };
    private final List<Item> items;

    public InventoryTableModel(List<Item> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> item.getId();
            case 1 -> item.getName();
            case 2 -> item.getQuantity();
            case 3 -> item.getPrice();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 2 -> Integer.class;
            case 3 -> Double.class;
            default -> String.class;
        };
    }
}
