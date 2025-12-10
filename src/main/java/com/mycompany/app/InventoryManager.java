package com.mycompany.app;

import java.util.List;

public class InventoryManager {

    // Initialize database on first use
    public InventoryManager() {
        Database.init();  // Creates inventory.db + items table
    }

    public void addItem(String name, int quantity, double price) {
        Database.addItem(name, quantity, price);
    }

    public void updateItem(int id, String name, int quantity, double price) {
        Database.updateItem(id, name, quantity, price);
    }

    public void deleteItem(int id) {
        Database.deleteItem(id);
    }

    public List<Item> getAllItems() {
        return Database.getAllItems();
    }

    public List<Item> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) return getAllItems();
        return getAllItems().stream()
                .filter(i -> i.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public List<Item> getLowStockItems() {
        return Database.getLowStockItems();  // Direct from DB
    }
}