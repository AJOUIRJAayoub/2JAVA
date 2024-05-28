package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import Database.DatabaseManager;
import Database.DatabaseManager.Employee;

public class LoginUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton goToSignUpPageButton;

    public LoginUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prepareUI();
    }

    private void prepareUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        goToSignUpPageButton = new JButton("Page d'inscription");

        setupComponents(panel, constraints);
        addActionListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupComponents(JPanel panel, GridBagConstraints constraints) {
        panel.add(new JLabel("Email:"), constraints);
        constraints.gridx = 1;
        panel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Mot de passe:"), constraints);

        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        constraints.gridy = 3;
        panel.add(goToSignUpPageButton, constraints);

        add(panel);
    }

    private void addActionListeners() {
        loginButton.addActionListener(e -> login());
        goToSignUpPageButton.addActionListener(e -> {
            dispose();
            new SignUpUI().setVisible(true);
        });
    }

    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Employee loggedInEmployee = DatabaseManager.authenticateUserAndGetEmployee(email, password);
            if (loggedInEmployee != null) {
                dispose();
                if ("admin".equals(loggedInEmployee.getRole())) {
                    JOptionPane.showMessageDialog(this, "Connecté avec succès en tant qu'admin !");
                    new AdminUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Connecté avec succès !");
                    new UserUI(loggedInEmployee).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Adresse e-mail ou mot de passe incorrect !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la connexion : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginUI::new);
    }
}
