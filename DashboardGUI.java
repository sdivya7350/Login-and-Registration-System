package loginapp;

import javax.swing.*;
import java.awt.*;

public class DashboardGUI extends JFrame {
    private String username;

    public DashboardGUI(String username) {
        this.username = username;
        setTitle("Dashboard");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> logout());

        add(welcomeLabel, BorderLayout.CENTER);
        add(logoutBtn, BorderLayout.SOUTH);
    }

    private void logout() {
        this.dispose();
        SwingUtilities.invokeLater(() -> new LoginRegisterGUI().setVisible(true));
    }
}
