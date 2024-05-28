package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Database.DatabaseManager;

public class SignUpUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField pseudoField;
    private JButton signUpButton;
    private JButton goToLoginPageButton; // Bouton pour passer à la page de connexion

    public SignUpUI() {
        setTitle("Inscription");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // Ajoute des marges entre les composants

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordField = new JPasswordField(20);
        JLabel pseudoLabel = new JLabel("Pseudo:");
        pseudoField = new JTextField(20);

        signUpButton = new JButton("S'inscrire");
        signUpButton.setBackground(Color.GREEN); // Couleur de fond verte pour le bouton
        signUpButton.setForeground(Color.WHITE); // Couleur du texte blanc pour le bouton
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });

        goToLoginPageButton = new JButton("Page de connexion");
        goToLoginPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fermez la page d'inscription
                new LoginUI(); // Ouvrez la page de connexion
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(pseudoLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(pseudoField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(signUpButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(goToLoginPageButton, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        setVisible(true);
    }

    private void signUp() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String pseudo = pseudoField.getText();

        try {
            // Vérifier si l'e-mail est sur liste blanche
            if (DatabaseManager.isEmailOnWhiteList(email)) {
                // Insérer l'utilisateur dans la base de données
                if (DatabaseManager.insertUser(email, pseudo, password,"employee")) {
                    JOptionPane.showMessageDialog(this, "Inscription réussie !");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Votre e-mail n'est pas autorisé à s'inscrire.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignUpUI();
            }
        });
    }
}
