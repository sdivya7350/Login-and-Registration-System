package loginapp;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.sql.*;

public class LoginRegisterGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginRegisterGUI() {
        setTitle("Login & Register");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        Font font = new Font("Arial", Font.PLAIN, 14);

        JLabel title = new JLabel("Login & Register");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.DARK_GRAY);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(font);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(font);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.BLUE);

        // Positioning
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        add(userLabel, gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(passLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(loginBtn, gbc);
        gbc.gridx = 1;
        add(registerBtn, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        add(messageLabel, gbc);

        // Action Listeners
        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        setVisible(true);
    }

    private void register() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            messageLabel.setText("Fill all fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(checkQuery);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                messageLabel.setText("Username already exists!");
                return;
            }

            String insertQuery = "INSERT INTO users(username, password) VALUES (?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(insertQuery);
            psInsert.setString(1, user);
            psInsert.setString(2, pass);
            int inserted = psInsert.executeUpdate();

            if (inserted > 0) {
                messageLabel.setText("Registered successfully.");
            } else {
                messageLabel.setText("Registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Database error.");
        }
    }

    private void login() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            messageLabel.setText("Fill all fields.");
            System.out.println("Login failed: Empty username or password.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                System.out.println("Stored hash: " + storedHash);
                System.out.println("Input hash: " + hashPassword(pass));
                if (storedHash.equals(pass)) {
                    messageLabel.setText("Login successful!");
                    System.out.println("Login successful!");

                    SwingUtilities.invokeLater(() -> {
                        new DashboardGUI(user).setVisible(true);
                        this.dispose();
                    });
                } else {
                    messageLabel.setText("Invalid credentials.");
                    System.out.println("Password mismatch.");
                }
            } else {
                messageLabel.setText("Invalid credentials.");
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Database error.");
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return password;
        }
    }

    public static void main(String[] args) {
        new LoginRegisterGUI();
    }
}
