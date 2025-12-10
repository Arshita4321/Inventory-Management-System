package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.List;

public class MainWindow extends JFrame {
    private final InventoryManager manager = new InventoryManager();
    private final JTable table;
    private final JTextField searchField = new JTextField(20);

    public MainWindow() {
        setTitle("Grocery Inventory Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable();
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        JButton addBtn = new JButton("Add Item");
        addBtn.addActionListener(e -> new AddEditDialog(this, manager, null, this::refresh));
        toolbar.add(addBtn);

        JButton editBtn = new JButton("Edit Selected");
        editBtn.addActionListener(e -> editSelected());
        toolbar.add(editBtn);

        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.addActionListener(e -> deleteSelected());
        toolbar.add(deleteBtn);

        JButton lowStockBtn = new JButton("Low Stock Report");
        lowStockBtn.addActionListener(e -> showLowStock());
        toolbar.add(lowStockBtn);

        // Dark Mode Button
        JButton darkBtn = new JButton("Dark Mode");
        darkBtn.addActionListener(e -> toggleDarkMode());
        toolbar.add(darkBtn);

        toolbar.addSeparator();
        toolbar.add(new JLabel(" Search: "));
        toolbar.add(searchField);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> search());
        toolbar.add(searchBtn);

        add(toolbar, BorderLayout.NORTH);
        refresh();
        setVisible(true);
    }

    private void refresh() {
        table.setModel(new InventoryTableModel(manager.getAllItems()));
    }

    private void search() {
        table.setModel(new InventoryTableModel(manager.search(searchField.getText())));
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to edit!");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        Item item = manager.getAllItems().stream()
                .filter(i -> i.getId() == id)
                .findFirst().orElse(null);
        if (item != null) {
            new AddEditDialog(this, manager, item, this::refresh);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete!");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        manager.deleteItem(id);
        refresh();
    }

    private void showLowStock() {
        List<Item> low = manager.getLowStockItems();
        JDialog dialog = new JDialog(this, "Low Stock Report (Quantity < 10)", true);
        JTable reportTable = new JTable(new InventoryTableModel(low));
        reportTable.setAutoCreateRowSorter(true);
        dialog.add(new JScrollPane(reportTable), BorderLayout.CENTER);

        JButton exportBtn = new JButton("Export to CSV");
        exportBtn.addActionListener(e -> {
            try (FileWriter writer = new FileWriter("Low_Stock_Report.csv")) {
                writer.write("ID,Name,Quantity,Price\n");
                for (Item i : low) {
                    writer.write(i.getId() + "," + i.getName() + "," + i.getQuantity() + "," + i.getPrice() + "\n");
                }
                JOptionPane.showMessageDialog(dialog, "Exported successfully to Low_Stock_Report.csv!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Export failed: " + ex.getMessage());
            }
        });
        dialog.add(exportBtn, BorderLayout.SOUTH);

        dialog.setSize(700, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void toggleDarkMode() {
        try {
            if (UIManager.getLookAndFeel().getName().contains("Dark")) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else {
                // Try multiple FlatLaf themes (one will work)
                try {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
                } catch (Exception ex) {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
            pack(); 
        } catch (Exception ex) {
           
        }
    }

    // MAIN METHOD WITH LOGIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null);
            if (login.isSuccess()) {
                new MainWindow();
            } else {
                System.exit(0);
            }
        });
    }
}

// fixed export bug
// fixed export 
// testing dark theme
