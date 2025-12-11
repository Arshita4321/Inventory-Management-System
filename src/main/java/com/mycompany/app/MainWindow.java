package com.mycompany.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.FileWriter;
import java.util.List;

public class MainWindow extends JFrame {
    private final InventoryManager manager = new InventoryManager();
    private final JTable table;
    private final JTextField searchField = new JTextField(20);

    public MainWindow() {
        setTitle("GroceryPro - Smart Inventory System");
        setSize(1150, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modern font
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));

        table = new JTable();
        table.setRowHeight(40);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(100, 149, 237));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBorder(new EmptyBorder(12, 12, 12, 12));
        toolbar.setBackground(UIManager.getColor("Panel.background"));

        // Beautiful button colors
        Color primary = new Color(41, 128, 185);   // Soft Blue
        Color danger = new Color(231, 76, 60);     // Red
        Color warning = new Color(241, 196, 15);   // Amber
        Color success = new Color(46, 204, 113);   // Green
        Color darkMode = new Color(155, 89, 182);  // Purple

        JButton addBtn = createButton("Add Item", primary);
        addBtn.addActionListener(e -> new AddEditDialog(this, manager, null, this::refresh));
        toolbar.add(addBtn);

        JButton editBtn = createButton("Edit", primary);
        editBtn.addActionListener(e -> editSelected());
        toolbar.add(editBtn);

        JButton deleteBtn = createButton("Delete", danger);
        deleteBtn.addActionListener(e -> deleteSelected());
        toolbar.add(deleteBtn);

        JButton lowStockBtn = createButton("Low Stock Alert", warning);
        lowStockBtn.addActionListener(e -> showLowStock());
        toolbar.add(lowStockBtn);

        JButton darkBtn = createButton("Dark Mode", darkMode);
        darkBtn.addActionListener(e -> toggleDarkMode());
        toolbar.add(darkBtn);

        toolbar.addSeparator();
        toolbar.add(new JLabel(" Search: "));
        searchField.setMaximumSize(new Dimension(280, 40));
        toolbar.add(searchField);

        JButton searchBtn = createButton("Search", primary);
        searchBtn.addActionListener(e -> search());
        toolbar.add(searchBtn);

        add(toolbar, BorderLayout.NORTH);
        refresh();
        setVisible(true);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void refresh() {
        table.setModel(new InventoryTableModel(manager.getAllItems()));
        colorCodeRows();
        styleTable();
    }

    private void colorCodeRows() {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    int qty = (int) table.getValueAt(row, 2); // Quantity column

                    if (qty < 10) {
                        c.setBackground(new Color(255, 182, 193)); // Light Red/Pink
                    } else if (qty <= 50) {
                        c.setBackground(new Color(255, 218, 185)); // Light Orange/Peach
                    } else {
                        c.setBackground(new Color(144, 238, 144)); // Light Green/Mint
                    }
                } else {
                    c.setBackground(table.getSelectionBackground());
                }
                return c;
            }
        });
    }

    private void styleTable() {
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center); // ID
        table.getColumnModel().getColumn(2).setCellRenderer(center); // Quantity
        table.getColumnModel().getColumn(3).setCellRenderer(center); // Price
    }

    private void search() {
        table.setModel(new InventoryTableModel(manager.search(searchField.getText())));
        colorCodeRows();
        styleTable();
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        Item item = manager.getAllItems().stream()
                .filter(i -> i.getId() == id).findFirst().orElse(null);
        if (item != null) new AddEditDialog(this, manager, item, this::refresh);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this item permanently?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) table.getValueAt(row, 0);
            manager.deleteItem(id);
            refresh();
        }
    }

    private void showLowStock() {
        List<Item> low = manager.getLowStockItems();
        JDialog dialog = new JDialog(this, "Low Stock Alert - Urgent Action Needed", true);
        dialog.getContentPane().setBackground(UIManager.getColor("Panel.background"));

        JTable reportTable = new JTable(new InventoryTableModel(low));
        reportTable.setRowHeight(45);
        reportTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        reportTable.getTableHeader().setBackground(new Color(231, 76, 60));
        reportTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(reportTable);
        scroll.setBorder(new EmptyBorder(15, 15, 15, 15));
        dialog.add(scroll, BorderLayout.CENTER);

        JButton exportBtn = createButton("Export Report to CSV", new Color(46, 204, 113));
        exportBtn.addActionListener(e -> {
            try (FileWriter writer = new FileWriter("Low_Stock_Report.csv")) {
                writer.write("ID,Name,Quantity,Price\n");
                for (Item i : low) {
                    writer.write(i.getId() + "," + i.getName() + "," + i.getQuantity() + "," + i.getPrice() + "\n");
                }
                JOptionPane.showMessageDialog(dialog, "Report exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Export failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(UIManager.getColor("Panel.background"));
        bottom.add(exportBtn);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setSize(850, 550);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void toggleDarkMode() {
        try {
            if (UIManager.getLookAndFeel().getName().contains("Dark")) {
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            } else {
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            }
            SwingUtilities.updateComponentTreeUI(this);
            refresh();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Theme change failed");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            System.err.println("Failed to set Look and Feel");
        }

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