package Ui;

import javax.swing.*;
import Database.DatabaseManager.Employee;
import java.awt.BorderLayout;

public class UserUI extends JFrame {
    private JList<String> userList;
    private JButton settingsButton;
    private Employee loggedInEmployee;

    public UserUI(Employee employee) {
        this.loggedInEmployee = employee; // Stocker l'employé connecté
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Interface utilisateur - " + loggedInEmployee.getPseudo());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        userList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(userList);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        settingsButton = new JButton("⚙ Paramètres");
        settingsButton.addActionListener(e -> {
            // Ouvre EmployeeDetailsDialog avec les informations de l'employé connecté
            new EmployeeDetailsDialog(this, loggedInEmployee).setVisible(true);
        });
        getContentPane().add(settingsButton, BorderLayout.NORTH);

        setVisible(true);
    }
}
