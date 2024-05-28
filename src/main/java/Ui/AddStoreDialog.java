package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Database.DatabaseManager;

public class AddStoreDialog extends JDialog {
    private JTextField storeNameField;
    private AdminUI adminUI;

    public AddStoreDialog(AdminUI parent) {
        super(parent, "Ajouter un magasin", true);
        this.adminUI = parent;
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel nameLabel = new JLabel("Nom du magasin:");
        storeNameField = new JTextField();
        panel.add(nameLabel);
        panel.add(storeNameField);
        getContentPane().add(panel, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String storeName = storeNameField.getText().trim();
                if (storeName.length() > 50) {
                    JOptionPane.showMessageDialog(AddStoreDialog.this,
                            "Le nom du magasin est trop long. Veuillez saisir un nom plus court.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (DatabaseManager.insertStore(storeName)) {
                        JOptionPane.showMessageDialog(AddStoreDialog.this,
                                "Magasin ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        adminUI.refreshStores(); // Rafraîchir la liste des magasins dans l'interface AdminUI
                        dispose(); // Ferme la boîte de dialogue après l'ajout réussi
                    } else {
                        JOptionPane.showMessageDialog(AddStoreDialog.this,
                                "Erreur lors de l'ajout du magasin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la boîte de dialogue sans ajouter de magasin
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}
