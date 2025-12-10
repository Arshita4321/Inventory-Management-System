package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

class InventoryManagerTest {
    private InventoryManager manager;

    @BeforeEach
    void setUp() {
        // FORCE DELETE DATABASE BEFORE EVERY TEST
        new File("inventory.db").delete();
        manager = new InventoryManager();  // Fresh DB
    }

    @Test
    void testAddAndList() {
        manager.addItem("Apple", 50, 2.5);
        List<Item> items = manager.getAllItems();
        assertEquals(1, items.size());
        assertEquals("Apple", items.get(0).getName());
    }

    @Test
    void testUpdate() {
        manager.addItem("Banana", 10, 1.0);
        int id = manager.getAllItems().get(0).getId();
        manager.updateItem(id, "Yellow Banana", 20, 1.5);
        Item updated = manager.getAllItems().get(0);
        assertEquals("Yellow Banana", updated.getName());
        assertEquals(20, updated.getQuantity());
    }

    @Test
    void testDelete() {
        manager.addItem("Orange", 30, 0.8);
        int id = manager.getAllItems().get(0).getId();
        manager.deleteItem(id);
        assertTrue(manager.getAllItems().isEmpty());
    }

    @Test
    void testLowStock() {
        manager.addItem("Tomato", 5, 0.5);
        manager.addItem("Potato", 50, 0.4);
        List<Item> low = manager.getLowStockItems();
        assertEquals(1, low.size());
        assertEquals("Tomato", low.get(0).getName());
    }
}