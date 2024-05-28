package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Database.DatabaseManager;

public class AddArticleDialog extends JDialog {
    private JComboBox<String> inventoryComboBox;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField stockField;
    private JTextField maxStockField;

    public AddArticleDialog(JFrame parent) {
        super(parent, "Ajouter un article", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel inventoryLabel = new JLabel("Inventory ID:");
        inventoryComboBox = new JComboBox<>();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField(20);
        JLabel stockLabel = new JLabel("Stock:");
        stockField = new JTextField(20);
        JLabel maxStockLabel = new JLabel("MaxStock:");
        maxStockField = new JTextField(20);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addArticle();
            }
        });

        // Populate inventory combo box
        populateInventoryComboBox();

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(inventoryLabel, constraints);
        constraints.gridx = 1;
        panel.add(inventoryComboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(nameLabel, constraints);
        constraints.gridx = 1;
        panel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(priceLabel, constraints);
        constraints.gridx = 1;
        panel.add(priceField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(stockLabel, constraints);
        constraints.gridx = 1;
        panel.add(stockField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(maxStockLabel, constraints);
        constraints.gridx = 1;
        panel.add(maxStockField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, constraints);

        add(panel);
    }

    private void populateInventoryComboBox() {
        // Clear existing items
        inventoryComboBox.removeAllItems();

        // Get store names from database
        List<String> storeNames = DatabaseManager.getAllStoreNames(); // Modification ici pour récupérer les noms des magasins

        // Add store names to combo box
        for (String storeName : storeNames) {
            inventoryComboBox.addItem(storeName);
        }
    }

    private void addArticle() {
        // Get input values
        String inventory_id = (String) inventoryComboBox.getSelectedItem(); // Modification ici pour récupérer le nom du magasin
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int stock = Integer.parseInt(stockField.getText());
        int maxStock = Integer.parseInt(maxStockField.getText());

        // Insert article into database
        boolean success = DatabaseManager.insertArticle(inventory_id, name, price, stock, maxStock); // Modification ici pour utiliser le nom du magasin

        if (success) {
            JOptionPane.showMessageDialog(this, "Article ajouté avec succès !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'article. Veuillez réessayer plus tard.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddArticleDialog dialog = new AddArticleDialog(new JFrame());
                    dialog.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
