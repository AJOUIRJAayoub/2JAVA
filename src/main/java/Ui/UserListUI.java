package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserListUI {
    // Méthode pour créer l'interface utilisateur de la liste des utilisateurs
    public void createUserListInterface(List<String> users) {
        // Créer une fenêtre Swing pour l'interface de la liste des utilisateurs
        JFrame userListFrame = new JFrame("Liste des Utilisateurs");
        userListFrame.setSize(400, 300);
        userListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Créer un panneau pour contenir la liste des utilisateurs
        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new GridLayout(0, 1));

        // Créer des cases à cocher pour chaque utilisateur
        List<JCheckBox> checkBoxes = new ArrayList<>();
        for (String user : users) {
            JCheckBox checkBox = new JCheckBox(user);
            checkBoxes.add(checkBox);
            userListPanel.add(checkBox);
        }

        // Créer un bouton pour valider la sélection
        JButton validateButton = new JButton("Valider la Sélection");
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les utilisateurs sélectionnés
                List<String> selectedUsers = new ArrayList<>();
                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        selectedUsers.add(checkBox.getText());
                    }
                }

                // Appeler une méthode pour traiter la sélection des utilisateurs
                handleSelection(selectedUsers);

                // Fermer la fenêtre après validation
                userListFrame.dispose();
            }
        });

        // Ajouter la liste des utilisateurs et le bouton de validation à la fenêtre
        userListFrame.getContentPane().add(new JScrollPane(userListPanel), BorderLayout.CENTER);
        userListFrame.getContentPane().add(validateButton, BorderLayout.SOUTH);

        // Afficher la fenêtre
        userListFrame.setVisible(true);
    }

    // Méthode pour traiter la sélection des utilisateurs
    private void handleSelection(List<String> selectedUsers) {
        // Faire quelque chose avec les utilisateurs sélectionnés, par exemple les ajouter à un magasin
        // Appelez votre méthode de gestion appropriée dans DatabaseManager ici
        // DatabaseManager.addUsersToStore(selectedUsers);
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
        // Créer une liste de noms d'utilisateurs (à remplacer par la récupération réelle depuis la base de données)
        List<String> users = new ArrayList<>();
        users.add("Utilisateur 1");
        users.add("Utilisateur 2");
        users.add("Utilisateur 3");

        // Créer et afficher l'interface de la liste des utilisateurs
        UserListUI userListUI = new UserListUI();
        userListUI.createUserListInterface(users);
    }
}
