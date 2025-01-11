package view;

import javax.swing.*;
import java.awt.*;
import controller.AuthController;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField namaLengkapField;
    private JTextField emailField;

    public RegisterView() {
        setTitle("Register Bank Sampah");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel titleLabel = new JLabel("Register Akun Baru");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Form fields
        gbc.gridwidth = 1;
        int gridy = 1;

        // Username
        mainPanel.add(new JLabel("Username:"), createGbc(0, gridy));
        usernameField = new JTextField(15);
        mainPanel.add(usernameField, createGbc(1, gridy++));

        // Password
        mainPanel.add(new JLabel("Password:"), createGbc(0, gridy));
        passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, createGbc(1, gridy++));

        // Confirm Password
        mainPanel.add(new JLabel("Konfirmasi Password:"), createGbc(0, gridy));
        confirmPasswordField = new JPasswordField(15);
        mainPanel.add(confirmPasswordField, createGbc(1, gridy++));

        // Nama Lengkap
        mainPanel.add(new JLabel("Nama Lengkap:"), createGbc(0, gridy));
        namaLengkapField = new JTextField(15);
        mainPanel.add(namaLengkapField, createGbc(1, gridy++));

        // Email
        mainPanel.add(new JLabel("Email:"), createGbc(0, gridy));
        emailField = new JTextField(15);
        mainPanel.add(emailField, createGbc(1, gridy++));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Kembali");
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);

        // Add action listeners
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String namaLengkap = namaLengkapField.getText();
            String email = emailField.getText();

            if (AuthController.getInstance().handleRegister(username, password,
                    confirmPassword, namaLengkap, email)) {
                dispose();
                new LoginView().setVisible(true);
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }
}