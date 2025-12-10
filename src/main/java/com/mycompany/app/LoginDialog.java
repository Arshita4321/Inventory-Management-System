package com.mycompany.app;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private boolean success = false;

    public LoginDialog(JFrame parent) {
        super(parent, "Login - Grocery System", true);
        setSize(330, 180);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Username field ---
        panel.add(new JLabel("Username:"));
        JTextField user = new JTextField();
        panel.add(user);

        // --- Password field ---
        panel.add(new JLabel("Password:"));
        JPasswordField pass = new JPasswordField();
        panel.add(pass);

        // --- Buttons ---
        JButton login = new JButton("Login");
        JButton cancel = new JButton("Cancel");

        // Click login button
        login.addActionListener(e -> {
            String u = user.getText().trim();
            String p = new String(pass.getPassword());

            if ("admin".equals(u) && "123".equals(p)) {
                success = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Wrong credentials!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Click cancel button
        cancel.addActionListener(e -> {
            success = false;
            dispose();
        });

        panel.add(login);
        panel.add(cancel);

        add(panel);

        // Press ENTER → Login
        getRootPane().setDefaultButton(login);

        // Press ESC → Close dialog
        getRootPane().registerKeyboardAction(
                e -> dispose(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        setVisible(true);
    }

    public boolean isSuccess() {
        return success;
    }
}
