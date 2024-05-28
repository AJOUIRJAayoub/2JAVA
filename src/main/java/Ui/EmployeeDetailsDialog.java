package Ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Database.DatabaseManager;

public class EmployeeDetailsDialog extends JDialog {
    private JTextField pseudoField;
    private JTextField emailField;
    private JTextField passwordField;

    private DatabaseManager.Employee employee;
    private JFrame parent;

    public EmployeeDetailsDialog(JFrame parent, DatabaseManager.Employee employee) {
        this.parent = parent;
        this.employee = employee;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Détails de l'employé");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel pseudoLabel = new JLabel("Pseudo:");
        pseudoField = new JTextField(employee.getPseudo(), 20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(employee.getEmail(), 20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(20);

        JButton updateButton = new JButton("Mettre à jour");
        updateButton.addActionListener(this::updateEmployeeDetails);


        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(pseudoLabel, constraints);
        constraints.gridx = 1;
        panel.add(pseudoField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(emailLabel, constraints);
        constraints.gridx = 1;
        panel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);



        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(updateButton, constraints);

        add(panel);
    }


    private void updateEmployeeDetails(ActionEvent e) {
        // Get input values
        String newPseudo = pseudoField.getText();
        String newEmail = emailField.getText();
        String newPassword = passwordField.getText();


        // Update employee in database
        boolean success = DatabaseManager.updateEmployee(employee, newPseudo, newEmail, newPassword);

        if (success) {
            JOptionPane.showMessageDialog(this, "Informations de l'employé mises à jour avec succès !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour des informations de l'employé.");
        }
    }
}
