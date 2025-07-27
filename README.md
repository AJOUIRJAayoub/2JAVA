# IStore 2.0 - Système de Gestion de Magasins

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=flat&logo=mysql&logoColor=white)
![WAMP](https://img.shields.io/badge/WAMP-Server-orange.svg)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven&logoColor=white)

## Description

IStore 2.0 est un système de gestion de magasins développé en Java avec une interface graphique Swing et une base de données MySQL. L'application permet de gérer plusieurs magasins, leurs employés, et leurs inventaires d'articles. Elle offre des interfaces différenciées pour les administrateurs et les employés avec un système de whitelist pour contrôler les inscriptions.

### Caractéristiques principales :
- Interface graphique Java Swing moderne
- Deux types d'utilisateurs : Administrateurs et Employés
- Gestion multi-magasins (Carrefour, Auchan, Intermarché, Lidl, Netto)
- Système de whitelist pour contrôler les inscriptions
- Gestion des articles avec prix et stock
- Interface d'administration complète
- Base de données MySQL avec 5 tables (users, stores, inventories, articles, whitelist)
- Documentation JavaDoc complète
- JAR exécutable fourni

## Prérequis

- Java JDK 21
- WAMP Server (Apache, MySQL, PHP)
- IDE Java (IntelliJ IDEA recommandé)
- Maven (optionnel, pour la gestion des dépendances)
- Navigateur web pour phpMyAdmin

## Installation

### 1. Installation de WAMP Server

1. Téléchargez WAMP depuis : [https://www.wampserver.com/en/](https://www.wampserver.com/en/)
2. Installez WAMP en suivant l'assistant d'installation
3. Lancez WAMP Server
4. Vérifiez que l'icône WAMP est verte dans la barre des tâches

### 2. Configuration de la base de données

1. **Accéder à phpMyAdmin**
   - Ouvrez votre navigateur
   - Accédez à : `http://localhost/phpmyadmin`

2. **Créer la base de données**
   - Cliquez sur l'onglet "Bases de données"
   - Créez une base de données nommée `istore2_0`
   - Cliquez sur "Créer"

3. **Importer le fichier SQL**
   - Sélectionnez la base de données `istore2_0`
   - Cliquez sur l'onglet "Importer"
   - Parcourez et sélectionnez `Database/istore2_0 (2).sql`
   - Cliquez sur "Exécuter"

### 3. Configuration du projet Java

1. **Cloner le repository**
   ```bash
   git clone https://github.com/AJOUIRJAayoub/2JAVA.git
   cd 2JAVA
   ```

2. **Ouvrir dans votre IDE**
   - Importez le projet comme projet Java existant
   - Configurez le JDK si nécessaire

3. **Configurer la connexion à la base de données**
   - Le projet utilise les paramètres par défaut :
   ```java
   String url = "jdbc:mysql://localhost:3306/istore2_0";
   String user = "root";
   String password = "";
   ```

## Utilisation

### Lancer l'application

1. **Option 1 : Utiliser le JAR fourni**
   ```bash
   java -jar IStoreLTD.jar
   ```

2. **Option 2 : Depuis l'IDE**
   - Assurez-vous que WAMP Server est démarré
   - Dans votre IDE, localisez `src/main/java/UI/SignUpUI.java`
   - Exécutez `SignUpUI.java` (Clic droit → Run)

### Navigation dans l'application

1. **Inscription** 
   - Email doit être dans la whitelist pour s'inscrire
   - Emails autorisés par défaut : `a` et `ayoub.ajouirja@supinfo.com`
   
2. **Connexion**
   - **Admin** : `ayoub.ajouirja@supinfo.com` / `123456`
   - **Employé** : `riad.ajouirja@supinfo.fr` / `123456`

3. **Interface Administrateur**
   - Gestion des magasins (ajouter, supprimer)
   - Gestion des employés (ajouter, voir détails, assigner aux magasins)
   - Gestion de la whitelist
   - Vue d'ensemble du système

4. **Interface Employé**
   - Gestion des articles du magasin assigné
   - Ajout/modification des articles
   - Gestion des stocks et prix

## Structure du projet

```
2JAVA/
├── .idea/                          # Configuration IntelliJ IDEA
│   ├── artifacts/                  # Configuration des artifacts
│   └── ...                        # Autres fichiers de configuration
├── Database/                       # Scripts SQL
│   └── istore2_0 (2).sql          # Base de données IStore 2.0
├── Documentation/                  # Documentation JavaDoc générée
│   ├── Database/                   # Documentation des classes Database
│   ├── UI/                        # Documentation des interfaces
│   ├── index-files/               # Index de la documentation
│   ├── legal/                     # Informations légales
│   ├── resources/                 # Ressources (CSS, images)
│   └── script-dir/                # Scripts JavaScript
├── META-INF/                      # Métadonnées du projet
│   └── MANIFEST.MF                # Manifest pour l'exécutable JAR
├── out/                           # Fichiers compilés
│   └── artifacts/                 
│       └── Istore2_0_jar/         # JAR exécutable
├── src/                           # Code source
│   ├── libs/                      # Bibliothèques externes
│   │   └── mysql-connector-j-8.3.0.jar
│   └── main/
│       └── java/
│           ├── Database/          # Classes de gestion BDD
│           │   └── DatabaseManager.java
│           └── UI/                # Interfaces utilisateur
│               ├── SignUpUI.java          # Interface d'inscription
│               ├── LoginUI.java           # Interface de connexion
│               ├── AdminUI.java           # Interface administrateur
│               ├── UserUI.java            # Interface utilisateur
│               ├── UserListUI.java        # Liste des utilisateurs
│               ├── AddArticleDialog.java  # Ajout d'articles
│               ├── AddEmployeeDialog.java # Ajout d'employés
│               ├── AddStoreDialog.java    # Ajout de magasins
│               ├── EmployeeDetailsDialog.java
│               └── WhitelistAdminInterface.java
├── .gitattributes                 # Configuration Git
├── .gitignore                     # Fichiers ignorés par Git
├── IStoreLTD.jar                  # JAR exécutable du projet
├── pom.xml                        # Configuration Maven
└── README.txt                     # Instructions d'installation
```

## Base de données

### Structure des tables

1. **users** : Utilisateurs du système (admin/employee)
2. **stores** : Magasins (Carrefour, Auchan, etc.)
3. **inventories** : Inventaires liés aux magasins
4. **articles** : Articles avec prix et stock
5. **whitelist** : Emails autorisés à s'inscrire

### Données initiales

- 5 magasins prédéfinis
- 5 utilisateurs de test (1 admin, 4 employés)
- 2 emails dans la whitelist

## Dépannage

### WAMP ne démarre pas
- Vérifiez qu'aucun autre service n'utilise les ports 80 et 3306
- Désactivez temporairement le pare-feu Windows
- Lancez WAMP en tant qu'administrateur

### Erreur de connexion à la base de données
- Vérifiez que MySQL est démarré (icône WAMP verte)
- Confirmez les paramètres de connexion dans le code
- Assurez-vous que la base de données existe

### L'application ne se lance pas
- Vérifiez la version du JDK installée
- Assurez-vous que toutes les dépendances sont présentes
- Consultez la console pour les messages d'erreur

## Technologies utilisées

- **Java 21** : Langage de programmation principal
- **Swing** : Framework pour l'interface graphique
- **MySQL 8.0** : Système de gestion de base de données
- **JDBC** : MySQL Connector/J 8.3.0
- **Maven** : Gestion des dépendances et build
- **WAMP Server** : Environnement de développement
- **phpMyAdmin** : Interface web pour MySQL
- **IntelliJ IDEA** : IDE recommandé

## Contexte

Ce projet a été réalisé dans le cadre du module 2JAVA à SUPINFO, projet de deuxième année.

## Contact

Pour toute question ou suggestion, n'hésitez pas à ouvrir une issue sur GitHub.

---
Projet 2JAVA - SUPINFO
