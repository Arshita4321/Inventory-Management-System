package com.mycompany.app;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class InventoryTableModel extends AbstractTableModel {
    private final List<Item> items;
    private final String[] columns = {"ID", "Name", "Quantity", "Price"};

    public InventoryTableModel(List<Item> items) {
        this.items = items;
    }

    @Override public int getRowCount() { return items.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int col) { return columns[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Item item = items.get(row);
        return switch (col) {
            case 0 -> item.getId();
            case 1 -> item.getName();
            case 2 -> item.getQuantity();
            case 3 -> item.getPrice();
            default -> null;
        };
    }
}