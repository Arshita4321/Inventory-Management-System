package com.mycompany.app;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class AddEditDialog extends JDialog {
    private final InventoryManager manager;
    private final JTextField nameField = new JTextField(20);
    private final JTextField qtyField = new JTextField(20);
    private final JTextField priceField = new JTextField(20);
    private final Runnable onSuccess;

    public AddEditDialog(JFrame parent, InventoryManager manager, Item item, Runnable onSuccess) {
        super(parent, item == null ? "➕ Add New Item" : "✏ Edit Item", true);
        this.manager = manager;
        this.onSuccess = onSuccess;

        setSize(500, 450);
        setResizable(false);
        setLocationRelativeTo(parent);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(249, 250, 251));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(79, 70, 229));
        headerPanel.setPreferredSize(new Dimension(500, 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel headerLabel = new JLabel(item == null ? "Add New Item to Inventory" : "Edit Item Details");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        // Item Name
        JLabel nameLabel = createFieldLabel("📝 Item Name");
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setPreferredSize(new Dimension(400, 42));
        nameField.setMaximumSize(new Dimension(400, 42));
        styleTextField(nameField);

        // Quantity
        JLabel qtyLabel = createFieldLabel("📦 Quantity");
        qtyField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        qtyField.setPreferredSize(new Dimension(400, 42));
        qtyField.setMaximumSize(new Dimension(400, 42));
        styleTextField(qtyField);

        // Price
        JLabel priceLabel = createFieldLabel("💰 Price (₹)");
        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceField.setPreferredSize(new Dimension(400, 42));
        priceField.setMaximumSize(new Dimension(400, 42));
        styleTextField(priceField);

        // Pre-fill if editing
        if (item != null) {
            nameField.setText(item.getName());
            qtyField.setText(String.valueOf(item.getQuantity()));
            priceField.setText(String.valueOf(item.getPrice()));
        }

        // Add all fields to form
        formPanel.add(nameLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        
        formPanel.add(qtyLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(qtyField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        
        formPanel.add(priceLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(priceField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveBtn = createStyledButton(
            item == null ? "💾 Add Item" : "💾 Save Changes", 
            new Color(34, 197, 94), 
            true
        );
        saveBtn.addActionListener(e -> saveAndClose(item));

        JButton cancelBtn = createStyledButton("❌ Cancel", new Color(156, 163, 175), false);
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(buttonPanel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Press ENTER → Save
        getRootPane().setDefaultButton(saveBtn);

        // Press ESC → Cancel
        getRootPane().registerKeyboardAction(
            e -> dispose(),
            KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        setVisible(true);
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(55, 65, 81));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add focus listener for visual feedback
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(79, 70, 229), 2, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });
    }

    private JButton createStyledButton(String text, Color bgColor, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        int width = isPrimary ? 160 : 130;
        button.setPreferredSize(new Dimension(width, 44));
        
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor, 0, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void saveAndClose(Item existing) {
        try {
            String name = nameField.getText().trim();
            String qtyText = qtyField.getText().trim();
            String priceText = priceField.getText().trim();

            // Validation
            if (name.isEmpty()) {
                showError("Item name cannot be empty!");
                nameField.requestFocus();
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyText);
                if (qty < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                showError("Please enter a valid quantity (positive number)!");
                qtyField.requestFocus();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText);
                if (price < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                showError("Please enter a valid price (positive number)!");
                priceField.requestFocus();
                return;
            }

            // Save to database
            if (existing == null) {
                manager.addItem(name, qty, price);
                showSuccess("Item added successfully!");
            } else {
                manager.updateItem(existing.getId(), name, qty, price);
                showSuccess("Item updated successfully!");
            }
            
            onSuccess.run();
            dispose();

        } catch (Exception ex) {
            showError("An error occurred: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel iconLabel = new JLabel("⚠");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        iconLabel.setForeground(new Color(239, 68, 68));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);
        
        JOptionPane.showMessageDialog(
            this,
            panel,
            "Validation Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private void showSuccess(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel iconLabel = new JLabel("✓");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        iconLabel.setForeground(new Color(34, 197, 94));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);
        
        JOptionPane.showMessageDialog(
            this,
            panel,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}