package com.shopsphere.swing.view;

import com.shopsphere.dao.*;
import com.shopsphere.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Main Swing Administrator Dashboard.
 * Demonstrates Swing MVC View, JTabbedPane, JTable, JScrollPane, JComboBox, JTextField, JButton, JOptionPane.
 */
public class AdminDashboard extends JFrame {

    private final User currentUser;
    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final UserDAO userDAO = new UserDAO();

    // Table Models
    private DefaultTableModel productTableModel;
    private DefaultTableModel categoryTableModel;
    private DefaultTableModel orderTableModel;
    private DefaultTableModel userTableModel;
    private DefaultTableModel inventoryTableModel;

    private JTable productTable;
    private JTable categoryTable;
    private JTable orderTable;
    private JTable userTable;
    private JTable inventoryTable;

    public AdminDashboard(User user) {
        this.currentUser = user;
        setTitle("ShopSphere Admin Portal - RTU Advanced Java Desktop Module");
        setSize(980, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadAllData();
    }

    private void initComponents() {
        JPanel rootPanel = new JPanel(new BorderLayout());

        // 1. Top Header Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 23, 42));
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel brandLabel = new JLabel("ShopSphere Enterprise Admin [Desktop]");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        brandLabel.setForeground(Color.WHITE);
        topBar.add(brandLabel, BorderLayout.WEST);

        JPanel rightUserPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightUserPanel.setOpaque(false);

        JLabel userBadge = new JLabel("Logged in: " + (currentUser != null ? currentUser.getName() : "Administrator"));
        userBadge.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userBadge.setForeground(new Color(226, 232, 240));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutBtn.setBackground(new Color(239, 68, 68));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        });

        rightUserPanel.add(userBadge);
        rightUserPanel.add(logoutBtn);
        topBar.add(rightUserPanel, BorderLayout.EAST);
        rootPanel.add(topBar, BorderLayout.NORTH);

        // 2. Tabbed Pane for Modules
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabbedPane.addTab("Products Management", createProductPanel());
        tabbedPane.addTab("Categories", createCategoryPanel());
        tabbedPane.addTab("Orders & Tracking", createOrderPanel());
        tabbedPane.addTab("Customers", createUserPanel());
        tabbedPane.addTab("Inventory / Low Stock", createInventoryPanel());

        rootPanel.add(tabbedPane, BorderLayout.CENTER);

        // 3. Bottom Status Bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        JLabel statusLabel = new JLabel("Database: Connected (MySQL:3306) | Pluggable Look and Feel Active");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setForeground(Color.GRAY);
        statusBar.add(statusLabel, BorderLayout.WEST);
        rootPanel.add(statusBar, BorderLayout.SOUTH);

        setContentPane(rootPanel);
    }

    // --- TAB 1: PRODUCT MANAGEMENT ---
    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Name", "Brand", "Price (₹)", "Discount %", "Stock", "Category", "Status"};
        productTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        productTable = new JTable(productTableModel);
        productTable.setRowHeight(24);
        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Product Action Controls
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton addBtn = new JButton("Add Product");
        JButton editStockBtn = new JButton("Update Stock");
        JButton delBtn = new JButton("Delete Product");
        JButton refreshBtn = new JButton("Refresh");

        addBtn.addActionListener(e -> showAddProductDialog());
        editStockBtn.addActionListener(e -> handleUpdateStock());
        delBtn.addActionListener(e -> handleDeleteProduct());
        refreshBtn.addActionListener(e -> loadProductData());

        actionPanel.add(addBtn);
        actionPanel.add(editStockBtn);
        actionPanel.add(delBtn);
        actionPanel.add(refreshBtn);
        panel.add(actionPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- TAB 2: CATEGORY MANAGEMENT ---
    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Category Name", "Description", "Status"};
        categoryTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        categoryTable = new JTable(categoryTableModel);
        categoryTable.setRowHeight(24);
        panel.add(new JScrollPane(categoryTable), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton addCatBtn = new JButton("Add Category");
        JButton delCatBtn = new JButton("Delete Category");
        JButton refreshBtn = new JButton("Refresh");

        addCatBtn.addActionListener(e -> showAddCategoryDialog());
        delCatBtn.addActionListener(e -> handleDeleteCategory());
        refreshBtn.addActionListener(e -> loadCategoryData());

        actionPanel.add(addCatBtn);
        actionPanel.add(delCatBtn);
        actionPanel.add(refreshBtn);
        panel.add(actionPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- TAB 3: ORDER MANAGEMENT ---
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Order ID", "Customer", "Amount (₹)", "Payment Method", "Payment Status", "Order Status", "Date"};
        orderTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        orderTable = new JTable(orderTableModel);
        orderTable.setRowHeight(24);
        panel.add(new JScrollPane(orderTable), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel statusLbl = new JLabel("Change Status To:");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"CONFIRMED", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED"});
        JButton applyBtn = new JButton("Update Status");
        JButton refreshBtn = new JButton("Refresh Orders");

        applyBtn.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select an order from the table first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int orderId = (int) orderTableModel.getValueAt(selectedRow, 0);
            String newStatus = (String) statusCombo.getSelectedItem();
            if (orderDAO.updateStatus(orderId, newStatus)) {
                JOptionPane.showMessageDialog(this, "Order #" + orderId + " updated to " + newStatus, "Success", JOptionPane.INFORMATION_MESSAGE);
                loadOrderData();
            }
        });

        refreshBtn.addActionListener(e -> loadOrderData());

        actionPanel.add(statusLbl);
        actionPanel.add(statusCombo);
        actionPanel.add(applyBtn);
        actionPanel.add(refreshBtn);
        panel.add(actionPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- TAB 4: CUSTOMER MANAGEMENT ---
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"User ID", "Name", "Email", "Mobile", "Role", "Status", "Joined"};
        userTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        userTable = new JTable(userTableModel);
        userTable.setRowHeight(24);
        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton toggleBtn = new JButton("Toggle Active/Block");
        JButton refreshBtn = new JButton("Refresh");

        toggleBtn.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a user to update status.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int userId = (int) userTableModel.getValueAt(row, 0);
            String current = (String) userTableModel.getValueAt(row, 5);
            String updated = "ACTIVE".equalsIgnoreCase(current) ? "BLOCKED" : "ACTIVE";
            userDAO.updateStatus(userId, updated);
            loadUserData();
        });

        refreshBtn.addActionListener(e -> loadUserData());

        actionPanel.add(toggleBtn);
        actionPanel.add(refreshBtn);
        panel.add(actionPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- TAB 5: INVENTORY / LOW STOCK ---
    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Product ID", "Product Name", "Brand", "Current Stock", "Alert Status"};
        inventoryTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setRowHeight(24);
        panel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton restockBtn = new JButton("Restock (+50 Units)");
        JButton refreshBtn = new JButton("Refresh Stock");

        restockBtn.addActionListener(e -> {
            int row = inventoryTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a product to restock.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int productId = (int) inventoryTableModel.getValueAt(row, 0);
            int currentStock = (int) inventoryTableModel.getValueAt(row, 3);
            productDAO.updateStock(productId, currentStock + 50);
            JOptionPane.showMessageDialog(this, "Product #" + productId + " restocked with 50 units.", "Restocked", JOptionPane.INFORMATION_MESSAGE);
            loadInventoryData();
            loadProductData();
        });

        refreshBtn.addActionListener(e -> loadInventoryData());

        actionPanel.add(restockBtn);
        actionPanel.add(refreshBtn);
        panel.add(actionPanel, BorderLayout.SOUTH);

        return panel;
    }

    // --- DATA LOADERS ---
    private void loadAllData() {
        loadProductData();
        loadCategoryData();
        loadOrderData();
        loadUserData();
        loadInventoryData();
    }

    private void loadProductData() {
        productTableModel.setRowCount(0);
        List<Product> list = productDAO.getAllForAdmin();
        for (Product p : list) {
            productTableModel.addRow(new Object[]{
                    p.getProductId(), p.getName(), p.getBrand(), p.getPrice(),
                    p.getDiscount(), p.getStock(), p.getCategoryName(), p.isStatus() ? "ACTIVE" : "INACTIVE"
            });
        }
    }

    private void loadCategoryData() {
        categoryTableModel.setRowCount(0);
        List<Category> list = categoryDAO.getAll();
        for (Category c : list) {
            categoryTableModel.addRow(new Object[]{
                    c.getCategoryId(), c.getName(), c.getDescription(), c.getStatus()
            });
        }
    }

    private void loadOrderData() {
        orderTableModel.setRowCount(0);
        List<Order> list = orderDAO.getAllOrders();
        for (Order o : list) {
            orderTableModel.addRow(new Object[]{
                    o.getOrderId(), o.getUser() != null ? o.getUser().getName() : ("User #" + o.getUserId()),
                    o.getTotalAmount(), o.getPaymentMethod(), o.getPaymentStatus(), o.getOrderStatus(), o.getCreatedAt()
            });
        }
    }

    private void loadUserData() {
        userTableModel.setRowCount(0);
        List<User> list = userDAO.getAllUsers();
        for (User u : list) {
            userTableModel.addRow(new Object[]{
                    u.getUserId(), u.getName(), u.getEmail(), u.getMobile(), u.getRole(), u.getStatus(), u.getCreatedAt()
            });
        }
    }

    private void loadInventoryData() {
        inventoryTableModel.setRowCount(0);
        List<Product> list = productDAO.getLowStock(30);
        for (Product p : list) {
            String alert = p.getStock() <= 10 ? "CRITICAL LOW" : "LOW STOCK WARNING";
            inventoryTableModel.addRow(new Object[]{
                    p.getProductId(), p.getName(), p.getBrand(), p.getStock(), alert
            });
        }
    }

    // --- ACTIONS ---
    private void showAddProductDialog() {
        JTextField nameField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField priceField = new JTextField("999.00");
        JTextField discountField = new JTextField("0.00");
        JTextField stockField = new JTextField("50");
        JTextField imgField = new JTextField("https://images.unsplash.com/photo-1523275335684-37898b6baf30");

        List<Category> categories = categoryDAO.getAll();
        JComboBox<Category> catCombo = new JComboBox<>(categories.toArray(new Category[0]));

        Object[] message = {
                "Product Name:", nameField,
                "Brand:", brandField,
                "Category:", catCombo,
                "Price (₹):", priceField,
                "Discount (%):", discountField,
                "Initial Stock:", stockField,
                "Image URL:", imgField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Product to Inventory", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Category selCat = (Category) catCombo.getSelectedItem();
                Product p = new Product();
                p.setCategoryId(selCat != null ? selCat.getCategoryId() : 1);
                p.setName(nameField.getText().trim());
                p.setBrand(brandField.getText().trim());
                p.setPrice(Double.parseDouble(priceField.getText().trim()));
                p.setDiscount(Double.parseDouble(discountField.getText().trim()));
                p.setStock(Integer.parseInt(stockField.getText().trim()));
                p.setImageUrl(imgField.getText().trim());
                p.setStatus(true);

                if (productDAO.insert(p)) {
                    JOptionPane.showMessageDialog(this, "Product inserted successfully! ID: " + p.getProductId());
                    loadProductData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inserting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleUpdateStock() {
        int row = productTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to update stock.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int productId = (int) productTableModel.getValueAt(row, 0);
        int currentStock = (int) productTableModel.getValueAt(row, 5);

        String input = JOptionPane.showInputDialog(this, "Enter new stock quantity for Product #" + productId + ":", currentStock);
        if (input != null) {
            try {
                int newStock = Integer.parseInt(input.trim());
                if (productDAO.updateStock(productId, newStock)) {
                    JOptionPane.showMessageDialog(this, "Stock updated successfully.");
                    loadProductData();
                    loadInventoryData();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDeleteProduct() {
        int row = productTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int productId = (int) productTableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Product #" + productId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (productDAO.delete(productId)) {
                JOptionPane.showMessageDialog(this, "Product deleted.");
                loadProductData();
                loadInventoryData();
            }
        }
    }

    private void showAddCategoryDialog() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        Object[] msg = {"Category Name:", nameField, "Description:", descField};
        int opt = JOptionPane.showConfirmDialog(this, msg, "Add Category", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            Category c = new Category();
            c.setName(nameField.getText().trim());
            c.setDescription(descField.getText().trim());
            c.setStatus("ACTIVE");
            categoryDAO.insert(c);
            loadCategoryData();
        }
    }

    private void handleDeleteCategory() {
        int row = categoryTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a category to delete.");
            return;
        }
        int catId = (int) categoryTableModel.getValueAt(row, 0);
        categoryDAO.delete(catId);
        loadCategoryData();
    }
}
