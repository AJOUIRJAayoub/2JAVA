package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/istore2.0";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // Mettez votre mot de passe de base de données ici

    // Méthode pour établir une connexion à la base de données
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Méthode pour insérer un nouvel utilisateur dans la base de données
    // Méthode pour insérer un nouvel utilisateur dans la base de données
    public static boolean insertUser(String email, String pseudo, String password, String role) {
        try {
            if (!isEmailOnWhiteList(email)) {
                System.out.println("L'email n'est pas sur la liste blanche. L'inscription est refusée.");
                return false;
            }

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email, pseudo, password, role) VALUES (?, ?, ?, ?)");
            statement.setString(1, email);
            statement.setString(2, pseudo);
            statement.setString(3, password);
            statement.setString(4, role);
            int rowsInserted = statement.executeUpdate();
            // Si au moins une ligne a été insérée, l'opération a réussi
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En cas d'erreur, renvoyer false
        }
    }


    // Méthode pour authentifier l'utilisateur avec son email et son mot de passe
    public static boolean authenticateUser(String email, String password) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            // Si une ligne est retournée, l'authentification réussit
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En cas d'erreur, renvoyer false
        }
    }

    // Méthode pour vérifier si un utilisateur avec l'email donné existe dans la base de données
    public static boolean checkUserExists(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            // Si une ligne est retournée, l'utilisateur existe
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En cas d'erreur, renvoyer false
        }
    }

    // Méthode pour vérifier si un e-mail est sur la liste blanche
    public static boolean isEmailOnWhiteList(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM WhiteList WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Méthode pour récupérer la liste des employés depuis la base de données
    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT id, email, pseudo, password, role, store_id FROM users WHERE role = 'employee'";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String pseudo = resultSet.getString("pseudo");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String storeId = resultSet.getString("store_id"); // Modifier le type de données de int à String
                Employee employee = new Employee(id, email, pseudo, password, role, storeId); // Utilisation de storeId
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }




    // Méthode pour mettre à jour les informations d'un employé
    public static boolean updateEmployee(Employee employee, String newPseudo, String newEmail, String newPassword) {
        try (Connection connection = getConnection()) {
            StringBuilder queryBuilder = new StringBuilder("UPDATE users SET ");
            List<Object> parameters = new ArrayList<>();

            // Ajouter les colonnes à mettre à jour et leurs paramètres correspondants
            if (newPseudo != null) {
                queryBuilder.append("pseudo = ?, ");
                parameters.add(newPseudo);
            }
            if (newEmail != null) {
                queryBuilder.append("email = ?, ");
                parameters.add(newEmail);
            }
            if (newPassword != null) {
                queryBuilder.append("password = ?, ");
                parameters.add(newPassword);
            }


            // Supprimer la virgule finale et ajouter la condition WHERE
            queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
            queryBuilder.append(" WHERE id = ?");
            parameters.add(employee.getId());

            // Préparer la requête SQL
            try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
                // Remplir les paramètres
                for (int i = 0; i < parameters.size(); i++) {
                    statement.setObject(i + 1, parameters.get(i));
                }
                // Exécuter la mise à jour
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getStoreNames() {
        List<String> storeNames = new ArrayList<>();
        // Logic to fetch store names from the database and add them to the list
        // For example:
        // storeNames = someDatabaseMethodToGetStoreNames();
        return storeNames;
    }


    public static Employee authenticateUserAndGetEmployee(String email, String password) {
        Employee employee = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String pseudo = resultSet.getString("pseudo");
                String role = resultSet.getString("role");
                String storeId = resultSet.getString("store_id"); // Assurez-vous que ce champ existe dans votre base de données

                // Créer un nouvel objet Employee avec les données récupérées
                employee = new Employee(id, pseudo, email, password, role, storeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }




    public static boolean updatePassword(Employee employee, String newPassword) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET password = ? WHERE id = ?")) {
            statement.setString(1, newPassword);
            statement.setInt(2, employee.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour ajouter un e-mail à la liste blanche
    public static boolean addEmailToWhiteList(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO WhiteList (email,approved) VALUES (?,1)")) {
            statement.setString(1, email);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateRole(Employee employee, String newRole) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET role = ? WHERE id = ?")) {
            statement.setString(1, newRole);
            statement.setInt(2, employee.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePseudo(Employee employee, String newPseudo) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET pseudo = ? WHERE id = ?")) {
            statement.setString(1, newPseudo);
            statement.setInt(2, employee.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateEmail(Employee employee, String newEmail) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET email = ? WHERE id = ?")) {
            statement.setString(1, newEmail);
            statement.setInt(2, employee.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteEmployee(Employee employee) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setInt(1, employee.getId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


// Méthode pour insérer un nouvel employé dans la base de données
    public static boolean insertEmployee(String pseudo, String email, String password, String role, String store_id) {
        try {
            if (!isEmailOnWhiteList(email)) {
                System.out.println("L'email fourni n'est pas autorisé. Veuillez utiliser une adresse e-mail autorisée.");
                return false;
            }

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (pseudo, email, password, role, store_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, pseudo);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, role);
            statement.setString(5, store_id);  // set store_id
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                return generatedKeys.next();
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static boolean insertStore(String storeName) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO stores (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, storeName);
            int rowsInserted = statement.executeUpdate();

            // Vérifier si l'insertion a réussi
            if (rowsInserted > 0) {
                // Récupérer l'ID du magasin nouvellement inséré
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int storeId = generatedKeys.getInt(1);

                    // Insérer une entrée dans la table inventories avec le nom du magasin comme store_id
                    boolean inventoryInserted = insertInventory(storeName, storeId);

                    // Retourner true si l'insertion dans les deux tables a réussi
                    return inventoryInserted;
                }
            }
            return false; // En cas d'échec ou si aucun ID n'a été généré, retourner false
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En cas d'erreur, renvoyer false
        }
    }


    public static boolean insertInventory(String storeName, int storeId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO inventories (store_id) VALUES (?)")) {
            statement.setString(1, storeName);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En cas d'erreur, renvoyer false
        }
    }


    public static List<String> getInventoryNames() {
        List<String> inventoryNames = new ArrayList<>();
        String query = "SELECT store_id FROM inventories";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                inventoryNames.add(resultSet.getString("store_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryNames;
    }



    public static List<String> getAllStoreIds() {
        List<String> storeIds = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT store_id FROM inventories")) {
            while (resultSet.next()) {
                storeIds.add(resultSet.getString("store_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeIds;
    }


    public static boolean insertArticle(String inventoryId, String name, double price, int stock, int maxStock) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO articles (inventory_id, name, price, stock, Maxstock) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, inventoryId); // Spécifiez la valeur de inventoryId
            statement.setString(2, name);
            statement.setDouble(3, price);
            statement.setInt(4, stock);
            statement.setInt(5, maxStock);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En cas d'erreur, renvoyer false
        }
    }








    public static List<Store> getStores() {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT id, name FROM stores";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Store store = new Store(id, name);
                stores.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stores;
    }



    // Méthode pour récupérer les employés par identifiant de magasin
    public static List<Employee> getEmployeesByStoreId(int storeId) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT id, email, pseudo, password, role FROM users WHERE store_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, storeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String pseudo = resultSet.getString("pseudo");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                Employee employee = new Employee(id, pseudo, email, password, role, String.valueOf(storeId));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }



    public static boolean deleteStore(int storeId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM stores WHERE id = ?")) {
            statement.setInt(1, storeId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





    public static int getUserIdByEmail(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE email = ?")) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Si aucun utilisateur n'est trouvé avec cet e-mail, retourner -1
        return -1;
    }

    public static List<String> getAllStoreNames() {
        List<String> storeNames = new ArrayList<>();

        // Écrire la requête SQL pour récupérer les noms de tous les magasins
        String query = "SELECT name FROM stores";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Parcourir les résultats de la requête et ajouter chaque nom de magasin à la liste
            while (resultSet.next()) {
                String storeName = resultSet.getString("name");
                storeNames.add(storeName);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs de manière appropriée dans votre application
        }

        return storeNames;
    }



    public static boolean checkAdminRole(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT role FROM users WHERE email = ?")) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String role = resultSet.getString("role");
                return role.equals("admin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Si une erreur se produit ou si aucun résultat n'est trouvé, renvoyer false par défaut
    }




    // Classe Employee pour représenter les données de l'employé
    public static class Employee {
        private int id;
        private String pseudo;
        private String email;
        private String password;
        private String role;
        private String store_id;

        public Employee(int id, String pseudo, String email, String password, String role, String storeId) { // Modification du constructeur
            this.id = id;
            this.pseudo = pseudo;
            this.email = email;
            this.password = password;
            this.role = role;
            this.store_id = store_id;
        }

        // Ajoutez les méthodes getter nécessaires
        public int getId() {
            return id;
        }

        public String getPseudo() {
            return pseudo;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public String getStoreId() { // Modification du getter
            return store_id;
        }

        public void setPseudo(String pseudo) {
            this.pseudo = pseudo;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }





    public static class Store {
        private int id;
        private String name;

        public Store(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }




}
