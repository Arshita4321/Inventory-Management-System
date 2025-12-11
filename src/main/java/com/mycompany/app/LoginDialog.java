package com.mycompany.app;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private boolean success = false;

    public LoginDialog(JFrame parent) {
        super(parent, "Login - Grocery Inventory System", true);
        setSize(480, 550);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(249, 250, 251));

        // Top decorative panel with gradient effect
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(79, 70, 229);
                Color color2 = new Color(99, 102, 241);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        topPanel.setPreferredSize(new Dimension(480, 160));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Lock icon
        JLabel iconLabel = new JLabel("🔐");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 60));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sign in to manage your inventory");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(224, 231, 255));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createVerticalGlue());
        topPanel.add(iconLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(titleLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(subtitleLabel);
        topPanel.add(Box.createVerticalGlue());

        // Form Panel - Card style
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
            )
        ));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // Username Section
        JLabel userLabel = new JLabel("👤 Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(55, 65, 81));
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        userField.setPreferredSize(new Dimension(360, 45));
        userField.setMaximumSize(new Dimension(360, 45));
        userField.setText("admin"); // Pre-filled for convenience
        userField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        userField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Password Section
        JLabel passLabel = new JLabel("🔑 Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(55, 65, 81));
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passField.setPreferredSize(new Dimension(360, 45));
        passField.setMaximumSize(new Dimension(360, 45));
        passField.setText("123"); // Pre-filled for convenience
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        passField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add focus effects
        addFocusEffect(userField);
        addFocusEffect(passField);

        // Buttons
        JButton loginBtn = createGradientButton("🚀 Login", new Color(79, 70, 229));
        JButton cancelBtn = createOutlineButton("Cancel", new Color(107, 114, 128));

        // Login action
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if ("admin".equals(username) && "123".equals(password)) {
                success = true;
                dispose();
            } else {
                showModernError();
                passField.setText("");
                passField.requestFocus();
            }
        });

        // Cancel action
        cancelBtn.addActionListener(e -> {
            success = false;
            dispose();
        });

        // Assemble card
        cardPanel.add(userLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        cardPanel.add(userField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(passLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        cardPanel.add(passField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(loginBtn);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(cancelBtn);

        // Footer with hint
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(249, 250, 251));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));

        JLabel hintLabel = new JLabel("<html><center>💡 <b>Quick Access:</b> admin / 123</center></html>");
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hintLabel.setForeground(new Color(107, 114, 128));
        footerPanel.add(hintLabel);

        // Assemble main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Keyboard shortcuts
        getRootPane().setDefaultButton(loginBtn);
        getRootPane().registerKeyboardAction(
            e -> dispose(),
            KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        setVisible(true);
    }

    private JButton createGradientButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(
                    0, 0, baseColor,
                    getWidth(), getHeight(), baseColor.brighter()
                );
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(360, 48));
        button.setMaximumSize(new Dimension(360, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(240, 240, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    private JButton createOutlineButton(String text, Color borderColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(borderColor);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(360, 45));
        button.setMaximumSize(new Dimension(360, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(249, 250, 251));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }

    private void addFocusEffect(JTextField field) {
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(79, 70, 229), 2, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(209, 213, 219), 2, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });
    }

    private void showModernError() {
        JDialog errorDialog = new JDialog(this, "Login Failed", true);
        errorDialog.setSize(350, 220);
        errorDialog.setLocationRelativeTo(this);
        errorDialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel iconLabel = new JLabel("❌");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 50));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel errorLabel = new JLabel("Invalid Credentials!");
        errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        errorLabel.setForeground(new Color(220, 38, 38));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel hintLabel = new JLabel("Please check username and password");
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        hintLabel.setForeground(new Color(107, 114, 128));
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton okButton = new JButton("Try Again");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        okButton.setBackground(new Color(79, 70, 229));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setPreferredSize(new Dimension(120, 38));
        okButton.setMaximumSize(new Dimension(120, 38));
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(e -> errorDialog.dispose());

        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(errorLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(hintLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(okButton);

        errorDialog.add(panel);
        errorDialog.setVisible(true);
    }

    public boolean isSuccess() {
        return success;
    }
}

// temp change
