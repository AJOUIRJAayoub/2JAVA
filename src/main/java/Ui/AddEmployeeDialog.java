package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Database.DatabaseManager;

public class AddEmployeeDialog extends JDialog {
    private JTextField pseudoField;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox<String> roleComboBox;
    private JComboBox<String> storeComboBox; // Nouveau champ pour sélectionner le magasin

    public AddEmployeeDialog(JFrame parent) {
        super(parent, "Ajouter un employé", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel pseudoLabel = new JLabel("Pseudo:");
        pseudoField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(20);
        JLabel roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>();
        JLabel storeLabel = new JLabel("Store:");
        storeComboBox = new JComboBox<>(); // Nouveau champ pour sélectionner le magasin

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        // Populate role combo box
        roleComboBox.addItem("admin");
        roleComboBox.addItem("employee");

        // Populate store combo box
        populateStoreComboBox();

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
        constraints.gridy = 3;
        panel.add(roleLabel, constraints);
        constraints.gridx = 1;
        panel.add(roleComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(storeLabel, constraints);
        constraints.gridx = 1;
        panel.add(storeComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, constraints);

        add(panel);
    }

    private void populateStoreComboBox() {
        // Clear existing items
        storeComboBox.removeAllItems();

        // Get store names from database
        List<String> storeNames = DatabaseManager.getInventoryNames();

        // Add store names to combo box
        for (String storeName : storeNames) {
            storeComboBox.addItem(storeName);
        }
    }

    private void addEmployee() {
        // Get input values
        String pseudo = pseudoField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = (String) roleComboBox.getSelectedItem();
        String store_id = (String) storeComboBox.getSelectedItem(); // Nouvelle valeur pour le magasin

        // Insert employee into database
        boolean success = DatabaseManager.insertEmployee(pseudo, email, password, role, store_id);

        if (success) {
            JOptionPane.showMessageDialog(this, "Employé ajouté avec succès !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'employé.");
        }
    }
}

