package com.shopsphere.swing.view;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Swing Desktop Login Window.
 * Demonstrates Swing components: JFrame, JPanel, JLabel, JTextField, JPasswordField, JButton, JOptionPane.
 * RTU Syllabus: Swing Desktop Admin Module.
 */
public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton exitBtn;
    private final UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("ShopSphere Desktop Administration - RTU Lab");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(new Color(248, 250, 252));

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        headerPanel.setBackground(new Color(248, 250, 252));
        JLabel titleLabel = new JLabel("ShopSphere Admin Console", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(79, 70, 229));

        JLabel subtitleLabel = new JLabel("Java Swing Desktop MVC Management", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(248, 250, 252));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        // Email
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        emailField = new JTextField("admin@shopsphere.com", 18);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        passwordField = new JPasswordField("admin123", 18);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(new Color(248, 250, 252));

        exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        exitBtn.addActionListener(e -> System.exit(0));

        loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginBtn.setBackground(new Color(79, 70, 229));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(this::handleLogin);

        buttonPanel.add(exitBtn);
        buttonPanel.add(loginBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Enter key initiates login
        getRootPane().setDefaultButton(loginBtn);

        setContentPane(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userDAO.authenticate(email, pass);
        if (user != null && user.isAdmin()) {
            JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + user.getName(), "Authorized", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(() -> new AdminDashboard(user).setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Invalid administrator credentials or unauthorized role.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
