package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Database.DatabaseManager;

public class WhitelistAdminInterface extends JFrame {
    private JTextField emailTextField;
    private JButton addButton;

    public WhitelistAdminInterface() {
        setTitle("Gestion de la liste blanche");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField();
        addButton = new JButton("Ajouter à la liste blanche");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                boolean success = DatabaseManager.addEmailToWhiteList(email);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Email ajouté à la liste blanche avec succès.");
                } else {
                    JOptionPane.showMessageDialog(null, "Impossible d'ajouter l'e-mail à la liste blanche.");
                }
            }
        });

        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(new JLabel()); // Spacer
        panel.add(addButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WhitelistAdminInterface();
            }
        });
    }
}
