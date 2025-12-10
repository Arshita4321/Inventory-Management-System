package com.mycompany.app;

import javax.swing.*;
import java.awt.*;

public class AddEditDialog extends JDialog {
    private final InventoryManager manager;
    private final JTextField nameField = new JTextField(20);
    private final JTextField qtyField = new JTextField(20);
    private final JTextField priceField = new JTextField(20);
    private final Runnable onSuccess;

    public AddEditDialog(JFrame parent, InventoryManager manager, Item item, Runnable onSuccess) {
        super(parent, item == null ? "Add New Item" : "Edit Item", true);
        this.manager = manager;
        this.onSuccess = onSuccess;

        setLayout(new GridLayout(4, 2, 10, 10));
        setResizable(false);

        add(new JLabel("   Name:"));
        add(nameField);
        add(new JLabel("   Quantity:"));
        add(qtyField);
        add(new JLabel("   Price:"));
        add(priceField);

        if (item != null) {
            nameField.setText(item.getName());
            qtyField.setText(String.valueOf(item.getQuantity()));
            priceField.setText(String.valueOf(item.getPrice()));
        }

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> saveAndClose(item));
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());

        JPanel buttons = new JPanel();
        buttons.add(saveBtn);
        buttons.add(cancelBtn);
        add(buttons);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);        // ← THIS WAS MISSING BEFORE! DIALOG WAS CREATED BUT NEVER SHOWN!
    }

    private void saveAndClose(Item existing) {
        try {
            String name = nameField.getText().trim();
            int qty = Integer.parseInt(qtyField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            if (name.isEmpty()) throw new Exception();

            if (existing == null) {
                manager.addItem(name, qty, price);
            } else {
                manager.updateItem(existing.getId(), name, qty, price);
            }
            onSuccess.run();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid Name, Quantity and Price!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}