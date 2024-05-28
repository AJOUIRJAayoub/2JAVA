package Ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Database.DatabaseManager;


public class AdminUI extends JFrame {
    private JPanel contentPane;
    private JPanel employeesPanel;
    private JPanel storesPanel;

    public void refreshEmployees() {
        employeesPanel.removeAll();
        List<DatabaseManager.Employee> employees = DatabaseManager.getEmployees();
        for (DatabaseManager.Employee employee : employees) {
            JPanel employeePanel = createEmployeePanel(employee);
            employeesPanel.add(employeePanel);
        }
        revalidate();
        repaint();
    }

    public void refreshStores() {
        storesPanel.removeAll();
        List<DatabaseManager.Store> stores = DatabaseManager.getStores();
        for (DatabaseManager.Store store : stores) {
            JPanel storePanel = createStorePanel(store.getName(), store.getId());
            storesPanel.add(storePanel);
        }
        revalidate();
        repaint();
    }

    public AdminUI() {
        setTitle("Interface Administrateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        contentPane.add(splitPane, BorderLayout.CENTER);

        employeesPanel = new JPanel();
        employeesPanel.setLayout(new BoxLayout(employeesPanel, BoxLayout.Y_AXIS));
        JScrollPane employeesScrollPane = new JScrollPane();
        employeesScrollPane.setViewportView(employeesPanel);
        splitPane.setLeftComponent(employeesScrollPane);

        storesPanel = new JPanel();
        storesPanel.setLayout(new BoxLayout(storesPanel, BoxLayout.Y_AXIS));
        JScrollPane storesScrollPane = new JScrollPane();
        storesScrollPane.setViewportView(storesPanel);
        splitPane.setRightComponent(storesScrollPane);

        refreshEmployees();
        refreshStores();



        JButton addEmployeeButton = new JButton("Ajouter un employé");
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployeeDialog(AdminUI.this).setVisible(true);
            }
        });

        JButton addStoreButton = new JButton("Ajouter un magasin");
        addStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStoreDialog(AdminUI.this).setVisible(true);
            }
        });

        JButton addArticleButton = new JButton("Ajouter un Article");
        addArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Afficher la boîte de dialogue pour ajouter un article
                new AddArticleDialog(AdminUI.this).setVisible(true);
            }
        });

        JButton whitelistButton = new JButton("Gérer la liste blanche");
        whitelistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WhitelistAdminInterface().setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(addStoreButton);
        buttonPanel.add(whitelistButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JPanel articleButtonPanel = new JPanel();
        articleButtonPanel.add(addArticleButton);
        contentPane.add(articleButtonPanel, BorderLayout.NORTH);


    }

    private JPanel createEmployeePanel(DatabaseManager.Employee employee) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Pseudo: " + employee.getPseudo());
        JLabel emailLabel = new JLabel("Email: " + employee.getEmail());
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));
        infoPanel.add(nameLabel);
        infoPanel.add(emailLabel);
        panel.add(infoPanel, BorderLayout.CENTER);

        JButton editButton = new JButton("Modifier");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeDetailsDialog(AdminUI.this, employee);
            }
        });

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(AdminUI.this,
                        "Êtes-vous sûr de vouloir supprimer cet employé ?", "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (DatabaseManager.deleteEmployee(employee)) {
                        JOptionPane.showMessageDialog(AdminUI.this, "Employé supprimé avec succès !");
                        refreshEmployees();
                    } else {
                        JOptionPane.showMessageDialog(AdminUI.this, "Erreur lors de la suppression de l'employé.");
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    public class WhitelistAdminInterface extends JFrame {
        private JTextField emailTextField;
        private JButton addButton;

        public WhitelistAdminInterface() {
            setTitle("Whitelist Management");
            setSize(300, 150);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));

            JLabel emailLabel = new JLabel("Email:");
            emailTextField = new JTextField();
            addButton = new JButton("Add to Whitelist");

            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String email = emailTextField.getText();
                    boolean success = DatabaseManager.addEmailToWhiteList(email);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Email added to whitelist successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add email to whitelist.");
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
    }

    private JPanel createStorePanel(String storeName, int storeId) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Nom du magasin: " + storeName);
        panel.add(nameLabel, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(AdminUI.this,
                        "Êtes-vous sûr de vouloir supprimer ce magasin ?", "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (DatabaseManager.deleteStore(storeId)) {
                        JOptionPane.showMessageDialog(AdminUI.this, "Magasin supprimé avec succès !");
                        refreshStores();
                    } else {
                        JOptionPane.showMessageDialog(AdminUI.this, "Erreur lors de la suppression du magasin.");
                    }
                }
            }
        });

        JButton viewEmployeesButton = new JButton("Voir les employés");
        viewEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer l'identifiant du magasin sélectionné


                // Vérifier si un magasin a été sélectionné
                if (storeId != -1) {
                    // Récupérer les employés associés à ce magasin
                    List<DatabaseManager.Employee> employees = DatabaseManager.getEmployeesByStoreId(storeId);

                    // Afficher les détails des employés dans une boîte de dialogue
                    StringBuilder message = new StringBuilder("Employés du magasin :\n");
                    for (DatabaseManager.Employee employee : employees) {
                        message.append("Pseudo: ").append(employee.getPseudo()).append(", Email: ").append(employee.getEmail()).append("\n");
                    }
                    JOptionPane.showMessageDialog(AdminUI.this, message.toString());
                } else {
                    // Afficher un message si aucun magasin n'est sélectionné
                    JOptionPane.showMessageDialog(AdminUI.this, "Veuillez sélectionner un magasin.");
                }
            }
        });

        JButton addEmployeesButton = new JButton("Ajouter des employés");
        addEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implémentez ici la logique pour ajouter des employés au magasin
                // Par exemple, afficher une boîte de dialogue pour sélectionner les employés à ajouter
                JOptionPane.showMessageDialog(AdminUI.this, "Fonctionnalité non implémentée.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewEmployeesButton);
        buttonPanel.add(addEmployeesButton);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminUI frame = new AdminUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}