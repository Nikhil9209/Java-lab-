package com.shopsphere.swing;

import com.shopsphere.swing.view.LoginFrame;

import javax.swing.*;

/**
 * Entry point for the ShopSphere Swing Desktop Administration Application.
 * Configures Pluggable Look and Feel (System Look and Feel) and launches LoginFrame.
 * RTU Advanced Java Syllabus Topic: Swing MVC Architecture.
 */
public class SwingAdminApp {

    public static void main(String[] args) {
        // Set System Pluggable Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("[SwingAdminApp] Could not set system look and feel: " + e.getMessage());
        }

        // Launch UI on Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
